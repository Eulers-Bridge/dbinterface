package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.CreatedEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.comments.CommentDetails;
import com.eulersbridge.iEngage.core.events.comments.CreateCommentEvent;
import com.eulersbridge.iEngage.core.events.comments.RequestReadCommentEvent;
import com.eulersbridge.iEngage.core.events.task.CompletedTaskEvent;
import com.eulersbridge.iEngage.core.events.task.CreateTaskEvent;
import com.eulersbridge.iEngage.database.domain.TaskComplete;
import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.repository.BadgeRepository;
import com.eulersbridge.iEngage.database.repository.CommentRepository;
import com.eulersbridge.iEngage.database.repository.TaskRepository;
import com.eulersbridge.iEngage.database.repository.UserRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Yikai Gong
 */

@Aspect
public class AspectService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BadgeRepository badgeRepository;
    @Autowired
    private TaskRepository taskRepository;

    // Use Spring Aspect to call the method below and check the result
    @AfterReturning(
            pointcut="execution(* com.eulersbridge.iEngage.core.services.CommentEventHandler.createComment(..)) && args(createCommentEvent)",
            returning="result")
    public void updateCommentTask(JoinPoint joinPoint, CreateCommentEvent createCommentEvent, CreatedEvent result){
        if(!result.isFailed()){
            CommentDetails commentDetails = (CommentDetails)createCommentEvent.getDetails();
            String userEmail = commentDetails.getUserEmail();
            String taskAction = "Post a Comment.";
            TaskComplete taskComplete = taskRepository.taskCompleted(taskAction, userEmail);
            if (taskComplete!=null){
                updateCommentBadge(userEmail, taskAction);
            }
        }
    }

    public void updateCommentBadge(String userEmail, String taskAction){
        Long numOfCompCommentTask = taskRepository.getNumOfCompletedASpecificTask(userEmail, taskAction);
        if (numOfCompCommentTask >= 30){
            // TODO may not be a good idea to use the badge's nodeId directly (a readable, unique and indexed label instead?)
            badgeRepository.badgeCompleted(33269l, userEmail);
        }
        else if(numOfCompCommentTask >= 20)
            badgeRepository.badgeCompleted(33268l, userEmail);
        else if(numOfCompCommentTask >= 10)
            badgeRepository.badgeCompleted(33267l, userEmail);
        else if(numOfCompCommentTask >= 1)
            badgeRepository.badgeCompleted(14853l, userEmail);
    }


}
