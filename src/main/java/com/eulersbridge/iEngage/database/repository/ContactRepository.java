package com.eulersbridge.iEngage.database.repository;

import com.eulersbridge.iEngage.database.domain.Contact;
import com.eulersbridge.iEngage.database.domain.DataConstants;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Yikai Gong
 */
@Repository
public interface ContactRepository extends Neo4jRepository<Contact, Long> {

  @Query("Match (u1:User),(u2:User) where u1.email={email1} and u2.email={email2} CREATE UNIQUE (u1)-[r:HAS_CONTACT]->(u2) SET r.timestamp={timestamp} return (u1)-[r]->(u2)")
  Contact createUniqueFriendship(@Param("email1") String senderEmail, @Param("email2") String receiverEmail, @Param("timestamp") Long timestamp);

  @Query("Match (u1:User)-[r:HAS_CONTACT]-(u2:User) where u1.email={userEmail} and u2.email={friendEmail} with r, id(r) as id delete r return id")
  Long deleteFriendship(@Param("userEmail") String userEmail, @Param("friendEmail") String friendEmail);
}
