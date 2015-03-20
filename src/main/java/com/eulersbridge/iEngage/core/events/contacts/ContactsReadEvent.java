/**
 * 
 */
package com.eulersbridge.iEngage.core.events.contacts;

import com.eulersbridge.iEngage.core.events.AllReadEvent;
import com.eulersbridge.iEngage.core.events.users.UserDetails;

/**
 * @author Greg Newitt
 *
 */
public class ContactsReadEvent extends AllReadEvent
{
	Iterable<UserDetails> contacts;
	private boolean userFound=true;
	
	public ContactsReadEvent(Long userId, Iterable<UserDetails> tickets, Long totalItems, Integer totalPages)
	{
		super(userId,totalItems,totalPages);
		this.contacts=tickets;
	}

	public ContactsReadEvent(Long electionId, Iterable<UserDetails> contacts)
	{
		super(electionId);
		this.contacts=contacts;
	}

	public ContactsReadEvent(Iterable<UserDetails> contacts)
	{
		super(null);
		this.contacts=contacts;
	}

	public ContactsReadEvent()
	{
		super(null);
	}
	
	/**
	 * @return the contacts
	 */
	public Iterable<UserDetails> getContacts() 
	{
		return contacts;
	}

	/**
	 * @param contacts the contacts to set
	 */
	public void setContacts(Iterable<UserDetails> contacts) 
	{
		this.contacts = contacts;
	}

	/**
	 * @return the userFound
	 */
	public boolean isUserFound() {
		return userFound;
	}

	public static ContactsReadEvent userNotFound() 
	{
		ContactsReadEvent nare=new ContactsReadEvent();
		nare.userFound=false;
		nare.entityFound=false;
		return nare;
	}

}
