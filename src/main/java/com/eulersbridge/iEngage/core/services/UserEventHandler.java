package com.eulersbridge.iEngage.core.services;

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.transaction.annotation.Transactional;

import com.eulersbridge.iEngage.core.events.users.ReadUserEvent;
import com.eulersbridge.iEngage.core.events.users.RequestReadUserEvent;
import com.eulersbridge.iEngage.core.events.users.UserDetails;
import com.eulersbridge.iEngage.database.domain.Institution;
import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.repository.InstitutionRepository;
import com.eulersbridge.iEngage.database.repository.UserRepository;

public class UserEventHandler implements UserService {

//	@Autowired InstitutionRepository instRepo;

    private static Logger LOG = LoggerFactory.getLogger(UserEventHandler.class);

    private UserRepository userRepository;

    public UserEventHandler(final UserRepository userRepository) {
      this.userRepository = userRepository;
    }
    
/*	public UserEventHandler() 
	{
		// TODO Auto-generated constructor stub
	}
*/
	@Override
	@Transactional
	public User signUpNewUser(UserDetails newUser) 
	{
//    	Institution inst=instRepo.findOne(newUser.getInstitution().getNodeId());
    	User userToInsert=User.fromUserDetails(newUser);
//    	userToInsert.setInstitution(inst);
    	
    	User result=null;
//    	if (inst!=null)
    	{
//    		newUser.setInstitution(inst);
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
    	return result;
	}

	@Override
	public User findUser(String email) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReadUserEvent requestReadUser(RequestReadUserEvent requestReadUserEvent) {
//TODO	    User user = userRepository.findByEmail(requestReadUserEvent.getEmail());
	    if (LOG.isDebugEnabled()) LOG.debug("requestReadUser("+requestReadUserEvent.getEmail()+")");
		Result <User> users = userRepository.findAll();
		Iterator<User> iter=users.iterator();
		User user=null;
		while (iter.hasNext())
		{
			User res=iter.next();
			if (res.getEmail().equals(requestReadUserEvent.getEmail()))
			{	
				user=res;
				if (LOG.isDebugEnabled()) LOG.debug("res = "+res);
			}
		}


	    if (user == null) {
	      return ReadUserEvent.notFound(requestReadUserEvent.getEmail());
	    }
	    UserDetails result=user.toUserDetails();
	    if (LOG.isDebugEnabled()) LOG.debug("Result - "+result);
	    return new ReadUserEvent(requestReadUserEvent.getEmail(), result);
	}

}
