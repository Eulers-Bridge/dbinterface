/**
 * 
 */
package com.eulersbridge.iEngage.database.domain.converters;

import org.springframework.core.convert.converter.Converter;

import com.eulersbridge.iEngage.database.domain.Event;
import com.eulersbridge.iEngage.database.domain.Owner;

/**
 * @author Greg Newitt
 *
 */

public class EventToOwnerConverter implements Converter<Event, Owner>
{

	@Override
	public Owner convert(Event source)
	{
		Owner owner=new Owner();
		owner.setNodeId(source.getEventId());
			
		return owner;
	}

}
