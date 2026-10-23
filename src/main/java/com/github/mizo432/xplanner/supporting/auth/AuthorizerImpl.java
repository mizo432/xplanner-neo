package com.github.mizo432.xplanner.supporting.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.github.mizo432.xplanner.buisiness.model.Iteration;
import com.github.mizo432.xplanner.buisiness.model.Note;
import com.github.mizo432.xplanner.buisiness.model.Permission;
import com.github.mizo432.xplanner.buisiness.model.Person;
import com.github.mizo432.xplanner.buisiness.model.Project;
import com.github.mizo432.xplanner.buisiness.model.Setting;
import com.github.mizo432.xplanner.buisiness.model.Task;
import com.github.mizo432.xplanner.buisiness.model.TimeEntry;
import com.github.mizo432.xplanner.buisiness.model.UserStory;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.github.mizo432.xplanner.buisiness.model.Feature;
import com.github.mizo432.xplanner.buisiness.model.Integration;
import com.github.mizo432.xplanner.supporting.AuthenticationException;


@Component
public class AuthorizerImpl implements Authorizer {
   private final Map<Class<?>, String> resourceTypes = new HashMap<>();
   public static final Long ANY_PROJECT = 0L;

   @Autowired
   private AuthorizerQueryHelper authorizerQueryHelper;

   @Autowired
   @Lazy
   private PrincipalSpecificPermissionHelper principalSpecificPermissionHelper;

   public AuthorizerImpl() {
      //TODO: Extract these constants
      //DEBT(METADATA) Move these to the DomainMetaDataRepository
      resourceTypes.put(Project.class, "system.project");
      resourceTypes.put(Iteration.class, "system.project.iteration");
      resourceTypes.put(UserStory.class, "system.project.iteration.story");
      resourceTypes.put(Task.class, "system.project.iteration.story.task");
      resourceTypes.put(Feature.class, "system.project.iteration.story.feature");
      resourceTypes.put(TimeEntry.class, "system.project.iteration.story.task.time_entry");
      resourceTypes.put(Integration.class, "system.project.integration");
      resourceTypes.put(Person.class, "system.person");
      resourceTypes.put(Note.class, "system.note");
      resourceTypes.put(Setting.class, "system.setting");
   }

   //TODO resource should be a DomainObject
   public boolean hasPermission(Long projectId, Long personId, Object resource, String permission)
         throws AuthenticationException {
      Long id;
      try {
         id = ((Number) PropertyUtils.getProperty(resource, "id")).longValue();
      } catch (Exception e) {
         throw new AuthenticationException(e);
      }
      return hasPermission(projectId, personId, getTypeOfResource(resource), id, permission);
   }


   public boolean hasPermission(Long projectId,
                                Long personId,
                                String resourceType,
                                Long resourceId,
                                String permissionName) throws AuthenticationException {
      try {
         Map<Long, List<Permission>> permissionsByProjectMap = principalSpecificPermissionHelper.getPermissionsForPrincipal(personId);
         return permissionMatches(permissionName,
                                  resourceType,
                                  resourceId,
                                  permissionsByProjectMap.get(projectId))
                ||
                // For 0.7 only sysadmins have any project permissions
                permissionMatches(permissionName,
                                  resourceType,
                                  resourceId,
                                  permissionsByProjectMap.get(ANY_PROJECT));
      } catch (Exception e) {
         throw new AuthenticationException(e);
      }
   }


   public Collection<Person> getPeopleWithPermissionOnProject(List<Person> allPeople, Long projectId) throws AuthenticationException {
      Collection<Person> people = new ArrayList<>();
      for (Person person : allPeople) {
         if (hasPermission(projectId, person.getId(), "system.project", projectId, "edit")) {
            people.add(person);
         }
      }
      return people;
   }

   public Collection getRolesForPrincipalOnProject(Long principalId, Long projectId, boolean includeWildcardProject)
         throws AuthenticationException {
      try {
         return authorizerQueryHelper.getRolesForPrincipalOnProject(principalId, projectId, includeWildcardProject);
      } catch (DataAccessException e) {
         throw new AuthenticationException(e);
      }
   }

   public boolean hasPermissionForSomeProject(Long personId,
                                              String resourceType, Long resourceId, String permission)
         throws AuthenticationException {
      try {
         List<Project> projects =
               authorizerQueryHelper.getAllUnhidenProjects();
         return hasPermissionForSomeProject(projects, personId, resourceType, resourceId, permission);
      } catch (AuthenticationException e) {
         throw e;
      } catch (Exception e) {
         throw new AuthenticationException(e);
      }
   }

   public boolean hasPermissionForSomeProject(Collection<Project> projects,
                                              Long personId,
                                              String resourceType,
                                              Long resourceId,
                                              String permission)
         throws AuthenticationException {
      for (Project project : projects) {
         if (hasPermission(project.getId(), personId, resourceType, resourceId, permission)) {
            return true;
         }
      }
      return false;
   }

   public String getTypeOfResource(Object resource) {
	   String keyClass = resource.getClass().getName();
	   for (Class<?> clazz : resourceTypes.keySet()) {
		   	if (keyClass.startsWith(clazz.getName())) {
	    			return resourceTypes.get(clazz);
	    		}
   		}
	   return null;
   }

   @Autowired
   public void setPrincipalSpecificPermissionHelper(PrincipalSpecificPermissionHelper principalSpecificPermissionHelper) {
      this.principalSpecificPermissionHelper = principalSpecificPermissionHelper;
   }

   @Autowired
   public void setAuthorizerQueryHelper(AuthorizerQueryHelper authorizerQueryHelper) {
      this.authorizerQueryHelper = authorizerQueryHelper;
   }

   private boolean isMatching(String pattern, String string) {
      return pattern.endsWith("%")
             ? string.startsWith(pattern.substring(0, pattern.length() - 1))
             : string.equals(pattern);
   }


   private boolean permissionMatches(String permissionName,
                                     String resourceType,
                                     Long resourceId,
                                     List<Permission> permissionsForProject) {
      boolean hasNegativePermission = false;
      boolean hasPositivePermission = false;
      if (permissionsForProject != null) {
         for (Permission permission : permissionsForProject) {
            if (isMatching(permission.getResourceType(), resourceType) &&
                (permission.getResourceId() == 0 || permission.getResourceId() == resourceId)) {
               if (isMatching(permission.getName(), permissionName)) {
                  if (permission.isPositive()) {
                     hasPositivePermission = true;
                  } else {
                     hasNegativePermission = true;
                  }
               }
            }
         }
      }
      return hasPositivePermission && !hasNegativePermission;
   }
}
