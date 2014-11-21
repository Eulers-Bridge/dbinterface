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
	public ReadNewsFeedEvent(Long id) 
	{
	    super(id);
	}
}
