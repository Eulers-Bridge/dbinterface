package com.eulersbridge.iEngage.database.domain;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class Password 
{
	@GraphId Long nodeId;
	private String password;

	public Long getNodeId()
	{
		return nodeId;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
