package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.newsFeed.NewsFeedDetails;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@NodeEntity
public class NewsFeed extends Node {
  @Relationship(type = DatabaseDomainConstants.HAS_NEWS_FEED_LABEL, direction = Relationship.INCOMING)
  private Node institution;
  @Relationship(type = DatabaseDomainConstants.HAS_NEWS_LABEL, direction = Relationship.OUTGOING)
  private List<Node> news;

  private static Logger LOG = LoggerFactory.getLogger(NewsFeed.class);

  public NewsFeed() {
    if (LOG.isDebugEnabled()) LOG.debug("Constructor");
  }

  public static NewsFeed fromDetails(NewsFeedDetails newsFeedDets) {
    NewsFeed nf = new NewsFeed();
    Institution inst = new Institution();
    inst.setNodeId(newsFeedDets.getInstitutionId());
    nf.setInstitution(inst);
    nf.setNodeId(newsFeedDets.getNodeId());
    return nf;
  }

  public NewsFeedDetails toDetails() {
    NewsFeedDetails sfd = new NewsFeedDetails();
    sfd.setNodeId(getNodeId());
    if (getInstitution() != null)
      sfd.setInstitutionId(getInstitution().getNodeId());

    return sfd;
  }

  /**
   * @return the institution
   */
  public Institution getInstitution() {
    return (Institution) institution;
  }

  public void setInstitution(Institution inst) {
    this.institution = inst;
  }

  /**
   * @return the news
   */
  public List<NewsArticle> getNews() {
    return castList(news, NewsArticle.class);
  }

  /**
   * @param news the news to set
   */
  public void setNews(List<Node> news) {
    this.news = news;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder buff = new StringBuilder("NewsFeed [nodeId=" + nodeId + ", institution=");
    if (institution != null)
      buff.append(institution.getNodeId());
    else buff.append((Object) null);
    buff.append(']');
    return buff.toString();
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
      result = prime * result
        + ((institution == null) ? 0 : institution.hashCode());
      result = prime * result + ((news == null) ? 0 : news.hashCode());
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
    NewsFeed other = (NewsFeed) obj;
    if (nodeId != null) {
      return nodeId.equals(other.nodeId);
    } else {
      if (other.nodeId != null)
        return false;
      if (institution == null) {
        if (other.institution != null)
          return false;
      } else if (!institution.equals(other.institution))
        return false;
      if (news == null) {
        if (other.news != null)
          return false;
      } else if (!news.equals(other.news))
        return false;
    }
    return true;
  }

}
