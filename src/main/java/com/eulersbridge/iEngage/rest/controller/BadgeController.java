package com.eulersbridge.iEngage.rest.controller;

import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.badge.*;
import com.eulersbridge.iEngage.core.services.BadgeService;
import com.eulersbridge.iEngage.rest.domain.Badge;
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
public class BadgeController {
    @Autowired
    BadgeService badgeService;

    private static Logger LOG = LoggerFactory.getLogger(ControllerConstants.class);

    public BadgeController() {
    }

    //Create
    @RequestMapping(method = RequestMethod.POST, value = ControllerConstants.BADGE_LABEL)
    public @ResponseBody ResponseEntity<Badge>
    createBadge(@RequestBody Badge badge){
        if (LOG.isInfoEnabled()) LOG.info("attempting to create badge "+badge);
        CreateBadgeEvent createBadgeEvent = new CreateBadgeEvent(badge.toBadgeDetails());
        BadgeCreatedEvent badgeCreatedEvent = badgeService.createBadge(createBadgeEvent);
        if(badgeCreatedEvent.getBadgeId() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else{
            Badge result = Badge.fromBadgeDetails((BadgeDetails) badgeCreatedEvent.getDetails());
            if (LOG.isDebugEnabled()) LOG.debug("badge"+result.toString());
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    //Get
    @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.BADGE_LABEL + "/{badgeId}")
    public @ResponseBody ResponseEntity<Badge>
    findBadge(@PathVariable Long badgeId){
        if (LOG.isInfoEnabled()) LOG.info(badgeId+" attempting to get badge. ");
        RequestReadBadgeEvent requestReadBadgeEvent = new RequestReadBadgeEvent(badgeId);
        ReadEvent readBadgeEvent = badgeService.requestReadBadge(requestReadBadgeEvent);
        if(readBadgeEvent.isEntityFound()){
            Badge badge = Badge.fromBadgeDetails((BadgeDetails) readBadgeEvent.getDetails());
            return new ResponseEntity<>(badge, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Update
    @RequestMapping(method = RequestMethod.PUT, value = ControllerConstants.BADGE_LABEL+"/{badgeId}")
    public @ResponseBody ResponseEntity<Badge>
    updateBadge(@PathVariable Long badgeId, @RequestBody Badge badge){
        if (LOG.isInfoEnabled()) LOG.info("Attempting to update badge. " + badgeId);
        UpdatedEvent badgeUpdatedEvent = badgeService.updateBadge(new UpdateBadgeEvent(badgeId, badge.toBadgeDetails()));
        if(null != badgeUpdatedEvent){
            if (LOG.isDebugEnabled()) LOG.debug("candidateUpdatedEvent - "+badgeUpdatedEvent);
            if(badgeUpdatedEvent.isEntityFound()){
                Badge result = Badge.fromBadgeDetails((BadgeDetails) badgeUpdatedEvent.getDetails());
                if (LOG.isDebugEnabled()) LOG.debug("result = "+result);
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //Delete
    @RequestMapping(method = RequestMethod.DELETE, value = ControllerConstants.BADGE_LABEL+"/{badgeId}")
    public @ResponseBody ResponseEntity<Boolean>
    deleteBadge(@PathVariable Long badgeId){
        if (LOG.isInfoEnabled()) LOG.info("Attempting to delete badge. " + badgeId);
        DeletedEvent badgeDeletedEvent = badgeService.deleteBadge(new DeleteBadgeEvent(badgeId));
        Boolean isDeletionCompleted = Boolean.valueOf(badgeDeletedEvent.isDeletionCompleted());
        return new ResponseEntity<Boolean>(isDeletionCompleted, HttpStatus.OK);
    }

    //TODO ReadAll
}
