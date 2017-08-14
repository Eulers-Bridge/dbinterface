/**
 *
 */
package com.eulersbridge.iEngage.core.events;


/**
 * @author Greg Newitt
 */
public class LikedEvent {
  protected boolean entityFound = true;
  protected boolean userFound = true;
  protected boolean result = true;

  protected String userEmail;
  protected Long nodeId;

  /**
   * @return the userEmail
   */
  public String getUserEmail() {
    return userEmail;
  }

  /**
   * @param userEmail the userEmail to set
   */
  public void setUserEmail(String userEmail) {
    this.userEmail = userEmail;
  }

  /**
   * @return the nodeId
   */
  public Long getNodeId() {
    return nodeId;
  }

  /**
   * @param nodeId the nodeId to set
   */
  public void setNodeId(Long nodeId) {
    this.nodeId = nodeId;
  }

  public boolean isEntityFound() {
    return entityFound;
  }

  public boolean isUserFound() {
    return userFound;
  }

  public boolean isResultSuccess() {
    return result;
  }

  public LikedEvent(Long nodeId, String userId, boolean result) {
    this.nodeId = nodeId;
    this.userEmail = userId;
    this.result = result;
  }

  public LikedEvent(Long nodeId, String userId) {
    this(nodeId, userId, false);
  }

  public LikedEvent(String userId) {
    this(null, userId, false);
  }

  public static LikedEvent entityNotFound(Long nodeId, String userId) {
    LikedEvent ev = new LikedEvent(nodeId, userId);
    ev.entityFound = false;
    ev.result = false;
    return ev;
  }

  public static LikedEvent userNotFound(Long nodeId, String userId) {
    LikedEvent ev = new LikedEvent(nodeId, userId);
    ev.userFound = false;
    ev.result = false;
    return ev;
  }
}
