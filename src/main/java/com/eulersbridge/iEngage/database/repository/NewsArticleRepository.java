package com.eulersbridge.iEngage.database.repository;

import com.eulersbridge.iEngage.database.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Depth;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsArticleRepository extends GraphRepository<NewsArticle> {
  Iterable<NewsArticle> findByCreator(User creator);

  @Depth(value = 2)
  @Query("Match (n:`" + DataConstants.INSTITUTION + "`)-[r:" + DataConstants.HAS_NEWS_FEED_LABEL +
    "]-(f:`" + DataConstants.NEWS_FEED + "`)-[s:" + DataConstants.HAS_NEWS_LABEL +
    "]-(a:`NewsArticle`) where id(n)={instId} return distinct (a)-[*0..1]-(), (a)")
  Page<NewsArticle> findByInstitutionId(@Param("instId") Long instId, Pageable p);

  @Query("Match (a:`User`),(b) where a.email={email} and id(b)={likedId} CREATE UNIQUE a-[r:LIKES]->b SET r.timestamp=coalesce(r.timestamp,timestamp()),r.__type__='Like' return r")
  Like likeArticle(@Param("email") String email, @Param("likedId") Long likedId);

  @Query("Match (a:`User`)-[r:LIKES]-(b) where a.email={email} and id(b)={likedId} delete r")
  void unlikeArticle(@Param("email") String email, @Param("likedId") Long likedId);

  @Query("Match l=(a)-[:CREATED_BY]->(x),(u:User) where id(a)={articleId} and u.email={userEmail} with l, a, u , Exists ( (u)-[:HAS_READ]->(a) ) as hasRead " +
    "Merge (u)-[r:HAS_READ]->(a) ON CREATE SET r.timestamp = [timestamp()] ON MATCH SET r.timestamp = r.timestamp + timestamp() return l")
  NewsArticle readOne(@Param("articleId") Long articleId, @Param("userEmail") String userEmail);
}
