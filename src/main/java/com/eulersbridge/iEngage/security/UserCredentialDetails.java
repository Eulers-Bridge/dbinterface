package com.eulersbridge.iEngage.security;

import com.eulersbridge.iEngage.database.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Yikai Gong
 */

public class UserCredentialDetails implements UserDetails{
  private String username;
  private String password;
  private List<GrantedAuthority> authorities;
  private boolean accountExpired = false;
  private boolean accountLocked = false;
  private boolean credentialsExpired = false;
  private boolean enabled = true;

  public UserCredentialDetails() {
  }

  public UserCredentialDetails(User userEntity) {
    this.username = userEntity.getEmail();
    this.password = userEntity.getPassword();
    this.authorities = authsFromString(userEntity.getRoles());
    this.enabled = userEntity.getAccountVerified();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return !accountExpired;
  }

  @Override
  public boolean isAccountNonLocked() {
    return !accountLocked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return !credentialsExpired;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }

  private List<GrantedAuthority> authsFromString(String authString) {
    List<GrantedAuthority> grantedAuths = new ArrayList<>();
    if (authString != null) {
      for (String auth1 : authString.split(","))
        grantedAuths.add(new SimpleGrantedAuthority(auth1));
    }
    return grantedAuths;
  }
}
