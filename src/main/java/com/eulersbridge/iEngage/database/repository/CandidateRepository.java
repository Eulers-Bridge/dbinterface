package com.eulersbridge.iEngage.database.repository;

import com.eulersbridge.iEngage.database.domain.Candidate;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * @author Yikai Gong
 */

public interface CandidateRepository extends GraphRepository<Candidate>{

}
