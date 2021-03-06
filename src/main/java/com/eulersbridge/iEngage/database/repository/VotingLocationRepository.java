/**
 *
 */
package com.eulersbridge.iEngage.database.repository;

import com.eulersbridge.iEngage.database.domain.DataConstants;
import com.eulersbridge.iEngage.database.domain.VotingLocation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Greg Newitt
 */
@Repository
public interface VotingLocationRepository extends
  Neo4jRepository<VotingLocation, Long> {

  @Query(value = "MATCH l=(p:`" + DataConstants.VOTING_LOCATION + "`)-[r:`" + DataConstants.HAS_VOTING_LOCATION_LABEL + "`]-(o) where id(o)={ownerId} RETURN l",
  countQuery = "MATCH (p:`" + DataConstants.VOTING_LOCATION + "`)-[r:`" + DataConstants.HAS_VOTING_LOCATION_LABEL + "`]-(o) where id(o)={ownerId} RETURN count(p)")
  Page<VotingLocation> findByInstitutionId(@Param("ownerId") Long ownerId, Pageable pageable);

  @Query("Match (a:`Election`),(b:`VotingLocation`) where id(a)={electionId} and id(b)={votingLocationId} CREATE UNIQUE a-[r:" + DataConstants.HAS_VOTING_BOOTH_LABEL +
    "]->b return b")
  VotingLocation addElection(@Param("votingLocationId") Long votingLocationId, @Param("electionId") Long electionId);

  @Query("Match (v:`VotingLocation`)-[r:" + DataConstants.HAS_VOTING_BOOTH_LABEL + "]-(e:`Election`) where id(v)={votingLocationId} and id(e)=electionId delete r return v")
  VotingLocation deleteElection(@Param("votingLocationId") Long votingLocationId, @Param("electionId") Long electionId);

  @Query(value = "MATCH (p:`" + DataConstants.VOTING_LOCATION + "`)-[r:`" + DataConstants.HAS_VOTING_BOOTH_LABEL + "`]-(o) where id(o)={ownerId} RETURN p",
  countQuery = "MATCH (p:`" + DataConstants.VOTING_LOCATION + "`)-[r:`" + DataConstants.HAS_VOTING_BOOTH_LABEL + "`]-(o) where id(o)={ownerId} RETURN count(p)")
  Page<VotingLocation> findByElectionId(@Param("ownerId") Long ownerId, Pageable pageable);

}
