package com.eulersbridge.iEngage.database.repository;

import com.eulersbridge.iEngage.database.domain.Comment;
import com.eulersbridge.iEngage.database.domain.DatabaseDomainConstants;
import com.eulersbridge.iEngage.database.domain.NodeObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author Yikai Gong
 */

public interface CommentReposotory extends GraphRepository<Comment> {

    @Query("Match (a) where id(a)={targetId} Return a")
    NodeObject findCommentTarget(@Param("targetId")Long targetId);

    @Query("Start t=node({targetId}) Match (n:`"+ DatabaseDomainConstants.USER +"`)-[r:"+ DatabaseDomainConstants.POST_COMMENT +"]-" +
            "(t) return r")
    Page<Comment> findByTargetId(@Param("targetId")Long targetId, Pageable pageable);
}
