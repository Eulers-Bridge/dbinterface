/**
 * 
 */
package com.eulersbridge.iEngage.core.events.polls;


/**
 * @author Greg Newitt
 *
 */
public class PollAnswerDetails
{
	Long nodeId;
	Long answererId;
	Long pollId;
	Integer answerIndex;
	Long timeStamp;

	/**
	 * @param nodeId
	 * @param answererId
	 * @param pollId
	 * @param answerIndex
	 * @param timeStamp
	 */
	public PollAnswerDetails(Long nodeId, Long answererId, Long pollId,
			Integer answerIndex, Long timeStamp)
	{
		super();
		this.nodeId = nodeId;
		this.answererId = answererId;
		this.pollId = pollId;
		this.answerIndex = answerIndex;
		this.timeStamp = timeStamp;
	}

	/**
	 * @return the nodeId
	 */
	public Long getNodeId()
	{
		return nodeId;
	}
	/**
	 * @param nodeId the nodeId to set
	 */
	public void setNodeId(Long nodeId)
	{
		this.nodeId = nodeId;
	}
	/**
	 * @return the answererId
	 */
	public Long getAnswererId()
	{
		return answererId;
	}
	/**
	 * @param answererId the answererId to set
	 */
	public void setAnswererId(Long answererId)
	{
		this.answererId = answererId;
	}
	/**
	 * @return the pollId
	 */
	public Long getPollId()
	{
		return pollId;
	}
	/**
	 * @param pollId the pollId to set
	 */
	public void setPollId(Long pollId)
	{
		this.pollId = pollId;
	}
	/**
	 * @return the answerIndex
	 */
	public Integer getAnswerIndex()
	{
		return answerIndex;
	}
	/**
	 * @param answerIndex the answerIndex to set
	 */
	public void setAnswerIndex(Integer answerIndex)
	{
		this.answerIndex = answerIndex;
	}
	/**
	 * @return the timeStamp
	 */
	public Long getTimeStamp()
	{
		return timeStamp;
	}
	/**
	 * @param timeStamp the timeStamp to set
	 */
	public void setTimeStamp(Long timeStamp)
	{
		this.timeStamp = timeStamp;
	}

}
