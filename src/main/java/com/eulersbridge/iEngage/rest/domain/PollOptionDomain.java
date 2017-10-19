package com.eulersbridge.iEngage.rest.domain;

/**
 * @author Yikai Gong
 */

public class PollOptionDomain {
  private Long id;
  private String txt;
  private Photo photo;
  private Boolean voted = false;

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

  public Photo getPhoto() {
    return photo;
  }

  public void setPhoto(Photo photo) {
    this.photo = photo;
  }

  public Boolean getVoted() {
    return voted;
  }

  public void setVoted(Boolean voted) {
    this.voted = voted;
  }
}
