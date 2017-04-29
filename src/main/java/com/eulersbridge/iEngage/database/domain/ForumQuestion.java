package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.forumQuestions.ForumQuestionDetails;
import org.neo4j.ogm.annotation.NodeEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yikai Gong
 */

@NodeEntity
public class ForumQuestion extends Likeable {

  private String question;

  private static Logger LOG = LoggerFactory.getLogger(ForumQuestion.class);

  public ForumQuestion() {
    if (LOG.isTraceEnabled()) LOG.trace("Constructor");
  }

  public static ForumQuestion fromForumQuestionDetails(
    ForumQuestionDetails forumQuestionDetails) {
    if (LOG.isTraceEnabled()) LOG.trace("fromForumQuestionDetails()");
    ForumQuestion forumQuestion = new ForumQuestion();
    if (LOG.isTraceEnabled())
      LOG.trace("forumQuestionDetails " + forumQuestionDetails);
    forumQuestion.setNodeId(forumQuestionDetails
      .getNodeId());
    forumQuestion.setQuestion(forumQuestionDetails.getQuestion());

    if (LOG.isTraceEnabled()) LOG.trace("forumQuestion " + forumQuestion);
    return forumQuestion;
  }

  public ForumQuestionDetails toForumQuestionDetails() {
    if (LOG.isTraceEnabled()) LOG.trace("toForumQuestionDetails()");
    ForumQuestionDetails forumQuestionDetails = new ForumQuestionDetails();
    if (LOG.isTraceEnabled()) LOG.trace("forumQuestion " + this);
    forumQuestionDetails.setNodeId(this.getNodeId());
    forumQuestionDetails.setQuestion(getQuestion());

    if (LOG.isTraceEnabled())
      LOG.trace("forumQuestionDetails; " + forumQuestionDetails);
    return forumQuestionDetails;
  }

  @Override
  public String toString() {
    String buff = "[ nodeId = " + getNodeId() +
      ", question = " +
      getQuestion() +
      " ]";
    if (LOG.isDebugEnabled()) LOG.debug("toString() = " + buff);
    return buff;
  }


  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }
}
