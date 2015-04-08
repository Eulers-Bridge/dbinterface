package com.eulersbridge.iEngage.rest.domain;

import com.eulersbridge.iEngage.core.events.candidate.CandidateDetails;
import com.eulersbridge.iEngage.core.events.photo.PhotoDetails;
import com.eulersbridge.iEngage.rest.controller.CandidateController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.Iterator;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author Yikai Gong
 */

public class Candidate extends ResourceSupport
{
    private Long candidateId;
    private String information;
    private String policyStatement;
	private Iterable<PhotoDetails> photos;
    private Long userId;
    private String givenName;
    private String familyName;
    private Long positionId;
    private Long ticketId;
//    private Ticket ticket;

    private static Logger LOG = LoggerFactory.getLogger(Candidate.class);

    public Candidate() {
        if (LOG.isDebugEnabled()) LOG.debug("constructor()");
    }

    public static Candidate fromCandidateDetails(CandidateDetails candidateDetails){
        Candidate candidate = new Candidate();
        String simpleName = Candidate.class.getSimpleName();
        String name = simpleName.substring(0, 1).toLowerCase()
                + simpleName.substring(1);

        candidate.setCandidateId(candidateDetails.getNodeId());
        candidate.setInformation(candidateDetails.getInformation());
        candidate.setPolicyStatement(candidateDetails.getPolicyStatement());
        candidate.setPhotos(candidateDetails.getPhotos());
        candidate.setUserId(candidateDetails.getUserId());
        candidate.setGivenName(candidateDetails.getGivenName());
        candidate.setFamilyName(candidateDetails.getFamilyName());
        candidate.setPositionId(candidateDetails.getPositionId());
        Long ticketId=null;
        if (candidateDetails.getTicketDetails()!=null)
        	ticketId=candidateDetails.getTicketDetails().getNodeId();
    	candidate.setTicketId(ticketId);

        // {!begin selfRel}
        candidate.add(linkTo(CandidateController.class).slash(name)
                .slash(candidate.candidateId).withSelfRel());
        // {!end selfRel}
        // {!begin readAll}
        candidate.add(linkTo(CandidateController.class).slash(name + 's')
                .withRel(RestDomainConstants.READALL_LABEL));
        // {!end readAll}

        return candidate;
    }

    public CandidateDetails toCandidateDetails(){
        CandidateDetails candidateDetails = new CandidateDetails();
        candidateDetails.setNodeId(getCandidateId());
         candidateDetails.setInformation(getInformation());
        candidateDetails.setPolicyStatement(getPolicyStatement());
        candidateDetails.setUserId(getUserId());
        candidateDetails.setPositionId(getPositionId());
        return candidateDetails;
    }

/*    public Ticket getTicket()
    {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
*/
    /**
	 * @return the ticketId
	 */
	public Long getTicketId()
	{
		return ticketId;
	}

	/**
	 * @param ticketId the ticketId to set
	 */
	public void setTicketId(Long ticketId)
	{
		this.ticketId = ticketId;
	}

	public Long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getPolicyStatement() {
        return policyStatement;
    }

    public void setPolicyStatement(String policyStatement) {
        this.policyStatement = policyStatement;
    }

    public Iterable<PhotoDetails> getPhotos()
    {
        return photos;
    }

    public void setPhotos(Iterable<PhotoDetails> pictures) {
        this.photos = pictures;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    /**
	 * @return the givenName
	 */
	public String getGivenName()
	{
		return givenName;
	}

	/**
	 * @param givenName the givenName to set
	 */
	public void setGivenName(String givenName)
	{
		this.givenName = givenName;
	}

	/**
	 * @return the familyName
	 */
	public String getFamilyName()
	{
		return familyName;
	}

	/**
	 * @param familyName the familyName to set
	 */
	public void setFamilyName(String familyName)
	{
		this.familyName = familyName;
	}

	public Long getPositionId()
    {
        return positionId;
    }

    public void setPositionId(Long positionId)
    {
        this.positionId = positionId;
    }
    
	public static Iterator<Candidate> toCandidatesIterator(
			Iterator<CandidateDetails> iter)
	{
		if (null==iter) return null;
		ArrayList <Candidate> elections=new ArrayList<Candidate>();
		while(iter.hasNext())
		{
			CandidateDetails dets=iter.next();
			Candidate thisCandidate=Candidate.fromCandidateDetails(dets);
			Link self = thisCandidate.getLink("self");
			thisCandidate.removeLinks();
			thisCandidate.add(self);
			elections.add(thisCandidate);		
		}
		return elections.iterator();
	}

}
