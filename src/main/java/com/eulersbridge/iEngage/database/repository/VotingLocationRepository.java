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
import com.eulersbridge.iEngage.database.domain.VotingLocation;

/**
 * @author Greg Newitt
 *
 */
public interface VotingLocationRepository extends
		GraphRepository<VotingLocation>
{

	@Query ("MATCH (p:`"+DatabaseDomainConstants.VOTING_LOCATION+"`)-[r:`"+DatabaseDomainConstants.HAS_VOTING_LOCATION_LABEL+"`]-(o) where id(o)={ownerId} RETURN p")
	Page<VotingLocation> findByOwnerId(@Param("ownerId")Long ownerId, Pageable pageable);

}
