package com.eulersbridge.iEngage.rest.domain;

import com.eulersbridge.iEngage.core.events.Elections.ElectionDetail;
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

    public static Election fromElectionDetail(ElectionDetail electionDetail){
        Election election = new Election();
        String simpleName=NewsArticle.class.getSimpleName();
        String name=simpleName.substring(0, 1).toLowerCase()+simpleName.substring(1);
        election.setElectionId(electionDetail.getElectionId());
        election.setTitle(electionDetail.getTitle());
        election.setStart(electionDetail.getStart());
        election.setEnd(electionDetail.getEnd());
        election.setStartVoting(electionDetail.getStartVoting());
        election.setEndVoting(electionDetail.getEndVoting());

        election.add(linkTo(ElectionController.class).slash(name).slash(election.electionId).withSelfRel());
        return election;
    }

    public static ElectionDetail toElectionDetail(Election election){
        ElectionDetail electionDetail = new ElectionDetail();
        //TODO
        return electionDetail;
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
