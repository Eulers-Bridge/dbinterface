package com.eulersbridge.iEngage.core.events.candidate;

/**
 * @author Yikai Gong
 */

public class TicketRemovedEvent {
    private Long candidateId;
    private Long ticketId;
    private boolean candidateFound = true;
    private boolean TicketFound = true;
    private boolean result = true;

    public TicketRemovedEvent(Long candidateId, Long ticketId) {
        this.candidateId = candidateId;
        this.ticketId = ticketId;
    }

    public static TicketRemovedEvent candidateNotFound(Long candidateId, Long ticketId){
        TicketRemovedEvent ticketRemovedEvent = new TicketRemovedEvent(candidateId, ticketId);
        ticketRemovedEvent.setCandidateFound(false);
        return ticketRemovedEvent;
    }

    public static TicketRemovedEvent ticketNotFound(Long candidateId, Long ticketId){
        TicketRemovedEvent ticketRemovedEvent = new TicketRemovedEvent(candidateId, ticketId);
        ticketRemovedEvent.setTicketFound(false);
        return ticketRemovedEvent;
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

    public boolean isCandidateFound() {
        return candidateFound;
    }

    public void setCandidateFound(boolean candidateFound) {
        this.candidateFound = candidateFound;
    }

    public boolean isTicketFound() {
        return TicketFound;
    }

    public void setTicketFound(boolean ticketFound) {
        TicketFound = ticketFound;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
