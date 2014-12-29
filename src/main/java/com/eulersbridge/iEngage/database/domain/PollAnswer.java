package com.eulersbridge.iEngage.database.domain;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;

import com.eulersbridge.iEngage.core.events.polls.PollAnswerDetails;

@RelationshipEntity(type=DatabaseDomainConstants.APQ_LABEL)
public class PollAnswer 
{
	@GraphId private Long id;
	private Owner answerer;
	private Poll poll;
	Integer answerIndex;
	private Long timeStamp;
	
    private static Logger LOG = LoggerFactory.getLogger(PollAnswer.class);

    public PollAnswer()
	{
		if (LOG.isTraceEnabled()) LOG.trace("Constructor");
	}
	
	public PollAnswer(Owner answerer, Poll poll, Integer answer)
	{
		this.answerer=answerer;
		this.poll=poll;
		this.answerIndex=answer;
		timeStamp=Calendar.getInstance().getTimeInMillis();
	}

	/**
	 * @return the id
	 */
	public Long getNodeId()
	{
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setNodeId(Long id) {
		this.id = id;
	}

	/**
	 * @return the answerer
	 */
	public Owner getAnswerer() {
		return answerer;
	}

	/**
	 * @param answerer the answerer to set
	 */
	public void setAnswerer(Owner answerer) {
		this.answerer = answerer;
	}

	/**
	 * @return the poll
	 */
	public Poll getPoll() {
		return poll;
	}

	/**
	 * @param poll the poll to set
	 */
	public void setPoll(Poll poll) {
		this.poll = poll;
	}

	/**
	 * @return the timeStamp
	 */
	public Long getTimeStamp() {
		return timeStamp;
	}

	/**
	 * @param timeStamp the timeStamp to set
	 */
	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}

	/**
	 * @return the answer
	 */
	public Integer getAnswer() {
		return answerIndex;
	}

	/**
	 * @param answer the answer to set
	 */
	public void setAnswer(Integer answer) {
		this.answerIndex = answer;
	}
	
	public PollAnswerDetails toPollAnswerDetails()
	{
		PollAnswerDetails dets=new PollAnswerDetails(id, answerer.getNodeId(), poll.getNodeId(), answerIndex, timeStamp);
		return dets;
	}
	
	static public PollAnswer fromPollAnswerDetails(PollAnswerDetails dets)
	{
		PollAnswer answer=new PollAnswer();
		answer.setAnswer(dets.getAnswerIndex());
		Owner answerer=new Owner();
		answerer.setNodeId(dets.getAnswererId());
		answer.setAnswerer(answerer);
		Poll poll=new Poll();
		poll.setNodeId(dets.getPollId());
		answer.setPoll(poll);
		answer.setNodeId(dets.getNodeId());
		if (dets.getTimeStamp()!=null) answer.setTimeStamp(dets.getTimeStamp());
		return answer;
	}
	
	@Override
	public String toString()
	{
		return "id = "+id+" answerer = "+answerer+" poll = "+poll+" timeStamp = "+timeStamp+" answerIndex = "+answerIndex;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		if (id!=null)
		{
			result = prime * result + id.hashCode();
		}
		else
		{
			result = prime * result + ((answerIndex == null) ? 0 : answerIndex.hashCode());
			result = prime * result
					+ ((answerer == null) ? 0 : answerer.hashCode());
			result = prime * result + ((poll == null) ? 0 : poll.hashCode());
			result = prime * result
					+ ((timeStamp == null) ? 0 : timeStamp.hashCode());
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PollAnswer other = (PollAnswer) obj;
		if (id != null) 
		{
			if (id.equals(other.id))
				return true;
			else return false;
		} 
		else
		{
			if (other.id != null)
				return false;
			if (answerIndex == null) {
				if (other.answerIndex != null)
					return false;
			} else if (!answerIndex.equals(other.answerIndex))
				return false;
			if (answerer == null) {
				if (other.answerer != null)
					return false;
			} else if (!answerer.equals(other.answerer))
				return false;
			if (poll == null) {
				if (other.poll != null)
					return false;
			} else if (!poll.equals(other.poll))
				return false;
			if (timeStamp == null) {
				if (other.timeStamp != null)
					return false;
			} else if (!timeStamp.equals(other.timeStamp))
				return false;
		}
		return true;
	}
	
}
