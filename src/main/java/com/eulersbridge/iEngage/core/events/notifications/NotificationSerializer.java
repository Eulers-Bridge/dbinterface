/**
 * 
 */
package com.eulersbridge.iEngage.core.events.notifications;

import java.io.IOException;

import com.eulersbridge.iEngage.rest.domain.Notification;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * @author Greg Newitt
 *
 */
public class NotificationSerializer extends JsonSerializer<Notification>
{

	/* (non-Javadoc)
	 * @see com.fasterxml.jackson.databind.JsonSerializer#serialize(java.lang.Object, com.fasterxml.jackson.core.JsonGenerator, com.fasterxml.jackson.databind.SerializerProvider)
	 */
	@Override
	public void serialize(Notification value, JsonGenerator jgen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException
	{
        jgen.writeStartObject();
        jgen.writeBooleanField("read", value.isRead());
        jgen.writeEndObject();
	}
}
