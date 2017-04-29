package com.eulersbridge.iEngage.rest.domain;

import com.eulersbridge.iEngage.core.events.countrys.CountryDetails;
import com.eulersbridge.iEngage.rest.controller.CountryController;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class Country extends ResourceSupport
{
	Long countryId;
	String countryName;
	Institution institutions[];
	
	public Country(Long id,String name,Institution insts[])
	{
		countryId=id;
		countryName=name;
		institutions=insts;
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
	
	public Institution[] getInstitutions()
	{
		return institutions;
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
	    // {!begin readAll}
	    country.add(linkTo(CountryController.class).slash(name+'s').withRel(RestDomainConstants.READALL_LABEL));
	    // {!end readAll}

	    return country;
	  }

	public void setId(Long countryId) 
	{
		this.countryId=countryId;		
	}

}
