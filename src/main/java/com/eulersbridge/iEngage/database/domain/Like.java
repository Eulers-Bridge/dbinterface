package com.eulersbridge.iEngage.database.domain;

import org.neo4j.ogm.annotation.*;

import java.util.Calendar;


@RelationshipEntity(type = DataConstants.LIKES_LABEL)
public class Like {
  @Id
  @GeneratedValue
  private Long id;
  @StartNode
  private User liker;
  @EndNode
  private Likeable liked;

  private Long timeStamp;

  public Like() {
  }

  public Like(User liker, Likeable liked) {
    this.liker = liker;
    this.liked = liked;
    timeStamp = Calendar.getInstance().getTimeInMillis();
  }

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @return the liker
   */
  public User getLiker() {
    return liker;
  }

  /**
   * @param liker the liker to set
   */
  public void setLiker(User liker) {
    this.liker = liker;
  }

  /**
   * @return the liked
   */
  public Likeable getLiked() {
    return liked;
  }

  /**
   * @param liked the likee to set
   */
  public void setLiked(Likeable liked) {
    this.liked = liked;
  }

  /**
   * @return the timeStamp
   */
  public Long getTimeStamp() {
    return timeStamp;
  }

  /**
   * @param timeStamp the timeStamp to set
   */
  public void setTimeStamp(Long timeStamp) {
    this.timeStamp = timeStamp;
  }

  public void hello() {

  }
}
