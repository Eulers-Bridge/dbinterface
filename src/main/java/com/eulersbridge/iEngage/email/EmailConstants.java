package com.eulersbridge.iEngage.email;

import org.apache.velocity.runtime.RuntimeConstants;

import java.util.Properties;

public class EmailConstants
{
	public static final int DEFAULT_EXPIRY_TIME_IN_MINS = 60 * 24 * 7; //seven days
	public static final String EmailVerificationTemplate = "templates/sign_up_in_progress.vm";
	public static final String EmailResetPWDTemplate = "templates/reset_password.vm";
}
