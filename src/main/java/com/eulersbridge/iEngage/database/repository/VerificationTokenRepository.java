package com.eulersbridge.iEngage.database.repository;

import com.eulersbridge.iEngage.database.domain.VerificationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends Neo4jRepository<VerificationToken, Long> {
  static Logger LOG = LoggerFactory.getLogger(VerificationTokenRepository.class);

  //TODO Fix this.
  VerificationToken findByToken(String token);
}