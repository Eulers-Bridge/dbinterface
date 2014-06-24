package com.eulersbridge.iEngage.database.repository;

import org.springframework.data.neo4j.repository.GraphRepository;
import com.eulersbridge.iEngage.database.domain.Institution;

public interface InstitutionRepository extends GraphRepository<Institution> 
{
}
