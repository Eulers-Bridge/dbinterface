package com.eulersbridge.iEngage.core.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eulersbridge.iEngage.rest.domain.Response;

public class Login 
{
    private final long id;
    private final String username;
    private final String content;

    private static Logger LOG = LoggerFactory.getLogger(Login.class);

    public Login(long id, String username, String content) 
    {
    	if (LOG.isTraceEnabled()) LOG.trace("constructor("+id+','+username+','+content+')');
        this.id = id;
        this.content = content;
        this.username = username;
    }

	public long getId() 
	{
    	if (LOG.isDebugEnabled()) LOG.debug("getId() = "+id);
        return id;
    }

    public String getUsername() {
    	if (LOG.isDebugEnabled()) LOG.debug("getUsername() = "+username);
        return username;
    }
    public String getContent() {
    	if (LOG.isDebugEnabled()) LOG.debug("getContent() = "+content);
        return content;
    }

	public Response process() 
	{
    	if (LOG.isDebugEnabled()) LOG.debug("process() = ");
		// TODO Auto-generated method stub
		return new Response(true,username+" logged in");
	}


}
