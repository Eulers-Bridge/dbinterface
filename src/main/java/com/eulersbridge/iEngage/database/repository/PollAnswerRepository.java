/**
 * 
 */
package com.eulersbridge.iEngage.database.repository;

import com.eulersbridge.iEngage.database.domain.PollAnswer;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * @author Greg Newitt
 *
 */
public interface PollAnswerRepository extends GraphRepository<PollAnswer>
{

}
