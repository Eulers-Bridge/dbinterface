package com.eulersbridge.iEngage.core.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.transaction.annotation.Transactional;

import com.eulersbridge.iEngage.core.events.users.CreateUserEvent;
import com.eulersbridge.iEngage.core.events.users.ReadUserEvent;
import com.eulersbridge.iEngage.core.events.users.RequestReadUserEvent;
import com.eulersbridge.iEngage.core.events.users.UserCreatedEvent;
import com.eulersbridge.iEngage.core.events.users.UserDetails;
import com.eulersbridge.iEngage.database.domain.Institution;
import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.repository.InstitutionRepository;
import com.eulersbridge.iEngage.database.repository.UserRepository;

public class UserEventHandler implements UserService {

//	@Autowired InstitutionRepository instRepo;

    private static Logger LOG = LoggerFactory.getLogger(UserEventHandler.class);

    private UserRepository userRepository;
    private InstitutionRepository instRepository;
    
    @Autowired 
    Neo4jOperations template;

    public UserEventHandler(final UserRepository userRepository, final InstitutionRepository instRepo) {
      this.userRepository = userRepository;
      this.instRepository = instRepo;
    }
    
/*	public UserEventHandler() 
	{
		// TODO Auto-generated constructor stub
	}
*/
	@Override
	@Transactional
	public UserCreatedEvent signUpNewUser(CreateUserEvent createUserEvent) 
	{
		UserDetails newUser=createUserEvent.getUserDetails();
		if (LOG.isDebugEnabled()) LOG.debug("Finding institution with instId = "+newUser.getInstitutionId());
    	Institution inst=instRepository.findOne(newUser.getInstitutionId());
    	if (LOG.isDebugEnabled()) LOG.debug("Found institution = "+inst);
    	if (LOG.isDebugEnabled()) LOG.debug("User Details :"+newUser);
    	User userToInsert=User.fromUserDetails(newUser);
    	if (LOG.isDebugEnabled()) LOG.debug("userToInsert :"+userToInsert);
    	userToInsert.setInstitution(inst);
    	
    	User result=null;
    	if (inst!=null)
    	{
    		userToInsert.setInstitution(inst);
    		userToInsert.setAccountVerified(false);
/*    		Transaction tx=graphDatabaseService.beginTx();
    		try
    		{
*/    			User test = userRepository.save(userToInsert);
/*    			tx.success();
    		}
    		catch (Exception e)
    		{
    			tx.failure();
    		}
    		finally
    		{
    			tx.finish();
    		}
*/    		if (LOG.isDebugEnabled()) LOG.debug("test = "+test);
    		if (LOG.isDebugEnabled()) LOG.debug("Count = "+userRepository.count());
    		result = userRepository.findOne(test.getNodeId());
    	}
    	return new UserCreatedEvent(result.getNodeId(),result.toUserDetails());
	}

	@Override
	public User findUser(String email) 
	{
		// TODO Auto-generated method stub
		return null;
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
	public UserDeletedEvent deleteUser(DeleteUserEvent deleteUserEvent) {
		// TODO Auto-generated method stub
		return null;
	}

}
