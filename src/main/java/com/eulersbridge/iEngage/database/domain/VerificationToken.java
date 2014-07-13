package com.eulersbridge.iEngage.database.domain;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class VerificationToken {

    private static final int DEFAULT_EXPIRY_TIME_IN_MINS = 60 * 24; //24 hours

    
    @GraphId String token;
    private Calendar expiryDate;
    private VerificationTokenType tokenType;
    private boolean verified;

    private static Logger LOG = LoggerFactory.getLogger(VerificationToken.class);

    public VerificationToken() {
    	
    	this.token = UUID.randomUUID().toString();
        if (LOG.isTraceEnabled()) LOG.trace("Constructor("+token+','+expiryDate.toString()+','+tokenType+','+verified+')');
		this.expiryDate = calculateExpiryDate(DEFAULT_EXPIRY_TIME_IN_MINS);
    }

    public VerificationToken(VerificationTokenType tokenType, int expirationTimeInMinutes) {
    	
    	this.token = UUID.randomUUID().toString();
    	if (LOG.isTraceEnabled()) LOG.trace("Constructor("+token+','+expirationTimeInMinutes+','+tokenType+')');
		this.tokenType = tokenType;
        this.expiryDate = calculateExpiryDate(expirationTimeInMinutes);
    }

    public VerificationTokenType getTokenType() {
        return tokenType;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public Calendar getExpiryDate() {
        return expiryDate;
    }

    public String getToken() {
        return token;
    }

    private Calendar calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar expiryDate = Calendar.getInstance();
        expiryDate.set(Calendar.HOUR, 0);
        expiryDate.set(Calendar.MINUTE, 0);;
        expiryDate.set(Calendar.SECOND, 0);
        expiryDate.set(Calendar.MILLISECOND, 0);
        expiryDate.add(Calendar.MINUTE, expiryTimeInMinutes);
        return expiryDate;
    }
    
    public enum VerificationTokenType {

        lostPassword, emailVerification, emailRegistration
    }

    public boolean hasExpired() {
        Calendar tokenDate = getExpiryDate();
        return tokenDate.before(new Date());
    }
}