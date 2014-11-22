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
public class DetailsTest
{
	Details details;
	Long nodeId;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		nodeId=123456l;
		details=new Details(nodeId);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.Details#Details()}.
	 */
	@Test
	public final void testDetails()
	{
		details=new Details();
		assertNotNull("Not yet implemented",details);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.Details#Details(java.lang.Long)}.
	 */
	@Test
	public final void testDetailsLong()
	{
		assertNotNull("Not yet implemented",details);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.Details#getNodeId()}.
	 */
	@Test
	public final void testGetNodeId()
	{
		assertEquals(nodeId,details.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.Details#setNodeId(java.lang.Long)}.
	 */
	@Test
	public final void testSetNodeId()
	{
		assertEquals(nodeId,details.getNodeId());
		nodeId=54321l;
		assertNotEquals(nodeId,details.getNodeId());
		details.setNodeId(nodeId);
		assertEquals(nodeId,details.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.Details#hashCode()}.
	 */
	@Test
	public final void testHashCode()
	{
		Details details2=new Details(nodeId);
		assertEquals(details2.hashCode(),details2.hashCode());
		assertEquals(details2.hashCode(),details.hashCode());
		details2.setNodeId(null);
		assertNotEquals(details.hashCode(), details2.hashCode());
		assertNotEquals(details2.hashCode(), details.hashCode());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.Details#equals(java.lang.Object)}.
	 */
	@Test
	public final void testEqualsObject()
	{
		Details details2=null;
		assertNotEquals(details2,details);
		assertNotEquals(details,details2);
		String notElection="";
		assertNotEquals(details,notElection);
		details2=new Details(nodeId);
		assertEquals(details2,details2);
		assertEquals(details2,details);
		details2.setNodeId(54l);
		assertNotEquals(details, details2);
		assertNotEquals(details2, details);
		details.setNodeId(null);
		assertNotEquals(details, details2);
		assertNotEquals(details2, details);
		details2.setNodeId(null);
		assertEquals(details, details2);
		assertEquals(details2, details);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.Details#toString()}.
	 */
	@Test
	public final void testToString()
	{
		assertNotNull("Not yet implemented",details.toString());
	}

}
