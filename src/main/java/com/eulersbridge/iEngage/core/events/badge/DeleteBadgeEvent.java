package com.eulersbridge.iEngage.core.events.badge;

import com.eulersbridge.iEngage.core.events.DeleteEvent;

/**
 * @author Yikai Gong
 */

public class DeleteBadgeEvent extends DeleteEvent{
    public DeleteBadgeEvent(Long badgeId) {
        super(badgeId);
    }
}
