package com.eulersbridge.iEngage.config;

import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Yikai Gong
 */
@Configuration
public class AwsSNSConfig {

  @Bean
  public AmazonSNS sns(){
    AmazonSNS sns = AmazonSNSClientBuilder.standard()
      .withRegion(Regions.AP_SOUTHEAST_2)
      .withCredentials(new ClasspathPropertiesFileCredentialsProvider("AwsCredentials.properties"))
      .build();
    return sns;
  }

}
