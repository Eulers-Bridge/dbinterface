/**
 * 
 */
package com.eulersbridge.iEngage.core.events.likes;

import com.eulersbridge.iEngage.core.events.Details;

/**
 * @author Greg Newitt
 *
 */
public class LikeableDetails extends Details
{
	private Long numOfLikes;

	public LikeableDetails(Long nodeId)
	{
		super(nodeId);
	}
	
	/**
	 * @return the numOfLikes
	 */
	public Long getNumOfLikes()
	{
		return numOfLikes;
	}

	/**
	 * @param numOfLikes the numOfLikes to set
	 */
	public void setNumOfLikes(Long numOfLikes)
	{
		this.numOfLikes = numOfLikes;
	}
	
}
