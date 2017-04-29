/**
 * 
 */
package com.eulersbridge.iEngage.core.events.notifications;

import com.eulersbridge.iEngage.database.domain.notifications.*;
import com.eulersbridge.iEngage.rest.domain.Notification;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author Greg Newitt
 *
 */
public class NotificationDeserializer extends JsonDeserializer<Notification>
{
    static Logger LOG = LoggerFactory.getLogger(NotificationDeserializer.class);
    
    
	@Override
	public Notification deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException
	{
        JsonNode node = jp.getCodec().readTree(jp);
        
        Notification notif=Notification.populateFields(node);
        String type = notif.getType();

   	 	JsonNode notificationBodyNode = node.get(NotificationConstants.NotificationBody);
   	 	Object notificationBody=null;
        if (NotificationConstants.CONTACT_REQUEST.equals(type)&&(notificationBodyNode!=null))
        {
        	notificationBody=NotificationContactRequest.populateFields(notificationBodyNode);
        }
        else if ((NotificationConstants.CONTACT_ACCEPTED.equals(type))&&(notificationBodyNode!=null))
        {
        	notificationBody=NotificationContactAccepted.populateFields(notificationBodyNode);
        }
        else if ((NotificationConstants.CONTACT_REJECTED.equals(type))&&(notificationBodyNode!=null))
        {
        	notificationBody=NotificationContactRejected.populateFields(notificationBodyNode);
        }
        else if ((NotificationConstants.MESSAGE.equals(type))&&(notificationBodyNode!=null))
        {
        	notificationBody=NotificationMessage.populateFields(notificationBodyNode);
        }
        else
        {
        	JsonProcessingException ex;
        	if (null==notificationBodyNode)
        	{
				ex = new JsonMappingException("notificationBody cannot be null");
        	}
        	else
        	{
        		ex = new JsonMappingException("Invalid content type.");
        	}
        		throw ex; 
        }
        notif.setNotificationBody(notificationBody);
        return notif;
    }

}
