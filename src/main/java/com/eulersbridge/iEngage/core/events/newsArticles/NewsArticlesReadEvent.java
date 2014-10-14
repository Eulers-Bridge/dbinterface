/**
 * 
 */
package com.eulersbridge.iEngage.core.events.newsArticles;

import java.util.Collection;

import com.eulersbridge.iEngage.core.events.ReadEvent;

/**
 * @author Greg Newitt
 *
 */
public class NewsArticlesReadEvent extends ReadEvent 
{
	private Long instId;
	private boolean newsFeedFound=true;
	private boolean institutionFound=true;
	private boolean articlesFound=true;
	
	private Collection<NewsArticleDetails> articles;

	public NewsArticlesReadEvent()
	{
		super();
	}
	
	public NewsArticlesReadEvent(Long instId,Collection<NewsArticleDetails>articles)
	{
		this.instId=instId;
		this.articles=articles;
	}
	
	/**
	 * @return the instId
	 */
	public Long getInstId() {
		return instId;
	}

	/**
	 * @param instId the instId to set
	 */
	public void setInstId(Long instId) {
		this.instId = instId;
	}

	/**
	 * @return the articles
	 */
	public Collection<NewsArticleDetails> getArticles() {
		return articles;
	}

	/**
	 * @param articles the articles to set
	 */
	public void setArticles(Collection<NewsArticleDetails> articles) {
		this.articles = articles;
	}

	/**
	 * @return the newsFeedFound
	 */
	public boolean isNewsFeedFound() {
		return newsFeedFound;
	}

	/**
	 * @param newsFeedFound the studentYearFound to set
	 */
	public void setNewsFeedFound(boolean newsFeedFound) {
		this.newsFeedFound = newsFeedFound;
	}

	/**
	 * @return the institutionFound
	 */
	public boolean isInstitutionFound() {
		return institutionFound;
	}

	/**
	 * @param institutionFound the institutionFound to set
	 */
	public void setInstitutionFound(boolean institutionFound) {
		this.institutionFound = institutionFound;
	}

	/**
	 * @return the articlesFound
	 */
	public boolean isArticlesFound() {
		return articlesFound;
	}

	/**
	 * @param articlesFound the articlesFound to set
	 */
	public void setArticlesFound(boolean articlesFound) {
		this.articlesFound = articlesFound;
	}

	public static NewsArticlesReadEvent newsFeedNotFound() 
	{
		NewsArticlesReadEvent nare=new NewsArticlesReadEvent();
		nare.setNewsFeedFound(false);
		nare.setArticlesFound(false);
		nare.entityFound=false;
		return nare;
	}

	public static NewsArticlesReadEvent institutionNotFound() 
	{
		NewsArticlesReadEvent nare=new NewsArticlesReadEvent();
		nare.setInstitutionFound(false);
		nare.setNewsFeedFound(false);
		nare.setArticlesFound(false);
		nare.entityFound=false;
		return nare;
	}
}
