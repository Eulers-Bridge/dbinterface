/**
 * 
 */
package com.eulersbridge.iEngage.core.events.badge;

import com.eulersbridge.iEngage.core.events.Details;

/**
 * @author Greg Newitt
 *
 */
public class BadgeCompleteDetails extends Details
{
	private Long userId;
	private Long badgeId;
	private Long date;
	/**
	 * 
	 */
	public BadgeCompleteDetails()
	{
		super();
	}
	/**
	 * @param userId
	 * @param badgeId
	 * @param date
	 */
	public BadgeCompleteDetails(Long nodeId, Long userId, Long taskId, Long date)
	{
		super(nodeId);
		this.userId = userId;
		this.badgeId = taskId;
		this.date = date;
	}
	/**
	 * @return the userId
	 */
	public Long getUserId()
	{
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId)
	{
		this.userId = userId;
	}
	/**
	 * @return the badgeId
	 */
	public Long getBadgeId()
	{
		return badgeId;
	}
	/**
	 * @param badgeId the badgeId to set
	 */
	public void setBadgeId(Long badgeId)
	{
		this.badgeId = badgeId;
	}
	/**
	 * @return the date
	 */
	public Long getDate()
	{
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(Long date)
	{
		this.date = date;
	}

}
