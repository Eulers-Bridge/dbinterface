package com.eulersbridge.iEngage.core.events.likes;

import com.eulersbridge.iEngage.core.events.LikesEvent;
import com.eulersbridge.iEngage.core.events.users.UserDetails;

import java.util.Collection;

/**
 * @author Yikai Gong
 */

public class LikeableObjectLikesEvent extends LikesEvent {
    private Long likeableObjId;

    private boolean objectFound = true;

    private Collection<UserDetails> userDetails;

    public LikeableObjectLikesEvent() {
        super();
    }

    public LikeableObjectLikesEvent(Long likeableObjId, Collection<UserDetails> userDetails) {
        this.likeableObjId = likeableObjId;
        this.userDetails = userDetails;
    }

    public static LikeableObjectLikesEvent objectNotFound(Long id){
        LikeableObjectLikesEvent likeableObjectLikesEvent = new LikeableObjectLikesEvent();
        likeableObjectLikesEvent.setObjectFound(false);
        likeableObjectLikesEvent.setLikeableObjId(id);

        return likeableObjectLikesEvent;
    }

    public Long getLikeableObjId() {
        return likeableObjId;
    }

    public void setLikeableObjId(Long likeableObjId) {
        this.likeableObjId = likeableObjId;
    }

    public boolean isObjectFound() {
        return objectFound;
    }

    public void setObjectFound(boolean objectFound) {
        this.objectFound = objectFound;
    }

    public Collection<UserDetails> getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(Collection<UserDetails> userDetails) {
        this.userDetails = userDetails;
    }
}
