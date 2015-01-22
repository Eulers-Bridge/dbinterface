package com.eulersbridge.iEngage.database.repository;

import com.eulersbridge.iEngage.database.domain.DatabaseDomainConstants;
import com.eulersbridge.iEngage.database.domain.Poll;
import com.eulersbridge.iEngage.database.domain.PollAnswer;
import com.eulersbridge.iEngage.database.domain.PollResultTemplate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by darcular on 21/09/14.
 */
public interface PollRepository extends GraphRepository<Poll>
{
	@Query ("MATCH (p:`"+DatabaseDomainConstants.POLL+"`)-[r:`"+DatabaseDomainConstants.HAS_POLL_LABEL+"`]-(o) where id(o)={ownerId} RETURN p")
	Page<Poll> findByOwnerId(@Param("ownerId")Long ownerId,Pageable p);

	@Query ("MATCH (p:`"+DatabaseDomainConstants.POLL+"`)-[r:`"+DatabaseDomainConstants.CREATED_BY_LABEL+"`]-(o) where id(o)={creatorId} RETURN p")
	Page<Poll> findByCreatorId(@Param("creatorId")Long creatorId,Pageable p);

	@Query("Match (a:`User`),(b) where id(a)={userId} and id(b)={pollId} CREATE UNIQUE a-[r:"+DatabaseDomainConstants.APQ_LABEL+
			"]-b SET r.timeStamp=coalesce(r.timeStamp,timestamp()),r.__type__='"+DatabaseDomainConstants.APQ_LABEL+"',r.answerIndex={answerIndex} return r")
	PollAnswer addPollAnswer(@Param("userId")Long userId,@Param("pollId")Long pollId,
								 @Param("answerIndex")Integer answerIndex);

	@Query("Match (a:`User`),(b) where id(a)={pollAnswer.getAnswererId} and id(b)={pollAnswer.getPollId()} CREATE UNIQUE a-[r:"+DatabaseDomainConstants.APQ_LABEL+
			"]-b SET r.timeStamp=coalesce(r.timeStamp,timestamp()),r.__type__='"+DatabaseDomainConstants.APQ_LABEL+"',r.answerIndex={pollAnswer.getAnswerIndex()} return r")
	PollAnswer addPollAnswer2(@Param("pollAnswer")PollAnswer pollAnswer);

	@Query("Match (a:`User`)-[r:"+DatabaseDomainConstants.APQ_LABEL+"]-(b) where id(a)={userId} and id(b)={pollId} return r")
	PollAnswer getPollAnswer(@Param("userId") Long answererId, @Param("pollId") Long pollId);
	
	@Query("MATCH (a:`User`)-[r:`"+DatabaseDomainConstants.APQ_LABEL+"`]->(b:`"+DatabaseDomainConstants.POLL+"`) WHERE id(b)={pollId} WITH collect(distinct r.answerIndex) as answers "+
			"unwind answers as x match (u:`User`)-[t:`"+DatabaseDomainConstants.APQ_LABEL+"`]-(p:`"+DatabaseDomainConstants.POLL+"`) where t.answerIndex=x return x as answer,count(t) as frequency order by x")
	Result <PollResultTemplate> getPollResults(@Param("pollId") Long pollId);

}
