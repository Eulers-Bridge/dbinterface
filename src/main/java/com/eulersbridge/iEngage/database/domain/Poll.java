package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.polls.PollDetails;

import org.neo4j.graphdb.Direction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.annotation.*;

/**
 * @author Yikai Gong
 */

@NodeEntity
public class Poll extends Likeable implements Commentable
{
	@GraphId
	private Long nodeId;
	private String question;
	private String answers;
	private Long start;
	private Long duration;
	@RelatedTo(type = DatabaseDomainConstants.CREATED_BY_LABEL, direction = Direction.BOTH)
	@Fetch
	private Owner creator;
	@RelatedTo(type = DatabaseDomainConstants.HAS_POLL_LABEL, direction = Direction.BOTH)
	@Fetch
	private Owner owner;

    @Query("START n = node({self}) match (a:`User`)-[r:"+ DatabaseDomainConstants.HAS_COMMENT+"]-(n) RETURN count(a) ")
    private Long numberOfComments;

	private static Logger LOG = LoggerFactory.getLogger(Poll.class);

	public Poll()
	{
	}

	/**
	 * @param question
	 * @param answers
	 * @param start
	 * @param duration
	 */
	public Poll(String question, String answers, Long start, Long duration)
	{
		super();
		this.question = question;
		this.answers = answers;
		this.start = start;
		this.duration = duration;
	}

	public PollDetails toPollDetails()
	{
		if (LOG.isTraceEnabled()) LOG.trace("toPollDetails()");
		PollDetails pollDetails = new PollDetails();
		pollDetails.setPollId(this.getNodeId());
		pollDetails.setQuestion(this.getQuestion());
		pollDetails.setAnswers(this.getAnswers());
		pollDetails.setStart(this.getStart());
		pollDetails.setDuration(this.getDuration());
		pollDetails.setOwnerId((owner == null) ? null : owner.getNodeId());
		pollDetails.setCreatorId((creator == null) ? null : creator.getNodeId());
        pollDetails.setNumOfComments(numberOfComments);
		if (LOG.isTraceEnabled()) LOG.trace("pollDetails; " + pollDetails);
		return pollDetails;
	}

	public static Poll fromPollDetails(PollDetails pollDetails)
	{
		if (LOG.isTraceEnabled()) LOG.trace("fromPollDetails()");
		Poll poll = new Poll();
		poll.setNodeId(pollDetails.getPollId());
		poll.setQuestion(pollDetails.getQuestion());
		poll.setAnswers(pollDetails.getAnswers());
		poll.setStart(pollDetails.getStart());
		poll.setDuration(pollDetails.getDuration());
		Owner owner = new Owner(pollDetails.getOwnerId());
		poll.setOwner(owner);
		Owner creator = new Owner(pollDetails.getCreatorId());
		poll.setCreator(creator);
		if (LOG.isTraceEnabled()) LOG.trace("poll " + poll);
		return poll;
	}

	public Long getNodeId()
	{
		return nodeId;
	}

	public void setNodeId(Long pollId)
	{
		this.nodeId = pollId;
	}

    public String getQuestion()
	{
		return question;
	}

	public void setQuestion(String question)
	{
		this.question = question;
	}

	public String getAnswers()
	{
		return answers;
	}

	public void setAnswers(String answers)
	{
		this.answers = answers;
	}

	public Long getStart()
	{
		return start;
	}

	public void setStart(Long start)
	{
		this.start = start;
	}

	public Long getDuration()
	{
		return duration;
	}

	public void setDuration(Long duration)
	{
		this.duration = duration;
	}

    public Long getNumberOfComments() {
        return numberOfComments;
    }

    public void setNumberOfComments(Long numberOfComments) {
        this.numberOfComments = numberOfComments;
    }

    /**
	 * @return the creator
	 */
	public Owner getCreator()
	{
		return creator;
	}

	/**
	 * @param creator
	 *            the creator to set
	 */
	public void setCreator(Owner creator)
	{
		this.creator = creator;
	}

	/**
	 * @return the owner
	 */
	public Owner getOwner()
	{
		return owner;
	}

	/**
	 * @param owner
	 *            the owner to set
	 */
	public void setOwner(Owner owner)
	{
		this.owner = owner;
	}

/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "Poll [nodeId=" + nodeId + ", question=" + question
				+ ", answers=" + answers + ", start=" + start + ", duration="
				+ duration + ", creator=" + creator + ", owner=" + owner + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		if (nodeId != null)
		{
			result = prime * result + nodeId.hashCode();
		}
		else
		{
			result = prime * result
					+ ((answers == null) ? 0 : answers.hashCode());
			result = prime * result
					+ ((creator == null) ? 0 : creator.hashCode());
			result = prime * result + ((owner == null) ? 0 : owner.hashCode());
			result = prime * result
					+ ((duration == null) ? 0 : duration.hashCode());
			result = prime * result
					+ ((question == null) ? 0 : question.hashCode());
			result = prime * result + ((start == null) ? 0 : start.hashCode());
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Poll other = (Poll) obj;
		if (nodeId != null)
		{
			if (nodeId.equals(other.nodeId))
				return true;
			else return false;
		}
		else
		{
			if (other.nodeId != null) return false;
			if (answers == null)
			{
				if (other.answers != null) return false;
			}
			else if (!answers.equals(other.answers)) return false;
			if (owner == null)
			{
				if (other.owner != null) return false;
			}
			else if (!owner.equals(other.owner)) return false;
			if (creator == null)
			{
				if (other.creator != null) return false;
			}
			else if (!creator.equals(other.creator)) return false;
			if (duration == null)
			{
				if (other.duration != null) return false;
			}
			else if (!duration.equals(other.duration)) return false;
			if (question == null)
			{
				if (other.question != null) return false;
			}
			else if (!question.equals(other.question)) return false;
			if (start == null)
			{
				if (other.start != null) return false;
			}
			else if (!start.equals(other.start)) return false;
		}
		return true;
	}

}
