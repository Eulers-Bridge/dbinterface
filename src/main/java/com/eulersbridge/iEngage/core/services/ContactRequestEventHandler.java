/**
 *
 */
package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.beans.Util;
import com.eulersbridge.iEngage.core.events.*;
import com.eulersbridge.iEngage.core.services.interfacePack.ContactRequestService;
import com.eulersbridge.iEngage.database.domain.Contact;
import com.eulersbridge.iEngage.database.domain.ContactRequest;
import com.eulersbridge.iEngage.database.domain.Node;
import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.repository.ContactRepository;
import com.eulersbridge.iEngage.database.repository.ContactRequestRepository;
import com.eulersbridge.iEngage.database.repository.UserRepository;
import com.eulersbridge.iEngage.rest.domain.ContactRequestDomain;
import com.eulersbridge.iEngage.rest.domain.UserProfile;
import com.eulersbridge.iEngage.rest.domain.WrappedDomainList;
import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
  private final Util util;

  @Autowired
  public ContactRequestEventHandler(ContactRequestRepository contactRequestRepository, UserRepository userRepository, ContactRepository contactRepo, Util util) {
    this.contactRequestRepository = contactRequestRepository;
    this.userRepository = userRepository;
    emailValidator = EmailValidator.getInstance();
    this.contactRepo = contactRepo;
    this.util = util;
  }

  @Override
  public RequestHandledEvent<ContactRequestDomain> createContactRequest(String userEmail, String targetEmail) {
    if (!(emailValidator.isValid(userEmail) && emailValidator.isValid(targetEmail)))
      return RequestHandledEvent.badRequest();
    if (userEmail.equals(targetEmail))
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

    util.sendNotification(Util.buildFriReqNotif(target, user));
    return new RequestHandledEvent<>(newReq.toDomain());
  }

  @Override
  public RequestHandledEvent<WrappedDomainList<ContactRequestDomain>> readContactRequestsMade(String userEmail) {
    if (!emailValidator.isValid(userEmail))
      return RequestHandledEvent.badRequest();
    List<ContactRequest> requests = contactRequestRepository.findSentRequests(userEmail);
    List<ContactRequestDomain> domains = requests.stream()
      .map(req -> req.toDomain()).collect(Collectors.toList());
    WrappedDomainList<ContactRequestDomain> wl =
      new WrappedDomainList<>(domains, domains.size(), 1);
    return new RequestHandledEvent<>(wl);
  }

  @Override
  public RequestHandledEvent<WrappedDomainList<ContactRequestDomain>> readContactRequestsReceived(String userEmail) {
    if (!emailValidator.isValid(userEmail))
      return RequestHandledEvent.badRequest();
    List<ContactRequest> requests = contactRequestRepository.findReceivedRequests(userEmail);
    List<ContactRequestDomain> domains = requests.stream()
      .map(req -> req.toDomain()).collect(Collectors.toList());
    WrappedDomainList<ContactRequestDomain> wl =
      new WrappedDomainList<>(domains, domains.size(), 1);
    return new RequestHandledEvent<>(wl);
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
    if (!isAlreadyFriend) {
      Contact contact = contactRepo.createUniqueFriendship(req.getCreator().getEmail(),
        req.getTarget().getEmail(), System.currentTimeMillis());
      if (contact == null)
        return RequestHandledEvent.failed();
    }

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

  @Override
  public RequestHandledEvent<WrappedDomainList<UserProfile>> readFriendsList(String userEmail) {
    if (!emailValidator.isValid(userEmail))
      return RequestHandledEvent.badRequest();
    User user = userRepository.findByEmail(userEmail, 0);
    if (user == null)
      return RequestHandledEvent.userNotFound();
    List<Contact> friends = userRepository.findByEmail(userEmail, 2).getFriends();
    if (friends == null)
      friends = new ArrayList<>();
    List<UserProfile> friendsProfiles = friends.stream()
      .map(u -> {
        User friend = u.getOpposedFriend(userEmail);
        return UserProfile.fromUserDetails(friend.toUserDetails());
      })
      .collect(Collectors.toList());
    WrappedDomainList<UserProfile> wl =
      new WrappedDomainList(friendsProfiles, friendsProfiles.size(), 1);
    return new RequestHandledEvent(wl);
  }

  @Override
  public RequestHandledEvent deleteFriend(String userEmail, String friendEmail) {
    if (!emailValidator.isValid(userEmail) || !emailValidator.isValid(friendEmail))
      return RequestHandledEvent.badRequest();
    Long delId = contactRepo.deleteFriendship(userEmail, friendEmail);
    if (delId == null)
      return RequestHandledEvent.targetNotFound();
    return RequestHandledEvent.success();
  }

  @Override
  public RequestHandledEvent revokeContactRequest(String userEmail, Long requestId) {
    if (!emailValidator.isValid(userEmail))
      return RequestHandledEvent.badRequest();
    Long delId = contactRequestRepository.revokeSentRequest(userEmail, requestId);
    if (delId == null)
      return RequestHandledEvent.targetNotFound();
    return RequestHandledEvent.success();
  }
}
