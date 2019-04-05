/**
 *
 */
package com.eulersbridge.iEngage.config;

import java.util.Arrays;

import com.eulersbridge.iEngage.core.services.interfacePack.UserService;
import com.eulersbridge.iEngage.rest.controller.ControllerConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.context.annotation.Bean;

/**
 * @author Greg Newitt
 */
//@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  private static Logger LOG = LoggerFactory.getLogger(SecurityConfig.class);

  private final UserService userService;

  private final PasswordEncoder passwordEncoder;

  @Autowired
  public SecurityConfig(UserService userService, PasswordEncoder passwordEncoder) {
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    if (LOG.isDebugEnabled()) LOG.debug("configure()");
    DaoAuthenticationProvider authProv = new DaoAuthenticationProvider();
    authProv.setUserDetailsService(userService);
    authProv.setPasswordEncoder(passwordEncoder);
//    Neo4jAuthenticationProvider authProv = new Neo4jAuthenticationProvider(userService);
    auth.authenticationProvider(authProv);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
//    AppBasicAuthenticationEntryPoint entryPoint = new AppBasicAuthenticationEntryPoint();
//    AppBasicAuthenticationSuccessHandler successHandler = new AppBasicAuthenticationSuccessHandler();
    http.authorizeRequests()
      .antMatchers(ControllerConstants.API_PREFIX + ControllerConstants.GENERAL_INFO_LABEL+"/**").permitAll()
      .antMatchers(ControllerConstants.API_PREFIX + ControllerConstants.SIGNUP_LABEL).permitAll()
      .antMatchers(ControllerConstants.API_PREFIX + ControllerConstants.EMAIL_VERIFICATION_LABEL + "/**").permitAll()
      .antMatchers(ControllerConstants.API_PREFIX + ControllerConstants.REQUEST_RESET_PWD + "/**").permitAll()
      .antMatchers(ControllerConstants.API_PREFIX + ControllerConstants.RESET_PWD + "/**").permitAll()

      .antMatchers(ControllerConstants.API_PREFIX + "/displayParams/**").permitAll()
//      .antMatchers(ControllerConstants.DBINTERFACE_PREFIX + ControllerConstants.API_PREFIX + ControllerConstants.GENERAL_INFO_LABEL).permitAll()
//      .antMatchers(ControllerConstants.DBINTERFACE_PREFIX + ControllerConstants.API_PREFIX + ControllerConstants.SIGNUP_LABEL).permitAll()
//      .antMatchers(ControllerConstants.DBINTERFACE_PREFIX + ControllerConstants.API_PREFIX + ControllerConstants.EMAIL_VERIFICATION_LABEL + "/**").permitAll()
      .anyRequest().fullyAuthenticated();

    http.httpBasic();
//      .formLogin().permitAll()//.successHandler(successHandler)//.loginPage(loginPage)
//        .and()
//      .logout().permitAll();

    http
      .cors()
      .and()
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and()
//      .addFilterAfter(digestFilter(), BasicAuthenticationFilter.class)
//        .httpBasic()
//        .authenticationEntryPoint(entryPoint)
//        .and()
//      .exceptionHandling()
//        .authenticationEntryPoint(digestEntryPoint())
//        .and()
      //TODO reenable CSRF security??
      .csrf().disable();
  }


  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("*"));
    configuration.setAllowCredentials(true);
    configuration.setAllowedHeaders(Arrays.asList("Access-Control-Allow-Headers","Access-Control-Allow-Origin","Access-Control-Request-Method", "Access-Control-Request-Headers","Origin","Cache-Control", "Content-Type", "Authorization"));
    configuration.setAllowedMethods(Arrays.asList("DELETE", "GET", "OPTIONS", "POST", "PATCH", "PUT"));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
//  @Bean
//  public DigestAuthenticationFilter digestFilter() {
//    if (LOG.isDebugEnabled()) LOG.debug("digestFilter()");
//    DigestAuthenticationFilter digestFilter = new DigestAuthenticationFilter();
//    digestFilter.setAuthenticationEntryPoint(digestEntryPoint);
//    digestFilter.setUserDetailsService(userService);
//    return digestFilter;
//  }
//
//  @Bean
//  public DigestAuthenticationEntryPoint digestEntryPoint() {
//    if (LOG.isDebugEnabled()) LOG.debug("digestEntryPoint()");
//    DigestAuthenticationEntryPoint digestEntryPoint = new DigestAuthenticationEntryPoint();
//    digestEntryPoint.setRealmName(SecurityConstants.REALM_NAME);
//    digestEntryPoint.setKey(SecurityConstants.DIGEST_KEY);
//    digestEntryPoint.setNonceValiditySeconds(SecurityConstants.NonceValiditySeconds);
//    return digestEntryPoint;
//  }
//
//  @Bean
//  public MethodSecurityExpressionHandler expressionHandler() {
//    DefaultMethodSecurityExpressionHandler bean = new DefaultMethodSecurityExpressionHandler();
//    bean.setPermissionEvaluator(permissionEvaluator);
//    return bean;
//  }

}
