/**
 * 
 */
package com.eulersbridge.iEngage.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

/**
 * @author Greg Newitt
 *
 */
public class AppBasicAuthenticationSuccessHandler extends
		SimpleUrlAuthenticationSuccessHandler 
{
	private RequestCache requestCache = new HttpSessionRequestCache();
	
    private static Logger LOG = LoggerFactory.getLogger(AppBasicAuthenticationSuccessHandler.class);
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) 
			throws ServletException, IOException 
	{
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		if (savedRequest == null) 
		{
			if (LOG.isDebugEnabled()) LOG.debug("No saved request.");
			clearAuthenticationAttributes(request);
			return;
		}
		String targetUrlParam = getTargetUrlParameter();
		if (isAlwaysUseDefaultTargetUrl() ||
		(targetUrlParam != null && StringUtils.hasText(request.getParameter(targetUrlParam)))) 
		{
			if (LOG.isDebugEnabled()) LOG.debug("Target URL param exists.");
			requestCache.removeRequest(request, response);
			clearAuthenticationAttributes(request);
			return;
		}
		clearAuthenticationAttributes(request);
	}
	
	public void setRequestCache(RequestCache requestCache) 
	{
		this.requestCache = requestCache;
	}
}
