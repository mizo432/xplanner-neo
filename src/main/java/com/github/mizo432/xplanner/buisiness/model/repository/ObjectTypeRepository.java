package com.github.mizo432.xplanner.buisiness.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.mizo432.xplanner.buisiness.model.ObjectType;

@Repository
public interface ObjectTypeRepository extends JpaRepository<ObjectType, Long> {

	List<ObjectType> findByName(String typeId);

}
