package com.eulersbridge.iEngage.core.beans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Yikai Gong
 */

@Component
public class ParamBean {


  @Value("${exp.share}")
  public Integer expShare;
  @Value("${exp.readArticle}")
  public Integer expReadArticle;
  @Value("${exp.scanVoteQR}")
  public Integer expScanVoteQR;
  @Value("${exp.setVoteReminder}")
  public Integer expSetVoteReminder;
  @Value("${exp.login24Hours}")
  public Integer expLogin24Hours;
  @Value("${exp.compPerQuestion}")
  public Integer expCompPerQuestion;
  @Value("${exp.applyVolunteer}")
  public Integer expApplyVolunteer;
  @Value("${exp.addEventToCale}")
  public Integer expAddEventToCale;
  @Value("${exp.addFriend}")
  public Integer expAddFriend;
  @Value("${exp.inviteFriend}")
  public Integer expInviteFriend;
  @Value("${exp.postComment}")
  public Integer expPostComment;
  @Value("${exp.voteInPoll}")
  public Integer expVoteInPoll;

}
