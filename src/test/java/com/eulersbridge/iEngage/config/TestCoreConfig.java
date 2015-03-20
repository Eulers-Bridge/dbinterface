package com.eulersbridge.iEngage.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.eulersbridge.iEngage.core.services.CountryEventHandler;
import com.eulersbridge.iEngage.core.services.CountryService;
import com.eulersbridge.iEngage.core.services.ElectionEventHandler;
import com.eulersbridge.iEngage.core.services.ElectionService;
import com.eulersbridge.iEngage.core.services.EventEventHandler;
import com.eulersbridge.iEngage.core.services.EventService;
import com.eulersbridge.iEngage.core.services.ForumQuestionEventHandler;
import com.eulersbridge.iEngage.core.services.ForumQuestionService;
import com.eulersbridge.iEngage.core.services.InstitutionEventHandler;
import com.eulersbridge.iEngage.core.services.InstitutionService;
import com.eulersbridge.iEngage.core.services.NewsEventHandler;
import com.eulersbridge.iEngage.core.services.NewsService;
import com.eulersbridge.iEngage.core.services.PollEventHandler;
import com.eulersbridge.iEngage.core.services.PollService;
import com.eulersbridge.iEngage.core.services.PositionEventHandler;
import com.eulersbridge.iEngage.core.services.PositionService;
import com.eulersbridge.iEngage.core.services.UserEventHandler;
import com.eulersbridge.iEngage.core.services.UserService;
import com.eulersbridge.iEngage.database.repository.CountryRepository;
import com.eulersbridge.iEngage.database.repository.ElectionRepository;
import com.eulersbridge.iEngage.database.repository.EventRepository;
import com.eulersbridge.iEngage.database.repository.ForumQuestionRepository;
import com.eulersbridge.iEngage.database.repository.InstitutionRepository;
import com.eulersbridge.iEngage.database.repository.NewsArticleRepository;
import com.eulersbridge.iEngage.database.repository.NewsFeedRepository;
import com.eulersbridge.iEngage.database.repository.OwnerRepository;
import com.eulersbridge.iEngage.database.repository.PersonalityRepository;
import com.eulersbridge.iEngage.database.repository.PollAnswerRepository;
import com.eulersbridge.iEngage.database.repository.PollRepository;
import com.eulersbridge.iEngage.database.repository.PositionRepository;
import com.eulersbridge.iEngage.database.repository.UserRepository;
import com.eulersbridge.iEngage.database.repository.VerificationTokenRepository;
import com.eulersbridge.iEngage.rest.domain.CountriesFactory;
import com.eulersbridge.iEngage.rest.domain.stubCountryFactory;

@Configuration
public class TestCoreConfig 
{
	/*	@Autowired
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
    ElectionRepository electionRepo;
    @Autowired
    OwnerRepository ownerRepo;
    @Autowired
    PersonalityRepository personRepo;
    @Autowired
    PollRepository pollRepository;
    @Autowired
    PollAnswerRepository pollAnswerRepository;
    @Autowired
    EventRepository eventRepository;
    @Autowired
    ForumQuestionRepository forumQuestionRepository;
    @Autowired
    PositionRepository candidateRepository;

    private static Logger LOG = LoggerFactory.getLogger(CoreConfig.class);

	public TestCoreConfig()
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
        return new ElectionEventHandler(electionRepo,instRepo);
    }

    @Bean
    public PollService createPollService()
    {
        if (LOG.isDebugEnabled()) LOG.debug("createPollService()");
        return new PollEventHandler(pollRepository,pollAnswerRepository,ownerRepo);
    }

    @Bean
    public EventService createEventService()
    {
        if (LOG.isDebugEnabled()) LOG.debug("createEventService()");
        return new EventEventHandler(eventRepository,instRepo);
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
        return new PositionEventHandler(candidateRepository);
    }
    */
}
