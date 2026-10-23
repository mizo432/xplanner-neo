package com.github.mizo432.xplanner.buisiness.model.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.mizo432.xplanner.buisiness.model.DataSample;

@Repository
public interface DataSampleRepository extends JpaRepository<DataSample, Long> {

	List<DataSample> findByReferenceId(int projectId);

	List<DataSample> findBySampleTimeAndReferenceIdAndAspect(Date sampleTime, int referenceId, String aspect);

}
