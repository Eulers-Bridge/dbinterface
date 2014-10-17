/**
 * 
 */
package com.eulersbridge.iEngage.database.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.eulersbridge.iEngage.core.events.elections.ElectionDetails;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;

/**
 * @author Greg Newitt
 *
 */
public class ElectionTest {

	private Election election;
	private ElectionDetails elecDets;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception 
	{
		election=DatabaseDataFixture.populateElection1();
		elecDets=election.toElectionDetails();
}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Election#Election()}.
	 */
	@Test
	public final void testElection() 
	{
		Election electionTest=new Election();
		assertEquals("electionTest not of Election class",electionTest.getClass(),Election.class);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Election#Election(java.lang.Long, java.lang.String, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long)}.
	 */
	@Test
	public final void testElectionLongStringLongLongLongLong() 
	{
		Election electionTest=new Election(election.getNodeId(),election.getTitle(),election.getStart(),election.getEnd(),election.getVotingStart(),election.getVotingEnd(),election.getInstitution());
		assertEquals("electionTest not of Election class",electionTest.getClass(),Election.class);
		assertEquals("",election.getNodeId(),electionTest.getNodeId());
		assertEquals("",election.getTitle(),electionTest.getTitle());
		assertEquals("",election.getStart(),electionTest.getStart());
		assertEquals("",election.getEnd(),electionTest.getEnd());
		assertEquals("",election.getVotingStart(),electionTest.getVotingStart());
		assertEquals("",election.getVotingEnd(),electionTest.getVotingEnd());
		assertEquals("",election.getInstitution(),electionTest.getInstitution());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Election#getStart()}.
	 */
	@Test
	public final void testGetStart() 
	{
		Election electionTest=new Election(election.getNodeId(),election.getTitle(),election.getStart(),election.getEnd(),election.getVotingStart(),election.getVotingEnd(),election.getInstitution());
		assertEquals("",election.getStart(),electionTest.getStart());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Election#getEnd()}.
	 */
	@Test
	public final void testGetEnd() {
		Election electionTest=new Election(election.getNodeId(),election.getTitle(),election.getStart(),election.getEnd(),election.getVotingStart(),election.getVotingEnd(),election.getInstitution());
		assertEquals("",election.getEnd(),electionTest.getEnd());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Election#getVotingStart()}.
	 */
	@Test
	public final void testGetVotingStart() {
		Election electionTest=new Election(election.getNodeId(),election.getTitle(),election.getStart(),election.getEnd(),election.getVotingStart(),election.getVotingEnd(),election.getInstitution());
		assertEquals("",election.getVotingStart(),electionTest.getVotingStart());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Election#getTitle()}.
	 */
	@Test
	public final void testGetTitle() {
		Election electionTest=new Election(election.getNodeId(),election.getTitle(),election.getStart(),election.getEnd(),election.getVotingStart(),election.getVotingEnd(),election.getInstitution());
		assertEquals("",election.getTitle(),electionTest.getTitle());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Election#getVotingEnd()}.
	 */
	@Test
	public final void testGetVotingEnd() {
		Election electionTest=new Election(election.getNodeId(),election.getTitle(),election.getStart(),election.getEnd(),election.getVotingStart(),election.getVotingEnd(),election.getInstitution());
		assertEquals("",election.getVotingEnd(),electionTest.getVotingEnd());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Election#getNodeId()}.
	 */
	@Test
	public final void testGetNodeId() 
	{
		Election electionTest=new Election(election.getNodeId(),election.getTitle(),election.getStart(),election.getEnd(),election.getVotingStart(),election.getVotingEnd(),election.getInstitution());
		assertEquals("",election.getNodeId(),electionTest.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Election#setNodeId(java.lang.Long)}.
	 */
	@Test
	public final void testSetNodeId() 
	{
		Long nodeId=494l;
		Election electionTest=new Election(election.getNodeId(),election.getTitle(),election.getStart(),election.getEnd(),election.getVotingStart(),election.getVotingEnd(),election.getInstitution());
		assertEquals("",election.getNodeId(),electionTest.getNodeId());
		electionTest.setNodeId(nodeId);
		assertEquals("",nodeId,electionTest.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Election#setStart(java.lang.Long)}.
	 */
	@Test
	public final void testSetStart() 
	{
		Long start=494l;
		Election electionTest=new Election(election.getNodeId(),election.getTitle(),election.getStart(),election.getEnd(),election.getVotingStart(),election.getVotingEnd(),election.getInstitution());
		assertEquals("",election.getStart(),electionTest.getStart());
		electionTest.setStart(start);
		assertEquals("",start,electionTest.getStart());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Election#setEnd(java.lang.Long)}.
	 */
	@Test
	public final void testSetEnd() 
	{
		Long end=494l;
		Election electionTest=new Election(election.getNodeId(),election.getTitle(),election.getStart(),election.getEnd(),election.getVotingStart(),election.getVotingEnd(),election.getInstitution());
		assertEquals("",election.getEnd(),electionTest.getEnd());
		electionTest.setEnd(end);
		assertEquals("",end,electionTest.getEnd());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Election#setVotingStart(java.lang.Long)}.
	 */
	@Test
	public final void testSetVotingStart() {
		Long start=494l;
		Election electionTest=new Election(election.getNodeId(),election.getTitle(),election.getStart(),election.getEnd(),election.getVotingStart(),election.getVotingEnd(),election.getInstitution());
		assertEquals("",election.getVotingStart(),electionTest.getVotingStart());
		electionTest.setVotingStart(start);
		assertEquals("",start,electionTest.getVotingStart());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Election#setVotingEnd(java.lang.Long)}.
	 */
	@Test
	public final void testSetVotingEnd() {
		Long end=494l;
		Election electionTest=new Election(election.getNodeId(),election.getTitle(),election.getStart(),election.getEnd(),election.getVotingStart(),election.getVotingEnd(),election.getInstitution());
		assertEquals("",election.getVotingEnd(),electionTest.getVotingEnd());
		electionTest.setVotingEnd(end);
		assertEquals("",end,electionTest.getVotingEnd());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Election#setTitle(java.lang.String)}.
	 */
	@Test
	public final void testSetTitle() 
	{
		String title="Whatever";
		Election electionTest=new Election(election.getNodeId(),election.getTitle(),election.getStart(),election.getEnd(),election.getVotingStart(),election.getVotingEnd(),election.getInstitution());
		assertEquals("",election.getTitle(),electionTest.getTitle());
		electionTest.setTitle(title);
		assertEquals("",title,electionTest.getTitle());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Election#getInstitution()}.
	 */
	@Test
	public final void testGetInstitution() 
	{
		Election electionTest=new Election(election.getNodeId(),election.getTitle(),election.getStart(),election.getEnd(),election.getVotingStart(),election.getVotingEnd(),election.getInstitution());
		assertEquals("",election.getInstitution(),electionTest.getInstitution());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Election#setInstitution(com.eulersbridge.iEngage.database.domain.Institution)}.
	 */
	@Test
	public final void testSetInstitution() 
	{
		Institution inst=DatabaseDataFixture.populateInstMonashUni();
		Election electionTest=new Election(election.getNodeId(),election.getTitle(),election.getStart(),election.getEnd(),election.getVotingStart(),election.getVotingEnd(),election.getInstitution());
		assertEquals("",election.getInstitution(),electionTest.getInstitution());
		electionTest.setInstitution(inst);
		assertEquals("",inst,electionTest.getInstitution());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Election#toString()}.
	 */
	@Test
	public final void testToString() 
	{
		assertNotNull("Not yet implemented",election.toString());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Election#toElectionDetails()}.
	 */
	@Test
	public final void testToElectionDetails() 
	{
		ElectionDetails dets=election.toElectionDetails();
		assertEquals("electionDetails not of ElectionDetails class",dets.getClass(),ElectionDetails.class);
		assertEquals("",election.getNodeId(),dets.getElectionId());
		assertEquals("",election.getTitle(),dets.getTitle());
		assertEquals("",election.getStart(),dets.getStart());
		assertEquals("",election.getEnd(),dets.getEnd());
		assertEquals("",election.getVotingStart(),dets.getStartVoting());
		assertEquals("",election.getVotingEnd(),dets.getEndVoting());
		assertEquals("",election.getInstitution().getNodeId(),dets.getInstitutionId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Election#fromElectionDetails(com.eulersbridge.iEngage.core.events.elections.ElectionDetails)}.
	 */
	@Test
	public final void testFromElectionDetails() 
	{
		Election electionTest=Election.fromElectionDetails(elecDets);
		assertEquals("electionTest not of Election class",electionTest.getClass(),Election.class);
		assertEquals("",elecDets.getElectionId(),electionTest.getNodeId());
		assertEquals("",elecDets.getTitle(),electionTest.getTitle());
		assertEquals("",elecDets.getStart(),electionTest.getStart());
		assertEquals("",elecDets.getEnd(),electionTest.getEnd());
		assertEquals("",elecDets.getStartVoting(),electionTest.getVotingStart());
		assertEquals("",elecDets.getEndVoting(),electionTest.getVotingEnd());
		assertEquals("",elecDets.getInstitutionId(),electionTest.getInstitution().getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Election#hashCode()}.
	 */
	@Test
	public final void testHashCode() 
	{
		Election electionTest=DatabaseDataFixture.populateElection1();
		assertEquals(electionTest.hashCode(),electionTest.hashCode());
		assertEquals(electionTest.hashCode(),election.hashCode());
		electionTest.setNodeId(null);
		assertNotEquals(election.hashCode(), electionTest.hashCode());
		assertNotEquals(electionTest.hashCode(), election.hashCode());
		election.setNodeId(null);
		electionTest.setStart(null);
		assertNotEquals(election.hashCode(), electionTest.hashCode());
		assertNotEquals(electionTest.hashCode(), election.hashCode());
		electionTest.setStart(election.getStart());
		electionTest.setEnd(null);
		assertNotEquals(election.hashCode(), electionTest.hashCode());
		assertNotEquals(electionTest.hashCode(), election.hashCode());
		electionTest.setEnd(election.getEnd());
		electionTest.setVotingStart(null);
		assertNotEquals(election.hashCode(), electionTest.hashCode());
		assertNotEquals(electionTest.hashCode(), election.hashCode());
		electionTest.setVotingStart(election.getVotingStart());
		electionTest.setVotingEnd(null);
		assertNotEquals(election.hashCode(), electionTest.hashCode());
		assertNotEquals(electionTest.hashCode(), election.hashCode());
		electionTest.setVotingEnd(election.getVotingEnd());
		electionTest.setInstitution(null);
		assertNotEquals(election.hashCode(), electionTest.hashCode());
		assertNotEquals(electionTest.hashCode(), election.hashCode());
		electionTest.setInstitution(election.getInstitution());
		electionTest.setTitle(null);
		assertNotEquals(election.hashCode(), electionTest.hashCode());
		assertNotEquals(electionTest.hashCode(), election.hashCode());
		electionTest.setTitle(election.getTitle());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Election#equals(java.lang.Object)}.
	 */
	@Test
	public final void testEqualsObject() 
	{
		Election electionTest=null;
		assertNotEquals(electionTest,election);
		assertNotEquals(election,electionTest);
		String notElection="";
		assertNotEquals(election,notElection);
		electionTest=DatabaseDataFixture.populateElection1();
		assertEquals(electionTest,electionTest);
		assertEquals(electionTest,election);
		electionTest.setNodeId(54l);
		assertNotEquals(election, electionTest);
		assertNotEquals(electionTest, election);
		election.setNodeId(null);
		electionTest.setNodeId(null);
		assertEquals(election, electionTest);
		assertEquals(electionTest, election);
		electionTest.setTitle("Something else");
		assertNotEquals(election, electionTest);
		electionTest.setTitle(null);
		assertNotEquals(election, electionTest);
		assertNotEquals(electionTest, election);
		electionTest.setTitle(election.getTitle());
		electionTest.setStart(54l);
		assertNotEquals(election, electionTest);
		electionTest.setStart(null);
		assertNotEquals(election, electionTest);
		assertNotEquals(electionTest, election);
		electionTest.setStart(election.getStart());
		electionTest.setEnd(54l);
		assertNotEquals(election, electionTest);
		electionTest.setEnd(null);
		assertNotEquals(election, electionTest);
		assertNotEquals(electionTest, election);
		electionTest.setEnd(election.getEnd());
		electionTest.setVotingStart(54l);
		assertNotEquals(election, electionTest);
		electionTest.setVotingStart(null);
		assertNotEquals(election, electionTest);
		assertNotEquals(electionTest, election);
		electionTest.setVotingStart(election.getVotingStart());
		electionTest.setVotingEnd(54l);
		assertNotEquals(election, electionTest);
		electionTest.setVotingEnd(null);
		assertNotEquals(election, electionTest);
		assertNotEquals(electionTest, election);
		electionTest.setVotingEnd(election.getVotingEnd());
		electionTest.setInstitution(new Institution());
		assertNotEquals(election, electionTest);
		electionTest.setInstitution(null);
		assertNotEquals(election, electionTest);
		assertNotEquals(electionTest, election);
		electionTest.setInstitution(election.getInstitution());

	}

}
