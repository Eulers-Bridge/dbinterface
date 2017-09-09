package com.eulersbridge.iEngage.database.repository;

import com.eulersbridge.iEngage.database.domain.DataConstants;
import com.eulersbridge.iEngage.database.domain.Poll;
import com.eulersbridge.iEngage.database.domain.PollAnswerRelation;
import com.eulersbridge.iEngage.database.domain.PollResultTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PollRepository extends GraphRepository<Poll> {
  @Query("MATCH (p:`" + DataConstants.POLL + "`)-[r:`" + DataConstants.HAS_POLL_LABEL + "`]-(o) where id(o)={ownerId} RETURN p")
  Page<Poll> findByOwnerId(@Param("ownerId") Long ownerId, Pageable p);

  @Query("MATCH (p:`" + DataConstants.POLL + "`)-[r:`" + DataConstants.CREATED_BY_LABEL + "`]-(o) where id(o)={creatorId} RETURN p")
  Page<Poll> findByCreatorId(@Param("creatorId") Long creatorId, Pageable p);

  @Query("Match (a:`User`),(b) where id(a)={userId} and id(b)={pollId} CREATE UNIQUE (a)-[r:" + DataConstants.APQ_LABEL +
    "]->(b) SET r.timeStamp=coalesce(r.timeStamp,timestamp()),r.__type__='" + DataConstants.APQ_LABEL + "',r.answerIndex={answerIndex} return id(r)")
  Long addPollAnswer(@Param("userId") Long userId, @Param("pollId") Long pollId,
                     @Param("answerIndex") Integer answerIndex);

  @Query("MATCH (a:`User`)-[r:`" + DataConstants.APQ_LABEL + "`]->(b:`" + DataConstants.POLL + "`) WHERE id(b)={pollId} WITH collect(distinct r.answerIndex) as answers " +
    "unwind answers as x match (u:`User`)-[t:`" + DataConstants.APQ_LABEL + "`]-(p:`" + DataConstants.POLL + "`) where t.answerIndex=x and id(p)={pollId} return x as answer,count(t) as frequency order by x")
  List<PollResultTemplate> getPollResults(@Param("pollId") Long pollId);

}
