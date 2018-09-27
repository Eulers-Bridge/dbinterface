package com.eulersbridge.iEngage.database.repository;

import com.eulersbridge.iEngage.database.domain.Node;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Yikai Gong
 */

@Repository
public interface NodeRepository extends Neo4jRepository<Node, Long> {
}
