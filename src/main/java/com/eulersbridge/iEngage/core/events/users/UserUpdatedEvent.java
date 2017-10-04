package com.eulersbridge.iEngage.core.events.users;

import com.eulersbridge.iEngage.core.events.UpdatedEvent;

public class UserUpdatedEvent extends UpdatedEvent {
  private String email;

  public UserUpdatedEvent(String email, UserDetails userDetails) {
    super(null, userDetails);
    this.email = email;
  }

  public UserUpdatedEvent(String email) {
    super(null);
    this.email = email;
  }

  public String getEmail() {
    return email;
  }

  public static UserUpdatedEvent instituteNotFound(String email) {
    UserUpdatedEvent ev = new UserUpdatedEvent(email);
    ev.entityFound = false;
    return ev;
  }

  public static UserUpdatedEvent userNotFound(String email) {
    UserUpdatedEvent ev = new UserUpdatedEvent(email);
    ev.entityFound = false;
    ev.failed = true;
    return ev;
  }
}
