package com.github.mizo432.xplanner.buisiness.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.mizo432.xplanner.buisiness.model.NotificationReceivers;

@Repository
public interface NotificationReceiversRepository extends JpaRepository<NotificationReceivers, Long> {

}
