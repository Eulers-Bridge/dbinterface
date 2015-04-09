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
    private Integer level;
    private String name;
    private String description;
//    private boolean awarded;
//    private Long timestamp;
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
        badge.setLevel(badgeDetails.getLevel());
        badge.setDescription(badgeDetails.getDescription());
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
        badgeDetails.setLevel(getLevel());
        badgeDetails.setDescription(getDescription());
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

    /**
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	public Long getXpValue() {
        return xpValue;
    }

    public void setXpValue(Long xpValue) {
        this.xpValue = xpValue;
    }
    
	/**
	 * @return the level
	 */
	public Integer getLevel()
	{
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(Integer level)
	{
		this.level = level;
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
