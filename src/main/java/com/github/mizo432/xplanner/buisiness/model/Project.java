package com.github.mizo432.xplanner.buisiness.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MapKey;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.xml.bind.annotation.XmlRootElement;

import com.github.mizo432.xplanner.buisiness.model.NoteAttachable;

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
@Table(name = "project")
@XmlRootElement
public class Project extends NamedObject implements java.io.Serializable, NoteAttachable {
	private static final long serialVersionUID = -6137321799143662647L;
	public static final String HIDDEN = "hidden";
	private Boolean hidden;
	private Iteration backlog;
    private List<Iteration> iterations = new ArrayList<Iteration>();
	private List<Person> notificationReceivers;

	public Project() {
	}

	@Column(name = "is_hidden")
	public Boolean getHidden() {
		return this.hidden;
	}

	public void setHidden(Boolean isHidden) {
		this.hidden = isHidden;
	}

	@OneToMany(mappedBy="project", cascade=CascadeType.ALL)
	public List<Iteration> getIterations() {
		return iterations;
	}

	public void setIterations(List<Iteration> iterations) {
		this.iterations = iterations;
	}

    public void setNotificationReceivers(List<Person> notificationReceivers) {
        this.notificationReceivers = notificationReceivers;
    }
    
    @ManyToMany()
    @JoinTable(name="notification_receivers", joinColumns=@JoinColumn(name="project_id"), inverseJoinColumns=@JoinColumn(name="person_id"))
	public List<Person> getNotificationReceivers() {
		return notificationReceivers;
	}

    @OneToOne(cascade=CascadeType.REMOVE)
    @JoinColumn(name="backlog_id", insertable=true, updatable=true, unique=true, nullable=true)
    public Iteration getBacklog() {
		return backlog;
	}

	public void setBacklog(Iteration backlog) {
		this.backlog = backlog;
	}

    @Transient
	public Boolean isHidden() {
		return this.hidden;
	}
    
    @ElementCollection
    @JoinTable(name="attribute" , joinColumns=@JoinColumn(name="targetId"))
    @MapKeyColumn(name="name")
    @Column(name="\"value\"")
	public Map<String,String> getAttributes() {
		return super.getAttributes();
	}

}
