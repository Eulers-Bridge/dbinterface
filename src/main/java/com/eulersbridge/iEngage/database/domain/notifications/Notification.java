/**
 * 
 */
package com.eulersbridge.iEngage.database.domain.notifications;

import java.util.HashMap;

import org.neo4j.graphdb.Direction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.eulersbridge.iEngage.core.events.notifications.NotificationDetails;
import com.eulersbridge.iEngage.database.domain.DatabaseDomainConstants;
import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.repository.UserRepository;

/**
 * @author Greg Newitt
 *
 */
@NodeEntity
public class Notification
{
    @GraphId
    Long nodeId;
	Boolean read=false;
	Long timestamp;
	String type;
	@RelatedTo(type = DatabaseDomainConstants.HAS_NOTIFICATION_LABEL, direction=Direction.OUTGOING)
	User user;
	
    static Logger LOG = LoggerFactory.getLogger(Notification.class);

	public Boolean setupForSave(HashMap<String,GraphRepository<?>> repos)
	{
    	if (LOG.isDebugEnabled()) LOG.debug("setupForSave()");
		Boolean response=false;
    	UserRepository userRepo=(UserRepository)repos.get(UserRepository.class.getSimpleName());
    	if (userRepo!=null)
		{
			if ((getUser()!=null)&&(getUser().getNodeId()!=null))
			{	
				User result=userRepo.findOne(getUser().getNodeId());
				if (result!=null)
				{
					user=result;
					response=true;
				}
			}
		}
		return response;
	}
	
	public NotificationDetails toNotificationDetails()
	{
		if (LOG.isDebugEnabled()) LOG.debug("toNotificationDetails()");
		
		Long userId=null;
		if (user!=null) userId=user.getNodeId();
		
		NotificationDetails dets=new NotificationDetails(nodeId, userId, timestamp, read, type, null);
		return dets;
	}
	
	public static Notification fromNotificationDetails(NotificationDetails nDets)
	{
		Notification notif=new NotificationContactRequest();
		if (nDets!=null)
		{
			if ("contactRequest".equals(nDets.getType()))
			{
				NotificationContactRequest notifCR=NotificationContactRequest.fromNotificationDetails(nDets);
				notif=notifCR;
			}
			else
			{
//			notif.setNotificationBody(nDets.getNotificationBody());
			}
			notif.setType(nDets.getType());
			notif.setNodeId(nDets.getNodeId());
			User user=new User(nDets.getUserId());
			notif.setUser(user);
			notif.setRead(nDets.getRead());
			notif.setTimestamp(nDets.getTimestamp());
		}
		return notif;
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
	 * @return the user
	 */
	public User getUser()
	{
		return user;
	}



	/**
	 * @param user the user to set
	 */
	public void setUser(User user)
	{
		this.user = user;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "Notification [nodeId=" + nodeId + ", read=" + read
				+ ", timestamp=" + timestamp + ", type=" + type + ", user="
				+ user + "]";
	}

}
