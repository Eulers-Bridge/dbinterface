/**
 * 
 */
package com.eulersbridge.iEngage.rest.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.eulersbridge.iEngage.core.events.elections.ElectionDetails;

/**
 * @author Greg Newitt
 *
 */
public class ElectionTest 
{
	
	@Mock
	private ServletRequestAttributes attrs;
	
	Election elec;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception 
	{
		MockitoAnnotations.initMocks(this);
		MockHttpServletRequest request=new MockHttpServletRequest();
		
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		elec=populateElection();
	}

	Long id=1l;
	String title="Test Election";
	Long start=123456l;
	Long end=126456l;
	Long startVote=125444l;
	Long endVote=126444l;
	Long instId=26l;
	String introduction = "This introduction",process="This process";
	
    public ElectionDetails populateElectionDetails()
    {
		ElectionDetails election=new ElectionDetails(id, title, start, end, startVote, endVote, instId, introduction, process);
    	return election;
    }

    public Election populateElection()
    {
		Election election=new Election();
		election.setElectionId(id);
		election.setTitle(title);
		election.setStart(start);
		election.setEnd(end);
		election.setStartVoting(startVote);
		election.setEndVoting(endVote);
		election.setInstitutionId(instId);
		election.setIntroduction(introduction);
		election.setProcess(process);
    	return election;
    }

	@Test
	public final void testElection()
	{
		Election election=new Election();
		assertNotNull("Not yet implemented",election);

	}
	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Election#fromElectionDetails(com.eulersbridge.iEngage.core.events.elections.ElectionDetails)}.
	 */
	@Test
	public final void testFromElectionDetails() 
	{
		ElectionDetails dets=populateElectionDetails();
		Election election=Election.fromElectionDetails(dets);
		assertNotNull("Not yet implemented",election);
		assertEquals("Elec Id doesn't match.",dets.getElectionId(),election.getElectionId());
		assertEquals("Title doesn't match.",dets.getTitle(),election.getTitle());
		assertEquals("Start doesn't match.",dets.getStart(),election.getStart());
		assertEquals("End doesn't match.",dets.getEnd(),election.getEnd());
		assertEquals("Start Voting doesn't match.",dets.getStartVoting(),election.getStartVoting());
		assertEquals("End Voting doesn't match.",dets.getEndVoting(),election.getEndVoting());
		assertEquals("Inst Id doesn't match.",dets.getInstitutionId(),election.getInstitutionId());
		assertEquals("Introduction doesn't match.",dets.getIntroduction(),election.getIntroduction());
		assertEquals("Process doesn't match.",dets.getProcess(),election.getProcess());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Election#toElectionDetails()}.
	 */
	@Test
	public final void testToElectionDetails() 
	{
		ElectionDetails dets=elec.toElectionDetails();
		assertNotNull("Not yet implemented",dets);
		assertEquals("Elec Id doesn't match.",elec.getElectionId(),dets.getElectionId());
		assertEquals("Title doesn't match.",elec.getTitle(),dets.getTitle());
		assertEquals("Start doesn't match.",elec.getStart(),dets.getStart());
		assertEquals("End doesn't match.",elec.getEnd(),dets.getEnd());
		assertEquals("Start Voting doesn't match.",elec.getStartVoting(),dets.getStartVoting());
		assertEquals("End Voting doesn't match.",elec.getEndVoting(),dets.getEndVoting());
		assertEquals("Inst Id doesn't match.",elec.getInstitutionId(),dets.getInstitutionId());
		assertEquals("Introduction doesn't match.",elec.getIntroduction(),dets.getIntroduction());
		assertEquals("Process doesn't match.",elec.getProcess(),dets.getProcess());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Election#getElectionId()}.
	 */
	@Test
	public final void testGetElectionId() 
	{
		assertEquals("Elec Id doesn't match.",id,elec.getElectionId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Election#getTitle()}.
	 */
	@Test
	public final void testGetTitle() 
	{
		assertEquals("Title doesn't match.",title,elec.getTitle());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Election#getStart()}.
	 */
	@Test
	public final void testGetStart() 
	{
		assertEquals("Start doesn't match.",start,elec.getStart());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Election#getEnd()}.
	 */
	@Test
	public final void testGetEnd() 
	{
		assertEquals("End doesn't match.",end,elec.getEnd());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Election#getStartVoting()}.
	 */
	@Test
	public final void testGetStartVoting() 
	{
		assertEquals("Start Voting doesn't match.",startVote,elec.getStartVoting());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Election#getEndVoting()}.
	 */
	@Test
	public final void testGetEndVoting() 
	{
		assertEquals("End Voting doesn't match.",endVote,elec.getEndVoting());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Election#getInstitutionId()}.
	 */
	@Test
	public final void testGetInstitutionId() 
	{
		assertEquals("Inst Id doesn't match.",instId,elec.getInstitutionId());
	}
	
	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Election#getIntroduction()}.
	 */
	@Test
	public final void testGetIntroduction() 
	{
		assertEquals("Introduction doesn't match.",introduction,elec.getIntroduction());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Election#getProcess()}.
	 */
	@Test
	public final void testGetProcess() 
	{
		assertEquals("Process doesn't match.",process,elec.getProcess());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Election#setElectionId(java.lang.Long)}.
	 */
	@Test
	public final void testSetElectionId() 
	{
		Long id=14l;
		elec.setElectionId(id);
		assertEquals("Elec Id doesn't match.",id,elec.getElectionId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Election#setTitle(java.lang.String)}.
	 */
	@Test
	public final void testSetTitle() 
	{
		String title="Another title";
		elec.setTitle(title);
		assertEquals("Title doesn't match.",title,elec.getTitle());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Election#setStart(java.lang.Long)}.
	 */
	@Test
	public final void testSetStart() 
	{
		Long start=2l;
		elec.setStart(start);
		assertEquals("Start doesn't match.",start,elec.getStart());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Election#setEnd(java.lang.Long)}.
	 */
	@Test
	public final void testSetEnd() 
	{
		Long end=3l;
		elec.setEnd(end);
		assertEquals("End doesn't match.",end,elec.getEnd());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Election#setStartVoting(java.lang.Long)}.
	 */
	@Test
	public final void testSetStartVoting() 
	{
		Long startVote=3l;
		elec.setStartVoting(startVote);
		assertEquals("Start Voting doesn't match.",startVote,elec.getStartVoting());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Election#setEndVoting(java.lang.Long)}.
	 */
	@Test
	public final void testSetEndVoting() 
	{
		Long endVote=5l;
		elec.setEndVoting(endVote);
		assertEquals("End Voting doesn't match.",endVote,elec.getEndVoting());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.rest.domain.Election#setElectionId(java.lang.Long)}.
	 */
	@Test
	public final void testSetInstitutionId() 
	{
		Long instId=25l;
		elec.setInstitutionId(instId);
		assertEquals("Elec Id doesn't match.",instId,elec.getInstitutionId());
	}

}
