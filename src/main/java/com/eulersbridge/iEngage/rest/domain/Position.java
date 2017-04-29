package com.eulersbridge.iEngage.rest.domain;

import com.eulersbridge.iEngage.core.events.Details;
import com.eulersbridge.iEngage.core.events.positions.PositionDetails;
import com.eulersbridge.iEngage.rest.controller.PositionController;
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

public class Position extends ResourceSupport
{
	private Long positionId;
	private String name;
	private String description;
	private Long electionId;

	private static Logger LOG = LoggerFactory.getLogger(Position.class);

	public Position()
	{
		if (LOG.isDebugEnabled()) LOG.debug("constructor()");
	}

	public static Position fromPositionDetails(PositionDetails positionDetails)
	{
		Position position = new Position();
		String simpleName = Position.class.getSimpleName();
		String name = simpleName.substring(0, 1).toLowerCase()
				+ simpleName.substring(1);

		position.setPositionId(positionDetails.getNodeId());
		position.setName(positionDetails.getName());
		position.setDescription(positionDetails.getDescription());
		position.setElectionId(positionDetails.getElectionId());

		// {!begin selfRel}
		position.add(linkTo(PositionController.class).slash(name)
				.slash(position.positionId).withSelfRel());
		// {!end selfRel}
		// {!begin previous}
		position.add(linkTo(PositionController.class).slash(name)
				.slash(position.positionId).slash(RestDomainConstants.PREVIOUS)
				.withRel(RestDomainConstants.PREVIOUS_LABEL));
		// {!end previous}
		// {!begin next}
		position.add(linkTo(PositionController.class).slash(name)
				.slash(position.positionId).slash(RestDomainConstants.NEXT)
				.withRel(RestDomainConstants.NEXT_LABEL));
		// {!end next}
		// {!begin readAll}
		position.add(linkTo(PositionController.class).slash(name + 's')
				.withRel(RestDomainConstants.READALL_LABEL));
		// {!end readAll}

		return position;
	}

	public PositionDetails toPositionDetails()
	{
		PositionDetails positionDetails = new PositionDetails();
		positionDetails.setNodeId(getPositionId());
		positionDetails.setName(getName());
		positionDetails.setDescription(getDescription());
		positionDetails.setElectionId(getElectionId());

		return positionDetails;
	}

	public Long getPositionId()
	{
		return positionId;
	}

	public void setPositionId(Long positionId)
	{
		this.positionId = positionId;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public Long getElectionId()
	{
		return electionId;
	}

	public void setElectionId(Long electionId)
	{
		this.electionId = electionId;
	}

	public static Iterator<Position> toPositionsIterator(
			Iterator<? extends Details> iter)
	{
		if (null==iter) return null;
		ArrayList <Position> elections=new ArrayList<Position>();
		while(iter.hasNext())
		{
			PositionDetails dets=(PositionDetails)iter.next();
			Position thisPosition=Position.fromPositionDetails(dets);
			Link self = thisPosition.getLink("self");
			thisPosition.removeLinks();
			thisPosition.add(self);
			elections.add(thisPosition);		
		}
		return elections.iterator();
	}
}
