package com.eulersbridge.iEngage.core.events.forumQuestions;

import com.eulersbridge.iEngage.core.events.DeleteEvent;

/**
 * @author Yikai Gong
 */

public class DeleteForumQuestionEvent extends DeleteEvent
{
	public DeleteForumQuestionEvent(Long forumQuestionId)
	{
		super(forumQuestionId);
	}

	public Long getForumQuestionId()
	{
		return getNodeId();
	}
}
