package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.institutions.InstitutionDetails;
import com.eulersbridge.iEngage.rest.domain.InstitutionDomain;
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

  public Institution(String name, String campus, String state, Node country) {
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

  public List<User> getStudents$() {
    return castList(students, User.class);
  }

  public List<Node> getStudents() {
    return students;
  }

  public void setStudents(List<Node> students) {
    this.students = students;
  }

  public NewsFeed getNewsFeed$() {
    return (NewsFeed) newsFeed;
  }

  public Node getNewsFeed() {
    return newsFeed;
  }

  public void setNewsFeed(Node newsFeed) {
    this.newsFeed = newsFeed;
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

  public static Institution fromDomain() {
    return null;
  }

  public InstitutionDomain toDomain() {
    InstitutionDomain domain = new InstitutionDomain();
    domain.setInstitutionId(nodeId);
    domain.setCampus(campus);
    domain.setName(name);
    domain.setState(state);
    if (getCountry$() != null)
      domain.setCountry(getCountry$().getCountryName());
    if (getNewsFeed$()!=null)
      domain.setNewsFeedId(getNewsFeed$().getNodeId());
    return domain;
  }
}
