package com.github.mizo432.xplanner.buisiness.model;


import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

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
@Table(name = "notification_receivers")
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"}, justification = "Getters return mutable objects, acceptable for JPA")
public class NotificationReceivers implements java.io.Serializable {
	private static final long serialVersionUID = -4537343448751435206L;
	private NotificationReceiversId id;

	public NotificationReceivers() {
	}

	public NotificationReceivers(NotificationReceiversId id) {
		this.id = id;
	}

	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "projectId", column = @Column(name = "project_id", nullable = false)),
			@AttributeOverride(name = "personId", column = @Column(name = "person_id", nullable = false)) })
	public NotificationReceiversId getId() {
		return this.id;
	}

	public void setId(NotificationReceiversId id) {
		this.id = id;
	}

}
