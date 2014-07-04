/**
 * 
 */
package com.eulersbridge.iEngage.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author Greg Newitt
 *
 */
//@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter 
{
    private static Logger LOG = LoggerFactory.getLogger(SecurityConfig.class);
    
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("configure()");
	    auth.inMemoryAuthentication()
        .withUser("gnewitt").password("test").roles("USER");
	}
	
/*	@Override
	public void configure(WebSecurity webSec) throws Exception
	{
		webSec.
	}
*/
	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
	    http.authorizeRequests()
        	.antMatchers("/api/general-info").permitAll()
        	.antMatchers("/api/signUp/*").permitAll()
        	.antMatchers("/api/displayParams/**").permitAll()
        	.antMatchers("/**").hasRole("USER")
        .and()
        	.formLogin()
        		.permitAll()
        .and()
        	.logout()
        		.permitAll()
        .and().csrf().disable();
//TODO reenable CSRF security
/*		String loginPage="/general-info";
		http.authorizeRequests().antMatchers("/*").authenticated().and()
		.anonymous().
		http.authorizeRequests().anyRequest().fullyAuthenticated().and().formLogin().loginPage(loginPage).permitAll();
*/	}
	
}
