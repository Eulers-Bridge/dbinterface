package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.core.events.DeletedEvent;
import com.eulersbridge.iEngage.core.events.ReadEvent;
import com.eulersbridge.iEngage.core.events.UpdatedEvent;
import com.eulersbridge.iEngage.core.events.configuration.*;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * @author Yikai Gong
 */
public interface ConfigurationService {

  @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
  public ConfigurationCreatedEvent createConfiguration(CreateConfigurationEvent createConfigurationEvent);

  @PreAuthorize("hasRole('ROLE_USER')")
  public ReadEvent requestReadConfiguration(RequestReadConfigurationEvent requestReadConfigurationEvent);

  @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
  public UpdatedEvent updateConfiguration(UpdateConfigurationEvent updateConfigurationEvent);

  @PreAuthorize("hasAnyRole('ROLE_CONTENT_MANAGER','ROLE_ADMIN')")
  public DeletedEvent deleteConfiguration(DeleteConfigurationEvent deleteConfigurationEvent);

}
