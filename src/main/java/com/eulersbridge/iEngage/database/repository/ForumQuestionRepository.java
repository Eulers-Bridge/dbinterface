package com.eulersbridge.iEngage.database.repository;

import com.eulersbridge.iEngage.database.domain.ForumQuestion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * Created by darcular on 28/09/14.
 */
public interface ForumQuestionRepository extends GraphRepository<ForumQuestion>{
    static Logger LOG = LoggerFactory.getLogger(ForumQuestionRepository.class);

}
