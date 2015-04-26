package com.eulersbridge.iEngage.database.domain;

import org.springframework.data.neo4j.annotation.Query;

import com.eulersbridge.iEngage.core.events.likes.LikeableDetails;

public class Likeable 
{
    @Query("START n = node({self}) match (n)-[r:"+ DatabaseDomainConstants.LIKES_LABEL+"]-(c) RETURN count(c) ")
    private Long numOfLikes;

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
	public void setNumOfLikes(Long numberOfLikes)
	{
		this.numOfLikes = numberOfLikes;
	}
	
	public LikeableDetails toDetails(LikeableDetails details)
	{
		details.setNumOfLikes(getNumOfLikes());
		return details;
	}
}
