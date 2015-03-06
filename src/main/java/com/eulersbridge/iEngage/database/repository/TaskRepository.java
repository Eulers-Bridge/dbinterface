package com.eulersbridge.iEngage.database.repository;

import com.eulersbridge.iEngage.database.domain.DatabaseDomainConstants;
import com.eulersbridge.iEngage.database.domain.Task;
import com.eulersbridge.iEngage.database.domain.TaskComplete;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author Yikai Gong
 */

public interface TaskRepository extends GraphRepository<Task>
{
	@Query("Match (a:`User`),(b:`Task`) where id(a)={userId} and id(b)={taskId} CREATE UNIQUE a-[r:"+DatabaseDomainConstants.HAS_COMPLETED_TASK_LABEL+
			"]-b SET r.date=coalesce(r.date,timestamp()),r.__type__='"+DatabaseDomainConstants.HAS_COMPLETED_TASK_LABEL+"' return r")
	TaskComplete taskCompleted(@Param("taskId") Long taskId, @Param("userId") Long userId);

	@Query("Match (a:`User`)-[r:"+DatabaseDomainConstants.HAS_COMPLETED_TASK_LABEL+"]-(b:`Task`) where id(a)={userId} return b")
	Page<Task> findCompletedTasks(Long userId, Pageable pageable);
}
