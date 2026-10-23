package com.github.mizo432.xplanner.buisiness.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.mizo432.xplanner.buisiness.model.History;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {

	List<History> findByTargetId(Long targetId);

	List<History> findByContainerId(Long projectId);

}
