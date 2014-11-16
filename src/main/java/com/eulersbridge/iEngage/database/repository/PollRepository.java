package com.eulersbridge.iEngage.database.repository;

import com.eulersbridge.iEngage.database.domain.Like;
import com.eulersbridge.iEngage.database.domain.Poll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by darcular on 21/09/14.
 */
public interface PollRepository extends GraphRepository<Poll>{
    static Logger LOG = LoggerFactory.getLogger(PollRepository.class);


}
