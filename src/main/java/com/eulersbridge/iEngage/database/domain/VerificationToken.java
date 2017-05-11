package com.eulersbridge.iEngage.database.domain;

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
  private static final Logger LOG = LoggerFactory.getLogger(VerificationToken.class);

  private String token;
  private Long expiryDate;
  private String tokenType;
  private boolean verified = false;

  @Relationship(type = DataConstants.VERIFIED_BY_LABEL, direction = Relationship.OUTGOING)
  private Node user;

  public VerificationToken() {
  }

  public VerificationToken(VerificationTokenType tokenType, User user, int expirationTimeInMinutes) {
    this.token = UUID.randomUUID().toString();
    this.tokenType = tokenType.name();
    this.user = user.toNode();
    this.expiryDate = calculateExpiryDate(expirationTimeInMinutes);
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

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public void setExpiryDate(Long expiryDate) {
    this.expiryDate = expiryDate;
  }

  public void setTokenType(String tokenType) {
    this.tokenType = tokenType;
  }

  public void setUser(Node user) {
    this.user = user;
  }

  public User getUser$() {
    return (User) user;
  }

  public Node getUser() {
    return user;
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
    UUID token = UUID.fromString(this.token);
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