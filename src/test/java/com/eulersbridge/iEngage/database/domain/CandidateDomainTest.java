/**
 * 
 */
package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.candidate.CandidateDetails;
import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Greg Newitt
 *
 */
public class CandidateDomainTest
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
		Candidate candidateTest=new Candidate(candidate.getNodeId(),candidate.getInformation(),candidate.getPolicyStatement(),candidate.getUser$(),candidate.getPosition$(), null);
		assertEquals("electionTest not of Election class",candidateTest.getClass(),Candidate.class);
		assertEquals("",candidate.getNodeId(),candidateTest.getNodeId());
		assertEquals("",candidate.getInformation(),candidateTest.getInformation());
		assertEquals("",candidate.getPolicyStatement(),candidateTest.getPolicyStatement());
		assertEquals("",candidate.getPosition$(),candidateTest.getPosition$());
		assertEquals("",candidate.getUser$(),candidateTest.getUser$());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Candidate#fromCandidateDetails(com.eulersbridge.iEngage.core.events.candidate.CandidateDetails)}.
	 */
	@Test
	public final void testFromCandidateDetails()
	{
		Candidate candidateTest=Candidate.fromCandidateDetails(dets);
		Position position = new Position(dets.getPositionId());
		User user = new User(dets.getUserId());
		candidateTest.setPosition(position);
		candidateTest.setUser(user);
		assertEquals("electionTest not of Election class",candidateTest.getClass(),Candidate.class);
		assertEquals("",dets.getNodeId(),candidateTest.getNodeId());
		assertEquals("",dets.getInformation(),candidateTest.getInformation());
		assertEquals("",dets.getPolicyStatement(),candidateTest.getPolicyStatement());
		assertEquals("",dets.getPositionId(),candidateTest.getPosition$().getNodeId());
		assertEquals("",dets.getUserId(),candidateTest.getUser$().getNodeId());
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
		assertEquals("",candidate.getPosition$().getNodeId(),dets.getPositionId());
		assertEquals("",candidate.getUser$().getNodeId(),dets.getUserId());
	}
	@Test
	public final void testToCandidateDetailsPositionNull()
	{
		Position position=null;
		candidate.setPosition(position);
		CandidateDetails dets=candidate.toCandidateDetails();
		assertEquals("electionDetails not of ElectionDetails class",dets.getClass(),CandidateDetails.class);
		assertEquals("",candidate.getNodeId(),dets.getNodeId());
		assertEquals("",candidate.getInformation(),dets.getInformation());
		assertEquals("",candidate.getPolicyStatement(),dets.getPolicyStatement());
		assertEquals("",position,dets.getPositionId());
		assertEquals("",candidate.getUser$().getNodeId(),dets.getUserId());
	}

	@Test
	public final void testToCandidateDetailsUserNull()
	{
		User user=null;
		candidate.setUser(user);
		CandidateDetails dets=candidate.toCandidateDetails();
		assertEquals("electionDetails not of ElectionDetails class",dets.getClass(),CandidateDetails.class);
		assertEquals("",candidate.getNodeId(),dets.getNodeId());
		assertEquals("",candidate.getInformation(),dets.getInformation());
		assertEquals("",candidate.getPolicyStatement(),dets.getPolicyStatement());
		assertEquals("",candidate.getPosition$().getNodeId(),dets.getPositionId());
		assertEquals("",user,dets.getUserId());
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
	 * Test method for {@link Candidate#getUser$()}.
	 */
	@Test
	public final void testGetUser()
	{
		assertEquals("",candidate.getUser$().getNodeId(),dets.getUserId());
		assertEquals("",candidate.getUser$(),DatabaseDataFixture.populateUserGnewitt());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Candidate#setUser(com.eulersbridge.iEngage.database.domain.User)}.
	 */
	@Test
	public final void testSetUser()
	{
		User user=DatabaseDataFixture.populateUserGnewitt2();
		assertNotEquals("",candidate.getUser$(),user);
		candidate.setUser(user);
		assertEquals("",user,candidate.getUser$());
	}

	/**
	 * Test method for {@link Candidate#getPosition$()}.
	 */
	@Test
	public final void testGetPosition()
	{
		assertEquals("",candidate.getPosition$().getNodeId(),dets.getPositionId());
		assertEquals("",candidate.getPosition$(),DatabaseDataFixture.populatePosition1());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Candidate#setPosition(com.eulersbridge.iEngage.database.domain.Position)}.
	 */
	@Test
	public final void testSetPosition()
	{
		Position position=DatabaseDataFixture.populatePosition2();
		assertNotEquals("",candidate.getPosition$(),position);
		candidate.setPosition(position);
		assertEquals("",position,candidate.getPosition$());
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
	
	private void checkHashCode(Candidate test1,Candidate test2)
	{
		assertNotEquals(test1.hashCode(), test2.hashCode());
		assertNotEquals(test2.hashCode(), test1.hashCode());
	}
	
	private void checkNotEquals(Candidate test1,Candidate test2)
	{
		assertNotEquals(test1, test2);
		assertNotEquals(test2, test1);
	}
	
	/**
	 * Test method for {@link java.lang.Object#hashCode()}.
	 */
	@Test
	public final void testHashCode()
	{
		Candidate candidateTest=DatabaseDataFixture.populateCandidate1();
		assertEquals(candidateTest.hashCode(),candidateTest.hashCode());
		assertEquals(candidateTest.hashCode(),candidate.hashCode());
		candidateTest.setNodeId(null);
		checkHashCode(candidate,candidateTest);
		candidate.setNodeId(null);
		
		candidateTest.setPolicyStatement(null);
		checkHashCode(candidate,candidateTest);
		candidateTest.setPolicyStatement(candidate.getPolicyStatement());
		
		candidateTest.setInformation(null);
		checkHashCode(candidate,candidateTest);
		candidateTest.setInformation(candidate.getInformation());
		
		candidateTest.setPosition(null);;
		checkHashCode(candidate,candidateTest);
		candidateTest.setPosition(candidate.getPosition$());
		
		candidateTest.setUser(null);;
		checkHashCode(candidate,candidateTest);
		candidateTest.setUser(candidate.getUser$());

	}

	/**
	 * Test method for {@link java.lang.Object#equals(java.lang.Object)}.
	 */
	@Test
	public final void testEquals()
	{
		Candidate candidateTest=null;
		assertNotEquals(candidateTest,candidate);
		assertNotEquals(candidate,candidateTest);
		String notElection="";
		assertNotEquals(candidate,notElection);
		candidateTest=DatabaseDataFixture.populateCandidate1();
		assertEquals(candidateTest,candidateTest);
		assertEquals(candidateTest,candidate);
		
		candidateTest.setNodeId(54l);
		checkNotEquals(candidate,candidateTest);
		candidate.setNodeId(null);
		checkNotEquals(candidate,candidateTest);
		candidateTest.setNodeId(null);
		
		assertEquals(candidate, candidateTest);
		assertEquals(candidateTest, candidate);
		
		candidateTest.setInformation("Some description");
		assertNotEquals(candidate, candidateTest);
		candidateTest.setInformation(null);
		checkNotEquals(candidateTest, candidate);
		candidateTest.setInformation(candidate.getInformation());
		
		candidateTest.setPolicyStatement("title");
		assertNotEquals(candidate, candidateTest);
		candidateTest.setPolicyStatement(null);
		checkNotEquals(candidate, candidateTest);
		candidateTest.setPolicyStatement(candidate.getPolicyStatement());
		
		candidateTest.setUser(null);
		checkNotEquals(candidate, candidateTest);
		candidateTest.setUser(candidate.getUser$());
		
		candidateTest.setPosition(null);
		checkNotEquals(candidate, candidateTest);
		candidateTest.setPosition(candidate.getPosition$());
	}
}
