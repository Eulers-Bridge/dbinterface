package com.eulersbridge.iEngage.database.repository;

import com.eulersbridge.iEngage.database.domain.DataConstants;
import com.eulersbridge.iEngage.database.domain.ForumQuestion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by darcular on 28/09/14.
 */
@Repository
public interface ForumQuestionRepository extends Neo4jRepository<ForumQuestion, Long> {
  @Query(value = "MATCH (p:`" + DataConstants.FORUM_QUESTION + "`)-[r:`" + DataConstants.HAS_FORUM_QUESTION_LABEL + "`]-(o) where id(o)={ownerId} RETURN p",
    countQuery = "MATCH (p:`" + DataConstants.FORUM_QUESTION + "`)-[r:`" + DataConstants.HAS_FORUM_QUESTION_LABEL + "`]-(o) where id(o)={ownerId} RETURN count(p)")
  Page<ForumQuestion> findByOwnerId(@Param("ownerId") Long ownerId, Pageable p);
}
