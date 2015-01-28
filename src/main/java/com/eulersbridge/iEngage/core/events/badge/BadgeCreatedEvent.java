package com.eulersbridge.iEngage.core.events.badge;

import com.eulersbridge.iEngage.core.events.CreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yikai Gong
 */

public class BadgeCreatedEvent extends CreatedEvent{
    private Long badgeId;

    private static Logger LOG = LoggerFactory.getLogger(BadgeCreatedEvent.class);

    public BadgeCreatedEvent(Long badgeId) {
        if (LOG.isDebugEnabled()) LOG.debug("constructor()");
        this.badgeId = badgeId;
    }

    public BadgeCreatedEvent(Long badgeId, BadgeDetails badgeDetails) {
        super(badgeDetails);
        this.badgeId = badgeId;
    }

    public Long getBadgeId() {
        return badgeId;
    }

    public void setBadgeId(Long badgeId) {
        this.badgeId = badgeId;
    }
}
