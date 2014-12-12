package com.eulersbridge.iEngage.core.services;

import java.util.ArrayList;
import java.util.Iterator;

import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.forumQuestions.*;
import com.eulersbridge.iEngage.database.domain.ForumQuestion;
import com.eulersbridge.iEngage.database.repository.ForumQuestionRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

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
        ForumQuestionCreatedEvent forumQuestionCreatedEvent = new ForumQuestionCreatedEvent(result.toForumQuestionDetails());
        return forumQuestionCreatedEvent;
    }

    @Override
    public ReadEvent readForumQuestion(ReadForumQuestionEvent readForumQuestionEvent) {
        ForumQuestion forumQuestion = forumQuestionRepository.findOne(readForumQuestionEvent.getNodeId());
        ReadEvent forumQuestionReadEvent;
        if(forumQuestion != null){
            forumQuestionReadEvent = new ForumQuestionReadEvent(readForumQuestionEvent.getNodeId(), forumQuestion.toForumQuestionDetails());
        }
        else
        {
        	forumQuestionReadEvent = ForumQuestionReadEvent.notFound(readForumQuestionEvent.getNodeId());
        }
        return forumQuestionReadEvent;
    }

    @Override
    public UpdatedEvent updateForumQuestion(UpdateForumQuestionEvent updateForumQuestionEvent) {
        ForumQuestionDetails forumQuestionDetails = (ForumQuestionDetails) updateForumQuestionEvent.getDetails();
        ForumQuestion forumQuestion = ForumQuestion.fromForumQuestionDetails(forumQuestionDetails);
        Long forumQuestionId = forumQuestionDetails.getNodeId();
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

	@Override
	public ForumQuestionsReadEvent findForumQuestions(
			ReadForumQuestionsEvent readForumQuestionsEvent,
			Direction sortDirection, int pageNumber, int pageLength)
	{
        if (LOG.isDebugEnabled()) LOG.debug("Entered findPhotos findPhotoEvent = "+readForumQuestionsEvent);
        Long ownerId = readForumQuestionsEvent.getOwnerId();
		Page <ForumQuestion>forumQuestions=null;
		ArrayList<ForumQuestionDetails> dets=new ArrayList<ForumQuestionDetails>();
        
		ForumQuestionsReadEvent result=null;
		
		if (LOG.isDebugEnabled()) LOG.debug("OwnerId "+ownerId);
		Pageable pageable=new PageRequest(pageNumber,pageLength,sortDirection,"p.date");
		forumQuestions=forumQuestionRepository.findByOwnerId(ownerId, pageable);

		if (forumQuestions!=null)
		{
			if (LOG.isDebugEnabled())
				LOG.debug("Total elements = "+forumQuestions.getTotalElements()+" total pages ="+forumQuestions.getTotalPages());
			Iterator<ForumQuestion> iter=forumQuestions.iterator();
			while (iter.hasNext())
			{
				ForumQuestion na=iter.next();
				if (LOG.isTraceEnabled()) LOG.trace("Converting to details - "+na.getQuestion());
				ForumQuestionDetails det=na.toForumQuestionDetails();
				dets.add(det);
			}
			if (0==dets.size())
			{
				// Need to check if we actually found ownerId.
				ForumQuestion inst=forumQuestionRepository.findOne(ownerId);
				if ( (null==inst) || (null==inst.getForumQuestionId()) )
				{
					if (LOG.isDebugEnabled()) LOG.debug("Null or null properties returned by findOne(ownerId)");
					result=ForumQuestionsReadEvent.institutionNotFound();
				}
				else
				{	
					result=new ForumQuestionsReadEvent(ownerId,dets,forumQuestions.getTotalElements(),forumQuestions.getTotalPages());
				}
			}
			else
			{	
				result=new ForumQuestionsReadEvent(ownerId,dets,forumQuestions.getTotalElements(),forumQuestions.getTotalPages());
			}
		}
		else
		{
			if (LOG.isDebugEnabled()) LOG.debug("Null returned by findByOwnerId");
			result=ForumQuestionsReadEvent.institutionNotFound();
		}
		return result;
	}
}
