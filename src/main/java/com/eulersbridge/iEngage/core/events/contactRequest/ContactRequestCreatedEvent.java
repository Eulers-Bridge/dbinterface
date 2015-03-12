/**
 * 
 */
package com.eulersbridge.iEngage.core.events.contactRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eulersbridge.iEngage.core.events.CreatedEvent;

/**
 * @author Greg Newitt
 *
 */
public class ContactRequestCreatedEvent extends CreatedEvent
{
    private Long failedId;

    private static Logger LOG = LoggerFactory.getLogger(ContactRequestCreatedEvent.class);

    public ContactRequestCreatedEvent(Long failedId){
        if (LOG.isDebugEnabled()) LOG.debug("constructor()");
        this.failedId = failedId;
    }

    public ContactRequestCreatedEvent(ContactRequestDetails contactRequestDetails)
    {
        super(contactRequestDetails);
    }

	public Long getFailedId() {
        return failedId;
    }

    public void setFailedId(Long failedId) {
        this.failedId = failedId;
    }

}
