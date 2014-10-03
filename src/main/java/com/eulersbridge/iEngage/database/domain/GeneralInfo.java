package com.eulersbridge.iEngage.database.domain;

import org.springframework.data.neo4j.annotation.QueryResult;
import org.springframework.data.neo4j.annotation.ResultColumn;

@QueryResult()
	public interface GeneralInfo
	{
		@ResultColumn ("institutionNames")
		Iterable<String> getInstitutionNames();
		
		@ResultColumn ("institutionIds")
		Iterable<Long> getInstitutionIds();
		
		@ResultColumn ("countryName")
		String getCountryName();
		
		@ResultColumn ("countryId")
		Long getCountryId();
}