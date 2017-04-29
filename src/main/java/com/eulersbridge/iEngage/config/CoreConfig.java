package com.eulersbridge.iEngage.config;

import com.eulersbridge.iEngage.core.services.*;
import com.eulersbridge.iEngage.database.repository.*;
import com.eulersbridge.iEngage.rest.domain.CountriesFactory;
import com.eulersbridge.iEngage.rest.domain.stubCountryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreConfig {
  BadgeRepository badgeRepository;
  CandidateRepository candidateRepository;
  CommentRepository commentRepository;
  ContactRequestRepository contactRequestRepository;
  ConfigurationRepository configurationRepository;
  CountryRepository countryRepo;
  ElectionRepository electionRepo;
  EventRepository eventRepository;
  ForumQuestionRepository forumQuestionRepository;
  InstitutionRepository institutionRepository;
  NewsArticleRepository newsRepo;
  NewsFeedRepository newsFeedRepository;
  NotificationRepository notificationRepository;
  OwnerRepository ownerRepository;
  PersonalityRepository personRepo;
  PollRepository pollRepository;
  PollAnswerRepository pollAnswerRepository;
  PhotoRepository photoRepository;
  PhotoAlbumRepository photoAlbumRepository;
  PositionRepository positionRepository;
  TicketRepository ticketRepository;
  TaskRepository taskRepository;
  UserRepository userRepo;
  VerificationTokenRepository tokenRepo;
  VotingLocationRepository votingLocationRepository;

  private static Logger LOG = LoggerFactory.getLogger(CoreConfig.class);

  @Autowired
  public CoreConfig(TaskRepository taskRepository,
                    ElectionRepository electionRepo,
                    PhotoRepository photoRepository,
                    PositionRepository positionRepository,
                    EventRepository eventRepository,
                    NewsArticleRepository newsRepo,
                    InstitutionRepository institutionRepository,
                    NewsFeedRepository newsFeedRepository,
                    CandidateRepository candidateRepository,
                    ForumQuestionRepository forumQuestionRepository,
                    PollRepository pollRepository,
                    CountryRepository countryRepo,
                    CommentRepository commentRepository,
                    VerificationTokenRepository tokenRepo,
                    OwnerRepository ownerRepository,
                    TicketRepository ticketRepository,
                    PhotoAlbumRepository photoAlbumRepository,
                    BadgeRepository badgeRepository,
                    VotingLocationRepository votingLocationRepository,
                    PersonalityRepository personRepo,
                    ConfigurationRepository configurationRepository,
                    PollAnswerRepository pollAnswerRepository,
                    UserRepository userRepo,
                    NotificationRepository notificationRepository,
                    ContactRequestRepository contactRequestRepository) {
    if (LOG.isDebugEnabled()) LOG.debug("CoreConfig()");
    this.taskRepository = taskRepository;
    this.electionRepo = electionRepo;
    this.photoRepository = photoRepository;
    this.positionRepository = positionRepository;
    this.eventRepository = eventRepository;
    this.newsRepo = newsRepo;
    this.institutionRepository = institutionRepository;
    this.newsFeedRepository = newsFeedRepository;
    this.candidateRepository = candidateRepository;
    this.forumQuestionRepository = forumQuestionRepository;
    this.pollRepository = pollRepository;
    this.countryRepo = countryRepo;
    this.commentRepository = commentRepository;
    this.tokenRepo = tokenRepo;
    this.ownerRepository = ownerRepository;
    this.ticketRepository = ticketRepository;
    this.photoAlbumRepository = photoAlbumRepository;
    this.badgeRepository = badgeRepository;
    this.votingLocationRepository = votingLocationRepository;
    this.personRepo = personRepo;
    this.configurationRepository = configurationRepository;
    this.pollAnswerRepository = pollAnswerRepository;
    this.userRepo = userRepo;
    this.notificationRepository = notificationRepository;
    this.contactRequestRepository = contactRequestRepository;
  }

  @Bean
  public UserService createUserService() {
    if (LOG.isDebugEnabled()) LOG.debug("createUserService()");
    return new UserEventHandler(userRepo, personRepo, institutionRepository, tokenRepo);
  }

  @Bean
  public InstitutionService createInstitutionService() {
    if (LOG.isDebugEnabled()) LOG.debug("createInstitutionService()");
    return new InstitutionEventHandler(institutionRepository, countryRepo, newsFeedRepository);
  }

  @Bean
  public ContactRequestService createContactRequestService() {
    if (LOG.isDebugEnabled()) LOG.debug("createContactRequestService()");
    return new ContactRequestEventHandler(contactRequestRepository, userRepo);
  }

  @Bean
  public CountryService createCountryService() {
    if (LOG.isDebugEnabled()) LOG.debug("createCountryService()");
    return new CountryEventHandler(countryRepo);
  }

  @Bean
  public NewsService createNewsService() {
    if (LOG.isDebugEnabled()) LOG.debug("createNewsService()");
    return new NewsEventHandler(newsRepo, userRepo, institutionRepository);
  }

  @Bean
  public CountriesFactory createCountryFactory() {
    if (LOG.isDebugEnabled()) LOG.debug("createCountryFactory()");
    return new stubCountryFactory();

  }

  @Bean
  public ElectionService createElectionService() {
    if (LOG.isDebugEnabled()) LOG.debug("createElectionService()");
    return new ElectionEventHandler(electionRepo, institutionRepository);
  }

  @Bean
  public PollService createPollService() {
    if (LOG.isDebugEnabled()) LOG.debug("createPollService()");
    return new PollEventHandler(pollRepository, pollAnswerRepository,
      ownerRepository, institutionRepository);
  }

  @Bean
  public EventService createEventService() {
    if (LOG.isDebugEnabled()) LOG.debug("createEventService()");
    return new EventEventHandler(eventRepository, institutionRepository);
  }

  @Bean
  public PhotoService createPhotoService() {
    if (LOG.isDebugEnabled()) LOG.debug("createPhotoService()");
    return new PhotoEventHandler(photoRepository, photoAlbumRepository, ownerRepository);
  }

  @Bean
  public ForumQuestionService createForumQuestionService() {
    if (LOG.isDebugEnabled()) LOG.debug("createForumQuestionService()");
    return new ForumQuestionEventHandler(forumQuestionRepository);
  }

  @Bean
  public PositionService createPositionService() {
    if (LOG.isDebugEnabled()) LOG.debug("createPositionService()");
    return new PositionEventHandler(positionRepository, electionRepo, candidateRepository);
  }

  @Bean
  public ConfigurationService createConfigurationService() {
    if (LOG.isDebugEnabled()) LOG.debug("createConfigurationService()");
    return new ConfigurationEventHandler(configurationRepository);
  }

  @Bean
  public LikesService createLikesService() {
    if (LOG.isDebugEnabled()) LOG.debug("createLikesService()");
    return new LikesEventHandler(userRepo, ownerRepository);
  }

  @Bean
  public CandidateService createCandidateService() {
    if (LOG.isDebugEnabled()) LOG.debug("createCandidateService()");
    return new CandidateEventHandler(candidateRepository, userRepo, positionRepository, electionRepo, ticketRepository);
  }

  @Bean
  public TicketService createTicketService() {
    if (LOG.isDebugEnabled()) LOG.debug("createTicketService()");
    return new TicketEventHandler(ticketRepository, electionRepo, userRepo, candidateRepository);
  }

  @Bean
  public TaskService createTaskService() {
    if (LOG.isDebugEnabled()) LOG.debug("createTaskService()");
    return new TaskEventHandler(taskRepository, userRepo);
  }

  @Bean
  public BadgeService createBadgeService() {
    if (LOG.isDebugEnabled()) LOG.debug("createBadgeService()");
    return new BadgeEventHandler(badgeRepository, userRepo);
  }

  @Bean
  public VotingLocationService createVotingLocationService() {
    if (LOG.isDebugEnabled()) LOG.debug("createVotingLocationService()");
    return new VotingLocationEventHandler(votingLocationRepository, electionRepo, ownerRepository);
  }

  @Bean
  public NotificationService createNotificationService() {
    if (LOG.isDebugEnabled()) LOG.debug("createNotificationService()");
    return new NotificationEventHandler(notificationRepository, userRepo, contactRequestRepository);
  }

  @Bean
  public CommentService createCommentService() {
    if (LOG.isDebugEnabled()) LOG.debug("createCommentService()");
    return new CommentEventHandler(userRepo, commentRepository, ownerRepository);
  }

  @Bean
  public AspectService createAspectService() {
    return new AspectService();
  }
}
