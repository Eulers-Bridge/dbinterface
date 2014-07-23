package com.eulersbridge.iEngage.database.domain;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.neo4j.graphdb.Direction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import com.eulersbridge.iEngage.email.EmailConstants;


@NodeEntity
public class VerificationToken {
    
    @GraphId Long nodeId; 
    private String token;
    private Long expiryDate;
    private String tokenType;
    private boolean verified=false;
	@RelatedTo(type = "verifiedBy", direction=Direction.BOTH) @Fetch
	private User user;

    private static Logger LOG = LoggerFactory.getLogger(VerificationToken.class);

    public VerificationToken() {
    	
    	this.token = UUID.randomUUID().toString();
		this.expiryDate = calculateExpiryDate(EmailConstants.DEFAULT_EXPIRY_TIME_IN_MINS).getTimeInMillis();
        if (LOG.isTraceEnabled()) LOG.trace("Constructor("+token+','+expiryDate.toString()+','+verified+')');
    }

    public VerificationToken(VerificationTokenType tokenType, User user, int expirationTimeInMinutes) {
    	
    	this.token = UUID.randomUUID().toString();
    	if (LOG.isTraceEnabled()) LOG.trace("Constructor("+token+','+expirationTimeInMinutes+','+tokenType+')');
		this.tokenType = tokenType.name();
		this.user=user;
        this.expiryDate = calculateExpiryDate(expirationTimeInMinutes).getTimeInMillis();
        if (LOG.isTraceEnabled()) LOG.trace("Constructor("+token+','+expiryDate.toString()+','+tokenType+','+verified+')');
    }

    public String getTokenType() {
        return tokenType;
    }

    public Long getNodeId()
    {
    	if (LOG.isTraceEnabled()) LOG.trace("getNodeId() = "+nodeId);
    	return nodeId;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public Long getExpiryDate() {
        return expiryDate;
    }

    public String getToken() {
        return token;
    }

    /**
	 * @return the user
	 */
	public User getUser() {
		return user;
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
    
    public enum VerificationTokenType 
    {
        lostPassword, emailVerification, emailRegistration
    }

    public boolean hasExpired() 
    {
    	Date now=new Date();
        return (getExpiryDate()<now.getTime());
    }
    
    public String toString()
    {
    	StringBuffer res=new StringBuffer("Token = ");
    	res.append(token);
    	res.append(" expriyDate = ");
    	res.append(expiryDate);
    	res.append("token type = ");
    	res.append(tokenType);
    	return res.toString();
    	
    }
}