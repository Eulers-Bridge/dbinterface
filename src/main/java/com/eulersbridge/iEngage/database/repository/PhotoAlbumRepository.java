/**
 * 
 */
package com.eulersbridge.iEngage.database.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import com.eulersbridge.iEngage.database.domain.DatabaseDomainConstants;
import com.eulersbridge.iEngage.database.domain.PhotoAlbum;

/**
 * @author Greg Newitt
 *
 */
public interface PhotoAlbumRepository extends GraphRepository<PhotoAlbum>
{
	@Query ("MATCH (p:`"+DatabaseDomainConstants.PHOTO_ALBUM+"`)-[r:`"+DatabaseDomainConstants.HAS_PHOTO_ALBUM_LABEL+"`]-(o) where id(o)={ownerId} RETURN p")
	Page<PhotoAlbum> findByOwnerId(@Param("ownerId")Long ownerId,Pageable p);

}
