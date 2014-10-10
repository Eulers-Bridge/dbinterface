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
	@RelatedTo(type = DatabaseDomainConstants.INSTITUTIONS_LABEL, direction=Direction.BOTH)
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
	  
	public void setNodeId(Long id) 
	{
		this.nodeId=id;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		if (nodeId!=null)
		{
			result = prime * result + nodeId.hashCode();
		}
		else
		{
			result = prime * result
					+ ((countryName == null) ? 0 : countryName.hashCode());
			result = prime * result
					+ ((institutions == null) ? 0 : institutions.hashCode());
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Country other = (Country) obj;
		if (nodeId != null) 
		{
			if (nodeId.equals(other.nodeId))
				return true;
			else return false;
		} 
		else
		{
			if (other.nodeId != null)
				return false;
			if (countryName == null) {
				if (other.countryName != null)
					return false;
			} else if (!countryName.equals(other.countryName))
				return false;
			if (institutions == null) {
				if (other.institutions != null)
					return false;
			} else if (!institutions.equals(other.institutions))
				return false;
		}
		return true;
	}
}
