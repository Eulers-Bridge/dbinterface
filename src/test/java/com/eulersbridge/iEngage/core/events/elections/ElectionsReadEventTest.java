/**
 * 
 */
package com.eulersbridge.iEngage.core.events.elections;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.eulersbridge.iEngage.core.events.elections.ElectionDetails;
import com.eulersbridge.iEngage.core.events.elections.ElectionsReadEvent;

/**
 * @author Greg Newitt
 *
 */
public class ElectionsReadEventTest 
{
    ArrayList<ElectionDetails> elections = null;
    ElectionDetails electionDetails = null;
    ElectionDetails electionDetails1 = null;
    ElectionsReadEvent electionsReadEvent = null;
    String title1="Student Union Election",title2="Union Election";
    Long start=123555l,end=123888l,startVoting=123666l,endVoting=123777l,institutionId=26l;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception 
	{
        elections = new ArrayList<>();
        electionDetails = new ElectionDetails(new Long(1), title1, start, end, startVoting, endVoting, institutionId);
        electionDetails1 = new ElectionDetails(new Long(2), title2, start, end, startVoting, endVoting, institutionId);
        elections.add(electionDetails);
        elections.add(electionDetails1);
        electionsReadEvent = new ElectionsReadEvent(institutionId, elections);
}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.elections.ElectionsReadEvent#ElectionsReadEvent(java.lang.Long, java.lang.Iterable)}.
	 */
	@Test
	public final void testElectionsReadEventLongIterableOfElectionDetails() 
	{
        assertNotNull("electionsReadEvent is null", electionsReadEvent);
        ElectionsReadEvent electionsReadEvent1 = new ElectionsReadEvent(institutionId,elections);
        assertNotNull("electionsReadEvent is null", electionsReadEvent1);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.elections.ElectionsReadEvent#ElectionsReadEvent(java.lang.Iterable)}.
	 */
	@Test
	public final void testElectionsReadEventIterableOfElectionDetails() {
        assertNotNull("electionsReadEvent is null", electionsReadEvent);
        ElectionsReadEvent electionsReadEvent1 = new ElectionsReadEvent(elections);
        assertNotNull("electionsReadEvent is null", electionsReadEvent1);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.elections.ElectionsReadEvent#getElections()}.
	 */
	@Test
	public final void testGetElections() {
        assertEquals("Institutions does not match", elections, electionsReadEvent.getElections());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.elections.ElectionsReadEvent#setElections(java.lang.Iterable)}.
	 */
	@Test
	public final void testSetElections() {
        ArrayList<ElectionDetails> elections1 = new ArrayList<>();
        elections1.add(electionDetails1);
        electionsReadEvent.setElections(elections1);
        assertEquals("Institutions does not match", elections1, electionsReadEvent.getElections());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.elections.ElectionsReadEvent#getElectionId()}.
	 */
	@Test
	public final void testGetInstitutionId() {
        assertEquals("institutionId does not match", institutionId, electionsReadEvent.getInstitutionId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.elections.ElectionsReadEvent#setElectionId(java.lang.Long)}.
	 */
	@Test
	public final void testSetInstitutionId() {
        electionsReadEvent.setInstitutionId(new Long(2));
        assertEquals("institutionId does not match", new Long(2), electionsReadEvent.getInstitutionId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.ReadEvent#isEntityFound()}.
	 */
	@Test
	public final void testIsEntityFound() 
	{
		assertTrue(electionsReadEvent.isEntityFound());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.elections.ElectionsReadEvent#isInstitutionFound()}.
	 */
	@Test
	public final void testIsInstitutionFound() 
	{
		assertTrue(electionsReadEvent.isInstitutionFound());
		ElectionsReadEvent evt=ElectionsReadEvent.institutionNotFound();
		assertFalse(evt.isInstitutionFound());
		assertFalse(evt.isEntityFound());
	}

}
