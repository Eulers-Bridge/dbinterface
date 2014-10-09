package com.eulersbridge.iEngage.rest.domain;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.ResourceSupport;

import com.eulersbridge.iEngage.core.events.newsFeed.NewsFeedDetails;
import com.eulersbridge.iEngage.rest.controller.InstitutionController;

public class NewsFeed extends ResourceSupport
{
	Long nodeId;
	Long institutionId;
	
	public NewsFeed()
	{
		
	}
	
	public NewsFeed(Long institutionId)
	{
		this.institutionId=institutionId;
	}
	
	/**
	 * @return the institutionId
	 */
	public Long getInstitutionId() {
		return institutionId;
	}
	/**
	 * @param institutionId the institutionId to set
	 */
	public void setInstitutionId(Long institutionId) {
		this.institutionId = institutionId;
	}
	
	/**
	 * @return the nodeId
	 */
	public Long getNodeId() {
		return nodeId;
	}

	/**
	 * @param nodeId the nodeId to set
	 */
	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public NewsFeedDetails toNewsFeedDetails() 
	  {
		NewsFeedDetails details = new NewsFeedDetails(institutionId);
		details.setNodeId(nodeId);

	    return details;
	  }

	  // {!begin fromOrderDetails}
	  public static NewsFeed fromNewsFeedDetails(NewsFeedDetails readNewsFeed) 
	  {
		  NewsFeed newsFeed = new NewsFeed();

		  newsFeed.nodeId = readNewsFeed.getNodeId();
		  newsFeed.institutionId = readNewsFeed.getInstitutionId();
	    
		  String simpleName=Institution.class.getSimpleName();
		  String name=simpleName.substring(0, 1).toLowerCase()+simpleName.substring(1);
	    //TODOCUMENT.  Adding the library, the above extends ResourceSupport and
	    //this section is all that is actually needed in our model to add hateoas support.

	    //Much of the rest of the framework is helping deal with the blending of domains that happens in many spring apps
	    //We have explicitly avoided that.
	    // {!begin selfRel}
		  newsFeed.add(linkTo(InstitutionController.class).slash(name).slash(newsFeed.nodeId).withSelfRel());
	    // {!end selfRel}

	    return newsFeed;
	  }


}
