/**
 *
 */
package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.*;
import com.eulersbridge.iEngage.core.events.notifications.NotificationDetails;
import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.domain.notifications.Notification;
import com.eulersbridge.iEngage.database.repository.ContactRequestRepository;
import com.eulersbridge.iEngage.database.repository.NotificationRepository;
import com.eulersbridge.iEngage.database.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.neo4j.repository.GraphRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author Greg Newitt
 */
public class NotificationEventHandler implements NotificationService {
  static Logger LOG = LoggerFactory.getLogger(NotificationEventHandler.class);

  NotificationRepository notificationRepository;
  UserRepository userRepository;
  ContactRequestRepository contactRequestRepository;
  HashMap<String, GraphRepository<?>> repos = new HashMap<>();

  public NotificationEventHandler(NotificationRepository notificationRepository, UserRepository userRepository, ContactRequestRepository contactRequestRepository) {
    this.notificationRepository = notificationRepository;
    this.userRepository = userRepository;
    this.contactRequestRepository = contactRequestRepository;
    if (LOG.isDebugEnabled())
      LOG.debug("User Repo name - " + UserRepository.class.getSimpleName());
    repos.put(NotificationRepository.class.getSimpleName(), notificationRepository);
    repos.put(UserRepository.class.getSimpleName(), userRepository);
    repos.put(ContactRequestRepository.class.getSimpleName(), contactRequestRepository);
  }

  @Override
  public CreatedEvent createNotification(
    CreateEvent createNotificationEvent) {
    Details dets = createNotificationEvent.getDetails();
    NotificationDetails nDets = (NotificationDetails) dets;

    if (LOG.isDebugEnabled()) {
      LOG.debug("Notification details - " + nDets);
    }

    Notification notif = Notification.fromNotificationDetails(nDets);
    if (LOG.isDebugEnabled())
      LOG.debug("Notification - " + notif + " className - " + notif.getClass().getSimpleName());
    Boolean check = notif.setupForSave(repos);
    if (LOG.isDebugEnabled()) LOG.debug("Check - " + check);
    Notification result = null;
    if (check) {
      result = notificationRepository.save(notif);
    }
    if (LOG.isDebugEnabled()) LOG.debug("Notification - " + notif);
    if (LOG.isDebugEnabled()) LOG.debug("Result - " + result);

    CreatedEvent evt;
    if (result != null)
      evt = new CreatedEvent(result.toNotificationDetails());
    else
      evt = CreatedEvent.failed(dets);
    return evt;
  }

  @Override
  public ReadEvent readNotification(
    RequestReadEvent requestReadNotificationEvent) {
    Long nodeId = requestReadNotificationEvent.getNodeId();
    Notification notification = notificationRepository.findOne(nodeId);

    ReadEvent readNotificationEvent;
    if (notification != null)
      readNotificationEvent = new ReadEvent(notification.getNodeId(), notification.toNotificationDetails());
    else
      readNotificationEvent = ReadEvent.notFound(requestReadNotificationEvent.getNodeId());

    return readNotificationEvent;
  }

  @Override
  public AllReadEvent readNotifications(ReadAllEvent readNotificationsEvent,
                                        Direction sortDirection, int pageNumber, int pageLength) {
    Long ownerId = readNotificationsEvent.getParentId();
    Page<Notification> notifications = null;
    ArrayList<Details> dets = new ArrayList<Details>();
    AllReadEvent nare = null;

    if (LOG.isDebugEnabled()) LOG.debug("OwnerId " + ownerId);
    Pageable pageable = new PageRequest(pageNumber, pageLength, sortDirection, "timestamp");
    notifications = notificationRepository.findByUser(ownerId, pageable);
    if (notifications != null) {
      if (LOG.isDebugEnabled())
        LOG.debug("Total elements = " + notifications.getTotalElements() + " total pages =" + notifications.getTotalPages());
      Iterator<Notification> iter = notifications.iterator();
      while (iter.hasNext()) {
        Notification na = iter.next();
        if (LOG.isTraceEnabled())
          LOG.trace("Converting to details - " + na.getType());
        NotificationDetails det = na.toNotificationDetails();
        dets.add(det);
      }
      if (0 == dets.size()) {
        // Need to check if we actually found parentId.
        User user = userRepository.findOne(ownerId);
        if ((null == user) ||
          ((null == user.getGivenName()) || ((null == user.getFamilyName()) && (null == user.getEmail()) && (null == user.getInstitution())))) {
          if (LOG.isDebugEnabled())
            LOG.debug("Null or null properties returned by findOne(UserId)");
          nare = AllReadEvent.notFound(ownerId);
        } else {
          nare = new AllReadEvent(ownerId, dets, notifications.getTotalElements(), notifications.getTotalPages());
        }
      } else {
        nare = new AllReadEvent(ownerId, dets, notifications.getTotalElements(), notifications.getTotalPages());
      }
    } else {
      if (LOG.isDebugEnabled())
        LOG.debug("Null returned by findByInstitutionId");
      nare = AllReadEvent.notFound(ownerId);
    }
    return nare;
  }

  @Override
  public DeletedEvent deleteNotification(DeleteEvent deleteNotificationEvent) {
    if (LOG.isDebugEnabled())
      LOG.debug("Entered deleteNotification Event= " + deleteNotificationEvent);

    Long nodeId = deleteNotificationEvent.getNodeId();
    DeletedEvent evt;

    Notification notification = notificationRepository.findOne(nodeId);
    if (null == notification)
      evt = DeletedEvent.notFound(nodeId);
    else {
      notificationRepository.delete(notification);
      evt = new DeletedEvent(nodeId);
    }
    return evt;
  }

  @Override
  public UpdatedEvent updateNotification(UpdateEvent updateNotificationEvent) {
    UpdatedEvent evt;
    if (updateNotificationEvent != null) {
      Long nodeId = updateNotificationEvent.getNodeId();
      if (LOG.isDebugEnabled()) LOG.debug("notificationId is " + nodeId);
      Details details = updateNotificationEvent.getDetails();
      NotificationDetails notificationDetails = (NotificationDetails) details;
      Notification notification = Notification.fromNotificationDetails(notificationDetails);
      Notification notificationOld = notificationRepository.findOne(nodeId);

      if (notificationOld == null) {
        if (LOG.isDebugEnabled())
          LOG.debug("notification entity not found " + nodeId);
        evt = UpdatedEvent.notFound(nodeId);
      } else {
        Notification result = notificationRepository.save(notification);
        if (result != null) {
          if (LOG.isDebugEnabled())
            LOG.debug("updated successfully" + result.getNodeId());
          evt = new UpdatedEvent(result.getNodeId(), result.toNotificationDetails());
        } else
          evt = UpdatedEvent.notFound(nodeId);
      }

    } else
      evt = UpdatedEvent.notFound(null);
    return evt;

  }

}
