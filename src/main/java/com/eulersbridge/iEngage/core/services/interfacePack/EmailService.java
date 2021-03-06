package com.eulersbridge.iEngage.core.services.interfacePack;

import com.eulersbridge.iEngage.email.Email;
import com.eulersbridge.iEngage.email.EmailVerification;

public interface EmailService {
  public void sendEmail(EmailVerification email);

  public void sendEmail(Email email);

  public void sendEmail();
}
