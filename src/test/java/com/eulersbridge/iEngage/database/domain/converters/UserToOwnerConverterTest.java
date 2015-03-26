/**
 * 
 */
package com.eulersbridge.iEngage.database.domain.converters;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.eulersbridge.iEngage.database.domain.Owner;
import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;

/**
 * @author Greg Newitt
 *
 */
public class UserToOwnerConverterTest
{
	UserToOwnerConverter converter;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		converter=new UserToOwnerConverter();
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.converters.UserToOwnerConverter#convert(com.eulersbridge.iEngage.database.domain.User)}.
	 */
	@Test
	public final void testConvert()
	{
		User source=DatabaseDataFixture.populateUserGnewitt();		
		Owner owner=converter.convert(source);
		assertEquals(owner.getNodeId(),source.getNodeId());
	}

}
