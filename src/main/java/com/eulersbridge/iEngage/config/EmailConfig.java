package com.eulersbridge.iEngage.config;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.apache.velocity.tools.view.WebappResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.servlet.ServletContext;
import java.util.Properties;

//import org.springframework.core.io.Resource;
@Configuration
public class EmailConfig {
  private static Logger LOG = LoggerFactory.getLogger(EmailConfig.class);

  @Value("${email.host}")
  private String host;

  @Value("${email.port}")
  private Integer port;

  @Value("${email.username}")
  private String username;

  @Value("${email.password}")
  private String password;

  @Bean
  public JavaMailSender mailSender() {
    if (LOG.isDebugEnabled()) LOG.debug("mailSender()");
    JavaMailSenderImpl sender = new JavaMailSenderImpl();
    sender.setHost(host);
    sender.setPort(port);
    sender.setUsername(username);
    sender.setPassword(password);
    Properties smtpProperties = getSmtpMailProperties();
    sender.setJavaMailProperties(smtpProperties);
    if (LOG.isDebugEnabled()) LOG.debug("host = " + host + " port = " + port);
    if (LOG.isDebugEnabled()) LOG.debug("properties = " + smtpProperties);
    if (LOG.isDebugEnabled()) LOG.debug("Sender = " + sender);
    return sender;

  }

  // TODO: Witch to FreeMarker as Spring in favor of
  @Bean
  public VelocityEngine velocityEngine(ServletContext servletContext) {
    if (LOG.isDebugEnabled()) LOG.debug("velocityEngine()");
    
    Properties velocityProperties = new Properties();
    velocityProperties.setProperty(RuntimeConstants.RESOURCE_LOADER, "class");
    velocityProperties.setProperty("class.resource.loader.class", ClasspathResourceLoader.class.getName());
    VelocityEngine velocityEngine = new VelocityEngine(velocityProperties);
    velocityEngine.setApplicationAttribute("javax.servlet.ServletContext", servletContext);

    // Classpath Resource loader is used. templates can be loaded via its absolute path
    // (Note: java/ and resources/ are merged together as the root entry of class resource loader)
    // Ref: http://velocity.apache.org/engine/1.7/developer-guide.html#configuring-resource-loaders
    // Ref: https://stackoverflow.com/questions/9051413/unable-to-find-velocity-template-resources
    velocityEngine.getTemplate("/templates/sign_up_in_progress.vm");
    return velocityEngine;

  }

  private Properties getSmtpMailProperties() {
    Properties javaMailProperties = new Properties();
    javaMailProperties.setProperty("mail.transport.protocol", "smtp");
    javaMailProperties.setProperty("mail.smtp.auth", "true");
    javaMailProperties.setProperty("mail.smtp.starttls.enable", "true");
    javaMailProperties.setProperty("mail.debug", "true");
    return javaMailProperties;
  }
}