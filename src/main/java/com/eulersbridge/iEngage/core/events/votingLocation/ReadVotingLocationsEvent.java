/**
 * 
 */
package com.eulersbridge.iEngage.core.events.votingLocation;

import com.eulersbridge.iEngage.core.events.ReadAllEvent;

/**
 * @author Greg Newitt
 *
 */
public class ReadVotingLocationsEvent extends ReadAllEvent
{
	public ReadVotingLocationsEvent(Long ownerId)
	{
		super(ownerId);
	}
}
