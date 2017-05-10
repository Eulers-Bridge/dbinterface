package com.eulersbridge.iEngage.database.domain;

import org.springframework.data.neo4j.annotation.QueryResult;

import java.util.Set;

@QueryResult
public class GeneralInfoData {
  String countryName;
  Long  countryId;
  Set<Long> institutionIds;
  Set<String> institutionNames;

  public GeneralInfoData() {
  }

  public String getCountryName() {
    return countryName;
  }

  public void setCountryName(String countryName) {
    this.countryName = countryName;
  }

  public Long getCountryId() {
    return countryId;
  }

  public void setCountryId(Long countryId) {
    this.countryId = countryId;
  }

  public Set<Long> getInstitutionIds() {
    return institutionIds;
  }

  public void setInstitutionIds(Set<Long> institutionIds) {
    this.institutionIds = institutionIds;
  }

  public Set<String> getInstitutionNames() {
    return institutionNames;
  }

  public void setInstitutionNames(Set<String> institutionNames) {
    this.institutionNames = institutionNames;
  }
}