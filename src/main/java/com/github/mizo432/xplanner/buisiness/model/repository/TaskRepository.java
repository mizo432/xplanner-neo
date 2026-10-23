package com.github.mizo432.xplanner.buisiness.model.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.github.mizo432.xplanner.buisiness.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

	@Query("SELECT t FROM Task t JOIN t.userStory us JOIN us.iteration i "
			+ "WHERE i.startDate <= :now AND i.endDate >= :now AND t.acceptorId = :personId")
	List<Task> findCurrentTasksForPerson(Date now, Long personId);

	List<Task> findByNameContaining(String name);

}
