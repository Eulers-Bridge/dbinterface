/**
 * 
 */
package com.eulersbridge.iEngage.core.services;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eulersbridge.iEngage.core.events.CreatedEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.contactRequest.AcceptContactRequestEvent;
import com.eulersbridge.iEngage.core.events.contactRequest.ContactRequestCreatedEvent;
import com.eulersbridge.iEngage.core.events.contactRequest.ContactRequestDetails;
import com.eulersbridge.iEngage.core.events.contactRequest.ContactRequestReadEvent;
import com.eulersbridge.iEngage.core.events.contactRequest.CreateContactRequestEvent;
import com.eulersbridge.iEngage.core.events.contactRequest.ReadContactRequestEvent;
import com.eulersbridge.iEngage.database.domain.ContactRequest;
import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.repository.ContactRequestRepository;
import com.eulersbridge.iEngage.database.repository.UserRepository;

import org.apache.commons.validator.routines.EmailValidator;

/**
 * @author Greg Newitt
 *
 */
public class ContactRequestEventHandler implements ContactRequestService
{

    private static Logger LOG = LoggerFactory.getLogger(ContactRequestEventHandler.class);

    private ContactRequestRepository contactRequestRepository;

	private UserRepository userRepository;

    public ContactRequestEventHandler(ContactRequestRepository contactRequestRepository, UserRepository userRepository)
    {
        this.contactRequestRepository = contactRequestRepository;
        this.userRepository = userRepository;
    }

	/* (non-Javadoc)
	 * @see com.eulersbridge.iEngage.core.services.ContactRequestService#createContactRequest(com.eulersbridge.iEngage.core.events.contactRequest.CreateContactRequestEvent)
	 */
	@Override
	public CreatedEvent createContactRequest(
			CreateContactRequestEvent createContactRequestEvent)
	{
		ContactRequestDetails dets=(ContactRequestDetails)createContactRequestEvent.getDetails();
        CreatedEvent contactRequestCreatedEvent;
		ContactRequest cr=ContactRequest.fromContactRequestDetails(dets);
		cr.setRequestDate(Calendar.getInstance().getTimeInMillis());
		ContactRequest result = contactRequestRepository.save(cr);
        if ((null==result)||(null==result.getNodeId()))
        	contactRequestCreatedEvent = CreatedEvent.failed(dets);
        else
        {
        	if (LOG.isDebugEnabled()) LOG.debug("ContactRequest created "+result);
			contactRequestCreatedEvent = new ContactRequestCreatedEvent(result.toContactRequestDetails());
        }
        return contactRequestCreatedEvent;
	}

	/* (non-Javadoc)
	 * @see com.eulersbridge.iEngage.core.services.ContactRequestService#readContactRequest(com.eulersbridge.iEngage.core.events.contactRequest.ReadContactRequestEvent)
	 */
	@Override
	public ReadEvent readContactRequest(
			ReadContactRequestEvent readContactRequestEvent)
	{
        ContactRequest task = contactRequestRepository.findOne(readContactRequestEvent.getNodeId());
        ReadEvent readTaskEvent;
        if(task != null){
            readTaskEvent = new ContactRequestReadEvent(task.getNodeId(), task.toContactRequestDetails());
        }
        else{
            readTaskEvent = ContactRequestReadEvent.notFound(readContactRequestEvent.getNodeId());
        }
        return readTaskEvent;
	}

	/* (non-Javadoc)
	 * @see com.eulersbridge.iEngage.core.services.ContactRequestService#readContactRequestByUserIdContactNumber(com.eulersbridge.iEngage.core.events.contactRequest.ReadContactRequestEvent)
	 */
	@Override
	public ReadEvent readContactRequestByUserIdContactNumber(
			ReadContactRequestEvent readContactRequestEvent)
	{
		String contactInfo=readContactRequestEvent.getDetails().getContactDetails();
		Long userId = readContactRequestEvent.getDetails().getUserId();		
       	if (LOG.isDebugEnabled()) LOG.debug("Looking for Contact "+userId+" contactInfo "+contactInfo);
        ContactRequest contactRequest = contactRequestRepository.findContactRequestByUserIdContactInfo(userId, contactInfo);
        ReadEvent contactRequestReadEvent;
        if(contactRequest != null)
        {
        	if (LOG.isDebugEnabled()) LOG.debug("Contact found."+contactRequest.getNodeId());
            contactRequestReadEvent = new ContactRequestReadEvent(contactRequest.getNodeId(), contactRequest.toContactRequestDetails());
        }
        else
        {
        	if (LOG.isDebugEnabled()) LOG.debug("Contact not found.");
            contactRequestReadEvent = ContactRequestReadEvent.notFound(readContactRequestEvent.getNodeId());
        }
        return contactRequestReadEvent;
	}

	@Override
	public UpdatedEvent acceptContactRequest(
			AcceptContactRequestEvent acceptContactRequestEvent)
	{
		Long contactRequestId=acceptContactRequestEvent.getNodeId();
       	if (LOG.isDebugEnabled()) LOG.debug("Looking for ContactRequest "+contactRequestId);
       	ContactRequest cr=contactRequestRepository.findOne(contactRequestId);
       	UpdatedEvent uEvt;
       	if (cr!=null)
       	{
    		EmailValidator emailValidator=EmailValidator.getInstance();
			boolean isEmail=emailValidator.isValid(cr.getContactDetails());
    		User contactee,contactor;
    		if (isEmail)
    			contactee=userRepository.findByEmail(cr.getContactDetails());
    		else
           		contactee=userRepository.findByContactNumber(cr.getContactDetails());
    		if (contactee!=null)
    		{
	    		contactor=cr.getUser();
	       		cr.setAccepted(true);
	       		cr.setRejected(false);
	       		cr.setResponseDate(Calendar.getInstance().getTimeInMillis());
	//       		userRepository.addContact(contactor,contactee);
	           	ContactRequest result=contactRequestRepository.save(cr);
	           	if (result!=null)
	           		uEvt=new UpdatedEvent(contactRequestId, result.toContactRequestDetails());
	           	//TODO Should really be failed.
	           	else uEvt=UpdatedEvent.notFound(null);
    		}
    		else
    		{
           		uEvt=UpdatedEvent.notFound(contactRequestId);
    		}
       	}
       	else
       	{
       		uEvt=UpdatedEvent.notFound(contactRequestId);
       	}
		return uEvt;
	}

}
