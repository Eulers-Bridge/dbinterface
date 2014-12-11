package com.eulersbridge.iEngage.core.events.forumQuestions;

import com.eulersbridge.iEngage.core.events.CreatedEvent;

/**
 * @author Yikai Gong
 */

public class ForumQuestionCreatedEvent extends CreatedEvent 
{
    public ForumQuestionCreatedEvent(ForumQuestionDetails forumQuestionDetails) 
    {
    	super(forumQuestionDetails);
    }
}
