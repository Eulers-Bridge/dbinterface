package com.eulersbridge.iEngage.database.repository;

import com.eulersbridge.iEngage.database.domain.Personality;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalityRepository extends Neo4jRepository<Personality, Long> {

}
