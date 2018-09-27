package com.eulersbridge.iEngage.database.repository;

import com.eulersbridge.iEngage.database.domain.Country;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.annotation.Depth;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends Neo4jRepository<Country, Long> {
  static Logger LOG = LoggerFactory.getLogger(CountryRepository.class);

  Country findByCountryName(String countryName);
  Country findByCountryName(String countryName, @Depth int i);
}
