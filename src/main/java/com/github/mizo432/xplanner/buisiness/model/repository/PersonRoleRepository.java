package com.github.mizo432.xplanner.buisiness.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.mizo432.xplanner.buisiness.model.PersonRole;

@Repository
public interface PersonRoleRepository extends JpaRepository<PersonRole, Long> {

	List<PersonRole> findByPersonId(Long personId);

	List<PersonRole> findByProjectId(Long projectId);

}
