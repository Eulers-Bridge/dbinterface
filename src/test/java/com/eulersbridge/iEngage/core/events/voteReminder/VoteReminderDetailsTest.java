package com.eulersbridge.iEngage.core.events.voteReminder;

import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

public class VoteReminderDetailsTest 
{
	VoteReminderDetails vrd;


    private static Logger LOG = LoggerFactory.getLogger(VoteReminderDetailsTest.class);

	@Before
	public void setUp() throws Exception 
	{
		vrd=(DatabaseDataFixture.populateVoteReminder1()).toVoteReminderDetails();
	}

	@Test
	public final void testVoteReminderDetails() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("Test constructor,");
		VoteReminderDetails vr1=new VoteReminderDetails();
		assertNotNull("Not yet implemented",vr1);
	}

	@Test
	public final void testVoteReminderDetailsLongLongLongLongStringLong() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("Test constructor,");
		VoteReminderDetails vr1=new VoteReminderDetails(null, null, null, null, null, null);
		assertNotNull("Not yet implemented",vr1);
	}

	@Test
	public final void testGetNodeId()
	{
		VoteReminderDetails vr1=DatabaseDataFixture.populateVoteReminder1().toVoteReminderDetails();
		assertEquals(vrd.getNodeId(),vr1.getNodeId());
	}

	@Test
	public final void testSetNodeId()
	{
		Long id=15l;
		VoteReminderDetails vr1=new VoteReminderDetails();
		vr1.setNodeId(id);
		assertEquals(id,vr1.getNodeId());
	}

	@Test
	public final void testGetUserId()
	{
		VoteReminderDetails vr1=DatabaseDataFixture.populateVoteReminder1().toVoteReminderDetails();
		assertEquals(vrd.getUserId(),vr1.getUserId());
	}

	@Test
	public final void testSetUserId()
	{
		String email="test@email.com";
		VoteReminderDetails vr1=new VoteReminderDetails();
		vr1.setUserEmail(email);
		assertEquals(email,vr1.getUserId());
	}

	@Test
	public final void testGetElectionId()
	{
		VoteReminderDetails vr1=DatabaseDataFixture.populateVoteReminder1().toVoteReminderDetails();
		assertEquals(vrd.getElectionId(),vr1.getElectionId());
	}

	@Test
	public final void testSetElectionId()
	{
		Long id=15l;
		VoteReminderDetails vr1=new VoteReminderDetails();
		vr1.setElectionId(id);
		assertEquals(id,vr1.getElectionId());
	}

	@Test
	public final void testGetDate()
	{
		VoteReminderDetails vr1=DatabaseDataFixture.populateVoteReminder1().toVoteReminderDetails();
		assertEquals(vrd.getDate(),vr1.getDate());
	}

	@Test
	public final void testSetDate()
	{
		Long date=1l;
		VoteReminderDetails vr1=new VoteReminderDetails();
		vr1.setDate(date);
		assertEquals(date,vr1.getDate());
	}

	@Test
	public final void testGetLocation()
	{
		VoteReminderDetails vr1=DatabaseDataFixture.populateVoteReminder1().toVoteReminderDetails();
		assertEquals(vrd.getLocation(),vr1.getLocation());
	}

	@Test
	public final void testSetLocation()
	{
		String location="Union House";
		VoteReminderDetails vr1=new VoteReminderDetails();
		vr1.setLocation(location);
		assertEquals(location,vr1.getLocation());
	}

	@Test
	public final void testGetTimestamp()
	{
		VoteReminderDetails vr1=DatabaseDataFixture.populateVoteReminder1().toVoteReminderDetails();
		assertEquals(vrd.getTimestamp(),vr1.getTimestamp());
	}

	@Test
	public final void testSetTimestamp()
	{
		Long timestamp=354341l;
		VoteReminderDetails vr1=new VoteReminderDetails();
		vr1.setTimestamp(timestamp);
		assertEquals(timestamp,vr1.getTimestamp());
	}

	@Test
	public final void testToString()
	{
		assertNotNull("Not yet implemented",vrd.toString());
	}

	@Test
	public final void testEqualsObject()
	{
		VoteReminderDetails vrTest=null;
		assertNotEquals(vrTest,vrd);
		assertNotEquals(vrd,vrTest);
		String notElection="";
		assertNotEquals(vrd,notElection);
		vrTest=DatabaseDataFixture.populateVoteReminder1().toVoteReminderDetails();
		assertEquals(vrTest,vrTest);
		assertEquals(vrTest,vrd);
		vrTest.setNodeId(54l);
		assertNotEquals(vrd, vrTest);
		assertNotEquals(vrTest, vrd);
		vrd.setNodeId(null);
		assertNotEquals(vrd, vrTest);
		assertNotEquals(vrTest, vrd);
		vrTest.setNodeId(null);
		assertEquals(vrd, vrTest);
		assertEquals(vrTest, vrd);
		vrTest.setLocation("Something else");
		assertNotEquals(vrd, vrTest);
		vrTest.setLocation(null);
		assertNotEquals(vrd, vrTest);
		assertNotEquals(vrTest, vrd);
		vrTest.setLocation(vrd.getLocation());
		vrTest.setDate(54l);
		assertNotEquals(vrd, vrTest);
		vrTest.setDate(null);
		assertNotEquals(vrd, vrTest);
		assertNotEquals(vrTest, vrd);
		vrTest.setDate(vrd.getDate());
		vrTest.setTimestamp(54l);
		assertNotEquals(vrd, vrTest);
		vrTest.setTimestamp(null);
		assertNotEquals(vrd, vrTest);
		assertNotEquals(vrTest, vrd);
		vrTest.setTimestamp(vrd.getDate());
		vrTest.setElectionId(223l);
		assertNotEquals(vrd, vrTest);
		vrTest.setElectionId(null);
		assertNotEquals(vrd, vrTest);
		assertNotEquals(vrTest, vrd);
		vrTest.setElectionId(vrd.getElectionId());
		vrTest.setUserEmail("test@email.com");
		assertNotEquals(vrd, vrTest);
		vrTest.setUserEmail(null);
		assertNotEquals(vrd, vrTest);
		assertNotEquals(vrTest, vrd);
		vrTest.setUserEmail(vrd.getUserId());
	}

	@Test
	public final void testHashCode() 
	{
		VoteReminderDetails voteRecordDetailsTest=DatabaseDataFixture.populateVoteReminder1().toVoteReminderDetails();
		assertEquals(voteRecordDetailsTest.hashCode(),voteRecordDetailsTest.hashCode());
		assertEquals(voteRecordDetailsTest.hashCode(),vrd.hashCode());
		voteRecordDetailsTest.setNodeId(null);
		assertNotEquals(vrd.hashCode(), voteRecordDetailsTest.hashCode());
		assertNotEquals(voteRecordDetailsTest.hashCode(), vrd.hashCode());
		vrd.setNodeId(null);
		voteRecordDetailsTest.setDate(null);
		assertNotEquals(vrd.hashCode(), voteRecordDetailsTest.hashCode());
		assertNotEquals(voteRecordDetailsTest.hashCode(), vrd.hashCode());
		voteRecordDetailsTest.setDate(vrd.getDate());
		voteRecordDetailsTest.setTimestamp(null);
		assertNotEquals(vrd.hashCode(), voteRecordDetailsTest.hashCode());
		assertNotEquals(voteRecordDetailsTest.hashCode(), vrd.hashCode());
		voteRecordDetailsTest.setTimestamp(vrd.getTimestamp());
		voteRecordDetailsTest.setLocation(null);
		assertNotEquals(vrd.hashCode(), voteRecordDetailsTest.hashCode());
		assertNotEquals(voteRecordDetailsTest.hashCode(), vrd.hashCode());
		voteRecordDetailsTest.setLocation(vrd.getLocation());
		voteRecordDetailsTest.setUserEmail(null);
		assertNotEquals(vrd.hashCode(), voteRecordDetailsTest.hashCode());
		assertNotEquals(voteRecordDetailsTest.hashCode(), vrd.hashCode());
		voteRecordDetailsTest.setUserEmail(vrd.getUserId());
		voteRecordDetailsTest.setElectionId(null);
		assertNotEquals(vrd.hashCode(), voteRecordDetailsTest.hashCode());
		assertNotEquals(voteRecordDetailsTest.hashCode(), vrd.hashCode());
		voteRecordDetailsTest.setElectionId(vrd.getElectionId());
	}

}
