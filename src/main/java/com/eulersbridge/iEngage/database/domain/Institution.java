package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.institutions.InstitutionDetails;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@NodeEntity
public class Institution extends Likeable {
  private String name;
  private String campus;
  private String state;
  // @Fetch
  @Relationship(type = DataConstants.INSTITUTIONS_LABEL, direction = Relationship.OUTGOING)
  private Node country;
  @Relationship(type = DataConstants.USERS_LABEL, direction = Relationship.INCOMING)
  private List<Node> students;
  @Relationship(type = DataConstants.HAS_NEWS_FEED_LABEL, direction = Relationship.OUTGOING)
  private Node newsFeed;

  private static Logger LOG = LoggerFactory.getLogger(Institution.class);

  public Institution() {
    if (LOG.isDebugEnabled()) LOG.debug("Constructor");
  }

  public Institution(Long nodeId) {
    this.nodeId = nodeId;
  }

  public Institution(String name, String campus, String state, Country country) {
    if (LOG.isDebugEnabled())
      LOG.debug("Constructor(" + name + ',' + campus + ',' + state + ',' + country + ')');
    this.name = name;
    this.campus = campus;
    this.state = state;
    this.country = country;
  }

  public String getName() {
    if (LOG.isDebugEnabled()) LOG.debug("getName() = " + name);
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCampus() {
    if (LOG.isDebugEnabled()) LOG.debug("getCampus() = " + campus);
    return campus;
  }

  public void setCampus(String campus) {
    this.campus = campus;
  }

  public String getState() {
    if (LOG.isDebugEnabled()) LOG.debug("getState() = " + state);
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public Country getCountry$() {
    if (LOG.isDebugEnabled()) LOG.debug("getCountry() = " + country);
    return (Country) country;
  }

  public Node getCountry() {
    return country;
  }

  public void setCountry(Node country) {
    this.country = country;
  }

  /**
   * @return the students
   */
  public List<User> getStudents$() {
    return castList(students, User.class);
  }

  public List<Node> getStudents() {
    return students;
  }

  /**
   * @param students the students to set
   */
  public void setStudents(List<Node> students) {
    this.students = students;
  }

  /**
   * @return the studentYears
   */
  public NewsFeed getNewsFeed$() {
    return (NewsFeed) newsFeed;
  }

  public Node getNewsFeed() {
    return newsFeed;
  }

  /**
   * @param newsFeed the studentYears to set
   */
  public void setNewsFeed(Node newsFeed) {
    this.newsFeed = newsFeed;
  }

  @Override
  public String toString() {
    String buff = "[ nodeId = " + getNodeId() +
      ", name = " +
      getName() +
      ", campus = " +
      getCampus() +
      ", state = " +
      getState() +
      ", country = " +
      getCountry() +
      ", newsFeed = " +
      getNewsFeed() +
      " ]";
    String retValue;
    retValue = buff;
    if (LOG.isDebugEnabled()) LOG.debug("toString() = " + retValue);
    return retValue;
  }

  public InstitutionDetails toInstDetails() {
    InstitutionDetails details = new InstitutionDetails(nodeId);
    details.setName(name);
    details.setCampus(campus);
    details.setState(state);
//    BeanUtils.copyProperties(this, details);
    if (country != null && country instanceof Country)
      details.setCountryName(getCountry$().getCountryName());

    if (newsFeed != null) {
      details.setNewsFeedId(newsFeed.nodeId);
    }

    return details;
  }

  public static Institution fromInstDetails(InstitutionDetails instDetails) {
    Institution inst = null;
    if (instDetails != null) {
      if (LOG.isTraceEnabled())
        LOG.trace("fromInstDetails(" + instDetails + ")");

      inst = new Institution();
      inst.nodeId = instDetails.getNodeId();
      inst.name = instDetails.getName();
      inst.campus = instDetails.getCampus();
      inst.state = instDetails.getState();
      if (LOG.isTraceEnabled()) LOG.trace("inst " + inst);
    }
    return inst;
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
      result = prime * result + ((campus == null) ? 0 : campus.hashCode());
      result = prime * result + ((country == null) ? 0 : country.hashCode());
      result = prime * result + ((name == null) ? 0 : name.hashCode());
      result = prime * result
        + ((newsFeed == null) ? 0 : newsFeed.hashCode());
      result = prime * result + ((state == null) ? 0 : state.hashCode());
      result = prime * result
        + ((students == null) ? 0 : students.hashCode());
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
    Institution other = (Institution) obj;
    if (nodeId != null) {
      return nodeId.equals(other.nodeId);
    } else {
      if (other.nodeId != null)
        return false;

      if (campus == null) {
        if (other.campus != null)
          return false;
      } else if (!campus.equals(other.campus))
        return false;
      if (country == null) {
        if (other.country != null)
          return false;
      } else if (!country.equals(other.country))
        return false;
      if (name == null) {
        if (other.name != null)
          return false;
      } else if (!name.equals(other.name))
        return false;
      if (newsFeed == null) {
        if (other.newsFeed != null)
          return false;
      } else if (!newsFeed.equals(other.newsFeed))
        return false;
      if (state == null) {
        if (other.state != null)
          return false;
      } else if (!state.equals(other.state))
        return false;
      if (students == null) {
        if (other.students != null)
          return false;
      } else if (!students.equals(other.students))
        return false;
    }
    return true;
  }

}
