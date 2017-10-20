/**
 *
 */
package com.eulersbridge.iEngage.database.repository;

import com.eulersbridge.iEngage.database.domain.DataConstants;
import com.eulersbridge.iEngage.database.domain.Photo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Greg Newitt
 */
@Repository
public interface PhotoRepository extends GraphRepository<Photo> {
  @Query("MATCH (p:`" + DataConstants.PHOTO + "`)-[r:`" + DataConstants.HAS_PHOTO_LABEL + "`]-(o) where id(o)={ownerId} RETURN p")
  Page<Photo> findByOwnerId(@Param("ownerId") Long ownerId, Pageable p);

  @Query("MATCH (p:`" + DataConstants.PHOTO + "`)-[r:`" + DataConstants.HAS_PHOTO_LABEL + "`]-(o) where id(o)={ownerId} RETURN count(p)")
  Long deletePhotosByOwnerId(@Param("ownerId") Long ownerId);


  @Query("MATCH (i)-[*0..3]-(p:Photo) where id(i)={insId} return distinct p order by p.date DESC")
  List<Photo> findPhotosByInstitution(@Param("insId") Long insId);
}
