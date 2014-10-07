package com.eulersbridge.iEngage.database.domain;

import java.util.Calendar;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.neo4j.graphdb.Direction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.annotation.RelatedToVia;

import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticleDetails;

@NodeEntity
public class NewsArticle extends Likeable
{
	@GraphId Long nodeId;
	private String title;
	private String content;
	private Iterable<String> picture;
	@RelatedToVia(direction=Direction.BOTH, type=DatabaseDomainConstants.LIKES_LABEL)
	private Set<Like> likes;
	@Indexed @NotNull private Long date;
	@RelatedTo(type = DatabaseDomainConstants.CREATED_BY_LABEL, direction=Direction.BOTH) @Fetch
	private User creator;
	@RelatedTo(type = DatabaseDomainConstants.HAS_NEWS_LABEL, direction=Direction.BOTH) @Fetch
	private NewsFeed newsFeed;
	
	private static Logger LOG = LoggerFactory.getLogger(NewsArticle.class);
	
	public NewsArticle() 
	{
		if (LOG.isTraceEnabled()) LOG.trace("Constructor");
	}
	
	public NewsArticle(String title,String content,Iterable<String> picture, Calendar date, User creator)
	{
		if (LOG.isTraceEnabled()) LOG.trace("Constructor("+title+','+content+','+picture+','+date.toString()+','+creator+')');
		this.title=title;
		this.content=content;
		this.picture=picture;
		this.date=date.getTimeInMillis();
		this.creator=creator;
	}
	
	public Long getNodeId()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getNodeId() = "+nodeId);
		return nodeId;
	}
	
	/**
	 * @param nodeId the nodeId to set
	 */
	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
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
	
	public Iterable<String> getPicture()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getPicture() = "+picture);
		return picture;
	}
	
	/**
	 * @return the likes
	 */
	public Iterable<Like> getLikes() {
		return likes;
	}
	
	public void setLikes(Set<Like> likes) 
	{
		this.likes=likes;
	}

	public boolean addLike(Like like)
	{
		boolean result=this.likes.add(like);
		return result;
	}

	public Long getDate()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getDate() = "+date.toString());
		return date;
	}
	
	public User getCreator()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getCreator() = "+creator);
		return creator;
	}
	
	public void setCreator(User creator) 
	{
		this.creator=creator;
	}

	/**
	 * @return the studentYear
	 */
	public NewsFeed getNewsFeed() 
	{
		return newsFeed;
	}

	/**
	 * @param newsFeed the studentYear to set
	 */
	public void setNewsFeed(NewsFeed newsFeed) 
	{
		this.newsFeed = newsFeed;
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
		buff.append(getDate());
		buff.append(", creator = ");
		buff.append(getCreator());
		buff.append(", studentYear = ");
		buff.append(getNewsFeed());
		buff.append(", pictures = ");
		buff.append(getPicture());
		buff.append(", likers = ");
		buff.append(getLikes());
		buff.append(" ]");
		retValue=buff.toString();
		if (LOG.isDebugEnabled()) LOG.debug("toString() = "+retValue);
		return retValue;
	}
	
	public NewsArticleDetails toNewsArticleDetails() 
	{
	    if (LOG.isTraceEnabled()) LOG.trace("toNewsArtDetails()");
	    
	    NewsArticleDetails details = new NewsArticleDetails();
	    details.setNewsArticleId(getNodeId());
	    if (LOG.isTraceEnabled()) LOG.trace("newsArticle "+this);

	    BeanUtils.copyProperties(this, details);
	    if (getCreator()!=null) details.setCreatorEmail(getCreator().getEmail());
	    if (getNewsFeed()!=null)
	    {
	    	if (getNewsFeed().getInstitution()!=null)
	    		details.setInstitutionId(getNewsFeed().getInstitution().getNodeId());
	    }
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
		    User creator=new User(newsArtDetails.getCreatorEmail(),null,null,null, null, null, null);
		    newsArt.creator=creator;
		    NewsFeed year=new NewsFeed();
			newsArt.newsFeed=year;
		    if (LOG.isTraceEnabled()) LOG.trace("newsArt "+newsArt);

		    return newsArt;
		  }
	
	@Override
	public boolean equals(Object other)
	{
		if (null == other) return false;
		if (other == this) return true;
		if (!(other instanceof NewsArticle)) return false;
		NewsArticle newsArticle2=(NewsArticle) other;

		if ((nodeId!=null)&&(nodeId.equals(newsArticle2.nodeId))) return true;
		return false;
	}

}
