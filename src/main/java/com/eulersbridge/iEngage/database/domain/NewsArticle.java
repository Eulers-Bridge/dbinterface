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
	@RelatedTo(type = DatabaseDomainConstants.HAS_PHOTO_LABEL, direction=Direction.BOTH) @Fetch
	private Iterable<Photo> photos;
	@RelatedToVia(direction=Direction.BOTH, type=DatabaseDomainConstants.LIKES_LABEL)
	private Set<Like> likes;
	@Indexed @NotNull private Long date;
	@RelatedTo(type = DatabaseDomainConstants.CREATED_BY_LABEL, direction=Direction.BOTH) @Fetch
	private User creator;
	@RelatedTo(type = DatabaseDomainConstants.HAS_NEWS_LABEL, direction=Direction.BOTH) @Fetch
	private NewsFeed newsFeed;
	private boolean inappropriateContent;
	
	private static Logger LOG = LoggerFactory.getLogger(NewsArticle.class);
	
	public NewsArticle() 
	{
		if (LOG.isTraceEnabled()) LOG.trace("Constructor");
	}
	
	public NewsArticle(String title,String content, Calendar date, User creator)
	{
		if (LOG.isTraceEnabled()) LOG.trace("Constructor("+title+','+content+','+photos+','+date.toString()+','+creator+')');
		this.title=title;
		this.content=content;
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
	
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getContent()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getContent() = "+content);
		return content;
	}
	
	/**
	 * @param content the content to set
	 */
	public void setContent(String content)
	{
		this.content = content;
	}

	public Iterable<Photo> getPhotos()
	{
		if (LOG.isDebugEnabled()) LOG.debug("getPhotos() = "+photos);
		return photos;
	}
	
	public void setPhotos(Iterable<Photo> picture)
	{
		this.photos=picture;
		
	}
	
	/**
	 * @return the likes
	 */
	public Set<Like> getLikes() {
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
	
	/**
	 * @param date the date to set
	 */
	public void setDate(Long date)
	{
		this.date = date;
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

	/**
	 * @return the inappropriateContent
	 */
	public boolean isInappropriateContent()
	{
		return inappropriateContent;
	}

	/**
	 * @param inappropriateContent the inappropriateContent to set
	 */
	public void setInappropriateContent(boolean inappropriateContent)
	{
		this.inappropriateContent = inappropriateContent;
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
		buff.append(", photos = ");
		buff.append(getPhotos());
		buff.append(", date = ");
		buff.append(getDate());
		buff.append(", creator = ");
		buff.append(getCreator());
		buff.append(", studentYear = ");
		buff.append(getNewsFeed());
		buff.append(", pictures = ");
		buff.append(getPhotos());
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
	    if (likes==null)
	       	details.setLikes(0);
	    else details.setLikes(likes.size());
	    details.setPhotos(Photo.photosToPhotoDetails(getPhotos()));	
	    	
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
		    newsArt.date=newsArtDetails.getDate();
		    User creator=new User(newsArtDetails.getCreatorEmail(),null,null,null, null, null, null,null);
		    newsArt.creator=creator;
		    NewsFeed nf=new NewsFeed();
			newsArt.newsFeed=nf;
			newsArt.setInappropriateContent(newsArtDetails.isInappropriateContent());
		    if (LOG.isTraceEnabled()) LOG.trace("newsArt "+newsArt);

		    return newsArt;
		  }

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		if (nodeId!=null)
		{
			result = prime * result + nodeId.hashCode();
		}
		else
		{
			result = prime * result + ((content == null) ? 0 : content.hashCode());
			result = prime * result + ((creator == null) ? 0 : creator.hashCode());
			result = prime * result + ((date == null) ? 0 : date.hashCode());
			result = prime * result + ((likes == null) ? 0 : likes.hashCode());
			result = prime * result
					+ ((newsFeed == null) ? 0 : newsFeed.hashCode());
			result = prime * result + ((photos == null) ? 0 : photos.hashCode());
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
		NewsArticle other = (NewsArticle) obj;
		if (nodeId != null) 
		{
			if (nodeId.equals(other.nodeId))
				return true;
			else return false;
		}
		else
		{
			if (other.nodeId != null)
				return false;
			if (content == null) {
				if (other.content != null)
					return false;
			} else if (!content.equals(other.content))
				return false;
			if (creator == null) {
				if (other.creator != null)
					return false;
			} else if (!creator.equals(other.creator))
				return false;
			if (date == null) {
				if (other.date != null)
					return false;
			} else if (!date.equals(other.date))
				return false;
			if (likes == null) {
				if (other.likes != null)
					return false;
			} else if (!likes.equals(other.likes))
				return false;
			if (newsFeed == null) {
				if (other.newsFeed != null)
					return false;
			} else if (!newsFeed.equals(other.newsFeed))
				return false;
			if (photos == null) {
				if (other.photos != null)
					return false;
			} else if (!photos.equals(other.photos))
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
