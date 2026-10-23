package com.github.mizo432.xplanner.supporting.auth;

import java.util.Collection;
import java.util.Iterator;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.mizo432.xplanner.supporting.AuthenticationException;
import com.github.mizo432.xplanner.supporting.SecurityHelper;

/**
 * User: Mateusz Prokopowicz
 * Date: Feb 15, 2005
 * Time: 11:08:59 AM
 */
@Component
public class AuthorizationHelper
{

    @Autowired
    private SystemAuthorizer systemAuthorizer;

    public boolean hasPermissionToAny(String[] permissionArray,
                                             Collection objectCollection,
                                             ServletRequest request)
        throws AuthenticationException
    {
        return hasPermissionToAny(permissionArray, objectCollection, request, 0L);
    }

    public boolean hasPermissionToAny(String[] permissionArray,
                                             Collection objectCollection,
                                             ServletRequest request,
                                             Long projectId)
        throws AuthenticationException
    {
        boolean isAuthorized = false;
        Long remoteUserId = SecurityHelper.getRemoteUserId((HttpServletRequest) request);
        for (Iterator iterator = objectCollection.iterator(); !isAuthorized && iterator.hasNext();)
        {
            Object resource = iterator.next();
            // PageHelper is no longer available in Spring Boot
            projectId = 0L;
            for (int i = 0; i < permissionArray.length; i++)
            {
                String permission = permissionArray[i];
                if (systemAuthorizer.get().hasPermission(projectId,
                                                         remoteUserId,
                                                         resource, permission))
                {
                    isAuthorized = true;
                    break;
                }
            }
        }
        return isAuthorized;
    }

    public boolean hasPermission(Long projectId,
                                        Long principalId,
                                        Long resourceId,
                                        String resourceType,
                                        String permission,
                                        Object resource,
                                        ServletRequest request) throws AuthenticationException
    {
        boolean hasPermission;
        if (principalId == 0)
        {
            principalId = SecurityHelper.getRemoteUserId((HttpServletRequest) request);
        }
        if (resourceType != null)
        {
            hasPermission = !systemAuthorizer.get().
                hasPermission(projectId, principalId, resourceType, resourceId, permission);
        }
        else
        {
            hasPermission = !systemAuthorizer.get().
                hasPermission(projectId, principalId, resource, permission);
        }
        return hasPermission;
    }
}
