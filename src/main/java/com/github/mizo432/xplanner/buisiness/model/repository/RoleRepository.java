package com.github.mizo432.xplanner.buisiness.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.github.mizo432.xplanner.buisiness.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	List<Role> findByName(String roleId);

	@Query("SELECT DISTINCT r FROM Role r, PersonRole pr WHERE pr.id.roleId = r.id "
			+ "AND pr.id.personId = :personId "
			+ "AND (pr.id.projectId = :projectId OR (:includeWildcard = true AND pr.id.projectId = -1))")
	List<Role> findRoles(Long personId, Long projectId, boolean includeWildcard);

}
