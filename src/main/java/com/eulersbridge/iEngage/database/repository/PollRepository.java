package com.eulersbridge.iEngage.database.repository;

import com.eulersbridge.iEngage.database.domain.DataConstants;
import com.eulersbridge.iEngage.database.domain.Poll;
import com.eulersbridge.iEngage.database.domain.PollResultTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PollRepository extends Neo4jRepository<Poll, Long> {
  @Query(value = "MATCH (own)<-[:HAS_POLL]-(p:Poll)-[:HAS_POLL_OPTION]->(o:PollOption)-[*0..1]-(x) where id(own)={ownerId} and (x:Photo OR x:PollOption OR (x:User and x.email={email})) return (own)--(p)--(o)-[*0..1]-(x)",
  countQuery = "MATCH (own)<-[:HAS_POLL]-(p:Poll)-[:HAS_POLL_OPTION]->(o:PollOption)-[*0..1]-(x) where id(own)={ownerId} and (x:Photo OR x:PollOption OR (x:User and x.email={email})) return count((own)--(p)--(o)-[*0..1]-(x))")
  Page<Poll> findByOwnerId(@Param("ownerId") Long ownerId, @Param("email") String email, Pageable p);

  @Query(value = "MATCH (p:`" + DataConstants.POLL + "`)-[r:`" + DataConstants.CREATED_BY_LABEL + "`]-(o) where id(o)={creatorId} RETURN p",
  countQuery = "MATCH (p:`" + DataConstants.POLL + "`)-[r:`" + DataConstants.CREATED_BY_LABEL + "`]-(o) where id(o)={creatorId} RETURN count(p)")
  Page<Poll> findByCreatorId(@Param("creatorId") Long creatorId, Pageable p);

  @Query("Match (a:`User`),(b) where id(a)={userId} and id(b)={pollId} CREATE UNIQUE (a)-[r:" + DataConstants.APQ_LABEL +
    "]->(b) SET r.timeStamp=coalesce(r.timeStamp,timestamp()),r.__type__='" + DataConstants.APQ_LABEL + "',r.answerIndex={answerIndex} return id(r)")
  Long addPollAnswer(@Param("userId") Long userId, @Param("pollId") Long pollId,
                     @Param("answerIndex") Integer answerIndex);

  @Query("MATCH (a:`User`)-[r:`" + DataConstants.APQ_LABEL + "`]->(b:`" + DataConstants.POLL + "`) WHERE id(b)={pollId} WITH collect(distinct r.answerIndex) as answers " +
    "unwind answers as x match (u:`User`)-[t:`" + DataConstants.APQ_LABEL + "`]-(p:`" + DataConstants.POLL + "`) where t.answerIndex=x and id(p)={pollId} return x as answer,count(t) as frequency order by x")
  List<PollResultTemplate> getPollResults(@Param("pollId") Long pollId);

  @Query("MATCH l=()-[*0..1]-(p:Poll)-[r:HAS_POLL_OPTION]-(o:PollOption)-[*0..1]-(x) WHERE id(p)={pollId} and (x:Photo OR x:PollOption OR (x:User and x.email={email})) return distinct l")
  Poll findOneCustom(@Param("pollId") Long pollId, @Param("email") String email);

//  p.end<{now} and
  @Query("Match l=(p:Poll)-[*0..1]->(x)<-[*0..1]-(y) where p.end<{now} and p.closed=false AND (x:Poll OR x:PollOption) AND (y:PollOption OR y:User) return distinct l")
  List<Poll> findExpiredUnclosedPolls(@Param("now") Long now);
}
