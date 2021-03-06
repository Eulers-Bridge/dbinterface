package com.eulersbridge.iEngage.rest.controller;

import com.eulersbridge.iEngage.core.beans.Util;
import com.eulersbridge.iEngage.core.events.*;
import com.eulersbridge.iEngage.core.events.users.*;
import com.eulersbridge.iEngage.core.events.users.UserAccountVerifiedEvent.VerificationErrorType;
import com.eulersbridge.iEngage.core.events.voteRecord.*;
import com.eulersbridge.iEngage.core.events.voteReminder.*;
import com.eulersbridge.iEngage.core.services.interfacePack.*;
import com.eulersbridge.iEngage.email.EmailConstants;
import com.eulersbridge.iEngage.email.EmailResetPWD;
import com.eulersbridge.iEngage.email.EmailVerification;
import com.eulersbridge.iEngage.rest.domain.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.LongValidator;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.exception.VelocityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;

@RestController
@RequestMapping(ControllerConstants.API_PREFIX)
public class UserController {
  private static Logger LOG = LoggerFactory.getLogger(UserController.class);

  final UserService userService;
  final EmailService emailService;
  final LikesService likesService;
  final ContactRequestService contactRequestService;
  final ServletContext servletContext;
  final VelocityEngine velocityEngine;
  final JavaMailSender emailSender;
  final BadgeService badgeService;
  final TaskService taskService;
  final Util util;
  EmailValidator emailValidator;
  LongValidator longValidator;

  @Autowired
  public UserController(UserService userService, EmailService emailService,
                        LikesService likesService,
                        ContactRequestService contactRequestService,
                        ServletContext servletContext,
                        VelocityEngine velocityEngine,
                        JavaMailSender emailSender, BadgeService badgeService,
                        TaskService taskService, Util util) {
    if (LOG.isDebugEnabled()) LOG.debug("UserController()");
    emailValidator = EmailValidator.getInstance();
    longValidator = LongValidator.getInstance();
    this.userService = userService;
    this.emailService = emailService;
    this.likesService = likesService;
    this.contactRequestService = contactRequestService;
    this.servletContext = servletContext;
    this.velocityEngine = velocityEngine;
    this.emailSender = emailSender;
    this.badgeService = badgeService;
    this.taskService = taskService;
    this.util = util;
  }

  /**
   * Is passed all the necessary data to update a user.
   * Or potentially create a new one.
   * The request must be a PUT with the necessary parameters in the
   * attached data.
   * <p/>
   * This method will return the resulting user object.
   * There will also be a relationship set up with the
   * institution the user belongs to.
   *
   * @param email the email address of the user to be updated.
   * @param user  the user object passed across as JSON.
   * @return the user object returned by the Graph Database.
   */

  @RequestMapping(method = RequestMethod.PUT, value = ControllerConstants.USER_LABEL + "/{email}")
  public @ResponseBody
  ResponseEntity<UserDomain> alterUser(@PathVariable String email,
                                       @RequestBody UserDomain user) {
    if (LOG.isInfoEnabled())
      LOG.info("Attempting to edit user. " + user.getEmail());
    ResponseEntity<UserDomain> result;
    if (!Util.getUserEmailFromSession().equals(email))
      return new ResponseEntity<UserDomain>(HttpStatus.UNAUTHORIZED);

    UpdatedEvent userEvent = userService.updateUser(new UpdateUserEvent(email, user.toUserDetails()));
    if (null != userEvent) {
      if (LOG.isDebugEnabled()) LOG.debug("userEvent - " + userEvent);
      if (!userEvent.isEntityFound()) {
        result = new ResponseEntity<UserDomain>(HttpStatus.NOT_FOUND);
      } else {
        UserDomain restUser = UserDomain.fromUserDetails((UserDetails) userEvent.getDetails());
        if (LOG.isDebugEnabled()) LOG.debug("restUser = " + restUser);
        result = new ResponseEntity<UserDomain>(restUser, HttpStatus.OK);
      }
    } else
      result = new ResponseEntity<UserDomain>(HttpStatus.BAD_REQUEST);
    return result;
  }

  /**
   * Is passed all the necessary data to update a user.
   * Or potentially create a new one.
   * The request must be a PUT with the necessary parameters in the
   * attached data.
   * <p/>
   * This method will return the resulting user object.
   * There will also be a relationship set up with the
   * institution the user belongs to.
   *
   * @param email       the email address of the user to be updated.
   * @param personality the user object passed across as JSON.
   * @return the user object returned by the Graph Database.
   */

  @RequestMapping(method = RequestMethod.PUT, value = ControllerConstants.USER_LABEL + "/{email}/personality")
  public @ResponseBody
  ResponseEntity<Personality> addUserPersonality(@PathVariable String email,
                                                 @RequestBody Personality personality) {
    ResponseEntity<Personality> result;
    if (LOG.isInfoEnabled())
      LOG.info("Attempting to add personality to user. ");
    if (LOG.isDebugEnabled()) LOG.debug("Personality - " + personality);

    AddPersonalityEvent addEvt = new AddPersonalityEvent(email, personality.toPersonalityDetails());
    if (LOG.isDebugEnabled()) LOG.debug("AddPersonalityEvent - " + addEvt);

    PersonalityAddedEvent persEvent = userService.addPersonality(addEvt);
    if (persEvent != null) {
      if (LOG.isDebugEnabled()) LOG.debug("personalityEvent - " + persEvent);
      if (persEvent.isUserFound()) {
        Personality restPersonality = Personality.fromPersonalityDetails(persEvent.getPersonalityDetails());
        if (LOG.isDebugEnabled()) LOG.debug("restUser = " + restPersonality);
        result = new ResponseEntity<Personality>(restPersonality, HttpStatus.CREATED);
      } else {
        result = new ResponseEntity<Personality>(HttpStatus.FAILED_DEPENDENCY);
      }
    } else {
      if (LOG.isWarnEnabled()) LOG.warn("personalityEvent null");
      result = new ResponseEntity<Personality>(HttpStatus.BAD_REQUEST);
    }
    return result;
  }

  @RequestMapping(method = RequestMethod.PUT, value = ControllerConstants.USER_LABEL + "/{email}/PPSEQuestions")
  public @ResponseBody
  ResponseEntity<PPSEQuestions> addUserPPSEQuestions(@PathVariable String email, @RequestBody PPSEQuestions ppseQuestions) {
    if (LOG.isInfoEnabled())
      LOG.info("Attempting to add PPSEQuestions to user: " + email);
    if (LOG.isDebugEnabled()) LOG.debug("PPSEQuestions - " + ppseQuestions);

    RequestHandledEvent<PPSEQuestions> requestHandledEvent = userService.addPPSEQuestions(email, ppseQuestions);
    if (requestHandledEvent.getUserNotFound())
      return new ResponseEntity<>(HttpStatus.FAILED_DEPENDENCY);
    if (requestHandledEvent.getBadRequest() || !requestHandledEvent.getSuccess())
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    return new ResponseEntity<>(requestHandledEvent.getResponseEntity(), HttpStatus.CREATED);
  }

  /**
   * Is passed all the necessary data to create a vote reminder.
   * The request must be a PUT with the necessary parameters in the
   * attached data.
   * <p/>
   * This method will return the resulting vote reminder object.
   *
   * @param email        the email address of the user to be updated.
   * @param voteReminder the user object passed across as JSON.
   * @return the user object returned by the Graph Database.
   */

  @RequestMapping(method = RequestMethod.PUT, value = ControllerConstants.USER_LABEL + "/{email}" + ControllerConstants.VOTE_REMINDER_LABEL)
  public @ResponseBody
  ResponseEntity<VoteReminderDomain> addVoteReminder(@PathVariable String email,
                                                     @RequestBody VoteReminderDomain voteReminder) {

    voteReminder.setUserEmail(Util.getUserEmailFromSession());
    RequestHandledEvent res = userService.addVoteReminder(voteReminder);
    return res.toResponseEntity();
  }

  /**
   * Is passed all the necessary data to create a vote record.
   * The request must be a PUT with the necessary parameters in the
   * attached data.
   * <p/>
   * This method will return the resulting vote record object.
   *
   * @param email      the email address of the user to be updated.
   * @param voteRecord the user object passed across as JSON.
   * @return the user object returned by the Graph Database.
   */

  @RequestMapping(method = RequestMethod.PUT, value = ControllerConstants.USER_LABEL + "/{email}" + ControllerConstants.VOTE_RECORD_LABEL)
  public @ResponseBody
  ResponseEntity<VoteRecord> addVoteRecord(@PathVariable String email,
                                           @RequestBody VoteRecord voteRecord) {
    ResponseEntity<VoteRecord> result;
    if (LOG.isInfoEnabled())
      LOG.info("Attempting to add vote record to user. ");
    if (LOG.isDebugEnabled()) LOG.debug("VoteReminder - " + voteRecord);

    VoteRecordDetails recDetails = voteRecord.toVoteRecordDetails();
    recDetails.setVoterId(email);
    AddVoteRecordEvent addEvt = new AddVoteRecordEvent(recDetails);
    if (LOG.isDebugEnabled()) LOG.debug("AddVoteRecordEvent - " + addEvt);

    VoteRecordAddedEvent vrEvent = userService.addVoteRecord(addEvt);
    if (vrEvent != null) {
      if (LOG.isDebugEnabled()) LOG.debug("personalityEvent - " + vrEvent);
      if (vrEvent.isUserFound()) {
        VoteRecord restVoteRecord = VoteRecord.fromVoteRecordDetails((VoteRecordDetails) vrEvent.getDetails());
        if (LOG.isDebugEnabled()) LOG.debug("restUser = " + restVoteRecord);
        result = new ResponseEntity<VoteRecord>(restVoteRecord, HttpStatus.CREATED);
      } else {
        result = new ResponseEntity<VoteRecord>(HttpStatus.FAILED_DEPENDENCY);
      }
    } else {
      if (LOG.isWarnEnabled()) LOG.warn("voteRecordEvent null");
      result = new ResponseEntity<VoteRecord>(HttpStatus.BAD_REQUEST);
    }
    return result;
  }

  /**
   * Is passed all the necessary data to read voteReminders from the database. The
   * request must be a GET with the userId.
   * <p/>
   * This method will return the voteReminders read from the database.
   *
   * @param email the userId who has the voteReminder objects to be read.
   * @return the contacts.
   */
  @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.USER_LABEL + "/{email}" + ControllerConstants.VOTE_REMINDERS_LABEL)
  public @ResponseBody
  ResponseEntity<WrappedDomainList> findVoteReminders(@PathVariable String email,
                                                      @RequestParam(value = "direction", required = false, defaultValue = ControllerConstants.DIRECTION) String direction,
                                                      @RequestParam(value = "page", required = false, defaultValue = ControllerConstants.PAGE_NUMBER) String page,
                                                      @RequestParam(value = "pageSize", required = false, defaultValue = ControllerConstants.PAGE_LENGTH) String pageSize
  ) {
    int pageNumber = 0;
    int pageLength = 10;
    pageNumber = Integer.parseInt(page);
    pageLength = Integer.parseInt(pageSize);
    Direction sortDirection = Direction.DESC;
    if (direction.equalsIgnoreCase("asc")) sortDirection = Direction.ASC;
    if (LOG.isInfoEnabled())
      LOG.info("Attempting to find existing vote reminders. " + email);
    ResponseEntity<WrappedDomainList> response;

    ReadAllEvent userEvent;
    AllReadEvent voteRemindersEvent;

    if (longValidator.isValid(email)) {
      Long id = longValidator.validate(email);
      if (LOG.isDebugEnabled()) LOG.debug("UserId supplied. - " + id);
      userEvent = new ReadAllEvent(id);
      voteRemindersEvent = userService.readVoteRemindersById(userEvent, sortDirection, pageNumber, pageLength);
    } else if (emailValidator.isValid(email)) {
      if (LOG.isDebugEnabled()) LOG.debug("Email supplied.");
      voteRemindersEvent = userService.readVoteRemindersByEmail(new RequestReadUserEvent(email), sortDirection, pageNumber, pageLength);
    } else
      return new ResponseEntity<WrappedDomainList>(HttpStatus.BAD_REQUEST);


    if (!voteRemindersEvent.isEntityFound()) {
      response = new ResponseEntity<WrappedDomainList>(HttpStatus.NOT_FOUND);
    } else {
      Iterator<VoteReminderDomain> voteReminders = VoteReminderDomain.toVoteRemindersIterator(voteRemindersEvent.getDetails().iterator());
      WrappedDomainList theVoteReminders = WrappedDomainList.fromIterator(voteReminders, voteRemindersEvent.getTotalItems(), voteRemindersEvent.getTotalPages());
      response = new ResponseEntity<WrappedDomainList>(theVoteReminders, HttpStatus.OK);
    }
    return response;
  }

  /**
   * Is passed all the necessary data to read voteRecords from the database. The
   * request must be a GET with the userId.
   * <p/>
   * This method will return the voteRecords read from the database.
   *
   * @param email the userId who has the voteRecord objects to be read.
   * @return the contacts.
   */
  @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.USER_LABEL + "/{email}" + ControllerConstants.VOTE_RECORDS_LABEL)
  public @ResponseBody
  ResponseEntity<WrappedDomainList> findVoteRecords(@PathVariable String email,
                                                    @RequestParam(value = "direction", required = false, defaultValue = ControllerConstants.DIRECTION) String direction,
                                                    @RequestParam(value = "page", required = false, defaultValue = ControllerConstants.PAGE_NUMBER) String page,
                                                    @RequestParam(value = "pageSize", required = false, defaultValue = ControllerConstants.PAGE_LENGTH) String pageSize
  ) {
    int pageNumber = 0;
    int pageLength = 10;
    pageNumber = Integer.parseInt(page);
    pageLength = Integer.parseInt(pageSize);
    Direction sortDirection = Direction.DESC;
    if (direction.equalsIgnoreCase("asc")) sortDirection = Direction.ASC;
    if (LOG.isInfoEnabled())
      LOG.info("Attempting to find existing vote records. " + email);

    ReadAllEvent userEvent;
    AllReadEvent voteRecordsEvent;
    ResponseEntity<WrappedDomainList> response;

    if (longValidator.isValid(email)) {
      Long id = longValidator.validate(email);
      if (LOG.isDebugEnabled()) LOG.debug("UserId supplied. - " + id);
      userEvent = new ReadAllEvent(id);
      voteRecordsEvent = userService.readVoteRecordsById(userEvent, sortDirection, pageNumber, pageLength);
    } else if (emailValidator.isValid(email)) {
      if (LOG.isDebugEnabled()) LOG.debug("Email supplied.");
      voteRecordsEvent = userService.readVoteRecordsByEmail(new RequestReadUserEvent(email), sortDirection, pageNumber, pageLength);
    } else
      return new ResponseEntity<WrappedDomainList>(HttpStatus.BAD_REQUEST);


    if (!voteRecordsEvent.isEntityFound()) {
      response = new ResponseEntity<WrappedDomainList>(HttpStatus.NOT_FOUND);
    } else {
      Iterator<VoteRecord> voteRecords = VoteRecord.toVoteRecordsIterator(voteRecordsEvent.getDetails().iterator());
      WrappedDomainList theVoteRecords = WrappedDomainList.fromIterator(voteRecords, voteRecordsEvent.getTotalItems(), voteRecordsEvent.getTotalPages());
      response = new ResponseEntity<WrappedDomainList>(theVoteRecords, HttpStatus.OK);
    }
    return response;
  }

  /**
   * Is passed all the necessary data to read a vote reminder from the database.
   * The request must be a GET with the vote record id presented
   * as the final portion of the URL.
   * <p/>
   * This method will return the voteReminder object read from the database.
   *
   * @param id the vote reminder id of the vote reminder object to be read.
   * @return the vote reminder object.
   */
  @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.USER_LABEL + ControllerConstants.VOTE_REMINDER_LABEL + "/{id}")
  public @ResponseBody
  ResponseEntity<VoteReminderDomain> findVoteReminder(@PathVariable Long id) {
    if (LOG.isInfoEnabled())
      LOG.info("Attempting to retrieve voteReminder. " + id);
    ReadEvent evt = userService.readVoteReminder(new ReadVoteReminderEvent(id));

    if (!evt.isEntityFound()) {
      return new ResponseEntity<VoteReminderDomain>(HttpStatus.NOT_FOUND);
    }
    VoteReminderDomain restVoteReminder = VoteReminderDomain.fromVoteReminderDetails((VoteReminderDetails) evt.getDetails());
    return new ResponseEntity<VoteReminderDomain>(restVoteReminder, HttpStatus.OK);
  }

  /**
   * Is passed all the necessary data to read a vote record from the database.
   * The request must be a GET with the vote record id presented
   * as the final portion of the URL.
   * <p/>
   * This method will return the voteRecord object read from the database.
   *
   * @param id the vote record id of the vote record object to be read.
   * @return the vote record object.
   */
  @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.USER_LABEL + ControllerConstants.VOTE_RECORD_LABEL + "/{id}")
  public @ResponseBody
  ResponseEntity<VoteRecord> findVoteRecord(@PathVariable Long id) {
    if (LOG.isInfoEnabled())
      LOG.info("Attempting to retrieve voteRecord. " + id);
    ReadEvent evt = userService.readVoteRecord(new ReadVoteRecordEvent(id));

    if (!evt.isEntityFound()) {
      return new ResponseEntity<VoteRecord>(HttpStatus.NOT_FOUND);
    }
    VoteRecord restVoteRecord = VoteRecord.fromVoteRecordDetails((VoteRecordDetails) evt.getDetails());
    return new ResponseEntity<VoteRecord>(restVoteRecord, HttpStatus.OK);
  }

  /**
   * Is passed all the necessary data to read a vote reminder from the database.
   * The request must be a GET with the vote record id presented
   * as the final portion of the URL.
   * <p/>
   * This method will return the voteReminder object read from the database.
   *
   * @param id the vote reminder id of the vote reminder object to be read.
   * @return the vote reminder object.
   */
  @RequestMapping(method = RequestMethod.DELETE, value = ControllerConstants.USER_LABEL + ControllerConstants.VOTE_REMINDER_LABEL + "/{id}")
  public @ResponseBody
  ResponseEntity<VoteReminderDomain> deleteVoteReminder(@PathVariable Long id) {
    if (LOG.isInfoEnabled())
      LOG.info("Attempting to delete voteReminder. " + id);
    DeletedEvent evt = userService.deleteVoteReminder(new DeleteVoteReminderEvent(id));

    if (!evt.isEntityFound()) {
      return new ResponseEntity<VoteReminderDomain>(HttpStatus.NOT_FOUND);
    }
    VoteReminderDomain restVoteReminder = new VoteReminderDomain();
    restVoteReminder.setNodeId(evt.getNodeId());
    return new ResponseEntity<VoteReminderDomain>(restVoteReminder, HttpStatus.OK);
  }

  /**
   * Is passed all the necessary data to read a vote record from the database.
   * The request must be a GET with the vote record id presented
   * as the final portion of the URL.
   * <p/>
   * This method will return the voteRecord object read from the database.
   *
   * @param id the vote record id of the vote record object to be read.
   * @return the vote record object.
   */
  @RequestMapping(method = RequestMethod.DELETE, value = ControllerConstants.USER_LABEL + ControllerConstants.VOTE_RECORD_LABEL + "/{id}")
  public @ResponseBody
  ResponseEntity<VoteRecord> deleteVoteRecord(@PathVariable Long id) {
    if (LOG.isInfoEnabled()) LOG.info("Attempting to delete voteRecord. " + id);
    DeletedEvent evt = userService.deleteVoteRecord(new DeleteVoteRecordEvent(id));

    if (!evt.isEntityFound()) {
      return new ResponseEntity<VoteRecord>(HttpStatus.NOT_FOUND);
    }
    VoteRecord restVoteRecord = new VoteRecord();
    restVoteRecord.setNodeId(evt.getNodeId());
    return new ResponseEntity<VoteRecord>(restVoteRecord, HttpStatus.OK);
  }

  /**
   * Is passed all the necessary data to read a user from the database.
   * The request must be a GET with the user email presented
   * as the final portion of the URL.
   * <p/>
   * This method will return the user object read from the database.
   *
   * @param email the email address of the user object to be read.
   * @return the user object.
   */
  @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.USER_LABEL + "/{email}")
  public @ResponseBody
  ResponseEntity<UserDomain> findUser(@PathVariable String email) {
    if (LOG.isInfoEnabled()) LOG.info("Attempting to retrieve user. " + email);
    ReadUserEvent userEvent;
    ResponseEntity<UserDomain> response;

    if (longValidator.isValid(email)) {
      Long id = longValidator.validate(email);
      if (LOG.isDebugEnabled()) LOG.debug("UserId supplied. - " + id);
      RequestReadUserEvent evt = new RequestReadUserEvent(id);
      userEvent = userService.readUserById(evt);
    } else if (emailValidator.isValid(email)) {
      if (LOG.isDebugEnabled()) LOG.debug("Email supplied.");
      userEvent = userService.readUser(new RequestReadUserEvent(email));
    } else
      return new ResponseEntity<UserDomain>(HttpStatus.BAD_REQUEST);


    if (!userEvent.isEntityFound()) {
      response = new ResponseEntity<UserDomain>(HttpStatus.NOT_FOUND);
    } else {
      UserDomain restUser = UserDomain.fromUserDetails((UserDetails) userEvent.getDetails());
      response = new ResponseEntity<UserDomain>(restUser, HttpStatus.OK);
    }
    return response;
  }


  /**
   * Is passed all the necessary data to read contacts from the database. The
   * request must be a GET with the userId presented as the final
   * portion of the URL.
   * <p/>
   * This method will return the contacts read from the database.
   *
   * @param email the userId who has the contact objects to be read.
   * @return the contacts.
   */
  @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.USER_LABEL + "/{email}" + ControllerConstants.SUPPORT)
  public @ResponseBody
  ResponseEntity<Iterator<Ticket>> findSupports(@PathVariable String email,
                                                @RequestParam(value = "direction", required = false, defaultValue = ControllerConstants.DIRECTION) String direction,
                                                @RequestParam(value = "page", required = false, defaultValue = ControllerConstants.PAGE_NUMBER) String page,
                                                @RequestParam(value = "pageSize", required = false, defaultValue = ControllerConstants.PAGE_LENGTH) String pageSize
  ) {
    int pageNumber = 0;
    int pageLength = 10;
    pageNumber = Integer.parseInt(page);
    pageLength = Integer.parseInt(pageSize);
    Direction sortDirection = Direction.DESC;
    if (direction.equalsIgnoreCase("asc")) sortDirection = Direction.ASC;
    if (LOG.isInfoEnabled())
      LOG.info("Attempting to find existing contacts. " + email);

    ReadAllEvent userEvent;
    AllReadEvent supportsEvent;
    ResponseEntity<Iterator<Ticket>> result;

    if (longValidator.isValid(email)) {
      Long id = longValidator.validate(email);
      if (LOG.isDebugEnabled()) LOG.debug("UserId supplied. - " + id);
      userEvent = new ReadAllEvent(id);
      supportsEvent = userService.readSupportsById(userEvent, sortDirection, pageNumber, pageLength);
    } else if (emailValidator.isValid(email)) {
      if (LOG.isDebugEnabled()) LOG.debug("Email supplied.");
      supportsEvent = userService.readSupportsByEmail(new RequestReadUserEvent(email), sortDirection, pageNumber, pageLength);
    } else
      return new ResponseEntity<Iterator<Ticket>>(HttpStatus.BAD_REQUEST);


    if (!supportsEvent.isEntityFound()) {
      return new ResponseEntity<Iterator<Ticket>>(HttpStatus.NOT_FOUND);
    }
    Iterator<Ticket> contactProfiles = Ticket.toTicketsIterator(supportsEvent.getDetails().iterator());

    result = new ResponseEntity<Iterator<Ticket>>(contactProfiles, HttpStatus.OK);

    return result;
  }


  /**
   * Is passed all the necessary data to create a new user.
   * The request must be a POST with the necessary parameters in the
   * attached data.
   * <p/>
   * This method will return the resulting user object.
   * There will also be a relationship set up with the
   * institution the user belongs to.
   *
   * @param user the user object passed across as JSON.
   * @return the user object returned by the Graph Database.
   */

  @RequestMapping(method = RequestMethod.POST, value = ControllerConstants.SIGNUP_LABEL)
  public @ResponseBody
  ResponseEntity<UserDomain> saveNewUser(@RequestBody UserDomain user) {
    if (LOG.isInfoEnabled()) LOG.info("attempting to save user " + user);
    // Assign default institution Id if missing.
    if (user.getInstitutionId() == null)
      user.setInstitutionId(util.getDefaultInstitutionId());

    UserCreatedEvent userEvent = userService.signUpNewUser(new CreateUserEvent(user.toUserDetails()));

    if ((null == userEvent) || (null == userEvent.getEmail())) {
      return new ResponseEntity<UserDomain>(HttpStatus.BAD_REQUEST);
    } else if (!userEvent.isInstituteFound()) {
      return new ResponseEntity<UserDomain>(HttpStatus.FAILED_DEPENDENCY);
    } else if (!userEvent.isUserUnique()) {
      return new ResponseEntity<UserDomain>(HttpStatus.CONFLICT);
    } else {
      UserDomain restUser = UserDomain.fromUserDetails((UserDetails) userEvent.getDetails());
      if (LOG.isDebugEnabled())
        LOG.debug(userEvent.getVerificationEmail().toString());
      EmailVerification emailVerification = userEvent.getVerificationEmail();
      util.asyncExecUserTask(emailVerification, mail -> {
        emailService.sendEmail(mail);
        return null;
      });
      return new ResponseEntity<UserDomain>(restUser, HttpStatus.CREATED);
    }
  }

  /**
   * Is passed all the necessary data to resend the verification email.
   * The request must be a GET with the email addressed in the
   * url.
   * <p/>
   * This method will return the user object.
   * The verification email will also be resent.
   *
   * @param email the user email address passed across in the URL.
   * @return the user object returned by the Graph Database.
   */

  @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.EMAIL_VERIFICATION_LABEL + "/{email}/resendEmail")
  public @ResponseBody
  ResponseEntity<UserDomain> resendVerificationEmail(@PathVariable String email) {
    if (LOG.isInfoEnabled())
      LOG.info("attempting to resend verification email to user " + email);

    RequestHandledEvent res = userService.resendVerificationEmail(email);
    return res.toResponseEntity();
  }

  @RequestMapping(method = RequestMethod.POST, value = ControllerConstants.REQUEST_RESET_PWD + "/{email}")
  public @ResponseBody
  ResponseEntity<String> requestResetUserPassword(@PathVariable String email) {
    if (email == null || !EmailValidator.getInstance().isValid(email))
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    RequestHandledEvent<EmailResetPWD> r = userService.requestResetPWD(email);
    if (r.getUserNotFound())
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    if (!r.getSuccess())
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    EmailResetPWD emailResetPWD = r.getResponseEntity();
    util.asyncExecUserTask(emailResetPWD, mail -> {
      emailService.sendEmail(mail);
      return null;
    });
    return new ResponseEntity<>("An email has been sent to your inbox", HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.PUT, value = ControllerConstants.RESET_PWD)
  public @ResponseBody
  ResponseEntity<String> resetUserPassword(@RequestBody String body) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    Map reqMap = objectMapper.readValue(body, HashMap.class);
    String email = String.valueOf(reqMap.get("email"));
    String token = String.valueOf(reqMap.get("token"));
    String password = String.valueOf(reqMap.get("password"));
    // Check request parameters
    if (email.equals("null") || token.equals("null") || password.equals("null"))
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    if (email.isEmpty() || token.isEmpty() || password.isEmpty())
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    if (!EmailValidator.getInstance().isValid(email))
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    RequestHandledEvent result = userService.resetPwd(email, token, password);
    if (!result.getSuccess()) {
      if (result.getUserNotFound())
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      if (result.getNotAllowed())
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
      if (result.getBadRequest())
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      if (result.getPremissionExpired())
        return new ResponseEntity<>(HttpStatus.GONE);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<>(HttpStatus.OK);
  }

  /**
   * Is passed all the necessary data to verify a user account.
   * The request must be a GET with the necessary parameters in the
   * URL.
   * <p/>
   * This method will return the user object owner of the verified account.
   *
   * @param email the email address of the user account to be verified.
   * @param token the token string to be used to verify the user account.
   * @return the user object returned by the Graph Database.
   */
  @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.EMAIL_VERIFICATION_LABEL + "/{email}/{token}")
  public @ResponseBody
  RedirectView verifyUserAccount1(@PathVariable String email, @PathVariable String token) {
    return verifyUserAccount(email, token);
  }

  /**
   * Is passed all the necessary data to verify a user account.
   * The request must be a POST with the necessary parameters in the
   * URL.
   * <p/>
   * This method will return the user object owner of the verified account.
   *
   * @param email the email address of the user account to be verified.
   * @param token the token string to be used to verify the user account.
   * @return the user object returned by the Graph Database.
   */
  @RequestMapping(method = RequestMethod.POST, value = ControllerConstants.EMAIL_VERIFICATION_LABEL + "/{email}/{token}")
  public @ResponseBody
  RedirectView verifyUserAccount2(@PathVariable String email, @PathVariable String token) {
    return verifyUserAccount(email, token);
  }

  public @ResponseBody
  RedirectView verifyUserAccount(String email, String token) {
    if (LOG.isInfoEnabled())
      LOG.info("attempting to verify email by token " + email + " " + token);

    UUID decodedToken = com.eulersbridge.iEngage.database.domain.VerificationToken.convertEncoded64URLStringtoUUID(token);
    if (LOG.isDebugEnabled()) LOG.debug("Decoded token - " + decodedToken);
    UserAccountVerifiedEvent userAccountVerifiedEvent = userService.validateUserAccount(new VerifyUserAccountEvent(email, decodedToken.toString()));
    RedirectView redirectView = new RedirectView();
    StringBuilder urlBuilder = new StringBuilder("https://isegoria.app/welcome?username=");
    String formalName = userService.getFormalNameByEmail(email);
    urlBuilder.append(formalName);

    if (userAccountVerifiedEvent.isAccountVerified()) {
      urlBuilder.append("&success=true");
    } else {
      urlBuilder.append("&success=false");
      VerificationErrorType verfError = userAccountVerifiedEvent.getVerificationError();
      if (verfError.equals(UserAccountVerifiedEvent.VerificationErrorType.userNotFound)) {
        urlBuilder.append("&message=This email address is not found");
      }
      else if (verfError.equals(UserAccountVerifiedEvent.VerificationErrorType.tokenDoesntExists.toString())) {
        urlBuilder.append("&message=The verification token does not exist. Please contact the admin for help.");
      }
      else if (verfError.equals(UserAccountVerifiedEvent.VerificationErrorType.tokenAlreadyUsed.toString())
        || verfError.equals(UserAccountVerifiedEvent.VerificationErrorType.tokenExpired.toString())
        || verfError.equals(UserAccountVerifiedEvent.VerificationErrorType.tokenTypeMismatch.toString())) {
        urlBuilder.append("&message=The verification token does not match or it is expired.");
      }
    }
    redirectView.setUrl(urlBuilder.toString());
    return redirectView;
  }


  @RequestMapping(value = "/displayParams")
  public @ResponseBody
  ResponseEntity<Boolean> displayDetails(@RequestBody UserDomain user) {
    if (LOG.isInfoEnabled())
      LOG.info("user = " + user);
    return new ResponseEntity<Boolean>(true, HttpStatus.OK);
  }

  @RequestMapping(value = "/testResources")
  public @ResponseBody
  ResponseEntity<StringWriter> testResources() throws Exception {
    if (LOG.isInfoEnabled())
      LOG.info("testResources()");
    if (LOG.isDebugEnabled())
      LOG.debug("this.velocityEngine = " + this.velocityEngine);
    LOG.debug("Real path = " + servletContext.getRealPath("/"));
    LOG.debug("Real path = " + servletContext.getRealPath("/WEB-INF/"));
    String resourceName = EmailConstants.EmailVerificationTemplate;
    Template t;
    StringWriter sw = new StringWriter();
    try {
      String info = servletContext.getServerInfo();
      if (LOG.isDebugEnabled()) {
        LOG.debug("info - " + info);
      }
      velocityEngine.setApplicationAttribute("javax.servlet.ServletContext", servletContext);
      velocityEngine.init();

      VelocityContext context = new VelocityContext();
      context.put("recipientName", "Greg Newitt");
      context.put("emailAddress", "gnewitt@hotmail.com");
      context.put("verificationToken", "blahblahblah");

      final Map<String, Object> hTemplateVariables = new HashMap<String, Object>();
      VelocityContext velocityContext = new VelocityContext();
      velocityContext.put("recipientName", "Greg Newitt");
      velocityContext.put("emailAddress", "gnewitt@hotmail.com");
      velocityContext.put("verificationToken", "blahblahblah");


      boolean exists = velocityEngine.resourceExists(resourceName);

      if (LOG.isDebugEnabled()) {
        if (exists)
          LOG.debug("Resource name " + resourceName + " exists.");
        else
          LOG.warn("validate template2 does not exist. ");
      }
//      t = velocityEngine.getTemplate(resourceName);
//      t.merge(context, sw);

      String velocityModel = resourceName;
      velocityEngine.mergeTemplate(velocityModel, "UTF-8", velocityContext, sw);
      String body = sw.toString();
//      String body = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, velocityModel, "UTF-8", hTemplateVariables);

      LOG.debug(sw.toString());
      LOG.debug(body);
    } catch (ResourceNotFoundException re) {
      LOG.warn("Resource not found");
    } catch (VelocityException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return new ResponseEntity<StringWriter>(sw, HttpStatus.OK);
  }


  @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.SEARCH_USER_LABEL + "/{inputName}")
  public @ResponseBody
  ResponseEntity<Iterator<UserProfile>> searchUserProfiles(@PathVariable String inputName) {
    LOG.info("Attempting to search User profiles Input:" + inputName);
    ResponseEntity result = null;
    if (EmailValidator.getInstance().isValid(inputName)) {
      ResponseEntity<UserProfile> foundUser = findUserProfile(inputName);
      UserProfile u = foundUser.getBody();
      List<UserProfile> uList = u == null ? Lists.newArrayList() : Lists.newArrayList(u);
      result = new ResponseEntity<Iterator<UserProfile>>(uList.iterator(), HttpStatus.OK);
    } else {
      RequestSearchUserEvent requestSearchUserEvent = new RequestSearchUserEvent(inputName);
      SearchUserEvent searchUserEvent = userService.searchUserProfileByName(requestSearchUserEvent);
      List<UserProfile> userProfileList = searchUserEvent.getUserProfileList();
      result = new ResponseEntity<Iterator<UserProfile>>(userProfileList.iterator(), HttpStatus.OK);
    }
    return result;
  }

  @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.CONTACT_LABEL + "/{contactInfo}")
  public @ResponseBody
  ResponseEntity<UserProfile> findUserProfile(@PathVariable String contactInfo) {
    if (LOG.isInfoEnabled())
      LOG.info("Attempting to find contact. " + contactInfo);

    ReadUserEvent userEvent;
    ResponseEntity<UserProfile> result;
    boolean isEmail = emailValidator.isValid(contactInfo);
    String email = null;

    if (isEmail) {
      email = contactInfo;
      userEvent = userService.readUserByContactEmail(new RequestReadUserEvent(email));
    } else {
      userEvent = userService.readUserByContactNumber(new RequestReadUserEvent(contactInfo));
    }

    if (!userEvent.isEntityFound()) {
      result = new ResponseEntity<UserProfile>(HttpStatus.NOT_FOUND);
    } else {
      UserDetails dets = (UserDetails) userEvent.getDetails();
      if (!isEmail)
        dets.setEmail(null);
      if (LOG.isDebugEnabled()) LOG.debug("dets - " + dets);
      UserProfile restUser = UserProfile.fromUserDetails(dets);
      restUser.setTotalTasks(taskService.getTotalNumOfTasks());
      restUser.setTotalBadges(badgeService.getTotalNumOfBadges());
      result = new ResponseEntity<UserProfile>(restUser, HttpStatus.OK);
      if (LOG.isDebugEnabled()) LOG.debug("result - " + result);
    }
    return result;
  }

//  @RequestMapping(value = "/redirect", method = RequestMethod.GET)
//  public RedirectView localRedirect() {
////    RedirectView redirectView = new RedirectView();
////    redirectView.setUrl("http://www.google.com");
//    String email = "yikaig@student.unimelb.edu.au";
//    RedirectView redirectView = new RedirectView();
//    StringBuilder urlBuilder = new StringBuilder("https://isegoria.app/welcome?username=");
//    String formalName = userService.getFormalNameByEmail(email);
//    urlBuilder.append(formalName);
//
//    redirectView.setUrl(urlBuilder.toString());
//    return redirectView;
//  }

}

