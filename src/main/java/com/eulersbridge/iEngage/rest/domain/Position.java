package com.eulersbridge.iEngage.rest.domain;

import com.eulersbridge.iEngage.core.events.positions.PositionDetails;
import com.eulersbridge.iEngage.rest.controller.PositionController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author Yikai Gong
 */

public class Position extends ResourceSupport {
    private Long positionId;
    private String name;
    private String description;
    private Long electionId;

    private static Logger LOG = LoggerFactory.getLogger(Position.class);

    public Position(){
        if (LOG.isDebugEnabled()) LOG.debug("constructor()");
    }

    public static Position fromPositionDetails(PositionDetails positionDetails){
        Position position = new Position();
        String simpleName = Position.class.getSimpleName();
        String name = simpleName.substring(0, 1).toLowerCase()+simpleName.substring(1);

        position.setPositionId(positionDetails.getPositionId());
        position.setName(positionDetails.getName());
        position.setDescription(positionDetails.getDescription());
        position.setElectionId(positionDetails.getElectionId());

        // {!begin selfRel}
        position.add(linkTo(PositionController.class).slash(name).slash(position.positionId).withSelfRel());
        // {!end selfRel}
        // {!begin previous}
        position.add(linkTo(PositionController.class).slash(name).slash(position.positionId).slash(RestDomainConstants.PREVIOUS).withRel(RestDomainConstants.PREVIOUS_LABEL));
        // {!end previous}
        // {!begin next}
        position.add(linkTo(PositionController.class).slash(name).slash(position.positionId).slash(RestDomainConstants.NEXT).withRel(RestDomainConstants.NEXT_LABEL));
        // {!end next}
        // {!begin readAll}
        position.add(linkTo(PositionController.class).slash(name+'s').withRel(RestDomainConstants.READALL_LABEL));
        // {!end readAll}

        return position;
    }

    public PositionDetails toPositionDetails(){
        PositionDetails positionDetails = new PositionDetails();
        positionDetails.setPositionId(getPositionId());
        positionDetails.setName(getName());
        positionDetails.setDescription(getDescription());
        positionDetails.setElectionId(getElectionId());

        return positionDetails;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getElectionId() {
        return electionId;
    }

    public void setElectionId(Long electionId) {
        electionId = electionId;
    }
}
