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
		public boolean equals(Object other)
		{
			if (null == other) return false;
			if (other == this) return true;
			if (!(other instanceof AnsweredPollQuestion)) return false;
			AnsweredPollQuestion answer2=(AnsweredPollQuestion) other;

			if (getId()!=null)
			{
					if (getId().equals(answer2.getId())) return true;
			}
			else
			{
				if (null==answer2.getId())
				{
					if  (this.getPoll().equals(answer2.getPoll())&&
						(this.getAnswerer().equals(answer2.getAnswerer()))&&
						(this.getTimeStamp().equals(answer2.getTimeStamp())&&
						(this.getAnswer().equals(answer2.getAnswer()))))
						return true;
				}
			}
			return false;
		}

		@Override
		public String toString()
		{
			return "id = "+id+" answerer = "+answerer+" poll = "+poll+" timeStamp = "+timeStamp+" answer = "+answer;
		}
}
