/**
 * 
 */
package com.eulersbridge.iEngage.rest.domain;

import com.eulersbridge.iEngage.core.events.generalInfo.GeneralInfoDetails;
import com.eulersbridge.iEngage.core.events.generalInfo.GiCountry;
import com.eulersbridge.iEngage.core.events.generalInfo.GiInstitution;
import com.eulersbridge.iEngage.rest.controller.InstitutionController;
import com.eulersbridge.iEngage.rest.domain.GeneralInfo.RestCountry.RestInst;
import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author Greg Newitt
 *
 */
public class GeneralInfo extends ResourceSupport 
{
	List <RestCountry> countrys;

	public static GeneralInfo fromGIDetails(GeneralInfoDetails dets) 
	{
		GeneralInfo gi=new GeneralInfo();
		gi.countrys=new ArrayList <RestCountry>();
		Iterator<GiCountry> iter=dets.getCountries();
		while (iter.hasNext())
		{
			GiCountry gic=iter.next();
			Iterator<GiInstitution> instIter = gic.getInsts();
			RestCountry country=gi.new RestCountry(gic.getCountryId(),gic.getCountryName());
			while (instIter.hasNext())
			{
				GiInstitution gii=instIter.next();
				RestInst restInst=country.new RestInst(gii.getInstId(),gii.getInstName());
				country.getInstitutions().add(restInst);
			}
			gi.countrys.add(country);
			
		}
		
	    // {!begin selfRel}
	    gi.add(linkTo(InstitutionController.class).slash(RestDomainConstants.GENERAL_INFO).withSelfRel());
	    // {!end selfRel}
	    // {!begin readAll}
	    gi.add(linkTo(InstitutionController.class).slash(RestDomainConstants.LOGIN).withRel(RestDomainConstants.RELATED_LABEL));
	    // {!end readAll}
		return gi;
	}
	
	public class RestCountry
	{
		String countryName;
		Long countryId;
		ArrayList <RestInst> institutions;
		
		public RestCountry(Long countryId,String countryName)
		{
			this.countryId=countryId;
			this.countryName=countryName;
			institutions=new ArrayList<RestInst>();
		}
		
		/**
		 * @return the countryName
		 */
		public String getCountryName() {
			return countryName;
		}

		/**
		 * @param countryName the countryName to set
		 */
		public void setCountryName(String countryName) {
			this.countryName = countryName;
		}

		/**
		 * @return the countryId
		 */
		public Long getCountryId() {
			return countryId;
		}

		/**
		 * @param countryId the countryId to set
		 */
		public void setCountryId(Long countryId) {
			this.countryId = countryId;
		}

		/**
		 * @return the institutions
		 */
		public ArrayList<RestInst> getInstitutions() {
			return institutions;
		}

		/**
		 * @param insts the insts to set
		 */
		public void setInstitutions(ArrayList<RestInst> institutions) {
			this.institutions = institutions;
		}

		public class RestInst
		{
			Long institutionId;
			String institutionName;
			
			public RestInst(Long institutionId, String institutionName) 
			{
				this.institutionId=institutionId;
				this.institutionName=institutionName;
			}

			/**
			 * @return the institutionId
			 */
			public Long getInstitutionId() {
				return institutionId;
			}

			/**
			 * @param institutionId the institutionId to set
			 */
			public void setInstitutionId(Long institutionId) {
				this.institutionId = institutionId;
			}

			/**
			 * @return the institutionName
			 */
			public String getInstitutionName() {
				return institutionName;
			}

			/**
			 * @param institutionName the institutionName to set
			 */
			public void setInstitutionName(String institutionName) 
			{
				this.institutionName = institutionName;
			}
			
		}
	}

	/**
	 * @return the countrys
	 */
	public List<RestCountry> getCountrys() {
		return countrys;
	}

	/**
	 * @param countrys the countrys to set
	 */
	public void setCountrys(List<RestCountry> countrys) {
		this.countrys = countrys;
	}

}
