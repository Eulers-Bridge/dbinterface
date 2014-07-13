package com.eulersbridge.iEngage.email;

import com.eulersbridge.iEngage.config.SecurityConfig;
import com.eulersbridge.iEngage.database.domain.Student;
import com.eulersbridge.iEngage.database.domain.VerificationToken;
import com.eulersbridge.iEngage.database.repository.VerificationTokenRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.validation.Validator;

public class EmailVerificationService  {
	
	private static final int EMAIL_EXPIRY_TIME_IN_MINS = 60 * 24 * 7; //seven days
	
	private VerificationTokenRepository tokenRepository;

    private EmailSender emailSender;

    public EmailVerificationService() {
        
    }

    @Autowired
    public EmailVerificationService(EmailSender emailSender)
    {
        this.tokenRepository = tokenRepository;
        this.emailSender = emailSender;
    }

    @Transactional
    public VerificationToken sendEmailVerificationToken(Student student) 
    {
        VerificationToken token = new VerificationToken(
        		VerificationToken.VerificationTokenType.emailVerification,
        		EMAIL_EXPIRY_TIME_IN_MINS);
        emailSender.sendVerificationEmail(new EmailVerification(student, token));
        return token;
    }
}