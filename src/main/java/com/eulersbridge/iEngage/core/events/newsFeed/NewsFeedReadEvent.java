/**
 * 
 */
package com.eulersbridge.iEngage.core.events.newsFeed;

import com.eulersbridge.iEngage.core.events.ReadEvent;

/**
 * @author Greg Newitt
 *
 */
public class NewsFeedReadEvent extends ReadEvent 
{
	public NewsFeedReadEvent(Long id) 
	{
		super(id);
	}

	  public NewsFeedReadEvent(Long id, NewsFeedDetails readNewsFeedDetails) 
	  {
		  super(id,readNewsFeedDetails);
	  }

	  public Long getNewsArticleId() 
	  {
		  return getNodeId();
	  }

	  public NewsFeedDetails getReadNewsFeedDetails() 
	  {
		  return (NewsFeedDetails)getDetails();
	  }
}
