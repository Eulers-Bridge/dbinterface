package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.email.EmailConstants;
import org.apache.tomcat.util.codec.binary.Base64;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@NodeEntity
public class VerificationToken extends Node {

  private UUID token;
  private Long expiryDate;
  private String tokenType;
  private boolean verified = false;
  @Relationship(type = DatabaseDomainConstants.VERIFIED_BY_LABEL, direction = Relationship.UNDIRECTED)
//  @Fetch
  private Node user;

  private static Logger LOG = LoggerFactory.getLogger(VerificationToken.class);

  public VerificationToken() {

    this.token = UUID.randomUUID();
    this.expiryDate = calculateExpiryDate(EmailConstants.DEFAULT_EXPIRY_TIME_IN_MINS);
    if (LOG.isTraceEnabled())
      LOG.trace("Constructor(" + token + ',' + expiryDate.toString() + ',' + verified + ')');
  }

  public VerificationToken(VerificationTokenType tokenType, User user, int expirationTimeInMinutes) {

    this.token = UUID.randomUUID();
    if (LOG.isTraceEnabled())
      LOG.trace("Constructor(" + token + ',' + expirationTimeInMinutes + ',' + tokenType + ')');
    this.tokenType = tokenType.name();
    this.user = user;
    this.expiryDate = calculateExpiryDate(expirationTimeInMinutes);
    if (LOG.isTraceEnabled())
      LOG.trace("Constructor(" + token + ',' + expiryDate.toString() + ',' + tokenType + ',' + verified + ')');
  }

  public String getTokenType() {
    return tokenType;
  }

  public boolean isVerified() {
    return verified;
  }

  public void setVerified(boolean verified) {
    this.verified = verified;
  }

  public Long getExpiryDate() {
    return expiryDate;
  }

  public String getTokenString() {
    return token.toString();
  }

  public String getEncodedTokenString() {
    return convertVTokentoEncoded64URLString(this);
  }

  public UUID getToken() {
    return token;
  }

  public void setUser(Node user) {
    this.user = user;
  }

  public User getUser() {
    return (User) user;
  }

  private Long calculateExpiryDate(int expiryTimeInMinutes) {
    Long now = Calendar.getInstance().getTimeInMillis();
    Long expiryDate = now + (expiryTimeInMinutes * 60 * 1000);
    if (LOG.isDebugEnabled()) LOG.debug("now - " + expiryDate);
    if (LOG.isDebugEnabled()) LOG.debug("expiry Date - " + expiryDate);
    return expiryDate;
  }

  public enum VerificationTokenType {
    lostPassword, emailVerification, emailRegistration
  }

  public boolean hasExpired() {
    Date now = new Date();
    return (getExpiryDate() < now.getTime());
  }

  public String toString() {
    StringBuffer res = new StringBuffer("Token = ");
    res.append(token);
    res.append(" expiryDate = ");
    res.append(expiryDate);
    res.append("token type = ");
    res.append(tokenType);
    return res.toString();

  }

  public static String convertVTokentoEncoded64URLString(VerificationToken token) {

    byte[] encodeTokenBytes = Base64.encodeBase64URLSafe(token.toByteArray());
    String encoded = new String(encodeTokenBytes);
    return encoded;
  }

  public static UUID convertEncoded64URLStringtoUUID(String encodedToken) {
    UUID thing = VerificationToken.convertByteArrayToUUID(Base64.decodeBase64(encodedToken));
    return thing;
  }

  public byte[] toByteArray() {
    ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
    bb.putLong(token.getMostSignificantBits());
    bb.putLong(token.getLeastSignificantBits());
    byte[] tokenByteArray = bb.array();
    return tokenByteArray;
  }

  public static byte[] convertUUIDtoByteArray(UUID token) {
    if (LOG.isDebugEnabled()) LOG.debug("token = " + token);
    ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
    bb.putLong(token.getMostSignificantBits());
    bb.putLong(token.getLeastSignificantBits());
    byte[] tokenByteArray = bb.array();
    return tokenByteArray;
  }

  public static UUID convertByteArrayToUUID(byte[] byteArray) {
    UUID token;
    ByteBuffer bb = ByteBuffer.wrap(byteArray);
    long firstLong = bb.getLong();
    long secondLong = bb.getLong();
    token = new UUID(firstLong, secondLong);
    return token;
  }
}