package com.eulersbridge.iEngage.rest.controller;

import com.eulersbridge.iEngage.core.events.positions.*;
import com.eulersbridge.iEngage.core.services.PositionService;
import com.eulersbridge.iEngage.rest.domain.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
        PositionCreatedEvent positionCreatedEvent = positionService.createPosition(createPositionEvent);
        if(positionCreatedEvent.getPositionId() == null){
            return new ResponseEntity<Position>(HttpStatus.BAD_REQUEST);
        }
        else{
            Position result = Position.fromPositionDetails(positionCreatedEvent.getDetails());
            if (LOG.isDebugEnabled()) LOG.debug("position"+result.toString());
            return new ResponseEntity<Position>(result, HttpStatus.OK);
        }
    }

    //Get
    @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.POSITION_LABEL + "/{positionId}")
    public @ResponseBody ResponseEntity<Position>
    findPosition(@PathVariable Long positionId){
        if (LOG.isInfoEnabled()) LOG.info(positionId+" attempting to get position. ");
        RequestReadPositionEvent requestReadPositionEvent = new RequestReadPositionEvent(positionId);
        ReadPositionEvent readPositionEvent = positionService.requestReadPosition(requestReadPositionEvent);
        if(readPositionEvent.isEntityFound()){
            Position position = Position.fromPositionDetails(readPositionEvent.getPositionDetails());
            return new ResponseEntity<Position>(position, HttpStatus.OK);
        }else{
            return new ResponseEntity<Position>(HttpStatus.NOT_FOUND);
        }
    }

    //Update
    @RequestMapping(method = RequestMethod.PUT, value = ControllerConstants.POSITION_LABEL+"/{positionId}")
    public @ResponseBody ResponseEntity<Position>
    updatePosition(@PathVariable Long positionId, @RequestBody Position position){
        if (LOG.isInfoEnabled()) LOG.info("Attempting to update position. " + positionId);
        PositionUpdatedEvent positionUpdatedEvent = positionService.updatePosition(new UpdatePositionEvent(positionId, position.toPositionDetails()));
        if(null != positionUpdatedEvent){
            if (LOG.isDebugEnabled()) LOG.debug("positionUpdatedEvent - "+positionUpdatedEvent);
            if(positionUpdatedEvent.isEntityFound()){
                Position result = Position.fromPositionDetails(positionUpdatedEvent.getPositionDetails());
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
        PositionDeletedEvent positionDeletedEvent = positionService.deletePosition(new DeletePositionEvent(positionId));
        Boolean isDeletionCompleted = Boolean.valueOf(positionDeletedEvent.isDeletionCompleted());
        return new ResponseEntity<Boolean>(isDeletionCompleted, HttpStatus.OK);
    }

}
