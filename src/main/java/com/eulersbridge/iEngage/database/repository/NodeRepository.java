package com.eulersbridge.iEngage.database.repository;

import com.eulersbridge.iEngage.database.domain.Node;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * @author Yikai Gong
 */

public interface NodeRepository extends GraphRepository<Node> {
}
