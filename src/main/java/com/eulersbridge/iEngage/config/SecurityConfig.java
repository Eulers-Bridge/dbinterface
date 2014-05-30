/**
 * 
 */
package com.eulersbridge.iEngage.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author Greg Newitt
 *
 */
//@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter 
{
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
	    auth.inMemoryAuthentication()
        .withUser("gnewitt").password("test").roles("USER");
	}

/*	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		String loginPage="/login";
		http.authorizeRequests().anyRequest().fullyAuthenticated().and().formLogin().loginPage(loginPage).permitAll();
	} */
}
