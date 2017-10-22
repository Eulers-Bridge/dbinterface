/**
 *
 */
package com.eulersbridge.iEngage.database.repository;

import com.eulersbridge.iEngage.database.domain.ContactRequest;
import com.eulersbridge.iEngage.database.domain.DataConstants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Greg Newitt
 */
@Repository
public interface ContactRequestRepository extends
  GraphRepository<ContactRequest> {
  @Query("MATCH (u:`" + DataConstants.USER + "`)-[r:`" + DataConstants.CONTACT_REQUEST_LABEL + "`]-(c:`" + DataConstants.CONTACT_REQUEST + "`) where id(u)={userId} and c.contactDetails={contactInfo} RETURN c")
  ContactRequest findContactRequestByUserIdContactInfo(@Param("userId") Long userId, @Param("contactInfo") String contactInfo);

  @Query("MATCH l=(u:User)-[r:HAS_CONTACT_REQUEST]->(t:User) where u.email={userEmail} return l order by r.requestDate")
  List<ContactRequest> findSentRequests(@Param("userEmail") String userEmail);

  @Query("MATCH l=(u:User)-[r:HAS_CONTACT_REQUEST]->(t:User) where t.email={userEmail} return l order by r.requestDate")
  List<ContactRequest> findReceivedRequests(@Param("userEmail") String userEmail);

  @Query("MATCH l=(u:User)-[r:HAS_CONTACT_REQUEST]->(t:User) where u.email={userEmail} and t.email={targetEmail} return l")
  List<ContactRequest> findExistingRequest(@Param("userEmail") String userEmail, @Param("targetEmail") String targetEmail);

  @Query("MATCH l=(u:User)-[r:HAS_CONTACT_REQUEST]->(t:User) where id(r)={requestId} return l")
  ContactRequest findExistingRequest(@Param("requestId") Long requestId);

}
