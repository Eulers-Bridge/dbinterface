package com.eulersbridge.iEngage.core.events.newsArticles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eulersbridge.iEngage.core.events.CreateEvent;

public class CreateNewsArticleEvent extends CreateEvent 
{
	NewsArticleDetails newsArticleDetails;
	
    private static Logger LOG = LoggerFactory.getLogger(CreateNewsArticleEvent.class);
    
    public CreateNewsArticleEvent(Long id, NewsArticleDetails newsArticleDetails) 
	{
		if (LOG.isDebugEnabled()) LOG.debug("CreateNewsArticleEvent("+id+","+newsArticleDetails+") = ");
		newsArticleDetails.setNewsArticleId(id);
		this.newsArticleDetails=newsArticleDetails;
	}

	public CreateNewsArticleEvent(NewsArticleDetails newsArticleDetails) 
	{
		if (LOG.isDebugEnabled()) LOG.debug("CreateNewsArticleEvent("+newsArticleDetails+") = ");
		this.newsArticleDetails=newsArticleDetails;
	}

	public NewsArticleDetails getNewsArticleDetails() {
		return this.newsArticleDetails;
	}

	public void setNewsArticleDetails(NewsArticleDetails newsArticleDetails) {
		this.newsArticleDetails = newsArticleDetails;
	}
}
