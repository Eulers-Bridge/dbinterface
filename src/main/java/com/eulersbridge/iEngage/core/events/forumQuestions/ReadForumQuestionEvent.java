package com.eulersbridge.iEngage.core.events.forumQuestions;

import com.eulersbridge.iEngage.core.events.ReadEvent;

/**
 * @author Yikai Gong
 */

public class ReadForumQuestionEvent extends ReadEvent
{
	public ReadForumQuestionEvent(Long forumQuestionId)
	{
		super(forumQuestionId);
	}

	public ReadForumQuestionEvent(Long forumQuestionId,
			ForumQuestionDetails forumQuestionDetails)
	{
		super(forumQuestionId,forumQuestionDetails);
	}

	public Long getForumQuestionId()
	{
		return getNodeId();
	}

	public ForumQuestionDetails getForumQuestionDetails()
	{
		return (ForumQuestionDetails)getDetails();
	}
}
