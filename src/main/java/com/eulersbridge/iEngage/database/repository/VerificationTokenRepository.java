package com.eulersbridge.iEngage.database.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.eulersbridge.iEngage.database.domain.VerificationToken;

public interface VerificationTokenRepository extends GraphRepository<VerificationToken> 
{
static Logger LOG = LoggerFactory.getLogger(VerificationTokenRepository.class);
//TODO Fix this.	
 	VerificationToken findByToken(String token);
}