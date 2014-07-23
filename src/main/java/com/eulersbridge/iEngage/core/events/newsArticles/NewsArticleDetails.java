package com.eulersbridge.iEngage.core.events.newsArticles;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NewsArticleDetails {
	
	private Long newsArticleId;
	private String title;
	private String content;
	private String picture;
	private Calendar date;
	private String creator;
	
	private static Logger LOG = LoggerFactory.getLogger(NewsArticleDetails.class);
	
	public NewsArticleDetails(Long id)
	{
		this.newsArticleId = id;
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
	public String getPicture()
	{
		return this.picture;
	}
	public void setPicture(String picture)
	{
		this.picture = picture;
	}
	public Calendar getDate()
	{
		return this.date;
	}
	public void setDate(Calendar date)
	{
		this.date = date;
	}
	public String getCreator()
	{
		return this.creator;
	}
	public void setCreator(String creator)
	{
		this.creator = creator;
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
		buff.append(getCreator());
		buff.append(" ]");
		retValue=buff.toString();
		if (LOG.isDebugEnabled()) LOG.debug("toString() = "+retValue);
		return retValue;
	}
}
