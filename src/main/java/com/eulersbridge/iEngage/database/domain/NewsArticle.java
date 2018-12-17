package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.newsArticles.NewsArticleDetails;
import com.eulersbridge.iEngage.database.repository.NewsFeedRepository;
import com.eulersbridge.iEngage.database.repository.UserRepository;
import com.fasterxml.jackson.databind.deser.impl.CreatorCandidate;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.List;

@NodeEntity
public class NewsArticle extends Likeable {
  private String title;
  private String content;
  @Index
  @NotNull
  private Long date;
  private boolean inappropriateContent;

  @Relationship(type = DataConstants.HAS_PHOTO_LABEL)
  private List<Node> photos;
  @Relationship(type = DataConstants.CREATED_BY_LABEL, direction = Relationship.OUTGOING)
  private User creator;

  @Relationship(type = DataConstants.HAS_NEWS_LABEL, direction = Relationship.INCOMING)
  private NewsFeed newsFeed;


//  @Query("START n = node({self}) match (n)-[r:" + DatabaseDomainConstants.CREATED_BY_LABEL + "]-(c) RETURN c.email ")
//  private String creatorEmail;
//
//  @Query("START n=node({self}) " +
//    "match (n)-[r:" + DatabaseDomainConstants.HAS_NEWS_LABEL + "]" +
//    "-(f)-[r2:" + DatabaseDomainConstants.HAS_NEWS_FEED_LABEL + "]" +
//    "-(i:" + DatabaseDomainConstants.INSTITUTION + ") RETURN id(i)")
//  private Long InstitutionID;

  private static Logger LOG = LoggerFactory.getLogger(NewsArticle.class);

  public NewsArticle() {
    if (LOG.isDebugEnabled()) LOG.debug("Constructor");
  }

  public NewsArticle(String title, String content, Calendar date, User creator) {
    if (LOG.isTraceEnabled())
      LOG.trace("Constructor(" + title + ',' + content + ',' + photos + ',' + date.toString() + ',' + creator + ')');
    this.title = title;
    this.content = content;
    this.date = date.getTimeInMillis();
    this.creator = creator;
  }

  public String getTitle() {
    if (LOG.isDebugEnabled()) LOG.debug("getTitle() = " + title);
    return title;
  }

  /**
   * @param title the title to set
   */
  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    if (LOG.isDebugEnabled()) LOG.debug("getContent() = ");
    return content;
  }

  /**
   * @param content the content to set
   */
  public void setContent(String content) {
    this.content = content;
  }

  public Iterable<Photo> getPhotos$() {
    if (LOG.isDebugEnabled()) LOG.debug("getPhotos() = " + photos);
    return castList(photos, Photo.class);
  }

  public List<Node> getPhotos() {
    return photos;
  }

  public void setPhotos(List<Node> picture) {
    this.photos = picture;

  }

  public Long getDate() {
    if (LOG.isDebugEnabled()) LOG.debug("getDate() = " + date.toString());
    return date;
  }

  /**
   * @param date the date to set
   */
  public void setDate(Long date) {
    this.date = date;
  }

  public User getCreator$() {
    return (User) creator;
  }

  public Node getCreator() {
    return creator;
  }

  public void setCreator(User creator) {
    this.creator = creator;
  }


  public NewsFeed getNewsFeed$() {
    return (NewsFeed) newsFeed;
  }

  public Node getNewsFeed() {
    return newsFeed;
  }

  /**
   * @param newsFeed the studentYear to set
   */
  public void setNewsFeed(NewsFeed newsFeed) {
    this.newsFeed = newsFeed;
  }

  /**
   * @return the inappropriateContent
   */
  public boolean isInappropriateContent() {
    return inappropriateContent;
  }

  /**
   * @param inappropriateContent the inappropriateContent to set
   */
  public void setInappropriateContent(boolean inappropriateContent) {
    this.inappropriateContent = inappropriateContent;
  }

  public String toString() {
    String buff = "[ nodeId = " + getNodeId() +
      ", title = " +
      getTitle() +
      ", content = " +
      getContent() +
      ", photos = " +
      getPhotos() +
      ", date = " +
      getDate() +
      ", creator = " +
      getCreator() +
      ", studentYear = " +
      getNewsFeed() +
      ", pictures = " +
      getPhotos() +
      " ]";
    String retValue;
    retValue = buff;
    if (LOG.isDebugEnabled()) LOG.debug("toString() = " + retValue);
    return retValue;
  }

  public NewsArticleDetails toNewsArticleDetails() {
    NewsArticleDetails details = new NewsArticleDetails();
    details.setNewsArticleId(getNodeId());
    BeanUtils.copyProperties(this, details);

    if (creator != null) {
      System.out.println(creator.nodeId);
      details.setCreatorEmail(getCreator$().getEmail());
      details.setCreatorDetails(getCreator$().toUserDetails());
    }

    if (newsFeed != null) {
      NewsFeed newsFeed = getNewsFeed$();
      if (newsFeed.getInstitution() != null)
        details.setInstitutionId(newsFeed.getInstitution().getNodeId());
    }

    if (photos != null && photos.size() > 0 && photos.iterator().next() instanceof Photo)
      details.setPhotos(Photo.photosToPhotoDetails(getPhotos$()));

    details.setLikes(getNumOfLikes().intValue());

    return details;
  }

  public static NewsArticle fromNewsArticleDetails(NewsArticleDetails newsArtDetails) {
    if (LOG.isTraceEnabled()) LOG.trace("fromNewsArticleDetails()");

    NewsArticle newsArt = new NewsArticle();
    if (LOG.isTraceEnabled()) LOG.trace("newsArtDetails " + newsArtDetails);
    newsArt.nodeId = newsArtDetails.getNewsArticleId();
    newsArt.title = newsArtDetails.getTitle();
    newsArt.content = newsArtDetails.getContent();
    newsArt.date = newsArtDetails.getDate();
    newsArt.setInappropriateContent(newsArtDetails.isInappropriateContent());
    if (LOG.isTraceEnabled()) LOG.trace("newsArt " + newsArt);

    return newsArt;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    if (nodeId != null) {
      result = prime * result + nodeId.hashCode();
    } else {
      result = prime * result + ((content == null) ? 0 : content.hashCode());
      result = prime * result + ((creator == null) ? 0 : creator.hashCode());
      result = prime * result + ((date == null) ? 0 : date.hashCode());
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
    if (nodeId != null) {
      return nodeId.equals(other.nodeId);
    } else {
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
