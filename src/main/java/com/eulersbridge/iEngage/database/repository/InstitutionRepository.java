package com.eulersbridge.iEngage.database.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.eulersbridge.iEngage.database.domain.Institution;

public interface InstitutionRepository extends GraphRepository<Institution> 
{
	   static Logger LOG = LoggerFactory.getLogger(InstitutionRepository.class);
}
