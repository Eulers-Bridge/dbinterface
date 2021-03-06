package com.eulersbridge.iEngage.rest.domain;

import com.eulersbridge.iEngage.core.events.institutions.InstitutionDetails;
import com.eulersbridge.iEngage.rest.controller.InstitutionController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class InstitutionDomain extends ResourceSupport
{
	Long institutionId;
	Long newsFeedId;
	String name;
	String state;
	String campus;
	String country;

	public Long getNewsFeedId()
	{
		return newsFeedId;
	}

	public void setInstitutionId(Long institutionId) {
		this.institutionId = institutionId;
	}

	public void setNewsFeedId(Long newsFeedId) {
		this.newsFeedId = newsFeedId;
	}

	private static Logger LOG = LoggerFactory.getLogger(InstitutionDomain.class);

	public InstitutionDomain()
	{
	}

	public InstitutionDomain(Long id)
	{
		this.institutionId = id;
	}

	public Long getInstitutionId()
	{
		return institutionId;
	}

	public void setId(Long id)
	{
		this.institutionId = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setCampus(String campus)
	{
		this.campus = campus;
	}

	public String getCampus()
	{
		return campus;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public String getCountry()
	{
		return country;
	}

	public void setCountry(String country)
	{
		this.country = country;
	}

	public InstitutionDomain(Long id, String name)
	{
		this.institutionId = id;
		this.name = name;
	}

	public InstitutionDetails toInstitutionDetails()
	{
		InstitutionDetails details = new InstitutionDetails(institutionId);

		details.setName(getName());
		details.setCampus(getCampus());
		details.setState(getState());
		details.setCountryName(getCountry());

		return details;
	}

	// {!begin fromOrderDetails}
	public static InstitutionDomain fromInstDetails(InstitutionDetails readInstitute)
	{
		InstitutionDomain inst = new InstitutionDomain();

		inst.institutionId = readInstitute.getNodeId();
		inst.name = readInstitute.getName();
		inst.campus = readInstitute.getCampus();
		inst.state = readInstitute.getState();
		inst.country = readInstitute.getCountryName();
		inst.newsFeedId = readInstitute.getNewsFeedId();

		String simpleName = InstitutionDomain.class.getSimpleName();
		String name = simpleName.substring(0, 1).toLowerCase()
				+ simpleName.substring(1);

		// TODOCUMENT. Adding the library, the above extends ResourceSupport and
		// this section is all that is actually needed in our model to add
		// hateoas support.

		// Much of the rest of the framework is helping deal with the blending
		// of domains that happens in many spring apps
		// We have explicitly avoided that.
		// {!begin selfRel}
		inst.add(linkTo(InstitutionController.class).slash(name)
				.slash(inst.institutionId).withSelfRel());
		// {!end selfRel}

		return inst;
	}

	public String toString()
	{
		StringBuffer buff = new StringBuffer("[ id = ");
		String retValue;
		buff.append(getId());
		buff.append(", newsFeedIds = ");
		buff.append(getNewsFeedId());
		buff.append(", name = ");
		buff.append(getName());
		buff.append(", campus = ");
		buff.append(getCampus());
		buff.append(", state = ");
		buff.append(getState());
		buff.append(", country = ");
		buff.append(getCountry());
		buff.append(", yearOfBirth = ");
		buff.append(" ]");
		retValue = buff.toString();
		if (LOG.isDebugEnabled()) LOG.debug("toString() = " + retValue);
		return retValue;
	}

}
