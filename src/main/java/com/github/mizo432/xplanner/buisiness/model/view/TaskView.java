package com.github.mizo432.xplanner.buisiness.model.view;

import java.util.Date;

import com.github.mizo432.xplanner.buisiness.model.NamedObject;

public class TaskView extends NamedObject{
	private String type;
	private boolean completed;
	private double originalEstimate;
	private double estimatedHours;
	private Date createdDate;
	private int acceptorId;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isCompleted() {
		return completed;
	}
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	public double getOriginalEstimate() {
		return this.originalEstimate;
	}
	public void setOriginalEstimate(double originalEstimate) {
		this.originalEstimate = originalEstimate;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public double getEstimatedHours() {
		return estimatedHours;
	}
	public void setEstimatedHours(double estimatedHours) {
		this.estimatedHours = estimatedHours;
	}
	public int getAcceptorId() {
		return acceptorId;
	}
	public void setAcceptorId(int acceptorId) {
		this.acceptorId = acceptorId;
	}
}
