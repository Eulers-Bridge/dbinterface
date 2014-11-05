/**
 * 
 */
package com.eulersbridge.iEngage.core.events;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Greg Newitt
 *
 */
public class ReadAllEventTest
{
	ReadAllEvent testEvt;
	Long institutionId=26l;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		testEvt=new ReadAllEvent(institutionId);
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
	 * Test method for {@link com.eulersbridge.iEngage.core.events.ReadAllEvent#getInstId()}.
	 */
	@Test
	public final void testGetInstId()
	{
		assertEquals(institutionId,testEvt.getInstId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.ReadAllEvent#setInstId(java.lang.Long)}.
	 */
	@Test
	public final void testSetInstId()
	{
		Long instId=34l;
		testEvt.setInstId(instId);
		assertEquals(instId,testEvt.getInstId());
	}

}
