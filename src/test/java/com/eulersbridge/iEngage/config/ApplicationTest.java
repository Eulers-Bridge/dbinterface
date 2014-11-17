package com.eulersbridge.iEngage.config;

import java.io.IOException;

import javax.annotation.Resource;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;
import org.neo4j.test.TestGraphDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.PropertyResolver;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.support.MappingInfrastructureFactoryBean;
import org.springframework.data.neo4j.rest.SpringRestGraphDatabase;
import org.springframework.security.access.PermissionEvaluator;

import com.eulersbridge.iEngage.core.domain.Login;
import com.eulersbridge.iEngage.core.services.UserEventHandler;
import com.eulersbridge.iEngage.core.services.UserService;
import com.eulersbridge.iEngage.database.repository.CountryRepository;
import com.eulersbridge.iEngage.database.repository.ElectionRepository;
import com.eulersbridge.iEngage.database.repository.EventRepository;
import com.eulersbridge.iEngage.database.repository.ForumQuestionRepository;
import com.eulersbridge.iEngage.database.repository.InstitutionRepository;
import com.eulersbridge.iEngage.database.repository.NewsArticleRepository;
import com.eulersbridge.iEngage.database.repository.NewsFeedRepository;
import com.eulersbridge.iEngage.database.repository.PersonalityRepository;
import com.eulersbridge.iEngage.database.repository.PollRepository;
import com.eulersbridge.iEngage.database.repository.PositionRepository;
import com.eulersbridge.iEngage.database.repository.UserRepository;
import com.eulersbridge.iEngage.database.repository.VerificationTokenRepository;
import com.eulersbridge.iEngage.security.UserPermissionEvaluator;

@PropertySource("classpath:application.properties")
@Configuration
@ComponentScan
@EnableNeo4jRepositories(basePackages={"com.eulersbridge.iEngage.database.repository"})
@EnableAutoConfiguration
public class ApplicationTest extends Neo4jConfiguration
{
	@Resource
	private PropertyResolver propResolver;
	
    private static Logger LOG = LoggerFactory.getLogger(ApplicationTest.class);

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
    ElectionRepository eleRepo;
    @Autowired
    PersonalityRepository personRepo;
    @Autowired
    PollRepository pollRepository;
    @Autowired
    EventRepository eventRepository;
    @Autowired
    ForumQuestionRepository forumQuestionRepository;
    @Autowired
    PositionRepository positionRepository;


	public static void main(String[] args) 
    {
    	if (LOG.isDebugEnabled()) LOG.debug("main()");
        SpringApplication app=new SpringApplication(ApplicationTest.class);
        app.setShowBanner(false);
    	app.run(args);
//    	SpringApplication.run(Application.class, args);
    }
    
    ApplicationTest()
    {
    	setBasePackage("com.eulersbridge.iEngage.database.domain");
    }

    @Bean(destroyMethod = "shutdown")
    public GraphDatabaseService graphDatabaseService() throws IOException
 	{
    	String url=propResolver.getRequiredProperty("neo4j.server.url");
    	if (LOG.isDebugEnabled()) LOG.debug("url = "+url);
    	GraphDatabaseService db = new TestGraphDatabaseFactory()
		.newImpermanentDatabaseBuilder()
		.setConfig(GraphDatabaseSettings.nodestore_propertystore_mapped_memory_size, "10M")
		.setConfig(GraphDatabaseSettings.string_block_size, "60")
		.setConfig(GraphDatabaseSettings.array_block_size, "300")
		.newGraphDatabase();
    	return db;
    }

	@Override
	    public MappingInfrastructureFactoryBean mappingInfrastructure() throws Exception {
	        MappingInfrastructureFactoryBean mapping = super.mappingInfrastructure();
	        mapping.afterPropertiesSet();
	        return mapping;
	    }
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() 
	{
	   return new PropertySourcesPlaceholderConfigurer();
	}
	
	@Bean
	public PermissionEvaluator permissionEvaluator()
	{
		UserPermissionEvaluator bean= new UserPermissionEvaluator();
		return bean;
		
	}
	
	@Bean
	public UserService createUserService() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("createUserService()");
	    return new UserEventHandler(userRepo,personRepo,instRepo,tokenRepo);
	}
*/	
    @Bean
    public GraphDatabaseService graphDatabaseService()
    {
    GraphDatabaseService db = new TestGraphDatabaseFactory()
    .newImpermanentDatabaseBuilder()
    .setConfig( GraphDatabaseSettings.nodestore_mapped_memory_size, "10M" )
    .setConfig( GraphDatabaseSettings.string_block_size, "60" )
    .setConfig( GraphDatabaseSettings.array_block_size, "300" )
    .newGraphDatabase();
    return db;
    }
    

}
