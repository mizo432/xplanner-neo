package com.github.mizo432.xplanner.buisiness.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.mizo432.xplanner.buisiness.model.Setting;

@Repository
public interface SettingRepository extends JpaRepository<Setting, Long> {

	List<Setting> findByScope(com.github.mizo432.xplanner.buisiness.model.enums.SettingScope scope);

}
