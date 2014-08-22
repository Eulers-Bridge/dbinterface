/**
 * 
 */
package com.eulersbridge.iEngage.core.events.newsArticles;

import com.eulersbridge.iEngage.core.events.LikeEvent;
import com.eulersbridge.iEngage.database.domain.NewsArticle;
import com.eulersbridge.iEngage.database.domain.User;

/**
 * @author Greg Newitt
 *
 */
public class UnlikeNewsArticleEvent extends LikeEvent 
{
	Long newsArticleId;

	public UnlikeNewsArticleEvent(NewsArticle newsArticle, User user) 
	{
		this(newsArticle.getNodeId(),user.getEmail());
	}
	
	public UnlikeNewsArticleEvent(Long newsArticleId, String emailAddress) 
	{
		this.newsArticleId=newsArticleId;
		this.emailAddress=emailAddress;
	}

	/**
	 * @return the newsArticleId
	 */
	public Long getNewsArticleId() {
		return newsArticleId;
	}

	/**
	 * @param newsArticleId the newsArticleId to set
	 */
	public void setNewsArticleId(Long newsArticleId) {
		this.newsArticleId = newsArticleId;
	}
}
