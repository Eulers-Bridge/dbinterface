/**
 * 
 */
package com.eulersbridge.iEngage.database.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.eulersbridge.iEngage.core.events.candidate.CandidateDetails;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;

/**
 * @author Greg Newitt
 *
 */
public class CandidateTest
{
	private Candidate candidate;
	private CandidateDetails dets;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		candidate=DatabaseDataFixture.populateCandidate1();
		dets=candidate.toCandidateDetails();
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Candidate#Candidate()}.
	 */
	@Test
	public final void testCandidate()
	{
		Candidate candidateTest=new Candidate();
		assertEquals("candidateTest not of Candidate class",candidateTest.getClass(),Candidate.class);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Candidate#Candidate()}.
	 */
	@Test
	public final void testCandidateLongStringStringUserPosition()
	{
		Candidate candidateTest=new Candidate(candidate.getNodeId(),candidate.getInformation(),candidate.getPolicyStatement(),candidate.getUser(),candidate.getPosition());
		assertEquals("electionTest not of Election class",candidateTest.getClass(),Candidate.class);
		assertEquals("",candidate.getNodeId(),candidateTest.getNodeId());
		assertEquals("",candidate.getInformation(),candidateTest.getInformation());
		assertEquals("",candidate.getPolicyStatement(),candidateTest.getPolicyStatement());
		assertEquals("",candidate.getPosition(),candidateTest.getPosition());
		assertEquals("",candidate.getUser(),candidateTest.getUser());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Candidate#fromCandidateDetails(com.eulersbridge.iEngage.core.events.candidate.CandidateDetails)}.
	 */
	@Test
	public final void testFromCandidateDetails()
	{
		Candidate candidateTest=Candidate.fromCandidateDetails(dets);
		assertEquals("electionTest not of Election class",candidateTest.getClass(),Candidate.class);
		assertEquals("",dets.getNodeId(),candidateTest.getNodeId());
		assertEquals("",dets.getInformation(),candidateTest.getInformation());
		assertEquals("",dets.getPolicyStatement(),candidateTest.getPolicyStatement());
		assertEquals("",dets.getPositionId(),candidateTest.getPosition().getNodeId());
		assertEquals("",dets.getUserId(),candidateTest.getUser().getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Candidate#toCandidateDetails()}.
	 */
	@Test
	public final void testToCandidateDetails()
	{
		CandidateDetails dets=candidate.toCandidateDetails();
		assertEquals("electionDetails not of ElectionDetails class",dets.getClass(),CandidateDetails.class);
		assertEquals("",candidate.getNodeId(),dets.getNodeId());
		assertEquals("",candidate.getInformation(),dets.getInformation());
		assertEquals("",candidate.getPolicyStatement(),dets.getPolicyStatement());
		assertEquals("",candidate.getPosition().getNodeId(),dets.getPositionId());
		assertEquals("",candidate.getUser().getNodeId(),dets.getUserId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Candidate#toString()}.
	 */
	@Test
	public final void testToString()
	{
		assertNotNull("Not yet implemented",candidate.toString());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Candidate#getNodeId()}.
	 */
	@Test
	public final void testGetCandidateId()
	{
		assertEquals("",candidate.getNodeId(),dets.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Candidate#setNodeId(java.lang.Long)}.
	 */
	@Test
	public final void testSetCandidateId()
	{
		Long id=515l;
		assertNotEquals("",candidate.getNodeId(),id);
		candidate.setNodeId(id);
		assertEquals("",id,candidate.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Candidate#getInformation()}.
	 */
	@Test
	public final void testGetInformation()
	{
		assertEquals("",candidate.getInformation(),dets.getInformation());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Candidate#setInformation(java.lang.String)}.
	 */
	@Test
	public final void testSetInformation()
	{
		String information="Whatever";
		assertNotEquals("",candidate.getInformation(),information);
		candidate.setInformation(information);
		assertEquals("",information,candidate.getInformation());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Candidate#getUser()}.
	 */
	@Test
	public final void testGetUser()
	{
		assertEquals("",candidate.getUser().getNodeId(),dets.getUserId());
		assertEquals("",candidate.getUser(),DatabaseDataFixture.populateUserGnewitt());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Candidate#setUser(com.eulersbridge.iEngage.database.domain.User)}.
	 */
	@Test
	public final void testSetUser()
	{
		User user=DatabaseDataFixture.populateUserGnewitt2();
		assertNotEquals("",candidate.getUser(),user);
		candidate.setUser(user);
		assertEquals("",user,candidate.getUser());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Candidate#getPosition()}.
	 */
	@Test
	public final void testGetPosition()
	{
		assertEquals("",candidate.getPosition().getNodeId(),dets.getPositionId());
		assertEquals("",candidate.getPosition(),DatabaseDataFixture.populatePosition1());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Candidate#setPosition(com.eulersbridge.iEngage.database.domain.Position)}.
	 */
	@Test
	public final void testSetPosition()
	{
		Position position=DatabaseDataFixture.populatePosition2();
		assertNotEquals("",candidate.getPosition(),position);
		candidate.setPosition(position);
		assertEquals("",position,candidate.getPosition());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Candidate#getPolicyStatement()}.
	 */
	@Test
	public final void testGetPolicyStatement()
	{
		assertEquals("",candidate.getPolicyStatement(),dets.getPolicyStatement());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Candidate#setPolicyStatement(java.lang.String)}.
	 */
	@Test
	public final void testSetPolicyStatement()
	{
		String policy="Whatever";
		assertNotEquals("",candidate.getPolicyStatement(),policy);
		candidate.setPolicyStatement(policy);
		assertEquals("",policy,candidate.getPolicyStatement());
	}

}
