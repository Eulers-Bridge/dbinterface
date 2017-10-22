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

  private ReadUserEvent getUser(String contactInfo) {
    ReadUserEvent userEvent = null;

    if (contactInfo != null) {
      boolean isEmail = emailValidator.isValid(contactInfo);
      String email = null;
      if (isEmail) {
        email = contactInfo;
        userEvent = userService.readUserByContactEmail(new RequestReadUserEvent(email));
      } else
        userEvent = userService.readUserByContactNumber(new RequestReadUserEvent(contactInfo));
    }
    return userEvent;
  }


  @RequestMapping(method = RequestMethod.PUT, value = ControllerConstants.USER_LABEL + "/{userId}" + ControllerConstants.CONTACT_LABEL + "/{targetEmail}")
  public @ResponseBody
  ResponseEntity<ContactRequestDomain> addContact(@PathVariable Long userId, @PathVariable String targetEmail) {
    String userEmail = Util.getUserEmailFromSession();
    if(!emailValidator.isValid(targetEmail))
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    RequestHandledEvent<ContactRequestDomain> res = contactRequestService.createContactRequest(userEmail, targetEmail);
    if(res.getUserNotFound() || res.getTargetNotFound())
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    if(res.getCanNotModify())
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    if(res.getBadRequest())
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    if(!res.getSuccess())
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    return new ResponseEntity<ContactRequestDomain>(res.getResponseEntity(), HttpStatus.OK);
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
//  /**
//   * Is passed all the necessary data to read contactRequests received from the database. The
//   * request must be a GET with the userId of the receiver presented as the final
//   * portion of the URL.
//   * <p/>
//   * This method will return the contactRequests received from the database.
//   *
//   * @param userId the userId whose contactRequests are to be read.
//   * @return the contactRequests.
//   */
//  @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.CONTACT_REQUESTS_LABEL
//    + "/{userId}")
//  public @ResponseBody
//  ResponseEntity<FindsParent> findContactRequestsReceived(
//    @PathVariable(value = "") Long userId,
//    @RequestParam(value = "direction", required = false, defaultValue = ControllerConstants.DIRECTION) String direction,
//    @RequestParam(value = "page", required = false, defaultValue = ControllerConstants.PAGE_NUMBER) String page,
//    @RequestParam(value = "pageSize", required = false, defaultValue = ControllerConstants.PAGE_LENGTH) String pageSize) {
//    int pageNumber = 0;
//    int pageLength = 10;
//    pageNumber = Integer.parseInt(page);
//    pageLength = Integer.parseInt(pageSize);
//    ResponseEntity<FindsParent> response;
//    if (LOG.isInfoEnabled())
//      LOG.info("Attempting to retrieve contactRequests for user "
//        + userId + '.');
//
//    Direction sortDirection = Direction.DESC;
//    if (direction.equalsIgnoreCase("asc")) sortDirection = Direction.ASC;
//    AllReadEvent contactRequestEvent = contactRequestService.readContactRequestsReceived(
//      new ReadAllEvent(userId), sortDirection,
//      pageNumber, pageLength);
//
//    if (!contactRequestEvent.isEntityFound()) {
//      response = new ResponseEntity<FindsParent>(HttpStatus.NOT_FOUND);
//    } else {
//      Iterator<ContactRequestDomain> contactRequests = ContactRequestDomain
//        .toContactRequestsIterator(contactRequestEvent.getDetails().iterator());
//      FindsParent theContactRequests = FindsParent.fromArticlesIterator(contactRequests, contactRequestEvent.getTotalItems(), contactRequestEvent.getTotalPages());
//      response = new ResponseEntity<FindsParent>(theContactRequests, HttpStatus.OK);
//    }
//    return response;
//  }


//  @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.USER_LABEL + "/{userId}" + ControllerConstants.CONTACT_REQUESTS_LABEL)
//  public @ResponseBody
//  ResponseEntity<FindsParent> findContactRequestsMade(
//    @PathVariable(value = "") Long userId,
//    @RequestParam(value = "direction", required = false, defaultValue = ControllerConstants.DIRECTION) String direction,
//    @RequestParam(value = "page", required = false, defaultValue = ControllerConstants.PAGE_NUMBER) String page,
//    @RequestParam(value = "pageSize", required = false, defaultValue = ControllerConstants.PAGE_LENGTH) String pageSize) {
//    int pageNumber = 0;
//    int pageLength = 10;
//    pageNumber = Integer.parseInt(page);
//    pageLength = Integer.parseInt(pageSize);
//    ResponseEntity<FindsParent> response;
//    if (LOG.isInfoEnabled())
//      LOG.info("Attempting to retrieve contactRequests for user "
//        + userId + '.');
//
//    Direction sortDirection = Direction.DESC;
//    if (direction.equalsIgnoreCase("asc")) sortDirection = Direction.ASC;
//    AllReadEvent contactRequestEvent = contactRequestService.readContactRequestsMade(
//      new ReadAllEvent(userId), sortDirection,
//      pageNumber, pageLength);
//
//    if (!contactRequestEvent.isEntityFound()) {
//      response = new ResponseEntity<FindsParent>(HttpStatus.NOT_FOUND);
//    } else {
//      Iterator<ContactRequestDomain> contactRequests = ContactRequestDomain
//        .toContactRequestsIterator(contactRequestEvent.getDetails().iterator());
//      FindsParent theContactRequests = FindsParent.fromArticlesIterator(contactRequests, contactRequestEvent.getTotalItems(), contactRequestEvent.getTotalPages());
//      response = new ResponseEntity<FindsParent>(theContactRequests, HttpStatus.OK);
//    }
//    return response;
//  }

}
