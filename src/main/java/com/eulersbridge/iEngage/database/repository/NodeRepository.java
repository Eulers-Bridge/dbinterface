package com.eulersbridge.iEngage.database.repository;

import com.eulersbridge.iEngage.database.domain.Node;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Yikai Gong
 */

@Repository
public interface NodeRepository extends GraphRepository<Node> {
}
