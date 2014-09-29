package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.forumQuestions.*;
import com.eulersbridge.iEngage.database.domain.ForumQuestion;
import com.eulersbridge.iEngage.database.repository.ForumQuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yikai Gong
 */

public class ForumQuestionEventHandler implements ForumQuestionService {
    private static Logger LOG = LoggerFactory.getLogger(ForumQuestionService.class);

    private ForumQuestionRepository forumQuestionRepository;

    public ForumQuestionEventHandler(ForumQuestionRepository forumQuestionRepository){
        this.forumQuestionRepository = forumQuestionRepository;
    }

    @Override
    public ForumQuestionCreatedEvent createForumQuestion(CreateForumQuestionEvent createForumQuestionEvent) {
        ForumQuestionDetails forumQuestionDetails = createForumQuestionEvent.getForumQuestionDetails();
        ForumQuestion forumQuestion = ForumQuestion.fromForumQuestionDetails(forumQuestionDetails);
        ForumQuestion result = forumQuestionRepository.save(forumQuestion);
        ForumQuestionCreatedEvent forumQuestionCreatedEvent = new ForumQuestionCreatedEvent(result.getForumQuestionId(), result.toForumQuestionDetails());
        return forumQuestionCreatedEvent;
    }

    @Override
    public ReadForumQuestionEvent requestReadForumQuestion(RequestReadForumQuestionEvent requestReadForumQuestionEvent) {
        ForumQuestion forumQuestion = forumQuestionRepository.findOne(requestReadForumQuestionEvent.getForumQuestionId());
        ReadForumQuestionEvent readForumQuestionEvent;
        if(forumQuestion != null){
            readForumQuestionEvent = new ReadForumQuestionEvent(requestReadForumQuestionEvent.getForumQuestionId(), forumQuestion.toForumQuestionDetails());
        }
        else{
            readForumQuestionEvent = ReadForumQuestionEvent.notFound(requestReadForumQuestionEvent.getForumQuestionId());
        }
        return readForumQuestionEvent;
    }

    @Override
    public ForumQuestionUpdatedEvent updateForumQuestion(UpdateForumQuestionEvent updateForumQuestionEvent) {
        ForumQuestionDetails forumQuestionDetails = updateForumQuestionEvent.getForumQuestionDetails();
        ForumQuestion forumQuestion = ForumQuestion.fromForumQuestionDetails(forumQuestionDetails);
        Long forumQuestionId = forumQuestionDetails.getForumQuestionId();
        if(LOG.isDebugEnabled()) LOG.debug("forumQuestionId is " + forumQuestionId);
        ForumQuestion forumQuestionOld = forumQuestionRepository.findOne(forumQuestionId);
        if(forumQuestionOld == null){
            if(LOG.isDebugEnabled()) LOG.debug("forumQuestion entity not found " + forumQuestionId);
            return ForumQuestionUpdatedEvent.notFound(forumQuestionId);
        }
        else{
            ForumQuestion result = forumQuestionRepository.save(forumQuestion);
            if(LOG.isDebugEnabled()) LOG.debug("updated successfully" + result.getForumQuestionId());
            return new ForumQuestionUpdatedEvent(result.getForumQuestionId(), result.toForumQuestionDetails());
        }
    }

    @Override
    public ForumQuestionDeletedEvent deleteForumQuestion(DeleteForumQuestionEvent deleteForumQuestionEvent) {
        return null;
    }
}
