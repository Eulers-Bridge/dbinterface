package com.eulersbridge.iEngage.database.repository;

import com.eulersbridge.iEngage.database.domain.DataConstants;
import com.eulersbridge.iEngage.database.domain.Task;
import com.eulersbridge.iEngage.database.domain.TaskComplete;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Depth;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Yikai Gong
 */
@Repository
public interface TaskRepository extends Neo4jRepository<Task, Long> {
  @Query("Match (a:`User`),(b:`Task`) where id(a)={userId} and id(b)={taskId} CREATE (a)-[r:" + DataConstants.HAS_COMPLETED_TASK_LABEL +
    "]->(b) SET r.date=coalesce(r.date,timestamp()),r.__type__='TaskComplete' return r")
  TaskComplete taskCompleted(@Param("taskId") Long taskId, @Param("userId") Long userId);

  @Query("Match (a:`User`),(b:`Task`) where a.email={userEmail} and b.action={taskAction} CREATE (a)-[r:" + DataConstants.HAS_COMPLETED_TASK_LABEL +
    "]->(b) SET r.date=coalesce(r.date,timestamp()),r.__type__='TaskComplete' return r")
  TaskComplete taskCompleted(@Param("taskAction") String taskAction, @Param("userEmail") String userEmail);

  @Query("Match (a:`User`),(b:`Task`) where a.email={userEmail} and b.action={taskAction} CREATE (a)-[r:" + DataConstants.HAS_COMPLETED_TASK_LABEL +
    "]->(b) SET r.date=coalesce(r.date,timestamp()),r.__type__='TaskComplete',r.tag={targetType} return r")
  TaskComplete taskCompleted(@Param("taskAction") String taskAction, @Param("userEmail") String userEmail, @Param("targetType") String targetType);

  @Query(value = "Match (a:`User`)-[r:`" + DataConstants.HAS_COMPLETED_TASK_LABEL + "`]-(b:`Task`) where id(a)={userId} return b",
  countQuery = "Match (a:`User`)-[r:`" + DataConstants.HAS_COMPLETED_TASK_LABEL + "`]-(b:`Task`) where id(a)={userId} return count(b)")
  Page<Task> findCompletedTasks(@Param("userId") Long userId, Pageable pageable);

  @Query(value = "Match (u:`" + DataConstants.USER + "`),(t:`" + DataConstants.TASK + "`) where id(u)={userId} and" +
    " not (u)-[:" + DataConstants.HAS_COMPLETED_TASK_LABEL + "]-(t) return t",
    countQuery = "Match (u:`" + DataConstants.USER + "`),(t:`" + DataConstants.TASK + "`) where id(u)={userId} and" +
      " not (u)-[:" + DataConstants.HAS_COMPLETED_TASK_LABEL + "]-(t) return count(t)")
  Page<Task> findRemainingTasks(@Param("userId") Long userId, Pageable pageable);

  @Query("Match (a:`User`)-[r:`" + DataConstants.HAS_COMPLETED_TASK_LABEL + "`]-(b:`Task`) where a.email={userEmail} and b.action={action} return r.numOfTimes")
  Long getNumOfCompletedASpecificTask(@Param("userEmail") String userEmail, @Param("action") String taskAction);

  @Query("Match (a:`User`)-[r:`" + DataConstants.HAS_COMPLETED_TASK_LABEL + "`]-(b:`Task`) where a.email={userEmail} and b.action={action} and r.tag={targetType} return r.numOfTimes")
  Long getNumOfCompletedASpecificTask(@Param("userEmail") String userEmail, @Param("action") String taskAction, @Param("targetType") String targetType);

  @Query("Match (t:`Task`) where t.action={action} return t")
  Task findByAction(@Param("action") String taskAction);

  Task findByAction(String taskAction, @Depth int i);
}
