/**
 * 
 */
package com.eulersbridge.iEngage.rest.domain;

import com.eulersbridge.iEngage.core.events.Details;
import com.eulersbridge.iEngage.core.events.notifications.NotificationDeserializer;
import com.eulersbridge.iEngage.core.events.notifications.NotificationDetails;
import com.eulersbridge.iEngage.core.events.notifications.NotificationHelper;
import com.eulersbridge.iEngage.database.domain.notifications.NotificationConstants;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

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

    static String notificationfieldsArray[]={
		NotificationConstants.NodeId,
		NotificationConstants.Timestamp,
        NotificationConstants.Type,
		NotificationConstants.UserId,
   	 	NotificationConstants.NotificationBody,
        NotificationConstants.Read
	};

    static Logger LOG = LoggerFactory.getLogger(Notification.class);

	/**
	 * 
	 */
	public Notification()
	{
		super();
	}

	public Notification(Long userId,String type,Object notificationBody)
	{
		setNotificationBody(notificationBody);
		setRead(false);
		setType(type);
		setTimestamp(Calendar.getInstance().getTimeInMillis());
		setUserId(userId);
		
		if (LOG.isDebugEnabled())
		{
			LOG.debug("Notification - "+this);
			LOG.debug("Notification details - "+toNotificationDetails());
		}
	}
    
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

    static public Notification populateFields(JsonNode node) throws JsonMappingException
    {
        Iterator<String> fields = node.fieldNames();
        NotificationHelper.checkFieldNames(fields,notificationfieldsArray);

        Long nodeId=NotificationHelper.getLong(node.get(NotificationConstants.NodeId));
        Long userId=NotificationHelper.getLong(node.get(NotificationConstants.UserId));
        Long timestamp = NotificationHelper.getLong(node.get(NotificationConstants.Timestamp));
        String type = NotificationHelper.getText(node.get(NotificationConstants.Type));
        Boolean read = NotificationHelper.getBoolean(node.get(NotificationConstants.Read));
   	 	Object notificationBody=null;

   	 	Notification notif=new Notification();
        notif.setNodeId(nodeId);
        notif.setRead(read);
        notif.setTimestamp(timestamp);
        notif.setType(type);
        notif.setNotificationBody(notificationBody);
        notif.setUserId(userId);
        return notif;

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
