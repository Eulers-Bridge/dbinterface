/**
 *
 */
package com.eulersbridge.iEngage.core.events.likes;

import com.eulersbridge.iEngage.core.events.Details;

/**
 * @author Greg Newitt
 */
public class LikeableDetails extends Details {
  private Integer numOfLikes;

  public LikeableDetails(Long nodeId) {
    super(nodeId);
  }

  public LikeableDetails() {
  }

  /**
   * @return the numOfLikes
   */
  public Integer getNumOfLikes() {
    return numOfLikes;
  }

  /**
   * @param numOfLikes the numOfLikes to set
   */
  public void setNumOfLikes(Integer numOfLikes) {
    this.numOfLikes = numOfLikes;
  }

}
