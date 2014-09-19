package com.eulersbridge.iEngage.database.repository;

import org.springframework.data.neo4j.repository.GraphRepository;

import com.eulersbridge.iEngage.database.domain.Personality;

public interface PersonalityRepository extends GraphRepository<Personality>
{

}
