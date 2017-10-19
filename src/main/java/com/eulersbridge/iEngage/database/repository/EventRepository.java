package com.eulersbridge.iEngage.database.repository;

import com.eulersbridge.iEngage.database.domain.DataConstants;
import com.eulersbridge.iEngage.database.domain.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Yikai Gong
 */
@Repository
public interface EventRepository extends GraphRepository<Event> {
  static Logger LOG = LoggerFactory.getLogger(EventRepository.class);

//  @Query("Match (n:Institution)-[r:HAS_NEWS_FEED]-(f:NewsFeed)-[s:HAS_EVENT]-(e:Event) where id(n)={instId} with (e) match l=(e)-[*0..1]-(x), (e)  return l")
//  Page<Event> findByInstitutionId(@Param("instId") Long instId, Pageable p);

  @Query("Match (n:Institution)-[r:HAS_NEWS_FEED]-(f:NewsFeed)-[s:HAS_EVENT]-(e:Event) where id(n)={instId} with (e) ORDER BY e.starts DESC SKIP {skip} LIMIT {pgSize} match l=(e)-[*0..1]-(x)  return l")
  List<Event> findByInstitutionId(@Param("instId") Long instId, @Param("skip") int skip, @Param("pgSize") int pgSize, @Param("direction") String direction);
}
