/**
 * 
 */
package com.eulersbridge.iEngage.database.repository;

import org.springframework.data.neo4j.repository.GraphRepository;

import com.eulersbridge.iEngage.database.domain.Photo;

/**
 * @author Greg Newitt
 *
 */
public interface PhotoRepository extends GraphRepository<Photo>
{

}
