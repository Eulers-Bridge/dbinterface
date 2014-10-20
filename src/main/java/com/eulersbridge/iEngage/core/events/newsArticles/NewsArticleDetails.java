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
	private Integer likes;
	private Long date;
	private String creatorEmail;
	private Long institutionId;
	
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
	public Integer getLikes()
	{
		return likes;
	}
	/**
	 * @param i the likers to set
	 */
	public void setLikes(Integer i) {
		this.likes = i;
	}
	/**
	 * @return the institutionId
	 */
	public Long getInstitutionId() {
		return institutionId;
	}
	/**
	 * @param institutionId the institutionId to set
	 */
	public void setInstitutionId(Long institutionId) {
		this.institutionId = institutionId;
	}
	
	@Override
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
		buff.append(", institutionId = ");
		buff.append(getInstitutionId());
		buff.append(", pictures = ");
		buff.append(getPicture());
		buff.append(", likers = ");
		buff.append(getLikes());
		buff.append(" ]");
		retValue=buff.toString();
		if (LOG.isDebugEnabled()) LOG.debug("toString() = "+retValue);
		return retValue;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		if (newsArticleId!=null)
		{
			result = prime * result	+ newsArticleId.hashCode();
		}
		else
		{
			result = prime * result + ((content == null) ? 0 : content.hashCode());
			result = prime * result
					+ ((creatorEmail == null) ? 0 : creatorEmail.hashCode());
			result = prime * result + ((date == null) ? 0 : date.hashCode());
			result = prime * result
					+ ((institutionId == null) ? 0 : institutionId.hashCode());
			result = prime * result + ((likes == null) ? 0 : likes.hashCode());
			result = prime * result + ((picture == null) ? 0 : picture.hashCode());
			result = prime * result + ((title == null) ? 0 : title.hashCode());
		}
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NewsArticleDetails other = (NewsArticleDetails) obj;
		
		if (other.getNewsArticleId()!=null)
		{
			if (other.getNewsArticleId().equals(getNewsArticleId()))
				return true;
			else return false;
		}
		else
		{
			if (newsArticleId != null) 
				return false;
			if (content == null) {
				if (other.content != null)
					return false;
			} else if (!content.equals(other.content))
				return false;
			if (creatorEmail == null) {
				if (other.creatorEmail != null)
					return false;
			} else if (!creatorEmail.equals(other.creatorEmail))
				return false;
			if (date == null) {
				if (other.date != null)
					return false;
			} else if (!date.equals(other.date))
				return false;
			if (institutionId == null) {
				if (other.institutionId != null)
					return false;
			} else if (!institutionId.equals(other.institutionId))
				return false;
			if (likes == null) {
				if (other.likes != null)
					return false;
			} else if (!likes.equals(other.likes))
				return false;
			if (picture == null) {
				if (other.picture != null)
					return false;
			} else if (!picture.equals(other.picture))
				return false;
			if (title == null) {
				if (other.title != null)
					return false;
			} else if (!title.equals(other.title))
				return false;
		}
		return true;
	}
}
