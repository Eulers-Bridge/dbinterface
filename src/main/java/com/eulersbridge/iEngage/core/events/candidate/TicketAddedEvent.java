package com.eulersbridge.iEngage.core.events.candidate;

/**
 * @author Yikai Gong
 */

public class TicketAddedEvent {
    private Long candidateId;
    private Long ticketId;
    private boolean candidateFound = true;
    private boolean TicketFound = true;
    private boolean result = true;

    public TicketAddedEvent(Long candidateId, Long ticketId) {
        this.candidateId = candidateId;
        this.ticketId = ticketId;
    }

    public static TicketAddedEvent candidateNotFound(Long candidateId, Long ticketId){
        TicketAddedEvent ticketAddedEvent = new TicketAddedEvent(candidateId, ticketId);
        ticketAddedEvent.setCandidateFound(false);
        ticketAddedEvent.setResult(false);
        return ticketAddedEvent;
    }

    public static TicketAddedEvent ticketNotFound(Long candidateId, Long ticketId){
        TicketAddedEvent ticketAddedEvent = new TicketAddedEvent(candidateId, ticketId);
        ticketAddedEvent.setTicketFound(false);
        ticketAddedEvent.setResult(false);
        return ticketAddedEvent;
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
