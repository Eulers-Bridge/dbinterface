package com.eulersbridge.iEngage.database.domain;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.neo4j.graphdb.Direction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import com.eulersbridge.iEngage.core.events.countrys.CountryDetails;

@NodeEntity
public class Country 
{
	@GraphId Long nodeId;
	@NotNull @NotBlank @Email@Indexed(unique=true) private String countryName;
	@RelatedTo(type = "HAS_INSTITUTIONS", direction=Direction.BOTH)
	private
	Iterable<Institution>  institutions; 

    private static Logger LOG = LoggerFactory.getLogger(Country.class);

    public Country()
	{
    	if (LOG.isDebugEnabled()) LOG.debug("Constructor()");
	}
	
	public Long getNodeId() 
	{
		return nodeId;
	}

	public String getCountryName() {
		return countryName;
	}
	
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	
	@Override
	public String toString()
	{
		StringBuffer buff=new StringBuffer("[ nodeId = ");
		String retValue;
		buff.append(getNodeId());
		buff.append(", name = ");
		buff.append(getCountryName());
		buff.append(" ]");
		retValue=buff.toString();
		if (LOG.isDebugEnabled()) LOG.debug("toString() = "+retValue);
		return retValue;
	}
	
	public CountryDetails toCountryDetails() 
	{
	    if (LOG.isTraceEnabled()) LOG.trace("toCountryDetails()");
	    
	    CountryDetails details = new CountryDetails(getNodeId());
	    if (LOG.isTraceEnabled()) LOG.trace("country "+this);

	    BeanUtils.copyProperties(this, details);
	    if (LOG.isTraceEnabled()) LOG.trace("countryDetails "+details);

	    return details;
	}

	public static Country fromCountryDetails(CountryDetails countryDetails) 
	{
		    if (LOG.isTraceEnabled()) LOG.trace("fromCountryDetails()");

		    if (LOG.isTraceEnabled()) LOG.trace("countryDetails "+countryDetails);
		    Country country = new Country();
		    country.setNodeId(countryDetails.getCountryId());
		    country.setCountryName(countryDetails.getCountryName());
		    if (LOG.isTraceEnabled()) LOG.trace("country "+country);

		    return country;
	}
	  
	@Override
	public boolean equals(Object other)
	{
		if (null == other) return false;
		if (other == this) return true;
		if (!(other instanceof Country)) return false;
		Country country2=(Country) other;

		if ((nodeId!=null)&&(nodeId.equals(country2.nodeId))) return true;
		return false;
	}

	public void setNodeId(Long id) 
	{
		this.nodeId=id;
	}
}
