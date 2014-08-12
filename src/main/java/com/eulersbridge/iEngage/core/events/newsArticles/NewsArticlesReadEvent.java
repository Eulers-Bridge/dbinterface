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
	private Long syId;
	
	private Iterable<NewsArticleDetails> articles;

	public NewsArticlesReadEvent()
	{
		super();
	}
	
	public NewsArticlesReadEvent(Long instId,Long syId,Iterable<NewsArticleDetails>articles)
	{
		this.instId=instId;
		this.syId=syId;
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
	 * @return the syId
	 */
	public Long getSyId() {
		return syId;
	}

	/**
	 * @param syId the syId to set
	 */
	public void setSyId(Long syId) {
		this.syId = syId;
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

}
