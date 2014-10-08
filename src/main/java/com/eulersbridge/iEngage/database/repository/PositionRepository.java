package com.eulersbridge.iEngage.database.repository;

import com.eulersbridge.iEngage.database.domain.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * @author Yikai Gong
 */

public interface PositionRepository extends GraphRepository<Position> {
    static Logger LOG = LoggerFactory.getLogger(PositionRepository.class);

}

