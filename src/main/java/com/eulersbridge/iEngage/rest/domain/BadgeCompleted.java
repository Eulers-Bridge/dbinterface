/**
 * 
 */
package com.eulersbridge.iEngage.rest.domain;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.ArrayList;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import com.eulersbridge.iEngage.core.events.badge.BadgeCompleteDetails;
import com.eulersbridge.iEngage.rest.controller.BadgeController;


/**
 * @author Greg Newitt
 *
 */
public class BadgeCompleted extends ResourceSupport
{
    private Long nodeId;
    private Long badgeId;
    private Long timestamp;
    private Long userId;

    private static Logger LOG = LoggerFactory.getLogger(BadgeCompleted.class);

    public static BadgeCompleted fromBadgeCompletedDetails(BadgeCompleteDetails badgeCompleteDetails)
    {
    	BadgeCompleted badge = new BadgeCompleted();
        String simpleName = Badge.class.getSimpleName();
        String name = simpleName.substring(0, 1).toLowerCase()
                + simpleName.substring(1);

        badge.setNodeId(badgeCompleteDetails.getNodeId());
        badge.setBadgeId(badgeCompleteDetails.getBadgeId());
        badge.setTimestamp(badgeCompleteDetails.getDate());
        badge.setUserId(badgeCompleteDetails.getUserId());

        // {!begin selfRel}
        badge.add(linkTo(BadgeController.class).slash(name)
                .slash(badge.badgeId).slash("complete").slash(badge.getUserId()).withSelfRel());
        // {!end selfRel}

        return badge;
    }

    public BadgeCompleteDetails toBadgeDetails()
    {
    	BadgeCompleteDetails badgeDetails = new BadgeCompleteDetails();
        badgeDetails.setNodeId(getNodeId());
        badgeDetails.setBadgeId(getBadgeId());
        badgeDetails.setDate(getTimestamp());
        badgeDetails.setUserId(getUserId());
        return badgeDetails;
    }

    public BadgeCompleted() 
    {
        if (LOG.isDebugEnabled()) LOG.debug("constructor()");
    }

    /**
	 * @return the nodeId
	 */
	public Long getNodeId()
	{
		return nodeId;
	}

	/**
	 * @param nodeId the nodeId to set
	 */
	public void setNodeId(Long nodeId)
	{
		this.nodeId = nodeId;
	}

	public Long getBadgeId() {
        return badgeId;
    }

    public void setBadgeId(Long badgeId) {
        this.badgeId = badgeId;
    }

	/**
	 * @return the timestamp
	 */
	public Long getTimestamp()
	{
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Long timestamp)
	{
		this.timestamp = timestamp;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId()
	{
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId)
	{
		this.userId = userId;
	}

	public static Iterator<BadgeCompleted> toBadgesIterator(
			Iterator<BadgeCompleteDetails> iter)
	{
		if (null==iter) return null;
		ArrayList <BadgeCompleted> completedBadges=new ArrayList<BadgeCompleted>();
		while(iter.hasNext())
		{
			BadgeCompleteDetails dets=iter.next();
			BadgeCompleted thisBadge=BadgeCompleted.fromBadgeCompletedDetails(dets);
			Link self = thisBadge.getLink("self");
			thisBadge.removeLinks();
			thisBadge.add(self);
			completedBadges.add(thisBadge);		
		}
		return completedBadges.iterator();
	}

}
