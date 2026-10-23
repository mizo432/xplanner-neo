package com.github.mizo432.xplanner.buisiness.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.mizo432.xplanner.buisiness.model.File;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {

	List<File> findByDirectory(Long directoryId);

}
