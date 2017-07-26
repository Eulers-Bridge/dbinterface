package com.eulersbridge.iEngage.database.repository;

import com.eulersbridge.iEngage.database.domain.PPSEQuestionsNode;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * @author Yikai Gong
 */

public interface PPSEQuestionsRepository extends GraphRepository<PPSEQuestionsNode> {
}
