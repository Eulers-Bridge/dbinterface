/**
 *
 */
package com.eulersbridge.iEngage.database.repository;

import com.eulersbridge.iEngage.database.domain.ContactRequest;
import com.eulersbridge.iEngage.database.domain.DataConstants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Yikai Gong
 */
@Repository
public interface ContactRequestRepository extends
  Neo4jRepository<ContactRequest, Long> {

  @Query("MATCH (u:User)-[r:HAS_CONTACT_REQUEST]->(t:User) optional match l=(x)-[r1]-(u)-[r]->(t)-[r2]-(y) where u.email={userEmail} and type(r1)<>'HAS_CONTACT_REQUEST' and type(r2)<>'HAS_CONTACT_REQUEST' return l order by r.requestDate DESC")
//  @Query("MATCH l=(x)-[r1*0..1]-(u:User)-[r:HAS_CONTACT_REQUEST]->(t:User)-[r2*0..1]-(y) where u.email={userEmail} and NONE (a IN r1 WHERE type(a)= 'HAS_CONTACT_REQUEST') and NONE (a IN r2 WHERE type(a)= 'HAS_CONTACT_REQUEST') return l order by r.requestDate DESC")
  List<ContactRequest> findSentRequests(@Param("userEmail") String userEmail);

//  @Query("MATCH l=(x)-[*0..1]-(u:User)-[r:HAS_CONTACT_REQUEST]->(t:User)-[*0..1]-(y) where t.email={userEmail} and (x:Institution or x:Badge or x:Task or id(x)=id(u)) and (y:Institution or y:Badge or y:Task or id(t)=id(y)) return l order by r.requestDate DESC")
//  and NONE (a IN x WHERE type(a)= 'HAS_CONTACT_REQUEST') and NONE (a IN y WHERE type(a)= 'HAS_CONTACT_REQUEST')

//  @Query("MATCH l=(u:User)-[r:HAS_CONTACT_REQUEST]->(t:User) where t.email={userEmail} return l order by r.requestDate DESC")
//  @Query("MATCH (u:User)-[r:HAS_CONTACT_REQUEST]->(t:User) where t.email={userEmail} with u, r, t optional match l=(x)-[r1]-(u)-[r]->(t)-[r2]-(y) where type(r1)<>'HAS_CONTACT_REQUEST' and type(r2)<>'HAS_CONTACT_REQUEST' return l order by r.requestDate DESC")
  @Query("MATCH (u:User)-[r:HAS_CONTACT_REQUEST]->(t:User) optional match l=(x)-[r1]-(u)-[r]->(t)-[r2]-(y) where t.email={userEmail} and type(r1)<>'HAS_CONTACT_REQUEST' and type(r2)<>'HAS_CONTACT_REQUEST' return l order by r.requestDate DESC")
  //  @Query("MATCH l=(x)-[r1*0..1]-(u:User)-[r:HAS_CONTACT_REQUEST]->(t:User)-[r2*0..1]-(y) where t.email={userEmail} and NONE (a IN r1 WHERE type(a)= 'HAS_CONTACT_REQUEST') and NONE (a IN r2 WHERE type(a)= 'HAS_CONTACT_REQUEST') return l order by r.requestDate DESC")
  List<ContactRequest> findReceivedRequests(@Param("userEmail") String userEmail);

  @Query("MATCH l=(u:User)-[r:HAS_CONTACT_REQUEST]->(t:User) where u.email={userEmail} and t.email={targetEmail} return l")
  List<ContactRequest> findExistingRequest(@Param("userEmail") String userEmail, @Param("targetEmail") String targetEmail);

  @Query("MATCH l= (u:User)-[r:HAS_CONTACT_REQUEST]->(t:User) where id(r)={requestId} return l")
  ContactRequest findExistingRequest(@Param("requestId") Long requestId);

  @Query("MATCH (u:User)-[r:HAS_CONTACT_REQUEST]->(t:User) WHERE u.email={userEmail} and id(r)={requestId} WITH r, id(r) as id DELETE r return id")
  Long revokeSentRequest(@Param("userEmail") String userEmail, @Param("requestId") Long requestId);

}
