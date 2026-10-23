package com.github.mizo432.xplanner.buisiness.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.github.mizo432.xplanner.buisiness.model.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

	@Query("SELECT n FROM Note n WHERE n.attachedToId = :fileId")
	List<Note> findByFileId(Long fileId);

	@Query("SELECT n FROM Note n WHERE n.attachedToId = :taskId")
	List<Note> findByTaskId(Long taskId);

	@Query("SELECT n FROM Note n WHERE n.attachedToId = :userStoryId")
	List<Note> findByUserStoryId(Long userStoryId);

}
