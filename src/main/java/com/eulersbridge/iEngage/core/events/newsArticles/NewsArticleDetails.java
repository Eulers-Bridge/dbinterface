package com.eulersbridge.iEngage.core.events.newsArticles;

import java.util.Calendar;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NewsArticleDetails {
	
	private Long newsArticleId;
	private String title;
	private String content;
	private Set<String> picture;
	private Set<String> likers;
	private Long date;
	private String creatorEmail;
	private String studentYear;
	
	private static Logger LOG = LoggerFactory.getLogger(NewsArticleDetails.class);
	
	public NewsArticleDetails()
	{
	}
	public Long getNewsArticleId()
	{
		return this.newsArticleId;
	}
	public void setNewsArticleId(Long newsArticleId)
	{
		this.newsArticleId = newsArticleId;
	}
	public String getTitle()
	{
		return this.title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public String getContent()
	{
		return this.content;
	}
	public void setContent(String content)
	{
		this.content = content;
	}
	public Set<String> getPicture()
	{
		return this.picture;
	}
	public void setPicture(Set<String> picture)
	{
		this.picture = picture;
	}
	public Long getDate()
	{
		return this.date;
	}
	public void setDate(Long date)
	{
		this.date = date;
	}
	public void setDate(Calendar date)
	{
		this.date = date.getTimeInMillis();
	}
	public String getCreatorEmail()
	{
		return this.creatorEmail;
	}
	public void setCreatorEmail(String creatorEmail)
	{
		this.creatorEmail = creatorEmail;
	}
	/**
	 * @return the likers
	 */
	public Set<String> getLikers() {
		return likers;
	}
	/**
	 * @param likers the likers to set
	 */
	public void setLikers(Set<String> likers) {
		this.likers = likers;
	}
	/**
	 * @return the studentYear
	 */
	public String getStudentYear() {
		return studentYear;
	}
	/**
	 * @param studentYear the studentYear to set
	 */
	public void setStudentYear(String studentYear) {
		this.studentYear = studentYear;
	}
	public String toString()
	{
		StringBuffer buff=new StringBuffer("[ id = ");
		String retValue;
		buff.append(getNewsArticleId());
		buff.append(", title = ");
		buff.append(getTitle());
		buff.append(", content = ");
		buff.append(getContent());
		buff.append(", picture = ");
		buff.append(getPicture());
		buff.append(", date = ");
		buff.append(getDate().toString());
		buff.append(", creator = ");
		buff.append(getCreatorEmail());
		buff.append(" ]");
		retValue=buff.toString();
		if (LOG.isDebugEnabled()) LOG.debug("toString() = "+retValue);
		return retValue;
	}
}
