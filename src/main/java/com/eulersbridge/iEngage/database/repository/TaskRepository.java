package com.eulersbridge.iEngage.database.repository;

import com.eulersbridge.iEngage.database.domain.DataConstants;
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
	@Query("Match (a:`User`),(b:`Task`) where id(a)={userId} and id(b)={taskId} CREATE (a)-[r:"+ DataConstants.HAS_COMPLETED_TASK_LABEL+
			"]->(b) SET r.date=coalesce(r.date,timestamp()),r.__type__='TaskComplete' return r")
	TaskComplete taskCompleted(@Param("taskId") Long taskId, @Param("userId") Long userId);

    @Query("Match (a:`User`),(b:`Task`) where a.email={userEmail} and b.action={taskAction} CREATE (a)-[r:"+ DataConstants.HAS_COMPLETED_TASK_LABEL+
            "]->(b) SET r.date=coalesce(r.date,timestamp()),r.__type__='TaskComplete' return r")
    TaskComplete taskCompleted(@Param("taskAction") String taskAction, @Param("userEmail") String userEmail);

    @Query("Match (a:`User`),(b:`Task`) where a.email={userEmail} and b.action={taskAction} CREATE (a)-[r:"+ DataConstants.HAS_COMPLETED_TASK_LABEL+
            "]->(b) SET r.date=coalesce(r.date,timestamp()),r.__type__='TaskComplete',r.tag={targetType} return r")
    TaskComplete taskCompleted(@Param("taskAction") String taskAction, @Param("userEmail") String userEmail, @Param("targetType") String targetType);

	@Query("Match (a:`User`)-[r:`"+ DataConstants.HAS_COMPLETED_TASK_LABEL+"`]-(b:`Task`) where id(a)={userId} return b")
	Page<Task> findCompletedTasks(@Param("userId") Long userId, Pageable pageable);

	@Query("Match (u:`"+ DataConstants.USER+"`),(t:`"+ DataConstants.TASK+"`) where id(u)={userId} and"+
			" not (u)-[:"+ DataConstants.HAS_COMPLETED_TASK_LABEL+"]-(t) return t")
	Page<Task> findRemainingTasks(@Param("userId") Long userId, Pageable pageable);

    @Query("Match (a:`User`)-[r:`"+ DataConstants.HAS_COMPLETED_TASK_LABEL+"`]-(b:`Task`) where a.email={userEmail} and b.action={action} return r.numOfTimes")
    Long getNumOfCompletedASpecificTask(@Param("userEmail") String userEmail, @Param("action") String taskAction);

    @Query("Match (a:`User`)-[r:`"+ DataConstants.HAS_COMPLETED_TASK_LABEL+"`]-(b:`Task`) where a.email={userEmail} and b.action={action} and r.tag={targetType} return r.numOfTimes")
    Long getNumOfCompletedASpecificTask(@Param("userEmail") String userEmail, @Param("action") String taskAction, @Param("targetType") String targetType);

    @Query("Match (t:`Task`) where t.action={action} return t")
    Task findByAction(@Param("action") String taskAction);
}
