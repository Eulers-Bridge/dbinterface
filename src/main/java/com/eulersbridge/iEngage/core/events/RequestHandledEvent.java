package com.eulersbridge.iEngage.core.events;

/**
 * @author Yikai Gong
 */

public class RequestHandledEvent<T> {

  private Boolean success = true;

  private Boolean userNotFound = false;
  private Boolean badRequest = false;

  private T responseEntity;

  public RequestHandledEvent() {
    success = true;
  }

  public RequestHandledEvent(Boolean success) {
    this.success = success;
  }

  public RequestHandledEvent(T responseEntity) {
    this();
    this.responseEntity = responseEntity;
  }

  public static RequestHandledEvent userNotFound() {
    RequestHandledEvent r = new RequestHandledEvent(false);
    r.setUserNotFound(Boolean.TRUE);
    return r;
  }

  public static RequestHandledEvent badRequest() {
    RequestHandledEvent r = new RequestHandledEvent(false);
    r.setBadRequest(Boolean.TRUE);
    return r;
  }

  public Boolean getSuccess() {
    return success;
  }

  public void setSuccess(Boolean success) {
    this.success = success;
  }

  public Boolean getUserNotFound() {
    return userNotFound;
  }

  public void setUserNotFound(Boolean userNotFound) {
    this.userNotFound = userNotFound;
  }

  public Boolean getBadRequest() {
    return badRequest;
  }

  public void setBadRequest(Boolean badRequest) {
    this.badRequest = badRequest;
  }

  public T getResponseEntity() {
    return responseEntity;
  }

  public void setResponseEntity(T responseEntity) {
    this.responseEntity = responseEntity;
  }
}
