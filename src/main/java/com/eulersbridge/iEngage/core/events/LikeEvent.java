/**
 * 
 */
package com.eulersbridge.iEngage.core.events;

/**
 * @author Greg Newitt
 *
 */
public class LikeEvent 
{
	protected String emailAddress;

	/**
	 * @return the userId
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
}
