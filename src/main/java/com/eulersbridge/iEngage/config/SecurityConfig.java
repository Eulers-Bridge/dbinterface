/**
 * 
 */
package com.eulersbridge.iEngage.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;

import com.eulersbridge.iEngage.core.services.UserService;
import com.eulersbridge.iEngage.rest.controller.ControllerConstants;
import com.eulersbridge.iEngage.security.AppBasicAuthenticationEntryPoint;
import com.eulersbridge.iEngage.security.AppBasicAuthenticationSuccessHandler;
import com.eulersbridge.iEngage.security.SecurityConstants;

/**
 * @author Greg Newitt
 *
 */
//@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter 
{
    private static Logger LOG = LoggerFactory.getLogger(SecurityConfig.class);
    
    @Autowired
    UserService userService;
    
    @Autowired
    UserDetailsService userDetailsService;
       
    @Autowired
    DigestAuthenticationEntryPoint digestEntryPoint;
    
    @Autowired
    PermissionEvaluator permissionEvaluator;

    @Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		if (LOG.isDebugEnabled()) LOG.debug("configure()");
		DaoAuthenticationProvider authProv=new DaoAuthenticationProvider();
		authProv.setUserDetailsService(userDetailsService);
//		Neo4jAuthenticationProvider authProv=new Neo4jAuthenticationProvider(userService);
		auth.authenticationProvider(authProv);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
	    AppBasicAuthenticationEntryPoint entryPoint=new AppBasicAuthenticationEntryPoint();
		AppBasicAuthenticationSuccessHandler successHandler=new AppBasicAuthenticationSuccessHandler();
		http.authorizeRequests()
        	.antMatchers(ControllerConstants.API_PREFIX+ControllerConstants.GENERAL_INFO_LABEL).permitAll()
        	.antMatchers(ControllerConstants.API_PREFIX+ControllerConstants.SIGNUP_LABEL).permitAll()
        	.antMatchers(ControllerConstants.API_PREFIX+ControllerConstants.EMAIL_VERIFICATION_LABEL+"/**").permitAll()
        	.antMatchers(ControllerConstants.API_PREFIX+"/displayParams/**").permitAll()
        	.antMatchers(ControllerConstants.DBINTERFACE_PREFIX+ControllerConstants.API_PREFIX+ControllerConstants.GENERAL_INFO_LABEL).permitAll()
        	.antMatchers(ControllerConstants.DBINTERFACE_PREFIX+ControllerConstants.API_PREFIX+ControllerConstants.SIGNUP_LABEL).permitAll()
        	.antMatchers(ControllerConstants.DBINTERFACE_PREFIX+ControllerConstants.API_PREFIX+ControllerConstants.EMAIL_VERIFICATION_LABEL+"/**").permitAll()
        	.antMatchers("/**").hasRole("USER").anyRequest().fullyAuthenticated()
        .and()
        	.exceptionHandling().authenticationEntryPoint(digestEntryPoint())
        .and()
        	.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        	.addFilterAfter(digestFilter(), BasicAuthenticationFilter.class)
        	.httpBasic().authenticationEntryPoint(entryPoint)
        .and()
        	.formLogin().successHandler(successHandler)//.loginPage(loginPage)
        		.permitAll()
        .and()
        	.logout()
        		.permitAll()
 //TODO reenable CSRF security??
       .and().csrf().disable()
        ;
/*		
		http.authorizeRequests().antMatchers("/*").authenticated().and()
		.anonymous().
		http.authorizeRequests().anyRequest().fullyAuthenticated().and().formLogin().loginPage(loginPage).permitAll();
*/	}

	@Bean
	public DigestAuthenticationFilter digestFilter()
	{
		if (LOG.isDebugEnabled()) LOG.debug("digestFilter()");
		DigestAuthenticationFilter digestFilter=new DigestAuthenticationFilter();
		digestFilter.setAuthenticationEntryPoint(digestEntryPoint);
		digestFilter.setUserDetailsService(userDetailsService);
		return digestFilter;
	}
	
	@Bean
	public DigestAuthenticationEntryPoint digestEntryPoint()
	{
		if (LOG.isDebugEnabled()) LOG.debug("digestEntryPoint()");
		DigestAuthenticationEntryPoint digestEntryPoint = new DigestAuthenticationEntryPoint();
		digestEntryPoint.setRealmName(SecurityConstants.REALM_NAME);
		digestEntryPoint.setKey(SecurityConstants.DIGEST_KEY);
		digestEntryPoint.setNonceValiditySeconds(SecurityConstants.NonceValiditySeconds);
		return digestEntryPoint;
	}
	
	@Bean
	public MethodSecurityExpressionHandler expressionHandler() 
	{
		DefaultMethodSecurityExpressionHandler bean = new DefaultMethodSecurityExpressionHandler();
		bean.setPermissionEvaluator(permissionEvaluator);
		return bean;
	}

}
