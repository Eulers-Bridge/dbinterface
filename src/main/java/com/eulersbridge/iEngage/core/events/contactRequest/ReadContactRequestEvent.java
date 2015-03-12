/**
 * 
 */
package com.eulersbridge.iEngage.core.events.contactRequest;

import com.eulersbridge.iEngage.core.events.RequestReadEvent;

/**
 * @author Greg Newitt
 *
 */
public class ReadContactRequestEvent extends RequestReadEvent
{
	private ContactRequestDetails dets;
	
	public ReadContactRequestEvent(Long nodeId)
	{
		super(nodeId);
	}

	public ReadContactRequestEvent(ContactRequestDetails dets)
	{
		super(null);
		this.setDetails(dets);
	}

	/**
	 * @return the dets
	 */
	public ContactRequestDetails getDetails()
	{
		return dets;
	}

	/**
	 * @param dets the dets to set
	 */
	public void setDetails(ContactRequestDetails dets)
	{
		this.dets = dets;
	}


}
