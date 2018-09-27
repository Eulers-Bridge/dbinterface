package com.eulersbridge.iEngage.database.repository;

import com.eulersbridge.iEngage.database.domain.PPSEQuestionsNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Yikai Gong
 */
@Repository
public interface PPSEQuestionsRepository extends Neo4jRepository<PPSEQuestionsNode, Long> {
}
