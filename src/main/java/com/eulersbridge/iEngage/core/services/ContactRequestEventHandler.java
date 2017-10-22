/**
 *
 */
package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.*;
import com.eulersbridge.iEngage.core.events.contactRequest.*;
import com.eulersbridge.iEngage.core.events.contacts.ContactDetails;
import com.eulersbridge.iEngage.core.services.interfacePack.ContactRequestService;
import com.eulersbridge.iEngage.database.domain.Contact;
import com.eulersbridge.iEngage.database.domain.ContactRequest;
import com.eulersbridge.iEngage.database.domain.Node;
import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.repository.ContactRequestRepository;
import com.eulersbridge.iEngage.database.repository.UserRepository;
import com.eulersbridge.iEngage.rest.domain.ContactRequestDomain;
import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Greg Newitt
 */
@Service
public class ContactRequestEventHandler implements ContactRequestService {
  private static Logger LOG = LoggerFactory.getLogger(ContactRequestEventHandler.class);

  private ContactRequestRepository contactRequestRepository;
  private UserRepository userRepository;
  private EmailValidator emailValidator;

  @Autowired
  public ContactRequestEventHandler(ContactRequestRepository contactRequestRepository, UserRepository userRepository) {
    this.contactRequestRepository = contactRequestRepository;
    this.userRepository = userRepository;
    emailValidator = EmailValidator.getInstance();
  }

  @Override
  public RequestHandledEvent<ContactRequestDomain> createContactRequest(String userEmail, String targetEmail) {
    if (!(emailValidator.isValid(userEmail) && emailValidator.isValid(targetEmail)))
      return RequestHandledEvent.badRequest();
    User user = userRepository.findByEmail(userEmail, 0);
    if (user == null)
      return RequestHandledEvent.userNotFound();
    User target = userRepository.findByEmail(targetEmail, 0);
    if (target == null)
      return RequestHandledEvent.targetNotFound();
    if (userRepository.isFriend(userEmail, targetEmail))
      return RequestHandledEvent.conflicted();
    List<ContactRequest> request = contactRequestRepository.findExistingRequest(user.getEmail(), target.getEmail());
    for (ContactRequest c : request) {
      if (c.getAccepted() == null)
        return RequestHandledEvent.conflicted();
    }
    ContactRequest newReq = new ContactRequest();
    newReq.setRequestDate(System.currentTimeMillis());
    newReq.setCreator(user);
    newReq.setTarget(target);
    newReq = contactRequestRepository.save(newReq);
    return new RequestHandledEvent<>(newReq.toDomain());
  }

  @Override
  public RequestHandledEvent<List<ContactRequestDomain>> readContactRequestsMade(String userEmail) {
    if (!emailValidator.isValid(userEmail))
      return RequestHandledEvent.badRequest();
    List<ContactRequest> requests = contactRequestRepository.findSentRequests(userEmail);
    List<ContactRequestDomain> domains = requests.stream()
      .map(req -> req.toDomain()).collect(Collectors.toList());
    return new RequestHandledEvent<>(domains);
  }

  @Override
  public RequestHandledEvent readContactRequestsReceived(String userEmail) {
    if (!emailValidator.isValid(userEmail))
      return RequestHandledEvent.badRequest();
    List<ContactRequest> requests = contactRequestRepository.findReceivedRequests(userEmail);
    List<ContactRequestDomain> domains = requests.stream()
      .map(req -> req.toDomain()).collect(Collectors.toList());
    return new RequestHandledEvent<>(domains);
  }

  //  /* (non-Javadoc)
//   * @see com.eulersbridge.iEngage.core.services.interfacePack.ContactRequestService#readContactRequest(com.eulersbridge.iEngage.core.events.contactRequest.ReadContactRequestEvent)
//   */
//  @Override
//  public ReadEvent readContactRequest(
//    ReadContactRequestEvent readContactRequestEvent) {
//    ContactRequest task = contactRequestRepository.findOne(readContactRequestEvent.getNodeId());
//    ReadEvent readTaskEvent;
//    if (task != null) {
//      readTaskEvent = new ContactRequestReadEvent(task.getNodeId(), task.toContactRequestDetails());
//    } else {
//      readTaskEvent = ContactRequestReadEvent.notFound(readContactRequestEvent.getNodeId());
//    }
//    return readTaskEvent;
//  }
//
//  /* (non-Javadoc)
//   * @see com.eulersbridge.iEngage.core.services.interfacePack.ContactRequestService#readContactRequestByUserIdContactNumber(com.eulersbridge.iEngage.core.events.contactRequest.ReadContactRequestEvent)
//   */
//  @Override
//  public ReadEvent readContactRequestByUserIdContactNumber(
//    ReadContactRequestEvent readContactRequestEvent) {
//    String contactInfo = readContactRequestEvent.getDetails().getContactDetails();
//    Long userId = readContactRequestEvent.getDetails().getUserId();
//    if (LOG.isDebugEnabled())
//      LOG.debug("Looking for Contact " + userId + " contactInfo " + contactInfo);
//    ContactRequest contactRequest = contactRequestRepository.findContactRequestByUserIdContactInfo(userId, contactInfo);
//    ReadEvent contactRequestReadEvent;
//    if (contactRequest != null) {
//      if (LOG.isDebugEnabled())
//        LOG.debug("Contact found." + contactRequest.getNodeId());
//      contactRequestReadEvent = new ContactRequestReadEvent(contactRequest.getNodeId(), contactRequest.toContactRequestDetails());
//    } else {
//      if (LOG.isDebugEnabled()) LOG.debug("Contact not found.");
//      contactRequestReadEvent = ContactRequestReadEvent.notFound(readContactRequestEvent.getNodeId());
//    }
//    return contactRequestReadEvent;
//  }
//
//  @Override
//  public UpdatedEvent acceptContactRequest(
//    AcceptContactRequestEvent acceptContactRequestEvent) {
//    Long contactRequestId = acceptContactRequestEvent.getNodeId();
//    if (LOG.isDebugEnabled())
//      LOG.debug("Looking for ContactRequest " + contactRequestId);
//    ContactRequest cr = contactRequestRepository.findOne(contactRequestId);
//    UpdatedEvent uEvt;
//    if ((cr != null) && (cr.getNodeId() != null) && (null == cr.getResponseDate())) {
//      EmailValidator emailValidator = EmailValidator.getInstance();
//      boolean isEmail = emailValidator.isValid(cr.getContactDetails());
//      User contactee;
//      Node contactor;
//      if (isEmail)
//        contactee = userRepository.findByEmail(cr.getContactDetails());
//      else
//        contactee = userRepository.findByContactNumber(cr.getContactDetails());
//      if (contactee != null) {
//        contactor = cr.getUser();
//        cr.setAccepted(true);
//        cr.setRejected(false);
//        cr.setResponseDate(Calendar.getInstance().getTimeInMillis());
//        Contact contact = userRepository.addContact(contactor.getNodeId(), contactee.getNodeId());
//        if ((contact != null) && (contact.getNodeId() != null)) {
//          ContactDetails cDets = contact.toContactDetails();
//          if (LOG.isDebugEnabled()) LOG.debug("contactDetails = " + cDets);
//          ContactRequest result = contactRequestRepository.save(cr, 0);
//          if (result != null)
//            uEvt = new UpdatedEvent(contactRequestId, cDets);
//            //TODO Should really be failed.
////		           	else uEvt=UpdatedEvent.notFound(null);
//          else uEvt = new UpdatedEvent(contactRequestId, cDets);
//          // Probably should remove the other contactRequest, if there is one.
//        } else {
//          //TODO Should really be failed.
//          uEvt = UpdatedEvent.notFound(null);
//        }
//      } else {
//        uEvt = UpdatedEvent.notFound(contactRequestId);
//      }
//    } else if ((null == cr) || (null == cr.getNodeId())) {
//      uEvt = UpdatedEvent.notFound(contactRequestId);
//    } else
//    //	if (cr.getResponseDate()!=null)
//    {
//      // TODO Should be something else to indicate CR has already been responded too.
//      Boolean rejected = cr.getRejected();
//      Boolean accepted = cr.getAccepted();
//      if ((rejected != null) && (true == rejected))
//        uEvt = UpdatedEvent.notFound(null);
//      else if ((accepted != null) && (true == accepted))
//        uEvt = UpdatedEvent.notFound(null);
//      else
//        uEvt = UpdatedEvent.notFound(null);
//
//    }
//    return uEvt;
//  }
//
//  @Override
//  public UpdatedEvent rejectContactRequest(
//    UpdateEvent rejectContactRequestEvent) {
//    UpdatedEvent uEvt;
//    if (rejectContactRequestEvent != null) {
//      Long contactRequestId = rejectContactRequestEvent.getNodeId();
//      if (LOG.isDebugEnabled())
//        LOG.debug("Looking for ContactRequest " + contactRequestId);
//      ContactRequest cr = contactRequestRepository.findOne(contactRequestId);
//      if ((cr != null) && (cr.getNodeId() != null) && (null == cr.getResponseDate())) {
//        // We have a contact request that has not been responded to.
//        EmailValidator emailValidator = EmailValidator.getInstance();
//        boolean isEmail = emailValidator.isValid(cr.getContactDetails());
//        User contactee;
//        if (isEmail)
//          contactee = userRepository.findByEmail(cr.getContactDetails());
//        else
//          contactee = userRepository.findByContactNumber(cr.getContactDetails());
//        if (contactee != null) {
//          cr.setAccepted(false);
//          cr.setRejected(true);
//          cr.setResponseDate(Calendar.getInstance().getTimeInMillis());
//          ContactRequest result = contactRequestRepository.save(cr, 0);
//          if (result != null)
//            uEvt = new UpdatedEvent(contactRequestId, cr.toContactRequestDetails());
//            //TODO Should really be failed.
////		           	else uEvt=UpdatedEvent.notFound(null);
//          else uEvt = UpdatedEvent.notFound(contactRequestId);
//          // Probably should remove the other contactRequest, if there is one.
//        } else {
//          uEvt = UpdatedEvent.notFound(contactRequestId);
//        }
//      } else if ((null == cr) || (null == cr.getNodeId())) {
//        uEvt = UpdatedEvent.notFound(contactRequestId);
//      } else
//      //	if (cr.getResponseDate()!=null)
//      {
//        // TODO Should be something else to indicate CR has already been responded too.
//        Boolean rejected = cr.getRejected();
//        Boolean accepted = cr.getAccepted();
//        if ((rejected != null) && (true == rejected))
//          uEvt = UpdatedEvent.notFound(null);
//        else if ((accepted != null) && (true == accepted))
//          uEvt = UpdatedEvent.notFound(null);
//        else
//          uEvt = UpdatedEvent.notFound(null);
//
//      }
//    } else {
//      uEvt = UpdatedEvent.notFound(null);
//    }
//    return uEvt;
//  }
//


}
