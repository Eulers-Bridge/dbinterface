package com.eulersbridge.iEngage.database.domain;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticleDetails;

@NodeEntity
public class NewsArticle {

	@GraphId Long nodeId;
	private String title;
	private String content;
	private String picture;
	private Calendar date;
	private String creator;
	
	private static Logger LOG = LoggerFactory.getLogger(NewsArticle.class);
	
	public NewsArticle() 
	{
		if (LOG.isTraceEnabled()) LOG.trace("Constructor");
	}
	
	public NewsArticle(String title,String content,String picture, Calendar date, String creator)
	{
		if (LOG.isTraceEnabled()) LOG.trace("Constructor("+title+','+content+','+picture+','+date.toString()+','+creator+')');
		this.title=title;
		this.content=content;
		this.picture=picture;
		this.date=date;
		this.creator=creator;
	}
	
	public Long getNodeId()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getNodeId() = "+nodeId);
		return nodeId;
	}
	
	public String getTitle()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getTitle() = "+title);
		return title;
	}
	
	public String getContent()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getContent() = "+content);
		return content;
	}
	
	public String getPicture()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getPicture() = "+picture);
		return picture;
	}
	
	public Calendar getDate()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getDate() = "+date.toString());
		return date;
	}
	
	public String getCreator()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getCreator() = "+creator);
		return creator;
	}
	
	public String toString()
	{
		StringBuffer buff=new StringBuffer("[ nodeId = ");
		String retValue;
		buff.append(getNodeId());
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
	
	public NewsArticleDetails toNewsArticleDetails() 
	{
	    if (LOG.isTraceEnabled()) LOG.trace("toNewsArtDetails()");
	    
	    NewsArticleDetails details = new NewsArticleDetails(getNodeId());
	    if (LOG.isTraceEnabled()) LOG.trace("newsArticle "+this);

	    BeanUtils.copyProperties(this, details);
	    if (LOG.isTraceEnabled()) LOG.trace("newsArticleDetails "+details);

	    return details;
	}

	  public static NewsArticle fromNewsArticleDetails(NewsArticleDetails newsArtDetails) 
	  {
		    if (LOG.isTraceEnabled()) LOG.trace("fromNewsArticleDetails()");

		    NewsArticle newsArt = new NewsArticle();
		    if (LOG.isTraceEnabled()) LOG.trace("newsArtDetails "+newsArtDetails);
		    newsArt.nodeId=newsArtDetails.getNewsArticleId();
		    newsArt.title=newsArtDetails.getTitle();
		    newsArt.content=newsArtDetails.getContent();
		    newsArt.picture=newsArtDetails.getPicture();
		    newsArt.date=newsArtDetails.getDate();
		    newsArt.creator=newsArtDetails.getCreator();
		    if (LOG.isTraceEnabled()) LOG.trace("newsArt "+newsArt);

		    return newsArt;
		  }
	  
	  public boolean equals(NewsArticle newsArticle2)
	  {
		  if ((nodeId!=null)&&(nodeId.equals(newsArticle2.nodeId))) return true;
		  else return false;
	  }

}
