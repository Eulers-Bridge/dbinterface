package com.eulersbridge.iEngage.core.events.users;

import com.eulersbridge.iEngage.rest.domain.UserProfile;

import java.util.List;

/**
 * @author Yikai Gong
 */

public class SearchUserEvent {
    String queryString;
    List<UserProfile> userProfileList;

    public SearchUserEvent(String queryString, List<UserProfile> userProfileList) {
        this.queryString = queryString;
        this.userProfileList = userProfileList;
    }

    public String getQueryString() {
        return queryString;
    }

    public List<UserProfile> getUserProfileList() {
        return userProfileList;
    }
}
