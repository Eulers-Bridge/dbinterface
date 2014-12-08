package com.eulersbridge.iEngage.database.domain;

import com.eulersbridge.iEngage.core.events.forumQuestions.ForumQuestionDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

/**
 * @author Yikai Gong
 */

@NodeEntity
public class ForumQuestion
{
	@GraphId
	private Long forumQuestionId;
	private String question;

	private static Logger LOG = LoggerFactory.getLogger(ForumQuestion.class);

	public ForumQuestion()
	{
		if (LOG.isTraceEnabled()) LOG.trace("Constructor");
	}

	public static ForumQuestion fromForumQuestionDetails(
			ForumQuestionDetails forumQuestionDetails)
	{
		if (LOG.isTraceEnabled()) LOG.trace("fromForumQuestionDetails()");
		ForumQuestion forumQuestion = new ForumQuestion();
		if (LOG.isTraceEnabled())
			LOG.trace("forumQuestionDetails " + forumQuestionDetails);
		forumQuestion.setForumQuestionId(forumQuestionDetails
				.getNodeId());
		forumQuestion.setQuestion(forumQuestionDetails.getQuestion());

		if (LOG.isTraceEnabled()) LOG.trace("forumQuestion " + forumQuestion);
		return forumQuestion;
	}

	public ForumQuestionDetails toForumQuestionDetails()
	{
		if (LOG.isTraceEnabled()) LOG.trace("toForumQuestionDetails()");
		ForumQuestionDetails forumQuestionDetails = new ForumQuestionDetails();
		if (LOG.isTraceEnabled()) LOG.trace("forumQuestion " + this);
		forumQuestionDetails.setNodeId(this.getForumQuestionId());
		forumQuestionDetails.setQuestion(getQuestion());

		if (LOG.isTraceEnabled())
			LOG.trace("forumQuestionDetails; " + forumQuestionDetails);
		return forumQuestionDetails;
	}

	@Override
	public String toString()
	{
		StringBuffer buff = new StringBuffer("[ nodeId = ");
		String retValue;
		buff.append(getForumQuestionId());
		buff.append(", question = ");
		buff.append(getQuestion());
		buff.append(" ]");
		retValue = buff.toString();
		if (LOG.isDebugEnabled()) LOG.debug("toString() = " + retValue);
		return retValue;
	}

	public Long getForumQuestionId()
	{
		return forumQuestionId;
	}

	public void setForumQuestionId(Long forumQuestionId)
	{
		this.forumQuestionId = forumQuestionId;
	}

	public String getQuestion()
	{
		return question;
	}

	public void setQuestion(String question)
	{
		this.question = question;
	}
}
