package com.eulersbridge.iEngage.core.services;

import java.util.ArrayList;
import java.util.Iterator;

import com.eulersbridge.iEngage.core.events.CreatedEvent;
import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadAllEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.badge.*;
import com.eulersbridge.iEngage.database.domain.Badge;
import com.eulersbridge.iEngage.database.repository.BadgeRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

/**
 * @author Yikai Gong
 */

public class BadgeEventHandler implements BadgeService{
    private static Logger LOG = LoggerFactory.getLogger(BadgeService.class);

    private BadgeRepository badgeRepository;

    public BadgeEventHandler(BadgeRepository badgeRepository) {
        this.badgeRepository = badgeRepository;
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
    public ReadEvent requestReadBadge(RequestReadBadgeEvent requestReadBadgeEvent) {
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
	public BadgesReadEvent readBadges(ReadAllEvent readBadgesEvent, Direction sortDirection,int pageNumber, int pageLength)
	{
		Page <Badge>badges=null;
		ArrayList<BadgeDetails> dets=new ArrayList<BadgeDetails>();
		BadgesReadEvent nare=null;

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
			nare=new BadgesReadEvent(dets,badges.getTotalElements(),badges.getTotalPages());
		}
		else
		{
			if (LOG.isDebugEnabled()) LOG.debug("Null returned by findAll");
			nare=(BadgesReadEvent) BadgesReadEvent.notFound(null);
		}
		return nare;
	}

    @Override
    public UpdatedEvent updateBadge(UpdateBadgeEvent updateBadgeEvent) {
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
    public DeletedEvent deleteBadge(DeleteBadgeEvent deleteBadgeEvent) {
        if (LOG.isDebugEnabled()) LOG.debug("Entered deleteBadgeEvent= "+deleteBadgeEvent);
        Long badgeId = deleteBadgeEvent.getNodeId();
        if (LOG.isDebugEnabled()) LOG.debug("deleteBadge("+badgeId+")");
        Badge badge = badgeRepository.findOne(badgeId);
        if(badge == null){
            return BadgeDeletedEvent.notFound(badgeId);
        }
        else{
            badgeRepository.delete(badge);
            BadgeDeletedEvent badgeDeletedEvent = new BadgeDeletedEvent(badgeId);
            return badgeDeletedEvent;
        }
    }
}
