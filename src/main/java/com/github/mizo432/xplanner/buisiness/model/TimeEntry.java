package com.github.mizo432.xplanner.buisiness.model;

import java.util.Collection;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;

import com.github.mizo432.xplanner.constant.XPlannerProperties;
import java.util.function.ToDoubleFunction;
import org.apache.commons.beanutils.PropertyUtils;

/**
*    XplannerPlus, agile planning software
*    @author Maksym_Chyrkov. 
*    Copyright (C) 2009  Maksym Chyrkov
*    This program is free software: you can redistribute it and/or modify
*    it under the terms of the GNU General Public License as published by
*    the Free Software Foundation, either version 3 of the License, or
*    (at your option) any later version.
*
*    This program is distributed in the hope that it will be useful,
*    but WITHOUT ANY WARRANTY; without even the implied warranty of
*    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*    GNU General Public License for more details.
*
*    You should have received a copy of the GNU General Public License
*    along with this program.  If not, see <http://www.gnu.org/licenses/>
* 	 
*/

@Entity
@Table(name = "time_entry")
@jakarta.annotation.Generated(value = "Junie", comments = "Suppressing @Temporal deprecation warnings until migration to java.time")
@SuppressWarnings("deprecation")
public class TimeEntry extends DomainObject implements java.io.Serializable, Nameable {
	private static final long serialVersionUID = -3275141598175122473L;
	private Date startTime;
	private Date endTime;
	private double duration;
	private int person1Id;
	private int person2Id;
	private Task task;
	private Date reportDate;
	private String description;

	public TimeEntry() {
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_time", length = 19)
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_time", length = 19)
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name = "duration", precision = 22, scale = 0)
	public double getDuration() {
		if (startTime != null && endTime != null) {
            duration = (endTime.getTime() - startTime.getTime()) / 3600000.0;
        }
        return duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

	@Column(name = "person1_id")
	public int getPerson1Id() {
		return this.person1Id;
	}

	public void setPerson1Id(int person1Id) {
		this.person1Id = person1Id;
	}

	@Column(name = "person2_id")
	public int getPerson2Id() {
		return this.person2Id;
	}

	public void setPerson2Id(int person2Id) {
		this.person2Id = person2Id;
	}

	@ManyToOne
	@JoinColumn(name="task_id")
	public Task getTask() {
		return this.task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "report_date", length = 19)
	public Date getReportDate() {
		return this.reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}
	
    /**
     * The classic way for XPlanner to calculate "effort" is in idea wall clock time or
     * pair-programming hours. Some teams would like to do labor tracking using XPlanner
     * and want to double the effort measured for a paired time entry.
     *
     * This behavior is configurable in xplanner.properties.
     * @return measured effort
     */
	@Transient
	@Deprecated
    public double getEffort() {
       boolean adjustHoursForPairing = "double".equalsIgnoreCase(new XPlannerProperties().getProperty("xplanner.pairprogramming", "single"));
        boolean isPairedEntry = person1Id != 0 && person2Id != 0;
        return (adjustHoursForPairing && isPairedEntry) ? getDuration() * 2 : getDuration();
    }

	@Deprecated
	   public boolean isCurrentlyActive(int personId) {
		    return startTime != null && endTime == null && duration == 0
		        && (personId == person1Id || personId == person2Id);
		  }
	
	
	@Column(name = "description", length = 65535)
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	@Transient
	@Override
	public String getName() {
		return null;
	}

  public static class CollectionUtils {
     public static double sum(Collection<?> collection, ToDoubleFunction<?> filter) {
        if (collection == null || collection.isEmpty()) return 0.0;
        double value = 0.0;
        for (Object item : collection) {
           @SuppressWarnings("unchecked")
           ToDoubleFunction<Object> objFilter = (ToDoubleFunction<Object>) filter;
           value += objFilter.applyAsDouble(item);
        }
        return value;
     }

     public static class DoublePropertyFilter implements ToDoubleFunction<Object> {
        private String name;

        public DoublePropertyFilter(String name) {this.name = name;}

        @Override
        public double applyAsDouble(Object o) {
           try {
              return ((Number) PropertyUtils.getProperty(o, name)).doubleValue();
           } catch (Exception e) { throw new RuntimeException(e); }
        }
     }
  }
}
