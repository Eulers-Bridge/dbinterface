package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.ticket.TicketDetails;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;


/**
 * @author Yikai Gong
 */

@NodeEntity
public class Ticket extends Likeable {
  private static final Logger LOG = LoggerFactory.getLogger(Ticket.class);

  private String name;
  private String logo;
  private String information;
  private String colour;
  private String code;

  @Relationship(type = DataConstants.HAS_TICKET_LABEL, direction = Relationship.INCOMING)
  private Node election;
  @Relationship(type = DataConstants.HAS_MEMBER_LABEL, direction = Relationship.INCOMING)
  private List<Node> candidates;
  @Relationship(type = DataConstants.SUPPORT_LABEL, direction = Relationship.UNDIRECTED)
  private List<Node> supporters;
  @Relationship(type = DataConstants.HAS_PHOTO_LABEL, direction = Relationship.UNDIRECTED)
  private List<Node> photos;

  public static Ticket fromTicketDetails(TicketDetails ticketDetails) {
    Ticket ticket = null;
    if (ticketDetails != null) {
      if (LOG.isTraceEnabled())
        LOG.trace("fromTicketDetails(" + ticketDetails + ")");
      ticket = new Ticket();
      ticket.setNodeId(ticketDetails.getNodeId());
      ticket.setName(ticketDetails.getName());
      ticket.setLogo(ticketDetails.getLogo());
      ticket.setInformation(ticketDetails.getInformation());
      Election election = new Election();
      election.setNodeId(ticketDetails.getElectionId());
      ticket.setElection(election.toNode());
      ticket.setColour(ticketDetails.getColour());
      ticket.setCode(ticketDetails.getChararcterCode());

      if (LOG.isTraceEnabled()) LOG.trace("ticket " + ticket);
    }
    return ticket;
  }

  public TicketDetails toTicketDetails() {
    TicketDetails ticketDetails = new TicketDetails();
    ticketDetails.setNodeId(getNodeId());
    ticketDetails.setName(getName());
    ticketDetails.setLogo(getLogo());
    ticketDetails.setInformation(getInformation());
    ticketDetails.setColour(getColour());
    ticketDetails.setChararcterCode(getCode());

    ticketDetails.setNumberOfSupporters(getNumberOfSupporters());

    if (election != null)
      ticketDetails.setElectionId(election.getNodeId());

    if (candidates != null && candidates.size() > 0 && candidates.iterator().next() instanceof Candidate)
      ticketDetails.setCandidateNames(toCandidateNames(getCandidates$()));

    if (photos != null && photos.size() > 0 && photos.iterator().next() instanceof Photo)
      ticketDetails.setPhotos(Photo.photosToPhotoDetails(getPhotos$()));

    return ticketDetails;
  }

  static protected Iterable<Long> toCandidateIds(Iterable<Candidate> candidates) {
    LinkedList<Long> candidateIds = new LinkedList<Long>();
    if (candidates != null) {
      for (Candidate candidate : candidates) {
        candidateIds.add(candidate.getNodeId());
      }
    }
    return candidateIds;
  }

  static protected Iterable<String> toCandidateNames(Iterable<Candidate> candidates) {
    LinkedList<String> candidateNames = new LinkedList<>();
    if (candidates != null) {
      for (Candidate candidate : candidates) {
        if (candidate.getUser() instanceof User)
          candidateNames.add(candidate.getUser$().getEmail());
      }
    }
    return candidateNames;
  }

  @Override
  public String toString() {
    String buff = "[ id = " + getNodeId() +
      ", name = " +
      getName() +
      ", logo = " +
      getLogo() +
      ", information = " +
      getInformation() +
      ", candidates = " +
      getCandidates() +
      ", photos = " +
      getPhotos() +
      ", colour = " +
      getColour() +
      " ]";
    String retValue;
    retValue = buff;
    if (LOG.isDebugEnabled()) LOG.debug("toString() = " + retValue);
    return retValue;
  }

  public Ticket() {
    super();
  }

  public Ticket(Long ticketId, String name, String logo, String information,
                List<Node> candidates, Election election, String characterCode) {
    super();
    this.nodeId = ticketId;
    this.name = name;
    this.logo = logo;
    this.information = information;
    this.candidates = candidates;
    this.election = election;
    this.code = characterCode;
  }

  public Ticket(Long nodeId) {
    super(nodeId);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLogo() {
    return logo;
  }

  public void setLogo(String logo) {
    this.logo = logo;
  }

  public String getInformation() {
    return information;
  }

  public void setInformation(String information) {
    this.information = information;
  }

  public String getColour() {
    return colour;
  }

  public void setColour(String colour) {
    this.colour = colour;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public void setCandidates(List<Node> candidates) {
    this.candidates = candidates;
  }

  public List<User> getSupporters$() {
    return castList(supporters, User.class);
  }

  public List<Node> getSupporters() {
    return supporters;
  }

  public void setSupporters(List<Node> supporters) {
    this.supporters = supporters;
  }

  public Integer getNumberOfSupporters() {
    if (supporters == null)
      return 0;
    return supporters.size();
  }


  /**
   * @return the candidate
   */
  public List<Candidate> getCandidates$() {
    return castList(candidates, Candidate.class);
  }

  public List<Node> getCandidates() {
    return candidates;
  }

  /**
   * @return the photos
   */
  public List<Photo> getPhotos$() {
    return castList(photos, Photo.class);
  }

  public List<Node> getPhotos() {
    return photos;
  }

  /**
   * @param photos the photos to set
   */
  public void setPhotos(List<Node> photos) {
    this.photos = photos;
  }

  /**
   * @return the election
   */
  public Election getElection$() {
    return (Election) election;
  }

  public Node getElection() {
    return election;
  }

  /**
   * @param election the election to set
   */
  public void setElection(Node election) {
    this.election = election;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    if (getNodeId() != null) {
      result = prime * result + getNodeId().hashCode();
    } else {
      result = prime * result
        + ((candidates == null) ? 0 : candidates.hashCode());
      result = prime * result
        + ((photos == null) ? 0 : photos.hashCode());
      result = prime * result
        + ((election == null) ? 0 : election.hashCode());
      result = prime * result
        + ((information == null) ? 0 : information.hashCode());
      result = prime * result + ((logo == null) ? 0 : logo.hashCode());
      result = prime * result + ((name == null) ? 0 : name.hashCode());
    }
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Ticket other = (Ticket) obj;
    if (nodeId != null) {
      return nodeId.equals(other.nodeId);
    } else {
      if (other.nodeId != null)
        return false;
      if (candidates == null) {
        if (other.candidates != null) return false;
      } else if (!candidates.equals(other.candidates)) return false;
      if (photos == null) {
        if (other.photos != null) return false;
      } else if (!photos.equals(other.photos)) return false;
      if (election == null) {
        if (other.election != null) return false;
      } else if (!election.equals(other.election)) return false;
      if (information == null) {
        if (other.information != null) return false;
      } else if (!information.equals(other.information)) return false;
      if (logo == null) {
        if (other.logo != null) return false;
      } else if (!logo.equals(other.logo)) return false;
      if (name == null) {
        if (other.name != null) return false;
      } else if (!name.equals(other.name)) return false;
      if (nodeId == null) {
        if (other.nodeId != null) return false;
      } else if (!nodeId.equals(other.nodeId)) return false;
    }
    return true;
  }


}
