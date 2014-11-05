/**
 * 
 */
package com.eulersbridge.iEngage.core.events.newsArticles;

import com.eulersbridge.iEngage.core.events.LikedEvent;

/**
 * @author Greg Newitt
 *
 */
public class NewsArticleLikedEvent extends LikedEvent
{
	Long articleId;

	public NewsArticleLikedEvent(Long articleId, String userId,boolean result) 
	{
		super(articleId,userId,result);
	}

	public NewsArticleLikedEvent(Long articleId, String userId) 
	{
		super(articleId,userId);
	}

	public NewsArticleLikedEvent(String userId) 
	{
		super(userId);
	}
//TODO remove, in fact delete entire class.
	public static NewsArticleLikedEvent articleNotFound(Long articleId, String userId) {
		NewsArticleLikedEvent ev = new NewsArticleLikedEvent(articleId, userId);
	    ev.entityFound=false;
	    ev.result=false;
	    return ev;
	  }

	  public static NewsArticleLikedEvent userNotFound(Long articleId, String userId) 
	  {
		  NewsArticleLikedEvent ev = new NewsArticleLikedEvent(articleId,userId);
	    ev.userFound=false;
	    ev.result=false;
	    return ev;
	  }
}
