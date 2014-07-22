package com.eulersbridge.iEngage.email;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.exception.VelocityException;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineFactory;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;

import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.domain.VerificationToken;

public class EmailVerification extends Email implements Serializable 
{

    private final String token;
    private final String tokenType;
//    private final String velocityModel= "classpath:**/validateEmailBody.vm";
    private final String velocityModel= "classpath:templates/validateEmailBody.vm";
	@Autowired
    private VelocityEngine velocityEngine;

    private static Logger LOG = LoggerFactory.getLogger(EmailVerification.class);

    public EmailVerification(User user, VerificationToken token) 
    {
    	super(user.getEmail(),user.getFirstName() + " " + user.getLastName(),"greg.newitt@eulersbridge.com","Email Verification Test");
        this.token = token.getToken();
        this.tokenType = token.getTokenType();
        VelocityEngineFactory vfact=new VelocityEngineFactory();
		Properties velocityProperties=new Properties();
		velocityProperties.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		velocityProperties.setProperty("class.resource.loader.class", ClasspathResourceLoader.class.getName());
        vfact.setVelocityProperties(velocityProperties);
        
        try {
			this.velocityEngine = vfact.createVelocityEngine();
			this.velocityEngine.init();
			Template t;
//			 t=this.velocityEngine.getTemplate("logback.xml");
//			 t=this.velocityEngine.getTemplate("templates/validateEmailBody.vm");
		} catch (VelocityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public String getEncodedToken() 
    {
        return new String(Base64.encodeBase64(token.getBytes()));
    }

    public String getToken() {
        return token;
    }

    public String getTokenType() 
    {
        return tokenType;
    }
    
    @Override
    public MimeMessagePreparator generatePreparator() throws MessagingException
    {
    	MimeMessagePreparator preparator = new MimeMessagePreparator() 
    	{
    		public void prepare(MimeMessage mimeMessage) throws Exception 
    		{
    			MimeMessageHelper message = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_RELATED, "UTF-8");
    			message.setTo(new InternetAddress(getRecipientEmailAddress()));
    			String body=null;
    			message.setReplyTo(new InternetAddress(getSenderEmailAddress()));
    			message.setFrom(new InternetAddress(getSenderEmailAddress()));
    			message.setSubject(getSubject());
    			final Map<String, Object> hTemplateVariables = new HashMap<String,Object>();

    			hTemplateVariables.put("email", this);
    			if (LOG.isDebugEnabled()) LOG.debug("Velocity engine :"+velocityEngine);
    			if (LOG.isDebugEnabled())
//    			body = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, velocityModel,"UTF-8", hTemplateVariables);
    			body="Dear "+getRecipientName()+", your token is "+getToken()+" your url is \nhttp://eulersbridge.com:8080/api/emailVerification/"+getRecipientEmailAddress()+"/"+getEncodedToken()+" Thankyou.";
    			if(LOG.isDebugEnabled()) LOG.debug("body={}", body);
    			message.setText(body, true);

           /*for(String resourceIdentifier: hTemplateVariables.keySet()) {
          	 //Inline resources are added to the mime message using the specified Content-ID. 
          	  //The order in which you are adding the text and the resource are very important. 
          	  //Be sure to first add the text and after that the resources. 
          	  //If you are doing it the other way around, it won't work!
        	   addInlineResource(message, hTemplateVariables.get(resourceIdentifier).toString(), resourceIdentifier);
           }*/
                 
    		}
    	};
    	
    	return preparator;
    }
    public String toString()
    {
    	return super.toString()+" token: "+token.toString();
    }

}
