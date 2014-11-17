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
import com.eulersbridge.iEngage.database.domain.Photo;

/**
 * @author Greg Newitt
 *
 */
public interface PhotoRepository extends GraphRepository<Photo>
{
	@Query ("MATCH (p:`"+DatabaseDomainConstants.PHOTO+"`)-[r:`"+DatabaseDomainConstants.HAS_PHOTO_LABEL+"`]-(o) where id(o)={ownerId} RETURN n")
	Page<Photo> findByOwnerId(@Param("ownerId")Long ownerId,Pageable p);
}
