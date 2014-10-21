package com.eulersbridge.iEngage.database.domain;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eulersbridge.iEngage.database.domain.VerificationToken.VerificationTokenType;

public class VerificationTokenTest 
{
	User user=new User("info@eulersbridge.com", "Euler", "Info", "Female", "Australian", "1942", "test");
	int expirationtime=100;
	VerificationToken token;
	VerificationToken oldToken=new VerificationToken(VerificationTokenType.emailVerification,user, -10);

    private static Logger LOG = LoggerFactory.getLogger(VerificationTokenTest.class);

    
	@Before
	public void setUp() throws Exception 
	{
		token=new VerificationToken(VerificationTokenType.emailVerification,user, expirationtime);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testVerificationToken() 
	{
		VerificationToken token=new VerificationToken();
		assertNotNull("Token constructed is null",token);
	}

	@Test
	public void testVerificationTokenVerificationTokenTypeUserInt() 
	{
		VerificationToken token=new VerificationToken(VerificationTokenType.emailVerification,user, expirationtime);
		assertNotNull("Token constructed is null",token);
	}

	@Test
	public void testGetTokenType() 
	{
		VerificationTokenType gotTokenType=VerificationTokenType.emailVerification;
		assertEquals("",gotTokenType,VerificationTokenType.emailVerification);
	}

	@Test
	public void testGetNodeId() 
	{
		Long nodeId=token.getNodeId();
		assertNull("",nodeId);
	}

	@Test
	public void testSetNodeId() 
	{
		Long nodeId= new Long(2);
		token.setNodeId(nodeId);
		assertEquals("",nodeId,token.getNodeId());
	}

	@Test
	public void testIsVerified() 
	{
		boolean verified=token.isVerified();
		assertFalse("",verified);
	}

	@Test
	public void testSetVerified() 
	{
		token.setVerified(true);
		assertTrue("",token.isVerified());
	}

	@Test
	public void testGetExpiryDate() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("expiry Date - "+token.getExpiryDate());
		Long now=Calendar.getInstance().getTimeInMillis();
		assertTrue(token.getExpiryDate()>now);
		Long future=now+10000;
		assertTrue(token.getExpiryDate()>future);
		assertFalse(oldToken.getExpiryDate()>future);
	}

	@Test
	public void testGetToken() 
	{
		String vt=token.getToken();
		if (LOG.isDebugEnabled()) LOG.debug("token = "+vt);
		
        String encoded=new String(Base64.encodeBase64(token.getToken().getBytes()));
        if (LOG.isDebugEnabled()) LOG.debug("encoded token = "+encoded);
        byte[] decodedBytes=Base64.decodeBase64(encoded);
        String decoded=decodedBytes.toString();
        if (LOG.isDebugEnabled()) LOG.debug("decoded token = "+decoded);
	}

	@Test
	public void testHasExpired() 
	{
		boolean expired=token.hasExpired();
		assertFalse(token.hasExpired());
		assertTrue(oldToken.hasExpired());
		if (LOG.isDebugEnabled()) LOG.debug("expired = "+expired);
	}

}
