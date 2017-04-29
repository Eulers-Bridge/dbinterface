/**
 * 
 */
package com.eulersbridge.iEngage.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

/**
 * @author Greg Newitt
 *
 */
public class UserPermissionEvaluator implements PermissionEvaluator 
{
    private static Logger LOG = LoggerFactory.getLogger(UserPermissionEvaluator.class);

	/* (non-Javadoc)
	 * @see org.springframework.security.access.PermissionEvaluator#hasPermission(org.springframework.security.core.Authentication, java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean hasPermission(Authentication auth, Object targetDomainObj, Object permission) 
	{
        LOG.info("hasPermission(Authentication, Object, Object) called");
        return true;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.access.PermissionEvaluator#hasPermission(org.springframework.security.core.Authentication, java.io.Serializable, java.lang.String, java.lang.Object)
	 */
	@Override
	public boolean hasPermission(Authentication auth, Serializable targetId,
			String targetType, Object permission) 
	{
        LOG.error("hasPermission(Authentication, Serializable, String, Object) called");
        throw new RuntimeException("ID based permission evaluation currently not supported.");
	}

}
