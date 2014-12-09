package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
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
        ForumQuestionDetails forumQuestionDetails = (ForumQuestionDetails) createForumQuestionEvent.getDetails();
        ForumQuestion forumQuestion = ForumQuestion.fromForumQuestionDetails(forumQuestionDetails);
        ForumQuestion result = forumQuestionRepository.save(forumQuestion);
        ForumQuestionCreatedEvent forumQuestionCreatedEvent = new ForumQuestionCreatedEvent(result.getForumQuestionId(), result.toForumQuestionDetails());
        return forumQuestionCreatedEvent;
    }

    @Override
    public ReadEvent requestReadForumQuestion(RequestReadForumQuestionEvent requestReadForumQuestionEvent) {
        ForumQuestion forumQuestion = forumQuestionRepository.findOne(requestReadForumQuestionEvent.getNodeId());
        ReadEvent readForumQuestionEvent;
        if(forumQuestion != null){
            readForumQuestionEvent = new ForumQuestionReadEvent(requestReadForumQuestionEvent.getNodeId(), forumQuestion.toForumQuestionDetails());
        }
        else{
            readForumQuestionEvent = ForumQuestionReadEvent.notFound(requestReadForumQuestionEvent.getNodeId());
        }
        return readForumQuestionEvent;
    }

    @Override
    public UpdatedEvent updateForumQuestion(UpdateForumQuestionEvent updateForumQuestionEvent) {
        ForumQuestionDetails forumQuestionDetails = (ForumQuestionDetails) updateForumQuestionEvent.getDetails();
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
    public DeletedEvent deleteForumQuestion(DeleteForumQuestionEvent deleteForumQuestionEvent) {
        if (LOG.isDebugEnabled()) LOG.debug("Entered deleteForumQuestionEvent= "+deleteForumQuestionEvent);
        Long forumQuestionId = deleteForumQuestionEvent.getNodeId();
        if (LOG.isDebugEnabled()) LOG.debug("deleteForumQuestion("+forumQuestionId+")");
        ForumQuestion forumQuestion = forumQuestionRepository.findOne(forumQuestionId);
        if(forumQuestion == null)
        {
            return ForumQuestionDeletedEvent.notFound(forumQuestionId);
        }
        else{
            forumQuestionRepository.delete(forumQuestionId);
            ForumQuestionDeletedEvent forumQuestionDeletedEvent = new ForumQuestionDeletedEvent(forumQuestionId);
            return forumQuestionDeletedEvent;
        }
    }
}
