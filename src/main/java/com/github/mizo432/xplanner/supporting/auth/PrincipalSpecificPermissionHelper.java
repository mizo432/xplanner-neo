/*
 * Copyright (c) 2006 Your Corporation. All Rights Reserved.
 */

package com.github.mizo432.xplanner.supporting.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.github.mizo432.xplanner.buisiness.model.Permission;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * User: mprokopowicz
 * Date: Mar 29, 2006
 * Time: 6:47:12 PM
 */
@Component
public class PrincipalSpecificPermissionHelper {
   @Autowired
   private AuthorizerQueryHelper authorizerQueryHelper;

   @Autowired
   public void setAuthorizerQueryHelper(AuthorizerQueryHelper authorizerQueryHelper) {
      this.authorizerQueryHelper = authorizerQueryHelper;
   }

   public Map<Long,List<Permission>> getPermissionsForPrincipal(Long principalId) {
      Map<Long,List<Permission>> map = new HashMap<>();
      addProjectsPermissions(principalId, map);
      addPersonPermissions(principalId, map);
      return map;
   }

   public Collection<Permission> getPermissionsSpecificToPerson(Long principalId) {
      Collection<Permission> personPermissionsCol = authorizerQueryHelper.getAllPermissionsToPerson();
      PersonFilterForPermission filter = new PersonFilterForPermission(principalId) {
         @Override
         protected boolean isEqual(Long actualId) {
            return actualId.equals(0L) || actualId.equals(id);
         }
      };
      return personPermissionsCol.stream()
          .filter(filter::test)
          .collect(Collectors.toList());
   }

   public Collection<Permission> getPermissionsBasedOnPersonRoles(Long principalId) {
      Collection<Permission> allPermissions = authorizerQueryHelper.getAllPermissions();
      PersonFilterForPermission filter = new PersonFilterForPermission(principalId);
      return allPermissions.stream()
          .filter(filter::test)
          .collect(Collectors.toList());
   }

   void addPersonPermissions(
         Long principalId,
         Map<Long,List<Permission>> map) {
      List<List<Permission>> permissionsLists = new ArrayList<>(map.values());
      for (Iterator<List<Permission>> it = permissionsLists.iterator(); it.hasNext();) {
         List<Permission> list = new ArrayList<>(it.next());
         for (Iterator<Permission> itPerm = list.iterator(); itPerm.hasNext();) {
            Permission p = itPerm.next();
            if (p.getResourceType().equals("%") || p.getResourceType().equals("system.person")) {
               Permission personPermission = new Permission("system.person",
                                                            p.getResourceId(),
                                                            p.getPrincipal(),
                                                            p.getName());
               addPermissionForProject(map, 0L, personPermission);
            }
         }
      }
      Collection<Permission> permissions = getPermissionsSpecificToPerson(principalId);
      for (Permission permission : permissions) {
         addPermissionForProject(map, 0L, permission);
      }
   }

   void addPermissionForProject(Map<Long,List<Permission>> permissionMap, Long projectId, Permission permission) {
      Long projectKey = projectId;
      List<Permission> permissions = permissionMap.get(projectKey);
      if (permissions == null) {
         permissions = new ArrayList<>();
         permissionMap.put(projectKey, permissions);
      }
      permissions.add(permission);
   }

   private void addProjectsPermissions(
         Long principalId,
         Map<Long,List<Permission>> map) {

      Collection<Permission> permissions = getPermissionsBasedOnPersonRoles(principalId);
      for (Permission permission : permissions) {
         addPermissionForProject(map, 0L, permission);
      }
   }

   protected static class PersonFilterForPermission implements java.util.function.Predicate<Permission> {
      Long id;

      public PersonFilterForPermission(Long id) {
         this.id = id;
      }

      @Override
      public boolean test(Permission permission) {
         final Long actualId = permission.getPrincipal();
         return isEqual(actualId);
      }

      protected boolean isEqual(Long actualId) {return actualId.equals(id);}
   }
}
