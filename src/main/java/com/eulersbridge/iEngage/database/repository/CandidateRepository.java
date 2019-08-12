package com.eulersbridge.iEngage.database.repository;

import com.eulersbridge.iEngage.database.domain.Candidate;
import com.eulersbridge.iEngage.database.domain.DataConstants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Yikai Gong
 */
@Repository
public interface CandidateRepository extends Neo4jRepository<Candidate, Long> {

  @Query(value = "Match (n:`" + DataConstants.ELECTION + "`)-[r:" + DataConstants.HAS_POSITION_LABEL +
    "]-(p:`" + DataConstants.POSITION + "`)-[q:" + DataConstants.HAS_CANDIDATE_LABEL +
    "]-(e:`" + DataConstants.CANDIDATE + "`) where id(n)={electionId} return distinct (e)-[*0..1]-(), e",
    countQuery = "Match (n:`" + DataConstants.ELECTION + "`)-[r:" + DataConstants.HAS_POSITION_LABEL +
      "]-(p:`" + DataConstants.POSITION + "`)-[q:" + DataConstants.HAS_CANDIDATE_LABEL +
      "]-(e:`" + DataConstants.CANDIDATE + "`) where id(n)={electionId} return count(distinct (e)-[*0..1]-())")
  Page<Candidate> findByElectionId(@Param("electionId") Long electionId, Pageable pageable);

  @Query(value = "Match (n:`" + DataConstants.POSITION + "`)-[r:" + DataConstants.HAS_CANDIDATE_LABEL +
    "]-(e:`" + DataConstants.CANDIDATE + "`) where id(n)={positionId} return distinct (e)-[*0..1]-(), e",
    countQuery = "Match (n:`" + DataConstants.POSITION + "`)-[r:" + DataConstants.HAS_CANDIDATE_LABEL +
      "]-(e:`" + DataConstants.CANDIDATE + "`) where id(n)={positionId} return count(distinct (e)-[*0..1]-())")
  Page<Candidate> findByPositionId(@Param("positionId") Long positionId, Pageable pageable);

  @Query(value = "Match (e:`" + DataConstants.CANDIDATE + "`)-[r:" + DataConstants.IS_ON_TICKET_LABEL +
    "]-(t:`" + DataConstants.TICKET + "`) where id(t)={ticketId} return distinct (e)-[*0..1]-(), e",
    countQuery = "Match (e:`" + DataConstants.CANDIDATE + "`)-[r:" + DataConstants.IS_ON_TICKET_LABEL +
      "]-(t:`" + DataConstants.TICKET + "`) where id(t)={ticketId} return count(distinct (e)-[*0..1]-())")
  Page<Candidate> findByTicketId(@Param("ticketId") Long ticketId, Pageable pageable);


  @Query(value = "Match l=(u:" + DataConstants.USER + ")-[r1:" + DataConstants.IS_CANDIDATE_LABEL + "]->(c:"
    + DataConstants.CANDIDATE + ")-[r2:" + DataConstants.HAS_CANDIDATE_LABEL + "]->(p:" + DataConstants.POSITION +
    ")-[r3:" + DataConstants.HAS_POSITION_LABEL + "]->(e:" + DataConstants.ELECTION
    + ") where id(u)={userId} and id(e)={electionId} return count(l)")
  Integer countUserToElectionChains(@Param("userId") Long userId, @Param("electionId") Long electionId);


  @Query(value = "Match l=(u:" + DataConstants.USER + ")-[r1:" + DataConstants.IS_CANDIDATE_LABEL
    + "]->(c:" + DataConstants.CANDIDATE + ")-[r2:" + DataConstants.IS_ON_TICKET_LABEL + "]->(t:"
    + DataConstants.TICKET + ") where id(u)={userId} and id(t)={ticketId} return count(l)")
  Integer countUserToTicketChains(@Param("userId") Long userId, @Param("ticketId") Long ticketId);
}
