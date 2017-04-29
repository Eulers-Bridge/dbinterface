/**
 * 
 */
package com.eulersbridge.iEngage.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Greg Newitt
 *
 */
public class AppBasicAuthenticationEntryPoint extends
		BasicAuthenticationEntryPoint 
{
    private static Logger LOG = LoggerFactory.getLogger(AppBasicAuthenticationEntryPoint.class);
	@Override
	public void commence (HttpServletRequest request,HttpServletResponse response, AuthenticationException authEx) 
			throws IOException, ServletException
	{
		String contentType = request.getContentType();
		if (LOG.isInfoEnabled()) LOG.info(contentType);
		
		response.addHeader("WWW-Authenticate", "Basic realm=\""+getRealmName()+"\"");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		PrintWriter writer= response.getWriter();
		writer.println("HTTP Status 401 - "+authEx.getMessage());
	}
	
	@Override
	public void afterPropertiesSet() throws Exception
	{
		setRealmName(SecurityConstants.REALM_NAME);
		super.afterPropertiesSet();
	}

}

