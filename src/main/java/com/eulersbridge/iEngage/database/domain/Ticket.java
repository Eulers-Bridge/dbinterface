package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.ticket.TicketDetails;

import org.neo4j.graphdb.Direction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.Iterator;
import java.util.LinkedList;


/**
 * @author Yikai Gong
 */

@NodeEntity
public class Ticket {
    @GraphId
    private Long ticketId;
    private String name;
    private String logo;
    private String information;
    @RelatedTo(type = DatabaseDomainConstants.HAS_MEMBER_LABEL, direction = Direction.INCOMING)
    private Iterable <Candidate> candidates;

    private static Logger LOG = LoggerFactory.getLogger(Ticket.class);

    public static Ticket fromTicketDetails(TicketDetails ticketDetails){
        if (LOG.isTraceEnabled()) LOG.trace("fromTicketDetails()");
        Ticket ticket = new Ticket();
        if (LOG.isTraceEnabled()) LOG.trace("ticketDetails "+ticketDetails);
        ticket.setTicketId(ticketDetails.getNodeId());
        ticket.setName(ticketDetails.getName());
        ticket.setLogo(ticketDetails.getLogo());
        ticket.setInformation(ticketDetails.getInformation());

        if (LOG.isTraceEnabled()) LOG.trace("ticket "+ticket);
        return ticket;
    }

    public TicketDetails toTicketDetails(){
        if (LOG.isTraceEnabled()) LOG.trace("toTicketDetails()");
        TicketDetails ticketDetails = new TicketDetails();
        if (LOG.isTraceEnabled()) LOG.trace("ticket "+this);
        ticketDetails.setNodeId(getTicketId());
        ticketDetails.setName(getName());
        ticketDetails.setLogo(getLogo());
        ticketDetails.setInformation(getInformation());
        
        ticketDetails.setCandidateIds(toCandidateIds(candidates));


        if (LOG.isTraceEnabled()) LOG.trace("ticketDetails; "+ ticketDetails);
        return ticketDetails;
    }

    static private Iterable<Long> toCandidateIds(Iterable<Candidate> candidates)
	{
    	LinkedList<Long> candidateIds=new LinkedList<Long>();
    	Iterator <Candidate> iter=candidates.iterator();
    	while (iter.hasNext())
    	{
    		Candidate candidate=iter.next();
    		candidateIds.add(candidate.getNodeId());
    	}
		return candidateIds;
	}

	@Override
    public String toString() {
        StringBuffer buff = new StringBuffer("[ id = ");
        String retValue;
        buff.append(getTicketId());
        buff.append(", name = ");
        buff.append(getName());
        buff.append(", logo = ");
        buff.append(getLogo());
        buff.append(", information = ");
        buff.append(getInformation());
        buff.append(", candidates = ");
        buff.append(getCandidates());
        buff.append(" ]");
        retValue = buff.toString();
        if (LOG.isDebugEnabled()) LOG.debug("toString() = "+retValue);
        return retValue;
    }

    public Ticket()
    {
		super();
        if (LOG.isTraceEnabled()) LOG.trace("Constructor");
    }

    /**
	 * @param ticketId
	 * @param name
	 * @param logo
	 * @param information
	 * @param candidates
	 */
	public Ticket(Long ticketId, String name, String logo, String information,
			Iterable<Candidate> candidates)
	{
		super();
        if (LOG.isTraceEnabled()) LOG.trace("Constructor");
		this.ticketId = ticketId;
		this.name = name;
		this.logo = logo;
		this.information = information;
		this.candidates = candidates;
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

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

	/**
	 * @return the candidate
	 */
	public Iterable<Candidate> getCandidates()
	{
		return candidates;
	}

	/**
	 * @param candidate the candidate to set
	 */
	public void setCandidate(Iterable<Candidate> candidates)
	{
		this.candidates = candidates;
	}
}
