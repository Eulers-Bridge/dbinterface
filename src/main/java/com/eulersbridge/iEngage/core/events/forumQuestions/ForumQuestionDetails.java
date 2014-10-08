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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		if (forumQuestionId!=null)
		{
			result = prime * result	+ forumQuestionId.hashCode();
		}
		else
		{
			result = prime * result	+ ((question == null) ? 0 : question.hashCode());
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ForumQuestionDetails other = (ForumQuestionDetails) obj;
		if (forumQuestionId != null) 
		{
			if (forumQuestionId.equals(other.forumQuestionId))
				return true;
			else return false;
		}
		else
		{
			if (other.forumQuestionId != null)
				return false;
			if (question == null) {
				if (other.question != null)
					return false;
			} else if (!question.equals(other.question))
				return false;
		}
		return true;
	}
}
