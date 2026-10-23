package com.github.mizo432.xplanner.buisiness.model.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.github.mizo432.xplanner.buisiness.model.Iteration;

@Repository
public interface IterationRepositoryJpa extends JpaRepository<Iteration, Long> {

	@Query("SELECT i FROM Iteration i JOIN i.project p WHERE p.hidden = false ORDER BY p.name, i.startDate")
	List<Iteration> findAllEditable();

	@Query("SELECT i FROM Iteration i WHERE i.project.id = :projectId AND i.startDate <= :now AND i.endDate >= :now")
	List<Iteration> findCurrentIterations(Long projectId, Date now);

	List<Iteration> findByNameContaining(String name);

}
