package com.eulersbridge.iEngage.rest.controller;

import java.util.Iterator;

import com.eulersbridge.iEngage.core.events.AllReadEvent;
import com.eulersbridge.iEngage.core.events.CreatedEvent;
import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadAllEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdateEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.badge.*;
import com.eulersbridge.iEngage.core.services.BadgeService;
import com.eulersbridge.iEngage.rest.domain.Badge;
import com.eulersbridge.iEngage.rest.domain.BadgeCompleted;

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
        CreatedEvent badgeCreatedEvent = badgeService.createBadge(createBadgeEvent);
        if(badgeCreatedEvent.getNodeId() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else{
            Badge result = Badge.fromBadgeDetails((BadgeDetails) badgeCreatedEvent.getDetails());
            if (LOG.isDebugEnabled()) LOG.debug("badge"+result.toString());
            return new ResponseEntity<>(result, HttpStatus.CREATED);
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

	/**
	 * Is passed all the necessary data to read badges from the database. The
	 * request must be a GET with the electionId presented as the final
	 * portion of the URL.
	 * <p/>
	 * This method will return the badges read from the database.
	 * 
	 * @param electionId
	 *            the electionId of the badge objects to be read.
	 * @return the badges.
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET, value = ControllerConstants.BADGES_LABEL)
	public @ResponseBody ResponseEntity<Iterator<Badge>> findBadges(
			@RequestParam(value = "direction", required = false, defaultValue = ControllerConstants.DIRECTION) String direction,
			@RequestParam(value = "page", required = false, defaultValue = ControllerConstants.PAGE_NUMBER) String page,
			@RequestParam(value = "pageSize", required = false, defaultValue = ControllerConstants.PAGE_LENGTH) String pageSize)
	{
		int pageNumber = 0;
		int pageLength = 10;
		pageNumber = Integer.parseInt(page);
		pageLength = Integer.parseInt(pageSize);
		if (LOG.isInfoEnabled())
			LOG.info("Attempting to retrieve badges.");

		Direction sortDirection = Direction.DESC;
		if (direction.equalsIgnoreCase("asc")) sortDirection = Direction.ASC;
		AllReadEvent articleEvent = badgeService.readBadges(
				new ReadAllEvent(null), sortDirection,
				pageNumber, pageLength);

		if (!articleEvent.isEntityFound())
		{
			return new ResponseEntity<Iterator<Badge>>(HttpStatus.NOT_FOUND);
		}

		Iterator<Badge> badges = Badge
				.toBadgesIterator(articleEvent.getDetails().iterator());

		return new ResponseEntity<Iterator<Badge>>(badges, HttpStatus.OK);
	}

	/**
	 * Is passed all the necessary data to read badges from the database. The
	 * request must be a GET with the electionId presented as the final
	 * portion of the URL.
	 * <p/>
	 * This method will return the badges read from the database.
	 * 
	 * @param electionId
	 *            the electionId of the badge objects to be read.
	 * @return the badges.
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET, value = ControllerConstants.BADGES_LABEL+"/complete/{userId}")
	public @ResponseBody ResponseEntity<Iterator<Badge>> findCompletedBadges(
			@PathVariable Long userId,
			@RequestParam(value = "direction", required = false, defaultValue = ControllerConstants.DIRECTION) String direction,
			@RequestParam(value = "page", required = false, defaultValue = ControllerConstants.PAGE_NUMBER) String page,
			@RequestParam(value = "pageSize", required = false, defaultValue = ControllerConstants.PAGE_LENGTH) String pageSize)
	{
		int pageNumber = 0;
		int pageLength = 10;
		pageNumber = Integer.parseInt(page);
		pageLength = Integer.parseInt(pageSize);
		if (LOG.isInfoEnabled())
			LOG.info("Attempting to retrieve completed badges for "+userId+".");

		Direction sortDirection = Direction.DESC;
		if (direction.equalsIgnoreCase("asc")) sortDirection = Direction.ASC;
		AllReadEvent articleEvent = badgeService.readCompletedBadges(
				new ReadAllEvent(userId), sortDirection,
				pageNumber, pageLength);

		if (!articleEvent.isEntityFound())
		{
			return new ResponseEntity<Iterator<Badge>>(HttpStatus.NOT_FOUND);
		}

		Iterator<Badge> badges = Badge
				.toBadgesIterator(articleEvent.getDetails().iterator());

		return new ResponseEntity<Iterator<Badge>>(badges, HttpStatus.OK);
	}

	/**
	 * Is passed all the necessary data to read badges from the database. The
	 * request must be a GET with the electionId presented as the final
	 * portion of the URL.
	 * <p/>
	 * This method will return the badges read from the database.
	 * 
	 * @param electionId
	 *            the electionId of the badge objects to be read.
	 * @return the badges.
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET, value = ControllerConstants.BADGES_LABEL+"/remaining/{userId}")
	public @ResponseBody ResponseEntity<Iterator<Badge>> findRemainingBadges(
			@PathVariable Long userId,
			@RequestParam(value = "direction", required = false, defaultValue = ControllerConstants.DIRECTION) String direction,
			@RequestParam(value = "page", required = false, defaultValue = ControllerConstants.PAGE_NUMBER) String page,
			@RequestParam(value = "pageSize", required = false, defaultValue = ControllerConstants.PAGE_LENGTH) String pageSize)
	{
		int pageNumber = 0;
		int pageLength = 10;
		pageNumber = Integer.parseInt(page);
		pageLength = Integer.parseInt(pageSize);
		if (LOG.isInfoEnabled())
			LOG.info("Attempting to retrieve remaining badges for "+userId+".");

		Direction sortDirection = Direction.DESC;
		if (direction.equalsIgnoreCase("asc")) sortDirection = Direction.ASC;
		AllReadEvent badgeEvent = badgeService.readRemainingBadges(
				new ReadAllEvent(userId), sortDirection,
				pageNumber, pageLength);

		if (!badgeEvent.isEntityFound())
		{
			return new ResponseEntity<Iterator<Badge>>(HttpStatus.NOT_FOUND);
		}

		Iterator<Badge> badges = Badge
				.toBadgesIterator(badgeEvent.getDetails().iterator());

		return new ResponseEntity<Iterator<Badge>>(badges, HttpStatus.OK);
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

    //Completed Badge
    @RequestMapping(method = RequestMethod.PUT, value = ControllerConstants.BADGE_LABEL+"/{badgeId}/complete/{userId}")
    public @ResponseBody ResponseEntity<BadgeCompleted>
    completedBadge(@PathVariable Long badgeId, @PathVariable Long userId)
    {
        if (LOG.isInfoEnabled()) LOG.info("User "+userId+" completed badge. " + badgeId);
        BadgeCompleteDetails dets=new BadgeCompleteDetails(null, userId, badgeId, null);
        UpdatedEvent badgeCompletedEvent = badgeService.completedBadge(new UpdateEvent(null,dets));
        if(null != badgeCompletedEvent)
        {
            if (LOG.isDebugEnabled()) LOG.debug("badgeUpdatedEvent - "+badgeCompletedEvent);
            if(badgeCompletedEvent.isEntityFound()){
                BadgeCompleted result = BadgeCompleted.fromBadgeCompletedDetails((BadgeCompleteDetails) badgeCompletedEvent.getDetails());
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

        ResponseEntity<Boolean> response;
        DeletedEvent badgeDeletedEvent = badgeService.deleteBadge(new DeleteBadgeEvent(badgeId));
        Boolean isDeletionCompleted = Boolean.valueOf(badgeDeletedEvent.isDeletionCompleted());
    	if (isDeletionCompleted)
    		response=new ResponseEntity<Boolean>(isDeletionCompleted,HttpStatus.OK);
    	else if (badgeDeletedEvent.isEntityFound())
    		response=new ResponseEntity<Boolean>(isDeletionCompleted,HttpStatus.GONE);
    	else
    		response=new ResponseEntity<Boolean>(isDeletionCompleted,HttpStatus.NOT_FOUND);
    	return response;
    }
}
