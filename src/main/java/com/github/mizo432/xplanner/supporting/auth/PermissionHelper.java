/*
 * Copyright (c) 2005 Your Corporation. All Rights Reserved.
 */
package com.github.mizo432.xplanner.supporting.auth;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.mizo432.xplanner.buisiness.model.Person;

import com.github.mizo432.xplanner.supporting.AuthenticationException;

//DEBT duplicate with the Authorizer.getPeopleWithPermissionOnProject
//DEBT Move the query to get all people to be cached in PermissionCache (to be renamed SecurityCache) and replace all hard coded usage of the people query
@Component
public class PermissionHelper {

    @Autowired
    private SystemAuthorizer systemAuthorizer;

   public Collection getPeopleWithProjectRole(String projectId, Collection people)
         throws AuthenticationException {
      Long projectOid = Long.parseLong(projectId);
      Collection peopleToShow = new HashSet();

      if (showFilterOnProject(projectOid)) {
         Iterator i = people.iterator();
         while (i.hasNext()) {
            Person p = (Person) i.next();
            if (isProjectAccessibleByPerson(projectOid, p)) {
               peopleToShow.add(p);
            }
         }
      } else {
         peopleToShow.addAll(people);
      }
      return peopleToShow;
   }

   private boolean isProjectAccessibleByPerson(Long projectOid, Person p) throws AuthenticationException {
      return systemAuthorizer.get().hasPermission(projectOid,
                                                  p.getId(),
                                                  "system.project",
                                                  projectOid,
                                                  "read%");
   }

   private boolean showFilterOnProject(Long projectOid) {return projectOid > 0;}
}
