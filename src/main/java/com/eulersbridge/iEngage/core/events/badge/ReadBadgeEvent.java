package com.eulersbridge.iEngage.core.events.badge;

import com.eulersbridge.iEngage.core.events.Details;
import com.eulersbridge.iEngage.core.events.ReadEvent;

/**
 * @author Yikai Gong
 */

public class ReadBadgeEvent extends ReadEvent{
    public ReadBadgeEvent(Long badgeId) {
        super(badgeId);
    }

    public ReadBadgeEvent(Long badgeId, BadgeDetails badgeDetails) {
        super(badgeId, badgeDetails);
    }
}
