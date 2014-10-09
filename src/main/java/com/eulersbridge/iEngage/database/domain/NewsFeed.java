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
	
	@Override
	public boolean equals(Object other)
	{
		if (null == other) return false;
		if (other == this) return true;
		if (!(other instanceof NewsFeed)) return false;
		NewsFeed nf2=(NewsFeed) other;
		
		if ((nodeId!=null)&&(nodeId.equals(nf2.nodeId))) return true;
		return false;
	}

}
