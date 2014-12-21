package com.eulersbridge.iEngage.core.events.badge;

import com.eulersbridge.iEngage.core.events.UpdateEvent;

/**
 * @author Yikai Gong
 */

public class UpdateBadgeEvent extends UpdateEvent{
    public UpdateBadgeEvent(Long badgeId, BadgeDetails badgeDetails) {
        super(badgeId, badgeDetails);
    }
}
