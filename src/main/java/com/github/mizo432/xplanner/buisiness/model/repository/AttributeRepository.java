package com.github.mizo432.xplanner.buisiness.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.mizo432.xplanner.buisiness.model.Attribute;

@Repository
public interface AttributeRepository extends JpaRepository<Attribute, Long> {

	List<Attribute> findByAttributeIdTargetId(int targetId);

}
