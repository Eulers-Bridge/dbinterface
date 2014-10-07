/**
 * 
 */
package com.eulersbridge.iEngage.core.events.newsFeed;

import com.eulersbridge.iEngage.core.events.RequestReadEvent;

/**
 * @author Greg Newitt
 *
 */
public class ReadNewsFeedEvent extends RequestReadEvent 
{
	private Long id;

	public ReadNewsFeedEvent(Long id) 
	{
	    this.id = id;
	}

	public Long getNewsFeedId() 
	{
	    return id;
	}

}
