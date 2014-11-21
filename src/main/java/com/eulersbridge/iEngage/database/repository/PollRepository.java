package com.eulersbridge.iEngage.database.repository;

import com.eulersbridge.iEngage.database.domain.Poll;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * Created by darcular on 21/09/14.
 */
public interface PollRepository extends GraphRepository<Poll>
{
}
