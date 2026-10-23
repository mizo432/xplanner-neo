package com.github.mizo432.xplanner.buisiness.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.github.mizo432.xplanner.buisiness.model.UserStory;

@Repository
public interface UserStoryRepository extends JpaRepository<UserStory, Long> {

	List<UserStory> findByIteration(Long iterationId);

	@Query("SELECT us FROM UserStory us WHERE us.iteration.project.id = :projectId")
	List<UserStory> findByProjectId(Long projectId);

	@Query("SELECT us FROM UserStory us WHERE us.customer.id = :personId AND us.iteration.endDate > CURRENT_DATE")
	List<UserStory> findByCustomerId(Long personId);

	@Query("SELECT us FROM UserStory us WHERE us.trackerId = :personId AND us.iteration.endDate > CURRENT_DATE")
	List<UserStory> findByTrackerId(Long personId);

	List<UserStory> findByNameContaining(String name);

}
