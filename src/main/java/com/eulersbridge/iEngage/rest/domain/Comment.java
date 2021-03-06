package com.eulersbridge.iEngage.rest.domain;

import com.eulersbridge.iEngage.core.events.Details;
import com.eulersbridge.iEngage.core.events.comments.CommentDetails;
import com.eulersbridge.iEngage.core.events.photo.PhotoDetails;
import com.eulersbridge.iEngage.rest.controller.CommentController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.Iterator;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author Yikai Gong
 */

public class Comment extends ResourceSupport {
    private Long commentId;
    private Long targetId;
    private String userName;
    private String userEmail;
    private Long timestamp;
    private String content;
    private PhotoDetails profilePhotoDetails;

    private static Logger LOG = LoggerFactory.getLogger(Comment.class);

    public Comment() {
        if (LOG.isDebugEnabled()) LOG.debug("constructor()");
    }

    public static Comment fromCommentDetails(CommentDetails commentDetails){
        Comment comment = new Comment();
        String simpleName = Comment.class.getSimpleName();
        String name = simpleName.substring(0, 1).toLowerCase()
                + simpleName.substring(1);

        if (commentDetails!=null)
        {
            comment.setCommentId(commentDetails.getNodeId());
            comment.setTargetId(commentDetails.getTargetId());
            comment.setUserName(commentDetails.getUserName());
            comment.setUserEmail(commentDetails.getUserEmail());
            comment.setTimestamp(commentDetails.getTimestamp());
            comment.setContent(commentDetails.getContent());
            comment.setProfilePhotoDetails(commentDetails.getProfilePhotoDetails());
        }
        // {!begin selfRel}
        comment.add(linkTo(CommentController.class).slash(name)
                .slash(comment.getCommentId()).withSelfRel());
        // {!end selfRel}
        // {!begin readAll}
        comment.add(linkTo(CommentController.class).slash(name + 's').slash(commentDetails.getTargetId())
                .withRel(RestDomainConstants.READALL_LABEL));
        // {!end readAll}

        return comment;
    }

    public CommentDetails toCommentDetails(){
        CommentDetails commentDetails = new CommentDetails();
        commentDetails.setNodeId(commentId);
        commentDetails.setTargetId(targetId);
        commentDetails.setUserName(userName);
        commentDetails.setUserEmail(userEmail);
        commentDetails.setTimestamp(timestamp);
        commentDetails.setContent(content);
        return commentDetails;
    }

    public static Iterator<Comment> toCommentIterator(
            Iterator<? extends Details> iterator)
    {
        if (iterator == null) return null;
        ArrayList<Comment> comments = new ArrayList<Comment>();
        while(iterator.hasNext())
        {
            CommentDetails commentDetails = (CommentDetails) iterator.next();
            Comment comment = Comment.fromCommentDetails(commentDetails);
            Link self = comment.getLink("self");
            comment.removeLinks();
            comment.add(self);
            comments.add(comment);
        }
        return comments.iterator();
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /**
	 * @return the profilePhotoDetails
	 */
	public PhotoDetails getProfilePhotoDetails()
	{
		return profilePhotoDetails;
	}

	/**
	 * @param profilePhotoDetails the profilePhotoDetails to set
	 */
	public void setProfilePhotoDetails(PhotoDetails profilePhotoDetails)
	{
		this.profilePhotoDetails = profilePhotoDetails;
	}

	@Override
    public String toString()
    {
        return "Comment [commentId=" + commentId + ", targetId=" + targetId + ", username="
                + userName + ", userEmail=" + userEmail + ", timestamp="
                + timestamp + ", content=" + content +"]";
    }
}
