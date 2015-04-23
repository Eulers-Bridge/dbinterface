package com.eulersbridge.iEngage.database.domain;

import org.springframework.data.neo4j.annotation.Query;

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

}
