/**
 *
 */
package com.eulersbridge.iEngage.database.repository;

import com.eulersbridge.iEngage.database.domain.DataConstants;
import com.eulersbridge.iEngage.database.domain.PollAnswerRelation;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Greg Newitt
 */
@Repository
public interface PollAnswerRepository extends GraphRepository<PollAnswerRelation> {

  @Query("Match (a:`User`),(b) where id(a)={userId} and id(b)={pollId} CREATE UNIQUE (a)-[r:" + DataConstants.APQ_LABEL +
    "]->(b) SET r.timeStamp=coalesce(r.timeStamp,timestamp()),r.__type__='" + DataConstants.APQ_LABEL + "',r.answerIndex={answerIndex} return id(r)")
  Long addPollAnswer(@Param("userId") Long userId, @Param("pollId") Long pollId,
                     @Param("answerIndex") Integer answerIndex);

  @Query("Match (a:`User`),(b) where id(a)={pollAnswer.getAnswererId} and id(b)={pollAnswer.getPollId()} CREATE UNIQUE (a)-[r:" + DataConstants.APQ_LABEL +
    "]->(b) SET r.timeStamp=coalesce(r.timeStamp,timestamp()),r.__type__='" + DataConstants.APQ_LABEL + "',r.answerIndex={pollAnswer.getAnswerIndex()} return r")
  PollAnswerRelation addPollAnswer2(@Param("pollAnswer") PollAnswerRelation pollAnswerRelation);

  @Query("Match (a:`User`)-[r:" + DataConstants.APQ_LABEL + "]-(b) where id(a)={userId} and id(b)={pollId} return r")
  PollAnswerRelation getPollAnswer(@Param("userId") Long answererId, @Param("pollId") Long pollId);

}
