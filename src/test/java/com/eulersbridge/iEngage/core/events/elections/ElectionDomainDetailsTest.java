package com.eulersbridge.iEngage.core.events.elections;

import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Yikai Gong
 */

public class ElectionDomainDetailsTest {
    ElectionDetails electionDetails;
    Long electionId;
    String title;
    Long start;
    Long end;
    Long startVoting;
    Long endVoting;
    Long institutionId;
    String introduction;
    String process;

    @Before
    public void setUp() throws Exception {
        electionDetails = DatabaseDataFixture.populateElection1().toElectionDetails();
        electionId = electionDetails.getElectionId();
        title = electionDetails.getTitle();
        start = electionDetails.getStart();
        end = electionDetails.getEnd();
        startVoting = electionDetails.getStartVoting();
        endVoting = electionDetails.getEndVoting();
        institutionId = electionDetails.getInstitutionId();
        introduction = electionDetails.getIntroduction();
        process = electionDetails.getProcess();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testElectionDetail() throws Exception{
        assertNotNull("electionDetail is null", electionDetails);
    }

    @Test
    public void testElectionDetailParams() throws Exception
    {
    	ElectionDetails electionDetails2=new ElectionDetails(electionId, title, start, end, startVoting, endVoting, institutionId, introduction, process);
        assertNotNull("electionDetail is null", electionDetails2);
    }

    @Test
    public void testSetElectionId() throws Exception {
        ElectionDetails electionDetails1 = new ElectionDetails();
        electionDetails1.setElectionId(electionId);
        assertEquals("electionId does not match", electionId, electionDetails1.getElectionId());
    }

    @Test
    public void testGetElectionId() throws Exception {
        assertEquals("electionId does not match", electionId, electionDetails.getElectionId());
    }

    @Test
    public void testSetTitle() throws Exception {
        ElectionDetails electionDetails1 = new ElectionDetails();
        electionDetails1.setTitle(title);
        assertEquals("title does not match", title, electionDetails1.getTitle());
    }

    @Test
    public void testGetTitle() throws Exception {
        assertEquals("title does not match", title, electionDetails.getTitle());
    }

    @Test
    public void testSetStart() throws Exception {
        ElectionDetails electionDetails1 = new ElectionDetails();
        electionDetails1.setStart(start);
        assertEquals("start does not match", start, electionDetails1.getStart());
    }

    @Test
    public void testGetStart() throws Exception {
        assertEquals("start does not match", start, electionDetails.getStart());
    }

    @Test
    public void testSetEnd() throws Exception {
        ElectionDetails electionDetails1 = new ElectionDetails();
        electionDetails1.setEnd(end);
        assertEquals("end does not match", end, electionDetails1.getEnd());
    }

    @Test
    public void testGetEnd() throws Exception {
        assertEquals("end does not match", end, electionDetails.getEnd());
    }

    @Test
    public void testSetStartVoting() throws Exception {
        ElectionDetails electionDetails1 = new ElectionDetails();
        electionDetails1.setStartVoting(startVoting);
        assertEquals("startVoting does not match", startVoting, electionDetails1.getStartVoting());
    }

    @Test
    public void testGetStartVoting() throws Exception {
        assertEquals("startVoting does not match", startVoting, electionDetails.getStartVoting());
    }

    @Test
    public void testSetEndVoting() throws Exception {
        ElectionDetails electionDetails1 = new ElectionDetails();
        electionDetails1.setEndVoting(endVoting);
        assertEquals("endVoting does not match", endVoting, electionDetails1.getEndVoting());
    }

    @Test
    public void testGetEndVoting() throws Exception {
        assertEquals("endVoting does not match", endVoting, electionDetails.getEndVoting());
    }

    @Test
    public void testSetInstituionId() throws Exception {
        ElectionDetails electionDetails1 = new ElectionDetails();
        electionDetails1.setInstitutionId(institutionId);
        assertEquals("instId does not match", institutionId, electionDetails1.getInstitutionId());
    }

    @Test
    public void testGetInstitutionId() throws Exception {
        assertEquals("institutionId does not match", institutionId, electionDetails.getInstitutionId());
    }

    @Test
    public void testSetIntroduction() throws Exception {
        ElectionDetails electionDetails1 = new ElectionDetails();
        electionDetails1.setIntroduction(introduction);
        assertEquals("Introduction does not match", introduction, electionDetails1.getIntroduction());
    }

    @Test
    public void testGetIntroduction() throws Exception {
        assertEquals("Introduction does not match", introduction, electionDetails.getIntroduction());
    }

    @Test
    public void testSetProcess() throws Exception {
        ElectionDetails electionDetails1 = new ElectionDetails();
        electionDetails1.setProcess(process);
        assertEquals("Process does not match", process, electionDetails1.getProcess());
    }

    @Test
    public void testGetProcess() throws Exception {
        assertEquals("Process does not match", process, electionDetails.getProcess());
    }

    @Test
    public void testToString() throws Exception {
        assertNotNull("toSting is null", electionDetails.toString());
        ElectionDetails electionDetails1 = new ElectionDetails();
        electionDetails1.setElectionId(electionId);
        electionDetails1.setTitle(title);
        electionDetails1.setStart(start);
        electionDetails1.setEnd(end);
        electionDetails1.setStartVoting(startVoting);
        electionDetails1.setEndVoting(endVoting);
        electionDetails1.setIntroduction(introduction);
        electionDetails1.setProcess(process);
        assertEquals("toString does not match", electionDetails1.toString(), electionDetails.toString());
    }

    @Test
    public void testEquals() throws Exception {
        ElectionDetails electionDetails1 = new ElectionDetails();
        electionDetails1.setElectionId(electionId);
        assertEquals("electionDetail does not match", electionDetails1, electionDetails);
        
        
		ElectionDetails electionTest=null;
		assertNotEquals(electionTest,electionDetails);
		assertNotEquals(electionDetails,electionTest);
		String notElection="";
		assertNotEquals(electionDetails,notElection);
		electionTest=DatabaseDataFixture.populateElection1().toElectionDetails();
		assertEquals(electionTest,electionTest);
		assertEquals(electionTest,electionDetails);
		electionTest.setElectionId(54l);
		assertNotEquals(electionDetails, electionTest);
		assertNotEquals(electionTest, electionDetails);
		electionDetails.setElectionId(null);
		electionTest.setElectionId(null);
		assertEquals(electionDetails, electionTest);
		assertEquals(electionTest, electionDetails);

		electionTest.setTitle("Something else");
		assertNotEquals(electionDetails, electionTest);
		electionTest.setTitle(null);
		assertNotEquals(electionDetails, electionTest);
		assertNotEquals(electionTest, electionDetails);
		electionTest.setTitle(electionDetails.getTitle());

		electionTest.setIntroduction("Something else");
		assertNotEquals(electionDetails, electionTest);
		electionTest.setIntroduction(null);
		assertNotEquals(electionDetails, electionTest);
		assertNotEquals(electionTest, electionDetails);
		electionTest.setIntroduction(electionDetails.getIntroduction());

		electionTest.setProcess("Something else");
		assertNotEquals(electionDetails, electionTest);
		electionTest.setProcess(null);
		assertNotEquals(electionDetails, electionTest);
		assertNotEquals(electionTest, electionDetails);
		electionTest.setProcess(electionDetails.getProcess());

		electionTest.setStart(54l);
		ElectionDetails election=electionDetails;
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
		electionTest.setStartVoting(54l);
		assertNotEquals(election, electionTest);
		electionTest.setStartVoting(null);
		assertNotEquals(election, electionTest);
		assertNotEquals(electionTest, election);
		electionTest.setStartVoting(election.getStartVoting());
		electionTest.setEndVoting(54l);
		assertNotEquals(election, electionTest);
		electionTest.setEndVoting(null);
		assertNotEquals(election, electionTest);
		assertNotEquals(electionTest, election);
		electionTest.setEndVoting(election.getEndVoting());
		electionTest.setInstitutionId(22l);
		assertNotEquals(election, electionTest);
		electionTest.setInstitutionId(null);
		assertNotEquals(election, electionTest);
		assertNotEquals(electionTest, election);
		electionTest.setInstitutionId(election.getInstitutionId());

    }
    
	/**
	 * Test method for {@link com.eulersbridge.iEngage.database.domain.Election#hashCode()}.
	 */
	@Test
	public final void testHashCode() 
	{
		ElectionDetails electionTest=DatabaseDataFixture.populateElection1().toElectionDetails();
		ElectionDetails election=electionDetails;
		assertEquals(electionTest.hashCode(),electionTest.hashCode());
		assertEquals(electionTest.hashCode(),election.hashCode());
		electionTest.setElectionId(null);
		assertNotEquals(election.hashCode(), electionTest.hashCode());
		assertNotEquals(electionTest.hashCode(), election.hashCode());
		electionTest.setElectionId(null);
		electionTest.setStart(null);
		assertNotEquals(election.hashCode(), electionTest.hashCode());
		assertNotEquals(electionTest.hashCode(), election.hashCode());
		electionTest.setStart(election.getStart());
		electionTest.setEnd(null);
		assertNotEquals(election.hashCode(), electionTest.hashCode());
		assertNotEquals(electionTest.hashCode(), election.hashCode());
		electionTest.setEnd(election.getEnd());
		electionTest.setStartVoting(null);
		assertNotEquals(election.hashCode(), electionTest.hashCode());
		assertNotEquals(electionTest.hashCode(), election.hashCode());
		electionTest.setStartVoting(election.getStartVoting());
		electionTest.setEndVoting(null);
		assertNotEquals(election.hashCode(), electionTest.hashCode());
		assertNotEquals(electionTest.hashCode(), election.hashCode());
		electionTest.setEndVoting(election.getEndVoting());
		electionTest.setInstitutionId(null);
		assertNotEquals(election.hashCode(), electionTest.hashCode());
		assertNotEquals(electionTest.hashCode(), election.hashCode());
		electionTest.setInstitutionId(election.getInstitutionId());

		electionTest.setTitle(null);
		assertNotEquals(election.hashCode(), electionTest.hashCode());
		assertNotEquals(electionTest.hashCode(), election.hashCode());
		electionTest.setTitle(election.getTitle());

		electionTest.setIntroduction(null);
		assertNotEquals(election.hashCode(), electionTest.hashCode());
		assertNotEquals(electionTest.hashCode(), election.hashCode());
		electionTest.setIntroduction(election.getIntroduction());

		electionTest.setProcess(null);
		assertNotEquals(election.hashCode(), electionTest.hashCode());
		assertNotEquals(electionTest.hashCode(), election.hashCode());
		electionTest.setProcess(election.getProcess());
	}


}