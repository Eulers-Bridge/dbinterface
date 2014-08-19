package com.eulersbridge.iEngage.database.domain;

import java.util.Calendar;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

@RelationshipEntity(type="LIKES")
public class Like 
{
	@GraphId private Long id;
	@StartNode private User liker;
	@EndNode private Likeable liked;
	private Long timeStamp;
	
	public Like()
	{
	}
	
	public Like(User liker, Likeable liked)
	{
		this.liker=liker;
		this.liked=liked;
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
	 * @return the liker
	 */
	public User getLiker() {
		return liker;
	}

	/**
	 * @param liker the liker to set
	 */
	public void setLiker(User liker) {
		this.liker = liker;
	}

	/**
	 * @return the liked
	 */
	public Likeable getLiked() {
		return liked;
	}

	/**
	 * @param likee the likee to set
	 */
	public void setLiked(Likeable liked) {
		this.liked = liked;
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
	
	public boolean equals(Like likes2)
	{
		if (likes2!=null)
		{
			if (getId()!=null)
			{
				if (getId()==likes2.getId())
					return true;
			}
			else
			{
				if ((getLiker().equals(likes2.getLiker()))&&(getLiked().equals(likes2.getLiked())))
					return true;
			}
		}
		return false;
	}
	
	public String toString()
	{
		return "id = "+id+" liker = "+liker+" liked = "+liked+" timeStamp = "+timeStamp;
	}
}
