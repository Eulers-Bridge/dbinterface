package com.eulersbridge.iEngage.security;

public class SecurityConstants 
{
	public static final String USER_ROLE = "ROLE_USER";
	public static final String ADMIN_ROLE = "ROLE_ADMIN";
	public static final String CONTENT_MANAGER_ROLE = "ROLE_CONTENT_MANAGER";
	public static final String RETURNING_OFFICER_ROLE = "ROLE_RETURNING_OFFICER";
	
	public static final String REALM_NAME = "isegoria";
	public static final String DIGEST_KEY = "eulersBridge";
	public static final int NonceValiditySeconds = 60;
	public static final String HEADER_SECURITY_TOKEN = "X-Security-Token";
	public static final String TOKEN_KEY = "some token goes here";
	
	public static final String BadPassword = "Password does not match.";
	public static final String NotVerified = "Account not verified.";
	public static final String UserNotFound = "User not found.";
	
	
}
