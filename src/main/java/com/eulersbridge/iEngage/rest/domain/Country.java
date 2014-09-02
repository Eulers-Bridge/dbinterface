package com.eulersbridge.iEngage.rest.domain;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import com.eulersbridge.iEngage.database.repository.InstitutionRepository.GeneralInfo;
import org.springframework.hateoas.ResourceSupport;

import com.eulersbridge.iEngage.core.events.countrys.CountryDetails;
import com.eulersbridge.iEngage.rest.controller.CountryController;

import java.util.ArrayList;
import java.util.Iterator;

public class Country extends ResourceSupport
{
	Long countryId;
	String countryName;
	Institution universities[];
	
	public Country(Long id,String name,Institution unis[])
	{
		countryId=id;
		countryName=name;
		universities=unis;
	}
	
	public Country() 
	{

	}

	public Long getCountryId()
	{
		return countryId;
	}
	
	public String getCountryName()
	{
		return countryName;
	}
	
	public Institution[] getUniversities()
	{
		return universities;
	}
	
	public CountryDetails toCountryDetails() 
	  {
		CountryDetails details = new CountryDetails(countryId);

	    details.setCountryName(getCountryName());

	    return details;
	  }

	  // {!begin fromOrderDetails}
	  public static Country fromCountryDetails(CountryDetails readCountry) 
	  {
		  Country country = new Country();

		country.countryId = readCountry.getCountryId();
		country.countryName = readCountry.getCountryName();
	    String simpleName=Country.class.getSimpleName();
	    String name=simpleName.substring(0, 1).toLowerCase()+simpleName.substring(1);
	    
	    //TODOCUMENT.  Adding the library, the above extends ResourceSupport and
	    //this section is all that is actually needed in our model to add hateoas support.

	    //Much of the rest of the framework is helping deal with the blending of domains that happens in many spring apps
	    //We have explicitly avoided that.
	    // {!begin selfRel}
	    country.add(linkTo(CountryController.class).slash(name).slash(country.countryId).withSelfRel());
	    // {!end selfRel}

	    return country;
	  }

    public static Country[] fromGeneralInfos(Iterator<GeneralInfo> generalInfos) {
		ArrayList<Country> countries =new ArrayList<Country>();
		while(generalInfos.hasNext())
		{
			GeneralInfo generalInfo = generalInfos.next();
            Long countryId = generalInfo.getCountryId();
            String countryName = generalInfo.getCountryName();
            ArrayList<Institution> institutions = new ArrayList<>();
			Iterator<Long> instIds = generalInfo.getInstitutionIds().iterator();
            Iterator<String> instNames = generalInfo.getInstitutionNames().iterator();
			while(instIds.hasNext() && instNames.hasNext())
			{
				Institution institution= new Institution(instIds.next());
                institution.setName(instNames.next());
                institutions.add(institution);
		    }
			Institution[] insts=institutions.toArray(new Institution[0]);
            countries.add(new Country(countryId, countryName, insts));
		}
        return (Country[])countries.toArray(new Country[0]);
    }

	public void setId(Long countryId) 
	{
		this.countryId=countryId;		
	}

}
