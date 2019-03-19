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

  @Value("${spring.mail.host}")
  private String host;

  @Value("${spring.mail.properties.mail.smtp.port}")
  private Integer port;

  @Value("${spring.mail.username}")
  private String username;

  @Value("${spring.mail.password}")
  private String password;

  @Bean
  public JavaMailSender mailSender() {
    JavaMailSenderImpl sender = new JavaMailSenderImpl();
    sender.setHost(host);
    sender.setPort(port);
    sender.setUsername(username);
    sender.setPassword(password);
    Properties javaMailProperties = new Properties();
    javaMailProperties.put("mail.transport.protocol", "smtp");
    javaMailProperties.put("mail.smtp.auth", "true");
    javaMailProperties.put("mail.smtp.starttls.enable", "true");
    javaMailProperties.put("spring.mail.properties.mail.smtp.starttls.required", "true");
    javaMailProperties.put("mail.debug", "true");
    sender.setJavaMailProperties(javaMailProperties);
    return sender;

  }

  // TODO: Witch to FreeMarker as Spring in favor of
  @Bean
  public VelocityEngine velocityEngine(ServletContext servletContext) throws Exception {
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
//    velocityEngine.getTemplate("/templates/sign_up_in_progress.vm");
    return velocityEngine;

  }

}