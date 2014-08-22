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
		this.articleId=articleId;
		this.userEmail=userId;
		this.result=result;
	}

	public NewsArticleLikedEvent(Long articleId, String userId) 
	{
		this(articleId,userId,false);
	}

	public NewsArticleLikedEvent(String userId) 
	{
		this(null,userId,false);
	}

	public static NewsArticleLikedEvent articleNotFound(Long articleId, String userId) {
		  NewsArticleLikedEvent ev = new NewsArticleLikedEvent(articleId, userId);
	    ev.entityFound=false;
	    return ev;
	  }

	  public static NewsArticleLikedEvent userNotFound(String userId) 
	  {
		  NewsArticleLikedEvent ev = new NewsArticleLikedEvent(userId);
	    ev.userFound=false;
	    return ev;
	  }
}
