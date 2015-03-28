/**
 * 
 */
package com.eulersbridge.iEngage.core.events.notifications;

import java.io.IOException;

import com.eulersbridge.iEngage.core.events.contactRequest.ContactRequestDetails;
import com.eulersbridge.iEngage.rest.domain.Notification;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Greg Newitt
 *
 */
public class NotificationDeserializer extends JsonDeserializer<Notification>
{
    static Logger LOG = LoggerFactory.getLogger(NotificationDeserializer.class);
    
    private Long getLong(JsonNode node)
    {
    	Long value=null;
        if ((node!=null)&&(node.isIntegralNumber()))
        	value = node.numberValue().longValue();
        return value;
    }
    private String getText(JsonNode node)
    {
    	String value=null;
        if ((node!=null)&&(node.isTextual()))
        	value = node.asText();
        return value;
    }
    private Boolean getBoolean(JsonNode node)
    {
    	Boolean value=null;
        if ((node!=null)&&(node.isBoolean()))
        	value = node.asBoolean();
        return value;
    }
	@Override
	public Notification deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException
	{
        JsonNode node = jp.getCodec().readTree(jp);

        Long nodeId=getLong(node.get("nodeId"));
        Long userId=getLong(node.get("userId"));
        Long timestamp = getLong(node.get("timestamp"));
        String type = getText(node.get("type"));
        Boolean read = getBoolean(node.get("read"));
   	 	JsonNode notificationBodyNode = node.get("notificationBody");
   	 	Object notificationBody=null;
        if ("contactRequest".equals(type)&&(notificationBodyNode!=null))
        {
 
        	Long crUserId=getLong(notificationBodyNode.get("userId"));
        	Long crNodeId=getLong(notificationBodyNode.get("nodeId"));
        	Long requestDate=getLong(notificationBodyNode.get("requestDate"));
        	Long responseDate=getLong(notificationBodyNode.get("responseDate"));
        	Boolean accepted=getBoolean(notificationBodyNode.get("accepted"));
        	Boolean rejected=getBoolean(notificationBodyNode.get("rejected"));
        	JsonNode contactDetails=notificationBodyNode.get("contactDetails");
        	String contactDets=null;
        	if (contactDetails!=null) contactDets=contactDetails.asText();
        	ContactRequestDetails crd=new ContactRequestDetails(crNodeId, contactDets, requestDate, responseDate, accepted, rejected, crUserId);
        	if ((null==crNodeId)&&((null==contactDetails)&&(null==crUserId)))
        		throw new JsonMappingException("notificationBody must be populated with a contactRequest containing nodeId or contactDetails and contactor UserId");
        	else
        		notificationBody=crd;
        }
        else if ("message".equals(type)&&(notificationBodyNode!=null))
        {
        	
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
        Notification notif=new Notification();
        notif.setNodeId(nodeId);
        notif.setRead(read);
        notif.setTimestamp(timestamp);
        notif.setType(type);
        notif.setNotificationBody(notificationBody);
        notif.setUserId(userId);
        return notif;
    }

}
