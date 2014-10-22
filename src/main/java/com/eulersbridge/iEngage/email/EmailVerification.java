package com.eulersbridge.iEngage.email;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final VerificationToken token;
    private final String tokenType;
    private final String resourceName;
    
    private static Logger LOG = LoggerFactory.getLogger(EmailVerification.class);

    public EmailVerification(VelocityEngine velocityEngine, User user, VerificationToken token) 
    {
    	super(velocityEngine,user.getEmail(),user.getGivenName() + " " + user.getFamilyName(),"greg.newitt@eulersbridge.com","Email Verification Test");
		resourceName=EmailConstants.EmailVerificationTemplate;
        this.token = token;
        this.tokenType = token.getTokenType();
        if (LOG.isDebugEnabled()) LOG.debug("this.velocityEngine = "+this.velocityEngine);
		if (this.velocityEngine!=null)
			this.velocityEngine.init();
    }

    public String getEncodedToken() 
    {
        return token.getEncodedTokenString();
    }

    public VerificationToken getToken() {
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

//    			hTemplateVariables.put("email", this);
    			hTemplateVariables.put("recipientName", getRecipientName());
    			hTemplateVariables.put("emailAddress",getRecipientEmailAddress());
    			hTemplateVariables.put("verificationToken",getToken());
    			
    			if (LOG.isDebugEnabled()) LOG.debug("Velocity engine :"+velocityEngine);
    			if (LOG.isDebugEnabled())
    			body = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, resourceName,"UTF-8", hTemplateVariables);
//    			body="Dear "+getRecipientName()+", your token is "+getToken()+" your url is \nhttp://eulersbridge.com:8080/dbInterface/api/emailVerification/"+getRecipientEmailAddress()+"/"+getToken()+" Thankyou.";
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
