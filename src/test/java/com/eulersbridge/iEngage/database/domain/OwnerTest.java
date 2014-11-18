/**
 * 
 */
package com.eulersbridge.iEngage.database.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Greg Newitt
 *
 */
public class OwnerTest
{
	Owner owner;
	Owner owner1;
	Owner owner2;
	Owner owner3;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		owner=new Owner();
		owner1=new Owner(1l);
		owner2=new Owner(1l);
		owner3=new Owner(2l);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Owner#Owner()}.
	 */
	@Test
	public final void testOwner()
	{
		assertNotNull("Not yet implemented",owner);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Owner#Owner(java.lang.Long)}.
	 */
	@Test
	public final void testOwnerLong()
	{
		assertNotNull("Not yet implemented",owner1);
		assertNotNull("Not yet implemented",owner2);
		assertNotNull("Not yet implemented",owner3);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Owner#getNodeId()}.
	 */
	@Test
	public final void testGetNodeId()
	{
		assertEquals(owner1.getNodeId(),owner2.getNodeId());
		assertNotEquals(owner1.getNodeId(),owner3.getNodeId());
		assertNotEquals(owner2.getNodeId(),owner3.getNodeId());
		
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Owner#setNodeId(java.lang.Long)}.
	 */
	@Test
	public final void testSetNodeId()
	{
		Long nodeId=2l;
		assertNotEquals(owner.getNodeId(),owner1.getNodeId());
		assertNotEquals(owner.getNodeId(),owner3.getNodeId());
		owner.setNodeId(nodeId);
		assertEquals(owner.getNodeId(),owner3.getNodeId());
		assertNotEquals(owner.getNodeId(),owner1.getNodeId());
		assertNotEquals(owner.getNodeId(),owner2.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Owner#equals(java.lang.Object)}.
	 */
	@Test
	public final void testEqualsObject()
	{
		assertNotEquals(owner,owner1);
		assertNotEquals(owner,owner3);
		assertNotEquals(owner1,owner);
		assertNotEquals(owner3,owner);
		assertEquals(owner1,owner2);
		assertNotEquals(owner1,owner3);
		assertNotEquals(owner3,owner1);
		assertEquals(owner,owner);
		assertEquals(owner1,owner1);
		assertNotEquals(null,owner1);
		assertNotEquals(owner1,null);
		assertNotEquals(owner1,new String());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Owner#hashCode()}.
	 */
	@Test
	public final void testHashCode()
	{
		assertNotEquals(owner.hashCode(),owner1.hashCode());
		assertNotEquals(owner.hashCode(),owner3.hashCode());
		assertNotEquals(owner1.hashCode(),owner.hashCode());
		assertNotEquals(owner3.hashCode(),owner.hashCode());
		assertEquals(owner1.hashCode(),owner2.hashCode());
		assertNotEquals(owner1.hashCode(),owner3.hashCode());
		assertNotEquals(owner3.hashCode(),owner1.hashCode());
		assertEquals(owner.hashCode(),owner.hashCode());
		assertEquals(owner1.hashCode(),owner1.hashCode());
		assertNotEquals(null,owner1.hashCode());
		assertNotEquals(owner1.hashCode(),null);
		assertNotEquals(owner1.hashCode(),new String().hashCode());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Owner#toString()}.
	 */
	@Test
	public final void testToString()
	{
		assertNotNull("Not yet implemented",owner.toString());
		assertNotNull("Not yet implemented",owner1.toString());
		assertNotNull("Not yet implemented",owner2.toString());
		assertNotNull("Not yet implemented",owner3.toString());
	}

}
