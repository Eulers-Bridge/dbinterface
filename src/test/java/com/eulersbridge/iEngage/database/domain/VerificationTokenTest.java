package com.eulersbridge.iEngage.database.domain;

import static org.junit.Assert.*;

import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eulersbridge.iEngage.database.domain.VerificationToken.VerificationTokenType;

public class VerificationTokenTest 
{
	User user=new User("info@eulersbridge.com", "Euler", "Info", "Female", "Australian", "1942", "absolutely none", "test");
	VerificationTokenType tokenType=VerificationTokenType.emailVerification;
	int expirationtime=100;
	VerificationToken token=new VerificationToken(tokenType,user, expirationtime);

    private static Logger LOG = LoggerFactory.getLogger(VerificationTokenTest.class);

	@Before
	public void setUp() throws Exception 
	{
//		token=new VerificationToken(tokenType,user, expirationtime);

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testVerificationToken() 
	{
		VerificationToken token=new VerificationToken();
	}

	@Test
	public void testVerificationTokenVerificationTokenTypeUserInt() 
	{
		VerificationToken token=new VerificationToken(tokenType,user, expirationtime);
	}

	@Test
	public void testGetTokenType() 
	{
		String gotTokenType=token.getTokenType();
		assertEquals("",gotTokenType,this.tokenType.name());
	}

	@Test
	public void testGetNodeId() 
	{
		Long nodeId=token.getNodeId();
		assertNull("",nodeId);
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
		Long expDate=token.getExpiryDate();
		if (LOG.isDebugEnabled()) LOG.debug("expiry Date - "+expDate);
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
		if (LOG.isDebugEnabled()) LOG.debug("expired = "+expired);
	}

}
