package com.github.mizo432.xplanner.buisiness.model.virtual;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class TimesheetEntry implements Serializable {

    private String projectName;
    private String iterationName;
    private String storyName;
    private BigDecimal totalDuration = new BigDecimal(0.0);
    private String personName;
    private Long projectId;
    private Long iterationId;
    private Long storyId;

    public TimesheetEntry() {
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getIterationName() {
        return iterationName;
    }

    public void setIterationName(String iterationName) {
        this.iterationName = iterationName;
    }

    public String getStoryName() {
        return storyName;
    }

    public void setStoryName(String storyName) {
        this.storyName = storyName;
    }

    public BigDecimal getTotalDuration() {
        return totalDuration.setScale(1, RoundingMode.HALF_EVEN);
    }

    public void setTotalDuration(BigDecimal totalDuration) {
        this.totalDuration = totalDuration;
    }

    public void setTotalDuration(double totalDuration) {
        this.totalDuration = this.totalDuration.add(new BigDecimal(totalDuration));
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getIterationId() {
        return iterationId;
    }

    public void setIterationId(Long iterationId) {
        this.iterationId = iterationId;
    }

    public Long getStoryId() {
        return storyId;
    }

    public void setStoryId(Long storyId) {
        this.storyId = storyId;
    }

}