package com.eulersbridge.iEngage.database.repository;

import com.eulersbridge.iEngage.database.domain.DatabaseDomainConstants;
import com.eulersbridge.iEngage.database.domain.Poll;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by darcular on 21/09/14.
 */
public interface PollRepository extends GraphRepository<Poll>
{
	@Query ("MATCH (p:`"+DatabaseDomainConstants.POLL+"`)-[r:`"+DatabaseDomainConstants.HAS_POLL_LABEL+"`]-(o) where id(o)={ownerId} RETURN p")
	Page<Poll> findByOwnerId(@Param("ownerId")Long ownerId,Pageable p);

	@Query ("MATCH (p:`"+DatabaseDomainConstants.POLL+"`)-[r:`"+DatabaseDomainConstants.CREATED_BY_LABEL+"`]-(o) where id(o)={creatorId} RETURN p")
	Page<Poll> findByCreatorId(@Param("creatorId")Long creatorId,Pageable p);
}
