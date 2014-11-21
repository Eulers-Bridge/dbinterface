/**
 * 
 */
package com.eulersbridge.iEngage.core.events.newsFeed;

import com.eulersbridge.iEngage.core.events.CreatedEvent;

/**
 * @author Greg Newitt
 *
 */
public class NewsFeedCreatedEvent extends CreatedEvent 
{
	private Long id;
	private boolean institutionFound=true;
	
	public NewsFeedCreatedEvent(Long id, NewsFeedDetails newsFeedDetails) 
	{
		super(newsFeedDetails);
		this.id=id;
	}

	public NewsFeedCreatedEvent(Long id) 
	{
		this.id=id;
	}

	/**
	 * @return the newsFeedDetails
	 */
	public NewsFeedDetails getNewsFeedDetails()
	{
		return (NewsFeedDetails)super.getDetails();
	}

	/**
	 * @param newsFeedDetails the newsFeedDetails to set
	 */
	public void setNewsFeedDetails(NewsFeedDetails newsFeedDetails)
	{
		setDetails(newsFeedDetails);
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the institutionFound
	 */
	public boolean isInstitutionFound() {
		return institutionFound;
	}

	/**
	 * @param institutionFound the institutionFound to set
	 */
	public void setInstitutionFound(boolean institutionFound) {
		this.institutionFound = institutionFound;
	}

	public static NewsFeedCreatedEvent institutionNotFound(Long nodeId) 
	{
		NewsFeedCreatedEvent ev = new NewsFeedCreatedEvent(nodeId);
	    ev.setInstitutionFound(false);
	    return ev;
	}

}
