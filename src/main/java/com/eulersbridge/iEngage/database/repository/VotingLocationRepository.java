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
	Page<VotingLocation> findByInstitutionId(@Param("ownerId")Long ownerId, Pageable pageable);

	@Query("Match (a:`Election`),(b:`VotingLocation`) where id(a)={electionId} and id(b)={votingLocationId} CREATE UNIQUE a-[r:"+DatabaseDomainConstants.HAS_VOTING_BOOTH_LABEL+
			"]->b return b")
	VotingLocation addElection(@Param("votingLocationId")Long votingLocationId, @Param("electionId")Long electionId);
	
	@Query("Match (v:`VotingLocation`)-[r:"+DatabaseDomainConstants.HAS_VOTING_BOOTH_LABEL+"]-(e:`Election`) where id(v)={votingLocationId} and id(e)=electionId delete r return v")
	VotingLocation deleteElection(@Param("votingLocationId")Long votingLocationId, @Param("electionId")Long electionId);

	@Query ("MATCH (p:`"+DatabaseDomainConstants.VOTING_LOCATION+"`)-[r:`"+DatabaseDomainConstants.HAS_VOTING_BOOTH_LABEL+"`]-(o) where id(o)={ownerId} RETURN p")
	Page<VotingLocation> findByElectionId(@Param("ownerId")Long ownerId, Pageable pageable);

}
