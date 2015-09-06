package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.*;
import com.eulersbridge.iEngage.core.events.comments.CommentDetails;
import com.eulersbridge.iEngage.core.events.comments.CreateCommentEvent;
import com.eulersbridge.iEngage.core.events.comments.RequestReadCommentEvent;
import com.eulersbridge.iEngage.core.events.contactRequest.AcceptContactRequestEvent;
import com.eulersbridge.iEngage.core.events.contactRequest.CreateContactRequestEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.RequestReadNewsArticleEvent;
import com.eulersbridge.iEngage.core.events.polls.CreatePollAnswerEvent;
import com.eulersbridge.iEngage.core.events.polls.PollAnswerCreatedEvent;
import com.eulersbridge.iEngage.core.events.task.CompletedTaskEvent;
import com.eulersbridge.iEngage.core.events.task.CreateTaskEvent;
import com.eulersbridge.iEngage.core.events.users.AddPersonalityEvent;
import com.eulersbridge.iEngage.core.events.users.PersonalityAddedEvent;
import com.eulersbridge.iEngage.core.events.voteReminder.AddVoteReminderEvent;
import com.eulersbridge.iEngage.database.domain.Badge;
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
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

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
            Badge badge = badgeRepository.checkBadgeCompleted(33269l, userEmail);
            if (badge == null)
                badgeRepository.badgeCompleted(33269l, userEmail);
        }
        else if(numOfCompCommentTask >= 20){
            Badge badge = badgeRepository.checkBadgeCompleted(33268l, userEmail);
            if (badge == null)
            badgeRepository.badgeCompleted(33268l, userEmail);
        }
        else if(numOfCompCommentTask >= 10){
            Badge badge = badgeRepository.checkBadgeCompleted(33267l, userEmail);
            if (badge == null)
                badgeRepository.badgeCompleted(33267l, userEmail);

        }
        else if(numOfCompCommentTask >= 1){
            Badge badge = badgeRepository.checkBadgeCompleted(14853l, userEmail);
            if (badge == null)
                badgeRepository.badgeCompleted(14853l, userEmail);
        }
    }

    @AfterReturning(
            pointcut="execution(* com.eulersbridge.iEngage.core.services.NewsEventHandler.requestReadNewsArticle(..)) && args(requestReadNewsArticleEvent)",
            returning="result")
    public void updateReadArticleTask(JoinPoint joinPoint, RequestReadNewsArticleEvent requestReadNewsArticleEvent, ReadEvent result){
        if(result.isEntityFound()){
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = "";
            if (!(auth instanceof AnonymousAuthenticationToken)) {
//                userDetails = (UserDetails)auth.getPrincipal();
                userEmail = auth.getName();
            }
            String taskAction = "Read an Article.";
            TaskComplete taskComplete = taskRepository.taskCompleted(taskAction, userEmail);
        }
    }

    @AfterReturning(
            pointcut="execution(* com.eulersbridge.iEngage.core.services.UserEventHandler.addPersonality(..)) && args(addPersonalityEvent)",
            returning="result")
    public void updateAddPersonalityTask(JoinPoint joinPoint, AddPersonalityEvent addPersonalityEvent, PersonalityAddedEvent result){
        if(result.isUserFound()){
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = "";
            if (!(auth instanceof AnonymousAuthenticationToken)) {
                userEmail = auth.getName();
            }
            String taskAction = "Complete Personality Questions.";
            TaskComplete taskComplete = taskRepository.taskCompleted(taskAction, userEmail);
            if (taskComplete!=null){
                updateAddPersonalityBadge(userEmail, taskAction);
            }
        }
    }

    public void updateAddPersonalityBadge(String userEmail, String taskAction){
        Long numOfCompCommentTask = taskRepository.getNumOfCompletedASpecificTask(userEmail, taskAction);
        if (numOfCompCommentTask >= 1){
            Badge badge = badgeRepository.checkBadgeCompleted(400l, userEmail);
            if (badge == null)
                badgeRepository.badgeCompleted(400l, userEmail);
        }
    }

    @AfterReturning(
            pointcut="execution(* com.eulersbridge.iEngage.core.services.LikesEventHandler.like(..)) && args(likeEvent)",
            returning="result")
    public void updateShareTask(JoinPoint joinPoint, LikeEvent likeEvent, LikedEvent result){
        if(result.isResultSuccess()){
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = "";
            if (!(auth instanceof AnonymousAuthenticationToken)) {
                userEmail = auth.getName();
            }
            String taskAction = "Share.";
            String targetType = likeEvent.getTargetType().getName();
            TaskComplete taskComplete = taskRepository.taskCompleted(taskAction, userEmail, targetType);
            if (taskComplete!=null){
                updateShareBadge(userEmail, taskAction, targetType);
            }
        }
    }

    public void updateShareBadge(String userEmail, String taskAction, String tag){
        Long numOfCompCommentTask = taskRepository.getNumOfCompletedASpecificTask(userEmail, taskAction, tag);
        Long[] badgeIds = new Long[4];
        switch (tag){
            case "NewsArticle":
                badgeIds[0] = 14867l;
                badgeIds[1] = 33252l;
                badgeIds[2] = 33253l;
                badgeIds[3] = 33254l;
                break;
            case "Event":
                badgeIds[0] = 14868l;
                badgeIds[1] = 33255l;
                badgeIds[2] = 33256l;
                badgeIds[3] = 33257l;
                break;
            case "Photo":
                badgeIds[0] = 14869l;
                badgeIds[1] = 33258l;
                badgeIds[2] = 33259l;
                badgeIds[3] = 33260l;

        }
        if (numOfCompCommentTask >= 50){
            Badge badge = badgeRepository.checkBadgeCompleted(badgeIds[3], userEmail);
            if (badge == null)
                badgeRepository.badgeCompleted(badgeIds[3], userEmail);
        }
        else if(numOfCompCommentTask >= 20){
            Badge badge = badgeRepository.checkBadgeCompleted(badgeIds[2], userEmail);
            if (badge == null)
                badgeRepository.badgeCompleted(badgeIds[2], userEmail);
        }
        else if(numOfCompCommentTask >= 10){
            Badge badge = badgeRepository.checkBadgeCompleted(badgeIds[1], userEmail);
            if (badge == null)
                badgeRepository.badgeCompleted(badgeIds[1], userEmail);

        }
        else if(numOfCompCommentTask >= 1){
            Badge badge = badgeRepository.checkBadgeCompleted(badgeIds[0], userEmail);
            if (badge == null)
                badgeRepository.badgeCompleted(badgeIds[0], userEmail);
        }
    }

    @AfterReturning(
            pointcut="execution(* com.eulersbridge.iEngage.core.services.PollEventHandler.answerPoll(..)) && args(createPollAnswerEvent)",
            returning="result")
    public void updateVoteInAPollTask(JoinPoint joinPoint, CreatePollAnswerEvent createPollAnswerEvent, PollAnswerCreatedEvent result){
        if(result.isPollFound() && result.isAnswererFound() && result.isAnswerValid()){
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = "";
            if (!(auth instanceof AnonymousAuthenticationToken)) {
                userEmail = auth.getName();
            }
            String taskAction = "Be a Pollster.";
            TaskComplete taskComplete = taskRepository.taskCompleted(taskAction, userEmail);
            if (taskComplete!=null){
                updateVoteInAPollBadge(userEmail, taskAction);
            }
        }
    }

    public void updateVoteInAPollBadge(String userEmail, String taskAction){
        Long numOfCompCommentTask = taskRepository.getNumOfCompletedASpecificTask(userEmail, taskAction);
        Long[] badgeIds = new Long[]{14873l, 33264l, 33265l, 33266l};
        if (numOfCompCommentTask >= 30){
            Badge badge = badgeRepository.checkBadgeCompleted(badgeIds[3], userEmail);
            if (badge == null)
                badgeRepository.badgeCompleted(badgeIds[3], userEmail);
        }
        else if(numOfCompCommentTask >= 20){
            Badge badge = badgeRepository.checkBadgeCompleted(badgeIds[2], userEmail);
            if (badge == null)
                badgeRepository.badgeCompleted(badgeIds[2], userEmail);
        }
        else if(numOfCompCommentTask >= 10){
            Badge badge = badgeRepository.checkBadgeCompleted(badgeIds[1], userEmail);
            if (badge == null)
                badgeRepository.badgeCompleted(badgeIds[1], userEmail);

        }
        else if(numOfCompCommentTask >= 1){
            Badge badge = badgeRepository.checkBadgeCompleted(badgeIds[0], userEmail);
            if (badge == null)
                badgeRepository.badgeCompleted(badgeIds[0], userEmail);
        }
        // TODO award/revoke the badge: Select user with highest poll votes per month
    }

    @AfterReturning(
            pointcut="execution(* com.eulersbridge.iEngage.core.services.UserEventHandler.addVoteReminder(..)) && args(addVoteReminderEvent)",
            returning="result")
    public void updateAddVoteReminderTask(JoinPoint joinPoint, AddVoteReminderEvent addVoteReminderEvent, CreatedEvent result){
        if(!result.isFailed()){
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = "";
            if (!(auth instanceof AnonymousAuthenticationToken)) {
                userEmail = auth.getName();
            }
            String taskAction = "Set Vote Reminder.";
            TaskComplete taskComplete = taskRepository.taskCompleted(taskAction, userEmail);
            if (taskComplete!=null){
                updateAddVoteReminderBadge(userEmail, taskAction);
            }
        }
    }

    public void updateAddVoteReminderBadge(String userEmail, String taskAction){
        Long numOfCompCommentTask = taskRepository.getNumOfCompletedASpecificTask(userEmail, taskAction);
        Long badgeId = 14854l;
        if (numOfCompCommentTask >= 1){
            Badge badge = badgeRepository.checkBadgeCompleted(badgeId, userEmail);
            if (badge == null)
                badgeRepository.badgeCompleted(badgeId, userEmail);
        }
    }

    @AfterReturning(
            pointcut="execution(* com.eulersbridge.iEngage.core.services.ContactRequestEventHandler.createContactRequest(..)) && args(createContactRequestEvent)",
            returning="result")
    public void updateInviteAFriendTask(JoinPoint joinPoint, CreateContactRequestEvent createContactRequestEvent, CreatedEvent result){
        if(!result.isFailed()){
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = "";
            if (!(auth instanceof AnonymousAuthenticationToken)) {
                userEmail = auth.getName();
            }
            String taskAction = "Invite a Friend.";
            TaskComplete taskComplete = taskRepository.taskCompleted(taskAction, userEmail);
            if (taskComplete!=null){
                updateInviteFriendsBadge(userEmail, taskAction);
            }
        }
    }

    public void updateInviteFriendsBadge(String userEmail, String taskAction){
        Long numOfCompCommentTask = taskRepository.getNumOfCompletedASpecificTask(userEmail, taskAction);
        Long[] badgeIds = new Long[]{14857l, 33272l, 33270l, 33271l};
        if (numOfCompCommentTask >= 25){
            Badge badge = badgeRepository.checkBadgeCompleted(badgeIds[3], userEmail);
            if (badge == null)
                badgeRepository.badgeCompleted(badgeIds[3], userEmail);
        }
        else if(numOfCompCommentTask >= 10){
            Badge badge = badgeRepository.checkBadgeCompleted(badgeIds[2], userEmail);
            if (badge == null)
                badgeRepository.badgeCompleted(badgeIds[2], userEmail);
        }
        else if(numOfCompCommentTask >= 5){
            Badge badge = badgeRepository.checkBadgeCompleted(badgeIds[1], userEmail);
            if (badge == null)
                badgeRepository.badgeCompleted(badgeIds[1], userEmail);

        }
        else if(numOfCompCommentTask >= 1){
            Badge badge = badgeRepository.checkBadgeCompleted(badgeIds[0], userEmail);
            if (badge == null)
                badgeRepository.badgeCompleted(badgeIds[0], userEmail);
        }
    }

    @AfterReturning(
            pointcut="execution(* com.eulersbridge.iEngage.core.services.ContactRequestEventHandler.acceptContactRequest(..)) && args(acceptContactRequestEvent)",
            returning="result")
    public void updateAcceptFriendRequestTask(JoinPoint joinPoint, AcceptContactRequestEvent acceptContactRequestEvent, UpdatedEvent result){
        if(!result.isFailed()){
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = "";
            if (!(auth instanceof AnonymousAuthenticationToken)) {
                userEmail = auth.getName();
            }
            String taskAction = "Add a Friend.";
            TaskComplete taskComplete = taskRepository.taskCompleted(taskAction, userEmail);
            if (taskComplete!=null){
                updateAcceptFriendRequestBadge(userEmail, taskAction);
            }
        }
    }

    public void updateAcceptFriendRequestBadge(String userEmail, String taskAction){
        Long numOfCompCommentTask = taskRepository.getNumOfCompletedASpecificTask(userEmail, taskAction);
        Long[] badgeIds = new Long[]{14856l, 32274l, 32275l, 32276l};
        if (numOfCompCommentTask >= 25){
            Badge badge = badgeRepository.checkBadgeCompleted(badgeIds[3], userEmail);
            if (badge == null)
                badgeRepository.badgeCompleted(badgeIds[3], userEmail);
        }
        else if(numOfCompCommentTask >= 10){
            Badge badge = badgeRepository.checkBadgeCompleted(badgeIds[2], userEmail);
            if (badge == null)
                badgeRepository.badgeCompleted(badgeIds[2], userEmail);
        }
        else if(numOfCompCommentTask >= 5){
            Badge badge = badgeRepository.checkBadgeCompleted(badgeIds[1], userEmail);
            if (badge == null)
                badgeRepository.badgeCompleted(badgeIds[1], userEmail);

        }
        else if(numOfCompCommentTask >= 1){
            Badge badge = badgeRepository.checkBadgeCompleted(badgeIds[0], userEmail);
            if (badge == null)
                badgeRepository.badgeCompleted(badgeIds[0], userEmail);
        }
        // TODO award/revoke the badge: Select user with highest number of connections per month
    }
}
