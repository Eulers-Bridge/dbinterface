/**
 * 
 */
package com.eulersbridge.iEngage.database.domain.converters;

import org.springframework.core.convert.converter.Converter;

import com.eulersbridge.iEngage.database.domain.Owner;
import com.eulersbridge.iEngage.database.domain.Ticket;

/**
 * @author Greg Newitt
 *
 */

public class TicketToOwnerConverter implements Converter<Ticket, Owner>
{

	@Override
	public Owner convert(Ticket source)
	{
		Owner owner=new Owner();
		owner.setNodeId(source.getNodeId());
			
		return owner;
	}

}
