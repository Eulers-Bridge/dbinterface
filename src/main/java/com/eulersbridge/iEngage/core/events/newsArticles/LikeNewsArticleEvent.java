/**
 * 
 */
package com.eulersbridge.iEngage.core.events.newsArticles;

import com.eulersbridge.iEngage.database.domain.NewsArticle;
import com.eulersbridge.iEngage.database.domain.User;

/**
 * @author Greg Newitt
 *
 */
public class LikeNewsArticleEvent 
{
	Long newsArticleId;
	String emailAddress;

	public LikeNewsArticleEvent(NewsArticle newsArticle, User user) 
	{
		this(newsArticle.getNodeId(),user.getEmail());
	}
	
	public LikeNewsArticleEvent(Long newsArticleId, String emailAddress) 
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

	/**
	 * @return the userId
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	

}
