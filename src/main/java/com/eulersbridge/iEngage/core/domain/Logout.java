package com.eulersbridge.iEngage.core.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eulersbridge.iEngage.rest.domain.Response;

public class Logout {
	
    private final long id;
    private final String username;
    private static final String logoutTemplate = " logged out.";

    private static Logger LOG = LoggerFactory.getLogger(Logout.class);

    public Logout(long id, String username) 
    {
    	if (LOG.isTraceEnabled()) LOG.trace("constructor("+id+','+','+username+')');
        this.id = id;
        this.username = username;
    }

    public long getId() 
    {
    	if (LOG.isDebugEnabled()) LOG.debug("getId() = "+id);
        return id;
    }

    public String getUsername() 
    {
    	if (LOG.isDebugEnabled()) LOG.debug("getUsername() = "+username);
        return username;
    }

	public Response process() 
	{
    	if (LOG.isDebugEnabled()) LOG.debug("process() = ");
		// TODO Auto-generated method stub
		return new Response(true,username+logoutTemplate);
	}

}
