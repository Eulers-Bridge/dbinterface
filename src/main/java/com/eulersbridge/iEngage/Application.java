package com.eulersbridge.iEngage;

import org.neo4j.graphdb.GraphDatabaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.support.MappingInfrastructureFactoryBean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import com.eulersbridge.iEngage.core.domain.Login;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableGlobalMethodSecurity
public class Application extends Neo4jConfiguration
{
    private static Logger LOG = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) 
    {
    	if (LOG.isDebugEnabled()) LOG.debug("main()");
        SpringApplication app=new SpringApplication(Application.class);
        app.setShowBanner(false);
    	app.run(args);
//    	SpringApplication.run(Application.class, args);
    }

    @Bean
    public Login testLogin2()
    {    	
    	return new Login(0, "testlogin", "testlogin in had a test");
    }

 @Bean
    public GraphDatabaseService graphDatabaseService() 
 	{
	 	return new org.springframework.data.neo4j.rest.SpringRestGraphDatabase("http://ec2-54-79-49-160.ap-southeast-2.compute.amazonaws.com:7474/db/data");
    }

@Override
    public MappingInfrastructureFactoryBean mappingInfrastructure() throws Exception {
        MappingInfrastructureFactoryBean mapping = super.mappingInfrastructure();
        mapping.afterPropertiesSet();
        return mapping;
    }

}
