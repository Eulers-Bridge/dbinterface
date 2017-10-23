package com.eulersbridge.iEngage.core.beans;

import com.eulersbridge.iEngage.core.notification.SNSMobilePush;
import com.eulersbridge.iEngage.database.domain.*;
import com.eulersbridge.iEngage.database.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Function;


/**
 * @author Yikai Gong
 * This class is used as a event listener bean (@Component) in Spring for
 * executing sometasks after API is setup.
 * <p>
 * By default, this listener should be turned off !
 */

@Component
public class ApplicationEventListener implements ApplicationListener<ContextRefreshedEvent> {
  private static Logger LOG = LoggerFactory.getLogger(ApplicationEventListener.class);
  @Autowired
  UserRepository userRepo;
  @Autowired
  PasswordEncoder pwdEncoder;
  @Autowired
  CountryRepository counRepo;
  @Autowired
  InstitutionRepository instRepo;
  @Autowired
  BadgeRepository bdgRepo;
  @Autowired
  TaskRepository taskRepo;
  @Autowired
  ScheduledTasks scheduledTasks;

  @Value("${institution.name}")
  String institutionName;
  @Value("${institution.state}")
  String institutionState;
  @Value("${institution.campus}")
  String institutionCampus;
  @Value("${country.name}")
  String countryName;

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    ApplicationContext ctx = event.getApplicationContext();
    // Use thread if task can be completed after service start.
    // Else, task will be done before service port being opened
    // (new Thread(() -> {   })).start();

    LOG.info("Task starts at ApplicationEventListener");

    checkOrCreateInitialNodes();
    scheduledTasks.setAppReady(true);
  }

  public void encodeUserPwd() {
    Iterable<User> userItr = userRepo.findAll(0);
    userItr.forEach(u -> {
      u.setPassword(pwdEncoder.encode(u.getPassword()));
      userRepo.save(u, 0);
    });
  }

  private void checkOrCreateInitialNodes() {
    Country country = new Country(countryName);
    persistNode(country, counRepo, counRepo.findByCountryName(country.getCountryName(), 0));
    Institution institution = new Institution(institutionName, institutionCampus, institutionState, country.toNode());
    persistNode(institution, instRepo, instRepo.findByName(institution.getName(), 0));

    forEachTaskNode(task -> persistNode(task, taskRepo, taskRepo.findByAction(task.getAction(), 0)));
    forEachBadgeNode(badge -> persistNode(badge, bdgRepo, bdgRepo.findByNameAndLevel(badge.getName(), badge.getLevel(), 0)));

  }

  private <N extends Node, R extends GraphRepository<N>> Object persistNode(N newNode, R repo, N entity) {
    if (entity == null) {
      LOG.info(newNode.getClass().getSimpleName() + " node does not exist. Creating...");
      repo.save(newNode, 0);
    } else {
      LOG.info("OK.. " + newNode.getClass().getSimpleName() + " node exists");
    }
    return null;
  }

  private void forEachTaskNode(Function<Task, Object> func) {
    ArrayList<Task> tasks = new ArrayList<>();
    tasks.add(new Task("Share.", "Share news article, photo, event", 75));
    tasks.add(new Task("Read an Article.", "Read an article", 75));
    tasks.add(new Task("Cast your Vote.", "Scan vote QR code", 500));
    tasks.add(new Task("Set Vote Reminder.", "Vote reminder", 300));
    tasks.add(new Task("Login Daily.", "Open app every 24 hours", 150));
    tasks.add(new Task("Complete Personality Questions.", "Personality Questionnaire", 500));
    tasks.add(new Task("Apply to Volunteer.", "", 250));
    tasks.add(new Task("Add an Event to Calendar.", "Add event to phone calendar", 400));
    tasks.add(new Task("Add a Friend.", "Accept friend request", 200));
    tasks.add(new Task("Invite a Friend.", "Invite a friend", 200));
    tasks.add(new Task("Post a Comment.", "Comment on poll", 200));
    tasks.add(new Task("Be a Pollster.", "Vote in a poll", 100));
    tasks.forEach(func::apply);
  }

  private void forEachBadgeNode(Function<Badge, Object> func) {
    ArrayList<Badge> badges = new ArrayList<>();
    badges.add(new Badge(DataConstants.CreateAccountBadge, "Newbie", 0, 400L));
    badges.add(new Badge(DataConstants.CreateCommentBadge, "Vox Pop", 0, 100L));
    badges.add(new Badge(DataConstants.SetVoteReminderBadge, "It's a Date", 0, 400L));
    badges.add(new Badge(DataConstants.ScanQRBadge, "Ahead of the Times", 0, 100L));
    badges.add(new Badge(DataConstants.AddFriendBadge, "Best Mates", 0, 100L));
    badges.add(new Badge(DataConstants.InviteFriendBadge, "G'day", 0, 100L));
    badges.add(new Badge(DataConstants.DailyLoginBadge, "Boomerang", 0, 100L));
    badges.add(new Badge(DataConstants.LoginDayAndNightBadge, "Day and Night", 0, 100L));
    badges.add(new Badge(DataConstants.MostActiveWeeklyPollVoterBadge, "Lord of the Polls", 0, 1000L));
    badges.add(new Badge(DataConstants.MostConnectedPersonBadge, "I am Kevin Bacon", 0, 1000L));
    badges.add(new Badge(DataConstants.MostActiveinSharingBadge, "Caffeinated", 0, 1000L));
    badges.add(new Badge(DataConstants.VoteInElectionsBadge, "I Voted!", 0, 1000L));
    badges.add(new Badge(DataConstants.CollectionBadge, "Ready to Launch", 0, 1000L));
    badges.add(new Badge(DataConstants.CompPersonalityQuizBadge, "Who Am I?", 0, 400L));
    badges.add(new Badge(DataConstants.ShareNewsBadge, "Isegoria Herald", 0, 100L));
    badges.add(new Badge(DataConstants.ShareEventBadge, "Shed Shindig", 0, 100L));
    badges.add(new Badge(DataConstants.SharePhtotBadge, "We were here", 0, 100L));
    badges.add(new Badge(DataConstants.LikedBadge, "Liked", 0, 100L));
    badges.add(new Badge(DataConstants.AddCalenEvent, "The Organiser", 0, 200L));
    badges.add(new Badge(DataConstants.VolunteersBadge, "I Helped!", 0, 400L));
    badges.add(new Badge(DataConstants.VotePollBadge, "Be Heard", 0, 100L));

    badges.add(new Badge(DataConstants.AddFriendBadge, "Gathering", 1, 200L));
    badges.add(new Badge(DataConstants.AddFriendBadge, "Party Time", 2, 300L));
    badges.add(new Badge(DataConstants.AddFriendBadge, "Community Supremo", 3, 400L));
    badges.add(new Badge(DataConstants.ShareNewsBadge, "The Sun", 1, 200L));
    badges.add(new Badge(DataConstants.ShareNewsBadge, "The Guardian", 2, 300L));
    badges.add(new Badge(DataConstants.ShareNewsBadge, "The New York Times", 3, 400L));
    badges.add(new Badge(DataConstants.ShareEventBadge, "House Hoedown", 1, 200L));
    badges.add(new Badge(DataConstants.ShareEventBadge, "Swanky Soiree", 2, 300L));
    badges.add(new Badge(DataConstants.ShareEventBadge, "Ballroom Blowout", 3, 400L));
    badges.add(new Badge(DataConstants.SharePhtotBadge, "Happy Snapper", 1, 200L));
    badges.add(new Badge(DataConstants.SharePhtotBadge, "Selfie Star", 2, 300L));
    badges.add(new Badge(DataConstants.SharePhtotBadge, "Professional Paparazzi", 3, 400L));
    badges.add(new Badge(DataConstants.VolunteersBadge, "Pro Bono", 1, 200L));
    badges.add(new Badge(DataConstants.VolunteersBadge, "Social Change Meister", 2, 300L));
    badges.add(new Badge(DataConstants.VolunteersBadge, "Social Change Architect", 3, 400L));
    badges.add(new Badge(DataConstants.VotePollBadge, "By The Way", 1, 200L));
    badges.add(new Badge(DataConstants.VotePollBadge, "On the Record", 2, 300L));
    badges.add(new Badge(DataConstants.VotePollBadge, "Community Pulse", 3, 400L));
    badges.add(new Badge(DataConstants.CreateCommentBadge, "Off My Chest", 1, 200L));
    badges.add(new Badge(DataConstants.CreateCommentBadge, "Gas-bagger", 2, 300L));
    badges.add(new Badge(DataConstants.CreateCommentBadge, "Outspoken", 3, 400L));
    badges.add(new Badge(DataConstants.InviteFriendBadge, "Two's A Crowd", 1, 200L));
    badges.add(new Badge(DataConstants.InviteFriendBadge, "Socialite", 2, 300L));
    badges.add(new Badge(DataConstants.InviteFriendBadge, "Honorary Kardashian", 3, 400L));
    badges.add(new Badge(DataConstants.CollectionBadge, "Take-Off", 1, 2000L));
    badges.add(new Badge(DataConstants.CollectionBadge, "Final Frontier", 2, 3000L));
    badges.add(new Badge(DataConstants.CollectionBadge, "Community Champion", 3, 4000L));
    badges.forEach(func::apply);
  }

}
