/**
 *
 */
package com.eulersbridge.iEngage.rest.controller;

import com.eulersbridge.iEngage.core.beans.Util;
import com.eulersbridge.iEngage.core.events.*;
import com.eulersbridge.iEngage.core.events.contactRequest.AcceptContactRequestEvent;
import com.eulersbridge.iEngage.core.events.contactRequest.ContactRequestDetails;
import com.eulersbridge.iEngage.core.events.contactRequest.ReadContactRequestEvent;
import com.eulersbridge.iEngage.core.events.contacts.ContactDetails;
import com.eulersbridge.iEngage.core.events.notifications.Message;
import com.eulersbridge.iEngage.core.events.users.ReadUserEvent;
import com.eulersbridge.iEngage.core.events.users.RequestReadUserEvent;
import com.eulersbridge.iEngage.core.events.users.UserDetails;
import com.eulersbridge.iEngage.core.services.interfacePack.ContactRequestService;
import com.eulersbridge.iEngage.core.services.interfacePack.NotificationService;
import com.eulersbridge.iEngage.core.services.interfacePack.UserService;
import com.eulersbridge.iEngage.database.domain.ContactRequest;
import com.eulersbridge.iEngage.rest.domain.Contact;
import com.eulersbridge.iEngage.rest.domain.ContactRequestDomain;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.LongValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
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

//  private ReadUserEvent getUser(String contactInfo) {
//    ReadUserEvent userEvent = null;
//
//    if (contactInfo != null) {
//      boolean isEmail = emailValidator.isValid(contactInfo);
//      String email = null;
//      if (isEmail) {
//        email = contactInfo;
//        userEvent = userService.readUserByContactEmail(new RequestReadUserEvent(email));
//      } else
//        userEvent = userService.readUserByContactNumber(new RequestReadUserEvent(contactInfo));
//    }
//    return userEvent;
//  }


  @RequestMapping(method = RequestMethod.PUT, value = ControllerConstants.USER_LABEL + "/{userId}" + ControllerConstants.CONTACT_LABEL + "/{targetEmail}")
  public @ResponseBody
  ResponseEntity<ContactRequestDomain> addContact(@PathVariable Long userId, @PathVariable String targetEmail) {
    String userEmail = Util.getUserEmailFromSession();
    if (!emailValidator.isValid(targetEmail))
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    RequestHandledEvent<ContactRequestDomain> res = contactRequestService.createContactRequest(userEmail, targetEmail);
    return res.toResponseEntity();
  }

  @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.USER_LABEL + "/{userId}" + ControllerConstants.CONTACT_REQUESTS_LABEL)
  public @ResponseBody
  ResponseEntity<List<ContactRequestDomain>> findContactRequestsMade(@PathVariable Long userId) {
    String userEmail = Util.getUserEmailFromSession();
    RequestHandledEvent<List<ContactRequestDomain>> res = contactRequestService.readContactRequestsMade(userEmail);
    return res.toResponseEntity();
  }

  @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.USER_LABEL + "/{userId}" + ControllerConstants.CONTACT_REQUESTS_LABEL + "/rec")
  public @ResponseBody
  ResponseEntity<List<ContactRequestDomain>> findContactRequestsReceived(@PathVariable Long userId){
    String userEmail = Util.getUserEmailFromSession();
    RequestHandledEvent<List<ContactRequestDomain>> res = contactRequestService.readContactRequestsReceived(userEmail);
    return res.toResponseEntity();
  }

//  @RequestMapping(method = RequestMethod.PUT, value = ControllerConstants.CONTACT_LABEL + "/{contactRequestId}")
//  public @ResponseBody
//  ResponseEntity<Contact> acceptContact(@PathVariable Long contactRequestId) {
//    if (LOG.isInfoEnabled())
//      LOG.info("Attempting to add contact from " + contactRequestId);
//
//    ResponseEntity<Contact> result;
//    Contact restContact;
//
//    ReadContactRequestEvent readContactRequestEvent = new ReadContactRequestEvent(contactRequestId);
//    ReadEvent rEvt = contactRequestService.readContactRequest(readContactRequestEvent);
//
//    if (rEvt.isEntityFound()) {
//      ContactRequestDetails crDets = (ContactRequestDetails) rEvt.getDetails();
//      if (LOG.isDebugEnabled())
//        LOG.debug("Contact Request details returned - " + crDets);
//
//
//      AcceptContactRequestEvent acceptContactRequestEvent = new AcceptContactRequestEvent(contactRequestId);
//      UpdatedEvent uEvt = contactRequestService.acceptContactRequest(acceptContactRequestEvent);
//      if (uEvt.isEntityFound()) {
//        if (LOG.isDebugEnabled()) LOG.debug("uEvt - " + uEvt);
//        ContactDetails cDets = (ContactDetails) uEvt.getDetails();
//        restContact = Contact.fromContactDetails(cDets);
//        result = new ResponseEntity<Contact>(restContact, HttpStatus.CREATED);
//        if (LOG.isDebugEnabled())
//          LOG.debug("Contact Request returned - " + restContact);
//        if (LOG.isDebugEnabled()) LOG.debug("Contact Details - " + cDets);
//
//        crDets.getContactDetails();
//        ReadUserEvent contacteeEvt = getUser(crDets.getContactDetails());
//        UserDetails contacteeDets = (UserDetails) contacteeEvt.getDetails();
//        String givenName = contacteeDets.getGivenName();
//        String familyName = contacteeDets.getFamilyName();
//        // Create a new contact request.
//        Message message = new Message(null, givenName + ' ' + familyName + " accepted your contact request.");
//        Notification notification = new Notification(crDets.getUserId(), NotificationConstants.MESSAGE, message);
//
//        CreateEvent createNotificationEvent = new CreateEvent(notification.toNotificationDetails());
//        CreatedEvent notificationCreatedEvent = notificationService.createNotification(createNotificationEvent);
//        if (notificationCreatedEvent.isFailed()) {
//          if (LOG.isDebugEnabled()) LOG.debug("Notification failed.");
//        }
//      } else {
//        result = new ResponseEntity<Contact>(HttpStatus.NOT_FOUND);
//      }
//    } else {
//      result = new ResponseEntity<Contact>(HttpStatus.NOT_FOUND);
//    }
//    return result;
//  }
//
//  @RequestMapping(method = RequestMethod.DELETE, value = ControllerConstants.CONTACT_LABEL + "/{contactRequestId}")
//  public @ResponseBody
//  ResponseEntity<ContactRequestDomain> rejectContact(@PathVariable Long contactRequestId) {
//    if (LOG.isInfoEnabled())
//      LOG.info("Attempting to reject contact request from " + contactRequestId);
//
//    ResponseEntity<ContactRequestDomain> result;
//    ContactRequestDomain restContact;
//
//    ReadContactRequestEvent readContactRequestEvent = new ReadContactRequestEvent(contactRequestId);
//    ReadEvent rEvt = contactRequestService.readContactRequest(readContactRequestEvent);
//
//    if (rEvt.isEntityFound()) {
//      ContactRequestDetails crDets = (ContactRequestDetails) rEvt.getDetails();
//      if (LOG.isDebugEnabled())
//        LOG.debug("Contact Request details returned - " + crDets);
//
//
//      UpdateEvent acceptContactRequestEvent = new UpdateEvent(contactRequestId, crDets);
//      UpdatedEvent uEvt = contactRequestService.rejectContactRequest(acceptContactRequestEvent);
//      if (uEvt.isEntityFound()) {
//        ContactRequestDetails cDets = (ContactRequestDetails) uEvt.getDetails();
//        restContact = ContactRequestDomain.fromContactRequestDetails(cDets);
//        result = new ResponseEntity<ContactRequestDomain>(restContact, HttpStatus.OK);
//        if (LOG.isDebugEnabled())
//          LOG.debug("Contact Request returned - " + restContact);
//      } else {
//        result = new ResponseEntity<ContactRequestDomain>(HttpStatus.NOT_FOUND);
//      }
//    } else {
//      result = new ResponseEntity<ContactRequestDomain>(HttpStatus.NOT_FOUND);
//    }
//    return result;
//  }
//


}
