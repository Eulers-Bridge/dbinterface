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
import com.eulersbridge.iEngage.database.repository.ContactRepository;
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

  private final ContactRequestRepository contactRequestRepository;
  private final UserRepository userRepository;
  private final EmailValidator emailValidator;
  private final ContactRepository contactRepo;

  @Autowired
  public ContactRequestEventHandler(ContactRequestRepository contactRequestRepository, UserRepository userRepository, ContactRepository contactRepo) {
    this.contactRequestRepository = contactRequestRepository;
    this.userRepository = userRepository;
    emailValidator = EmailValidator.getInstance();
    this.contactRepo = contactRepo;
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
    newReq = contactRequestRepository.findExistingRequest(newReq.getId());
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
  public RequestHandledEvent<List<ContactRequestDomain>> readContactRequestsReceived(String userEmail) {
    if (!emailValidator.isValid(userEmail))
      return RequestHandledEvent.badRequest();
    List<ContactRequest> requests = contactRequestRepository.findReceivedRequests(userEmail);
    List<ContactRequestDomain> domains = requests.stream()
      .map(req -> req.toDomain()).collect(Collectors.toList());
    return new RequestHandledEvent<>(domains);
  }

  @Override
  public RequestHandledEvent<ContactRequestDomain> acceptContactRequest(String userEmail, Long requestId) {
    if (!emailValidator.isValid(userEmail))
      return RequestHandledEvent.badRequest();
    ContactRequest req = contactRequestRepository.findExistingRequest(requestId);
    if (req == null || req.getCreator() == null || req.getTarget() == null)
      return RequestHandledEvent.targetNotFound();
    if (!userEmail.equals(req.getTarget().getEmail()))
      return RequestHandledEvent.notAllowed();
    if (req.getAccepted() != null || req.getResponseDate() != null)
      return RequestHandledEvent.canNotModiry();

    boolean isAlreadyFriend =
      userRepository.isFriend(req.getCreator().getEmail(), req.getTarget().getEmail());
    if (isAlreadyFriend)
      return RequestHandledEvent.conflicted();
    Contact contact = new Contact();
    contact.setContactor(req.getCreator());
    contact.setContactee(req.getTarget());
    contact.setTimestamp(System.currentTimeMillis());
    contact = contactRepo.save(contact);
    if (contact == null)
      return RequestHandledEvent.failed();

    req.setAccepted(true);
    req.setResponseDate(System.currentTimeMillis());
    req = contactRequestRepository.save(req);
    if (req == null)
      return RequestHandledEvent.failed();
    return new RequestHandledEvent<>(req.toDomain());
  }

  @Override
  public RequestHandledEvent rejectContactRequest(String userEmail, Long requestId) {
    if (!emailValidator.isValid(userEmail))
      return RequestHandledEvent.badRequest();
    ContactRequest req = contactRequestRepository.findExistingRequest(requestId);
    if (req == null || req.getCreator() == null || req.getTarget() == null)
      return RequestHandledEvent.targetNotFound();
    if (!userEmail.equals(req.getTarget().getEmail()))
      return RequestHandledEvent.notAllowed();
    if (req.getAccepted() != null || req.getResponseDate() != null)
      return RequestHandledEvent.canNotModiry();

    req.setAccepted(false);
    req.setResponseDate(System.currentTimeMillis());
    req = contactRequestRepository.save(req);
    if (req == null)
      return RequestHandledEvent.failed();
    return new RequestHandledEvent<>(req.toDomain());
  }

  //  /* (non-Javadoc)
//   * @see com.eulersbridge.iEngage.core.services.interfacePack.ContactRequestService#readContactRequest(com.eulersbridge.iEngage.core.events.contactRequest.ReadContactRequestEvent)
//   */
//  @Override
//  public ReadEvent readContactRequest(
//    ReadContactRequestEvent readContactRequestEvent) {
//    ContactRequest task = contactRequestRepository.findOne(readContactRequestEvent.getId());
//    ReadEvent readTaskEvent;
//    if (task != null) {
//      readTaskEvent = new ContactRequestReadEvent(task.getId(), task.toContactRequestDetails());
//    } else {
//      readTaskEvent = ContactRequestReadEvent.notFound(readContactRequestEvent.getId());
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
//        LOG.debug("Contact found." + contactRequest.getId());
//      contactRequestReadEvent = new ContactRequestReadEvent(contactRequest.getId(), contactRequest.toContactRequestDetails());
//    } else {
//      if (LOG.isDebugEnabled()) LOG.debug("Contact not found.");
//      contactRequestReadEvent = ContactRequestReadEvent.notFound(readContactRequestEvent.getId());
//    }
//    return contactRequestReadEvent;
//  }
//



//


}
