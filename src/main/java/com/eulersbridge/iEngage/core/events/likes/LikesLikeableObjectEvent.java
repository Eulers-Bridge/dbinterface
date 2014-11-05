package com.eulersbridge.iEngage.core.events.likes;

import com.eulersbridge.iEngage.core.events.LikesEvent;

/**
 * @author Yikai Gong
 */

public class LikesLikeableObjectEvent extends LikesEvent {
    private Long likeableObjId;

    public LikesLikeableObjectEvent(Long likeableObjId) {
        this.likeableObjId = likeableObjId;
    }

    public Long getLikeableObjId() {
        return likeableObjId;
    }
}
