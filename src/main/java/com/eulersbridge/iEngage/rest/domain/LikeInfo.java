package com.eulersbridge.iEngage.rest.domain;

import com.eulersbridge.iEngage.core.events.users.UserDetails;

/**
 * @author Yikai Gong
 */

public class LikeInfo {
    private String givenName;
    private String familyName;
    private String email;

    public LikeInfo() {
        super();
    }

    public static LikeInfo fromUserDetails(UserDetails userDetails){
        LikeInfo likeInfo = new LikeInfo();
        likeInfo.setFamilyName(userDetails.getFamilyName());
        likeInfo.setGivenName(userDetails.getGivenName());
        likeInfo.setEmail(userDetails.getEmail());
        return likeInfo;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
