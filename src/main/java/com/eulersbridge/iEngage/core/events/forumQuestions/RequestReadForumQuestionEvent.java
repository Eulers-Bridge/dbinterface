package com.eulersbridge.iEngage.core.events.forumQuestions;

import com.eulersbridge.iEngage.core.events.RequestReadEvent;

/**
 * @author Yikai Gong
 */

public class RequestReadForumQuestionEvent extends RequestReadEvent
{
	public RequestReadForumQuestionEvent(Long forumQuestionId)
	{
		super(forumQuestionId);
	}
}
