package com.eulersbridge.iEngage.email;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.MimeMessagePreparator;

public class Email implements Serializable 
{
	private final String emailAddress;
	private final String recipientName;
	private String subject;
	private String from;
	private String body;
    private static Logger LOG = LoggerFactory.getLogger(Email.class);
    
    private MimeMessagePreparator preparator;
    
    public Email(String emailAddress,String recipientName) 
    {
    	if (LOG.isDebugEnabled()) LOG.debug("Constructor("+emailAddress+","+recipientName+")");
        this.emailAddress = emailAddress;
        this.recipientName = recipientName;
    }
    
    public String getEmailAddress() 
    {
        return emailAddress;
    }

	public String getRecipientName() {
		return recipientName;
	}
	
	public MimeMessagePreparator getPreparator()
	{
		return preparator;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}


}
