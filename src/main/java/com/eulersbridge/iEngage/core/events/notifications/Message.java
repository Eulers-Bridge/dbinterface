/**
 * 
 */
package com.eulersbridge.iEngage.core.events.notifications;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eulersbridge.iEngage.core.events.Details;

/**
 * @author Greg Newitt
 *
 */
public class Message extends Details
{
	String text;
	
    private static Logger LOG = LoggerFactory.getLogger(Message.class);
	public Message(Long nodeId, String text)
	{
		super(nodeId);
		this.text=text;
		if (LOG.isDebugEnabled()) LOG.debug("Message ()"+this);
	}

	/**
	 * @return the text
	 */
	public String getText()
	{
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text)
	{
		this.text = text;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "Message [text=" + text + "]";
	}

}
