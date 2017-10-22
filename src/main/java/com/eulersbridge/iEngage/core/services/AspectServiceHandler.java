package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.beans.ParamBean;
import com.eulersbridge.iEngage.core.beans.Util;
import com.eulersbridge.iEngage.core.events.*;
import com.eulersbridge.iEngage.core.events.comments.CreateCommentEvent;
import com.eulersbridge.iEngage.core.events.contactRequest.AcceptContactRequestEvent;
import com.eulersbridge.iEngage.core.events.contactRequest.CreateContactRequestEvent;
import com.eulersbridge.iEngage.core.events.newsArticles.RequestReadNewsArticleEvent;

import com.eulersbridge.iEngage.core.events.users.AddPersonalityEvent;
import com.eulersbridge.iEngage.core.events.users.PersonalityAddedEvent;
import com.eulersbridge.iEngage.core.events.voteReminder.AddVoteReminderEvent;
import com.eulersbridge.iEngage.database.domain.*;
import com.eulersbridge.iEngage.database.repository.BadgeRepository;
import com.eulersbridge.iEngage.database.repository.TaskRepository;
import com.eulersbridge.iEngage.database.repository.UserRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yikai Gong
 */

@Service
@Aspect
public class AspectServiceHandler {

  private final UserRepository userRepository;
  private final BadgeRepository badgeRepository;
  private final TaskRepository taskRepository;
  private final Util util;
  private final ParamBean p;


  @Autowired
  public AspectServiceHandler(Util util,
                              ParamBean paramBean,
                              UserRepository userRepository,
                              BadgeRepository badgeRepository,
                              TaskRepository taskRepository) {
    this.util = util;
    this.p = paramBean;
    this.userRepository = userRepository;
    this.badgeRepository = badgeRepository;
    this.taskRepository = taskRepository;
  }

  public boolean updateTask(String userEmail, String taskAction, Long gainedExp, String tag) {
    User user = userRepository.findByEmail(userEmail, 1);
    if (user == null)
      return false;
    List<TaskComplete> taskCompletes = user.getCompletedTasks() == null
      ? new ArrayList<>()
      : user.getCompletedTasks();
    TaskComplete targetTaskComplete = null;
    for (TaskComplete t : taskCompletes) {
      if (t.getTask().getAction().equals(taskAction)) {
        if (tag == null) {
          targetTaskComplete = t;
          break;
        } else if (t.getTag() != null && tag.equals(t.getTag())) {
          targetTaskComplete = t;
          break;
        }
      }
    }
    if (targetTaskComplete == null) {
      TaskComplete taskComplete = TaskComplete.init();
      Task task = taskRepository.findByAction(taskAction);
      taskComplete.setTask(task);
      taskComplete.setUser(user);
      if (tag != null)
        taskComplete.setTag(tag);
      taskCompletes.add(taskComplete);
    } else {
      targetTaskComplete.update();
    }
    user.setCompletedTasks(taskCompletes);
    user.setExperience(user.getExperience() + gainedExp);
    User savedUser = userRepository.save(user, 1);

    return savedUser == null ? false : true;
  }


  public void buildTaskCompletedNotification(String userEmail, String txt) {
//    Message message = new Message(null, txt);
//    Long userId = userRepository.getUserId(userEmail);
//    NotificationDetails notificationDetails = new NotificationDetails(null, userId, new Date().getTime(), false, NotificationConstants.MESSAGE, message);
//    CreateEvent createNotificationEvent = new CreateEvent(notificationDetails);
//    notificationService.createNotification(createNotificationEvent);
  }

  public void buildBadgeAwardedNotification(String userEmail, String txt) {
//    Message message = new Message(null, txt);
//    Long userId = userRepository.getUserId(userEmail);
//    NotificationDetails notificationDetails = new NotificationDetails(null, userId, new Date().getTime(), false, NotificationConstants.MESSAGE, message);
//    CreateEvent createNotificationEvent = new CreateEvent(notificationDetails);
//    notificationService.createNotification(createNotificationEvent);
  }

  // Use Spring Aspect to call the method below and check the result
  @AfterReturning(
    pointcut = "execution(* com.eulersbridge.iEngage.core.services.CommentEventHandler.createComment(..)) && args(createCommentEvent)",
    returning = "result")
  public void updateCommentTask(JoinPoint joinPoint, CreateCommentEvent createCommentEvent, CreatedEvent result) {
    if (!result.isFailed()) {
      String userEmail = Util.getUserEmailFromSession();
      // async block
      util.asyncExecUserTask(userEmail, id -> {
        String taskAction = "Post a Comment.";
        Boolean success = updateTask(userEmail, taskAction, p.expPostComment.longValue(), null);
        if (success)
          updateCommentBadge(userEmail, taskAction);
        return null;
      });
    }
  }

  public void updateCommentBadge(String userEmail, String taskAction) {
    Long numOfCompCommentTask = taskRepository.getNumOfCompletedASpecificTask(userEmail, taskAction);
    if (numOfCompCommentTask >= 30) {
      // TODO may not be a good idea to use the badge's nodeId directly (a readable, unique and indexed label instead?)
      Badge badge = badgeRepository.checkBadgeCompleted(DataConstants.CreateCommentBadge, 3, userEmail);
      if (badge == null) {
        badgeRepository.badgeCompleted(DataConstants.CreateCommentBadge, 3, userEmail);
        buildBadgeAwardedNotification(userEmail, "New Badge: Community Comments - level 3");
      }
    } else if (numOfCompCommentTask >= 20) {
      Badge badge = badgeRepository.checkBadgeCompleted(DataConstants.CreateCommentBadge, 2, userEmail);
      if (badge == null) {
        badgeRepository.badgeCompleted(DataConstants.CreateCommentBadge, 2, userEmail);
        buildBadgeAwardedNotification(userEmail, "New Badge: Community Comments - level 2");
      }
    } else if (numOfCompCommentTask >= 10) {
      Badge badge = badgeRepository.checkBadgeCompleted(DataConstants.CreateCommentBadge, 1, userEmail);
      if (badge == null) {
        badgeRepository.badgeCompleted(DataConstants.CreateCommentBadge, 1, userEmail);
        buildBadgeAwardedNotification(userEmail, "New Badge: Community Comments - level 1");
      }
    } else if (numOfCompCommentTask >= 1) {
      Badge badge = badgeRepository.checkBadgeCompleted(DataConstants.CreateCommentBadge, 0, userEmail);
      if (badge == null) {
        badgeRepository.badgeCompleted(DataConstants.CreateCommentBadge, 0, userEmail);
        buildBadgeAwardedNotification(userEmail, "New Badge: Community Comments");
      }
    }
  }

  @AfterReturning(
    pointcut = "execution(* com.eulersbridge.iEngage.core.services.NewsEventHandler.requestReadNewsArticle(..)) && args(requestReadNewsArticleEvent)",
    returning = "result")
  public void updateReadArticleTask(JoinPoint joinPoint, RequestReadNewsArticleEvent requestReadNewsArticleEvent, ReadEvent result) {
    if (result.isEntityFound()) {
      String userEmail = Util.getUserEmailFromSession();
      // Async execution block
      util.asyncExecUserTask(userEmail, id -> {
        String taskAction = "Read an Article.";
        Boolean success = updateTask(userEmail, taskAction, p.expReadArticle.longValue(), null);
        return null;
      });
    }
  }

  @AfterReturning(
    pointcut = "execution(* com.eulersbridge.iEngage.core.services.UserEventHandler.addPersonality(..)) && args(addPersonalityEvent)",
    returning = "result")
  public void updateAddPersonalityTask(JoinPoint joinPoint, AddPersonalityEvent addPersonalityEvent, PersonalityAddedEvent result) {
    if (result.isUserFound()) {
      String userEmail = Util.getUserEmailFromSession();
      // async block
      util.asyncExecUserTask(userEmail, id -> {
        String taskAction = "Complete Personality Questions.";
        Boolean success = updateTask(userEmail, taskAction, p.expCompPerQuestion.longValue(), null);
        if (success)
          updateAddPersonalityBadge(userEmail, taskAction);
        return null;
      });
    }
  }

  public void updateAddPersonalityBadge(String userEmail, String taskAction) {
    Long numOfCompCommentTask = taskRepository.getNumOfCompletedASpecificTask(userEmail, taskAction);
    if (numOfCompCommentTask >= 1) {
      Badge badge = badgeRepository.checkBadgeCompleted(DataConstants.CompPersonalityQuizBadge, 0, userEmail);
      if (badge == null) {
        badgeRepository.badgeCompleted(DataConstants.CompPersonalityQuizBadge, 0, userEmail);
        buildBadgeAwardedNotification(userEmail, "New Badge: Personality Discovery");
      }
    }
  }

  @AfterReturning(
    pointcut = "execution(* com.eulersbridge.iEngage.core.services.LikesEventHandler.like(..)) && args(likeEvent)",
    returning = "result")
  public void updateShareTask(JoinPoint joinPoint, LikeEvent likeEvent, LikedEvent result) {
    if (result.isResultSuccess()) {
      String userEmail = Util.getUserEmailFromSession();
      // async block
      util.asyncExecUserTask(userEmail, id -> {
        String taskAction = "Share.";
        String targetType = likeEvent.getTargetType().getSimpleName();
        Boolean success = updateTask(userEmail, taskAction, p.expShare.longValue(), targetType);
        if (success)
          updateShareBadge(userEmail, taskAction, targetType);
        return null;
      });
    }
  }

  // FIXME: should not use hard-code node id here!!
  public void updateShareBadge(String userEmail, String taskAction, String tag) {
    Long numOfCompCommentTask = taskRepository.getNumOfCompletedASpecificTask(userEmail, taskAction, tag);
//    Long[] badgeIds = new Long[4];
    String badgeName = "";
    String sharedType = "";
    switch (tag) {
      case "NewsArticle":
        badgeName = DataConstants.ShareNewsBadge;
        sharedType = "News";
        break;
      case "Event":
        badgeName = DataConstants.ShareEventBadge;
        sharedType = "Event";
        break;
      case "Photo":
        badgeName = DataConstants.SharePhtotBadge;
        sharedType = "Photo";
    }
    if (numOfCompCommentTask >= 50) {
      Badge badge = badgeRepository.checkBadgeCompleted(badgeName, 3, userEmail);
      if (badge == null) {
        badgeRepository.badgeCompleted(badgeName, 3, userEmail);
        buildBadgeAwardedNotification(userEmail, "New Badge: Shared " + sharedType + " - level 3");
      }
    } else if (numOfCompCommentTask >= 20) {
      Badge badge = badgeRepository.checkBadgeCompleted(badgeName, 2, userEmail);
      if (badge == null) {
        badgeRepository.badgeCompleted(badgeName, 2, userEmail);
        buildBadgeAwardedNotification(userEmail, "New Badge: Shared " + sharedType + " - level 2");
      }
    } else if (numOfCompCommentTask >= 10) {
      Badge badge = badgeRepository.checkBadgeCompleted(badgeName, 1, userEmail);
      if (badge == null) {
        badgeRepository.badgeCompleted(badgeName, 1, userEmail);
        buildBadgeAwardedNotification(userEmail, "New Badge: Shared " + sharedType + " - level 1");
      }
    } else if (numOfCompCommentTask >= 1) {
      Badge badge = badgeRepository.checkBadgeCompleted(badgeName, 0, userEmail);
      if (badge == null) {
        badgeRepository.badgeCompleted(badgeName, 0, userEmail);
        buildBadgeAwardedNotification(userEmail, "New Badge: Shared " + sharedType);
      }
    }
  }

  @AfterReturning(
    pointcut = "execution(* com.eulersbridge.iEngage.core.services.PollEventHandler.votePollOption(..)) && args(userEmail, pollId, optionId)",
    returning = "result")
  public void updateVoteInAPollTask(JoinPoint joinPoint, String userEmail, Long pollId, Long optionId, RequestHandledEvent result) {
    if (result.getSuccess()) {
      String userEmail1 = Util.getUserEmailFromSession();
      // async block
      util.asyncExecUserTask(userEmail, id -> {
        String taskAction = "Be a Pollster.";
        Boolean success = updateTask(userEmail1, taskAction, p.expVoteInPoll.longValue(), null);
        if (success)
          updateVoteInAPollBadge(userEmail1, taskAction);
        return null;
      });
    }
  }

  public void updateVoteInAPollBadge(String userEmail, String taskAction) {
    Long numOfCompCommentTask = taskRepository.getNumOfCompletedASpecificTask(userEmail, taskAction);
    String badgeName = DataConstants.VotePollBadge;
//    Long[] badgeIds = new Long[]{14873l, 33264l, 33265l, 33266l};
    if (numOfCompCommentTask >= 30) {
      Badge badge = badgeRepository.checkBadgeCompleted(badgeName, 3, userEmail);
      if (badge == null) {
        badgeRepository.badgeCompleted(badgeName, 3, userEmail);
        buildBadgeAwardedNotification(userEmail, "New Badge: Voted in a Poll - level 3");
      }
    } else if (numOfCompCommentTask >= 20) {
      Badge badge = badgeRepository.checkBadgeCompleted(badgeName, 2, userEmail);
      if (badge == null) {
        badgeRepository.badgeCompleted(badgeName, 2, userEmail);
        buildBadgeAwardedNotification(userEmail, "New Badge: Voted in a Poll - level 2");
      }
    } else if (numOfCompCommentTask >= 10) {
      Badge badge = badgeRepository.checkBadgeCompleted(badgeName, 1, userEmail);
      if (badge == null) {
        badgeRepository.badgeCompleted(badgeName, 1, userEmail);
        buildBadgeAwardedNotification(userEmail, "New Badge: Voted in a Poll - level 1");
      }
    } else if (numOfCompCommentTask >= 1) {
      Badge badge = badgeRepository.checkBadgeCompleted(badgeName, 0, userEmail);
      if (badge == null) {
        badgeRepository.badgeCompleted(badgeName, 0, userEmail);
        buildBadgeAwardedNotification(userEmail, "New Badge: Voted in a Poll");
      }
    }
    // TODO award/revoke the badge: Select user with highest poll votes per month
  }

  @AfterReturning(
    pointcut = "execution(* com.eulersbridge.iEngage.core.services.UserEventHandler.addVoteReminder(..)) && args(addVoteReminderEvent)",
    returning = "result")
  public void updateAddVoteReminderTask(JoinPoint joinPoint, AddVoteReminderEvent addVoteReminderEvent, CreatedEvent result) {
    if (!result.isFailed()) {
      String userEmail = Util.getUserEmailFromSession();
      // async block
      util.asyncExecUserTask(userEmail, id -> {
        String taskAction = "Set Vote Reminder.";
        Boolean success = updateTask(userEmail, taskAction, 100L, null);
        if (success)
          updateAddVoteReminderBadge(userEmail, taskAction);
        return null;
      });
    }
  }

  public void updateAddVoteReminderBadge(String userEmail, String taskAction) {
    Long numOfCompCommentTask = taskRepository.getNumOfCompletedASpecificTask(userEmail, taskAction);
    String badgeName = DataConstants.SetVoteReminderBadge;
//    Long badgeId = 14854l;
    if (numOfCompCommentTask >= 1) {
      Badge badge = badgeRepository.checkBadgeCompleted(badgeName, 0, userEmail);
      if (badge == null) {
        badgeRepository.badgeCompleted(badgeName, 0, userEmail);
        buildBadgeAwardedNotification(userEmail, "New Badge: Set Vote Reminder");
      }
    }
  }

  @AfterReturning(
    pointcut = "execution(* com.eulersbridge.iEngage.core.services.ContactRequestEventHandler.createContactRequest(..)) && args(createContactRequestEvent)",
    returning = "result")
  public void updateInviteAFriendTask(JoinPoint joinPoint, CreateContactRequestEvent createContactRequestEvent, CreatedEvent result) {
    if (!result.isFailed()) {
      String userEmail = Util.getUserEmailFromSession();
      // async block
      util.asyncExecUserTask(userEmail, id -> {
        String taskAction = "Invite a Friend.";
        Boolean success = updateTask(userEmail, taskAction, p.expInviteFriend.longValue(), null);
        if (success)
          updateInviteFriendsBadge(userEmail, taskAction);
        return null;
      });
    }
  }

  public void updateInviteFriendsBadge(String userEmail, String taskAction) {
    Long numOfCompCommentTask = taskRepository.getNumOfCompletedASpecificTask(userEmail, taskAction);
    String badgeName = DataConstants.InviteFriendBadge;
//    Long[] badgeIds = new Long[]{14857l, 33272l, 33270l, 33271l};
    if (numOfCompCommentTask >= 25) {
      Badge badge = badgeRepository.checkBadgeCompleted(badgeName, 3, userEmail);
      if (badge == null) {
        badgeRepository.badgeCompleted(badgeName, 3, userEmail);
        buildBadgeAwardedNotification(userEmail, "New Badge: Invited a friend - level 3");
      }
    } else if (numOfCompCommentTask >= 10) {
      Badge badge = badgeRepository.checkBadgeCompleted(badgeName, 2, userEmail);
      if (badge == null) {
        badgeRepository.badgeCompleted(badgeName, 2, userEmail);
        buildBadgeAwardedNotification(userEmail, "New Badge: Invited a friend - level 2");
      }
    } else if (numOfCompCommentTask >= 5) {
      Badge badge = badgeRepository.checkBadgeCompleted(badgeName, 1, userEmail);
      if (badge == null) {
        badgeRepository.badgeCompleted(badgeName, 1, userEmail);
        buildBadgeAwardedNotification(userEmail, "New Badge: Invited a friend - level 1");
      }
    } else if (numOfCompCommentTask >= 1) {
      Badge badge = badgeRepository.checkBadgeCompleted(badgeName, 0, userEmail);
      if (badge == null) {
        badgeRepository.badgeCompleted(badgeName, 0, userEmail);
        buildBadgeAwardedNotification(userEmail, "New Badge: Invited a friend");
      }
    }
  }

  @AfterReturning(
    pointcut = "execution(* com.eulersbridge.iEngage.core.services.ContactRequestEventHandler.acceptContactRequest(..)) && args(acceptContactRequestEvent)",
    returning = "result")
  public void updateAcceptFriendRequestTask(JoinPoint joinPoint, AcceptContactRequestEvent acceptContactRequestEvent, UpdatedEvent result) {
    if (!result.isFailed()) {
      String userEmail = Util.getUserEmailFromSession();
      // async block
      util.asyncExecUserTask(userEmail, id -> {
        String taskAction = "Add a Friend.";
        Boolean success = updateTask(userEmail, taskAction, p.expAddFriend.longValue(), null);
        if (success)
          updateAcceptFriendRequestBadge(userEmail, taskAction);
        return null;
      });
    }
  }

  public void updateAcceptFriendRequestBadge(String userEmail, String taskAction) {
    Long numOfCompCommentTask = taskRepository.getNumOfCompletedASpecificTask(userEmail, taskAction);
    String badgeName = DataConstants.AddFriendBadge;
//    Long[] badgeIds = new Long[]{14856l, 32274l, 32275l, 32276l};
    if (numOfCompCommentTask >= 25) {
      Badge badge = badgeRepository.checkBadgeCompleted(badgeName, 3, userEmail);
      if (badge == null) {
        badgeRepository.badgeCompleted(badgeName, 3, userEmail);
        buildBadgeAwardedNotification(userEmail, "New Badge: Added a friend - level 3");
      }
    } else if (numOfCompCommentTask >= 10) {
      Badge badge = badgeRepository.checkBadgeCompleted(badgeName, 2, userEmail);
      if (badge == null) {
        badgeRepository.badgeCompleted(badgeName, 2, userEmail);
        buildBadgeAwardedNotification(userEmail, "New Badge: Added a friend - level 2");
      }
    } else if (numOfCompCommentTask >= 5) {
      Badge badge = badgeRepository.checkBadgeCompleted(badgeName, 1, userEmail);
      if (badge == null) {
        badgeRepository.badgeCompleted(badgeName, 1, userEmail);
        buildBadgeAwardedNotification(userEmail, "New Badge: Added a friend - level 1");
      }
    } else if (numOfCompCommentTask >= 1) {
      Badge badge = badgeRepository.checkBadgeCompleted(badgeName, 0, userEmail);
      if (badge == null) {
        badgeRepository.badgeCompleted(badgeName, 0, userEmail);
        buildBadgeAwardedNotification(userEmail, "New Badge: Added a friend - level 0");
      }
    }
    // TODO award/revoke the badge: Select user with highest number of connections per month
  }
}
