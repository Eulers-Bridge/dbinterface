package com.eulersbridge.iEngage.rest.domain;

import com.eulersbridge.iEngage.core.events.ticket.TicketDetails;
import com.eulersbridge.iEngage.rest.controller.TicketController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.ResourceSupport;

import java.util.Set;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;


/**
 * @author Yikai Gong
 */

public class Ticket extends ResourceSupport{
    private Long ticketId;
    private String name;
    private String logo;
    private Set<String> pictures;
    private String information;
    private Iterable<Long> candidateIds;

    private static Logger LOG = LoggerFactory.getLogger(Ticket.class);

    public Ticket() {
        if (LOG.isDebugEnabled()) LOG.debug("constructor()");
    }

    public static Ticket fromTicketDetails(TicketDetails ticketDetails){
        Ticket ticket = new Ticket();
        String simpleName = Ticket.class.getSimpleName();
        String name = simpleName.substring(0, 1).toLowerCase()
                + simpleName.substring(1);

        ticket.setTicketId(ticketDetails.getNodeId());
        ticket.setName(ticketDetails.getName());
        ticket.setLogo(ticketDetails.getLogo());
        ticket.setPictures(ticketDetails.getPictures());
        ticket.setInformation(ticketDetails.getInformation());
        ticket.setCandidateIds(ticketDetails.getCandidateIds());

        // {!begin selfRel}
        ticket.add(linkTo(TicketController.class).slash(name)
                .slash(ticket.ticketId).withSelfRel());
        // {!end selfRel}
        // {!begin readAll}
        ticket.add(linkTo(TicketController.class).slash(name + 's')
                .withRel(RestDomainConstants.READALL_LABEL));
        // {!end readAll}
        return ticket;
    }

    public TicketDetails toTicketDetails(){
        TicketDetails ticketDetails = new TicketDetails();
        ticketDetails.setNodeId(getTicketId());
        ticketDetails.setName(getName());
        ticketDetails.setLogo(getLogo());
        ticketDetails.setPictures(getPictures());
        ticketDetails.setInformation(getInformation());
        return ticketDetails;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
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

    public Set<String> getPictures() {
        return pictures;
    }

    public void setPictures(Set<String> pictures) {
        this.pictures = pictures;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    /**
	 * @return the candidateIds
	 */
	public Iterable<Long> getCandidateIds()
	{
		return candidateIds;
	}

	/**
	 * @param candidateIds the candidateIds to set
	 */
	public void setCandidateIds(Iterable<Long> candidateIds)
	{
		this.candidateIds = candidateIds;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "Ticket [ticketId=" + ticketId + ", name=" + name + ", logo="
				+ logo + ", pictures=" + pictures + ", information="
				+ information + ", candidateIds=" + candidateIds + "]";
	}
}
