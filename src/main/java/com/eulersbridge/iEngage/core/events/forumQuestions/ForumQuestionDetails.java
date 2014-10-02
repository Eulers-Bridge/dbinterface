package com.eulersbridge.iEngage.core.events.forumQuestions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yikai Gong
 */

public class ForumQuestionDetails {
    private Long forumQuestionId;
    private String question;

    private static Logger LOG = LoggerFactory.getLogger(ForumQuestionDetails.class);

    @Override
    public String toString(){
        StringBuffer buff = new StringBuffer("[ id = ");
        String retValue;
        buff.append(getForumQuestionId());
        buff.append(", question = ");
        buff.append(getQuestion());
        buff.append(" ]");
        retValue = buff.toString();
        if (LOG.isDebugEnabled()) LOG.debug("toString() = "+retValue);
        return retValue;
    }

    @Override
    public boolean equals(Object other){
        if (null == other) return false;
        if (other ==this) return true;
        if(!(other instanceof ForumQuestionDetails)) return false;
        ForumQuestionDetails forumQuestionDetails = (ForumQuestionDetails) other;
        if (forumQuestionDetails.getForumQuestionId() != null){
            if(forumQuestionDetails.getForumQuestionId().equals(getForumQuestionId()))
                return true;
        }
        return false;
    }

    public Long getForumQuestionId() {
        return forumQuestionId;
    }

    public void setForumQuestionId(Long forumQuestionId) {
        this.forumQuestionId = forumQuestionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
