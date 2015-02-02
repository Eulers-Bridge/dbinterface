package com.eulersbridge.iEngage.database.repository;

import com.eulersbridge.iEngage.database.domain.Candidate;
import com.eulersbridge.iEngage.database.domain.DatabaseDomainConstants;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author Yikai Gong
 */

public interface CandidateRepository extends GraphRepository<Candidate>
{

	@Query("Match (n:`"+DatabaseDomainConstants.ELECTION+"`)-[r:"+DatabaseDomainConstants.HAS_POSITION_LABEL+
			"]-(p:`"+DatabaseDomainConstants.POSITION+"`)-[q:"+DatabaseDomainConstants.HAS_CANDIDATE_LABEL+
				"]-(e:`"+DatabaseDomainConstants.CANDIDATE+"`) where id(n)={electionId} return e")
	Page<Candidate> findByElectionId(@Param("electionId")Long electionId, Pageable pageable);

	@Query("Match (n:`"+DatabaseDomainConstants.POSITION+"`)-[r:"+DatabaseDomainConstants.HAS_CANDIDATE_LABEL+
			"]-(e:`"+DatabaseDomainConstants.CANDIDATE+"`) where id(n)={positionId} return e")
	Page<Candidate> findByPositionId(@Param("positionId")Long positionId, Pageable pageable);

}
