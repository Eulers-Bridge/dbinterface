package com.eulersbridge.iEngage.database.repository;

import com.eulersbridge.iEngage.database.domain.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Yikai Gong
 */
@Repository
public interface ConfigurationRepository extends Neo4jRepository<Configuration, Long> {
  static Logger LOG = LoggerFactory.getLogger(ConfigurationRepository.class);

}
