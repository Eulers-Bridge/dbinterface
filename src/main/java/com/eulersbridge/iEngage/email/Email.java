package com.eulersbridge.iEngage.email;

import java.io.Serializable;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

public class Email implements Serializable 
{
	private final String recipientEmailAddress;
	private final String recipientName;
	private final String senderEmailAddress;
	private final String subject;
    private static Logger LOG = LoggerFactory.getLogger(Email.class);
    
    public Email(String recipientEmailAddress,String recipientName,String senderEmailAddress, String subject) 
    {
    	if (LOG.isDebugEnabled()) LOG.debug("Constructor("+recipientEmailAddress+","+recipientName+","+senderEmailAddress+","+subject+")");
        this.recipientEmailAddress = recipientEmailAddress;
        this.recipientName = recipientName;
        this.senderEmailAddress = senderEmailAddress;
        this.subject = subject;
    }
    
    public String getRecipientEmailAddress() {
        return recipientEmailAddress;
    }

	protected String getRecipientName() {
		return recipientName;
	}
	
	public String getSenderEmailAddress() {
        return senderEmailAddress;
    }
	
	public String getSubject() {
        return subject;
    }
	
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

    			body="This is a test email.";
            
            if(LOG.isDebugEnabled()) LOG.debug("body={}", body);
            message.setText(body, true);    
    		}
    	};
	    	
		return preparator;
	}


}
