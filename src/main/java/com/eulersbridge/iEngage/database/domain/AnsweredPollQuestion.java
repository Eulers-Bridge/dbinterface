package com.eulersbridge.iEngage.database.domain;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

@RelationshipEntity(type=DatabaseDomainConstants.APQ_LABEL)
public class AnsweredPollQuestion 
{
	@GraphId private Long id;
	@StartNode private User answerer;
	@EndNode private Poll poll;
	String answer;
	private Long timeStamp;
	
    private static Logger LOG = LoggerFactory.getLogger(AnsweredPollQuestion.class);

    public AnsweredPollQuestion()
	{
		if (LOG.isTraceEnabled()) LOG.trace("Constructor");
	}
	
	public AnsweredPollQuestion(User answerer, Poll poll, String answer)
	{
		this.answerer=answerer;
		this.poll=poll;
		this.answer=answer;
		timeStamp=Calendar.getInstance().getTimeInMillis();
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the answerer
	 */
	public User getAnswerer() {
		return answerer;
	}

	/**
	 * @param answerer the answerer to set
	 */
	public void setAnswerer(User answerer) {
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
	public String getAnswer() {
		return answer;
	}

	/**
	 * @param answer the answer to set
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	@Override
	public String toString()
	{
		return "id = "+id+" answerer = "+answerer+" poll = "+poll+" timeStamp = "+timeStamp+" answer = "+answer;
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
			result = prime * result + ((answer == null) ? 0 : answer.hashCode());
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
		AnsweredPollQuestion other = (AnsweredPollQuestion) obj;
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
			if (answer == null) {
				if (other.answer != null)
					return false;
			} else if (!answer.equals(other.answer))
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
