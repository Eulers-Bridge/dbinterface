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
	private Long id;
	private NewsFeedDetails readNewsFeedDetails;
	
	public NewsFeedReadEvent(Long id) 
	{
		this.id = id;
	}

	  public NewsFeedReadEvent(Long id, NewsFeedDetails readNewsFeedDetails) 
	  {
		  this.id = id;
		  this.readNewsFeedDetails = readNewsFeedDetails;
	  }

	  public Long getNewsArticleId() 
	  {
		  return this.id;
	  }

	  public NewsFeedDetails getReadNewsFeedDetails() 
	  {
		  return readNewsFeedDetails;
	  }

	  public static NewsFeedReadEvent notFound(Long id) 
	  {
		  NewsFeedReadEvent ev = new NewsFeedReadEvent(id);
		  ev.entityFound=false;
		  return ev;
	  }

}
