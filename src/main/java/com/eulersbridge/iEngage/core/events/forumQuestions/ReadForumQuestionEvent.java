package com.eulersbridge.iEngage.core.events.forumQuestions;

import com.eulersbridge.iEngage.core.events.RequestReadEvent;

/**
 * @author Yikai Gong
 */

public class ReadForumQuestionEvent extends RequestReadEvent
{
	public ReadForumQuestionEvent(Long forumQuestionId)
	{
		super(forumQuestionId);
	}
}
