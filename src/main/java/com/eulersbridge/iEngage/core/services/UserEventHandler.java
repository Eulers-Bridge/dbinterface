package com.eulersbridge.iEngage.core.services;

import java.util.ArrayList;
import java.util.List;

import com.eulersbridge.iEngage.core.events.LikeEvent;
import com.eulersbridge.iEngage.core.events.LikedEvent;
import com.eulersbridge.iEngage.database.domain.*;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;

import com.eulersbridge.iEngage.core.events.users.AddPersonalityEvent;
import com.eulersbridge.iEngage.core.events.users.AuthenticateUserEvent;
import com.eulersbridge.iEngage.core.events.users.CreateUserEvent;
import com.eulersbridge.iEngage.core.events.users.DeleteUserEvent;
import com.eulersbridge.iEngage.core.events.users.PersonalityAddedEvent;
import com.eulersbridge.iEngage.core.events.users.PersonalityDetails;
import com.eulersbridge.iEngage.core.events.users.ReadUserEvent;
import com.eulersbridge.iEngage.core.events.users.RequestReadUserEvent;
import com.eulersbridge.iEngage.core.events.users.UpdateUserEvent;
import com.eulersbridge.iEngage.core.events.users.UserAccountVerifiedEvent;
import com.eulersbridge.iEngage.core.events.users.UserAuthenticatedEvent;
import com.eulersbridge.iEngage.core.events.users.UserCreatedEvent;
import com.eulersbridge.iEngage.core.events.users.UserDeletedEvent;
import com.eulersbridge.iEngage.core.events.users.UserDetails;
import com.eulersbridge.iEngage.core.events.users.UserUpdatedEvent;
import com.eulersbridge.iEngage.core.events.users.VerifyUserAccountEvent;
import com.eulersbridge.iEngage.core.events.voteRecord.AddVoteRecordEvent;
import com.eulersbridge.iEngage.core.events.voteRecord.DeleteVoteRecordEvent;
import com.eulersbridge.iEngage.core.events.voteRecord.ReadVoteRecordEvent;
import com.eulersbridge.iEngage.core.events.voteRecord.VoteRecordAddedEvent;
import com.eulersbridge.iEngage.core.events.voteRecord.VoteRecordDeletedEvent;
import com.eulersbridge.iEngage.core.events.voteRecord.VoteRecordDetails;
import com.eulersbridge.iEngage.core.events.voteRecord.VoteRecordReadEvent;
import com.eulersbridge.iEngage.core.events.voteReminder.AddVoteReminderEvent;
import com.eulersbridge.iEngage.core.events.voteReminder.DeleteVoteReminderEvent;
import com.eulersbridge.iEngage.core.events.voteReminder.ReadVoteReminderEvent;
import com.eulersbridge.iEngage.core.events.voteReminder.VoteReminderAddedEvent;
import com.eulersbridge.iEngage.core.events.voteReminder.VoteReminderDeletedEvent;
import com.eulersbridge.iEngage.core.events.voteReminder.VoteReminderDetails;
import com.eulersbridge.iEngage.core.events.voteReminder.VoteReminderReadEvent;
import com.eulersbridge.iEngage.database.repository.InstitutionRepository;
import com.eulersbridge.iEngage.database.repository.PersonalityRepository;
import com.eulersbridge.iEngage.database.repository.UserRepository;
import com.eulersbridge.iEngage.database.repository.VerificationTokenRepository;
import com.eulersbridge.iEngage.email.EmailVerification;
import com.eulersbridge.iEngage.email.EmailConstants;
import com.eulersbridge.iEngage.security.SecurityConstants;

public class UserEventHandler implements UserService,UserDetailsService
{

    private static Logger LOG = LoggerFactory.getLogger(UserEventHandler.class);

    private UserRepository userRepository;
    private PersonalityRepository personRepository;
    private InstitutionRepository instRepository;
    private VerificationTokenRepository tokenRepository;
    @Autowired VelocityEngine velocityEngine;
    
    public UserEventHandler(final UserRepository userRepository, final PersonalityRepository personRepository, final InstitutionRepository instRepo, final VerificationTokenRepository tokenRepo) 
    {
      this.userRepository = userRepository;
      this.personRepository = personRepository;
      this.instRepository = instRepo;
      this.tokenRepository = tokenRepo;
    }
    
	@Override
	@Transactional
	public UserCreatedEvent signUpNewUser(CreateUserEvent createUserEvent) 
	{
		UserDetails newUser=createUserEvent.getUserDetails();
		if (LOG.isDebugEnabled()) LOG.debug("Finding institution with instId = "+newUser.getInstitutionId());
    	Institution inst=instRepository.findOne(newUser.getInstitutionId());
    	if (LOG.isDebugEnabled()) LOG.debug("User Details :"+newUser);
    	User userToInsert=User.fromUserDetails(newUser);
    	UserCreatedEvent result;   	
    	
    	User createdUser=null,existingUser=null;
    	
		existingUser = userRepository.findByEmail(userToInsert.getEmail());
//TODO potentially check if existing user is verified.  User might want another verification email
    	if ((inst!=null)&&(null==existingUser))
    	{
        	if (LOG.isDebugEnabled()) LOG.debug("Found institution = "+inst);
    		userToInsert.setInstitution(inst);
    		userToInsert.setAccountVerified(false);
    		userToInsert.setRoles(SecurityConstants.USER_ROLE);
        	if (LOG.isDebugEnabled()) LOG.debug("userToInsert :"+userToInsert);
    		User test = userRepository.save(userToInsert);
    		//TODO what happens if this fails?
    		if (LOG.isDebugEnabled()) LOG.debug("test = "+test);
    		createdUser = userRepository.findOne(test.getNodeId());
    		
            VerificationToken token = new VerificationToken(
            		VerificationToken.VerificationTokenType.emailVerification,test,
            		EmailConstants.DEFAULT_EXPIRY_TIME_IN_MINS);
            if (LOG.isDebugEnabled()) LOG.debug("Verification token = "+token.toString());
            tokenRepository.save(token);
            EmailVerification verifyEmail=new EmailVerification(velocityEngine,createdUser,token);
            result=new UserCreatedEvent(createdUser.getEmail(),createdUser.toUserDetails(),verifyEmail);
    		
    	}
    	else if (null==inst)
    	{
    		result=UserCreatedEvent.instituteNotFound(newUser.getEmail());
    	}
    	else
    	{
    		result=UserCreatedEvent.userNotUnique(newUser.getEmail());
    	}
    	return result;
	}

	@Override
	public ReadUserEvent requestReadUser(RequestReadUserEvent requestReadUserEvent) 
	{
	    if (LOG.isDebugEnabled()) LOG.debug("requestReadUser("+requestReadUserEvent.getEmail()+")");
	    User user = userRepository.findByEmail(requestReadUserEvent.getEmail());
	    ReadUserEvent response;
	    if (user == null) 
	    {
	      response=ReadUserEvent.notFound(requestReadUserEvent.getEmail());
	    }
	    else
	    {
//	    template.fetch(user.getInstitution());
		    UserDetails result=user.toUserDetails();
		    if (LOG.isDebugEnabled()) LOG.debug("Result - "+result);
		    response=new ReadUserEvent(requestReadUserEvent.getEmail(), result);
	    }
	    return response;
	}

	@Override
	@Transactional
	public UserDeletedEvent deleteUser(DeleteUserEvent deleteUserEvent) 
	{
	    if (LOG.isDebugEnabled()) LOG.debug("requestDeleteUser("+deleteUserEvent.getEmail()+")");
	    User user=userRepository.findByEmail(deleteUserEvent.getEmail());
	    if (user==null)
	    {
	    	return UserDeletedEvent.notFound(deleteUserEvent.getEmail());
	    }
	    userRepository.delete(user.getNodeId());
	    return new UserDeletedEvent(deleteUserEvent.getEmail(),user.toUserDetails());
	}

	@Override
	@Transactional
	public UserUpdatedEvent updateUser(UpdateUserEvent updateUserEvent) 
	{
		UserDetails newUser=updateUserEvent.getUserDetails();
		User user=null,result=null,userToUpdate=User.fromUserDetails(newUser);
    	if (LOG.isDebugEnabled()) LOG.debug("User Details :"+newUser);
	    user=userRepository.findByEmail(updateUserEvent.getEmail());
	    if (null==user)
	    {
	    	if (LOG.isDebugEnabled()) LOG.debug("User does not exist, adding another.");
	    	// Do not allow a new user to be created with account verified set to true.
	    	newUser.setAccountVerified(false);
	    }
	    else
	    {
	    	userToUpdate.setNodeId(user.getNodeId());
	    }
    	if (LOG.isDebugEnabled()) LOG.debug("userToUpdate :"+userToUpdate);

		if (LOG.isDebugEnabled()) LOG.debug("Finding institution with instId = "+newUser.getInstitutionId());
    	Institution inst=instRepository.findOne(newUser.getInstitutionId());

    	if (inst!=null)
    	{
        	if (LOG.isDebugEnabled()) LOG.debug("Found institution = "+inst);
        	userToUpdate.setInstitution(inst);
   			result = userRepository.save(userToUpdate);
    		if (LOG.isDebugEnabled()) LOG.debug("test = "+result);
    	}
    	else
    	{
    		return UserUpdatedEvent.instituteNotFound(updateUserEvent.getEmail());
    	}

    	return new UserUpdatedEvent(result.getEmail(),result.toUserDetails());
	}
	
	@Override
	@Transactional
	public UserAccountVerifiedEvent validateUserAccount(VerifyUserAccountEvent verifyUserAccountEvent)
	{
		String emailToVerify = verifyUserAccountEvent.getEmail();
		String tokenString = verifyUserAccountEvent.getToken();
		User user=null,resultUser=null;
		VerificationToken token=null, resultToken=null;
		UserAccountVerifiedEvent verificationResult = null;
    	if (LOG.isDebugEnabled()) LOG.debug("Verification Details :"+ emailToVerify + " " + tokenString);
	    user=userRepository.findByEmail(emailToVerify);
	    token=tokenRepository.findByToken(tokenString);
	    if (null==user)
	    {
	    	if (LOG.isDebugEnabled()) LOG.debug("User not found, cannot be verified.");
	    	verificationResult = new UserAccountVerifiedEvent(emailToVerify);
	    	verificationResult.setVerificationError(UserAccountVerifiedEvent.VerificationErrorType.userNotFound);
	    }
	    else if(null == token)
	    {
	    	if (LOG.isDebugEnabled()) LOG.debug("Token does not exist, cannot be verified.");
	    	verificationResult = new UserAccountVerifiedEvent(emailToVerify, user.toUserDetails());
	    	verificationResult.setVerificationError(UserAccountVerifiedEvent.VerificationErrorType.tokenDoesntExists);
	    }
	    else if (!(token.getUser().getNodeId().equals(user.getNodeId())))
	    {
	    	if (LOG.isDebugEnabled()) LOG.debug("Token does not match, specified user.  Cannot be verified.");
	    	verificationResult = new UserAccountVerifiedEvent(emailToVerify, user.toUserDetails());
	    	verificationResult.setVerificationError(UserAccountVerifiedEvent.VerificationErrorType.tokenUserMismatch);
	    }
	    else if((token.isVerified())||(user.isAccountVerified()))
	    {
	    	if (LOG.isDebugEnabled()) LOG.debug("User is already verified, cannot be verified twice.");
	    	verificationResult = new UserAccountVerifiedEvent(emailToVerify, user.toUserDetails());
	    	verificationResult.setVerificationError(UserAccountVerifiedEvent.VerificationErrorType.tokenAlreadyUsed);
	    }
	    else if(token.hasExpired())
	    {
	    	if (LOG.isDebugEnabled()) LOG.debug("Token already expired, cannot be used anymore.");
	    	verificationResult = new UserAccountVerifiedEvent(emailToVerify, user.toUserDetails());
	    	verificationResult.setVerificationError(UserAccountVerifiedEvent.VerificationErrorType.tokenExpired);
	    }
	    else if(!(token.getTokenType().equals(VerificationToken.VerificationTokenType.emailVerification.toString())))
	    {
	    	if (LOG.isDebugEnabled()) LOG.debug("Token type mismatch, " + token.getTokenType() +" cannot be used for email verification.");
	    	if (LOG.isDebugEnabled()) LOG.debug("Token type = " + token.getTokenType() +" VerificationToken.VerificationTokenType = "+VerificationToken.VerificationTokenType.emailVerification.toString());
	    	verificationResult = new UserAccountVerifiedEvent(emailToVerify, user.toUserDetails());
	    	verificationResult.setVerificationError(UserAccountVerifiedEvent.VerificationErrorType.tokenTypeMismatch);
	    }
	    else
	    {
	    	user.setAccountVerified(true);
	    	if (LOG.isDebugEnabled()) LOG.debug("userToVerify :"+user);
	    	resultUser = userRepository.save(user);
	    	
	    	token.setVerified(true);
	    	if (LOG.isDebugEnabled()) LOG.debug("tokenToVerify :"+token);
	    	resultToken = tokenRepository.save(token);
	    	
	    	verificationResult = new UserAccountVerifiedEvent(emailToVerify,resultUser.toUserDetails(),resultToken.isVerified());
	    }

	    return verificationResult;
	}

	@Override
	public UserAuthenticatedEvent authenticateUser(
			AuthenticateUserEvent authUserEvent) throws AuthenticationException
	{
		String userName=authUserEvent.getUserName();
		String password=authUserEvent.getPassword();
		String emailAddress=userName;
		UserAuthenticatedEvent evt;
		User user=userRepository.findByEmail(emailAddress);
		if (user!=null)
		{
			if (user.isAccountVerified())
			{	
				if (user.comparePassword(password))
				{
					List<GrantedAuthority> grantedAuths=authsFromString(user.getRoles());
					evt=new UserAuthenticatedEvent(grantedAuths);
				}
				else
				{
					if (LOG.isDebugEnabled()) LOG.debug("Password does not match.");
					evt=UserAuthenticatedEvent.badCredentials();
					throw new BadCredentialsException(SecurityConstants.BadPassword);
				}
			}
			else
			{
				if (LOG.isDebugEnabled()) LOG.debug("Account is not verified.");
				evt=UserAuthenticatedEvent.badCredentials();
				AccountStatusException e=new DisabledException(SecurityConstants.NotVerified);
				throw e;
			}
		}
		else
		{
			if (LOG.isDebugEnabled()) LOG.debug("No such account.");
			evt=UserAuthenticatedEvent.badCredentials();
			throw new UsernameNotFoundException(SecurityConstants.UserNotFound);
		}
		return evt;
	}

	List<GrantedAuthority> authsFromString(String authString)
	{
		List<GrantedAuthority> grantedAuths = new ArrayList<>();
		if (authString!=null)
		{
			String[] auths=authString.split(",");
			for (int x=0;x<auths.length;x++)
			{
				SimpleGrantedAuthority auth=new SimpleGrantedAuthority(auths[x]);
				grantedAuths.add(auth);
			}
		}
		return grantedAuths;
	}

	@Override
	public PersonalityAddedEvent addPersonality(AddPersonalityEvent addPersonalityEvent) 
	{
		PersonalityAddedEvent evt;
		
		String emailAddress=addPersonalityEvent.getEmail();
		if(LOG.isDebugEnabled()) LOG.debug("Email address - "+emailAddress);
		
		User user=userRepository.findByEmail(emailAddress);
		if (user!=null)
		{  // Valid User
			if (LOG.isDebugEnabled()) LOG.debug("UserId - "+user.getNodeId());
			PersonalityDetails dets=addPersonalityEvent.getDetails();	
			Personality personality=Personality.fromPersonalityDetails(dets);
			if (LOG.isDebugEnabled()) LOG.debug("Personality - "+personality);
			
			Personality personalityAdded=personRepository.save(personality);
			if (personalityAdded!=null)
			{
				Long personalityId=personalityAdded.getNodeId();
				Personality personalityLinked=userRepository.addPersonality(user.getNodeId(), personalityId);
				if (personalityLinked.equals(personalityAdded))
					evt=new PersonalityAddedEvent(personalityAdded.toPersonalityDetails());
				else
					evt=PersonalityAddedEvent.userNotFound();
			}
			else
			{
				evt=PersonalityAddedEvent.userNotFound();
			}
			
		}
		else
		{
			if (LOG.isDebugEnabled()) LOG.debug("No such account.");
			evt=PersonalityAddedEvent.userNotFound();
		}
		return evt;
	}

	@Override
	public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(
			String username) throws UsernameNotFoundException 
	{
		User user=userRepository.findByEmail(username);
		if (user!=null)
		{
			boolean notLocked=true;
			boolean enabled=user.isAccountVerified();
			boolean acctNotExpired=true;
			boolean credsNotExpired=true;
			org.springframework.security.core.userdetails.UserDetails dets=new org.springframework.security.core.userdetails.User
					(username, user.getPassword(), enabled, acctNotExpired, credsNotExpired, notLocked, authsFromString(user.getRoles()));
			return dets;
		}
		else
		{
			throw new UsernameNotFoundException(username+" not found.");
		}
	}

	@Override
	public VoteReminderAddedEvent addVoteReminder(AddVoteReminderEvent addVoteReminderEvent)
	{
		VoteReminderAddedEvent evt;
		
		String emailAddress=addVoteReminderEvent.getVoteReminderDetails().getUserId();
		if(LOG.isDebugEnabled()) LOG.debug("Email address - "+emailAddress);
		
		User user=userRepository.findByEmail(emailAddress);
		if (user!=null)
		{  // Valid User
			if (LOG.isDebugEnabled()) LOG.debug("UserId - "+user.getNodeId());
			Long electionId=addVoteReminderEvent.getVoteReminderDetails().getElectionId();
			Long date=addVoteReminderEvent.getVoteReminderDetails().getDate();
			String location=addVoteReminderEvent.getVoteReminderDetails().getLocation();
			if (LOG.isDebugEnabled()) LOG.debug("Election id - "+electionId+" Location - "+location+" Date - "+date);
			
			VoteReminder voteReminderAdded=userRepository.addVoteReminder(user.getNodeId(),electionId,date,location);
			if (voteReminderAdded!=null)
			{
				evt=new VoteReminderAddedEvent();
				evt.setVoteReminderDetails(voteReminderAdded.toVoteReminderDetails());
			}
			else
			{
				evt=VoteReminderAddedEvent.electionNotFound();
			}
		}
		else
		{
			if (LOG.isDebugEnabled()) LOG.debug("No such account.");
			evt=VoteReminderAddedEvent.userNotFound();
		}
		return evt;
	}

	@Override
	public VoteRecordAddedEvent addVoteRecord(AddVoteRecordEvent addVoteRecordEvent)
	{
		VoteRecordAddedEvent evt;
		
		String emailAddress=addVoteRecordEvent.getVoteRecordDetails().getVoterId();
		if(LOG.isDebugEnabled()) LOG.debug("Email address - "+emailAddress);
		
		User user=userRepository.findByEmail(emailAddress);
		if (user!=null)
		{  // Valid User
			if (LOG.isDebugEnabled()) LOG.debug("UserId - "+user.getNodeId());
			Long electionId=addVoteRecordEvent.getVoteRecordDetails().getElectionId();
			String location=addVoteRecordEvent.getVoteRecordDetails().getLocation();
			if (LOG.isDebugEnabled()) LOG.debug("Election id - "+electionId+" Location - "+location);
			
			VoteRecord voteRecordAdded=userRepository.addVoteRecord(user.getNodeId(),electionId,location);
			if (voteRecordAdded!=null)
			{
				evt=new VoteRecordAddedEvent();
				evt.setVoteRecordDetails(voteRecordAdded.toVoteRecordDetails());
			}
			else
			{
				evt=VoteRecordAddedEvent.electionNotFound();
			}
		}
		else
		{
			if (LOG.isDebugEnabled()) LOG.debug("No such account.");
			evt=VoteRecordAddedEvent.userNotFound();
		}
		return evt;
	}

	@Override
	public VoteRecordReadEvent readVoteRecord(ReadVoteRecordEvent readVoteRecordEvent)
	{
	    if (LOG.isDebugEnabled()) LOG.debug("readVoteRecord("+readVoteRecordEvent.getVoteRecordId()+")");
	    VoteRecord vr = userRepository.readVoteRecord(readVoteRecordEvent.getVoteRecordId());
	    VoteRecordReadEvent response;
	    if (vr == null) 
	    {
	      response=VoteRecordReadEvent.notFound(readVoteRecordEvent.getVoteRecordId());
	    }
	    else
	    {
		    VoteRecordDetails result=vr.toVoteRecordDetails();
		    if (LOG.isDebugEnabled()) LOG.debug("Result - "+result);
		    response=new VoteRecordReadEvent(readVoteRecordEvent.getVoteRecordId(), result);
	    }
	    return response;
	}

	@Override
	public VoteReminderReadEvent readVoteReminder(ReadVoteReminderEvent readVoteReminderEvent) {
	    if (LOG.isDebugEnabled()) LOG.debug("readVoteRecord("+readVoteReminderEvent.getVoteReminderId()+")");
	    VoteReminder vr = userRepository.readVoteReminder(readVoteReminderEvent.getVoteReminderId());
	    VoteReminderReadEvent response;
	    if (vr == null) 
	    {
	      response=VoteReminderReadEvent.notFound(readVoteReminderEvent.getVoteReminderId());
	    }
	    else
	    {
		    VoteReminderDetails result=vr.toVoteReminderDetails();
		    if (LOG.isDebugEnabled()) LOG.debug("Result - "+result);
		    response=new VoteReminderReadEvent(readVoteReminderEvent.getVoteReminderId(), result);
	    }
	    return response;
	}

	@Override
	public VoteRecordDeletedEvent deleteVoteRecord(
			DeleteVoteRecordEvent deleteVoteRecordEvent)
	{
	    if (LOG.isDebugEnabled()) LOG.debug("deleteVoteRecord("+deleteVoteRecordEvent.getVoteRecordId()+")");
	    VoteRecord vr = userRepository.deleteVoteRecord(deleteVoteRecordEvent.getVoteRecordId());
	    VoteRecordDeletedEvent response;
	    if (vr == null) 
	    {
	      response=VoteRecordDeletedEvent.notFound(deleteVoteRecordEvent.getVoteRecordId());
	    }
	    else
	    {
		    VoteRecordDetails result=vr.toVoteRecordDetails();
		    if (LOG.isDebugEnabled()) LOG.debug("Result - "+result);
//		    response=new VoteRecordDeletedEvent(deleteVoteRecordEvent.getVoteRecordId(), result);
		    response=new VoteRecordDeletedEvent(deleteVoteRecordEvent.getVoteRecordId());
	    }
	    return response;
	}

	@Override
	public VoteReminderDeletedEvent deleteVoteReminder(
			DeleteVoteReminderEvent deleteVoteReminderEvent)
	{
	    if (LOG.isDebugEnabled()) LOG.debug("deleteVoteReminder("+deleteVoteReminderEvent.getVoteReminderId()+")");
	    VoteReminder vr = userRepository.deleteVoteReminder(deleteVoteReminderEvent.getVoteReminderId());
	    VoteReminderDeletedEvent response;
	    if (vr == null) 
	    {
	      response=VoteReminderDeletedEvent.notFound(deleteVoteReminderEvent.getVoteReminderId());
	    }
	    else
	    {
		    VoteReminderDetails result=vr.toVoteReminderDetails();
		    if (LOG.isDebugEnabled()) LOG.debug("Result - "+result);
//		    response=new VoteReminderDeletedEvent(deleteVoteReminderEvent.getVoteReminderId(), result);
		    response=new VoteReminderDeletedEvent(deleteVoteReminderEvent.getVoteReminderId());
	    }
	    return response;
	}

    @Override
    public LikedEvent like(LikeEvent likeEvent) {
        boolean result=true;
        LikedEvent retValue;
        String email = likeEvent.getEmailAddress();
        Long pollId = likeEvent.getNodeId();
        Like like = userRepository.like(email, pollId);
        if (like!=null) result=true; else result=false;
        retValue = new LikedEvent(pollId, email, result);
        return retValue;
    }

    @Override
    public LikedEvent unlike(LikeEvent unlikeEvent) {
        boolean result=true;
        LikedEvent retValue;
        String email=unlikeEvent.getEmailAddress();
        Long pollId=unlikeEvent.getNodeId();
        userRepository.unlike(email, pollId);
        retValue=new LikedEvent(pollId,email,result);
        return retValue;
    }
    
	public Long findUserId(String emailAddress)
	{
	    if (LOG.isDebugEnabled()) LOG.debug("findUserId("+emailAddress+")");
	    User user = userRepository.findByEmail(emailAddress);
	    Long response;
	    if (user == null) 
	    {
	      response=null;
	    }
	    else
	    {
		    response=user.getNodeId();
	    }
	    return response;
		
	}

}
