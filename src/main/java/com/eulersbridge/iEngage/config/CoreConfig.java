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
	CountryRepository countryRepo;
	@Autowired
	VerificationTokenRepository tokenRepo;
	@Autowired
	NewsArticleRepository newsRepo;
	@Autowired
	NewsFeedRepository syRepo;
    @Autowired
    ElectionRepository eleRepo;
    @Autowired
    PersonalityRepository personRepo;
    @Autowired
    PollRepository pollRepository;
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
        return new ElectionEventHandler(eleRepo,instRepo);
    }

    @Bean
    public PollService createPollService()
    {
        if (LOG.isDebugEnabled()) LOG.debug("createPollService()");
        return new PollEventHandler(pollRepository,ownerRepository);
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
        return new PositionEventHandler(positionRepository);
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
        return new LikesEventHandler(userRepo);
    }

    @Bean
    public CandidateService createCandidateService()
    {
        if (LOG.isDebugEnabled()) LOG.debug("createCandidateService()");
        return new CandidateEventHandler(candidateRepository);
    }
}
