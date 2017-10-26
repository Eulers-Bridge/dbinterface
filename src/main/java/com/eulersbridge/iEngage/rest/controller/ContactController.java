/**
 *
 */
package com.eulersbridge.iEngage.rest.controller;

import com.eulersbridge.iEngage.core.beans.Util;
import com.eulersbridge.iEngage.core.events.*;
import com.eulersbridge.iEngage.core.services.interfacePack.ContactRequestService;
import com.eulersbridge.iEngage.core.services.interfacePack.UserService;
import com.eulersbridge.iEngage.rest.domain.ContactRequestDomain;

import com.eulersbridge.iEngage.rest.domain.UserProfile;
import com.eulersbridge.iEngage.rest.domain.WrappedDomainList;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.LongValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Greg Newitt
 */
@RestController
@RequestMapping(ControllerConstants.API_PREFIX)
public class ContactController {
  @Autowired
  UserService userService;
  @Autowired
  ContactRequestService contactRequestService;
//  @Autowired
//  NotificationService notificationService;

  private static Logger LOG = LoggerFactory.getLogger(ContactController.class);
  private EmailValidator emailValidator;
  private LongValidator longValidator;

  public ContactController() {
    if (LOG.isDebugEnabled()) LOG.debug("ContactController()");
    emailValidator = EmailValidator.getInstance();
    longValidator = LongValidator.getInstance();
  }

  @RequestMapping(method = RequestMethod.POST, value = ControllerConstants.USER_LABEL + "/{userId}" + ControllerConstants.CONTACT_REQUEST_LABEL + "/{targetEmail}")
  public @ResponseBody
  ResponseEntity<ContactRequestDomain> addContact(@PathVariable Long userId, @PathVariable String targetEmail) {
    String userEmail = Util.getUserEmailFromSession();
    if (!emailValidator.isValid(targetEmail))
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    RequestHandledEvent<ContactRequestDomain> res =
      contactRequestService.createContactRequest(userEmail, targetEmail);
    return res.toResponseEntity();
  }

  @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.USER_LABEL + "/{userId}" + ControllerConstants.CONTACT_REQUESTS_LABEL)
  public @ResponseBody
  ResponseEntity<WrappedDomainList<ContactRequestDomain>> findContactRequestsMade(@PathVariable Long userId) {
    String userEmail = Util.getUserEmailFromSession();
    RequestHandledEvent<WrappedDomainList<ContactRequestDomain>> res =
      contactRequestService.readContactRequestsMade(userEmail);
    return res.toResponseEntity();
  }

  @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.USER_LABEL + "/{userId}" + ControllerConstants.CONTACT_REQUESTS_LABEL + "/rec")
  public @ResponseBody
  ResponseEntity<WrappedDomainList<ContactRequestDomain>> findContactRequestsReceived(@PathVariable Long userId) {
    String userEmail = Util.getUserEmailFromSession();
    RequestHandledEvent<WrappedDomainList<ContactRequestDomain>> res =
      contactRequestService.readContactRequestsReceived(userEmail);
    return res.toResponseEntity();
  }

  @RequestMapping(method = RequestMethod.PUT, value = ControllerConstants.USER_LABEL + ControllerConstants.CONTACT_REQUEST_LABEL + "/{contactRequestId}/accept")
  public @ResponseBody
  ResponseEntity<ContactRequestDomain> acceptContact(@PathVariable Long contactRequestId) {
    String userEmail = Util.getUserEmailFromSession();
    RequestHandledEvent<ContactRequestDomain> res =
      contactRequestService.acceptContactRequest(userEmail, contactRequestId);
    return res.toResponseEntity();
  }

  @RequestMapping(method = RequestMethod.PUT, value = ControllerConstants.USER_LABEL + ControllerConstants.CONTACT_REQUEST_LABEL + "/{contactRequestId}/reject")
  public @ResponseBody
  ResponseEntity<ContactRequestDomain> rejectContact(@PathVariable Long contactRequestId) {
    String userEmail = Util.getUserEmailFromSession();
    RequestHandledEvent<ContactRequestDomain> res =
      contactRequestService.rejectContactRequest(userEmail, contactRequestId);
    return res.toResponseEntity();
  }


  @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.CONTACTS_LABEL)
  public @ResponseBody
  ResponseEntity<WrappedDomainList<UserProfile>> getFriendsList() {
    String userEmail = Util.getUserEmailFromSession();
    RequestHandledEvent<WrappedDomainList<UserProfile>> res = contactRequestService.readFriendsList(userEmail);
    return res.toResponseEntity();
  }

  @RequestMapping(method = RequestMethod.DELETE, value = ControllerConstants.CONTACT_LABEL + "/{friendEmail}")
  public @ResponseBody
  ResponseEntity deleteFriendship(@PathVariable String friendEmail) {
    String userEmail = Util.getUserEmailFromSession();
    RequestHandledEvent<WrappedDomainList<UserProfile>> res = contactRequestService.deleteFriend(userEmail, friendEmail);
    return res.toResponseEntity();
  }

  @RequestMapping(method = RequestMethod.DELETE, value = ControllerConstants.USER_LABEL + ControllerConstants.CONTACT_REQUEST_LABEL + "/{contactRequestId}")
  public @ResponseBody
  ResponseEntity revokeContactRequest(@PathVariable Long contactRequestId) {
    String userEmail = Util.getUserEmailFromSession();
    RequestHandledEvent<WrappedDomainList<UserProfile>> res = contactRequestService.revokeContactRequest(userEmail, contactRequestId);
    return res.toResponseEntity();
  }
}
