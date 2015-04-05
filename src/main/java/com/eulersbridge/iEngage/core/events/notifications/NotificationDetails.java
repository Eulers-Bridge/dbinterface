/**
 * 
 */
package com.eulersbridge.iEngage.core.events.notifications;

import com.eulersbridge.iEngage.core.events.Details;

/**
 * @author Greg Newitt
 *
 */
public class NotificationDetails extends Details
{
	Boolean read=false;
	Long userId;
	Long timestamp;
	String type;
	Object notificationBody;

	/**
	 * @param read
	 * @param nodeid
	 * @param userId
	 * @param timestamp
	 * @param type
	 * @param notificationBody
	 */
	public NotificationDetails(Long nodeid, Long userId, Long timestamp, Boolean read,
			String type, Object notificationBody)
	{
		super(nodeid);
		this.userId = userId;
		this.read = read;
		this.timestamp = timestamp;
		this.type = type;
		this.notificationBody = notificationBody;
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
	 * @return the read
	 */
	public Boolean getRead()
	{
		return read;
	}
	/**
	 * @param read the read to set
	 */
	public void setRead(Boolean read)
	{
		this.read = read;
	}

	/**
	 * @return the timestamp
	 */
	public Long getTimestamp()
	{
		return timestamp;
	}
	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Long timestamp)
	{
		this.timestamp = timestamp;
	}
	/**
	 * @return the type
	 */
	public String getType()
	{
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type)
	{
		this.type = type;
	}
	/**
	 * @return the notificationBody
	 */
	public Object getNotificationBody()
	{
		return notificationBody;
	}
	/**
	 * @param notificationBody the notificationBody to set
	 */
	public void setNotificationBody(Object notificationBody)
	{
		this.notificationBody = notificationBody;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "NotificationDetails [read=" + read + ", userId=" + userId
				+ ", timestamp=" + timestamp + ", type=" + type
				+ ", notificationBody=" + notificationBody + "]";
	}
	
}
