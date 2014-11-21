/**
 * 
 */
package com.eulersbridge.iEngage.core.events.newsFeed;

import com.eulersbridge.iEngage.core.events.CreateEvent;

/**
 * @author Greg Newitt
 *
 */
public class CreateNewsFeedEvent extends CreateEvent 
{
    public CreateNewsFeedEvent(NewsFeedDetails newsFeedDetails) 
	{
		super(newsFeedDetails);
	}
}
