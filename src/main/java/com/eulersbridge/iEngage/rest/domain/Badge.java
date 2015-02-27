package com.eulersbridge.iEngage.rest.domain;

import java.util.ArrayList;
import java.util.Iterator;

import com.eulersbridge.iEngage.core.events.badge.BadgeDetails;
import com.eulersbridge.iEngage.rest.controller.BadgeController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;


/**
 * @author Yikai Gong
 */

public class Badge extends ResourceSupport
{
    private Long badgeId;
    private String name;
    private boolean awarded;
    private Long timestamp;
    private Long xpValue;

    private static Logger LOG = LoggerFactory.getLogger(Badge.class);

    public Badge()
    {
        if (LOG.isDebugEnabled()) LOG.debug("constructor()");
    }

    public static Badge fromBadgeDetails(BadgeDetails badgeDetails){
        Badge badge = new Badge();
        String simpleName = Badge.class.getSimpleName();
        String name = simpleName.substring(0, 1).toLowerCase()
                + simpleName.substring(1);

        badge.setBadgeId(badgeDetails.getNodeId());
        badge.setName(badgeDetails.getName());
        badge.setAwarded(badgeDetails.isAwarded());
        badge.setTimestamp(badgeDetails.getTimestamp());
        badge.setXpValue(badgeDetails.getXpValue());

        // {!begin selfRel}
        badge.add(linkTo(BadgeController.class).slash(name)
                .slash(badge.badgeId).withSelfRel());
        // {!end selfRel}
        // {!begin readAll}
        badge.add(linkTo(BadgeController.class).slash(name + 's')
                .withRel(RestDomainConstants.READALL_LABEL));
        // {!end readAll}

        return badge;
    }

    public BadgeDetails toBadgeDetails(){
        BadgeDetails badgeDetails = new BadgeDetails();
        badgeDetails.setNodeId(getBadgeId());
        badgeDetails.setName(getName());
        badgeDetails.setAwarded(isAwarded());
        badgeDetails.setTimestamp(getTimestamp());
        badgeDetails.setXpValue(getXpValue());
        return badgeDetails;
    }

    public Long getBadgeId() {
        return badgeId;
    }

    public void setBadgeId(Long badgeId) {
        this.badgeId = badgeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAwarded() {
        return awarded;
    }

    public void setAwarded(boolean awarded) {
        this.awarded = awarded;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getXpValue() {
        return xpValue;
    }

    public void setXpValue(Long xpValue) {
        this.xpValue = xpValue;
    }
    
	public static Iterator<Badge> toBadgesIterator(
			Iterator<BadgeDetails> iter)
	{
		if (null==iter) return null;
		ArrayList <Badge> elections=new ArrayList<Badge>();
		while(iter.hasNext())
		{
			BadgeDetails dets=iter.next();
			Badge thisBadge=Badge.fromBadgeDetails(dets);
			Link self = thisBadge.getLink("self");
			thisBadge.removeLinks();
			thisBadge.add(self);
			elections.add(thisBadge);		
		}
		return elections.iterator();
	}
}
