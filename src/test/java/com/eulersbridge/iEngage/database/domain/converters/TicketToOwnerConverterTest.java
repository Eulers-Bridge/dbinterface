/**
 * 
 */
package com.eulersbridge.iEngage.database.domain.converters;

import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import com.eulersbridge.iEngage.database.domain.Owner;
import com.eulersbridge.iEngage.database.domain.Ticket;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Greg Newitt
 *
 */
public class TicketToOwnerConverterTest
{
	TicketToOwnerConverter converter;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		converter=new TicketToOwnerConverter();
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.converters.TicketToOwnerConverter#convert(com.eulersbridge.iEngage.database.domain.Ticket)}.
	 */
	@Test
	public final void testConvert()
	{
		Ticket source=DatabaseDataFixture.populateTicket1();		
		Owner owner=converter.convert(source);
		assertEquals(owner.getNodeId(),source.getNodeId());
	}

}
