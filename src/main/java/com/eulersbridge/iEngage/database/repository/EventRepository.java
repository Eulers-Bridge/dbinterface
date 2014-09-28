package com.eulersbridge.iEngage.database.repository;

import com.eulersbridge.iEngage.database.domain.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * @author Yikai Gong
 */

public interface EventRepository extends GraphRepository<Event> {
    static Logger LOG = LoggerFactory.getLogger(EventRepository.class);

}
