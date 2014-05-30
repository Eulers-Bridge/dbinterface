package com.eulersbridge.iEngage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import com.eulersbridge.iEngage.core.domain.Login;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableGlobalMethodSecurity
public class Application 
{
    public static void main(String[] args) 
    {
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

    
}
