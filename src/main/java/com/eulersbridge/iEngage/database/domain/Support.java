package com.eulersbridge.iEngage.database.domain;


import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

import java.util.Calendar;

/**
 * @author Yikai Gong
 */

@RelationshipEntity(type = DatabaseDomainConstants.SUPPORT_LABEL)
public class Support {
  @GraphId
  private Long id;
  @StartNode
  private User supporter;
  @EndNode
  private Ticket ticket;
  private Long timeStamp;

  public Support() {
  }

  public Support(User supporter, Ticket ticket) {
    this.supporter = supporter;
    this.ticket = ticket;
    this.timeStamp = Calendar.getInstance().getTimeInMillis();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public User getSupporter() {
    return supporter;
  }

  public void setSupporter(User supporter) {
    this.supporter = supporter;
  }

  public Ticket getTicket() {
    return ticket;
  }

  public void setTicket(Ticket ticket) {
    this.ticket = ticket;
  }

  public Long getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(Long timeStamp) {
    this.timeStamp = timeStamp;
  }
}
