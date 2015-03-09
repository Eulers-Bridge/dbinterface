/**
 * 
 */
package com.eulersbridge.iEngage.core.events.voteRecord;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eulersbridge.iEngage.database.domain.Fixture.DatabaseDataFixture;

/**
 * @author Greg Newitt
 *
 */
public class VoteRecordDetailsTest 
{
	VoteRecordDetails vrd;

    private static Logger LOG = LoggerFactory.getLogger(VoteRecordDetailsTest.class);

    /**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception 
	{
		vrd=(DatabaseDataFixture.populateVoteRecord1()).toVoteRecordDetails();
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.voteRecord.VoteRecordDetails#VoteRecordDetails()}.
	 */
	@Test
	public final void testVoteRecordDetails() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("Test constructor,");
		VoteRecordDetails vr1=new VoteRecordDetails();
		assertNotNull("Not yet implemented",vr1);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.voteRecord.VoteRecordDetails#VoteRecordDetails(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.String)}.
	 */
	@Test
	public final void testVoteRecordDetailsLongLongLongLongString() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("Test constructor,");
		VoteRecordDetails vr1=new VoteRecordDetails(null, null, null, null, null,null);
		assertNotNull("Not yet implemented",vr1);
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.voteRecord.VoteRecordDetails#getNodeId()}.
	 */
	@Test
	public final void testGetNodeId() 
	{
		VoteRecordDetails vr1=DatabaseDataFixture.populateVoteRecord1().toVoteRecordDetails();
		assertEquals(vrd.getNodeId(),vr1.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.voteRecord.VoteRecordDetails#setNodeId(java.lang.Long)}.
	 */
	@Test
	public final void testSetNodeId()
	{
		Long id=15l;
		VoteRecordDetails vr1=new VoteRecordDetails();
		vr1.setNodeId(id);
		assertEquals(id,vr1.getNodeId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.voteRecord.VoteRecordDetails#getVoterId()}.
	 */
	@Test
	public final void testGetVoterId()
	{
		VoteRecordDetails vr1=DatabaseDataFixture.populateVoteRecord1().toVoteRecordDetails();
		assertEquals(vrd.getVoterId(),vr1.getVoterId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.voteRecord.VoteRecordDetails#setVoterId(java.lang.Long)}.
	 */
	@Test
	public final void testSetVoterId()
	{
		String email="test@email.com";
		VoteRecordDetails vr1=new VoteRecordDetails();
		vr1.setVoterId(email);
		assertEquals(email,vr1.getVoterId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.voteRecord.VoteRecordDetails#getElectionId()}.
	 */
	@Test
	public final void testGetElectionId() 
	{
		VoteRecordDetails vr1=DatabaseDataFixture.populateVoteRecord1().toVoteRecordDetails();
		assertEquals(vrd.getElectionId(),vr1.getElectionId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.voteRecord.VoteRecordDetails#setElectionId(java.lang.Long)}.
	 */
	@Test
	public final void testSetElectionId() {
		Long id=15l;
		VoteRecordDetails vr1=new VoteRecordDetails();
		vr1.setElectionId(id);
		assertEquals(id,vr1.getElectionId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.voteRecord.VoteRecordDetails#getDate()}.
	 */
	@Test
	public final void testGetDate()
	{
		VoteRecordDetails vr1=DatabaseDataFixture.populateVoteRecord1().toVoteRecordDetails();
		assertEquals(vrd.getDate(),vr1.getDate());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.voteRecord.VoteRecordDetails#setDate(java.lang.Long)}.
	 */
	@Test
	public final void testSetDate()
	{
		Long date=1l;
		VoteRecordDetails vr1=new VoteRecordDetails();
		vr1.setDate(date);
		assertEquals(date,vr1.getDate());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.voteRecord.VoteRecordDetails#getLocation()}.
	 */
	@Test
	public final void testGetLocation() 
	{
		VoteRecordDetails vr1=DatabaseDataFixture.populateVoteRecord1().toVoteRecordDetails();
		assertEquals(vrd.getLocation(),vr1.getLocation());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.voteRecord.VoteRecordDetails#setLocation(java.lang.String)}.
	 */
	@Test
	public final void testSetLocation() 
	{
		String location="Union House";
		VoteRecordDetails vr1=new VoteRecordDetails();
		vr1.setLocation(location);
		assertEquals(location,vr1.getLocation());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.voteRecord.VoteRecordDetails#getQrCode()}.
	 */
	@Test
	public final void testGetQrCode() 
	{
		VoteRecordDetails vr1=DatabaseDataFixture.populateVoteRecord1().toVoteRecordDetails();
		assertEquals(vrd.getQrCode(),vr1.getQrCode());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.voteRecord.VoteRecordDetails#setQrCode(java.lang.String)}.
	 */
	@Test
	public final void testSetQrCode() 
	{
		String qrCode="other qrCode";
		VoteRecordDetails vr1=new VoteRecordDetails();
		assertNotEquals(qrCode,vr1.getQrCode());
		vr1.setQrCode(qrCode);
		assertEquals(qrCode,vr1.getQrCode());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.voteRecord.VoteRecordDetails#toString()}.
	 */
	@Test
	public final void testToString() 
	{
		assertNotNull("Not yet implemented",vrd.toString());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.voteRecord.VoteRecordDetails#equals(java.lang.Object)}.
	 */
	@Test
	public final void testEqualsObject()
	{
		VoteRecordDetails vrTest=null;
		assertNotEquals(vrTest,vrd);
		assertNotEquals(vrd,vrTest);
		String notElection="";
		assertNotEquals(vrd,notElection);
		vrTest=DatabaseDataFixture.populateVoteRecord1().toVoteRecordDetails();
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

		vrTest.setQrCode("another QrCode");
		assertNotEquals(vrd, vrTest);
		vrTest.setQrCode(null);
		assertNotEquals(vrd, vrTest);
		assertNotEquals(vrTest, vrd);
		vrTest.setQrCode(vrd.getQrCode());

		vrTest.setDate(54l);
		assertNotEquals(vrd, vrTest);
		vrTest.setDate(null);
		assertNotEquals(vrd, vrTest);
		assertNotEquals(vrTest, vrd);
		vrTest.setDate(vrd.getDate());
		vrTest.setElectionId(223l);
		assertNotEquals(vrd, vrTest);
		vrTest.setElectionId(null);
		assertNotEquals(vrd, vrTest);
		assertNotEquals(vrTest, vrd);
		vrTest.setElectionId(vrd.getElectionId());
		vrTest.setVoterId("test@email.com");
		assertNotEquals(vrd, vrTest);
		vrTest.setVoterId(null);
		assertNotEquals(vrd, vrTest);
		assertNotEquals(vrTest, vrd);
		vrTest.setVoterId(vrd.getVoterId());
	}

	/**
	 * Test method for {@link com.eulersbridge.iEngage.core.events.voteRecord.VoteRecordDetails#hashCode()}.
	 */
	@Test
	public final void testHashCode() 
	{
		VoteRecordDetails voteRecordDetailsTest=DatabaseDataFixture.populateVoteRecord1().toVoteRecordDetails();
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
		voteRecordDetailsTest.setLocation(null);
		assertNotEquals(vrd.hashCode(), voteRecordDetailsTest.hashCode());
		assertNotEquals(voteRecordDetailsTest.hashCode(), vrd.hashCode());
		voteRecordDetailsTest.setLocation(vrd.getLocation());

		voteRecordDetailsTest.setQrCode(null);
		assertNotEquals(vrd.hashCode(), voteRecordDetailsTest.hashCode());
		assertNotEquals(voteRecordDetailsTest.hashCode(), vrd.hashCode());
		voteRecordDetailsTest.setQrCode(vrd.getQrCode());

		voteRecordDetailsTest.setVoterId(null);
		assertNotEquals(vrd.hashCode(), voteRecordDetailsTest.hashCode());
		assertNotEquals(voteRecordDetailsTest.hashCode(), vrd.hashCode());
		voteRecordDetailsTest.setVoterId(vrd.getVoterId());
		voteRecordDetailsTest.setElectionId(null);
		assertNotEquals(vrd.hashCode(), voteRecordDetailsTest.hashCode());
		assertNotEquals(voteRecordDetailsTest.hashCode(), vrd.hashCode());
		voteRecordDetailsTest.setElectionId(vrd.getElectionId());
	}

}
