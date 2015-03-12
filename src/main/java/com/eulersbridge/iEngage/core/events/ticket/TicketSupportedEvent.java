package com.eulersbridge.iEngage.core.events.ticket;

/**
 * @author Yikai Gong
 */

public class TicketSupportedEvent {
    protected Long ticketId;
    protected String userEmail;

    protected boolean entityFound = true;
    protected boolean userFound = true;
    protected boolean result = true;

    public TicketSupportedEvent(Long ticketId, String userEmail, boolean result) {
        this.ticketId = ticketId;
        this.userEmail = userEmail;
        this.result = result;
    }

    public static TicketSupportedEvent entityNotFound(Long ticketId, String userEmail){
        TicketSupportedEvent ticketSupportedEvent = new TicketSupportedEvent(ticketId, userEmail, false);
        ticketSupportedEvent.entityFound = false;
        return ticketSupportedEvent;
    }

    public static TicketSupportedEvent userNotFound(Long ticketId, String userEmail){
        TicketSupportedEvent ticketSupportedEvent = new TicketSupportedEvent(ticketId, userEmail, false);
        ticketSupportedEvent.userFound = false;
        return ticketSupportedEvent;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public boolean isEntityFound() {
        return entityFound;
    }

    public void setEntityFound(boolean entityFound) {
        this.entityFound = entityFound;
    }

    public boolean isUserFound() {
        return userFound;
    }

    public void setUserFound(boolean userFound) {
        this.userFound = userFound;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
