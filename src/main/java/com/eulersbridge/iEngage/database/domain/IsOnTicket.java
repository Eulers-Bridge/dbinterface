package com.eulersbridge.iEngage.database.domain;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

/**
 * @author Yikai Gong
 */

@RelationshipEntity(type=DatabaseDomainConstants.IS_ON_TICKET_LABEL)
public class IsOnTicket {
    @GraphId private Long id;
    @StartNode private Candidate candidate;
    @EndNode private Ticket ticket;

    public IsOnTicket() {
    }

    public IsOnTicket(Long id, Candidate candidate, Ticket ticket) {
        this.id = id;
        this.candidate = candidate;
        this.ticket = ticket;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
