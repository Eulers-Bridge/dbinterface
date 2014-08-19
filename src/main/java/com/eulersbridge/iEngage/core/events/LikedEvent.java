/**
 * 
 */
package com.eulersbridge.iEngage.core.events;

/**
 * @author Greg Newitt
 *
 */
public class LikedEvent 
{
	  protected boolean entityFound = true;
	  protected boolean userFound = true;
	  protected boolean result=true;

	  public boolean isEntityFound() 
	  {
	    return entityFound;
	  }

	  public boolean isUserFound() 
	  {
	    return userFound;
	  }

	  public boolean isResultSuccess() 
	  {
	    return result;
	  }

}
