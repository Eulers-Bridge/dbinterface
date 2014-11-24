package com.eulersbridge.iEngage.core.events.forumQuestions;

import com.eulersbridge.iEngage.core.events.UpdatedEvent;

/**
 * @author Yikai Gong
 */

public class ForumQuestionUpdatedEvent extends UpdatedEvent
{
	public ForumQuestionUpdatedEvent(Long forumQuestionId,
			ForumQuestionDetails forumQuestionDetails)
	{
		super(forumQuestionId,forumQuestionDetails);
	}

	public ForumQuestionUpdatedEvent(Long forumQuestionId)
	{
		super(forumQuestionId);
	}
}
