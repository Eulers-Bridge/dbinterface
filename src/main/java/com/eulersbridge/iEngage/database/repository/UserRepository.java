package com.eulersbridge.iEngage.database.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import com.eulersbridge.iEngage.database.domain.Personality;
import com.eulersbridge.iEngage.database.domain.User;

public interface UserRepository extends GraphRepository<User> 
{
    static Logger LOG = LoggerFactory.getLogger(UserRepository.class);
	
 	User findByEmail(String email);
 	
	@Query("MATCH (u:`User`)-[r:verifiedBy]-(v:`VerificationToken`) where ID(u)={userId} AND ID(v)={tokenId} set u.accountVerified={isVerified} set v.verified={isVerified} ")
	void verifyUser(@Param("userId") Long userId, @Param("tokenId") Long tokenId, @Param("isVerified") boolean isVerified);

	@Query("MATCH (u:`User`) where ID(u)={userId} create (p:Personality{personality}) create (u)-[r:hasPersonality]->(p) return p;")
	Personality addPersonality(@Param("userId") Long userId,@Param("personality") Personality personality);
}