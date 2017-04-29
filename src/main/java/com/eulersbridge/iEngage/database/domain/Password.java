package com.eulersbridge.iEngage.database.domain;

import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Password extends Node{
  private String password;

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

}
