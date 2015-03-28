/**
 * 
 */
package com.eulersbridge.iEngage.rest.domain;

import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import com.eulersbridge.iEngage.core.events.Details;
import com.eulersbridge.iEngage.core.events.notifications.NotificationDeserializer;
import com.eulersbridge.iEngage.core.events.notifications.NotificationDetails;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @author Greg Newitt
 *
 */
@JsonDeserialize(using=NotificationDeserializer.class)
@JsonPropertyOrder({"nodeId","userId","timestamp","read","type","notificationBody"})
public class Notification extends ResourceSupport
{
	Long nodeId;
	Long timestamp;
	String type;
	Long userId;
	Object notificationBody;
	Boolean read=false;

	public NotificationDetails toNotificationDetails()
	{
		NotificationDetails dets=new NotificationDetails(nodeId, userId, timestamp, read, type, notificationBody);
		return dets;
	}

	public static Notification fromNotificationDetails(
			NotificationDetails details)
	{
		Notification notification=null;
		if (details!=null)
		{
			notification=new Notification();
			notification.setNodeId(details.getNodeId());
			notification.setUserId(details.getUserId());
			notification.setTimestamp(details.getTimestamp());
			notification.setRead(details.getRead());
			notification.setType(details.getType());
			notification.setNotificationBody(details.getNotificationBody());
		}
		return notification;
	}

	/**
	 * @return the read
	 */
	public Boolean isRead()
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
		return "Notification [read=" + read + ", nodeId=" + nodeId
				+ ", timestamp=" + timestamp + ", type=" + type
				+ ", notificationBody=" + notificationBody + "]";
	}

	public static Iterator<Notification> toNotificationsIterator(
			Iterator<? extends Details> iterator)
	{
		if (null==iterator) return null;
		ArrayList <Notification> notifications=new ArrayList<Notification>();
		while(iterator.hasNext())
		{
			NotificationDetails dets=(NotificationDetails)iterator.next();
			Notification thisNotification=Notification.fromNotificationDetails(dets);
			Link self = thisNotification.getLink("self");
			thisNotification.removeLinks();
			if (self!=null)
				thisNotification.add(self);
			notifications.add(thisNotification);		
		}
		return notifications.iterator();
	}
}
