package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.likes.LikeableDetails;
import com.google.common.collect.Iterables;
import org.neo4j.ogm.annotation.Relationship;

// Ticket Pull User Likeable have numOfEntity method
public class Likeable extends Node {
  //    @Query("START n = node({self}) match (n)-[r:"+ DatabaseDomainConstants.LIKES_LABEL+"]-(c) RETURN count(c) ")
  // TODO: Set<> seems more flexible for mapping graph into nested pojo
  @Relationship(type = DatabaseDomainConstants.LIKES_LABEL, direction = Relationship.INCOMING)
  private Iterable<Node> likedUser;

  public Integer getNumOfLikes() {
    if (likedUser == null)
      return 0;
    return Iterables.size(likedUser);
  }

  public Iterable<Node> getLikedUser() {
    return likedUser;
  }

  public void setLikedUser(Iterable<Node> likedUser) {
    this.likedUser = likedUser;
  }

  public LikeableDetails toDetails(LikeableDetails details) {
    details.setNumOfLikes(getNumOfLikes());
    return details;
  }
}
