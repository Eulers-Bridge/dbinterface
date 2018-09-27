package com.eulersbridge.iEngage.database.repository;

import com.eulersbridge.iEngage.database.domain.Comment;
import com.eulersbridge.iEngage.database.domain.DataConstants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Yikai Gong
 */
@Repository
public interface CommentRepository extends Neo4jRepository<Comment, Long> {

//    @Query("Match (a) where id(a)={targetId} Return a")
//    Owner findCommentTarget(@Param("targetId")Long targetId);

  @Query(value = "Match (n:`" + DataConstants.COMMENT + "`)-[r:" + DataConstants.HAS_COMMENT + "]-" +
    "(t) Where id(t)={targetId} Return n", countQuery = "Match (n:`" + DataConstants.COMMENT + "`)-[r:" + DataConstants.HAS_COMMENT + "]-" +
    "(t) Where id(t)={targetId} Return count(n)")
  Page<Comment> findByTargetId(@Param("targetId") Long targetId, Pageable pageable);
}
