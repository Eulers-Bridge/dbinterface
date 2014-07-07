package com.eulersbridge.iEngage.database.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.eulersbridge.iEngage.database.domain.User;

public interface UserRepository extends GraphRepository<User> 
{
    static Logger LOG = LoggerFactory.getLogger(UserRepository.class);
	
 	User findByEmail(String email);
}