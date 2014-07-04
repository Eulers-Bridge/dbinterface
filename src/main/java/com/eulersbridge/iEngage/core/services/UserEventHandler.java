package com.eulersbridge.iEngage.core.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.eulersbridge.iEngage.database.domain.Institution;
import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.repository.InstitutionRepository;
import com.eulersbridge.iEngage.database.repository.UserRepository;

public class UserEventHandler implements UserService {

	@Autowired UserRepository studentRepo;
	@Autowired InstitutionRepository instRepo;

    private static Logger LOG = LoggerFactory.getLogger(User.class);

	@Override
	@Transactional
	public User signUpNewUser(User newUser, Long institutionId) 
	{
    	Institution inst=instRepo.findOne(institutionId);
    	User result=newUser;
    	if (inst!=null)
    	{
    		newUser.setInstitution(inst);
/*    		Transaction tx=graphDatabaseService.beginTx();
    		try
    		{
*/    			User test = studentRepo.save(newUser);
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
    		if (LOG.isDebugEnabled()) LOG.debug("Count = "+studentRepo.count());
    		result = studentRepo.findOne(test.getNodeId());
    	}
    	return result;
	}

	@Override
	public User findUserByEmail(String emailAddress) 
	{
		// TODO Auto-generated method stub
		return null;
	}

}
