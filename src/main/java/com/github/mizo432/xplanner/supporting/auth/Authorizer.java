package com.github.mizo432.xplanner.supporting.auth;

import java.util.Collection;
import java.util.List;

import com.github.mizo432.xplanner.buisiness.model.Person;
import com.github.mizo432.xplanner.buisiness.model.Project;
import com.github.mizo432.xplanner.buisiness.model.Role;
import com.github.mizo432.xplanner.supporting.AuthenticationException;

//DEBT Should throw AuthorizationException not AuthenticationException

public interface Authorizer {
   boolean hasPermission(Long projectId, Long personId, String resourceType,
                         Long resourceId, String permission) throws AuthenticationException;

   boolean hasPermission(Long projectId, Long personId,
                         Object resource, String permission) throws AuthenticationException;

   boolean hasPermissionForSomeProject(Long personlId,
                                       String resourceType, Long resourceId, String permissions)
         throws AuthenticationException;

   boolean hasPermissionForSomeProject(Collection<Project> projects,
                                       Long personId,
                                       String resourceType,
                                       Long resourceId,
                                       String permission)
         throws AuthenticationException;

   Collection<Person> getPeopleWithPermissionOnProject(List<Person> allPeople, Long projectId) throws AuthenticationException;

   Collection<Role> getRolesForPrincipalOnProject(Long principalId, Long projectId, boolean includeWildcardProject)
         throws AuthenticationException;
}
