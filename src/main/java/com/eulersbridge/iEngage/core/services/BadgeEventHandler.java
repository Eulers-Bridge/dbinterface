package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.*;
import com.eulersbridge.iEngage.core.events.badge.*;
import com.eulersbridge.iEngage.database.domain.Badge;
import com.eulersbridge.iEngage.database.domain.BadgeComplete;
import com.eulersbridge.iEngage.database.domain.User;
import com.eulersbridge.iEngage.database.repository.BadgeRepository;
import com.eulersbridge.iEngage.database.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Yikai Gong
 */

public class BadgeEventHandler implements BadgeService{
    private static Logger LOG = LoggerFactory.getLogger(BadgeService.class);

    private BadgeRepository badgeRepository;

	private UserRepository userRepository;

    public BadgeEventHandler(BadgeRepository badgeRepository, UserRepository userRepository)
    {
        this.badgeRepository = badgeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CreatedEvent createBadge(CreateBadgeEvent createBadgeEvent)
    {
        BadgeDetails badgeDetails = (BadgeDetails) createBadgeEvent.getDetails();
        CreatedEvent badgeCreatedEvent;
        
        Badge badge = Badge.fromBadgeDetails(badgeDetails);
        Badge result = badgeRepository.save(badge);
        if ((null==result)||(null==result.getNodeId()))
        	badgeCreatedEvent = CreatedEvent.failed(badgeDetails);
        else
        	badgeCreatedEvent = new BadgeCreatedEvent(result.getNodeId(), result.toBadgeDetails());
        return badgeCreatedEvent;
    }
    

    @Override
    public ReadEvent requestReadBadge(RequestReadBadgeEvent requestReadBadgeEvent)
    {
        Badge badge = badgeRepository.findOne(requestReadBadgeEvent.getNodeId());
        ReadEvent readBadgeEvent;
        if(badge != null){
            readBadgeEvent = new ReadBadgeEvent(badge.getNodeId(), badge.toBadgeDetails());
        }
        else{
            readBadgeEvent = ReadBadgeEvent.notFound(requestReadBadgeEvent.getNodeId());
        }
        return readBadgeEvent;
    }

	@Override
	public AllReadEvent readBadges(ReadAllEvent readBadgesEvent, Direction sortDirection,int pageNumber, int pageLength)
	{
		Page <Badge>badges=null;
		ArrayList<BadgeDetails> dets=new ArrayList<BadgeDetails>();
		AllReadEvent nare=null;

		Pageable pageable=new PageRequest(pageNumber,pageLength,sortDirection,"name");
		badges=badgeRepository.findAll(pageable);
		if (badges!=null)
		{
			if (LOG.isDebugEnabled())
				LOG.debug("Total elements = "+badges.getTotalElements()+" total pages ="+badges.getTotalPages());
			Iterator<Badge> iter=badges.iterator();
			while (iter.hasNext())
			{
				Badge na=iter.next();
				if (LOG.isTraceEnabled()) LOG.trace("Converting to details - "+na.getName());
				BadgeDetails det=na.toBadgeDetails();
				dets.add(det);
			}
			nare=new AllReadEvent(null,dets,badges.getTotalElements(),badges.getTotalPages());
		}
		else
		{
			if (LOG.isDebugEnabled()) LOG.debug("Null returned by findAll");
			nare=AllReadEvent.notFound(null);
		}
		return nare;
	}

    @Override
    public UpdatedEvent updateBadge(UpdateBadgeEvent updateBadgeEvent)
    {
        BadgeDetails badgeDetails = (BadgeDetails) updateBadgeEvent.getDetails();
        Badge badge = Badge.fromBadgeDetails(badgeDetails);
        Long badgeId = badgeDetails.getNodeId();
        if(LOG.isDebugEnabled()) LOG.debug("badgeId is " + badgeId);
        Badge badgeOld = badgeRepository.findOne(badgeId);
        if(badgeOld == null){
            if(LOG.isDebugEnabled()) LOG.debug("badge entity not found " + badgeId);
            return BadgeUpdatedEvent.notFound(badgeId);
        }
        else{
            Badge result = badgeRepository.save(badge);
            if(LOG.isDebugEnabled()) LOG.debug("updated successfully" + result.getNodeId());
            return new BadgeUpdatedEvent(result.getNodeId(), result.toBadgeDetails());
        }
    }

	@Override
	public UpdatedEvent completedBadge(UpdateEvent updateBadgeEvent)
	{
        BadgeCompleteDetails badgeDetails = (BadgeCompleteDetails) updateBadgeEvent.getDetails();
        Long badgeId = badgeDetails.getBadgeId();
        Long userId = badgeDetails.getUserId();
        UpdatedEvent response=null;
        if(LOG.isDebugEnabled()) LOG.debug("badgeId is " + badgeId+" userId - "+userId);
        Badge badge = badgeRepository.findOne(badgeId);
        if(badge == null)
        {
            if(LOG.isDebugEnabled()) LOG.debug("badge entity not found " + badgeId);
            response = BadgeUpdatedEvent.notFound(badgeId );
        }
        else
        {
            User user = userRepository.findOne(userId);
        	if (null==user)
        	{
                if(LOG.isDebugEnabled()) LOG.debug("user entity not found " + badgeId);
                response = BadgeUpdatedEvent.notFound(userId );
        	}
        	else
        	{
        		BadgeComplete tc = BadgeComplete.fromBadgeCompleteDetails(badgeDetails);
                BadgeComplete result = badgeRepository.badgeCompleted(tc.getBadge().getNodeId(),tc.getUser().getNodeId());
                if(LOG.isDebugEnabled()) LOG.debug("updated successfully" + result.getNodeId());
                response = new UpdatedEvent(result.getNodeId(), result.toBadgeCompleteDetails());
        	}
        }
        return response;
	}

    @Override
    public DeletedEvent deleteBadge(DeleteEvent deleteBadgeEvent)
    {
        if (LOG.isDebugEnabled()) LOG.debug("Entered deleteBadgeEvent= "+deleteBadgeEvent);
        Long badgeId = deleteBadgeEvent.getNodeId();
        if (LOG.isDebugEnabled()) LOG.debug("deleteBadge("+badgeId+")");
        Badge badge = badgeRepository.findOne(badgeId);
        if(badge == null){
            return DeletedEvent.notFound(badgeId);
        }
        else{
            badgeRepository.delete(badge);
            DeletedEvent badgeDeletedEvent = new DeletedEvent(badgeId);
            return badgeDeletedEvent;
        }
    }

	@Override
	public AllReadEvent readCompletedBadges(ReadAllEvent readCompletedBadgesEvent,
			Direction sortDirection, int pageNumber, int pageLength)
	{
		Page <Badge>badges=null;
		ArrayList<BadgeDetails> dets=new ArrayList<BadgeDetails>();
		AllReadEvent nare=null;
		Long userId=readCompletedBadgesEvent.getParentId();

		Pageable pageable=new PageRequest(pageNumber,pageLength,sortDirection,"r.date");
		badges=badgeRepository.findCompletedBadges(userId,pageable);
		if (badges!=null)
		{
			if (LOG.isDebugEnabled())
				LOG.debug("Total elements = "+badges.getTotalElements()+" total pages ="+badges.getTotalPages());
			Iterator<Badge> iter=badges.iterator();
			while (iter.hasNext())
			{
				Badge na=iter.next();
				if (LOG.isTraceEnabled()) LOG.trace("Converting to details - "+na.getName());
				BadgeDetails det=na.toBadgeDetails();
				dets.add(det);
			}
			if (0==dets.size())
			{
				// Need to check if we actually found parentId.
				User elec=userRepository.findOne(userId);
				if ( (null==elec) ||
					 ((null==elec.getGivenName()) || ((null==elec.getFamilyName()) && (null==elec.getEmail()) && (null==elec.getInstitution()))))
				{
					if (LOG.isDebugEnabled()) LOG.debug("Null or null properties returned by findOne(userId)");
					nare=AllReadEvent.notFound(userId);
				}
				else
				{	
					nare=new AllReadEvent(userId,dets,badges.getTotalElements(),badges.getTotalPages());
				}
			}
			else
			{	
				nare=new AllReadEvent(userId,dets,badges.getTotalElements(),badges.getTotalPages());
			}
		}
		else
		{
			if (LOG.isDebugEnabled()) LOG.debug("Null returned by findAll");
			nare= AllReadEvent.notFound(null);
		}
		return nare;
	}

	@Override
	public AllReadEvent readRemainingBadges(ReadAllEvent readAllEvent,
			Direction sortDirection, int pageNumber, int pageLength)
	{
		Page <Badge>badges=null;
		ArrayList<BadgeDetails> dets=new ArrayList<BadgeDetails>();
		AllReadEvent nare=null;
		Long userId=readAllEvent.getParentId();

		Pageable pageable=new PageRequest(pageNumber,pageLength,sortDirection,"t.xpValue");
		badges=badgeRepository.findRemainingBadges(userId,pageable);
		if (badges!=null)
		{
			if (LOG.isDebugEnabled())
				LOG.debug("Total elements = "+badges.getTotalElements()+" total pages ="+badges.getTotalPages());
			Iterator<Badge> iter=badges.iterator();
			while (iter.hasNext())
			{
				Badge na=iter.next();
				if (LOG.isTraceEnabled()) LOG.trace("Converting to details - "+na.getName());
				BadgeDetails det=na.toBadgeDetails();
				dets.add(det);
			}
			if (0==dets.size())
			{
				// Need to check if we actually found parentId.
				User elec=userRepository.findOne(userId);
				if ( (null==elec) ||
					 ((null==elec.getGivenName()) || ((null==elec.getFamilyName()) && (null==elec.getEmail()) && (null==elec.getInstitution()))))
				{
					if (LOG.isDebugEnabled()) LOG.debug("Null or null properties returned by findOne(userId)");
					nare=AllReadEvent.notFound(userId);
				}
				else
				{	
					nare=new AllReadEvent(userId,dets,badges.getTotalElements(),badges.getTotalPages());
				}
			}
			else
			{	
				nare=new AllReadEvent(userId,dets,badges.getTotalElements(),badges.getTotalPages());
			}
		}
		else
		{
			if (LOG.isDebugEnabled()) LOG.debug("Null returned by findAll");
			nare= AllReadEvent.notFound(null);
		}
		return nare;
	}
}
