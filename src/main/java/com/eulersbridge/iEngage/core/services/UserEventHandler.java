package com.eulersbridge.iEngage.core.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.eulersbridge.iEngage.core.events.users.CreateUserEvent;
import com.eulersbridge.iEngage.core.events.users.DeleteUserEvent;
import com.eulersbridge.iEngage.core.events.users.ReadUserEvent;
import com.eulersbridge.iEngage.core.events.users.RequestReadUserEvent;
import com.eulersbridge.iEngage.core.events.users.UpdateUserEvent;
import com.eulersbridge.iEngage.core.events.users.UserAccountVerifiedEvent;
import com.eulersbridge.iEngage.core.events.users.UserCreatedEvent;
import com.eulersbridge.iEngage.core.events.users.UserDeletedEvent;
import com.eulersbridge.iEngage.core.events.users.UserDetails;
import com.eulersbridge.iEngage.core.events.users.UserUpdatedEvent;
import com.eulersbridge.iEngage.core.events.users.VerifyUserAccountEvent;
import com.eulersbridge.iEngage.database.domain.Institution;
import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.domain.VerificationToken;
import com.eulersbridge.iEngage.database.repository.InstitutionRepository;
import com.eulersbridge.iEngage.database.repository.UserRepository;
import com.eulersbridge.iEngage.database.repository.VerificationTokenRepository;
import com.eulersbridge.iEngage.email.EmailVerification;
import com.eulersbridge.iEngage.email.EmailConstants;

public class UserEventHandler implements UserService 
{

    private static Logger LOG = LoggerFactory.getLogger(UserEventHandler.class);

    private UserRepository userRepository;
    private InstitutionRepository instRepository;
    private VerificationTokenRepository tokenRepository;
    
    public UserEventHandler(final UserRepository userRepository, final InstitutionRepository instRepo, final VerificationTokenRepository tokenRepo) 
    {
      this.userRepository = userRepository;
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
            EmailVerification verifyEmail=new EmailVerification(createdUser,token);
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

	    if (user == null) 
	    {
	      return ReadUserEvent.notFound(requestReadUserEvent.getEmail());
	    }

//	    template.fetch(user.getInstitution());

	    UserDetails result=user.toUserDetails();
	    if (LOG.isDebugEnabled()) LOG.debug("Result - "+result);
	    return new ReadUserEvent(requestReadUserEvent.getEmail(), result);
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
    	if (LOG.isDebugEnabled()) LOG.debug("Verification Details :"+ emailToVerify + " " + token);
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


}
