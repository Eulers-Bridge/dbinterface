package com.eulersbridge.iEngage.database.repository;

import org.springframework.data.neo4j.repository.GraphRepository;

import com.eulersbridge.iEngage.database.domain.Election;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.query.Param;

public interface ElectionRepository extends GraphRepository<Election>
{
    @Query("START tag=node({id}) MATCH (n:Election) WHERE n.start<tag.start RETURN n ORDER BY n.start DESC LIMIT 1")
    Election findPreviousElection(@Param("id")Long id);

    @Query("START tag=node({id}) MATCH (n:Election) WHERE n.start>tag.start RETURN n ORDER BY n.start LIMIT 1")
    Election findNextElection(@Param("id")Long id);
}
