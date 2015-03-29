/**
 * 
 */
package com.eulersbridge.iEngage.core.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.eulersbridge.iEngage.core.events.AllReadEvent;
import com.eulersbridge.iEngage.core.events.CreateEvent;
import com.eulersbridge.iEngage.core.events.CreatedEvent;
import com.eulersbridge.iEngage.core.events.DeleteEvent;
import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.Details;
import com.eulersbridge.iEngage.core.events.ReadAllEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.RequestReadEvent;
import com.eulersbridge.iEngage.core.events.UpdateEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.notifications.NotificationDetails;
import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.domain.notifications.Notification;
import com.eulersbridge.iEngage.database.repository.ContactRequestRepository;
import com.eulersbridge.iEngage.database.repository.NotificationRepository;
import com.eulersbridge.iEngage.database.repository.UserRepository;

/**
 * @author Greg Newitt
 *
 */
public class NotificationEventHandler implements NotificationService
{
    static Logger LOG = LoggerFactory.getLogger(NotificationEventHandler.class);

    NotificationRepository notificationRepository;
    UserRepository userRepository;
    ContactRequestRepository contactRequestRepository;
    HashMap repos=new HashMap<String,GraphRepository<?>>();
    
	public NotificationEventHandler(NotificationRepository notificationRepository, UserRepository userRepository, ContactRequestRepository contactRequestRepository)
	{
		this.notificationRepository=notificationRepository;
		this.userRepository=userRepository;
		this.contactRequestRepository=contactRequestRepository;
		if (LOG.isDebugEnabled()) LOG.debug("User Repo name - "+UserRepository.class.getSimpleName());
		repos.put(notificationRepository.getClass().getSimpleName(), notificationRepository);
		repos.put(UserRepository.class.getSimpleName(), userRepository);
		repos.put(ContactRequestRepository.class.getSimpleName(), contactRequestRepository);
	}

	/* (non-Javadoc)
	 * @see com.eulersbridge.iEngage.core.services.NotificationService#createNotification(com.eulersbridge.iEngage.core.events.notifications.CreateNotificationEvent)
	 */
	@Override
	public CreatedEvent createNotification(
			CreateEvent createNotificationEvent)
	{
		Details dets=createNotificationEvent.getDetails();
		NotificationDetails nDets=(NotificationDetails)dets;
		
		if (LOG.isDebugEnabled())
		{
			LOG.debug("Notification details - "+nDets);
		}
		
		Notification notif=Notification.fromNotificationDetails(nDets);
		if (LOG.isDebugEnabled()) LOG.debug("Notification - "+notif+" className - "+notif.getClass().getSimpleName());
		Boolean check=notif.setupForSave(repos);
		if (LOG.isDebugEnabled()) LOG.debug("Check - "+check);
		Notification result=null;
		if (check)
		{
			result=notificationRepository.save(notif);
		}
		if (LOG.isDebugEnabled()) LOG.debug("Notification - "+notif);
		if (LOG.isDebugEnabled()) LOG.debug("Result - "+result);

		CreatedEvent evt=new CreatedEvent(result.toNotificationDetails());
		return evt;
	}

	@Override
	public ReadEvent readNotification(
			RequestReadEvent requestReadNotificationEvent)
	{
		Long nodeId=requestReadNotificationEvent.getNodeId();
		// TODO Auto-generated method stub
		ReadEvent evt=new ReadEvent(nodeId);
		return evt;
	}

	@Override
	public AllReadEvent readNotifications(ReadAllEvent readNotificationsEvent,
			Direction sortDirection, int pageNumber, int pageLength)
	{
		Long ownerId=readNotificationsEvent.getParentId();
		Page <Notification>notifications=null;
		ArrayList<Details> dets=new ArrayList<Details>();
		AllReadEvent nare=null;

		if (LOG.isDebugEnabled()) LOG.debug("OwnerId "+ownerId);
		Pageable pageable=new PageRequest(pageNumber,pageLength,sortDirection,"timestamp");
		notifications=notificationRepository.findByUser(ownerId, pageable);
		if (notifications!=null)
		{
			if (LOG.isDebugEnabled())
				LOG.debug("Total elements = "+notifications.getTotalElements()+" total pages ="+notifications.getTotalPages());
			Iterator<Notification> iter=notifications.iterator();
			while (iter.hasNext())
			{
				Notification na=iter.next();
				if (LOG.isTraceEnabled()) LOG.trace("Converting to details - "+na.getType());
				NotificationDetails det=na.toNotificationDetails();
				dets.add(det);
			}
			if (0==dets.size())
			{
				// Need to check if we actually found parentId.
				User user=userRepository.findOne(ownerId);
				if ( (null==user) ||
					 ((null==user.getGivenName()) || ((null==user.getFamilyName()) && (null==user.getEmail()) && (null==user.getInstitution()))))
				{
					if (LOG.isDebugEnabled()) LOG.debug("Null or null properties returned by findOne(UserId)");
					nare=AllReadEvent.notFound(ownerId);
				}
				else
				{	
					nare=new AllReadEvent(ownerId,dets,notifications.getTotalElements(),notifications.getTotalPages());
				}
			}
			else
			{	
				nare=new AllReadEvent(ownerId,dets,notifications.getTotalElements(),notifications.getTotalPages());
			}
		}
		else
		{
			if (LOG.isDebugEnabled()) LOG.debug("Null returned by findByInstitutionId");
			nare=AllReadEvent.notFound(ownerId);
		}
		return nare;
	}

	@Override
	public DeletedEvent deleteNotification(DeleteEvent deleteNotificationEvent)
	{
		Long nodeId=deleteNotificationEvent.getNodeId();
		DeletedEvent evt=new DeletedEvent(nodeId);
		// TODO Auto-generated method stub
		return evt;
	}

	@Override
	public UpdatedEvent updateNotification(UpdateEvent updateNotificationEvent)
	{
		Long nodeId=updateNotificationEvent.getNodeId();
		Details details=updateNotificationEvent.getDetails();
		// TODO Auto-generated method stub
		UpdatedEvent evt=new UpdatedEvent(nodeId, details);
		return evt;
	}

}
