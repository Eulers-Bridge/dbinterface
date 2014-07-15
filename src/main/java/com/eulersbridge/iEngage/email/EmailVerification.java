package com.eulersbridge.iEngage.email;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.domain.VerificationToken;

public class EmailVerification extends Email implements Serializable 
{

    private final String token;
    private final String tokenType;

    private static Logger LOG = LoggerFactory.getLogger(EmailVerification.class);

    public EmailVerification(User user, VerificationToken token) 
    {
    	super(user.getEmail(),user.getFirstName() + " " + user.getLastName());
        this.token = token.getToken();
        this.tokenType = token.getTokenType();
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
    
    
    MimeMessagePreparator preparator = new MimeMessagePreparator() 
    {
        public void prepare(MimeMessage mimeMessage) throws Exception 
        {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_RELATED, "UTF-8");
            message.setTo(new InternetAddress(getEmailAddress()));
            String body=null;
            message.setReplyTo(new InternetAddress("info@eulersbridge.com"));
            message.setFrom(new InternetAddress("info@eulersbridge.com"));
            message.setSubject("Account Verification");

            body="This is a test email.";
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

}
