package com.eulersbridge.iEngage.core.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import com.eulersbridge.iEngage.core.events.generalInfo.GeneralInfoDetails;
import com.eulersbridge.iEngage.core.events.generalInfo.GeneralInfoReadEvent;
import com.eulersbridge.iEngage.core.events.generalInfo.ReadGeneralInfoEvent;
import com.eulersbridge.iEngage.core.events.generalInfo.GiCountry;
import com.eulersbridge.iEngage.core.events.generalInfo.GiInstitution;
import com.eulersbridge.iEngage.core.events.institutions.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.transaction.annotation.Transactional;

import com.eulersbridge.iEngage.core.events.studentYear.CreateStudentYearEvent;
import com.eulersbridge.iEngage.core.events.studentYear.ReadStudentYearEvent;
import com.eulersbridge.iEngage.core.events.studentYear.StudentYearCreatedEvent;
import com.eulersbridge.iEngage.core.events.studentYear.StudentYearDetails;
import com.eulersbridge.iEngage.core.events.studentYear.StudentYearReadEvent;
import com.eulersbridge.iEngage.database.domain.Country;
import com.eulersbridge.iEngage.database.domain.GeneralInfo;
import com.eulersbridge.iEngage.database.domain.Institution;
import com.eulersbridge.iEngage.database.domain.StudentYear;
import com.eulersbridge.iEngage.database.repository.CountryRepository;
import com.eulersbridge.iEngage.database.repository.InstitutionRepository;
import com.eulersbridge.iEngage.database.repository.StudentYearRepository;

public class InstitutionEventHandler implements InstitutionService {

    private static Logger LOG = LoggerFactory.getLogger(InstitutionEventHandler.class);

    private InstitutionRepository instRepository;
    private CountryRepository countryRepository;
    private StudentYearRepository syRepository;
    
    public InstitutionEventHandler(final InstitutionRepository instRepo, final CountryRepository countryRepo, final StudentYearRepository syRepo) 
    {
      this.instRepository = instRepo;
      this.countryRepository = countryRepo;
      this.syRepository = syRepo;
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
	public InstitutionsReadEvent readInstitutions(ReadInstitutionsEvent readInstitutionsEvent) 
	{
	    if (LOG.isDebugEnabled()) LOG.debug("readInstitutions()");
	    Result<Institution> returned=null;
	    Long countryId=readInstitutionsEvent.getCountryId();
	    if (null==countryId)
			returned=instRepository.findAll();
	    else
	    	returned=instRepository.findByCountryId(countryId);
		ArrayList<InstitutionDetails> instList=new ArrayList<InstitutionDetails>();
		Iterator<Institution> iter=returned.iterator();
		while (iter.hasNext())
		{
			Institution institution=iter.next();
			InstitutionDetails dets=institution.toInstDetails();
			instList.add(dets);
		}
		InstitutionsReadEvent evt=new InstitutionsReadEvent(instList);
		return evt;
	}

	@Override
	public StudentYearCreatedEvent createStudentYear(
			CreateStudentYearEvent createStudentYearEvent) 
	{
		StudentYearDetails newYear=createStudentYearEvent.getStudentYearDetails();
		if (LOG.isDebugEnabled()) LOG.debug("Finding institution with institutionId = "+newYear.getInstitutionId());
    	Institution inst=instRepository.findOne(newYear.getInstitutionId());
    	if (LOG.isDebugEnabled()) LOG.debug("inst - "+inst);
    	StudentYear yearToInsert=StudentYear.fromDetails(newYear);
    	StudentYearCreatedEvent result;	
    	StudentYear createdYear=null;
    	
    	if (inst!=null)
    	{
    		yearToInsert.setInstitution(inst);
	    	if (LOG.isDebugEnabled()) LOG.debug("yearToInsert :"+yearToInsert);
	    	createdYear = syRepository.save(yearToInsert);
			//TODO what happens if this fails?
			if (LOG.isDebugEnabled()) LOG.debug("created year = "+createdYear);
	        result=new StudentYearCreatedEvent(createdYear.getNodeId(),createdYear.toDetails());
    	}
    	else
    	{
    		result=StudentYearCreatedEvent.institutionNotFound(null);
    	}
    	return result;
	}

	@Override
	public StudentYearReadEvent readStudentYear(
			ReadStudentYearEvent readStudentYearEvent) 
	{
		StudentYear sy=syRepository.findOne(readStudentYearEvent.getStudentYearId());
		StudentYearReadEvent result;
		if (sy!=null)
		{
			result=new StudentYearReadEvent(readStudentYearEvent.getStudentYearId(),sy.toDetails());
		}
		else
		{
			result=StudentYearReadEvent.notFound(readStudentYearEvent.getStudentYearId());
		}
		return result;
	}

	@Override
	public Iterator<StudentYear> getStudentYears(Long institutionId) 
	{
		Set<StudentYear> years=syRepository.findStudentYears(institutionId);
		return years.iterator();
	}

	@Override
	public GeneralInfoReadEvent getGeneralInfo(ReadGeneralInfoEvent readGeneralInfoEvent)
	{
        Iterator<GeneralInfo> countryIter=instRepository.getGeneralInfo().iterator();
        
        ArrayList <GiCountry> countries= new ArrayList<GiCountry>();
        while(countryIter.hasNext())
		{
			GeneralInfo country=countryIter.next();
			
			Long countryId=country.getCountryId();
			String countryName=country.getCountryName();
			if (LOG.isDebugEnabled())
			{
				LOG.debug("CountryId - "+countryId);
				LOG.debug("CountryName - "+countryName);
			}
			
			Iterator<Long> instIdIter=country.getInstitutionIds().iterator();
			Iterator<String> instNameIter=country.getInstitutionNames().iterator();
			ArrayList<GiInstitution> insts=new ArrayList<GiInstitution>();
			while(instIdIter.hasNext()&&(instNameIter.hasNext()))
			{
				Long instId=instIdIter.next();
				String instName=instNameIter.next();
				GiInstitution giDets=new GiInstitution(instId,instName);
				insts.add(giDets);
			}
			GiCountry giCountry=new GiCountry(countryId,countryName,insts.iterator());
			countries.add(giCountry);
		}
       
        GeneralInfoDetails dets=new GeneralInfoDetails(countries.iterator());
        
        GeneralInfoReadEvent generalInfoReadEvent = new GeneralInfoReadEvent(dets);
        return generalInfoReadEvent;
	}

	//TODO Create the associate methods for Student Year.
	/*
	@Override
	public StudentYearReadEvent readStudentYear(
			ReadStudentYearEvent readStudentYearEvent) 
	{
	}
	*/
}
