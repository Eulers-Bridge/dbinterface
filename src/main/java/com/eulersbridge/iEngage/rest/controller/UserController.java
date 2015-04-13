package com.eulersbridge.iEngage.rest.controller;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;

import com.eulersbridge.iEngage.core.events.AllReadEvent;
import com.eulersbridge.iEngage.core.events.CreatedEvent;
import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadAllEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.services.LikesService;
import com.eulersbridge.iEngage.rest.domain.*;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.LongValidator;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.exception.VelocityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eulersbridge.iEngage.core.events.users.CreateUserEvent;
import com.eulersbridge.iEngage.core.events.users.DeleteUserEvent;
import com.eulersbridge.iEngage.core.events.users.ReadUserEvent;
import com.eulersbridge.iEngage.core.events.users.RequestReadUserEvent;
import com.eulersbridge.iEngage.core.events.users.UpdateUserEvent;
import com.eulersbridge.iEngage.core.events.users.UserAccountVerifiedEvent.VerificationErrorType;
import com.eulersbridge.iEngage.core.events.users.UserCreatedEvent;
import com.eulersbridge.iEngage.core.events.users.UserDeletedEvent;
import com.eulersbridge.iEngage.core.events.users.UserDetails;
import com.eulersbridge.iEngage.core.events.users.UserAccountVerifiedEvent;
import com.eulersbridge.iEngage.core.events.users.VerifyUserAccountEvent;
import com.eulersbridge.iEngage.core.events.users.AddPersonalityEvent;
import com.eulersbridge.iEngage.core.events.users.PersonalityAddedEvent;
import com.eulersbridge.iEngage.core.events.voteRecord.AddVoteRecordEvent;
import com.eulersbridge.iEngage.core.events.voteRecord.DeleteVoteRecordEvent;
import com.eulersbridge.iEngage.core.events.voteRecord.ReadVoteRecordEvent;
import com.eulersbridge.iEngage.core.events.voteRecord.VoteRecordAddedEvent;
import com.eulersbridge.iEngage.core.events.voteRecord.VoteRecordDetails;
import com.eulersbridge.iEngage.core.events.voteReminder.AddVoteReminderEvent;
import com.eulersbridge.iEngage.core.events.voteReminder.DeleteVoteReminderEvent;
import com.eulersbridge.iEngage.core.events.voteReminder.ReadVoteReminderEvent;
import com.eulersbridge.iEngage.core.events.voteReminder.VoteReminderAddedEvent;
import com.eulersbridge.iEngage.core.events.voteReminder.VoteReminderDetails;
import com.eulersbridge.iEngage.core.services.ContactRequestService;
import com.eulersbridge.iEngage.core.services.EmailService;
import com.eulersbridge.iEngage.core.services.NotificationService;
import com.eulersbridge.iEngage.core.services.UserService;
import com.eulersbridge.iEngage.email.EmailConstants;

@RestController
@RequestMapping(ControllerConstants.API_PREFIX)
public class UserController
{

    @Autowired UserService userService;
    @Autowired EmailService emailService;
    @Autowired LikesService likesService;
    @Autowired ContactRequestService contactRequestService;
    @Autowired NotificationService notificationService;
    @Autowired ServletContext servletContext;
    @Autowired VelocityEngine velocityEngine;
	@Autowired JavaMailSender emailSender;
	
	EmailValidator emailValidator;
	LongValidator longValidator;
    
	public UserController() 
	{
		if (LOG.isDebugEnabled()) LOG.debug("UserController()");
		emailValidator=EmailValidator.getInstance();
		longValidator=LongValidator.getInstance();
	}

    private static Logger LOG = LoggerFactory.getLogger(UserController.class);

    /**
     * Is passed all the necessary data to update a user.
     * Or potentially create a new one.
     * The request must be a PUT with the necessary parameters in the
     * attached data.
     * <p/>
     * This method will return the resulting user object. 
     * There will also be a relationship set up with the 
     * institution the user belongs to.
     * 
     * @param email the email address of the user to be updated.
     * @param user the user object passed across as JSON.
     * @return the user object returned by the Graph Database.
     * 

	*/
    
    @RequestMapping(method=RequestMethod.PUT,value=ControllerConstants.USER_LABEL+"/{email}")
    public @ResponseBody ResponseEntity<User> alterUser(@PathVariable String email,
    		@RequestBody User user) 
    {
    	if (LOG.isInfoEnabled()) LOG.info("Attempting to edit user. "+user.getEmail());
    	ResponseEntity<User> result;
    	
    	UpdatedEvent userEvent=userService.updateUser(new UpdateUserEvent(email,user.toUserDetails()));
    	if (null!=userEvent)
    	{
   			if(LOG.isDebugEnabled()) LOG.debug("userEvent - "+userEvent);
	    	if (!userEvent.isEntityFound())
	    	{
	    		result=new ResponseEntity<User>(HttpStatus.FAILED_DEPENDENCY);
	    	}
	    	else
	    	{
		    	User restUser=User.fromUserDetails((UserDetails) userEvent.getDetails());
		    	if (LOG.isDebugEnabled()) LOG.debug("restUser = "+restUser);
		    	result=new ResponseEntity<User>(restUser,HttpStatus.OK);
	    	}
    	}
    	else
          	result=new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
    	return result;
    }
    
    /**
     * Is passed all the necessary data to update a user.
     * Or potentially create a new one.
     * The request must be a PUT with the necessary parameters in the
     * attached data.
     * <p/>
     * This method will return the resulting user object. 
     * There will also be a relationship set up with the 
     * institution the user belongs to.
     * 
     * @param email the email address of the user to be updated.
     * @param user the user object passed across as JSON.
     * @return the user object returned by the Graph Database.
     * 

	*/
    
    @RequestMapping(method=RequestMethod.PUT,value=ControllerConstants.USER_LABEL+"/{email}/personality")
    public @ResponseBody ResponseEntity<Personality> addUserPersonality(@PathVariable String email,
    		@RequestBody Personality personality) 
    {
    	ResponseEntity<Personality> result;
    	if (LOG.isInfoEnabled()) LOG.info("Attempting to add personality to user. ");
    	if (LOG.isDebugEnabled()) LOG.debug("Personality - "+personality);
    	
    	AddPersonalityEvent addEvt=new AddPersonalityEvent(email,personality.toPersonalityDetails());
    	if (LOG.isDebugEnabled()) LOG.debug("AddPersonalityEvent - "+addEvt);
    	
    	PersonalityAddedEvent persEvent=userService.addPersonality(addEvt);
    	if (persEvent!=null)
    	{
    		if (LOG.isDebugEnabled()) LOG.debug("personalityEvent - "+persEvent);
	    	if (persEvent.isUserFound())
	       	{
		    	Personality restPersonality=Personality.fromPersonalityDetails(persEvent.getPersonalityDetails());
		    	if (LOG.isDebugEnabled()) LOG.debug("restUser = "+restPersonality);
		      	result= new ResponseEntity<Personality>(restPersonality,HttpStatus.CREATED);
	    	}
	    	else
	    	{
	    		result=new ResponseEntity<Personality>(HttpStatus.FAILED_DEPENDENCY);
	    	}
    	}
    	else
    	{
    		if (LOG.isWarnEnabled()) LOG.warn("personalityEvent null");
    		result=new ResponseEntity<Personality>(HttpStatus.BAD_REQUEST);
    	}
    	return result;
    }
    
    /**
     * Is passed all the necessary data to create a vote reminder.
     * The request must be a PUT with the necessary parameters in the
     * attached data.
     * <p/>
     * This method will return the resulting vote reminder object. 
     * 
     * @param email the email address of the user to be updated.
     * @param user the user object passed across as JSON.
     * @return the user object returned by the Graph Database.
     * 

	*/
    
    @RequestMapping(method=RequestMethod.PUT,value=ControllerConstants.USER_LABEL+"/{email}"+ControllerConstants.VOTE_REMINDER_LABEL)
    public @ResponseBody ResponseEntity<VoteReminder> addVoteReminder(@PathVariable String email,
    		@RequestBody VoteReminder voteReminder) 
    {
    	ResponseEntity<VoteReminder> result;
    	if (LOG.isInfoEnabled()) LOG.info("Attempting to add vote reminder to user. ");
    	if (LOG.isDebugEnabled()) LOG.debug("VoteReminder - "+voteReminder);
    	
    	VoteReminderDetails remDetails = voteReminder.toVoteReminderDetails();
    	remDetails.setUserId(email);
    	AddVoteReminderEvent addEvt=new AddVoteReminderEvent(remDetails);
    	if (LOG.isDebugEnabled()) LOG.debug("AddPersonalityEvent - "+addEvt);
    	
    	CreatedEvent vrEvent=userService.addVoteReminder(addEvt);
    	if (vrEvent!=null)
    	{
    		if (LOG.isDebugEnabled()) LOG.debug("personalityEvent - "+vrEvent);
    		if (vrEvent.isFailed())
    			result=new ResponseEntity<VoteReminder>(HttpStatus.BAD_REQUEST);
    		else if (((VoteReminderAddedEvent)vrEvent).isUserFound())
	       	{
	    		VoteReminder restVoteReminder=VoteReminder.fromVoteReminderDetails((VoteReminderDetails) vrEvent.getDetails());
		    	if (LOG.isDebugEnabled()) LOG.debug("restUser = "+restVoteReminder);
		      	result= new ResponseEntity<VoteReminder>(restVoteReminder,HttpStatus.CREATED);
	    	}
	    	else
	    	{
	    		result=new ResponseEntity<VoteReminder>(HttpStatus.FAILED_DEPENDENCY);
	    	}
    	}
    	else
    	{
    		if (LOG.isWarnEnabled()) LOG.warn("voteReminderEvent null");
    		result=new ResponseEntity<VoteReminder>(HttpStatus.BAD_REQUEST);
    	}
    	return result;
    }
    
    /**
     * Is passed all the necessary data to create a vote record.
     * The request must be a PUT with the necessary parameters in the
     * attached data.
     * <p/>
     * This method will return the resulting vote record object. 
     * 
     * @param email the email address of the user to be updated.
     * @param user the user object passed across as JSON.
     * @return the user object returned by the Graph Database.
     * 

	*/
    
    @RequestMapping(method=RequestMethod.PUT,value=ControllerConstants.USER_LABEL+"/{email}"+ControllerConstants.VOTE_RECORD_LABEL)
    public @ResponseBody ResponseEntity<VoteRecord> addVoteRecord(@PathVariable String email,
    		@RequestBody VoteRecord voteRecord) 
    {
    	ResponseEntity<VoteRecord> result;
    	if (LOG.isInfoEnabled()) LOG.info("Attempting to add vote record to user. ");
    	if (LOG.isDebugEnabled()) LOG.debug("VoteReminder - "+voteRecord);
    	
    	VoteRecordDetails recDetails = voteRecord.toVoteRecordDetails();
    	recDetails.setVoterId(email);
    	AddVoteRecordEvent addEvt=new AddVoteRecordEvent(recDetails);
    	if (LOG.isDebugEnabled()) LOG.debug("AddVoteRecordEvent - "+addEvt);
    	
    	VoteRecordAddedEvent vrEvent=userService.addVoteRecord(addEvt);
    	if (vrEvent!=null)
    	{
    		if (LOG.isDebugEnabled()) LOG.debug("personalityEvent - "+vrEvent);
	    	if (vrEvent.isUserFound())
	       	{
	    		VoteRecord restVoteRecord=VoteRecord.fromVoteRecordDetails((VoteRecordDetails) vrEvent.getDetails());
		    	if (LOG.isDebugEnabled()) LOG.debug("restUser = "+restVoteRecord);
		      	result= new ResponseEntity<VoteRecord>(restVoteRecord,HttpStatus.CREATED);
	    	}
	    	else
	    	{
	    		result=new ResponseEntity<VoteRecord>(HttpStatus.FAILED_DEPENDENCY);
	    	}
    	}
    	else
    	{
    		if (LOG.isWarnEnabled()) LOG.warn("voteRecordEvent null");
    		result=new ResponseEntity<VoteRecord>(HttpStatus.BAD_REQUEST);
    	}
    	return result;
    }
    
	/**
	 * Is passed all the necessary data to read voteReminders from the database. The
	 * request must be a GET with the userId.
	 * <p/>
	 * This method will return the voteReminders read from the database.
	 * 
	 * @param userId
	 *            the userId who has the voteReminder objects to be read.
	 * @return the contacts.
	 * 
	 */
	@RequestMapping(method=RequestMethod.GET,value=ControllerConstants.USER_LABEL+"/{email}"+ControllerConstants.VOTE_REMINDERS_LABEL)
	public @ResponseBody ResponseEntity<Iterator<VoteReminder>> findVoteReminders(@PathVariable String email,
			@RequestParam(value = "direction", required = false, defaultValue = ControllerConstants.DIRECTION) String direction,
			@RequestParam(value = "page", required = false, defaultValue = ControllerConstants.PAGE_NUMBER) String page,
			@RequestParam(value = "pageSize", required = false, defaultValue = ControllerConstants.PAGE_LENGTH) String pageSize
			) 
	{
		int pageNumber = 0;
		int pageLength = 10;
		pageNumber = Integer.parseInt(page);
		pageLength = Integer.parseInt(pageSize);
		Direction sortDirection = Direction.DESC;
		if (direction.equalsIgnoreCase("asc")) sortDirection = Direction.ASC;
		if (LOG.isInfoEnabled()) LOG.info("Attempting to find existing vote reminders. "+email);

		ReadAllEvent userEvent;
		AllReadEvent voteRemindersEvent;
		ResponseEntity<Iterator<VoteReminder>> result;
		
		if (longValidator.isValid(email))
		{
			Long id=longValidator.validate(email);
			if (LOG.isDebugEnabled()) LOG.debug("UserId supplied. - "+id);
			userEvent =new ReadAllEvent(id);
			voteRemindersEvent=userService.readVoteRemindersById(userEvent, sortDirection, pageNumber, pageLength);
		}
		else if (emailValidator.isValid(email))
		{
			if (LOG.isDebugEnabled()) LOG.debug("Email supplied.");
			voteRemindersEvent=userService.readVoteRemindersByEmail(new RequestReadUserEvent(email), sortDirection, pageNumber, pageLength);
		}
		else
			return new ResponseEntity<Iterator<VoteReminder>>(HttpStatus.BAD_REQUEST);
			

		if (!voteRemindersEvent.isEntityFound())
		{
			return new ResponseEntity<Iterator<VoteReminder>>(HttpStatus.NOT_FOUND);
		}
		Iterator<VoteReminder> contactProfiles = VoteReminder.toVoteRemindersIterator(voteRemindersEvent.getDetails().iterator());

		result = new ResponseEntity<Iterator<VoteReminder>>(contactProfiles, HttpStatus.OK);
		
		return result;
	}
    	
	/**
	 * Is passed all the necessary data to read voteRecords from the database. The
	 * request must be a GET with the userId.
	 * <p/>
	 * This method will return the voteRecords read from the database.
	 * 
	 * @param userId
	 *            the userId who has the voteRecord objects to be read.
	 * @return the contacts.
	 * 
	 */
	@RequestMapping(method=RequestMethod.GET,value=ControllerConstants.USER_LABEL+"/{email}"+ControllerConstants.VOTE_RECORDS_LABEL)
	public @ResponseBody ResponseEntity<Iterator<VoteRecord>> findVoteRecords(@PathVariable String email,
			@RequestParam(value = "direction", required = false, defaultValue = ControllerConstants.DIRECTION) String direction,
			@RequestParam(value = "page", required = false, defaultValue = ControllerConstants.PAGE_NUMBER) String page,
			@RequestParam(value = "pageSize", required = false, defaultValue = ControllerConstants.PAGE_LENGTH) String pageSize
			) 
	{
		int pageNumber = 0;
		int pageLength = 10;
		pageNumber = Integer.parseInt(page);
		pageLength = Integer.parseInt(pageSize);
		Direction sortDirection = Direction.DESC;
		if (direction.equalsIgnoreCase("asc")) sortDirection = Direction.ASC;
		if (LOG.isInfoEnabled()) LOG.info("Attempting to find existing vote records. "+email);

		ReadAllEvent userEvent;
		AllReadEvent voteRecordsEvent;
		ResponseEntity<Iterator<VoteRecord>> result;
		
		if (longValidator.isValid(email))
		{
			Long id=longValidator.validate(email);
			if (LOG.isDebugEnabled()) LOG.debug("UserId supplied. - "+id);
			userEvent =new ReadAllEvent(id);
			voteRecordsEvent=userService.readVoteRecordsById(userEvent, sortDirection, pageNumber, pageLength);
		}
		else if (emailValidator.isValid(email))
		{
			if (LOG.isDebugEnabled()) LOG.debug("Email supplied.");
			voteRecordsEvent=userService.readVoteRecordsByEmail(new RequestReadUserEvent(email), sortDirection, pageNumber, pageLength);
		}
		else
			return new ResponseEntity<Iterator<VoteRecord>>(HttpStatus.BAD_REQUEST);
			

		if (!voteRecordsEvent.isEntityFound())
		{
			return new ResponseEntity<Iterator<VoteRecord>>(HttpStatus.NOT_FOUND);
		}
		Iterator<VoteRecord> contactProfiles = VoteRecord.toVoteRecordsIterator(voteRecordsEvent.getDetails().iterator());

		result = new ResponseEntity<Iterator<VoteRecord>>(contactProfiles, HttpStatus.OK);
		
		return result;
	}
    	
    /**
     * Is passed all the necessary data to read a vote reminder from the database.
     * The request must be a GET with the vote record id presented
     * as the final portion of the URL.
     * <p/>
     * This method will return the voteReminder object read from the database.
     * 
     * @param email the vote reminder id of the vote reminder object to be read.
     * @return the vote reminder object.
     * 

	*/
	@RequestMapping(method=RequestMethod.GET,value=ControllerConstants.USER_LABEL+ControllerConstants.VOTE_REMINDER_LABEL+"/{id}")
	public @ResponseBody ResponseEntity<VoteReminder> findVoteReminder(@PathVariable Long id) 
	{
		if (LOG.isInfoEnabled()) LOG.info("Attempting to retrieve voteReminder. "+id);
		ReadEvent evt=userService.readVoteReminder(new ReadVoteReminderEvent(id));
  	
		if (!evt.isEntityFound())
		{
			return new ResponseEntity<VoteReminder>(HttpStatus.NOT_FOUND);
		}
		VoteReminder restVoteReminder=VoteReminder.fromVoteReminderDetails((VoteReminderDetails) evt.getDetails());
		return new ResponseEntity<VoteReminder>(restVoteReminder,HttpStatus.OK);
	}
    
    /**
     * Is passed all the necessary data to read a vote record from the database.
     * The request must be a GET with the vote record id presented
     * as the final portion of the URL.
     * <p/>
     * This method will return the voteRecord object read from the database.
     * 
     * @param email the vote record id of the vote record object to be read.
     * @return the vote record object.
     * 

	*/
	@RequestMapping(method=RequestMethod.GET,value=ControllerConstants.USER_LABEL+ControllerConstants.VOTE_RECORD_LABEL+"/{id}")
	public @ResponseBody ResponseEntity<VoteRecord> findVoteRecord(@PathVariable Long id) 
	{
		if (LOG.isInfoEnabled()) LOG.info("Attempting to retrieve voteRecord. "+id);
		ReadEvent evt=userService.readVoteRecord(new ReadVoteRecordEvent(id));
  	
		if (!evt.isEntityFound())
		{
			return new ResponseEntity<VoteRecord>(HttpStatus.NOT_FOUND);
		}
		VoteRecord restVoteRecord=VoteRecord.fromVoteRecordDetails((VoteRecordDetails) evt.getDetails());
		return new ResponseEntity<VoteRecord>(restVoteRecord,HttpStatus.OK);
	}
    
    /**
     * Is passed all the necessary data to read a vote reminder from the database.
     * The request must be a GET with the vote record id presented
     * as the final portion of the URL.
     * <p/>
     * This method will return the voteReminder object read from the database.
     * 
     * @param email the vote reminder id of the vote reminder object to be read.
     * @return the vote reminder object.
     * 

	*/
	@RequestMapping(method=RequestMethod.DELETE,value=ControllerConstants.USER_LABEL+ControllerConstants.VOTE_REMINDER_LABEL+"/{id}")
	public @ResponseBody ResponseEntity<VoteReminder> deleteVoteReminder(@PathVariable Long id) 
	{
		if (LOG.isInfoEnabled()) LOG.info("Attempting to delete voteReminder. "+id);
		DeletedEvent evt=userService.deleteVoteReminder(new DeleteVoteReminderEvent(id));
  	
		if (!evt.isEntityFound())
		{
			return new ResponseEntity<VoteReminder>(HttpStatus.NOT_FOUND);
		}
		VoteReminder restVoteReminder=new VoteReminder();
		restVoteReminder.setNodeId(evt.getNodeId());
		return new ResponseEntity<VoteReminder>(restVoteReminder,HttpStatus.OK);
	}
    
    /**
     * Is passed all the necessary data to read a vote record from the database.
     * The request must be a GET with the vote record id presented
     * as the final portion of the URL.
     * <p/>
     * This method will return the voteRecord object read from the database.
     * 
     * @param email the vote record id of the vote record object to be read.
     * @return the vote record object.
     * 

	*/
	@RequestMapping(method=RequestMethod.DELETE,value=ControllerConstants.USER_LABEL+ControllerConstants.VOTE_RECORD_LABEL+"/{id}")
	public @ResponseBody ResponseEntity<VoteRecord> deleteVoteRecord(@PathVariable Long id) 
	{
		if (LOG.isInfoEnabled()) LOG.info("Attempting to delete voteRecord. "+id);
		DeletedEvent evt=userService.deleteVoteRecord(new DeleteVoteRecordEvent(id));
  	
		if (!evt.isEntityFound())
		{
			return new ResponseEntity<VoteRecord>(HttpStatus.NOT_FOUND);
		}
		VoteRecord restVoteRecord=new VoteRecord();
		restVoteRecord.setNodeId(evt.getNodeId());
		return new ResponseEntity<VoteRecord>(restVoteRecord,HttpStatus.OK);
	}
    
    /**
     * Is passed all the necessary data to read a user from the database.
     * The request must be a GET with the user email presented
     * as the final portion of the URL.
     * <p/>
     * This method will return the user object read from the database.
     * 
     * @param email the email address of the user object to be read.
     * @return the user object.
     * 

	*/
	@RequestMapping(method=RequestMethod.GET,value=ControllerConstants.USER_LABEL+"/{email}")
	public @ResponseBody ResponseEntity<User> findUser(@PathVariable String email) 
	{
		if (LOG.isInfoEnabled()) LOG.info("Attempting to retrieve user. "+email);
		ReadUserEvent userEvent;
		ResponseEntity<User> response;
		
		if (longValidator.isValid(email))
		{
			Long id=longValidator.validate(email);
			if (LOG.isDebugEnabled()) LOG.debug("UserId supplied. - "+id);
			RequestReadUserEvent evt=new RequestReadUserEvent(id);
			userEvent=userService.readUserById(evt);
		}
		else if (emailValidator.isValid(email))
		{
			if (LOG.isDebugEnabled()) LOG.debug("Email supplied.");
			 userEvent=userService.readUser(new RequestReadUserEvent(email));
		}
		else
			return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
			
  	
		if (!userEvent.isEntityFound())
		{
			response = new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		else
		{
			User restUser=User.fromUserDetails((UserDetails) userEvent.getDetails());
			response = new ResponseEntity<User>(restUser,HttpStatus.OK);
		}
		return response;
	}
    
    /**
     * Is passed all the necessary data to read a user from the database.
     * The request must be a GET with the user email presented
     * as the final portion of the URL.
     * <p/>
     * This method will return the user object read from the database.
     * 
     * @param email the email address of the user object to be read.
     * @return the user object.
     * 

	*/
	@RequestMapping(method=RequestMethod.GET,value=ControllerConstants.CONTACT_LABEL+"/{contactInfo}")
	public @ResponseBody ResponseEntity<UserProfile> findContact(@PathVariable String contactInfo) 
	{
		if (LOG.isInfoEnabled()) LOG.info("Attempting to find contact. "+contactInfo);

		ReadUserEvent userEvent;
		ResponseEntity<UserProfile> result;
		boolean isEmail=emailValidator.isValid(contactInfo);
		String email=null;
		
		if (isEmail)
		{
			email=contactInfo;
			userEvent=userService.readUserByContactEmail(new RequestReadUserEvent(email));
		}
		else
		{
			userEvent=userService.readUserByContactNumber(new RequestReadUserEvent(contactInfo));
		}
			 	
		if (!userEvent.isEntityFound())
		{
			result = new ResponseEntity<UserProfile>(HttpStatus.NOT_FOUND);
		}
		else
		{
			UserDetails dets=(UserDetails) userEvent.getDetails();
			if (!isEmail)
				dets.setEmail(null);
			if (LOG.isDebugEnabled()) LOG.debug("dets - "+dets);
			UserProfile restUser=UserProfile.fromUserDetails(dets);
			result = new ResponseEntity<UserProfile>(restUser,HttpStatus.OK);
			if (LOG.isDebugEnabled()) LOG.debug("result - "+result);
		}
		return result;
	}
    	
	/**
	 * Is passed all the necessary data to read contacts from the database. The
	 * request must be a GET with the userId presented as the final
	 * portion of the URL.
	 * <p/>
	 * This method will return the contacts read from the database.
	 * 
	 * @param userId
	 *            the userId who has the contact objects to be read.
	 * @return the contacts.
	 * 
	 */
	@RequestMapping(method=RequestMethod.GET,value=ControllerConstants.CONTACTS_LABEL+"/{contactInfo}")
	public @ResponseBody ResponseEntity<FindsParent> findExistingContacts(@PathVariable String contactInfo,
			@RequestParam(value = "direction", required = false, defaultValue = ControllerConstants.DIRECTION) String direction,
			@RequestParam(value = "page", required = false, defaultValue = ControllerConstants.PAGE_NUMBER) String page,
			@RequestParam(value = "pageSize", required = false, defaultValue = ControllerConstants.PAGE_LENGTH) String pageSize
			) 
	{
		int pageNumber = 0;
		int pageLength = 10;
		pageNumber = Integer.parseInt(page);
		pageLength = Integer.parseInt(pageSize);
		Direction sortDirection = Direction.DESC;
		if (direction.equalsIgnoreCase("asc")) sortDirection = Direction.ASC;
		if (LOG.isInfoEnabled()) LOG.info("Attempting to find existing contacts. "+contactInfo);

		ReadAllEvent userEvent;
		AllReadEvent contactEvent;
		ResponseEntity<FindsParent> result;
		
		if (longValidator.isValid(contactInfo))
		{
			Long id=longValidator.validate(contactInfo);
			if (LOG.isDebugEnabled()) LOG.debug("UserId supplied. - "+id);
			userEvent =new ReadAllEvent(id);
			contactEvent=userService.readExistingContactsById(userEvent, sortDirection, pageNumber, pageLength);
		}
		else if (emailValidator.isValid(contactInfo))
		{
			if (LOG.isDebugEnabled()) LOG.debug("Email supplied.");
			contactEvent=userService.readExistingContactsByEmail(new RequestReadUserEvent(contactInfo), sortDirection, pageNumber, pageLength);
		}
		else
			return new ResponseEntity<FindsParent>(HttpStatus.BAD_REQUEST);
			

		if (!contactEvent.isEntityFound())
		{
			return new ResponseEntity<FindsParent>(HttpStatus.NOT_FOUND);
		}
		Iterator<UserProfile> contactProfiles = UserProfile
				.toUserProfilesIterator(contactEvent.getDetails().iterator());
		FindsParent theUsers = FindsParent.fromArticlesIterator(contactProfiles, contactEvent.getTotalItems(), contactEvent.getTotalPages());
		result = new ResponseEntity<FindsParent>(theUsers, HttpStatus.OK);
		
		return result;
	}
    	
	/**
	 * Is passed all the necessary data to read contacts from the database. The
	 * request must be a GET with the userId presented as the final
	 * portion of the URL.
	 * <p/>
	 * This method will return the contacts read from the database.
	 * 
	 * @param userId
	 *            the userId who has the contact objects to be read.
	 * @return the contacts.
	 * 
	 */
	@RequestMapping(method=RequestMethod.GET,value=ControllerConstants.USER_LABEL+"/{email}"+ControllerConstants.SUPPORT)
	public @ResponseBody ResponseEntity<Iterator<Ticket>> findSupports(@PathVariable String email,
			@RequestParam(value = "direction", required = false, defaultValue = ControllerConstants.DIRECTION) String direction,
			@RequestParam(value = "page", required = false, defaultValue = ControllerConstants.PAGE_NUMBER) String page,
			@RequestParam(value = "pageSize", required = false, defaultValue = ControllerConstants.PAGE_LENGTH) String pageSize
			) 
	{
		int pageNumber = 0;
		int pageLength = 10;
		pageNumber = Integer.parseInt(page);
		pageLength = Integer.parseInt(pageSize);
		Direction sortDirection = Direction.DESC;
		if (direction.equalsIgnoreCase("asc")) sortDirection = Direction.ASC;
		if (LOG.isInfoEnabled()) LOG.info("Attempting to find existing contacts. "+email);

		ReadAllEvent userEvent;
		AllReadEvent supportsEvent;
		ResponseEntity<Iterator<Ticket>> result;
		
		if (longValidator.isValid(email))
		{
			Long id=longValidator.validate(email);
			if (LOG.isDebugEnabled()) LOG.debug("UserId supplied. - "+id);
			userEvent =new ReadAllEvent(id);
			supportsEvent=userService.readSupportsById(userEvent, sortDirection, pageNumber, pageLength);
		}
		else if (emailValidator.isValid(email))
		{
			if (LOG.isDebugEnabled()) LOG.debug("Email supplied.");
			supportsEvent=userService.readSupportsByEmail(new RequestReadUserEvent(email), sortDirection, pageNumber, pageLength);
		}
		else
			return new ResponseEntity<Iterator<Ticket>>(HttpStatus.BAD_REQUEST);
			

		if (!supportsEvent.isEntityFound())
		{
			return new ResponseEntity<Iterator<Ticket>>(HttpStatus.NOT_FOUND);
		}
		Iterator<Ticket> contactProfiles = Ticket.toTicketsIterator(supportsEvent.getDetails().iterator());

		result = new ResponseEntity<Iterator<Ticket>>(contactProfiles, HttpStatus.OK);
		
		return result;
	}
    	
    /**
     * Is passed all the necessary data to delete a user.
     * The request must be a DELETE with the user email presented
     * as the final portion of the URL.
     * <p/>
     * This method will return the deleted user object.
     * 
     * @param email the email address of the user object to be deleted.
     * @return the user object deleted.
     * 

	*/
	@RequestMapping(method=RequestMethod.DELETE,value=ControllerConstants.USER_LABEL+"/{email}")
	public @ResponseBody ResponseEntity<User> deleteUser(@PathVariable String email) 
	{
		if (LOG.isInfoEnabled()) LOG.info("Attempting to delete user. "+email);
		UserDeletedEvent userEvent=userService.deleteUser(new DeleteUserEvent(email));
  	
		if (!userEvent.isEntityFound())
		{
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		User restUser=User.fromUserDetails((UserDetails) userEvent.getDetails());
		return new ResponseEntity<User>(restUser,HttpStatus.OK);
	}
    
    
    /**
     * Is passed all the necessary data to create a new user.
     * The request must be a POST with the necessary parameters in the
     * attached data.
     * <p/>
     * This method will return the resulting user object.
     * There will also be a relationship set up with the 
     * institution the user belongs to.
     * 
     * @param user the user object passed across as JSON.
     * @return the user object returned by the Graph Database.
     * 

	*/
    
    @RequestMapping(method=RequestMethod.POST,value=ControllerConstants.SIGNUP_LABEL)
    public @ResponseBody ResponseEntity<User> saveNewUser(@RequestBody User user) 
    {
    	if (LOG.isInfoEnabled()) LOG.info("attempting to save user "+user);
    	UserCreatedEvent userEvent=userService.signUpNewUser(new CreateUserEvent(user.toUserDetails()));

    	if ((null==userEvent)||(null==userEvent.getEmail()))
    	{
    		return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
    	}
    	else if (!userEvent.isInstituteFound())
    	{
    		return new ResponseEntity<User>(HttpStatus.FAILED_DEPENDENCY);
    	}
    	else if (!userEvent.isUserUnique())
    	{
    		return new ResponseEntity<User>(HttpStatus.CONFLICT);
    	}
    	else
    	{
	    	User restUser=User.fromUserDetails((UserDetails) userEvent.getDetails());
	    	if (LOG.isDebugEnabled()) LOG.debug(userEvent.getVerificationEmail().toString());
	    	emailService.sendEmail(userEvent.getVerificationEmail());
	    	return new ResponseEntity<User>(restUser,HttpStatus.CREATED);
    	}
    }
    
    /**
     * Is passed all the necessary data to resend the verification email.
     * The request must be a GET with the email addressed in the
     * url.
     * <p/>
     * This method will return the user object.
     * The verification email will also be resent. 
     * 
     * @param email the user email address passed across in the URL.
     * @return the user object returned by the Graph Database.
     * 

	*/
    
    @RequestMapping(method=RequestMethod.GET,value=ControllerConstants.EMAIL_VERIFICATION_LABEL+"/{email}/resendEmail")
    public @ResponseBody ResponseEntity<User> resendVerificationEmail(@PathVariable String email) 
    {
    	if (LOG.isInfoEnabled()) LOG.info("attempting to resend verification email to user "+email);
    	
    	UserCreatedEvent userEvent=userService.resendVerificationEmail(new RequestReadUserEvent(email));

    	ResponseEntity<User> response;
    	
    	if (null==userEvent)
    	{
    		response = new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
    	}
    	else if (!userEvent.isInstituteFound())
    	{
    		response = new ResponseEntity<User>(HttpStatus.NOT_FOUND);
    	}
    	else if (!userEvent.isUserUnique())
    	{
    		response = new ResponseEntity<User>(HttpStatus.CONFLICT);
    	}
    	else if (null==userEvent.getEmail())
    	{
    		response = new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
    	}
    	else
    	{
	    	User restUser=User.fromUserDetails((UserDetails) userEvent.getDetails());
	    	if (LOG.isDebugEnabled()) LOG.debug(userEvent.getVerificationEmail().toString());
	    	emailService.sendEmail(userEvent.getVerificationEmail());
	    	response = new ResponseEntity<User>(restUser,HttpStatus.OK);
    	}
    	return response;
    }
    
    /**
     * Is passed all the necessary data to verify a user account.
     * The request must be a GET with the necessary parameters in the
     * URL.
     * <p/>
     * This method will return the user object owner of the verified account.
     * 
     * @param email the email address of the user account to be verified.
     * @param token the token string to be used to verify the user account.
     * @return the user object returned by the Graph Database.
     * 
	*/
    @RequestMapping(method=RequestMethod.GET,value=ControllerConstants.EMAIL_VERIFICATION_LABEL+"/{email}/{token}")
    public @ResponseBody ResponseEntity<User> verifyUserAccount1(@PathVariable String email, @PathVariable String token) 
    {
    	return verifyUserAccount(email,token);
    }
    
    /**
     * Is passed all the necessary data to verify a user account.
     * The request must be a POST with the necessary parameters in the
     * URL.
     * <p/>
     * This method will return the user object owner of the verified account.
     * 
     * @param email the email address of the user account to be verified.
     * @param token the token string to be used to verify the user account.
     * @return the user object returned by the Graph Database.
     * 
	*/
    @RequestMapping(method=RequestMethod.POST,value=ControllerConstants.EMAIL_VERIFICATION_LABEL+"/{email}/{token}")
    public @ResponseBody ResponseEntity<User> verifyUserAccount2(@PathVariable String email, @PathVariable String token) 
    {
	    return verifyUserAccount(email, token);
    }
    
    public @ResponseBody ResponseEntity<User> verifyUserAccount(String email, String token) 
    {
    	if (LOG.isInfoEnabled()) LOG.info("attempting to verify email by token "+email+" " +token);
    	
    	UUID decodedToken=com.eulersbridge.iEngage.database.domain.VerificationToken.convertEncoded64URLStringtoUUID(token);
    	if (LOG.isDebugEnabled()) LOG.debug("Decoded token - "+decodedToken);
    	UserAccountVerifiedEvent userAccountVerifiedEvent=userService.validateUserAccount(new VerifyUserAccountEvent(email,decodedToken.toString()));

    	if (!userAccountVerifiedEvent.isAccountVerified())
    	{
    		VerificationErrorType verfError = userAccountVerifiedEvent.getVerificationError();
    		
    		if(verfError.equals(UserAccountVerifiedEvent.VerificationErrorType.userNotFound))
    			return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
    		else
    		{ 
    			User resultUser = User.fromUserDetails(userAccountVerifiedEvent.getUserDetails());
    			if(verfError.equals(UserAccountVerifiedEvent.VerificationErrorType.tokenDoesntExists.toString()))
    				return new ResponseEntity<User>(resultUser,HttpStatus.BAD_REQUEST);
    		else if(verfError.equals(UserAccountVerifiedEvent.VerificationErrorType.tokenAlreadyUsed.toString())
    				|| verfError.equals(UserAccountVerifiedEvent.VerificationErrorType.tokenExpired.toString())
    				|| verfError.equals(UserAccountVerifiedEvent.VerificationErrorType.tokenTypeMismatch.toString()))
    			return new ResponseEntity<User>(resultUser,HttpStatus.FAILED_DEPENDENCY);
    		else
    			return new ResponseEntity<User>(resultUser,HttpStatus.BAD_REQUEST);
    		}
    	}
    	else
    	{
	    	User restUser=User.fromUserDetails(userAccountVerifiedEvent.getUserDetails());
	    	return new ResponseEntity<User>(restUser,HttpStatus.OK);
    	}
    }
    
    
    
    @RequestMapping(value="/displayParams")
    public @ResponseBody ResponseEntity<Boolean> displayDetails(@RequestBody User user) 
    {
    	if (LOG.isInfoEnabled()) 
    		LOG.info("user = "+user);
    	return new ResponseEntity<Boolean>(true,HttpStatus.OK);
    }
    
    @RequestMapping(value="/testResources")
    public @ResponseBody ResponseEntity<StringWriter> testResources() 
    {
    	if (LOG.isInfoEnabled()) 
    		LOG.info("testResources()");
        if (LOG.isDebugEnabled()) LOG.debug("this.velocityEngine = "+this.velocityEngine);
        LOG.debug("Real path = "+servletContext.getRealPath("/"));
		LOG.debug("Real path = "+servletContext.getRealPath("/WEB-INF/"));
		String resourceName=EmailConstants.EmailVerificationTemplate;
		Template t;
	    StringWriter sw = new StringWriter();
		try {
			String info=servletContext.getServerInfo();
			if (LOG.isDebugEnabled())
			{
				LOG.debug("info - "+info);
			}
			velocityEngine.setApplicationAttribute("javax.servlet.ServletContext",servletContext );
			velocityEngine.init();

			VelocityContext context=new VelocityContext();
			context.put("recipientName", "Greg Newitt");
			context.put("emailAddress","gnewitt@hotmail.com");
			context.put("verificationToken","blahblahblah");
			
			final Map<String, Object> hTemplateVariables = new HashMap<String,Object>();

			hTemplateVariables.put("recipientName", "Greg Newitt");
			hTemplateVariables.put("emailAddress","gnewitt@hotmail.com");
			hTemplateVariables.put("verificationToken","blahblahblah");
		
			
			boolean exists=velocityEngine.resourceExists(resourceName);
	
			if (LOG.isDebugEnabled())
			{
				if (exists)
					LOG.debug("Resource name "+resourceName+" exists.");
				else
					LOG.warn("validate template2 does not exist. ");
			}
			t=velocityEngine.getTemplate(resourceName);
    	    t.merge(context, sw);
    	    
			String velocityModel=resourceName;
			String body = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, velocityModel,"UTF-8", hTemplateVariables);

    	    LOG.debug(sw.toString());
    	    LOG.debug(body);
		}
        catch (ResourceNotFoundException re)
        {
        	LOG.warn("Resource not found");
        }
        
        catch (VelocityException e) 
        {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return new ResponseEntity<StringWriter>(sw,HttpStatus.OK);
    }

//    @RequestMapping(method=RequestMethod.GET,value=ControllerConstants.USER_LABEL+"/{email}" + ControllerConstants.LIKES_LABEL)
//    public @ResponseBody ResponseEntity<Iterator<LikeInfo>> findLikes(
//            @PathVariable String email,
//            @RequestParam(value="direction",required=false,defaultValue=ControllerConstants.DIRECTION) String direction,
//            @RequestParam(value="page",required=false,defaultValue=ControllerConstants.PAGE_NUMBER) String page,
//            @RequestParam(value="pageSize",required=false,defaultValue=ControllerConstants.PAGE_LENGTH) String pageSize)
//    {
//        int pageNumber = 0;
//        int pageLength = 10;
//        pageNumber = Integer.parseInt(page);
//        pageLength = Integer.parseInt(pageSize);
//        if (LOG.isInfoEnabled()) LOG.info("Attempting to retrieve liked users via user "+email+'.');
//        Direction sortDirection = Direction.DESC;
//        if (direction.equalsIgnoreCase("asc")) sortDirection = Direction.ASC;
//
//        LikeableObjectLikesEvent likeableObjectLikesEvent = likesService.likes(new LikesLikeableObjectEvent(email), sortDirection, pageNumber, pageLength);
//        Iterator<LikeInfo> likes = User.toLikesIterator(likeableObjectLikesEvent.getUserDetails().iterator());
//        if (likes.hasNext() == false){
//            ReadUserEvent readUserEvent=userService.requestReadUser(new RequestReadUserEvent(email));
//            if (!readUserEvent.isEntityFound())
//                return new ResponseEntity<Iterator<LikeInfo>>(HttpStatus.NOT_FOUND);
//            else
//                return new ResponseEntity<Iterator<LikeInfo>>(likes, HttpStatus.OK);
//        }
//        else
//            return new ResponseEntity<Iterator<LikeInfo>>(likes, HttpStatus.OK);
//    }
}

