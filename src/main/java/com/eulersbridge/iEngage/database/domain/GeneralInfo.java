package com.eulersbridge.iEngage.database.domain;

import org.neo4j.ogm.annotation.Property;
import org.springframework.data.neo4j.annotation.QueryResult;

@QueryResult
public interface GeneralInfo {

  @Property(name = "institutionNames")
  Iterable<String> getInstitutionNames();

  @Property(name = "institutionIds")
  Iterable<Long> getInstitutionIds();

  @Property(name = "countryName")
  String getCountryName();

  @Property(name = "countryId")
  Long getCountryId();
}