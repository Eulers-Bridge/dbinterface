/**
 * 
 */
package com.eulersbridge.iEngage.core.events.newsArticles;

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
	
	private Iterable<NewsArticleDetails> articles;

	public NewsArticlesReadEvent()
	{
		super();
	}
	
	public NewsArticlesReadEvent(Long instId,Iterable<NewsArticleDetails>articles)
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
	public Iterable<NewsArticleDetails> getArticles() {
		return articles;
	}

	/**
	 * @param articles the articles to set
	 */
	public void setArticles(Iterable<NewsArticleDetails> articles) {
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
		return nare;
	}
}
