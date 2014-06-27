package com.eulersbridge.iEngage.database.domain;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class NewsArticle {

	private String id;
	private String title;
	private String content;
	private String picture;
	private Date date;
	private String creator;
	private Date created;
	private Date modified;
	
	private static Logger LOG = LoggerFactory.getLogger(NewsArticle.class);
	
	public NewsArticle() 
	{
		if (LOG.isTraceEnabled()) LOG.trace("Constructor");
	}
	
	public NewsArticle(String id, String title,String content,String picture, Date date, String creator, Date created, Date modified)
	{
		if (LOG.isTraceEnabled()) LOG.trace("Constructor("+id+','+title+','+content+','+picture+','+date.toString()+','+creator+','+created.toString()+','+modified.toString()+')');
		this.id=id;
		this.title=title;
		this.content=content;
		this.picture=picture;
		this.date=date;
		this.creator=creator;
		this.created=created;
		this.modified=modified;
	}
	
	public String getId()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getId() = "+id);
		return id;
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
	
	public Date getDate()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getDate() = "+date.toString());
		return date;
	}
	
	public String getCreator()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getCreator() = "+creator);
		return creator;
	}
	
	public Date getCreated()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getCreated() = "+created.toString());
		return created;
	}
	
	public Date getModified()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getModified() = "+modified);
		return modified;
	}
	
	public String toString()
	{
		StringBuffer buff=new StringBuffer("[ id = ");
		String retValue;
		buff.append(id);
		buff.append(", title = ");
		buff.append(title);
		buff.append(", content = ");
		buff.append(content);
		buff.append(", picture = ");
		buff.append(picture);
		buff.append(", date = ");
		buff.append(date.toString());
		buff.append(", creator = ");
		buff.append(creator);
		buff.append(", created = ");
		buff.append(created.toString());
		buff.append(", modified = ");
		buff.append(modified.toString());
		buff.append(" ]");
		retValue=buff.toString();
		if (LOG.isDebugEnabled()) LOG.debug("toString() = "+retValue);
		return retValue;
	}	
	

}
