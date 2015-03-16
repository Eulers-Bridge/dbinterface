package com.eulersbridge.iEngage.config;

import com.eulersbridge.iEngage.core.services.*;
import com.eulersbridge.iEngage.database.repository.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.eulersbridge.iEngage.rest.domain.CountriesFactory;
import com.eulersbridge.iEngage.rest.domain.stubCountryFactory;

@Configuration
public class CoreConfig 
{
	@Autowired
	UserRepository userRepo;
	@Autowired
	InstitutionRepository instRepo;
	@Autowired
	ContactRequestRepository contactRequestRepository;
	@Autowired
	CountryRepository countryRepo;
	@Autowired
	VerificationTokenRepository tokenRepo;
	@Autowired
	NewsArticleRepository newsRepo;
	@Autowired
	NewsFeedRepository syRepo;
    @Autowired
    ElectionRepository electionRepo;
    @Autowired
    PersonalityRepository personRepo;
    @Autowired
    PollRepository pollRepository;
    @Autowired
    PollAnswerRepository pollAnswerRepository;
    @Autowired
    EventRepository eventRepository;
    @Autowired
    OwnerRepository ownerRepository;
    @Autowired
    PhotoRepository photoRepository;
    @Autowired
    PhotoAlbumRepository photoAlbumRepository;
    @Autowired
    ForumQuestionRepository forumQuestionRepository;
    @Autowired
    PositionRepository positionRepository;
    @Autowired
    ConfigurationRepository configurationRepository;
    @Autowired
    CandidateRepository candidateRepository;
    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    BadgeRepository badgeRepository;
    @Autowired
    VotingLocationRepository votingLocationRepository;

    private static Logger LOG = LoggerFactory.getLogger(CoreConfig.class);

	public CoreConfig()
	{
		if (LOG.isDebugEnabled()) LOG.debug("CoreConfig()");
	}

	@Bean
	public UserService createUserService() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("createUserService()");
	    return new UserEventHandler(userRepo,personRepo,instRepo,tokenRepo);
	}
	
	@Bean
	public InstitutionService createInstitutionService()
	{
		if (LOG.isDebugEnabled()) LOG.debug("createInstitutionService()");
		return new InstitutionEventHandler(instRepo,countryRepo,syRepo);
	}

	@Bean
	public ContactRequestService createContactRequestService()
	{
		if (LOG.isDebugEnabled()) LOG.debug("createContactRequestService()");
		return new ContactRequestEventHandler(contactRequestRepository,userRepo);
	}

	@Bean
	public CountryService createCountryService()
	{
		if (LOG.isDebugEnabled()) LOG.debug("createCountryService()");
		return new CountryEventHandler(countryRepo);
	}

	@Bean
	public NewsService createNewsService()
	{
		if (LOG.isDebugEnabled()) LOG.debug("createNewsService()");
		return new NewsEventHandler(newsRepo,userRepo, instRepo);
	}
	
	@Bean
	public CountriesFactory createCountryFactory()
	{
		if (LOG.isDebugEnabled()) LOG.debug("createCountryFactory()");
		return new stubCountryFactory();
		
	}

    @Bean
    public ElectionService createElectionService()
    {
        if (LOG.isDebugEnabled()) LOG.debug("createElectionService()");
        return new ElectionEventHandler(electionRepo,instRepo);
    }

    @Bean
    public PollService createPollService()
    {
        if (LOG.isDebugEnabled()) LOG.debug("createPollService()");
        return new PollEventHandler(pollRepository,pollAnswerRepository,ownerRepository);
    }

    @Bean
    public EventService createEventService()
    {
        if (LOG.isDebugEnabled()) LOG.debug("createEventService()");
        return new EventEventHandler(eventRepository,instRepo);
    }

    @Bean
    public PhotoService createPhotoService()
    {
        if (LOG.isDebugEnabled()) LOG.debug("createPhotoService()");
        return new PhotoEventHandler(photoRepository,photoAlbumRepository,ownerRepository);
    }

    @Bean
    public ForumQuestionService createForumQuestionService()
    {
        if (LOG.isDebugEnabled()) LOG.debug("createForumQuestionService()");
        return new ForumQuestionEventHandler(forumQuestionRepository);
    }

    @Bean
    public PositionService createPositionService()
    {
        if (LOG.isDebugEnabled()) LOG.debug("createPositionService()");
        return new PositionEventHandler(positionRepository,electionRepo);
    }

    @Bean
    public ConfigurationService createConfigurationService()
    {
        if (LOG.isDebugEnabled()) LOG.debug("createConfigurationService()");
        return new ConfigurationEventHandler(configurationRepository);
    }

    @Bean
    public LikesService createLikesService()
    {
        if (LOG.isDebugEnabled()) LOG.debug("createLikesService()");
        return new LikesEventHandler(userRepo,ownerRepository);
    }

    @Bean
    public CandidateService createCandidateService()
    {
        if (LOG.isDebugEnabled()) LOG.debug("createCandidateService()");
        return new CandidateEventHandler(candidateRepository,userRepo,positionRepository,electionRepo, ticketRepository);
    }

    @Bean
    public TicketService createTicketService()
    {
        if (LOG.isDebugEnabled()) LOG.debug("createTicketService()");
        return new TicketEventHandler(ticketRepository,electionRepo,userRepo);
    }

    @Bean
    public TaskService createTaskService()
    {
        if (LOG.isDebugEnabled()) LOG.debug("createTaskService()");
        return new TaskEventHandler(taskRepository,userRepo);
    }

    @Bean
    public BadgeService createBadgeService()
    {
        if (LOG.isDebugEnabled()) LOG.debug("createBadgeService()");
        return new BadgeEventHandler(badgeRepository);
    }

    @Bean
    public VotingLocationService createVotingLocationService()
    {
        if (LOG.isDebugEnabled()) LOG.debug("createVotingLocationService()");
        return new VotingLocationEventHandler(votingLocationRepository,electionRepo,ownerRepository);
    }
}
