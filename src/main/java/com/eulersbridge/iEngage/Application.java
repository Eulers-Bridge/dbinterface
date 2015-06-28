package com.eulersbridge.iEngage;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.neo4j.graphdb.GraphDatabaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterRegistry;
import org.springframework.core.env.PropertyResolver;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.support.MappingInfrastructureFactoryBean;
import org.springframework.data.neo4j.rest.SpringRestGraphDatabase;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.access.PermissionEvaluator;

import com.eulersbridge.iEngage.core.domain.Login;
import com.eulersbridge.iEngage.database.domain.converters.CandidateToOwnerConverter;
import com.eulersbridge.iEngage.database.domain.converters.EventToOwnerConverter;
import com.eulersbridge.iEngage.database.domain.converters.NewsArticleToOwnerConverter;
import com.eulersbridge.iEngage.database.domain.converters.PhotoAlbumToOwnerConverter;
import com.eulersbridge.iEngage.database.domain.converters.TicketToOwnerConverter;
import com.eulersbridge.iEngage.database.domain.converters.UserToOwnerConverter;
import com.eulersbridge.iEngage.security.UserPermissionEvaluator;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.module.SimpleDeserializers;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.module.SimpleSerializers;
import com.google.common.collect.Sets;

@PropertySource("classpath:application.properties")
@Configuration
@ComponentScan
@EnableNeo4jRepositories(basePackages={"com.eulersbridge.iEngage.database.repository"})
@EnableAutoConfiguration
@EnableAspectJAutoProxy
public class Application extends Neo4jConfiguration
{
	@Resource
	private PropertyResolver propResolver;
	
    private static Logger LOG = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) 
    {
    	if (LOG.isDebugEnabled()) LOG.debug("main()");
        SpringApplication app=new SpringApplication(Application.class);
        app.setShowBanner(false);
    	app.run(args);
//    	SpringApplication.run(Application.class, args);
    }
    
    Application()
    {
    	setBasePackage("com.eulersbridge.iEngage.database.domain");
    }

    @Bean
    public Login testLogin2()
    {    	
    	return new Login(0, "testlogin", "testlogin in had a test");
    }
    
    @Bean(destroyMethod = "shutdown")
    public GraphDatabaseService graphDatabaseService() throws IOException
 	{
    	String url=propResolver.getRequiredProperty("neo4j.server.url");
    	if (LOG.isDebugEnabled()) LOG.debug("url = "+url);
	 	return new SpringRestGraphDatabase(url);
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
	
	@Bean protected ConversionService neo4jConversionService() throws Exception
	{
		ConversionService conversionService = super.neo4jConversionService();
		ConverterRegistry registry = (ConverterRegistry) conversionService;
		registry.addConverter(new EventToOwnerConverter());
		registry.addConverter(new NewsArticleToOwnerConverter());
		registry.addConverter(new PhotoAlbumToOwnerConverter());
		registry.addConverter(new UserToOwnerConverter());
		registry.addConverter(new CandidateToOwnerConverter());
		registry.addConverter(new TicketToOwnerConverter());
		
		return conversionService;
	}

/*	@Primary
	@Bean
	public ObjectMapper objectMapper()
	{
		ObjectMapper om=new ObjectMapper();
	    om.registerModule(new SimpleModule("CustomSerializerModule")
	    {
	        @Override public void setupModule(SetupContext context)
	        {
	            context.addSerializers(serializers);
	        }
	    });
		return om;
	}
*/	
/*	@Bean
	public Module  module1()
	{
	    final SimpleSerializers serializers = new SimpleSerializers();
	    final SimpleDeserializers deserializers = new SimpleDeserializers();
	    deserializers.addDeserializer(Notification.class, new NotificationDeserializer());
	    serializers.addSerializer(Notification.class, new NotificationSerializer());
		Module mod=new SimpleModule("CustomSerializerModule")
		{
	        @Override public void setupModule(SetupContext context)
	        {
				context.addDeserializers(deserializers);
	            context.addSerializers(serializers);
	        }
			
		};
		return mod;
	}
*/	
/*	@Bean 
	public MappingJackson2HttpMessageConverter messageConverter()
	{
		MappingJackson2HttpMessageConverter converter=new MappingJackson2HttpMessageConverter();
		return converter;
	}
	*/
	
}
