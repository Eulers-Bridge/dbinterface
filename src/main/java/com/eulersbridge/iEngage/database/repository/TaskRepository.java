package com.eulersbridge.iEngage.database.repository;

import com.eulersbridge.iEngage.database.domain.Task;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * @author Yikai Gong
 */

public interface TaskRepository extends GraphRepository<Task>
{
}
