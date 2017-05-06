package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.likes.LikeableDetails;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

// Ticket Pull User Likeable have numOfEntity method
public class Likeable extends Node {
  //    @Query("START n = node({self}) match (n)-[r:"+ DatabaseDomainConstants.LIKES_LABEL+"]-(c) RETURN count(c) ")
  @Relationship(type = DatabaseDomainConstants.LIKES_LABEL, direction = Relationship.INCOMING)
  private List<Node> likedUser;

  public Integer getNumOfLikes() {
    if (likedUser == null)
      return 0;
    return likedUser.size();
  }

  public List<Node> getLikedUser() {
    return likedUser;
  }

  public void setLikedUser(List<Node> likedUser) {
    this.likedUser = likedUser;
  }

  public LikeableDetails toDetails(LikeableDetails details) {
    details.setNumOfLikes(getNumOfLikes());
    return details;
  }
}
