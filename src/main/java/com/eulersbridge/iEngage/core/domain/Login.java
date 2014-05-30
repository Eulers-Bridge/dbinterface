package com.eulersbridge.iEngage.core.domain;

import com.eulersbridge.iEngage.rest.domain.Response;

public class Login {
    private final long id;
    private final String username;
    private final String content;

    public Login(long id, String username, String content) {
        this.id = id;
        this.content = content;
        this.username = username;
    }

	public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
    public String getContent() {
        return content;
    }

	public Response process() {
		// TODO Auto-generated method stub
		return new Response(true,username+" logged in");
	}


}
