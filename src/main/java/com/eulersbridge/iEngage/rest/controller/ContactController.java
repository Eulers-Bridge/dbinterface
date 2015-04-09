/**
 * 
 */
package com.eulersbridge.iEngage.rest.controller;

import java.util.Calendar;
import java.util.Iterator;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.LongValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.eulersbridge.iEngage.core.events.AllReadEvent;
import com.eulersbridge.iEngage.core.events.CreateEvent;
import com.eulersbridge.iEngage.core.events.CreatedEvent;
import com.eulersbridge.iEngage.core.events.ReadAllEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdateEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.contactRequest.AcceptContactRequestEvent;
import com.eulersbridge.iEngage.core.events.contactRequest.ContactRequestDetails;
import com.eulersbridge.iEngage.core.events.contactRequest.CreateContactRequestEvent;
import com.eulersbridge.iEngage.core.events.contactRequest.ReadContactRequestEvent;
import com.eulersbridge.iEngage.core.events.contacts.ContactDetails;
import com.eulersbridge.iEngage.core.events.users.ReadUserEvent;
import com.eulersbridge.iEngage.core.events.users.RequestReadUserEvent;
import com.eulersbridge.iEngage.core.events.users.UserDetails;
import com.eulersbridge.iEngage.core.services.ContactRequestService;
import com.eulersbridge.iEngage.core.services.NotificationService;
import com.eulersbridge.iEngage.core.services.UserService;
import com.eulersbridge.iEngage.database.domain.notifications.NotificationConstants;
import com.eulersbridge.iEngage.rest.domain.Contact;
import com.eulersbridge.iEngage.rest.domain.ContactRequest;
import com.eulersbridge.iEngage.rest.domain.Notification;

/**
 * @author Greg Newitt
 *
 */
@RestController
@RequestMapping(ControllerConstants.API_PREFIX)
public class ContactController
{
    @Autowired UserService userService;
	@Autowired ContactRequestService contactRequestService;
    @Autowired NotificationService notificationService;

	    private static Logger LOG = LoggerFactory.getLogger(ContactController.class);
		private EmailValidator emailValidator;
		private LongValidator longValidator;

	    public ContactController()
	    {
			if (LOG.isDebugEnabled()) LOG.debug("ContactController()");
			emailValidator=EmailValidator.getInstance();
			longValidator=LongValidator.getInstance();
	    }

	    /**
	     * Is passed all the necessary data to add a contact from the database.
	     * The request must be a PUT with the contact email or phone number presented
	     * as the final portion of the URL.
	     * <p/>
	     * This method will return a 200 ok if nothing has gone wrong.
	     * 
	     * @param contactInfo the email address or phone number of the contact to be added.
	     * @return userProfile.
	     * 

		*/
		@RequestMapping(method=RequestMethod.PUT,value=ControllerConstants.USER_LABEL+"/{userId}"+ControllerConstants.CONTACT_LABEL+"/{contactInfo}")
		public @ResponseBody ResponseEntity<ContactRequest> addContact(@PathVariable Long userId,@PathVariable String contactInfo) 
		{
			if (LOG.isInfoEnabled()) LOG.info("Attempting to add contact "+contactInfo+" for "+userId);

			ReadUserEvent userEvent;
			ResponseEntity<ContactRequest> result;
			boolean isEmail=emailValidator.isValid(contactInfo);
			String email=null;
		
			if (isEmail)
			{
				email=contactInfo;
				userEvent=userService.readUserByContactEmail(new RequestReadUserEvent(email));
			}
			else
				userEvent=userService.readUserByContactNumber(new RequestReadUserEvent(contactInfo));
				 	
			// Look for existing contact request.
			ContactRequestDetails fr=new ContactRequestDetails(contactInfo,userId);
			
			ReadContactRequestEvent readContactRequestEvent=new ReadContactRequestEvent(fr);
			ReadEvent crEvt=contactRequestService.readContactRequestByUserIdContactNumber(readContactRequestEvent);
			ContactRequest restContactRequest;

			if (crEvt.isEntityFound())
			{
				if (LOG.isDebugEnabled()) LOG.debug("Contact Request details returned - "+(crEvt.getDetails()));
				restContactRequest=ContactRequest.fromContactRequestDetails((ContactRequestDetails)(crEvt.getDetails()));
				// Request already exists, bounce out of here.
				if (LOG.isDebugEnabled()) LOG.debug("Contact Request returned - "+restContactRequest);
				// Has it been denied?  TODO
				result = new ResponseEntity<ContactRequest>(restContactRequest,HttpStatus.ACCEPTED);
			}
			else
			{
				CreateContactRequestEvent createEvt=new CreateContactRequestEvent(fr);
				CreatedEvent evt=contactRequestService.createContactRequest(createEvt);
				if (!evt.isFailed())
				{	
					if (!userEvent.isEntityFound())
					{
						// They are not already users.  Need to deal with that.
						ContactRequestDetails dets=(ContactRequestDetails) evt.getDetails();
						restContactRequest=ContactRequest.fromContactRequestDetails(dets);
						
						// Create a ContactRequest.
						result = new ResponseEntity<ContactRequest>(restContactRequest,HttpStatus.CREATED);
					}
					else
					{
						ContactRequestDetails dets=(ContactRequestDetails) evt.getDetails();
						
						restContactRequest=ContactRequest.fromContactRequestDetails(dets);
						// Create a new contact request.
						Notification notification = new Notification();
						notification.setNotificationBody(dets);
						notification.setRead(false);
						notification.setType(NotificationConstants.CONTACT_REQUEST);
						notification.setTimestamp(Calendar.getInstance().getTimeInMillis());
						UserDetails userDets=(UserDetails)userEvent.getDetails();
						Long contacteeId=userService.findUserId(userDets.getEmail());
						notification.setUserId(contacteeId);
						
						
						if (LOG.isDebugEnabled())
						{
							LOG.debug("userEvent - "+userEvent);
							LOG.debug("Notification - "+notification);
							LOG.debug("Notification details - "+notification.toNotificationDetails());
						}
				        CreateEvent createNotificationEvent = new CreateEvent(notification.toNotificationDetails());
				        CreatedEvent notificationCreatedEvent = notificationService.createNotification(createNotificationEvent);
				        if (notificationCreatedEvent.isFailed())
				        {
				        	if (LOG.isDebugEnabled()) LOG.debug("Notification failed.");
				        }

						result = new ResponseEntity<ContactRequest>(restContactRequest,HttpStatus.OK);
					}
				}
				else
				{
					result = new ResponseEntity<ContactRequest>(HttpStatus.BAD_REQUEST);
				}
			}
			return result;
		}
	    
	    /**
	     * Is passed all the necessary data to add a contact from the database.
	     * The request must be a PUT with the contact email or phone number presented
	     * as the final portion of the URL.
	     * <p/>
	     * This method will return a 200 ok if nothing has gone wrong.
	     * 
	     * @param contactInfo the email address or phone number of the contact to be added.
	     * @return userProfile.
	     * 

		*/
		@RequestMapping(method=RequestMethod.PUT,value=ControllerConstants.CONTACT_LABEL+"/{contactRequestId}")
		public @ResponseBody ResponseEntity<Contact> acceptContact(@PathVariable Long contactRequestId) 
		{
			if (LOG.isInfoEnabled()) LOG.info("Attempting to add contact from "+contactRequestId);

			ResponseEntity<Contact> result;
			Contact restContact;

			ReadContactRequestEvent readContactRequestEvent=new ReadContactRequestEvent(contactRequestId);
			ReadEvent rEvt=contactRequestService.readContactRequest(readContactRequestEvent);
			
			if (rEvt.isEntityFound())
			{
				ContactRequestDetails crDets=(ContactRequestDetails)rEvt.getDetails();
				if (LOG.isDebugEnabled()) LOG.debug("Contact Request details returned - "+crDets);

				
				AcceptContactRequestEvent acceptContactRequestEvent=new AcceptContactRequestEvent(contactRequestId);
				UpdatedEvent uEvt=contactRequestService.acceptContactRequest(acceptContactRequestEvent);
				if (uEvt.isEntityFound())
				{
					ContactDetails cDets=(ContactDetails)uEvt.getDetails();
					restContact=Contact.fromContactDetails(cDets);
					result = new ResponseEntity<Contact>(restContact,HttpStatus.CREATED);
					if (LOG.isDebugEnabled()) LOG.debug("Contact Request returned - "+restContact);
				}
				else
				{
					result = new ResponseEntity<Contact>(HttpStatus.NOT_FOUND);
				}
			}
			else
			{
				result = new ResponseEntity<Contact>(HttpStatus.NOT_FOUND);
			}
			return result;
		}
	    
	    /**
	     * Is passed all the necessary data to reject a contact  request.
	     * The request must be a DELETE with the contact request ID presented
	     * as the final portion of the URL.
	     * <p/>
	     * This method will return a 200 ok if nothing has gone wrong.
	     * 
	     * @param contactInfo the email address or phone number of the contact to be rejected.
	     * @return userProfile.
	     * 

		*/
		@RequestMapping(method=RequestMethod.DELETE,value=ControllerConstants.CONTACT_LABEL+"/{contactRequestId}")
		public @ResponseBody ResponseEntity<Contact> rejectContact(@PathVariable Long contactRequestId) 
		{
			if (LOG.isInfoEnabled()) LOG.info("Attempting to reject contact request from "+contactRequestId);

			ResponseEntity<Contact> result;
			Contact restContact;

			ReadContactRequestEvent readContactRequestEvent=new ReadContactRequestEvent(contactRequestId);
			ReadEvent rEvt=contactRequestService.readContactRequest(readContactRequestEvent);
			
			if (rEvt.isEntityFound())
			{
				ContactRequestDetails crDets=(ContactRequestDetails)rEvt.getDetails();
				if (LOG.isDebugEnabled()) LOG.debug("Contact Request details returned - "+crDets);

				
				UpdateEvent acceptContactRequestEvent=new UpdateEvent(contactRequestId,crDets);
				UpdatedEvent uEvt=contactRequestService.rejectContactRequest(acceptContactRequestEvent);
				if (uEvt.isEntityFound())
				{
					ContactDetails cDets=(ContactDetails)uEvt.getDetails();
					restContact=Contact.fromContactDetails(cDets);
					result = new ResponseEntity<Contact>(restContact,HttpStatus.OK);
					if (LOG.isDebugEnabled()) LOG.debug("Contact Request returned - "+restContact);
				}
				else
				{
					result = new ResponseEntity<Contact>(HttpStatus.NOT_FOUND);
				}
			}
			else
			{
				result = new ResponseEntity<Contact>(HttpStatus.NOT_FOUND);
			}
			return result;
		}
	    
		/**
		 * Is passed all the necessary data to read contactRequests received from the database. The
		 * request must be a GET with the userId of the receiver presented as the final
		 * portion of the URL.
		 * <p/>
		 * This method will return the contactRequests received from the database.
		 * 
		 * @param userId
		 *            the userId whose contactRequests are to be read.
		 * @return the contactRequests.
		 * 
		 */
		@RequestMapping(method = RequestMethod.GET, value = ControllerConstants.CONTACT_REQUESTS_LABEL
				+ "/{userId}")
		public @ResponseBody ResponseEntity<Iterator<ContactRequest>> findContactRequestsReceived(
				@PathVariable(value = "") Long userId,
				@RequestParam(value = "direction", required = false, defaultValue = ControllerConstants.DIRECTION) String direction,
				@RequestParam(value = "page", required = false, defaultValue = ControllerConstants.PAGE_NUMBER) String page,
				@RequestParam(value = "pageSize", required = false, defaultValue = ControllerConstants.PAGE_LENGTH) String pageSize)
		{
			int pageNumber = 0;
			int pageLength = 10;
			pageNumber = Integer.parseInt(page);
			pageLength = Integer.parseInt(pageSize);
			if (LOG.isInfoEnabled())
				LOG.info("Attempting to retrieve contactRequests for user "
						+ userId + '.');

			Direction sortDirection = Direction.DESC;
			if (direction.equalsIgnoreCase("asc")) sortDirection = Direction.ASC;
			AllReadEvent articleEvent = contactRequestService.readContactRequestsReceived(
					new ReadAllEvent(userId), sortDirection,
					pageNumber, pageLength);

			if (!articleEvent.isEntityFound())
			{
				return new ResponseEntity<Iterator<ContactRequest>>(HttpStatus.NOT_FOUND);
			}

			Iterator<ContactRequest> contactRequests = ContactRequest
					.toContactRequestIterator(articleEvent.getDetails().iterator());

			return new ResponseEntity<Iterator<ContactRequest>>(contactRequests, HttpStatus.OK);
		}

		/**
		 * Is passed all the necessary data to read contactRequests sent from the database. The
		 * request must be a GET with the userId of the sender presented as the final
		 * portion of the URL.
		 * <p/>
		 * This method will return the contactRequests sent from the database.
		 * 
		 * @param userId
		 *            the userId whose contactRequests are to be read.
		 * @return the contactRequests.
		 * 
		 */
		@RequestMapping(method = RequestMethod.GET, value = ControllerConstants.USER_LABEL+"/{userId}"+ControllerConstants.CONTACT_REQUESTS_LABEL)
		public @ResponseBody ResponseEntity<Iterator<ContactRequest>> findContactRequestsMade(
				@PathVariable(value = "") Long userId,
				@RequestParam(value = "direction", required = false, defaultValue = ControllerConstants.DIRECTION) String direction,
				@RequestParam(value = "page", required = false, defaultValue = ControllerConstants.PAGE_NUMBER) String page,
				@RequestParam(value = "pageSize", required = false, defaultValue = ControllerConstants.PAGE_LENGTH) String pageSize)
		{
			int pageNumber = 0;
			int pageLength = 10;
			pageNumber = Integer.parseInt(page);
			pageLength = Integer.parseInt(pageSize);
			if (LOG.isInfoEnabled())
				LOG.info("Attempting to retrieve contactRequests for user "
						+ userId + '.');

			Direction sortDirection = Direction.DESC;
			if (direction.equalsIgnoreCase("asc")) sortDirection = Direction.ASC;
			AllReadEvent articleEvent = contactRequestService.readContactRequestsMade(
					new ReadAllEvent(userId), sortDirection,
					pageNumber, pageLength);

			if (!articleEvent.isEntityFound())
			{
				return new ResponseEntity<Iterator<ContactRequest>>(HttpStatus.NOT_FOUND);
			}

			Iterator<ContactRequest> contactRequests = ContactRequest
					.toContactRequestIterator(articleEvent.getDetails().iterator());

			return new ResponseEntity<Iterator<ContactRequest>>(contactRequests, HttpStatus.OK);
		}

}
