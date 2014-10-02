package com.eulersbridge.iEngage.rest.domain;

import com.eulersbridge.iEngage.core.events.elections.ElectionDetails;
import com.eulersbridge.iEngage.rest.controller.ElectionController;

import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Yikai Gong
 */

public class Election extends ResourceSupport{
    private Long electionId;
    private String title;
    private Long start;
    private Long end;
    private Long startVoting;
    private Long endVoting;

    private static Logger LOG = LoggerFactory.getLogger(Election.class);

    public static Election fromElectionDetails(ElectionDetails electionDetails){
        Election election = new Election();
        String simpleName=Election.class.getSimpleName();
        String name = simpleName.substring(0, 1).toLowerCase()+simpleName.substring(1);
        election.setElectionId(electionDetails.getElectionId());
        election.setTitle(electionDetails.getTitle());
        election.setStart(electionDetails.getStart());
        election.setEnd(electionDetails.getEnd());
        election.setStartVoting(electionDetails.getStartVoting());
        election.setEndVoting(electionDetails.getEndVoting());

	    // {!begin selfRel}
        election.add(linkTo(ElectionController.class).slash(name).slash(election.electionId).withSelfRel());
	    // {!end selfRel}
	    // {!begin previous}
        election.add(linkTo(ElectionController.class).slash(name).slash(election.electionId).slash(RestDomainConstants.PREVIOUS).withRel(RestDomainConstants.PREVIOUS_LABEL));
	    // {!end previous}
	    // {!begin next}
        election.add(linkTo(ElectionController.class).slash(name).slash(election.electionId).slash(RestDomainConstants.NEXT).withRel(RestDomainConstants.NEXT_LABEL));
	    // {!end next}
	    // {!begin readAll}
	    election.add(linkTo(ElectionController.class).slash(name+'s').withRel(RestDomainConstants.READALL_LABEL));
	    // {!end readAll}

        return election;
    }

    public ElectionDetails toElectionDetails(){
        ElectionDetails electionDetails = new ElectionDetails();
        electionDetails.setElectionId(this.getElectionId());
        electionDetails.setTitle(this.getTitle());
        electionDetails.setStart(this.getStart());
        electionDetails.setEnd(this.getEnd());
        electionDetails.setStartVoting(this.getStartVoting());
        electionDetails.setEndVoting(this.getEndVoting());
        if (LOG.isTraceEnabled()) LOG.trace("electionDetails "+electionDetails);
        return electionDetails;
    }

    /**
     * @return the electionId
     */
    public Long getElectionId(){
        return this.electionId;
    }

    /**
     * @return the title
     */
    public String getTitle(){
        return this.title;
    }

    /**
     * @return the start
     */
    public Long getStart(){
        return this.start;
    }

    /**
     * @return the end
     */
    public Long getEnd(){
        return this.end;
    }

    /**
     * @return the startVoting
     */
    public Long getStartVoting(){
        return this.startVoting;
    }

    /**
     * @return the endVoting
     */
    public Long getEndVoting(){
        return this.endVoting;
    }

    /**
     * @param electionId the electionId to set
     */
    public void setElectionId(Long electionId){
        this.electionId = electionId;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title){
        this.title = title;
    }

    /**
     * @param start the start to set
     */
    public void setStart(Long start){
        this.start = start;
    }

    /**
     * @param end the end to set
     */
    public void setEnd(Long end){
        this.end = end;
    }

    /**
     * @param startVoting the startVoting to set
     */
    public void setStartVoting(Long startVoting){
        this.startVoting = startVoting;
    }

    /**
     * @param endVoting the endVoting to set
     */
    public void setEndVoting(Long endVoting){
        this.endVoting = endVoting;
    }
}
