/**
 * 
 */
package com.eulersbridge.iEngage.core.events.newsFeed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eulersbridge.iEngage.core.events.CreateEvent;

/**
 * @author Greg Newitt
 *
 */
public class CreateNewsFeedEvent extends CreateEvent 
{
	NewsFeedDetails newsFeedDetails;
	Long newsFeedId;
	
    private static Logger LOG = LoggerFactory.getLogger(CreateNewsFeedEvent.class);
    
    public CreateNewsFeedEvent(Long id, NewsFeedDetails newsFeedDetails) 
	{
		if (LOG.isDebugEnabled()) LOG.debug("CreateStudentYearEvent("+id+","+newsFeedDetails+") = ");
		newsFeedDetails.setNodeId(id);
		this.newsFeedDetails=newsFeedDetails;
	}

	public CreateNewsFeedEvent(NewsFeedDetails newsFeedDetails) 
	{
		if (LOG.isDebugEnabled()) LOG.debug("CreateNewsFeedEvent("+newsFeedDetails+") = ");
		this.newsFeedDetails=newsFeedDetails;
	}

	public NewsFeedDetails getNewsFeedDetails() {
		return this.newsFeedDetails;
	}

	public void setNewsFeedDetails(NewsFeedDetails studentYearDetails) {
		this.newsFeedDetails = studentYearDetails;
	}

}
