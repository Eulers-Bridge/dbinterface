package com.eulersbridge.iEngage.core.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.eulersbridge.iEngage.core.events.institutions.CreateInstitutionEvent;
import com.eulersbridge.iEngage.core.events.institutions.DeleteInstitutionEvent;
import com.eulersbridge.iEngage.core.events.institutions.InstitutionCreatedEvent;
import com.eulersbridge.iEngage.core.events.institutions.InstitutionDeletedEvent;
import com.eulersbridge.iEngage.core.events.institutions.InstitutionDetails;
import com.eulersbridge.iEngage.core.events.institutions.InstitutionUpdatedEvent;
import com.eulersbridge.iEngage.core.events.institutions.ReadInstitutionEvent;
import com.eulersbridge.iEngage.core.events.institutions.RequestReadInstitutionEvent;
import com.eulersbridge.iEngage.core.events.institutions.UpdateInstitutionEvent;
import com.eulersbridge.iEngage.database.domain.Institution;
import com.eulersbridge.iEngage.database.repository.InstitutionRepository;

public class InstitutionEventHandler implements InstitutionService {

    private static Logger LOG = LoggerFactory.getLogger(InstitutionEventHandler.class);

    private InstitutionRepository instRepository;
    
    public InstitutionEventHandler(final InstitutionRepository instRepo) 
    {
      this.instRepository = instRepo;
    }
    
	@Override
	@Transactional
	public InstitutionCreatedEvent createInstitution(
			CreateInstitutionEvent createInstitutionEvent) 
	{
		InstitutionDetails newInst=createInstitutionEvent.getDetails();
    	if (LOG.isDebugEnabled()) LOG.debug("Institution Details :"+newInst);
    	Institution instToInsert=Institution.fromInstDetails(newInst);
    	InstitutionCreatedEvent result;   	
    	
    	Institution createdInst=null;
    	if (LOG.isDebugEnabled()) LOG.debug("instToInsert :"+instToInsert);
    	Institution test = instRepository.save(instToInsert);
		//TODO what happens if this fails?
		if (LOG.isDebugEnabled()) LOG.debug("test = "+test);
		createdInst = instRepository.findOne(test.getNodeId());
    		
        result=new InstitutionCreatedEvent(createdInst.getNodeId(),createdInst.toInstDetails());
    	return result;
	}

	@Override
	public ReadInstitutionEvent requestReadInstitution(
			RequestReadInstitutionEvent requestReadInstitutionEvent) 
	{
	    if (LOG.isDebugEnabled()) LOG.debug("requestReadInstitution("+requestReadInstitutionEvent.getId()+")");
	    Institution inst = instRepository.findOne(requestReadInstitutionEvent.getId());

	    if (inst == null) 
	    {
	      return ReadInstitutionEvent.notFound(requestReadInstitutionEvent.getId());
	    }

//	    template.fetch(user.getInstitution());

	    InstitutionDetails result=inst.toInstDetails();
	    if (LOG.isDebugEnabled()) LOG.debug("Result - "+result);
	    return new ReadInstitutionEvent(requestReadInstitutionEvent.getId(), result);
	}

	@Override
	public InstitutionUpdatedEvent updateInstitution(
			UpdateInstitutionEvent updateInstitutionEvent) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public InstitutionDeletedEvent deleteInstitution(
			DeleteInstitutionEvent deleteInstitutionEvent) 
	{
	    if (LOG.isDebugEnabled()) LOG.debug("deleteInstitution("+deleteInstitutionEvent.getId()+")");
	    Institution inst=instRepository.findOne(deleteInstitutionEvent.getId());
	    if (inst==null)
	    {
	    	return InstitutionDeletedEvent.notFound(deleteInstitutionEvent.getId());
	    }
	    instRepository.delete(inst.getNodeId());
	    return new InstitutionDeletedEvent(deleteInstitutionEvent.getId(),inst.toInstDetails());
	}

}
