/**
 * 
 */
package com.eulersbridge.iEngage.core.events.users;

import java.util.Iterator;

import com.eulersbridge.iEngage.core.events.Details;
import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticleDetails;

/**
 * @author Greg Newitt
 *
 */
public class LoginDetails extends Details
{
	private Iterator<NewsArticleDetails> articles;
	private UserDetails user;

	/**
	 * @param articles
	 * @param user
	 * @param userId
	 */
	public LoginDetails(Iterator<NewsArticleDetails> articles, UserDetails user, Long userId) 
	{
		super(userId);
		this.articles = articles;
		this.user = user;
	}

	public Iterator<NewsArticleDetails> getArticles() 
	{
		return this.articles;
	}

	public UserDetails getUser() 
	{
		return this.user;
	}

	/**
	 * @param articles the articles to set
	 */
	public void setArticles(Iterator<NewsArticleDetails> articles) 
	{
		this.articles = articles;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(UserDetails user) 
	{
		this.user = user;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId()
	{
		return getNodeId();
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId)
	{
		setNodeId(userId);
	}

}
