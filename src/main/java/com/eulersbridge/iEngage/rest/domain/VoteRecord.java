/**
 * 
 */
package com.eulersbridge.iEngage.rest.domain;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.ResourceSupport;

import com.eulersbridge.iEngage.core.events.voteRecord.VoteRecordDetails;
import com.eulersbridge.iEngage.rest.controller.UserController;

/**
 * @author Greg Newitt
 *
 */
public class VoteRecord extends ResourceSupport
{
	private Long nodeId;
	private String userEmail;
	private Long electionId;
	private Long date;
	private String location;
	private String qrCode;

	public VoteRecordDetails toVoteRecordDetails()
	{
		VoteRecordDetails vrd=new VoteRecordDetails(nodeId, userEmail, electionId, date, location,qrCode);
		return vrd;
	}

	public static VoteRecord fromVoteRecordDetails(
			VoteRecordDetails voteRecordDetails)
	{
		VoteRecord vr=new VoteRecord();
		vr.setUserEmail(voteRecordDetails.getVoterId());
		vr.setDate(voteRecordDetails.getDate());
		vr.setLocation(voteRecordDetails.getLocation());
		vr.setQrCode(voteRecordDetails.getQrCode());
		vr.setNodeId(voteRecordDetails.getNodeId());
		vr.setElectionId(voteRecordDetails.getElectionId());

	    String simpleName=User.class.getSimpleName();
	    String name=simpleName.substring(0, 1).toLowerCase()+simpleName.substring(1);

	    //TODOCUMENT.  Adding the library, the above extends ResourceSupport and
	    //this section is all that is actually needed in our model to add hateoas support.

	    //Much of the rest of the framework is helping deal with the blending of domains that happens in many spring apps
	    //We have explicitly avoided that.
	    // {!begin selfRel}
	    vr.add(linkTo(UserController.class).slash(name).slash(vr.getUserEmail()).slash("voteRecord").withSelfRel());
	    // {!end selfRel}
	    return vr;
	}

/**
	 * @return the nodeId
	 */
	public Long getNodeId()
	{
		return nodeId;
	}
	/**
	 * @param nodeId the nodeId to set
	 */
	public void setNodeId(Long nodeId)
	{
		this.nodeId = nodeId;
	}
	/**
	 * @return the userEmail
	 */
	public String getUserEmail()
	{
		return userEmail;
	}
	/**
	 * @param userEmail the userEmail to set
	 */
	public void setUserEmail(String userEmail)
	{
		this.userEmail = userEmail;
	}
	/**
	 * @return the electionId
	 */
	public Long getElectionId()
	{
		return electionId;
	}
	/**
	 * @param electionId the electionId to set
	 */
	public void setElectionId(Long electionId)
	{
		this.electionId = electionId;
	}
	/**
	 * @return the date
	 */
	public Long getDate()
	{
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(Long date)
	{
		this.date = date;
	}
	/**
	 * @return the location
	 */
	public String getLocation()
	{
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(String location)
	{
		this.location = location;
	}

	/**
	 * @return the qrCode
	 */
	public String getQrCode()
	{
		return qrCode;
	}

	/**
	 * @param qrCode the qrCode to set
	 */
	public void setQrCode(String qrCode)
	{
		this.qrCode = qrCode;
	}
}
