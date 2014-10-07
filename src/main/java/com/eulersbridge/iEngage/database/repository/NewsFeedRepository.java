package com.eulersbridge.iEngage.database.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import com.eulersbridge.iEngage.database.domain.DatabaseDomainConstants;
import com.eulersbridge.iEngage.database.domain.NewsFeed;

public interface NewsFeedRepository extends GraphRepository<NewsFeed> 
{
	   static Logger LOG = LoggerFactory.getLogger(NewsFeedRepository.class);

		@Query("MATCH (i:`Institution`)-[r:"+DatabaseDomainConstants.HAS_NEWS_FEED_LABEL+"]-(s:`"+DatabaseDomainConstants.NEWS_FEED+
										"`) where ID(i)={instId} return s")
		NewsFeed findNewsFeed(@Param("instId") Long institutionId);
}
