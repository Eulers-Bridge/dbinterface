package com.eulersbridge.iEngage.core.services;

import java.util.ArrayList;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.conversion.Result;
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
import com.eulersbridge.iEngage.database.domain.Country;
import com.eulersbridge.iEngage.database.domain.Institution;
import com.eulersbridge.iEngage.database.repository.CountryRepository;
import com.eulersbridge.iEngage.database.repository.InstitutionRepository;

public class InstitutionEventHandler implements InstitutionService {

    private static Logger LOG = LoggerFactory.getLogger(InstitutionEventHandler.class);

    private InstitutionRepository instRepository;
    private CountryRepository countryRepository;
    
    public InstitutionEventHandler(final InstitutionRepository instRepo, final CountryRepository countryRepo) 
    {
      this.instRepository = instRepo;
      this.countryRepository = countryRepo;
    }
    
	@Override
	@Transactional
	public InstitutionCreatedEvent createInstitution(
			CreateInstitutionEvent createInstitutionEvent) 
	{
		InstitutionDetails newInst=createInstitutionEvent.getDetails();
    	if (LOG.isDebugEnabled()) LOG.debug("Institution Details :"+newInst);
		if (LOG.isDebugEnabled()) LOG.debug("Finding country with countryName = "+newInst.getCountryName());
    	Country country=countryRepository.findByCountryName(newInst.getCountryName());

    	Institution instToInsert=Institution.fromInstDetails(newInst);
    	InstitutionCreatedEvent result;   	
    	
    	Institution createdInst=null;
    	
    	if (country!=null)
    	{	
    		instToInsert.setCountry(country);
	    	if (LOG.isDebugEnabled()) LOG.debug("instToInsert :"+instToInsert);
	    	createdInst = instRepository.save(instToInsert);
			//TODO what happens if this fails?
			if (LOG.isDebugEnabled()) LOG.debug("created inst = "+createdInst);
	        result=new InstitutionCreatedEvent(createdInst.getNodeId(),createdInst.toInstDetails());
    	}
    	else
    	{
    		if (LOG.isErrorEnabled()) LOG.error("Country returned was null.");
			result=InstitutionCreatedEvent.countryNotFound(new Long(0));
    	}
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
	    if (LOG.isDebugEnabled()) LOG.debug("updateInstitution("+updateInstitutionEvent.getId()+")");
		InstitutionDetails updInst=updateInstitutionEvent.getInstDetails();
		Institution result=null,instToUpdate=Institution.fromInstDetails(updInst);
		instToUpdate.setNodeId(updateInstitutionEvent.getId());
    	if (LOG.isDebugEnabled()) LOG.debug("Inst Details :"+updInst);
    	
		if (LOG.isDebugEnabled()) LOG.debug("Finding country with countryName = "+updInst.getCountryName());
    	Country country=countryRepository.findByCountryName(updInst.getCountryName());
    	if (country!=null)
    	{
    	instToUpdate.setCountry(country);
    	if (LOG.isDebugEnabled()) LOG.debug("instToUpdate :"+instToUpdate);
    	
    	
		result = instRepository.save(instToUpdate);
    	}
    	else
    	{
    		return InstitutionUpdatedEvent.countryNotFound(updateInstitutionEvent.getId());
    	}
		if (LOG.isDebugEnabled()) LOG.debug("result = "+result);
    	return new InstitutionUpdatedEvent(result.getNodeId(),result.toInstDetails());

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

	@Override
	public Iterator<com.eulersbridge.iEngage.rest.domain.Institution> getInstitutions() 
	{
		Iterable<Institution> returned=instRepository.findAll();
		ArrayList<com.eulersbridge.iEngage.rest.domain.Institution> instList=new ArrayList<com.eulersbridge.iEngage.rest.domain.Institution>();
		Iterator<Institution> iter=returned.iterator();
		while (iter.hasNext())
		{
			Institution inst=iter.next();
			InstitutionDetails dets=inst.toInstDetails();
			
			com.eulersbridge.iEngage.rest.domain.Institution restInst=com.eulersbridge.iEngage.rest.domain.Institution.fromInstDetails(dets);
			instList.add(restInst);
		}
		return instList.iterator();
	}

}
