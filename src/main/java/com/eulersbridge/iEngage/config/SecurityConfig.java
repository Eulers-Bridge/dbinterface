/**
 * 
 */
package com.eulersbridge.iEngage.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.eulersbridge.iEngage.security.AppBasicAuthenticationEntryPoint;
import com.eulersbridge.iEngage.security.AppBasicAuthenticationSuccessHandler;

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
	    AppBasicAuthenticationEntryPoint entryPoint=new AppBasicAuthenticationEntryPoint();
		AppBasicAuthenticationSuccessHandler successHandler=new AppBasicAuthenticationSuccessHandler();
		String loginPage="/general-info";
		http.authorizeRequests()
        	.antMatchers("/api/general-info").permitAll()
        	.antMatchers("/api/signUp").permitAll()
        	.antMatchers("/api/displayParams/**").permitAll()
//        	.antMatchers("/api/emailVerification/**").permitAll()
//        	.antMatchers("/api/countrys").permitAll()
//        	.antMatchers("/api/institutions").permitAll()
//        	.antMatchers("/api/**").permitAll()
        	.antMatchers("/dbInterface/api/**").permitAll()
        	.antMatchers("/**").hasRole("USER").anyRequest().authenticated()
        .and()
//        	.httpBasic()
        	.httpBasic().authenticationEntryPoint(entryPoint)
        .and()
        	.formLogin().successHandler(successHandler)//.loginPage(loginPage)
        		.permitAll()
        .and()
        	.logout()
        		.permitAll()
        .and().csrf().disable();
//TODO reenable CSRF security
/*		
		http.authorizeRequests().antMatchers("/*").authenticated().and()
		.anonymous().
		http.authorizeRequests().anyRequest().fullyAuthenticated().and().formLogin().loginPage(loginPage).permitAll();
*/	}
	
}
