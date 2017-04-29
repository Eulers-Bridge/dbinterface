/**
 * 
 */
package com.eulersbridge.iEngage.core.events;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Greg Newitt
 *
 */
public class ReadAllEventTest
{
	ReadAllEvent testEvt;
	Long parentId=26l;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		testEvt=new ReadAllEvent(parentId);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.ReadAllEvent#ReadAllEvent(java.lang.Long)}.
	 */
	@Test
	public final void testReadAllEvent()
	{
		assertNotNull("Not yet implemented",testEvt);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.ReadAllEvent#getParentId()}.
	 */
	@Test
	public final void testGetParentId()
	{
		assertEquals(parentId,testEvt.getParentId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.ReadAllEvent#setParentId(java.lang.Long)}.
	 */
	@Test
	public final void testSetParentId()
	{
		Long parentId=34l;
		testEvt.setParentId(parentId);
		assertEquals(parentId,testEvt.getParentId());
	}

}
