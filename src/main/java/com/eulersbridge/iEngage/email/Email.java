package com.eulersbridge.iEngage.email;

import java.io.Serializable;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
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
	protected final transient VelocityEngine velocityEngine;
    private static transient Logger LOG = LoggerFactory.getLogger(Email.class);
    
    public Email(VelocityEngine velocityEngine,String recipientEmailAddress,String recipientName,String senderEmailAddress, String subject) 
    {
    	if (LOG.isDebugEnabled()) LOG.debug("Constructor("+recipientEmailAddress+","+recipientName+","+senderEmailAddress+","+subject+")");
        this.velocityEngine=velocityEngine;
    	this.recipientEmailAddress = recipientEmailAddress;
        this.recipientName = recipientName;
        this.senderEmailAddress = senderEmailAddress;
        this.subject = subject;
    }
    
    public String getRecipientEmailAddress() {
        return recipientEmailAddress;
    }

	public String getRecipientName() {
		return recipientName;
	}
	
	public String getSenderEmailAddress() {
        return senderEmailAddress;
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

	public String getSubject() {
		return subject;
	}
	
	public String toString()
	{
		StringBuffer buff=new StringBuffer();
		buff.append("To:");
		buff.append(recipientEmailAddress);
		buff.append(" Recipient:");
		buff.append(recipientName);
		buff.append(" From:");
		buff.append(senderEmailAddress);
		buff.append(" Subject:");
		buff.append(subject);
		return buff.toString();
	}

}
