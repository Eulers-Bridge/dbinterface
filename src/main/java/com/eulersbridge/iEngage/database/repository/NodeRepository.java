package com.eulersbridge.iEngage.database.repository;

import com.eulersbridge.iEngage.database.domain.DataConstants;
import com.eulersbridge.iEngage.database.domain.Node;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Yikai Gong
 */

@Repository
public interface NodeRepository extends Neo4jRepository<Node, Long> {

  @Query("Match (s),(t) where id(s)={sourceId} And id(t)={targetId} create unique (s)-[r:"+ DataConstants.CREATED_BY_LABEL+"]->(t)")
  void createdBy (@Param("sourceId") Long sourceId, @Param("targetId") Long targetId);

  @Query("Match (s),(t) where id(s)={sourceId} And id(t)={targetId} create unique (s)-[r:"+ DataConstants.HAS_NEWS_LABEL+"]->(t)")
  void hasNews (@Param("sourceId") Long sourceId, @Param("targetId") Long targetId);

  @Query("Match (s),(t) where id(s)={eventId} And id(t)={newsFeedId} create unique (s)-[r:"+ DataConstants.HAS_EVENT_LABEL+"]->(t)")
  void hasEvent (@Param("eventId") Long eventId, @Param("newsFeedId") Long newsFeedId);
}
