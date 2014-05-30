package com.eulersbridge.iEngage.core.domain;

import com.eulersbridge.iEngage.rest.domain.Response;

public class Logout {
	
    private final long id;
    private final String username;
    private static final String logoutTemplate = " logged out.";

    public Logout(long id, String content) {
        this.id = id;
        this.username = content;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

	public Response process() 
	{
		// TODO Auto-generated method stub
		return new Response(true,username+logoutTemplate);
	}

}
