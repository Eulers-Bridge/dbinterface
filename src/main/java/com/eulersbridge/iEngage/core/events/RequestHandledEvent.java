package com.eulersbridge.iEngage.core.events;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author Yikai Gong
 */

public class RequestHandledEvent<T> {

  private Boolean success = true;

  private Boolean userNotFound = false;
  private Boolean targetNotFound = false;
  private Boolean badRequest = false;
  private Boolean notAllowed = false;
  private Boolean premissionExpired = false;
  private Boolean canNotModify = false;
  private Boolean conflicted = false;
  HttpStatus expectedHttpStatus = HttpStatus.OK;

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

  public RequestHandledEvent(T responseEntitym, HttpStatus expectedHttpStatus) {
    this();
    this.responseEntity = responseEntity;
    this.expectedHttpStatus = expectedHttpStatus;
  }

  public static RequestHandledEvent success(){
    return new RequestHandledEvent(true);
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

  public static RequestHandledEvent targetNotFound(){
    RequestHandledEvent r = new RequestHandledEvent(false);
    r.setTargetNotFound(Boolean.TRUE);
    return r;
  }

  public static RequestHandledEvent failed(){
    RequestHandledEvent r = new RequestHandledEvent(false);
    return r;
  }

  public static RequestHandledEvent notAllowed(){
    RequestHandledEvent r = new RequestHandledEvent(false);
    r.setNotAllowed(true);
    return r;
  }

  public static RequestHandledEvent premissionExpired(){
    RequestHandledEvent r = new RequestHandledEvent(false);
    r.setPremissionExpired(true);
    return r;
  }

  public static RequestHandledEvent canNotModiry(){
    RequestHandledEvent r = new RequestHandledEvent(false);
    r.setCanNotModify(true);
    return r;
  }

  public static RequestHandledEvent conflicted(){
    RequestHandledEvent r = new RequestHandledEvent(false);
    r.setConflicted(true);
    return r;
  }

  public ResponseEntity<T> toResponseEntity(){
    if(this.getUserNotFound() || this.getTargetNotFound())
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    if(this.getConflicted())
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    if(this.getBadRequest())
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    if(this.getPremissionExpired())
      return new ResponseEntity<>(HttpStatus.GONE);
    if(this.getCanNotModify())
      return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    if(this.getNotAllowed())
      return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    if(!this.getSuccess())
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    return new ResponseEntity<>(this.getResponseEntity(), expectedHttpStatus);
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

  public Boolean getNotAllowed() {
    return notAllowed;
  }

  public void setNotAllowed(Boolean notAllowed) {
    this.notAllowed = notAllowed;
  }

  public Boolean getPremissionExpired() {
    return premissionExpired;
  }

  public void setPremissionExpired(Boolean premissionExpired) {
    this.premissionExpired = premissionExpired;
  }

  public Boolean getTargetNotFound() {
    return targetNotFound;
  }

  public void setTargetNotFound(Boolean targetNotFound) {
    this.targetNotFound = targetNotFound;
  }

  public Boolean getCanNotModify() {
    return canNotModify;
  }

  public void setCanNotModify(Boolean canNotModify) {
    this.canNotModify = canNotModify;
  }

  public Boolean getConflicted() {
    return conflicted;
  }

  public void setConflicted(Boolean conflicted) {
    this.conflicted = conflicted;
  }
}
