package com.eulersbridge.iEngage.core.events.badge;

import com.eulersbridge.iEngage.core.events.DeletedEvent;

/**
 * @author Yikai Gong
 */

public class BadgeDeletedEvent extends DeletedEvent{
    public BadgeDeletedEvent(Long badgeId) {
        super(badgeId);
    }
}
