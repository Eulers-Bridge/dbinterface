/**
 * 
 */
package com.eulersbridge.iEngage.database.domain.converters;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.eulersbridge.iEngage.database.domain.Event;
import com.eulersbridge.iEngage.database.domain.Owner;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;

/**
 * @author Greg Newitt
 *
 */
public class EventToOwnerConverterTest
{
	EventToOwnerConverter converter;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		converter=new EventToOwnerConverter();
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.converters.EventToOwnerConverter#convert(com.eulersbridge.iEngage.database.domain.Event)}.
	 */
	@Test
	public final void testConvert()
	{
		Event source=DatabaseDataFixture.populateEvent1();		
		Owner owner=converter.convert(source);
		assertEquals(owner.getNodeId(),source.getEventId());
	}

}

