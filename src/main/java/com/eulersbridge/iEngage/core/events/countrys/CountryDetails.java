package com.eulersbridge.iEngage.core.events.countrys;

import com.eulersbridge.iEngage.core.events.Details;
import com.eulersbridge.iEngage.core.events.institutions.InstitutionDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CountryDetails extends Details {
  private String countryName;
  private Iterable<InstitutionDetails> institutions;

  private static Logger LOG = LoggerFactory.getLogger(CountryDetails.class);

  public CountryDetails(Long id) {
    this.nodeId = id;
  }

  public Long getCountryId() {
    return nodeId;
  }

  public void setCountryId(Long countryId) {
    this.nodeId = countryId;
  }

  public String getCountryName() {
    return countryName;
  }

  public void setCountryName(String countryName) {
    this.countryName = countryName;
  }

  public Iterable<InstitutionDetails> getInstitutions() {
    return institutions;
  }

  public void setInstitutions(Iterable<InstitutionDetails> institutions) {
    this.institutions = institutions;
  }

  @Override
  public String toString() {
    StringBuffer buff = new StringBuffer("[ id = ");
    String retValue;
    buff.append(getCountryId());
    buff.append(", countryName = ");
    buff.append(getCountryName());
    buff.append(" ]");
    retValue = buff.toString();
    if (LOG.isDebugEnabled()) LOG.debug("toString() = " + retValue);
    return retValue;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    if (null == getCountryId()) {
      result = prime * result
        + ((countryName == null) ? 0 : countryName.hashCode());
    } else {
      result = prime * result + nodeId.hashCode();
    }
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object other) {
    if (null == other) return false;
    if (other == this) return true;
    if (!(other instanceof CountryDetails)) return false;
    CountryDetails dets2 = (CountryDetails) other;
    if (dets2.getCountryId() != null) {
      if (dets2.getCountryId().equals(getCountryId()))
        return true;
    } else {
      if ((null == getCountryId()) && (getCountryName().equals(dets2.getCountryName())))
        return true;
    }
    return false;
  }
}
