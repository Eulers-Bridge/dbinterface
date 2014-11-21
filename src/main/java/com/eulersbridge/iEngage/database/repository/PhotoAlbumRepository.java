/**
 * 
 */
package com.eulersbridge.iEngage.database.repository;

import org.springframework.data.neo4j.repository.GraphRepository;
import com.eulersbridge.iEngage.database.domain.PhotoAlbum;

/**
 * @author Greg Newitt
 *
 */
public interface PhotoAlbumRepository extends GraphRepository<PhotoAlbum>
{

}
