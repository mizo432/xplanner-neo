package com.github.mizo432.xplanner.buisiness.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class DataSampleId implements Serializable {
	private static final long serialVersionUID = -5204295414444697497L;
	private Date sampleTime;
	private int referenceId;
	private String aspect;

	@Column(name = "sampleTime", nullable = false, length = 19)
	public Date getSampleTime() {
		return this.sampleTime;
	}

	public void setSampleTime(Date sampleTime) {
		this.sampleTime = sampleTime;
	}

	@Column(name = "referenceId", nullable = false)
	public int getReferenceId() {
		return this.referenceId;
	}

	public void setReferenceId(int referenceId) {
		this.referenceId = referenceId;
	}

	@Column(name = "aspect", nullable = false)
	public String getAspect() {
		return this.aspect;
	}

	public void setAspect(String aspect) {
		this.aspect = aspect;
	}
	
	

}
