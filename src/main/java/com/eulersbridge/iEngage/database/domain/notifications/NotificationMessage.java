/**
 * 
 */
package com.eulersbridge.iEngage.database.domain.notifications;

import com.eulersbridge.iEngage.core.events.notifications.Message;
import com.eulersbridge.iEngage.core.events.notifications.NotificationDetails;
import com.eulersbridge.iEngage.core.events.notifications.NotificationHelper;
import com.eulersbridge.iEngage.database.domain.User;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Iterator;

/**
 * @author Greg Newitt
 *
 */
public class NotificationMessage extends Notification implements NotificationInterface
{
	String message;

	private static String[] messageFieldsArray={
		NotificationConstants.NodeId,
   	 	NotificationConstants.Timestamp,
        NotificationConstants.UserId,
        NotificationConstants.Text
	};

	public static Message populateFields(JsonNode node)  throws JsonMappingException
	{
        Iterator<String> fields = node.fieldNames();
        NotificationHelper.checkFieldNames(fields,messageFieldsArray);

		Long nodeId=NotificationHelper.getLong(node.get(NotificationConstants.NodeId));
		String text=NotificationHelper.getText(node.get(NotificationConstants.Text));
		Message mes=new Message(nodeId, text);
		if ((null==text))
			throw new JsonMappingException("notificationBody must be populated with a message containing text");
		return mes;
	}

    @Override
	public NotificationDetails toNotificationDetails()
	{
		if (LOG.isDebugEnabled()) LOG.debug("toNotificationDetails()");
		Long userId=null;
		if (user!=null) userId=user.getNodeId();
		
		NotificationDetails dets=new NotificationDetails(nodeId, userId, timestamp, isRead(), type, new Message(userId, message));
		return dets;
	}
	
	public static NotificationMessage fromNotificationDetails(NotificationDetails nDets)
	{
		NotificationMessage notif=new NotificationMessage();
		if (nDets!=null)
		{
			if (NotificationConstants.MESSAGE.equals(nDets.getType()))
			{
				Message message=(Message)nDets.getNotificationBody();
				if (LOG.isDebugEnabled()) LOG.debug("Message - "+message);
				if (message!=null)
					notif.setMessage(message.getText());
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
	 * @return the message
	 */
	public String getMessage()
	{
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message)
	{
		this.message = message;
	}

	
}
