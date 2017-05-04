package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.candidate.CandidateDetails;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 * @author Yikai Gong
 */

@NodeEntity
public class Candidate extends Likeable {

  private String information;
  private String policyStatement;
  //    @Fetch
  @Relationship(type = DatabaseDomainConstants.IS_CANDIDATE_LABEL, direction = Relationship.INCOMING)
  private Node user;

  @Relationship(type = DatabaseDomainConstants.HAS_CANDIDATE_LABEL, direction = Relationship.OUTGOING)
  private Node position;

  //    @Fetch
  @Relationship(type = DatabaseDomainConstants.IS_ON_TICKET_LABEL, direction = Relationship.OUTGOING)
  private Node ticket;

  //    @Fetch
  @Relationship(type = DatabaseDomainConstants.HAS_PHOTO_LABEL, direction = Relationship.OUTGOING)
  private List<Node> photos;

  private static Logger LOG = LoggerFactory.getLogger(Candidate.class);

  public Candidate() {
    super();
    if (LOG.isTraceEnabled()) LOG.trace("Constructor");
  }


  public Candidate(Long candidateId, String information,
                   String policyStatement, User user, Position position, Ticket ticket) {
    super();
    this.nodeId = candidateId;
    this.information = information;
    this.policyStatement = policyStatement;
    this.user = user;
    this.position = position;
    this.ticket = ticket;
  }

  public static Candidate fromCandidateDetails(CandidateDetails candidateDetails) {
    if (LOG.isTraceEnabled()) LOG.trace("fromCandidateDetails()");
    Candidate candidate = new Candidate();
    if (LOG.isTraceEnabled()) LOG.trace("candidateDetails " + candidateDetails);
    candidate.setNodeId(candidateDetails.getNodeId());
    candidate.setInformation(candidateDetails.getInformation());
    candidate.setPolicyStatement(candidateDetails.getPolicyStatement());
    User user = new User();
    Long userId = candidateDetails.getUserId();
    String email = candidateDetails.getEmail();
    if (userId != null)
      user.setNodeId(userId);
    else if (email != null)
      user.setEmail(email);
    candidate.setUser(user.toNode());
    Position position = new Position();
    position.setNodeId(candidateDetails.getPositionId());
    candidate.setPosition(position.toNode());

//        candidate.setTicket(Ticket.fromTicketDetails(candidateDetails.getTicketDetails()));

    if (LOG.isTraceEnabled()) LOG.trace("candidate " + candidate);
    return candidate;
  }

  public CandidateDetails toCandidateDetails() {
    if (LOG.isTraceEnabled()) LOG.trace("toCandidateDetails()");
    CandidateDetails candidateDetails = new CandidateDetails();
    if (LOG.isTraceEnabled()) LOG.trace("candidate " + this);
    candidateDetails.setNodeId(getNodeId());
    if (null == getUser()) {
      candidateDetails.setUserId(null);
      candidateDetails.setFamilyName(null);
      candidateDetails.setGivenName(null);
      candidateDetails.setEmail(null);
    } else {
      candidateDetails.setUserId(getUser().getNodeId());
      candidateDetails.setFamilyName(getUser().getFamilyName());
      candidateDetails.setGivenName(getUser().getGivenName());
      candidateDetails.setEmail(getUser().getEmail());
    }
    candidateDetails.setInformation(getInformation());
    candidateDetails.setPolicyStatement(getPolicyStatement());
    if (null == getPosition())
      candidateDetails.setPositionId(null);
    else candidateDetails.setPositionId(getPosition().getNodeId());

    if (null == getTicket())
      candidateDetails.setTicketDetails(null);
    else candidateDetails.setTicketDetails(getTicket().toTicketDetails());

    candidateDetails.setPhotos(Photo.photosToPhotoDetails(getPhotos()));

    if (LOG.isTraceEnabled())
      LOG.trace("candidateDetails; " + candidateDetails);
    return candidateDetails;
  }

  @Override
  public String toString() {
    String retValue = "[ id = " + getNodeId() +
      ", information = " +
      getInformation() +
      ", policyStatement = " +
      getPolicyStatement() +
      ", ticket = " +
      getTicket() +
      ", photos = " +
      getPhotos() +
      " ]";
    if (LOG.isDebugEnabled()) LOG.debug("toString() = " + retValue);
    return retValue;
  }

  public String getInformation() {
    return information;
  }

  public void setInformation(String information) {
    this.information = information;
  }

  /**
   * @return the user
   */
  public User getUser() {
    return (User) user;
  }

  /**
   * @param user the user to set
   */
  public void setUser(Node user) {
    this.user = user;
  }

  /**
   * @return the position
   */
  public Position getPosition() {
    return (Position) position;
  }

  /**
   * @param position the position to set
   */
  public void setPosition(Node position) {
    this.position = position;
  }

  public String getPolicyStatement() {
    return policyStatement;
  }

  public void setPolicyStatement(String policyStatement) {
    this.policyStatement = policyStatement;
  }

  public Ticket getTicket() {
    return (Ticket) ticket;
  }

  public void setTicket(Node ticket) {
    this.ticket = ticket;
  }

  public List<Photo> getPhotos() {
    if (LOG.isDebugEnabled()) LOG.debug("getPhotos() = " + photos);
    return castList(photos, Photo.class);
  }

  public void setPhotos(List<Node> picture) {
    this.photos = picture;
  }

  /*
       * (non-Javadoc)
       *
       * @see java.lang.Object#hashCode()
       */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    if (this.nodeId != null) {
      result = prime * result + nodeId.hashCode();
    } else {
      result = prime * result + ((policyStatement == null) ? 0 : policyStatement.hashCode());
      result = prime * result
        + ((information == null) ? 0 : information.hashCode());
      result = prime * result + ((user == null) ? 0 : user.hashCode());
      result = prime * result + ((position == null) ? 0 : position.hashCode());
      result = prime * result + ((ticket == null) ? 0 : ticket.hashCode());
      result = prime * result + ((photos == null) ? 0 : photos.hashCode());
    }
    return result;
  }

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Candidate other = (Candidate) obj;
    if (nodeId != null) {
      return nodeId.equals(other.nodeId);
    } else {
      if (other.nodeId != null) return false;

      if (policyStatement == null) {
        if (other.policyStatement != null) return false;
      } else if (!policyStatement.equals(other.policyStatement)) return false;

      if (information == null) {
        if (other.information != null) return false;
      } else if (!information.equals(other.information)) return false;

      if (user == null) {
        if (other.user != null) return false;
      } else if (!user.equals(other.user)) return false;

      if (position == null) {
        if (other.position != null) return false;
      } else if (!position.equals(other.position)) return false;

      if (ticket == null) {
        if (other.ticket != null) return false;
      } else if (!ticket.equals(other.ticket)) return false;

      if (photos == null) {
        if (other.photos != null) return false;
      } else if (!photos.equals(other.photos)) return false;
    }
    return true;
  }

  public void prune() {
    this.position = position == null ? null : position.toNode();
    this.user = user == null ? null : user.toNode();
    this.ticket = ticket == null ? null : ticket.toNode();
    if (photos != null){
      this.photos = toNodeList(photos);
    }
  }
}
