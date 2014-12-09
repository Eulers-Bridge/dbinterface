package com.eulersbridge.iEngage.core.events.forumQuestions;

import com.eulersbridge.iEngage.core.events.ReadEvent;

/**
 * @author Yikai Gong
 */

public class ForumQuestionReadEvent extends ReadEvent
{
	public ForumQuestionReadEvent(Long forumQuestionId)
	{
		super(forumQuestionId);
	}

	public ForumQuestionReadEvent(Long forumQuestionId,
			ForumQuestionDetails forumQuestionDetails)
	{
		super(forumQuestionId,forumQuestionDetails);
	}
}
