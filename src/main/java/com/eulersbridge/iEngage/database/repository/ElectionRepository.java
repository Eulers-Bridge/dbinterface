package com.eulersbridge.iEngage.database.repository;

import com.eulersbridge.iEngage.database.domain.DataConstants;
import com.eulersbridge.iEngage.database.domain.Election;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElectionRepository extends Neo4jRepository<Election, Long> {
  @Query("START tag=node({id}) MATCH (n:Election) WHERE n.start<tag.start RETURN n ORDER BY n.start DESC LIMIT 1")
  Election findPreviousElection(@Param("id") Long id);

  @Query("START tag=node({id}) MATCH (n:Election) WHERE n.start>tag.start RETURN n ORDER BY n.start LIMIT 1")
  Election findNextElection(@Param("id") Long id);

  @Query("Match l=(n:`" + DataConstants.INSTITUTION + "`)-[r:" + DataConstants.HAS_ELECTION_LABEL +
    "]-(e:`Election`) where id(n)={instId} return l")
  List<Election> findByInstitutionId(@Param("instId") Long instId);


  @Query(value = "Match (p:'" + DataConstants.POSITION + "')-[r:'" + DataConstants.HAS_POSITION_LABEL + "']->(e:'"
    + DataConstants.ELECTION + "') where id(p)={positionId} return e")
  Election findByPositionId(@Param("positionId") Long positionId);
}
