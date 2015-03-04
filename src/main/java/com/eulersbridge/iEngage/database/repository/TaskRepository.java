package com.eulersbridge.iEngage.database.repository;

import com.eulersbridge.iEngage.database.domain.DatabaseDomainConstants;
import com.eulersbridge.iEngage.database.domain.Task;
import com.eulersbridge.iEngage.database.domain.TaskComplete;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * @author Yikai Gong
 */

public interface TaskRepository extends GraphRepository<Task>
{
	@Query("Match (a:`User`),(b:`Task`) where id(a)={tc.getUser().getNodeId()} and id(b)={tc.getTask().getNodeId()} CREATE UNIQUE a-[r:"+DatabaseDomainConstants.VRECORD_LABEL+
			"]-b SET r.date=coalesce(r.date,timestamp()),r.__type__='TaskComplete' return r")
	TaskComplete taskCompleted(TaskComplete tc);
}
