package com.eulersbridge.iEngage.database.domain;

import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@NodeEntity
public class Country 
{
	@GraphId Long nodeId;
	private String countryName;
	@RelatedTo(type = "HAS_INSTITUTIONS", direction=Direction.OUTGOING)
	private
	Set<Institution>  institutions; 

    private static Logger LOG = LoggerFactory.getLogger(Country.class);

    public Country(String countryName)
	{
    	if (LOG.isDebugEnabled()) LOG.debug("Constructor("+countryName+')');
		this.countryName=countryName;
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
}
