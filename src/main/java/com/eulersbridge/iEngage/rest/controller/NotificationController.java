/**
 * 
 */
package com.eulersbridge.iEngage.rest.controller;

import com.eulersbridge.iEngage.core.events.*;
import com.eulersbridge.iEngage.core.events.notifications.NotificationDetails;
import com.eulersbridge.iEngage.core.services.interfacePack.NotificationService;
import com.eulersbridge.iEngage.rest.domain.Response;
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
 *
 */
@RestController
@RequestMapping(ControllerConstants.API_PREFIX)
public class NotificationController
{
//    @Autowired NotificationService notificationService;
	public NotificationController() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("NotificationController()");
	}

    private static Logger LOG = LoggerFactory.getLogger(NotificationController.class);

//    //Create
//    @RequestMapping(method = RequestMethod.POST, value = ControllerConstants.NOTIFICATION_LABEL)
//    public @ResponseBody ResponseEntity<Notification>
//    createNotification(@RequestBody Notification notification)
//    {
//        if (LOG.isInfoEnabled()) LOG.info("attempting to create notification. "+notification);
//        CreateEvent createNotificationEvent = new CreateEvent(notification.toNotificationDetails());
//        CreatedEvent notificationCreatedEvent = notificationService.createNotification(createNotificationEvent);
//        ResponseEntity<Notification> response;
//        if(null==notificationCreatedEvent)
//        {
//            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    	else if (notificationCreatedEvent.isFailed())
//    	{
//    		response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    	}
//    	else if(null==notificationCreatedEvent.getId())
//        {
//            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//        else
//        {
//            Notification result = Notification.fromNotificationDetails((NotificationDetails) notificationCreatedEvent.getDetails());
//            if (LOG.isDebugEnabled()) LOG.debug("notification"+result.toString());
//            response = new ResponseEntity<Notification>(result, HttpStatus.CREATED);
//        }
//        return response;
//    }
//
//    //Get
//    @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.NOTIFICATION_LABEL + "/{notificationId}")
//    public @ResponseBody ResponseEntity<Notification>
//    findNotification(@PathVariable Long notificationId)
//    {
//        if (LOG.isInfoEnabled()) LOG.info(notificationId+" attempting to get notification. ");
//        RequestReadEvent requestReadNotificationEvent = new RequestReadEvent(notificationId);
//        ResponseEntity<Notification> response;
//        ReadEvent readNotificationEvent = notificationService.readNotification(requestReadNotificationEvent);
//        if((readNotificationEvent!=null)&&(readNotificationEvent.isEntityFound()))
//        {
//            Notification notification = Notification.fromNotificationDetails((NotificationDetails) readNotificationEvent.getDetails());
//            response = new ResponseEntity<>(notification, HttpStatus.OK);
//        }
//        else
//        {
//            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return response;
//    }
//
//	/**
//	 * Is passed all the necessary data to read notifications from the database. The
//	 * request must be a GET with the ownerId presented as the final
//	 * portion of the URL.
//	 * <p/>
//	 * This method will return the notifications read from the database.
//	 *
//	 * @param ownerId
//	 *            the ownerId of the notification objects to be read.
//	 * @return the notifications.
//	 *
//	 */
//	@RequestMapping(method = RequestMethod.GET, value = ControllerConstants.NOTIFICATIONS_LABEL+"/{ownerId}")
//	public @ResponseBody ResponseEntity<Iterator<Notification>> findNotifications(
//			@PathVariable(value = "") Long ownerId,
//			@RequestParam(value = "direction", required = false, defaultValue = ControllerConstants.DIRECTION) String direction,
//			@RequestParam(value = "page", required = false, defaultValue = ControllerConstants.PAGE_NUMBER) String page,
//			@RequestParam(value = "pageSize", required = false, defaultValue = ControllerConstants.PAGE_LENGTH) String pageSize)
//	{
//		int pageNumber = 0;
//		int pageLength = 10;
//		pageNumber = Integer.parseInt(page);
//		pageLength = Integer.parseInt(pageSize);
//		if (LOG.isInfoEnabled())
//			LOG.info("Attempting to retrieve notifications from institution "
//					+ ownerId + '.');
//
//		Direction sortDirection = Direction.DESC;
//		if (direction.equalsIgnoreCase("asc")) sortDirection = Direction.ASC;
//		AllReadEvent notificationsEvent = notificationService.readNotifications(
//				new ReadAllEvent(ownerId), sortDirection,
//				pageNumber, pageLength);
//
//		if (!notificationsEvent.isEntityFound())
//		{
//			return new ResponseEntity<Iterator<Notification>>(HttpStatus.NOT_FOUND);
//		}
//
//		Iterable<? extends Details> details = notificationsEvent.getDetails();
//		if (LOG.isDebugEnabled()) LOG.debug("details - "+details);
//		Iterator<? extends Details> iterator = details.iterator();
//		Iterator<Notification> notifications = Notification
//				.toNotificationsIterator(iterator);
//
//		return new ResponseEntity<Iterator<Notification>>(notifications, HttpStatus.OK);
//	}
//
//    //Delete
//    @RequestMapping(method = RequestMethod.DELETE, value = ControllerConstants.NOTIFICATION_LABEL + "/{notificationId}")
//    public @ResponseBody ResponseEntity<Response>
//    deleteNotification(@PathVariable Long notificationId)
//    {
//        if (LOG.isInfoEnabled()) LOG.info(notificationId+" attempting to delete notification. ");
//        DeleteEvent deleteNotificationEvent = new DeleteEvent(notificationId);
//        ResponseEntity<Response> response;
//        DeletedEvent notificationDeletedEvent = notificationService.deleteNotification(deleteNotificationEvent);
//        Response restEvent;
//        if((notificationDeletedEvent!=null)&&(notificationDeletedEvent.isEntityFound()))
//        {
//            Notification notification = Notification.fromNotificationDetails((NotificationDetails) notificationDeletedEvent.getDetails());
//            if (LOG.isDebugEnabled()) LOG.debug(notificationId+" deleted.");
//            restEvent = new Response();
//            response = new ResponseEntity<Response>(restEvent, HttpStatus.OK);
//        }
//        else
//        {
//            restEvent = Response.failed("Not found");
//            response = new ResponseEntity<Response>(restEvent, HttpStatus.NOT_FOUND);
//        }
//        return response;
//    }
//
//    //Update
//    @RequestMapping(method = RequestMethod.PUT, value = ControllerConstants.NOTIFICATION_LABEL+"/{notificationId}")
//    public @ResponseBody ResponseEntity<Notification>
//    updateNotification(@PathVariable Long notificationId, @RequestBody Notification notification)
//    {
//        if (LOG.isInfoEnabled()) LOG.info("Attempting to update notification. " + notificationId);
//        ResponseEntity<Notification> response;
//        if (notification!=null)
//        {
//	        UpdateEvent updateEvent = new UpdateEvent(notificationId, notification.toNotificationDetails());
//	        UpdatedEvent notificationUpdatedEvent = notificationService.updateNotification(updateEvent);
//	        if(null != notificationUpdatedEvent)
//	        {
//	            if (LOG.isDebugEnabled()) LOG.debug("notificationUpdatedEvent - "+notificationUpdatedEvent);
//	            if(notificationUpdatedEvent.isEntityFound())
//	            {
//	                Notification result = Notification.fromNotificationDetails((NotificationDetails) notificationUpdatedEvent.getDetails());
//	                if (LOG.isDebugEnabled()) LOG.debug("result = "+result);
//	                response = new ResponseEntity<>(result, HttpStatus.OK);
//	            }
//	            else{
//	                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
//	            }
//	        }
//	        else
//	        {
//	            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//	        }
//        }
//        else response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        return response;
//    }
}
