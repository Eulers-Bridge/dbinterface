package com.eulersbridge.iEngage.core.events.ticket;

import com.eulersbridge.iEngage.database.domain.User;

/**
 * @author Yikai Gong
 */

public class SupportTicketEvent {
    private Long ticketId;
    private String emailAddress;

    public SupportTicketEvent(Long ticketId, String emailAddress) {
        this.ticketId = ticketId;
        this.emailAddress = emailAddress;
    }

    public SupportTicketEvent(Long ticketId, User user) {
        this(ticketId, user.getEmail());
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
