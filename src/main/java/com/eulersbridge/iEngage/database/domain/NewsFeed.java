package com.eulersbridge.iEngage.database.domain;

import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import com.eulersbridge.iEngage.core.events.newsFeed.NewsFeedDetails;

@NodeEntity
public class NewsFeed 
{
	@GraphId 
	Long nodeId;
	@RelatedTo(type = DatabaseDomainConstants.HAS_NEWS_FEED_LABEL, direction=Direction.BOTH) @Fetch
	private	Institution  institution;
	@RelatedTo(type = DatabaseDomainConstants.HAS_NEWS_LABEL, direction=Direction.BOTH)
	private	Set<NewsArticle>  news;
	
	public NewsFeed()
	{
		
	}
	
	public static NewsFeed fromDetails(NewsFeedDetails newsFeedDets) 
	{
		NewsFeed nf=new NewsFeed();
		Institution inst=new Institution();
		inst.setNodeId(newsFeedDets.getInstitutionId());
		nf.setInstitution(inst);
		nf.setNodeId(newsFeedDets.getNodeId());
		return nf;
	}
	
	public NewsFeedDetails toDetails()
	{
		NewsFeedDetails sfd=new NewsFeedDetails();
		if (getInstitution()!=null)
		sfd.setNodeId(getNodeId());
		{
			sfd.setInstitutionId(getInstitution().getNodeId());
		}
		return sfd;
	}

	/**
	 * @return the nodeId
	 */
	public Long getNodeId() {
		return nodeId;
	}

	/**
	 * @param nodeId the nodeId to set
	 */
	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	/**
	 * @return the institution
	 */
	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution inst) 
	{
		this.institution=inst;
	}

	/**
	 * @return the news
	 */
	public Set<NewsArticle> getNews() {
		return news;
	}

	/**
	 * @param news the news to set
	 */
	public void setNews(Set<NewsArticle> news) {
		this.news = news;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() 
	{
		StringBuffer buff=new StringBuffer("NewsFeed [nodeId=" + nodeId + ", institution=");
		if (institution!=null)
			buff.append(institution.getNodeId());
		else buff.append(institution);
		buff.append(']');
		return buff.toString();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		if (nodeId!=null)
		{
			result = prime * result + nodeId.hashCode();
		}
		else
		{
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
