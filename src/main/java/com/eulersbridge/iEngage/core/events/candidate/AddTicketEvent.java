package com.eulersbridge.iEngage.core.events.candidate;

/**
 * @author Yikai Gong
 */

public class AddTicketEvent {
    private Long candidateId;
    private Long ticketId;

    public AddTicketEvent(Long candidateId, Long ticketId) {
        this.candidateId = candidateId;
        this.ticketId = ticketId;
    }

    public Long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }
}
