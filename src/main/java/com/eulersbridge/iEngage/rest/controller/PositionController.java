package com.eulersbridge.iEngage.rest.controller;

import java.util.Iterator;

import com.eulersbridge.iEngage.core.events.AllReadEvent;
import com.eulersbridge.iEngage.core.events.CreatedEvent;
import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadAllEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.positions.*;
import com.eulersbridge.iEngage.core.services.PositionService;
import com.eulersbridge.iEngage.rest.domain.Candidate;
import com.eulersbridge.iEngage.rest.domain.FindsParent;
import com.eulersbridge.iEngage.rest.domain.Position;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Yikai Gong
 */

@RestController
@RequestMapping(ControllerConstants.API_PREFIX)
public class PositionController {
    @Autowired
    PositionService positionService;

    private static Logger LOG = LoggerFactory.getLogger(PositionController.class);

    public PositionController() {
    }

    //Create
    @RequestMapping(method = RequestMethod.POST, value = ControllerConstants.POSITION_LABEL)
    public @ResponseBody ResponseEntity<Position>
    createPosition(@RequestBody Position position){
        if (LOG.isInfoEnabled()) LOG.info("attempting to create position "+position);
        CreatePositionEvent createPositionEvent = new CreatePositionEvent(position.toPositionDetails());
        CreatedEvent positionCreatedEvent = positionService.createPosition(createPositionEvent);
        ResponseEntity<Position> response;
        if(null==positionCreatedEvent)
        {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    	else if ((positionCreatedEvent.getClass()==PositionCreatedEvent.class)&&(!((PositionCreatedEvent)positionCreatedEvent).isElectionFound()))
    	{
    		response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    	else if((null==positionCreatedEvent.getNodeId())||(positionCreatedEvent.isFailed()))
        {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else
        {
            Position result = Position.fromPositionDetails((PositionDetails) positionCreatedEvent.getDetails());
            if (LOG.isDebugEnabled()) LOG.debug("position"+result.toString());
            response = new ResponseEntity<Position>(result, HttpStatus.CREATED);
        }
        return response;
    }

    //Get
    @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.POSITION_LABEL + "/{positionId}")
    public @ResponseBody ResponseEntity<Position>
    findPosition(@PathVariable Long positionId)
    {
        if (LOG.isInfoEnabled()) LOG.info(positionId+" attempting to get position. ");
        RequestReadPositionEvent requestReadPositionEvent = new RequestReadPositionEvent(positionId);
        ReadEvent readPositionEvent = positionService.readPosition(requestReadPositionEvent);
        if(readPositionEvent.isEntityFound()){
            Position position = Position.fromPositionDetails((PositionDetails)readPositionEvent.getDetails());
            return new ResponseEntity<Position>(position, HttpStatus.OK);
        }else{
            return new ResponseEntity<Position>(HttpStatus.NOT_FOUND);
        }
    }

	/**
	 * Is passed all the necessary data to read positions from the database. The
	 * request must be a GET with the electionId presented as the final
	 * portion of the URL.
	 * <p/>
	 * This method will return the positions read from the database.
	 * 
	 * @param electionId
	 *            the electionId of the position objects to be read.
	 * @return the positions.
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET, value = ControllerConstants.POSITION_LABEL
			+ "/{positionId}"+ControllerConstants.CANDIDATES_LABEL)
	public @ResponseBody ResponseEntity<FindsParent> findCandidatesForPosition(
			@PathVariable(value = "") Long positionId,
			@RequestParam(value = "direction", required = false, defaultValue = ControllerConstants.DIRECTION) String direction,
			@RequestParam(value = "page", required = false, defaultValue = ControllerConstants.PAGE_NUMBER) String page,
			@RequestParam(value = "pageSize", required = false, defaultValue = ControllerConstants.PAGE_LENGTH) String pageSize)
	{
		int pageNumber = 0;
		int pageLength = 10;
		pageNumber = Integer.parseInt(page);
		pageLength = Integer.parseInt(pageSize);
		if (LOG.isInfoEnabled())
			LOG.info("Attempting to retrieve candidates for position"
					+ positionId + '.');
		ResponseEntity<FindsParent> response;
		
		Direction sortDirection = Direction.DESC;
		if (direction.equalsIgnoreCase("asc")) sortDirection = Direction.ASC;
		AllReadEvent candidatesEvent = positionService.readCandidates(
				new ReadAllEvent(positionId), sortDirection,
				pageNumber, pageLength);

		if (!candidatesEvent.isEntityFound())
		{
			response = new ResponseEntity<FindsParent>(HttpStatus.NOT_FOUND);
		}
		else
		{
			Iterator<Candidate> candidates = Candidate
					.toCandidatesIterator(candidatesEvent.getDetails().iterator());
			FindsParent theCandidates = FindsParent.fromArticlesIterator(candidates, candidatesEvent.getTotalItems(), candidatesEvent.getTotalPages());
			response = new ResponseEntity<FindsParent>(theCandidates, HttpStatus.OK);
		}
		return response;
	}

	/**
	 * Is passed all the necessary data to read positions from the database. The
	 * request must be a GET with the electionId presented as the final
	 * portion of the URL.
	 * <p/>
	 * This method will return the positions read from the database.
	 * 
	 * @param electionId
	 *            the electionId of the position objects to be read.
	 * @return the positions.
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET, value = ControllerConstants.POSITIONS_LABEL
			+ "/{electionId}")
	public @ResponseBody ResponseEntity<FindsParent> findPositions(
			@PathVariable(value = "") Long electionId,
			@RequestParam(value = "direction", required = false, defaultValue = ControllerConstants.DIRECTION) String direction,
			@RequestParam(value = "page", required = false, defaultValue = ControllerConstants.PAGE_NUMBER) String page,
			@RequestParam(value = "pageSize", required = false, defaultValue = ControllerConstants.PAGE_LENGTH) String pageSize)
	{
		int pageNumber = 0;
		int pageLength = 10;
		pageNumber = Integer.parseInt(page);
		pageLength = Integer.parseInt(pageSize);
		if (LOG.isInfoEnabled())
			LOG.info("Attempting to retrieve positions from institution "
					+ electionId + '.');
		ResponseEntity<FindsParent> response;
		
		Direction sortDirection = Direction.DESC;
		if (direction.equalsIgnoreCase("asc")) sortDirection = Direction.ASC;
		AllReadEvent positionsEvent = positionService.readPositions(
				new ReadAllEvent(electionId), sortDirection,
				pageNumber, pageLength);

		if (!positionsEvent.isEntityFound())
		{
			response = new ResponseEntity<FindsParent>(HttpStatus.NOT_FOUND);
		}
		else
		{
			Iterator<Position> badges = Position
					.toPositionsIterator(positionsEvent.getDetails().iterator());
			FindsParent thePositions = FindsParent.fromArticlesIterator(badges, positionsEvent.getTotalItems(), positionsEvent.getTotalPages());
			response = new ResponseEntity<FindsParent>(thePositions, HttpStatus.OK);
		}
		return response;
	}

    //Update
    @RequestMapping(method = RequestMethod.PUT, value = ControllerConstants.POSITION_LABEL+"/{positionId}")
    public @ResponseBody ResponseEntity<Position>
    updatePosition(@PathVariable Long positionId, @RequestBody Position position){
        if (LOG.isInfoEnabled()) LOG.info("Attempting to update position. " + positionId);
        UpdatedEvent positionUpdatedEvent = positionService.updatePosition(new UpdatePositionEvent(positionId, position.toPositionDetails()));
        if(null != positionUpdatedEvent){
            if (LOG.isDebugEnabled()) LOG.debug("positionUpdatedEvent - "+positionUpdatedEvent);
            if(positionUpdatedEvent.isEntityFound()){
                Position result = Position.fromPositionDetails((PositionDetails) positionUpdatedEvent.getDetails());
                if (LOG.isDebugEnabled()) LOG.debug("result = "+result);
                return new ResponseEntity<Position>(result, HttpStatus.OK);
            }
            else{
                return new ResponseEntity<Position>(HttpStatus.NOT_FOUND);
            }
        }
        else{
            return new ResponseEntity<Position>(HttpStatus.BAD_REQUEST);
        }
    }

    //Delete
    @RequestMapping(method = RequestMethod.DELETE, value = ControllerConstants.POSITION_LABEL+"/{positionId}")
    public @ResponseBody ResponseEntity<Boolean>
    deletePosition(@PathVariable Long positionId){
        if (LOG.isInfoEnabled()) LOG.info("Attempting to delete position. " + positionId);
        ResponseEntity<Boolean> response;

        DeletedEvent positionDeletedEvent = positionService.deletePosition(new DeletePositionEvent(positionId));
        Boolean isDeletionCompleted = Boolean.valueOf(positionDeletedEvent.isDeletionCompleted());
    	if (isDeletionCompleted)
    		response=new ResponseEntity<Boolean>(isDeletionCompleted,HttpStatus.OK);
    	else if (positionDeletedEvent.isEntityFound())
    		response=new ResponseEntity<Boolean>(isDeletionCompleted,HttpStatus.GONE);
    	else
    		response=new ResponseEntity<Boolean>(isDeletionCompleted,HttpStatus.NOT_FOUND);
    	return response;
    }


}
