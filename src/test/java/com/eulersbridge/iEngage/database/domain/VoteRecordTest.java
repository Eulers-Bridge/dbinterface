package com.eulersbridge.iEngage.database.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;

public class VoteRecordTest 
{
    private static Logger LOG = LoggerFactory.getLogger(VoteRecordTest.class);
	VoteRecord vr;
	
	@Before
	public void setUp() throws Exception 
	{
		vr=DatabaseDataFixture.populateVoteRecord1();
	}

	@Test
	public final void testVoteRecord() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("Test constructor,");
		VoteRecord vr1=new VoteRecord();
		assertNotNull("Not yet implemented",vr1);
	}

	@Test
	public final void testVoteRecordString()
	{
		VoteRecord vr1=new VoteRecord("Union Building");
		assertNotNull("Not yet implemented",vr1);
	}

	@Test
	public final void testGetNodeId() 
	{
		Long id=1l;
		VoteRecord vr1=DatabaseDataFixture.populateVoteRecord(id, 1234l, null, "Union Building", null);
		assertEquals(id,vr1.getNodeId());
	}

	@Test
	public final void testSetNodeId() 
	{
		Long id=1l;
		VoteRecord vr1=new VoteRecord();
		vr1.setNodeId(id);
		assertEquals(id,vr1.getNodeId());
	}

	@Test
	public final void testGetDate()
	{
		Long date=1234l;
		VoteRecord vr1=DatabaseDataFixture.populateVoteRecord(1l, date, null, "Union Building", null);
		assertEquals(date,vr1.getDate());
	}

	@Test
	public final void testSetDate()
	{
		Long date=134l;
		VoteRecord vr1=new VoteRecord();
		vr1.setDate(date);
		assertEquals(date,vr1.getDate());
	}

	@Test
	public final void testGetLocation()
	{
		String location="Union House";
		VoteRecord vr1=DatabaseDataFixture.populateVoteRecord(1l, 123456l, null, location, null);
		assertEquals(location,vr1.getLocation());
	}

	@Test
	public final void testSetLocation()
	{
		String location="Union House";
		VoteRecord vr1=new VoteRecord();
		vr1.setLocation(location);
		assertEquals(location,vr1.getLocation());
	}

	@Test
	public final void testGetVoter()
	{
		User voter=DatabaseDataFixture.populateUserGnewitt();
		VoteRecord vr1=DatabaseDataFixture.populateVoteRecord(1l, 123456l, null, "Union House", voter);
		assertEquals(voter,vr1.getVoter());
	}

	@Test
	public final void testSetVoter()
	{
		User voter=DatabaseDataFixture.populateUserGnewitt();
		VoteReminder vr1=new VoteReminder();
		vr1.setVoter(voter);
		assertEquals(voter,vr1.getVoter());
	}

	@Test
	public final void testGetElection()
	{
		Election election=DatabaseDataFixture.populateElection1();
		VoteRecord vr1=DatabaseDataFixture.populateVoteRecord(1l, 123456l, election, "Union House", null);
		assertEquals(election,vr1.getElection());
	}

	@Test
	public final void testSetElection()
	{
		Election election=DatabaseDataFixture.populateElection1();
		VoteRecord vr1=new VoteRecord();
		vr1.setElection(election);
		assertEquals(election,vr1.getElection());
	}

	@Test
	public final void testToString()
	{
		assertNotNull("Not yet implemented",vr.toString());
	}

	@Test
	public final void testEqualsObject() 
	{
		VoteRecord vrTest=null;
		assertNotEquals(vrTest,vr);
		assertNotEquals(vr,vrTest);
		String notElection="";
		assertNotEquals(vr,notElection);
		vrTest=DatabaseDataFixture.populateVoteRecord1();
		assertEquals(vrTest,vrTest);
		assertEquals(vrTest,vr);
		vrTest.setNodeId(54l);
		assertNotEquals(vr, vrTest);
		assertNotEquals(vrTest, vr);
		vr.setNodeId(null);
		assertNotEquals(vr, vrTest);
		assertNotEquals(vrTest, vr);
		vrTest.setNodeId(null);
		assertEquals(vr, vrTest);
		assertEquals(vrTest, vr);
		vrTest.setLocation("Something else");
		assertNotEquals(vr, vrTest);
		vrTest.setLocation(null);
		assertNotEquals(vr, vrTest);
		assertNotEquals(vrTest, vr);
		vrTest.setLocation(vr.getLocation());
		vrTest.setDate(54l);
		assertNotEquals(vr, vrTest);
		vrTest.setDate(null);
		assertNotEquals(vr, vrTest);
		assertNotEquals(vrTest, vr);
		vrTest.setDate(vr.getDate());
		vrTest.setElection(new Election());
		assertNotEquals(vr, vrTest);
		vrTest.setElection(null);
		assertNotEquals(vr, vrTest);
		assertNotEquals(vrTest, vr);
		vrTest.setElection(vr.getElection());
		vrTest.setVoter(new User());
		assertNotEquals(vr, vrTest);
		vrTest.setVoter(null);
		assertNotEquals(vr, vrTest);
		assertNotEquals(vrTest, vr);
		vrTest.setVoter(vr.getVoter());
	}

	@Test
	public final void testHashCode() 
	{
		VoteRecord voteRecordTest=DatabaseDataFixture.populateVoteRecord1();
		assertEquals(voteRecordTest.hashCode(),voteRecordTest.hashCode());
		assertEquals(voteRecordTest.hashCode(),vr.hashCode());
		voteRecordTest.setNodeId(null);
		assertNotEquals(vr.hashCode(), voteRecordTest.hashCode());
		assertNotEquals(voteRecordTest.hashCode(), vr.hashCode());
		vr.setNodeId(null);
		voteRecordTest.setDate(null);
		assertNotEquals(vr.hashCode(), voteRecordTest.hashCode());
		assertNotEquals(voteRecordTest.hashCode(), vr.hashCode());
		voteRecordTest.setDate(vr.getDate());
		voteRecordTest.setLocation(null);
		assertNotEquals(vr.hashCode(), voteRecordTest.hashCode());
		assertNotEquals(voteRecordTest.hashCode(), vr.hashCode());
		voteRecordTest.setLocation(vr.getLocation());
		voteRecordTest.setVoter(null);
		assertNotEquals(vr.hashCode(), voteRecordTest.hashCode());
		assertNotEquals(voteRecordTest.hashCode(), vr.hashCode());
		voteRecordTest.setVoter(vr.getVoter());
		voteRecordTest.setElection(null);
		assertNotEquals(vr.hashCode(), voteRecordTest.hashCode());
		assertNotEquals(voteRecordTest.hashCode(), vr.hashCode());
		voteRecordTest.setElection(vr.getElection());
	}

}
