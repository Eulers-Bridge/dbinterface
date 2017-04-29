package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.countrys.CountryDetails;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;
import java.util.List;

@NodeEntity
public class Country extends Node{
  @NotNull
  @NotBlank
  @Email
  @Index(unique = true)
  private String countryName;
  @Relationship(type = DatabaseDomainConstants.INSTITUTIONS_LABEL)
  private List<Node> institutions;

  private static Logger LOG = LoggerFactory.getLogger(Country.class);

  public Country() {
    if (LOG.isDebugEnabled()) LOG.debug("Constructor()");
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
  public Iterable<Institution> getInstitutions() {
    return castList(institutions, Institution.class);
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
    if (LOG.isTraceEnabled()) LOG.trace("toCountryDetails(" + this + ")");

    CountryDetails details = new CountryDetails(getNodeId());

    BeanUtils.copyProperties(this, details);
    if (LOG.isTraceEnabled()) LOG.trace("countryDetails " + details);

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
