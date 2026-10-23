package com.github.mizo432.xplanner.buisiness.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.github.mizo432.xplanner.buisiness.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

	Optional<Person> findByUserId(String userId);


	@Query("SELECT p FROM Person p ORDER BY p.name")
	List<Person> selectOrderByName();

	List<Person> findByNameContaining(String name);

}
