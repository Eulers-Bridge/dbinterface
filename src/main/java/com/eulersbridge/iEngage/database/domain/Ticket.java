package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.ticket.TicketDetails;

import org.neo4j.graphdb.Direction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;


/**
 * @author Yikai Gong
 */

@NodeEntity
public class Ticket extends Likeable
{
    @GraphId
    private Long nodeId;
    private String name;
    private String logo;
    private String information;
    @RelatedTo(type = DatabaseDomainConstants.HAS_MEMBER_LABEL, direction = Direction.INCOMING)
    private Iterable <Candidate> candidates;
    @RelatedTo(type = DatabaseDomainConstants.HAS_TICKET_LABEL, direction = Direction.INCOMING)
    private Election election;
    private String colour;
    private String characterCode;
    @RelatedTo(type = DatabaseDomainConstants.SUPPORT, direction = Direction.INCOMING)
    private Collection<User> supporters;

    private static Logger LOG = LoggerFactory.getLogger(Ticket.class);

    public static Ticket fromTicketDetails(TicketDetails ticketDetails)
    {
        Ticket ticket = null;
    	if (ticketDetails!=null)
    	{
	        if (LOG.isTraceEnabled()) LOG.trace("fromTicketDetails("+ticketDetails+")");
	        ticket = new Ticket();
	        ticket.setNodeId(ticketDetails.getNodeId());
	        ticket.setName(ticketDetails.getName());
	        ticket.setLogo(ticketDetails.getLogo());
	        ticket.setInformation(ticketDetails.getInformation());
	        Election election=new Election();
	        election.setNodeId(ticketDetails.getElectionId());
	        ticket.setElection(election);
            ticket.setColour(ticketDetails.getColour());
            ticket.setCharacterCode(ticketDetails.getChararcterCode());
	
	        if (LOG.isTraceEnabled()) LOG.trace("ticket "+ticket);
    	}
        return ticket;
    }

    public TicketDetails toTicketDetails(){
        if (LOG.isTraceEnabled()) LOG.trace("toTicketDetails("+this+")");
        TicketDetails ticketDetails = new TicketDetails();
        ticketDetails.setNodeId(getNodeId());
        ticketDetails.setName(getName());
        ticketDetails.setLogo(getLogo());
        ticketDetails.setInformation(getInformation());
        ticketDetails.setElectionId(getElection().getNodeId());
        ticketDetails.setColour(getColour());
        ticketDetails.setChararcterCode(getCharacterCode());
        if(supporters!=null)
            ticketDetails.setNumberOfSupporters(supporters.size());
        else
            ticketDetails.setNumberOfSupporters(0);
        
        ticketDetails.setCandidateNames(toCandidateNames(candidates));


        if (LOG.isTraceEnabled()) LOG.trace("ticketDetails; "+ ticketDetails);
        return ticketDetails;
    }

    static protected Iterable<Long> toCandidateIds(Iterable<Candidate> candidates)
	{
    	LinkedList<Long> candidateIds=new LinkedList<Long>();
    	if (candidates!=null)
    	{
	    	Iterator <Candidate> iter=candidates.iterator();
	    	while (iter.hasNext())
	    	{
	    		Candidate candidate=iter.next();
	    		candidateIds.add(candidate.getNodeId());
	    	}
    	}
		return candidateIds;
	}

    static protected Iterable<String> toCandidateNames(Iterable<Candidate> candidates)
    {
        LinkedList<String> candidateNames=new LinkedList<String>();
        if (candidates!=null)
        {
            Iterator <Candidate> iter=candidates.iterator();
            while (iter.hasNext())
            {
                Candidate candidate=iter.next();
                candidateNames.add(candidate.getUser().getEmail());
            }
        }
        return candidateNames;
    }

	@Override
    public String toString() {
        StringBuffer buff = new StringBuffer("[ id = ");
        String retValue;
        buff.append(getNodeId());
        buff.append(", name = ");
        buff.append(getName());
        buff.append(", logo = ");
        buff.append(getLogo());
        buff.append(", information = ");
        buff.append(getInformation());
        buff.append(", candidates = ");
        buff.append(getCandidates());
        buff.append(", colour = ");
        buff.append(getColour());
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
	 * @param nodeId
	 * @param name
	 * @param logo
	 * @param information
	 * @param candidates
	 * @param election
	 */
	public Ticket(Long ticketId, String name, String logo, String information,
			Iterable<Candidate> candidates, Election election, String characterCode)
	{
		super();
		this.nodeId = ticketId;
		this.name = name;
		this.logo = logo;
		this.information = information;
		this.candidates = candidates;
		this.election = election;
        this.characterCode = characterCode;
	}

	public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long ticketId) {
        this.nodeId = ticketId;
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

    public String getCharacterCode() {
        return characterCode;
    }

    public void setCharacterCode(String characterCode) {
        this.characterCode = characterCode;
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

	/**
	 * @return the election
	 */
	public Election getElection()
	{
		return election;
	}

	/**
	 * @param election the election to set
	 */
	public void setElection(Election election)
	{
		this.election = election;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        if (getNodeId()!=null)
        {
            result = prime * result	+ getNodeId().hashCode();
        }
        else
        {
    		result = prime * result
    				+ ((candidates == null) ? 0 : candidates.hashCode());
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
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Ticket other = (Ticket) obj;
        if (nodeId != null)
        {
            if (nodeId.equals(other.nodeId))
                return true;
            else return false;
        }
        else
        {
            if (other.nodeId != null)
                return false;
			if (candidates == null)
			{
				if (other.candidates != null) return false;
			}
			else if (!candidates.equals(other.candidates)) return false;
			if (election == null)
			{
				if (other.election != null) return false;
			}
			else if (!election.equals(other.election)) return false;
			if (information == null)
			{
				if (other.information != null) return false;
			}
			else if (!information.equals(other.information)) return false;
			if (logo == null)
			{
				if (other.logo != null) return false;
			}
			else if (!logo.equals(other.logo)) return false;
			if (name == null)
			{
				if (other.name != null) return false;
			}
			else if (!name.equals(other.name)) return false;
			if (nodeId == null)
			{
				if (other.nodeId != null) return false;
			}
			else if (!nodeId.equals(other.nodeId)) return false;
        }
		return true;
	}
	
	
}
