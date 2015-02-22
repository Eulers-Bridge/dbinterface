/**
 * 
 */
package com.eulersbridge.iEngage.rest.controller;

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.eulersbridge.iEngage.core.events.CreatedEvent;
import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.LikeEvent;
import com.eulersbridge.iEngage.core.events.LikedEvent;
import com.eulersbridge.iEngage.core.events.ReadAllEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.likes.LikeableObjectLikesEvent;
import com.eulersbridge.iEngage.core.events.likes.LikesLikeableObjectEvent;
import com.eulersbridge.iEngage.core.events.votingLocation.CreateVotingLocationEvent;
import com.eulersbridge.iEngage.core.events.votingLocation.DeleteVotingLocationEvent;
import com.eulersbridge.iEngage.core.events.votingLocation.ReadVotingLocationEvent;
import com.eulersbridge.iEngage.core.events.votingLocation.UpdateVotingLocationEvent;
import com.eulersbridge.iEngage.core.events.votingLocation.VotingLocationCreatedEvent;
import com.eulersbridge.iEngage.core.events.votingLocation.VotingLocationDetails;
import com.eulersbridge.iEngage.core.events.votingLocation.VotingLocationsReadEvent;
import com.eulersbridge.iEngage.core.services.LikesService;
import com.eulersbridge.iEngage.core.services.VotingLocationService;
import com.eulersbridge.iEngage.rest.domain.LikeInfo;
import com.eulersbridge.iEngage.rest.domain.User;
import com.eulersbridge.iEngage.rest.domain.VotingLocation;

/**
 * @author Greg Newitt
 *
 */
@RestController
@RequestMapping(ControllerConstants.API_PREFIX)
public class VotingLocationController
{
    @Autowired
    VotingLocationService votingLocationService;
    @Autowired
    LikesService likesService;

    private static Logger LOG = LoggerFactory.getLogger(VotingLocationController.class);

    public VotingLocationController() {
    }

    //Create
    @RequestMapping(method = RequestMethod.POST, value = ControllerConstants.VOTING_LOCATION_LABEL)
    public @ResponseBody ResponseEntity<VotingLocation>
    createVotingLocation(@RequestBody VotingLocation votingLocation){
        if (LOG.isInfoEnabled()) LOG.info("attempting to create votingLocation "+votingLocation);
        CreateVotingLocationEvent createVotingLocationEvent = new CreateVotingLocationEvent(votingLocation.toVotingLocationDetails());
        CreatedEvent votingLocationCreatedEvent = votingLocationService.createVotingLocation(createVotingLocationEvent);
        ResponseEntity<VotingLocation> response;
        if(null==votingLocationCreatedEvent)
        {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
		else if ((votingLocationCreatedEvent.getClass()==VotingLocationCreatedEvent.class)&&(!(((VotingLocationCreatedEvent)votingLocationCreatedEvent).isOwnerFound())))
		{
			response = new ResponseEntity<VotingLocation>(HttpStatus.NOT_FOUND);
		}
		else if((null==votingLocationCreatedEvent.getNodeId())||(votingLocationCreatedEvent.isFailed()))
        {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else
        {
            VotingLocation result = VotingLocation.fromVotingLocationDetails((VotingLocationDetails)votingLocationCreatedEvent.getDetails());
            if (LOG.isDebugEnabled()) LOG.debug("votingLocation"+result.toString());
            response = new ResponseEntity<>(result, HttpStatus.CREATED);
        }
        return response;
    }

    //Get
    @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.VOTING_LOCATION_LABEL + "/{votingLocationId}")
    public @ResponseBody ResponseEntity<VotingLocation>
    findVotingLocation(@PathVariable Long votingLocationId)
    {
        if (LOG.isInfoEnabled()) LOG.info(votingLocationId+" attempting to get votingLocation. ");
        ReadVotingLocationEvent requestReadVotingLocationEvent = new ReadVotingLocationEvent(votingLocationId);
        ReadEvent readVotingLocationEvent = votingLocationService.readVotingLocation(requestReadVotingLocationEvent);
        if(readVotingLocationEvent.isEntityFound())
        {
            VotingLocation votingLocation = VotingLocation.fromVotingLocationDetails((VotingLocationDetails) (readVotingLocationEvent.getDetails()));
            return new ResponseEntity<>(votingLocation, HttpStatus.OK);
        }else
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
	
	/**
	 * Is passed all the necessary data to read votingLocations from the database. The
	 * request must be a GET with the electionId presented as the final
	 * portion of the URL.
	 * <p/>
	 * This method will return the votingLocations read from the database.
	 * 
	 * @param electionId
	 *            the electionId of the votingLocation objects to be read.
	 * @return the votingLocations.
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET, value = ControllerConstants.VOTING_LOCATIONS_LABEL
			+ "/{electionId}")
	public @ResponseBody ResponseEntity<Iterator<VotingLocation>> findVotingLocations(
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
			LOG.info("Attempting to retrieve votingLocations from institution "
					+ electionId + '.');

		Direction sortDirection = Direction.DESC;
		if (direction.equalsIgnoreCase("asc")) sortDirection = Direction.ASC;
		VotingLocationsReadEvent articleEvent = votingLocationService.findVotingLocations(
				new ReadAllEvent(electionId), sortDirection,
				pageNumber, pageLength);

		if (!articleEvent.isEntityFound())
		{
			return new ResponseEntity<Iterator<VotingLocation>>(HttpStatus.NOT_FOUND);
		}

		Iterator<VotingLocation> votingLocations = VotingLocation
				.toVotingLocationsIterator(articleEvent.getVotingLocations().iterator());

		return new ResponseEntity<Iterator<VotingLocation>>(votingLocations, HttpStatus.OK);
	}

    //Update
    @RequestMapping(method = RequestMethod.PUT, value = ControllerConstants.VOTING_LOCATION_LABEL+"/{votingLocationId}")
    public @ResponseBody ResponseEntity<VotingLocation>
    updateVotingLocation(@PathVariable Long votingLocationId, @RequestBody VotingLocation votingLocation){
        if (LOG.isInfoEnabled()) LOG.info("Attempting to update votingLocation. " + votingLocationId);
        UpdatedEvent votingLocationUpdatedEvent = votingLocationService.updateVotingLocation(new UpdateVotingLocationEvent(votingLocationId, votingLocation.toVotingLocationDetails()));
        if(null != votingLocationUpdatedEvent)
        {
            if (LOG.isDebugEnabled()) LOG.debug("votingLocationUpdatedEvent - "+votingLocationUpdatedEvent);
            if(votingLocationUpdatedEvent.isEntityFound())
            {
                VotingLocation result = VotingLocation.fromVotingLocationDetails((VotingLocationDetails) votingLocationUpdatedEvent.getDetails());
                if (LOG.isDebugEnabled()) LOG.debug("result = "+result);
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
            else
            {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //Delete
    @RequestMapping(method = RequestMethod.DELETE, value = ControllerConstants.VOTING_LOCATION_LABEL+"/{votingLocationId}")
    public @ResponseBody ResponseEntity<Boolean>
    deleteVotingLocation(@PathVariable Long votingLocationId)
    {
        if (LOG.isInfoEnabled()) LOG.info("Attempting to delete votingLocation. " + votingLocationId);
        DeletedEvent votingLocationDeletedEvent = votingLocationService.deleteVotingLocation(new DeleteVotingLocationEvent(votingLocationId));
        ResponseEntity<Boolean> response;

		if (votingLocationDeletedEvent.isDeletionCompleted())
			response=new ResponseEntity<Boolean>(votingLocationDeletedEvent.isDeletionCompleted(),HttpStatus.OK);
		else if (votingLocationDeletedEvent.isEntityFound())
			response=new ResponseEntity<Boolean>(votingLocationDeletedEvent.isDeletionCompleted(),HttpStatus.GONE);
		else
			response=new ResponseEntity<Boolean>(votingLocationDeletedEvent.isDeletionCompleted(),HttpStatus.NOT_FOUND);
		return response;
		
    }

    // like
    @RequestMapping(method = RequestMethod.PUT, value = ControllerConstants.VOTING_LOCATION_LABEL
            + "/{votingLocationId}/likedBy/{email}/")
    public @ResponseBody ResponseEntity<Boolean> likeVotingLocation(
            @PathVariable Long votingLocationId, @PathVariable String email)
    {
        if (LOG.isInfoEnabled())
            LOG.info("Attempting to have " + email + " like votingLocation. " + votingLocationId);
        LikedEvent likedPollEvent = likesService.like(new LikeEvent(votingLocationId,
                email));
        ResponseEntity<Boolean> response;
        if (!likedPollEvent.isEntityFound())
        {
            response = new ResponseEntity<Boolean>(HttpStatus.GONE);
        }
        else if (!likedPollEvent.isUserFound())
        {
            response = new ResponseEntity<Boolean>(HttpStatus.NOT_FOUND);
        }
        else
        {
            Boolean restNews = likedPollEvent.isResultSuccess();
            response = new ResponseEntity<Boolean>(restNews, HttpStatus.OK);
        }
        return response;
    }

    // unlike
    @RequestMapping(method = RequestMethod.PUT, value = ControllerConstants.VOTING_LOCATION_LABEL
            + "/{votingLocationId}/unlikedBy/{email}/")
    public @ResponseBody ResponseEntity<Boolean> unlikeVotingLocation(
            @PathVariable Long votingLocationId, @PathVariable String email)
    {
        if (LOG.isInfoEnabled())
            LOG.info("Attempting to have " + email + " unlike votingLocation. " + votingLocationId);
        LikedEvent unlikedPollEvent = likesService.unlike(new LikeEvent(votingLocationId,
                email));
        ResponseEntity<Boolean> response;
        if (!unlikedPollEvent.isEntityFound())
        {
            response = new ResponseEntity<Boolean>(HttpStatus.GONE);
        }
        else if (!unlikedPollEvent.isUserFound())
        {
            response = new ResponseEntity<Boolean>(HttpStatus.NOT_FOUND);
        }
        else
        {
            Boolean restNews = unlikedPollEvent.isResultSuccess();
            response = new ResponseEntity<Boolean>(restNews, HttpStatus.OK);
        }
        return response;
    }

    // likes
    @RequestMapping(method = RequestMethod.GET, value = ControllerConstants.VOTING_LOCATION_LABEL
            + "/{votingLocationId}" + ControllerConstants.LIKES_LABEL)
    public @ResponseBody ResponseEntity<Iterator<LikeInfo>> findLikes(
            @PathVariable Long votingLocationId,
            @RequestParam(value = "direction", required = false, defaultValue = ControllerConstants.DIRECTION) String direction,
            @RequestParam(value = "page", required = false, defaultValue = ControllerConstants.PAGE_NUMBER) String page,
            @RequestParam(value = "pageSize", required = false, defaultValue = ControllerConstants.PAGE_LENGTH) String pageSize)
    {
        int pageNumber = 0;
        int pageLength = 10;
        pageNumber = Integer.parseInt(page);
        pageLength = Integer.parseInt(pageSize);
        if (LOG.isInfoEnabled())
            LOG.info("Attempting to retrieve liked users from votingLocation " + votingLocationId
                    + '.');
        Sort.Direction sortDirection = Sort.Direction.DESC;
        if (direction.equalsIgnoreCase("asc")) sortDirection = Sort.Direction.ASC;
        LikeableObjectLikesEvent likeableObjectLikesEvent = likesService.likes(
                new LikesLikeableObjectEvent(votingLocationId), sortDirection,
                pageNumber, pageLength);
        Iterator<LikeInfo> likes = User
                .toLikesIterator(likeableObjectLikesEvent.getUserDetails()
                        .iterator());
        if (likes.hasNext() == false)
        {
            ReadEvent readPollEvent = votingLocationService
                    .readVotingLocation(new ReadVotingLocationEvent(votingLocationId));
            if (!readPollEvent.isEntityFound())
                return new ResponseEntity<Iterator<LikeInfo>>(
                        HttpStatus.NOT_FOUND);
            else return new ResponseEntity<Iterator<LikeInfo>>(likes,
                    HttpStatus.OK);
        }
        else return new ResponseEntity<Iterator<LikeInfo>>(likes, HttpStatus.OK);
    }


}