package com.github.mizo432.xplanner.buisiness.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.mizo432.xplanner.buisiness.model.Identifier;

@Repository
public interface IdentifierRepository extends JpaRepository<Identifier, Long> {

}
