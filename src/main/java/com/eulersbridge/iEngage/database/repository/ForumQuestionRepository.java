package com.eulersbridge.iEngage.database.repository;

import com.eulersbridge.iEngage.database.domain.DatabaseDomainConstants;
import com.eulersbridge.iEngage.database.domain.ForumQuestion;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by darcular on 28/09/14.
 */
public interface ForumQuestionRepository extends GraphRepository<ForumQuestion>
{
	@Query ("MATCH (p:`"+DatabaseDomainConstants.FORUM_QUESTION+"`)-[r:`"+DatabaseDomainConstants.HAS_FORUM_QUESTION_LABEL+"`]-(o) where id(o)={ownerId} RETURN p")
	Page<ForumQuestion> findByOwnerId(@Param("ownerId")Long ownerId,Pageable p);
}
