/**
 * 
 */
package com.eulersbridge.iEngage.database.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import com.eulersbridge.iEngage.database.domain.ContactRequest;
import com.eulersbridge.iEngage.database.domain.DatabaseDomainConstants;

/**
 * @author Greg Newitt
 *
 */
public interface ContactRequestRepository extends
		GraphRepository<ContactRequest>
{
	@Query ("MATCH (u:`"+DatabaseDomainConstants.USER+"`)-[r:`"+DatabaseDomainConstants.CONTACT_REQUEST_LABEL+"`]-(c:`"+DatabaseDomainConstants.CONTACT_REQUEST+"`) where id(u)={userId} and c.contactDetails={contactInfo} RETURN c")
	ContactRequest findContactRequestByUserIdContactInfo(@Param("userId")Long userId, @Param("contactInfo")String contactInfo);

}
