package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.countrys.CountryDetails;
import com.eulersbridge.iEngage.core.events.institutions.InstitutionDetails;

import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NodeEntity
public class Country extends Node {
  private static final Logger LOG = LoggerFactory.getLogger(Country.class);

  @NotNull
  @NotBlank
  @Email
  @Index(unique = true)
  private String countryName;
  @Relationship(type = DataConstants.INSTITUTIONS_LABEL, direction = Relationship.INCOMING)
  private List<Node> institutions;

  public Country() {
    if (LOG.isDebugEnabled()) LOG.debug("Constructor()");
  }

  public Country(String countryName) {
    this.countryName = countryName;
  }

  public String getCountryName() {
    return countryName;
  }

  public void setCountryName(String countryName) {
    this.countryName = countryName;
  }

  /**
   * @return the institutions
   */
  public List<Institution> getInstitutions$() {
    return castList(institutions, Institution.class);
  }

  public List<Node> getInstitutions() {
    return institutions;
  }

  /**
   * @param institutions the institutions to set
   */
  public void setInstitutions(List<Node> institutions) {
    this.institutions = institutions;
  }

  @Override
  public String toString() {
    return "[ nodeId = " + getNodeId() +
      ", name = " +
      getCountryName() +
      " ]";
  }

  public CountryDetails toCountryDetails() {
    CountryDetails details = new CountryDetails(getNodeId());
    details.setCountryName(countryName);

    Set<InstitutionDetails> institutionDetails = new HashSet<>();
    if (institutions != null) {
      institutions.forEach(institution -> {
        if (institution instanceof Institution)
          institutionDetails.add(((Institution) institution).toInstDetails());
        else
          institutionDetails.add(new InstitutionDetails(institution.getNodeId()));
      });
      details.setInstitutions(institutionDetails);
    }

    return details;
  }

  public static Country fromCountryDetails(CountryDetails countryDetails) {
    if (LOG.isTraceEnabled()) LOG.trace("fromCountryDetails()");
    if (LOG.isTraceEnabled()) LOG.trace("countryDetails " + countryDetails);
    Country country = new Country();
    country.setNodeId(countryDetails.getCountryId());
    country.setCountryName(countryDetails.getCountryName());
    if (LOG.isTraceEnabled()) LOG.trace("country " + country);

    return country;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    if (nodeId != null) {
      result = prime * result + nodeId.hashCode();
    } else {
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
    if (nodeId != null) {
      return nodeId.equals(other.nodeId);
    } else {
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
