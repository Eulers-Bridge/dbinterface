package com.eulersbridge.iEngage.core.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.eulersbridge.iEngage.database.domain.Institution;
import com.eulersbridge.iEngage.database.domain.Student;
import com.eulersbridge.iEngage.database.repository.InstitutionRepository;
import com.eulersbridge.iEngage.database.repository.StudentRepository;

public class UserEventHandler implements UserService {

	@Autowired StudentRepository studentRepo;
	@Autowired InstitutionRepository instRepo;

    private static Logger LOG = LoggerFactory.getLogger(Student.class);

	@Override
	@Transactional
	public Student signUpNewUser(Student newUser, Long institutionId) 
	{
    	Institution inst=instRepo.findOne(institutionId);
    	Student result=newUser;
    	if (inst!=null)
    	{
    		newUser.setInstitution(inst);
/*    		Transaction tx=graphDatabaseService.beginTx();
    		try
    		{
*/    			Student test = studentRepo.save(newUser);
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
	public Student findUserByEmail(String emailAddress) 
	{
		// TODO Auto-generated method stub
		return null;
	}

}
