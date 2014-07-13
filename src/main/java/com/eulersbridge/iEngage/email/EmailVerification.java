package com.eulersbridge.iEngage.email;

import java.io.Serializable;

import org.apache.tomcat.util.codec.binary.Base64;

import com.eulersbridge.iEngage.database.domain.Student;
import com.eulersbridge.iEngage.database.domain.VerificationToken;

public class EmailVerification implements Serializable {

	private final String emailAddress;
	private final String recipientName;
    private final String token;
    private final VerificationToken.VerificationTokenType tokenType;


    public EmailVerification(Student student, VerificationToken token) {
        this.emailAddress = student.getEmail();
        this.token = token.getToken();
        this.tokenType = token.getTokenType();
        this.recipientName = student.getFirstName() + " " + student.getLastName();
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getEncodedToken() {
        return new String(Base64.encodeBase64(token.getBytes()));
    }

    public String getToken() {
        return token;
    }

    public VerificationToken.VerificationTokenType getTokenType() {
        return tokenType;
    }
}
