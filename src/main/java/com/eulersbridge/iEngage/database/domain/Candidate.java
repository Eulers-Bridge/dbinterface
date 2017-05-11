package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.candidate.CandidateDetails;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * @author Yikai Gong
 */

@NodeEntity
public class Candidate extends Likeable {
  private static final Logger LOG = LoggerFactory.getLogger(Candidate.class);

  private String information;
  private String policyStatement;

  @Relationship(type = DataConstants.IS_CANDIDATE_LABEL, direction = Relationship.INCOMING)
  private Node user;
  @Relationship(type = DataConstants.HAS_CANDIDATE_LABEL, direction = Relationship.OUTGOING)
  private Node position;
  @Relationship(type = DataConstants.IS_ON_TICKET_LABEL, direction = Relationship.OUTGOING)
  private Node ticket;
  @Relationship(type = DataConstants.HAS_PHOTO_LABEL, direction = Relationship.OUTGOING)
  private List<Node> photos;


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

    if (LOG.isTraceEnabled()) LOG.trace("candidate " + candidate);
    return candidate;
  }

  public CandidateDetails toCandidateDetails() {
    CandidateDetails candidateDetails = new CandidateDetails();
    candidateDetails.setNodeId(getNodeId());
    candidateDetails.setInformation(getInformation());
    candidateDetails.setPolicyStatement(getPolicyStatement());

    if (user == null) {
      candidateDetails.setUserId(null);
      candidateDetails.setFamilyName(null);
      candidateDetails.setGivenName(null);
      candidateDetails.setEmail(null);
    } else if (user instanceof User) {
      candidateDetails.setUserId(getUser$().getNodeId());
      candidateDetails.setFamilyName(getUser$().getFamilyName());
      candidateDetails.setGivenName(getUser$().getGivenName());
      candidateDetails.setEmail(getUser$().getEmail());
    } else {
      candidateDetails.setUserId(user.nodeId);
    }

    if (position == null)
      candidateDetails.setPositionId(null);
    else
      candidateDetails.setPositionId(position.nodeId);

    if (ticket == null)
      candidateDetails.setTicketDetails(null);
    else if (ticket instanceof Ticket)
      candidateDetails.setTicketDetails(getTicket$().toTicketDetails());
    else {
      Ticket t = new Ticket(this.ticket.nodeId);
      candidateDetails.setTicketDetails(t.toTicketDetails());
    }

    if (photos != null) {
      if (photos.isEmpty()) {
        candidateDetails.setPhotos(new HashSet<>());
      } else if (photos.get(0) instanceof Photo) {
        candidateDetails.setPhotos(Photo.photosToPhotoDetails(getPhotos$()));
      } else {
        Set<Photo> photos = new HashSet<>();
        photos.forEach(node-> photos.add(new Photo(node.nodeId)));
        candidateDetails.setPhotos(Photo.photosToPhotoDetails(photos));
      }
    }


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
  public User getUser$() {
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
  public Position getPosition$() {
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

  public Ticket getTicket$() {
    return (Ticket) ticket;
  }

  public void setTicket(Node ticket) {
    this.ticket = ticket;
  }

  public List<Photo> getPhotos$() {
    return castList(photos, Photo.class);
  }

  public void setPhotos(List<Node> picture) {
    this.photos = picture;
  }

  public Node getUser() {
    return user;
  }

  public Node getPosition() {
    return position;
  }

  public Node getTicket() {
    return ticket;
  }

  public List<Node> getPhotos() {
    return photos;
  }

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
    if (photos != null) {
      this.photos = toNodeList(photos);
    }
  }
}
