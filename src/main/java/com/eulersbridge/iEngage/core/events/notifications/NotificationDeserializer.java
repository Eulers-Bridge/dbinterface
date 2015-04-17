/**
 * 
 */
package com.eulersbridge.iEngage.core.events.notifications;

import java.io.IOException;
import java.util.Iterator;

import com.eulersbridge.iEngage.core.events.contactRequest.ContactRequestDetails;
import com.eulersbridge.iEngage.database.domain.notifications.NotificationConstants;
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
    
    static String notificationfieldsArray[]={
    								NotificationConstants.NodeId,
    								NotificationConstants.UserId,
    								NotificationConstants.Timestamp,
    						        NotificationConstants.Type,
    						        NotificationConstants.Read,
    						   	 	NotificationConstants.NotificationBody
    							};
    
    public static boolean stringContainsValidNotificationField(String inputString)
    {
        for(int i =0; i < notificationfieldsArray.length; i++)
        {
            if(inputString.equals(notificationfieldsArray[i]))
            {
                return true;
            }
        }
        return false;
    }
    
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
        Iterator<String> fields = node.fieldNames();
        while (fields.hasNext())
        {
        	String field = fields.next();
        	boolean valid=stringContainsValidNotificationField(field);
        	if (LOG.isDebugEnabled()) LOG.debug("field - "+field+" validField - "+valid);
        	if (!valid) throw new JsonMappingException("Invalid field name -"+field);
        	
        }

        Long nodeId=getLong(node.get(NotificationConstants.NodeId));
        Long userId=getLong(node.get(NotificationConstants.UserId));
        Long timestamp = getLong(node.get(NotificationConstants.Timestamp));
        String type = getText(node.get(NotificationConstants.Type));
        Boolean read = getBoolean(node.get(NotificationConstants.Read));
   	 	JsonNode notificationBodyNode = node.get(NotificationConstants.NotificationBody);
   	 	Object notificationBody=null;
        if (NotificationConstants.CONTACT_REQUEST.equals(type)&&(notificationBodyNode!=null))
        {
 
        	Long crUserId=getLong(notificationBodyNode.get(NotificationConstants.UserId));
        	Long crNodeId=getLong(notificationBodyNode.get(NotificationConstants.NodeId));
        	Long requestDate=getLong(notificationBodyNode.get(NotificationConstants.RequestDate));
        	Long responseDate=getLong(notificationBodyNode.get(NotificationConstants.ResponseDate));
        	Boolean accepted=getBoolean(notificationBodyNode.get(NotificationConstants.Accepted));
        	Boolean rejected=getBoolean(notificationBodyNode.get(NotificationConstants.Rejected));
        	JsonNode contactDetails=notificationBodyNode.get("contactDetails");
        	String contactDets=null;
        	if (contactDetails!=null) contactDets=contactDetails.asText();
        	ContactRequestDetails crd=new ContactRequestDetails(crNodeId, contactDets, requestDate, responseDate, accepted, rejected, crUserId);
        	if ((null==crNodeId)&&((null==contactDetails)&&(null==crUserId)))
        		throw new JsonMappingException("notificationBody must be populated with a contactRequest containing nodeId or contactDetails and contactor UserId");
        	else
        		notificationBody=crd;
        }
        else if ((NotificationConstants.CONTACT_ACCEPTED.equals(type))&&(notificationBodyNode!=null))
        {
//        	ContactRequestAcceptedDetails cra= new ContactRequestAcceptedDetails();
        }
        else if ((NotificationConstants.CONTACT_REJECTED.equals(type))&&(notificationBodyNode!=null))
        {
        	
        }
        else if ((NotificationConstants.MESSAGE.equals(type))&&(notificationBodyNode!=null))
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
