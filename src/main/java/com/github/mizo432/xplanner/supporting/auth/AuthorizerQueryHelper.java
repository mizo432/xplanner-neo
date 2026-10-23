/*
 * Copyright (c) 2006 Your Corporation. All Rights Reserved.
 */

package com.github.mizo432.xplanner.supporting.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import jakarta.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.mizo432.xplanner.buisiness.model.Permission;
import com.github.mizo432.xplanner.buisiness.model.Project;
import com.github.mizo432.xplanner.buisiness.model.Role;

/**
 * User: mprokopowicz
 * Date: Mar 29, 2006
 * Time: 5:36:07 PM
 */
@Component
public class AuthorizerQueryHelper {

    @Autowired
    private EntityManager entityManager;

public Collection<Permission> getAllPermissions() {
    List<Permission> permissions = entityManager.createNamedQuery("security.personal.permissions", Permission.class).getResultList();
    permissions.addAll(entityManager.createNamedQuery("security.role.permissions", Permission.class).getResultList());
    return permissions;
 }

  public Collection<Permission> getAllPermissionsToPerson() {
     return entityManager.createNamedQuery("security.person.permissions", Permission.class).getResultList();
  }

  public Collection<Role> getRolesForPrincipalOnProject(Long principalId, Long projectId, boolean includeWildcardProject) {
     return entityManager.createNamedQuery("security.roles", Role.class)
         .setParameter("personId", principalId)
         .setParameter("projectId", projectId)
         .setParameter("includeWildcardProject", includeWildcardProject ? 1 : 0)
         .getResultList();
  }

  public List<Project> getAllUnhidenProjects() {
     return entityManager.createQuery("SELECT p FROM Project p WHERE p.hidden = false", Project.class).getResultList();
  }
}
