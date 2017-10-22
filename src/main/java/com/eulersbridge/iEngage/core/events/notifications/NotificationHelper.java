/**
 * 
 */
package com.eulersbridge.iEngage.core.events.notifications;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

/**
 * @author Greg Newitt
 *
 */
public class NotificationHelper
{
    static Logger LOG = LoggerFactory.getLogger(NotificationHelper.class);

    static public Long getLong(JsonNode node)
    {
    	Long value=null;
        if ((node!=null)&&(node.isIntegralNumber()))
        	value = node.numberValue().longValue();
        return value;
    }
    static public String getText(JsonNode node)
    {
    	String value=null;
        if ((node!=null)&&(node.isTextual()))
        	value = node.asText();
        return value;
    }
    static public Boolean getBoolean(JsonNode node)
    {
    	Boolean value=null;
        if ((node!=null)&&(node.isBoolean()))
        	value = node.asBoolean();
        return value;
    }

    public static void checkFieldNames(Iterator<String> fields,String [] notificationfieldsArray) throws JsonMappingException
    {
	    while (fields.hasNext())
	    {
	    	String field = fields.next();
	    	boolean valid=NotificationHelper.stringContainsValidNotificationField(field,notificationfieldsArray);
	    	if (LOG.isDebugEnabled()) LOG.debug("field - "+field+" validField - "+valid);
	    	if (!valid) throw new JsonMappingException("Invalid field name -"+field);
	    }
    }

    public static boolean stringContainsValidNotificationField(String inputString, String [] notificationfieldsArray)
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

}
