package com.github.mizo432.xplanner.buisiness.model;


import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;

import com.github.mizo432.xplanner.buisiness.model.Identifiable;

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
@Table(name = "history")
@jakarta.annotation.Generated(value = "Junie", comments = "Suppressing @Temporal deprecation warnings until migration to java.time")
@SuppressWarnings("deprecation")
public class History implements Identifiable, Serializable {
    public static final String CREATED = "created";
    public static final String UPDATED = "updated";
    public static final String DELETED = "deleted";
    public static final String REESTIMATED = "reestimated";
    public static final String ITERATION_STARTED = "started";
    public static final String ITERATION_CLOSED = "closed";
    public static final String MOVED = "moved";
    public static final String MOVED_IN = "moved in";
    public static final String MOVED_OUT = "moved out";
    public static final String CONTINUED = "continued";

	private static final long serialVersionUID = 87622707160315552L;
	private Long id;
	private Date whenHappened;
	private Long containerId;
	private Long targetId;
	private String objectType;
	private String action;
	private String description;
	private Long personId;
	private boolean notified;

	public History() {
	}

	public History(Long id) {
		this.id = id;
	}

	public History(Date whenHappened, Long containerId,
			Long targetId, String objectType, String action,
			String description, Long personId) {
		this.whenHappened = whenHappened;
		this.containerId = containerId;
		this.targetId = targetId;
		this.objectType = objectType;
		this.action = action;
		this.description = description;
		this.personId = personId;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "when_happened", length = 19)
	public Date getWhenHappened() {
		return this.whenHappened;
	}

	public void setWhenHappened(Date whenHappened) {
		this.whenHappened = whenHappened;
	}

	@Column(name = "container_id")
	public Long getContainerId() {
		return this.containerId;
	}

	public void setContainerId(Long containerId) {
		this.containerId = containerId;
	}

	@Column(name = "target_id")
	public Long getTargetId() {
		return this.targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	@Column(name = "object_type")
	public String getObjectType() {
		return this.objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	@Column(name = "action")
	public String getAction() {
		return this.action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	@Column(name = "description")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "person_id")
	public Long getPersonId() {
		return this.personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	@Column(name = "notified")
	public boolean getNotified() {
		return this.notified;
	}

	public void setNotified(boolean notified) {
		this.notified = notified;
	}

	@Transient
	public boolean isNotified() {
		return notified;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result
				+ ((containerId == null) ? 0 : containerId.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (notified ? 1231 : 1237);
		result = prime * result
				+ ((objectType == null) ? 0 : objectType.hashCode());
		result = prime * result
				+ ((personId == null) ? 0 : personId.hashCode());
		result = prime * result
				+ ((targetId == null) ? 0 : targetId.hashCode());
		result = prime * result
				+ ((whenHappened == null) ? 0 : whenHappened.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		History other = (History) obj;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
		if (containerId == null) {
			if (other.containerId != null)
				return false;
		} else if (!containerId.equals(other.containerId))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (notified != other.notified)
			return false;
		if (objectType == null) {
			if (other.objectType != null)
				return false;
		} else if (!objectType.equals(other.objectType))
			return false;
		if (personId == null) {
			if (other.personId != null)
				return false;
		} else if (!personId.equals(other.personId))
			return false;
		if (targetId == null) {
			if (other.targetId != null)
				return false;
		} else if (!targetId.equals(other.targetId))
			return false;
		if (whenHappened == null) {
			if (other.whenHappened != null)
				return false;
		} else if (!whenHappened.equals(other.whenHappened))
			return false;
		return true;
	}

		

}
