package com.eulersbridge.iEngage.database.repository;

import com.eulersbridge.iEngage.database.domain.DataConstants;
import com.eulersbridge.iEngage.database.domain.Poll;
import com.eulersbridge.iEngage.database.domain.PollOption;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Yikai Gong
 */

@Repository
public interface PollOptionRepository extends Neo4jRepository<PollOption, Long> {

  @Query("MATCH l=(p:Poll)-[r:"+ DataConstants.HAS_POLL_OPTION_LABEL+"]->(o:PollOption) where id(o)={optionId} return l")
  public PollOption findPollOptionAndPoll(@Param("optionId") Long optionId);

  @Query("Match (p:Poll)-[r:HAS_POLL_OPTION]->(o:PollOption) where id(p)={pollId} with distinct o Match (u:User)-[x:VOTE_POLL_OPTION]->(o) where u.email={userEmail} return distinct o")
  public PollOption checkIfUserHasVoted(@Param("pollId") Long pollId, @Param("userEmail") String userEmail);

  @Query("MATCH (o:PollOption), (u:User) where id(o)={optionId} and u.email={userEmail} CREATE UNIQUE (u)-[r:VOTE_POLL_OPTION]->(o) SET r.timestamp=coalesce(r.timestamp,timestamp())")
  public void votePollOption(@Param("userEmail") String userEmail, @Param("optionId") Long optionId);
}
