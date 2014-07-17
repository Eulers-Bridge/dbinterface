package com.eulersbridge.iEngage.core.services;

import com.eulersbridge.iEngage.email.Email;
import com.eulersbridge.iEngage.email.EmailVerification;

public interface EmailService 
{
    public void sendEmail(Email email);
}
