/**
 * 
 */
package com.eulersbridge.iEngage.database.domain.notifications;

import com.eulersbridge.iEngage.core.events.contacts.ContactDetails;
import com.eulersbridge.iEngage.core.events.notifications.NotificationDetails;
import com.eulersbridge.iEngage.core.events.notifications.NotificationHelper;
import com.eulersbridge.iEngage.database.domain.Contact;
import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.repository.GraphRepository;

import java.util.HashMap;
import java.util.Iterator;

/**
 * @author Greg Newitt
 *
 */
public class NotificationContactAccepted extends Notification implements NotificationInterface
{
//	@RelatedTo(type = DatabaseDomainConstants.HAS_NOTIFICATION_DETAILS_LABEL, direction=Direction.OUTGOING) @Fetch
	Contact contact;

    static Logger LOG = LoggerFactory.getLogger(NotificationContactAccepted.class);

	private static String[] contactFieldsArray={
		NotificationConstants.NodeId,
   	 	NotificationConstants.Timestamp,
        NotificationConstants.ContacteeId,
        NotificationConstants.ContactorId
	};


    @Override
    public Boolean setupForSave(HashMap<String,GraphRepository<?>> repos)
    {
    	if (LOG.isDebugEnabled()) LOG.debug("setupForSave()");
		Boolean response=false;
		User result=null;
    	response = super.setupForSave(repos);
    	if (response)
    	{
    		if (LOG.isDebugEnabled()) LOG.debug("Super method successful.");
	    	response=false;
    		UserRepository userRepo=(UserRepository)repos.get(UserRepository.class.getSimpleName());
	    	if (userRepo!=null)
			{
	    		if (LOG.isDebugEnabled()) LOG.debug("User repository retrieved. - "+getContact());
				if (getContact()!=null)
				{	
					if ((getContact().getContactee()!=null)&&(getContact().getContactee().getNodeId()!=null))
					{
			    		if (LOG.isDebugEnabled()) LOG.debug("NodeId present. - ");
						result=userRepo.findOne(getContact().getContactee().getNodeId());

						getContact().setContactee(result);
						response=true;
					}
					if ((getContact().getContactor()!=null)&&(getContact().getContactor().getNodeId()!=null))
					{
			    		if (LOG.isDebugEnabled()) LOG.debug("NodeId present. - ");
						result=userRepo.findOne(getContact().getContactor().getNodeId());

						getContact().setContactor(result);
						response=true;
					}
				}
			}
    	}
		return response;
    }
    
    @Override
	public NotificationDetails toNotificationDetails()
	{
		if (LOG.isDebugEnabled()) LOG.debug("toNotificationDetails()");
		Long userId=null;
		if (user!=null) userId=user.getNodeId();
		
		ContactDetails contactDetails=contact.toContactDetails();
		NotificationDetails dets=new NotificationDetails(nodeId, userId, timestamp, isRead(), type, contactDetails);
		return dets;
	}
	
	public static NotificationContactAccepted fromNotificationDetails(NotificationDetails nDets)
	{
		NotificationContactAccepted notif=new NotificationContactAccepted();
		if (nDets!=null)
		{
			if (NotificationConstants.CONTACT_ACCEPTED.equals(nDets.getType()))
			{
				ContactDetails crd=(ContactDetails)nDets.getNotificationBody();
				Contact cr=Contact.fromContactDetails(crd);
				notif.setContact(cr);
			}
			notif.setType(nDets.getType());
			notif.setNodeId(nDets.getNodeId());
			User user=new User(nDets.getUserId());
			notif.setUser(user);
			notif.setRead(nDets.getRead());
			notif.setTimestamp(nDets.getTimestamp());
		}
		return notif;
	}
	
	public static ContactDetails populateFields(JsonNode node) throws JsonMappingException
	{
        Iterator<String> fields = node.fieldNames();
        NotificationHelper.checkFieldNames(fields,contactFieldsArray);

		Long nodeId=NotificationHelper.getLong(node.get(NotificationConstants.NodeId));
		Long contacteeId=NotificationHelper.getLong(node.get(NotificationConstants.ContacteeId));
        Long contactorId=NotificationHelper.getLong(node.get(NotificationConstants.ContactorId));
		Long timestamp=NotificationHelper.getLong(node.get(NotificationConstants.Timestamp));
		ContactDetails crd=new ContactDetails(nodeId, contactorId, contacteeId, timestamp);
		if ((null==nodeId)&&((null==contactorId)&&(null==contacteeId)))
			throw new JsonMappingException("notificationBody must be populated with a contact containing nodeId and contacteeId and contactorId");
		return crd;
	}

	/**
	 * @return the contact
	 */
	public Contact getContact()
	{
		return contact;
	}

	/**
	 * @param contact the contact to set
	 */
	public void setContact(Contact contact)
	{
		this.contact = contact;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "NotificationContactAccepted [contact=" + contact + ", nodeId="
				+ nodeId + ", read=" + isRead() + ", timestamp=" + timestamp
				+ ", type=" + type + ", user=" + user + "]";
	}

}
