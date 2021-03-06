package com.eulersbridge.iEngage.rest.domain;

/**
 * @author Yikai Gong
 */

public class PollOptionDomain {
  private Long id;
  private String txt;
  private PhotoDomain photo;
  private Boolean voted = false;
  private Long numOfVoters;

  public PollOptionDomain() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTxt() {
    return txt;
  }

  public void setTxt(String txt) {
    this.txt = txt;
  }

  public PhotoDomain getPhoto() {
    return photo;
  }

  public void setPhoto(PhotoDomain photo) {
    this.photo = photo;
  }

  public Boolean getVoted() {
    return voted;
  }

  public void setVoted(Boolean voted) {
    this.voted = voted;
  }

  public Long getNumOfVoters() {
    return numOfVoters;
  }

  public void setNumOfVoters(Long numOfVoters) {
    this.numOfVoters = numOfVoters;
  }
}
