/**
 * 
 */
package com.eulersbridge.iEngage.core.events.elections;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Greg Newitt
 *
 */
public class ReadElectionsEventTest {

    final Long institutionId = new Long(1);
    ReadElectionsEvent readElectionsEvent = null;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception 
	{
		readElectionsEvent = new ReadElectionsEvent(institutionId);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.elections.ReadElectionsEvent#ReadElectionsEvent()}.
	 */
	@Test
	public final void testReadElectionsEvent() 
	{
		ReadElectionsEvent evt=new ReadElectionsEvent();
        assertNotNull("readElectionsEvent is null", evt);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.elections.ReadElectionsEvent#ReadElectionsEvent(java.lang.Long)}.
	 */
	@Test
	public final void testReadElectionsEventLong() 
	{
        assertNotNull("readElectionsEvent is null", readElectionsEvent);
        assertEquals(institutionId, readElectionsEvent.getParentId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.elections.ReadElectionsEvent#getInstitutionId()}.
	 */
	@Test
	public final void testGetInstitutionId() {
        assertEquals("institutionId does not match", institutionId, readElectionsEvent.getParentId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.elections.ReadElectionsEvent#setInstitutionId(java.lang.Long)}.
	 */
	@Test
	public final void testSetInstitutionId() {
        Long insitutionId2 = new Long(2);
        readElectionsEvent.setParentId(insitutionId2);
        assertEquals("countryId does not match", insitutionId2, readElectionsEvent.getParentId());
	}

}
