package com.eulersbridge.iEngage.rest.controller;

import com.eulersbridge.iEngage.core.events.*;
import com.eulersbridge.iEngage.core.events.badge.*;
import com.eulersbridge.iEngage.core.services.interfacePack.BadgeService;
import com.eulersbridge.iEngage.rest.domain.Badge;
import com.eulersbridge.iEngage.rest.domain.BadgeCompleted;
import com.eulersbridge.iEngage.rest.domain.WrappedDomainList;
import com.eulersbridge.iEngage.rest.domain.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;

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
	public @ResponseBody ResponseEntity<WrappedDomainList> findBadges(
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
		ResponseEntity<WrappedDomainList> response;

		Direction sortDirection = Direction.DESC;
		if (direction.equalsIgnoreCase("asc")) sortDirection = Direction.ASC;
		AllReadEvent badgeEvent = badgeService.readBadges(
				new ReadAllEvent(null), sortDirection,
				pageNumber, pageLength);

		if (!badgeEvent.isEntityFound())
		{
			response = new ResponseEntity<WrappedDomainList>(HttpStatus.NOT_FOUND);
		}
		else
		{
			Iterator<Badge> badges = Badge
					.toBadgesIterator(badgeEvent.getDetails().iterator());
			WrappedDomainList theBadges = WrappedDomainList.fromIterator(badges, badgeEvent.getTotalItems(), badgeEvent.getTotalPages());
			response = new ResponseEntity<WrappedDomainList>(theBadges, HttpStatus.OK);
		}
		return response;
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
	public @ResponseBody ResponseEntity<WrappedDomainList> findCompletedBadges(
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
		ResponseEntity<WrappedDomainList> response;

		Direction sortDirection = Direction.DESC;
		if (direction.equalsIgnoreCase("asc")) sortDirection = Direction.ASC;
		AllReadEvent badgeEvent = badgeService.readCompletedBadges(
				new ReadAllEvent(userId), sortDirection,
				pageNumber, pageLength);

		if (!badgeEvent.isEntityFound())
		{
			response = new ResponseEntity<WrappedDomainList>(HttpStatus.NOT_FOUND);
		}
		else
		{
			Iterator<Badge> badges = Badge
					.toBadgesIterator(badgeEvent.getDetails().iterator());
			WrappedDomainList theBadges = WrappedDomainList.fromIterator(badges, badgeEvent.getTotalItems(), badgeEvent.getTotalPages());
			response = new ResponseEntity<WrappedDomainList>(theBadges, HttpStatus.OK);
		}
		return response;
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
	public @ResponseBody ResponseEntity<WrappedDomainList> findRemainingBadges(
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
		ResponseEntity<WrappedDomainList> response;
		
		Direction sortDirection = Direction.DESC;
		if (direction.equalsIgnoreCase("asc")) sortDirection = Direction.ASC;
		AllReadEvent badgeEvent = badgeService.readRemainingBadges(
				new ReadAllEvent(userId), sortDirection,
				pageNumber, pageLength);

		if (!badgeEvent.isEntityFound())
		{
			response = new ResponseEntity<WrappedDomainList>(HttpStatus.NOT_FOUND);
		}
		else
		{
			Iterator<Badge> badges = Badge
					.toBadgesIterator(badgeEvent.getDetails().iterator());
			WrappedDomainList theBadges = WrappedDomainList.fromIterator(badges, badgeEvent.getTotalItems(), badgeEvent.getTotalPages());
			response = new ResponseEntity<WrappedDomainList>(theBadges, HttpStatus.OK);
		}
		return response;
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
    public @ResponseBody ResponseEntity<Response>
    deleteBadge(@PathVariable Long badgeId){
        if (LOG.isInfoEnabled()) LOG.info("Attempting to delete badge. " + badgeId);
        ResponseEntity<Response> response;
        DeletedEvent badgeDeletedEvent = badgeService.deleteBadge(new DeleteBadgeEvent(badgeId));
        Response restEvent;
        if (!badgeDeletedEvent.isEntityFound()){
            restEvent = Response.failed("Not found");
            response = new ResponseEntity<Response>(restEvent, HttpStatus.NOT_FOUND);
        }
        else{
            if (badgeDeletedEvent.isDeletionCompleted()){
                restEvent = new Response();
                response=new ResponseEntity<Response>(restEvent,HttpStatus.OK);
            }
            else {
                restEvent = Response.failed("Could not delete");
                response=new ResponseEntity<Response>(restEvent,HttpStatus.GONE);
            }
        }
        return response;
    }
}
