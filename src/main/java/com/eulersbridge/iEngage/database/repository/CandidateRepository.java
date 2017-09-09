package com.eulersbridge.iEngage.database.repository;

import com.eulersbridge.iEngage.database.domain.Candidate;
import com.eulersbridge.iEngage.database.domain.DataConstants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Yikai Gong
 */
@Repository
public interface CandidateRepository extends GraphRepository<Candidate> {

  @Query("Match (n:`" + DataConstants.ELECTION + "`)-[r:" + DataConstants.HAS_POSITION_LABEL +
    "]-(p:`" + DataConstants.POSITION + "`)-[q:" + DataConstants.HAS_CANDIDATE_LABEL +
    "]-(e:`" + DataConstants.CANDIDATE + "`) where id(n)={electionId} return distinct (e)-[*0..1]-(), e")
  Page<Candidate> findByElectionId(@Param("electionId") Long electionId, Pageable pageable);

  @Query("Match (n:`" + DataConstants.POSITION + "`)-[r:" + DataConstants.HAS_CANDIDATE_LABEL +
    "]-(e:`" + DataConstants.CANDIDATE + "`) where id(n)={positionId} return distinct (e)-[*0..1]-(), e")
  Page<Candidate> findByPositionId(@Param("positionId") Long positionId, Pageable pageable);

  @Query("Match (e:`" + DataConstants.CANDIDATE + "`)-[r:" + DataConstants.IS_ON_TICKET_LABEL +
    "]-(t:`" + DataConstants.TICKET + "`) where id(t)={ticketId} return distinct (e)-[*0..1]-(), e")
  Page<Candidate> findByTicketId(@Param("ticketId") Long ticketId, Pageable pageable);
}
