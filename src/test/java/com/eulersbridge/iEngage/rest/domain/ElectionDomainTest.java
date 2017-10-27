/**
 * 
 */
package com.eulersbridge.iEngage.rest.domain;

import com.eulersbridge.iEngage.core.events.elections.ElectionDetails;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Greg Newitt
 *
 */
public class ElectionDomainTest
{
	
	@Mock
	private ServletRequestAttributes attrs;
	
	ElectionDomain elec;
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

    public ElectionDomain populateElection()
    {
		ElectionDomain electionDomain =new ElectionDomain();
		electionDomain.setElectionId(id);
		electionDomain.setTitle(title);
		electionDomain.setStart(start);
		electionDomain.setEnd(end);
		electionDomain.setStartVoting(startVote);
		electionDomain.setEndVoting(endVote);
		electionDomain.setIntroduction(introduction);
		electionDomain.setProcess(process);
    	return electionDomain;
    }

	@Test
	public final void testElection()
	{
		ElectionDomain electionDomain =new ElectionDomain();
		assertNotNull("Not yet implemented", electionDomain);

	}
	/**
	 * Test method for {@link ElectionDomain#fromElectionDetails(com.eulersbridge.iEngage.core.events.elections.ElectionDetails)}.
	 */
	@Test
	public final void testFromElectionDetails() 
	{
		ElectionDetails dets=populateElectionDetails();
		ElectionDomain electionDomain = ElectionDomain.fromElectionDetails(dets);
		assertNotNull("Not yet implemented", electionDomain);
		assertEquals("Elec Id doesn't match.",dets.getElectionId(), electionDomain.getElectionId());
		assertEquals("Title doesn't match.",dets.getTitle(), electionDomain.getTitle());
		assertEquals("Start doesn't match.",dets.getStart(), electionDomain.getStart());
		assertEquals("End doesn't match.",dets.getEnd(), electionDomain.getEnd());
		assertEquals("Start Voting doesn't match.",dets.getStartVoting(), electionDomain.getStartVoting());
		assertEquals("End Voting doesn't match.",dets.getEndVoting(), electionDomain.getEndVoting());
		assertEquals("Introduction doesn't match.",dets.getIntroduction(), electionDomain.getIntroduction());
		assertEquals("Process doesn't match.",dets.getProcess(), electionDomain.getProcess());
	}

	/**
	 * Test method for {@link ElectionDomain#toElectionDetails()}.
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
		assertEquals("Introduction doesn't match.",elec.getIntroduction(),dets.getIntroduction());
		assertEquals("Process doesn't match.",elec.getProcess(),dets.getProcess());
	}

	/**
	 * Test method for {@link ElectionDomain#getElectionId()}.
	 */
	@Test
	public final void testGetElectionId() 
	{
		assertEquals("Elec Id doesn't match.",id,elec.getElectionId());
	}

	/**
	 * Test method for {@link ElectionDomain#getTitle()}.
	 */
	@Test
	public final void testGetTitle() 
	{
		assertEquals("Title doesn't match.",title,elec.getTitle());
	}

	/**
	 * Test method for {@link ElectionDomain#getStart()}.
	 */
	@Test
	public final void testGetStart() 
	{
		assertEquals("Start doesn't match.",start,elec.getStart());
	}

	/**
	 * Test method for {@link ElectionDomain#getEnd()}.
	 */
	@Test
	public final void testGetEnd() 
	{
		assertEquals("End doesn't match.",end,elec.getEnd());
	}

	/**
	 * Test method for {@link ElectionDomain#getStartVoting()}.
	 */
	@Test
	public final void testGetStartVoting() 
	{
		assertEquals("Start Voting doesn't match.",startVote,elec.getStartVoting());
	}

	/**
	 * Test method for {@link ElectionDomain#getEndVoting()}.
	 */
	@Test
	public final void testGetEndVoting() 
	{
		assertEquals("End Voting doesn't match.",endVote,elec.getEndVoting());
	}


	/**
	 * Test method for {@link ElectionDomain#getIntroduction()}.
	 */
	@Test
	public final void testGetIntroduction() 
	{
		assertEquals("Introduction doesn't match.",introduction,elec.getIntroduction());
	}

	/**
	 * Test method for {@link ElectionDomain#getProcess()}.
	 */
	@Test
	public final void testGetProcess() 
	{
		assertEquals("Process doesn't match.",process,elec.getProcess());
	}

	/**
	 * Test method for {@link ElectionDomain#setElectionId(java.lang.Long)}.
	 */
	@Test
	public final void testSetElectionId() 
	{
		Long id=14l;
		elec.setElectionId(id);
		assertEquals("Elec Id doesn't match.",id,elec.getElectionId());
	}

	/**
	 * Test method for {@link ElectionDomain#setTitle(java.lang.String)}.
	 */
	@Test
	public final void testSetTitle() 
	{
		String title="Another title";
		elec.setTitle(title);
		assertEquals("Title doesn't match.",title,elec.getTitle());
	}

	/**
	 * Test method for {@link ElectionDomain#setStart(java.lang.Long)}.
	 */
	@Test
	public final void testSetStart() 
	{
		Long start=2l;
		elec.setStart(start);
		assertEquals("Start doesn't match.",start,elec.getStart());
	}

	/**
	 * Test method for {@link ElectionDomain#setEnd(java.lang.Long)}.
	 */
	@Test
	public final void testSetEnd() 
	{
		Long end=3l;
		elec.setEnd(end);
		assertEquals("End doesn't match.",end,elec.getEnd());
	}

	/**
	 * Test method for {@link ElectionDomain#setStartVoting(java.lang.Long)}.
	 */
	@Test
	public final void testSetStartVoting() 
	{
		Long startVote=3l;
		elec.setStartVoting(startVote);
		assertEquals("Start Voting doesn't match.",startVote,elec.getStartVoting());
	}

	/**
	 * Test method for {@link ElectionDomain#setEndVoting(java.lang.Long)}.
	 */
	@Test
	public final void testSetEndVoting() 
	{
		Long endVote=5l;
		elec.setEndVoting(endVote);
		assertEquals("End Voting doesn't match.",endVote,elec.getEndVoting());
	}


}
