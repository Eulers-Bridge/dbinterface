package com.eulersbridge.iEngage.rest.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.ResourceSupport;

import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticleDetails;

public class NewsArticle extends ResourceSupport
{
	private Long articleId;
	private String title;
	private String content;
	private Iterable<String> picture;
	private Iterable<String> likers;
	private Long date;
	private String creatorEmail;

    private static Logger LOG = LoggerFactory.getLogger(NewsArticle.class);

    public static NewsArticle fromNewsArticleDetails(NewsArticleDetails newsArticleDetails) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	public NewsArticleDetails toNewsArticleDetails() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the articleId
	 */
	public Long getArticleId() {
		return articleId;
	}

	/**
	 * @param articleId the articleId to set
	 */
	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the picture
	 */
	public Iterable<String> getPicture() {
		return picture;
	}

	/**
	 * @param picture the picture to set
	 */
	public void setPicture(Iterable<String> picture) {
		this.picture = picture;
	}

	/**
	 * @return the likers
	 */
	public Iterable<String> getLikers() {
		return likers;
	}

	/**
	 * @param likers the likers to set
	 */
	public void setLikers(Iterable<String> likers) {
		this.likers = likers;
	}

	/**
	 * @return the date
	 */
	public Long getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Long date) {
		this.date = date;
	}

	/**
	 * @return the creatorEmail
	 */
	public String getCreatorEmail() {
		return creatorEmail;
	}

	/**
	 * @param creatorEmail the creatorEmail to set
	 */
	public void setCreatorEmail(String creatorEmail) {
		this.creatorEmail = creatorEmail;
	}
	public String toString()
	{
		StringBuffer buff=new StringBuffer("[ articleId = ");
		String retValue;
		buff.append(getArticleId());
		buff.append(", title = ");
		buff.append(getTitle());
		buff.append(", content = ");
		buff.append(getContent());
		buff.append(", picture = ");
		buff.append(getPicture());
		buff.append(", likers = ");
		buff.append(getLikers());
		buff.append(", date = ");
		buff.append(getDate());
		buff.append(", creator email = ");
		buff.append(getCreatorEmail());
		buff.append(" ]");
		retValue=buff.toString();
		if (LOG.isDebugEnabled()) LOG.debug("toString() = "+retValue);
		return retValue;
	}

}
