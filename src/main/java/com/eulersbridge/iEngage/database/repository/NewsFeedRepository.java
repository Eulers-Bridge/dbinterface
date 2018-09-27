package com.eulersbridge.iEngage.database.repository;

import com.eulersbridge.iEngage.database.domain.DataConstants;
import com.eulersbridge.iEngage.database.domain.NewsFeed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsFeedRepository extends Neo4jRepository<NewsFeed, Long> {
  static Logger LOG = LoggerFactory.getLogger(NewsFeedRepository.class);

  @Query("MATCH (i:`Institution`)-[r:" + DataConstants.HAS_NEWS_FEED_LABEL + "]-(s:`" + DataConstants.NEWS_FEED +
    "`) where ID(i)={instId} return s")
  NewsFeed findNewsFeed(@Param("instId") Long institutionId);
}
