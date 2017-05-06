package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.database.domain.VerificationToken.VerificationTokenType;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Calendar;
import java.util.UUID;

import static org.junit.Assert.*;

public class VerificationTokenTest {
  User user = new User("info@eulersbridge.com", "Euler", "Info", "Female", "Australian", "1942", "test", "0400543432");
  int expirationtime = 100;
  VerificationToken token;
  VerificationToken oldToken = new VerificationToken(VerificationTokenType.emailVerification, user, -10);

  private static Logger LOG = LoggerFactory.getLogger(VerificationTokenTest.class);


  @Before
  public void setUp() throws Exception {
    token = new VerificationToken(VerificationTokenType.emailVerification, user, expirationtime);
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testVerificationToken() {
    VerificationToken token = new VerificationToken();
    assertNotNull("Token constructed is null", token);
  }

  @Test
  public void testVerificationTokenVerificationTokenTypeUserInt() {
    VerificationToken token = new VerificationToken(VerificationTokenType.emailVerification, user, expirationtime);
    assertNotNull("Token constructed is null", token);
  }

  @Test
  public void testGetTokenType() {
    VerificationTokenType gotTokenType = VerificationTokenType.emailVerification;
    assertEquals("", gotTokenType, VerificationTokenType.emailVerification);
  }

  @Test
  public void testGetNodeId() {
    Long nodeId = token.getNodeId();
    assertNull("", nodeId);
  }

  @Test
  public void testSetNodeId() {
    Long nodeId = new Long(2);
    token.setNodeId(nodeId);
    assertEquals("", nodeId, token.getNodeId());
  }

  @Test
  public void testIsVerified() {
    boolean verified = token.isVerified();
    assertFalse("", verified);
  }

  @Test
  public void testSetVerified() {
    token.setVerified(true);
    assertTrue("", token.isVerified());
  }

  @Test
  public void testGetExpiryDate() {
    if (LOG.isDebugEnabled())
      LOG.debug("expiry Date - " + token.getExpiryDate());
    Long now = Calendar.getInstance().getTimeInMillis();
    assertTrue(token.getExpiryDate() > now);
    Long future = now + 10000;
    assertTrue(token.getExpiryDate() > future);
    assertFalse(oldToken.getExpiryDate() > future);
  }

  @Test
  public void testGetToken() {
    Base64 encoder = new Base64(true);
    if (LOG.isDebugEnabled()) LOG.debug("token = " + token.getToken());
    assertNotNull(token.getToken());
    byte[] vtBytes = VerificationToken.convertUUIDtoByteArray(UUID.fromString(token.getToken()));

    if (LOG.isDebugEnabled())
      LOG.debug("vtBytes - " + Arrays.toString(vtBytes));
    byte[] byteArray = Base64.encodeBase64URLSafe(vtBytes);
    String encoded = Arrays.toString(byteArray);
    if (LOG.isDebugEnabled()) LOG.debug("encoded byte array = " + encoded);
    encoded = new String(byteArray);
    assertEquals(encoded, VerificationToken.convertVTokentoEncoded64URLString(token));
    if (LOG.isDebugEnabled()) LOG.debug("encoded token = " + encoded);
    byte[] decodedBytes = Base64.decodeBase64(encoded);
    if (LOG.isDebugEnabled())
      LOG.debug("decodedBytes - " + Arrays.toString(decodedBytes));
    decodedBytes = Base64.decodeBase64(byteArray);
    if (LOG.isDebugEnabled())
      LOG.debug("decodedBytes - " + Arrays.toString(decodedBytes));
    UUID thing = VerificationToken.convertByteArrayToUUID(decodedBytes);

    assertEquals(thing.toString(), token.getToken());
    assertEquals(thing, VerificationToken.convertEncoded64URLStringtoUUID(encoded));
    if (LOG.isDebugEnabled()) LOG.debug("token = " + thing);

    encoded = encoder.encodeAsString(VerificationToken.convertUUIDtoByteArray(UUID.fromString(token.getToken())));
    if (LOG.isDebugEnabled()) LOG.debug("encoded token = " + encoded);
    decodedBytes = encoder.decode(encoded);
    if (LOG.isDebugEnabled())
      LOG.debug("decodedBytes - " + Arrays.toString(decodedBytes));
    thing = VerificationToken.convertByteArrayToUUID(decodedBytes);
    if (LOG.isDebugEnabled()) LOG.debug("token = " + thing);
    assertEquals(thing.toString(), token.getToken());
  }

  @Test
  public void testGetTokenString() {
    String vt = token.getTokenString();
    assertEquals(vt, token.getToken().toString());
  }

  @Test
  public void testGetEncodedTokenString() {
    String vt = token.getEncodedTokenString();
    if (LOG.isDebugEnabled()) LOG.debug("encoded token = " + vt);
    String encoded = VerificationToken.convertVTokentoEncoded64URLString(token);
    if (LOG.isDebugEnabled()) LOG.debug("encoded token = " + encoded);

    assertEquals(encoded, vt);
  }

  @Test
  public void testHasExpired() {
    boolean expired = token.hasExpired();
    assertFalse(token.hasExpired());
    assertTrue(oldToken.hasExpired());
    if (LOG.isDebugEnabled()) LOG.debug("expired = " + expired);
  }

}
