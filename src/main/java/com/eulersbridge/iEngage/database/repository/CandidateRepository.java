package com.eulersbridge.iEngage.database.repository;

import com.eulersbridge.iEngage.database.domain.Candidate;
import com.eulersbridge.iEngage.database.domain.DatabaseDomainConstants;

import com.eulersbridge.iEngage.database.domain.IsOnTicket;
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

	@Query("Match (n:`"+DatabaseDomainConstants.CANDIDATE+"`)-[r:"+DatabaseDomainConstants.IS_ON_TICKET_LABEL+
			"]-(e:`"+DatabaseDomainConstants.TICKET+"`) where id(e)={ticketId} return n")
	Page<Candidate> findByTicketId(@Param("ticketId")Long ticketId, Pageable pageable);

    @Query("Match (a:`"+DatabaseDomainConstants.CANDIDATE+"`),(b:`"+DatabaseDomainConstants.TICKET+"`) where id(a)={candidateId} and id(b)={ticketId} CREATE UNIQUE a-[r:IS_ON_TICKET]-b SET r.__type__='IsOnTicket' return r")
    IsOnTicket createIsOnTicketRelationship(@Param("candidateId")Long candidateId, @Param("ticketId")Long ticketId);

    @Query("Match (a:`"+DatabaseDomainConstants.CANDIDATE+"`)-[r:IS_ON_TICKET]-(b:`"+DatabaseDomainConstants.TICKET+"`) where id(a)={candidateId} and id(b)={ticketId} delete r")
    void deleteIsOnTicketRelationship(@Param("candidateId")Long candidateId, @Param("ticketId")Long ticketId);

}
