package com.eulersbridge.iEngage.database.domain;

import java.util.Set;

import org.neo4j.graphdb.Direction;
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

	public Long getNodeId() {
		return nodeId;
	}

	public String getCountryName() {
		return countryName;
	}
	
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
}
