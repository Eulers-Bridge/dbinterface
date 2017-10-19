package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.rest.domain.PollOptionDomain;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yikai Gong
 */

@NodeEntity
public class PollOption extends Node {
  private static final Logger LOG = LoggerFactory.getLogger(PollOption.class);

  private String txt;

  @Relationship(type = DataConstants.HAS_POLL_OPTION_LABEL, direction = Relationship.INCOMING)
  private Node poll;

  @Relationship(type = DataConstants.HAS_PHOTO_LABEL, direction = Relationship.OUTGOING)
  private Node attachedImage;

  @Relationship(type = DataConstants.VOTE_POLL_OPTION, direction = Relationship.INCOMING)
  private Node user;

  public PollOption() {
  }

  public String getTxt() {
    return txt;
  }

  public void setTxt(String txt) {
    this.txt = txt;
  }

  public Node getPoll() {
    return poll;
  }

  public Poll getPoll$() {
    if (poll == null)
      return null;
    if (poll instanceof Poll)
      return (Poll) poll;
    else
      return new Poll(poll.getNodeId());
  }

  public void setPoll(Node poll) {
    this.poll = poll;
  }

  public Node getAttachedImage() {
    return attachedImage;
  }

  public Node getUser() {
    return user;
  }

  public void setUser(Node user) {
    this.user = user;
  }

  public Photo getAttachedImage$() {
    if (attachedImage == null)
      return null;
    if (attachedImage instanceof Photo)
      return (Photo) attachedImage;
    else
      return new Photo(attachedImage.getNodeId());
  }

  public void setAttachedImage(Node attachedImage) {
    this.attachedImage = attachedImage;
  }

  public PollOptionDomain toDomainObj() {
    PollOptionDomain domain = new PollOptionDomain();
    domain.setId(getNodeId());
    domain.setTxt(getTxt());
    if (attachedImage != null) {
      Photo photo = getAttachedImage$();
      domain.setPhoto(com.eulersbridge.iEngage.rest.domain.Photo.fromPhotoDetails(photo.toPhotoDetails()));
    }
    if (user != null)
      domain.setVoted(true);
    return domain;
  }

  public static PollOption fromPollOptionDomain(PollOptionDomain domain) {
    PollOption option = new PollOption();
    option.setTxt(domain.getTxt());
    return option;
  }
}
