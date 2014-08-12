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
	private boolean studentYearFound=true;
	private boolean institutionFound=true;
	private boolean articlesFound=true;
	
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

	/**
	 * @return the studentYearFound
	 */
	public boolean isStudentYearFound() {
		return studentYearFound;
	}

	/**
	 * @param studentYearFound the studentYearFound to set
	 */
	public void setStudentYearFound(boolean studentYearFound) {
		this.studentYearFound = studentYearFound;
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

	public static NewsArticlesReadEvent studentYearNotFound() 
	{
		// TODO Auto-generated method stub
		return null;
	}

}
