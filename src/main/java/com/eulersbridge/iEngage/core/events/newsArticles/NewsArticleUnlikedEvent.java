/**
 * 
 */
package com.eulersbridge.iEngage.core.events.newsArticles;

import com.eulersbridge.iEngage.core.events.LikedEvent;

/**
 * @author Greg Newitt
 *
 */
public class NewsArticleUnlikedEvent extends LikedEvent 
{
	Long articleId;

	public NewsArticleUnlikedEvent(Long articleId, String userId,boolean result) 
	{
		super(articleId,userId,result);
	}

	public NewsArticleUnlikedEvent(Long articleId, String userId) 
	{
		super(articleId,userId);
	}

	public NewsArticleUnlikedEvent(String userId) 
	{
		super(userId);
	}
//TODO delete entire class.
	public static NewsArticleUnlikedEvent articleNotFound(Long articleId, String userId) 
	{
		NewsArticleUnlikedEvent ev = new NewsArticleUnlikedEvent(articleId, userId);
	    ev.entityFound=false;
	    ev.result=false;
	    return ev;
	}

	public static NewsArticleUnlikedEvent userNotFound(Long articleId, String userId) 
	{
		NewsArticleUnlikedEvent ev = new NewsArticleUnlikedEvent(articleId, userId);
	    ev.userFound=false;
	    ev.result=false;
	    return ev;
	}

}
