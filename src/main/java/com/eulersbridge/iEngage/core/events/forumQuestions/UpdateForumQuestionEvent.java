package com.eulersbridge.iEngage.core.events.forumQuestions;

import com.eulersbridge.iEngage.core.events.UpdateEvent;

/**
 * @author Yikai Gong
 */

public class UpdateForumQuestionEvent extends UpdateEvent
{
	public UpdateForumQuestionEvent(Long forumQuestionId,
			ForumQuestionDetails forumQuestionDetails)
	{
		super(forumQuestionId, forumQuestionDetails);
	}
}
