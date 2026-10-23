package com.github.mizo432.xplanner.buisiness.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.mizo432.xplanner.buisiness.model.Directory;

@Repository
public interface DirectoryRepository extends JpaRepository<Directory, Long> {

	List<Directory> findByName(String projectId);

}
