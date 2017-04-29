/**
 * 
 */
package com.eulersbridge.iEngage.database.domain.converters;

import com.eulersbridge.iEngage.database.domain.Event;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import com.eulersbridge.iEngage.database.domain.Owner;
import org.junit.Before;
import org.junit.Test;

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
		assert(owner.getNodeId().equals(source.getNodeId()));
	}

}

