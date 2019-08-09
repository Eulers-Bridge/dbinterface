/**
 *
 */
package com.eulersbridge.iEngage.database.repository;

import com.eulersbridge.iEngage.database.domain.DataConstants;
import com.eulersbridge.iEngage.database.domain.Photo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Greg Newitt
 */
@Repository
public interface PhotoRepository extends Neo4jRepository<Photo, Long> {
  @Query(value = "MATCH (p:`" + DataConstants.PHOTO + "`)-[r:`" + DataConstants.HAS_PHOTO_LABEL + "`]-(o) where id(o)={ownerId} RETURN p",
    countQuery = "MATCH (p:`" + DataConstants.PHOTO + "`)-[r:`" + DataConstants.HAS_PHOTO_LABEL + "`]-(o) where id(o)={ownerId} RETURN count(p)")
  Page<Photo> findByOwnerId(@Param("ownerId") Long ownerId, Pageable p);

  @Query("MATCH (p:`" + DataConstants.PHOTO + "`)-[r:`" + DataConstants.HAS_PHOTO_LABEL + "`]-(o) where id(o)={ownerId} RETURN count(p)")
  Long deletePhotosByOwnerId(@Param("ownerId") Long ownerId);


  @Query("MATCH (i)-[*0..3]-(p:Photo) where id(i)={insId} return distinct p order by p.date DESC")
  List<Photo> findPhotosByInstitution(@Param("insId") Long insId);

  @Query("Match (p:"+ DataConstants.PHOTO +"), (o) where id(p)={photoId} and id(o)={ownerId} create unique (o)-[r:"+ DataConstants.HAS_PHOTO_LABEL+"]->(p)")
  void setOwner(@Param("photoId") Long photoId, @Param("ownerId") Long ownerId);
}
