package com.eulersbridge.iEngage.rest.controller;

import java.util.Iterator;

import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.elections.*;
import com.eulersbridge.iEngage.core.services.ElectionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eulersbridge.iEngage.rest.domain.Election;

@RestController
@RequestMapping(ControllerConstants.API_PREFIX)
public class ElectionController
{

	@Autowired
	ElectionService electionService;

	public ElectionController()
	{
		if (LOG.isDebugEnabled()) LOG.debug("ElectionController()");
	}

	private static Logger LOG = LoggerFactory
			.getLogger(ElectionController.class);

	// Get
	@RequestMapping(method = RequestMethod.GET, value = ControllerConstants.ELECTION_LABEL
			+ "/{electionId}")
	public @ResponseBody ResponseEntity<Election> findElection(
			@PathVariable Long electionId)
	{
		if (LOG.isInfoEnabled())
			LOG.info(electionId + " attempting to get election. ");
		RequestReadElectionEvent requestReadElectionEvent = new RequestReadElectionEvent(
				electionId);
		ReadEvent readElectionEvent = electionService
				.readElection(requestReadElectionEvent);
		if (readElectionEvent.isEntityFound())
		{
			Election election = Election.fromElectionDetails((ElectionDetails)readElectionEvent.getDetails());
			return new ResponseEntity<Election>(election, HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<Election>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Is passed all the necessary data to read elections from the database. The
	 * request must be a GET with the institutionId presented as the final
	 * portion of the URL.
	 * <p/>
	 * This method will return the elections read from the database.
	 * 
	 * @param institutionId
	 *            the instituitonId of the election objects to be read.
	 * @return the elections.
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET, value = ControllerConstants.ELECTIONS_LABEL
			+ "/{institutionId}")
	public @ResponseBody ResponseEntity<Iterator<Election>> findElections(
			@PathVariable(value = "") Long institutionId,
			@RequestParam(value = "direction", required = false, defaultValue = ControllerConstants.DIRECTION) String direction,
			@RequestParam(value = "page", required = false, defaultValue = ControllerConstants.PAGE_NUMBER) String page,
			@RequestParam(value = "pageSize", required = false, defaultValue = ControllerConstants.PAGE_LENGTH) String pageSize)
	{
		int pageNumber = 0;
		int pageLength = 10;
		pageNumber = Integer.parseInt(page);
		pageLength = Integer.parseInt(pageSize);
		if (LOG.isInfoEnabled())
			LOG.info("Attempting to retrieve elections from institution "
					+ institutionId + '.');

		Direction sortDirection = Direction.DESC;
		if (direction.equalsIgnoreCase("asc")) sortDirection = Direction.ASC;
		ElectionsReadEvent articleEvent = electionService.readElections(
				new ReadElectionsEvent(institutionId), sortDirection,
				pageNumber, pageLength);

		if (!articleEvent.isEntityFound())
		{
			return new ResponseEntity<Iterator<Election>>(HttpStatus.NOT_FOUND);
		}

		Iterator<Election> elections = Election
				.toElectionsIterator(articleEvent.getElections().iterator());

		return new ResponseEntity<Iterator<Election>>(elections, HttpStatus.OK);
	}

	// Create
	@RequestMapping(method = RequestMethod.POST, value = ControllerConstants.ELECTION_LABEL)
	public @ResponseBody ResponseEntity<Election> createElection(
			@RequestBody Election election)
	{
		if (LOG.isInfoEnabled())
			LOG.info("attempting to create election " + election);
		ElectionCreatedEvent electionCreatedEvent = electionService
				.createElection(new CreateElectionEvent(election
						.toElectionDetails()));
		ResponseEntity<Election> response;
		if ((null == electionCreatedEvent)
				|| (null == electionCreatedEvent.getElectionId()))
		{
			response = new ResponseEntity<Election>(HttpStatus.BAD_REQUEST);
		}
		else if (!(electionCreatedEvent.isInstitutionFound()))
		{
			response = new ResponseEntity<Election>(HttpStatus.NOT_FOUND);
		}
		else
		{
			Election result = Election.fromElectionDetails((ElectionDetails)electionCreatedEvent
					.getDetails());
			if (LOG.isDebugEnabled())
				LOG.debug("election" + result.toString());
			response = new ResponseEntity<Election>(result, HttpStatus.CREATED);
		}
		return response;
	}

	// Get Previous
	@RequestMapping(method = RequestMethod.GET, value = ControllerConstants.ELECTION_LABEL
			+ "/{electionId}/previous")
	public @ResponseBody ResponseEntity<Election> findPreviousElection(
			@PathVariable Long electionId)
	{
		if (LOG.isInfoEnabled())
			LOG.info("attempting to get previous election");
		RequestReadElectionEvent requestReadElectionEvent = new RequestReadElectionEvent(
				electionId);
		ReadEvent readElectionEvent = electionService
				.readPreviousElection(requestReadElectionEvent);
		if (readElectionEvent.isEntityFound())
		{
			Election election = Election.fromElectionDetails((ElectionDetails)readElectionEvent.getDetails());
			return new ResponseEntity<Election>(election, HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<Election>(HttpStatus.NOT_FOUND);
		}
	}

	// Get Next
	@RequestMapping(method = RequestMethod.GET, value = ControllerConstants.ELECTION_LABEL
			+ "/{electionId}/next")
	public @ResponseBody ResponseEntity<Election> findNextElection(
			@PathVariable Long electionId)
	{
		if (LOG.isInfoEnabled()) LOG.info("attempting to get next election");
		RequestReadElectionEvent requestReadElectionEvent = new RequestReadElectionEvent(
				electionId);
		ReadEvent readElectionEvent = electionService.readNextElection(requestReadElectionEvent);
		if (readElectionEvent.isEntityFound())
		{
			Election election = Election.fromElectionDetails((ElectionDetails)readElectionEvent.getDetails());
			return new ResponseEntity<Election>(election, HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<Election>(HttpStatus.NOT_FOUND);
		}
	}

	// Delete
	@RequestMapping(method = RequestMethod.DELETE, value = ControllerConstants.ELECTION_LABEL
			+ "/{electionId}")
	public @ResponseBody ResponseEntity<Boolean> deleteElection(
			@PathVariable Long electionId)
	{
		if (LOG.isInfoEnabled())
			LOG.info("Attempting to delete election. " + electionId);
		ResponseEntity<Boolean> response;
		DeletedEvent elecEvent = electionService
				.deleteElection(new DeleteElectionEvent(electionId));
		if (elecEvent.isDeletionCompleted())
			response = new ResponseEntity<Boolean>(
					elecEvent.isDeletionCompleted(), HttpStatus.OK);
		else if (elecEvent.isEntityFound())
			response = new ResponseEntity<Boolean>(
					elecEvent.isDeletionCompleted(), HttpStatus.GONE);
		else response = new ResponseEntity<Boolean>(
				elecEvent.isDeletionCompleted(), HttpStatus.NOT_FOUND);
		return response;
	}

	// Update
	@RequestMapping(method = RequestMethod.PUT, value = ControllerConstants.ELECTION_LABEL
			+ "/{electionId}")
	public @ResponseBody ResponseEntity<Election> updateElection(
			@PathVariable Long electionId, @RequestBody Election election)
	{
		if (LOG.isInfoEnabled())
			LOG.info("Attempting to update election. " + electionId);
		UpdatedEvent electionUpdatedEvent = electionService
				.updateElection(new UpdateElectionEvent(electionId, election
						.toElectionDetails()));
		if ((null != electionUpdatedEvent))
		{
			if (LOG.isDebugEnabled())
				LOG.debug("electionUpdatedEvent - " + electionUpdatedEvent);
			if (electionUpdatedEvent.isEntityFound())
			{
				Election restElection = Election
						.fromElectionDetails((ElectionDetails) electionUpdatedEvent
								.getDetails());
				if (LOG.isDebugEnabled())
					LOG.debug("restElection = " + restElection);
				return new ResponseEntity<Election>(restElection, HttpStatus.OK);
			}
			else
			{
				return new ResponseEntity<Election>(HttpStatus.NOT_FOUND);
			}
		}
		else
		{
			return new ResponseEntity<Election>(HttpStatus.BAD_REQUEST);
		}
	}
}