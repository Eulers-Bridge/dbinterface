package com.eulersbridge.iEngage.database.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.eulersbridge.iEngage.database.domain.Country;

public interface CountryRepository extends GraphRepository<Country>
{
	   static Logger LOG = LoggerFactory.getLogger(CountryRepository.class);
}
