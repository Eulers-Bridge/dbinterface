/**
 *
 */
package com.eulersbridge.iEngage.database.repository;

import com.eulersbridge.iEngage.database.domain.DataConstants;
import com.eulersbridge.iEngage.database.domain.PhotoAlbum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Greg Newitt
 */
@Repository
public interface PhotoAlbumRepository extends GraphRepository<PhotoAlbum> {
  @Query("MATCH (p:`" + DataConstants.PHOTO_ALBUM + "`)-[r:`" + DataConstants.HAS_PHOTO_ALBUM_LABEL + "`]-(o) where id(o)={ownerId} RETURN p")
  Page<PhotoAlbum> findByOwnerId(@Param("ownerId") Long ownerId, Pageable p);

  @Query("MATCH (p:`" + DataConstants.PHOTO_ALBUM + "`)-[r:`" + DataConstants.CREATED_BY_LABEL + "`]-(o) where id(o)={creatorId} RETURN p")
  Page<PhotoAlbum> findByCreatorId(@Param("creatorId") Long creatorId, Pageable p);

}
