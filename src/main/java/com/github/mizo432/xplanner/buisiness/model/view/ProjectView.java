package com.github.mizo432.xplanner.buisiness.model.view;

import java.util.List;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import com.github.mizo432.xplanner.buisiness.model.Iteration;
import com.github.mizo432.xplanner.buisiness.model.NamedObject;
import com.github.mizo432.xplanner.buisiness.model.Note;
import com.github.mizo432.xplanner.buisiness.model.Person;

//@Entity
//@Loader(namedQuery="project.loadView")
//@NamedQuery(name="project.loadView", query="select project.id as id,project.name as name from project in class com.github.mizo432.xplanner.buisiness.model.Project where project.id=?")
@XmlRootElement
@SuppressFBWarnings(value = {"EQ_DOESNT_OVERRIDE_EQUALS", "EI_EXPOSE_REP", "EI_EXPOSE_REP2"}, justification = "DomainObject implements equals based on ID, sufficient for JPA entities; getters return mutable objects, acceptable for JPA")
public class ProjectView extends NamedObject{
	private Boolean hidden;
    private List<Person> notificationReceivers;
	private List<Note> notes;
	private Iteration currentIteration;
	
	public Boolean getHidden() {
		return hidden;
	}
	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}

//	@OneToMany()
//    @JoinTable(name="notification_receivers", joinColumns=@JoinColumn(name="attachedTo_id"))
	@XmlTransient
	public List<Person> getNotificationReceivers() {
		return notificationReceivers;
	}
	public void setNotificationReceivers(List<Person> notificationReceivers) {
		this.notificationReceivers = notificationReceivers;
	}

//	@ManyToMany()
//    @JoinTable(name="note", joinColumns=@JoinColumn(name="project_id"), inverseJoinColumns=@JoinColumn(name="person_id"))
	@XmlTransient
	public List<Note> getNotes() {
		return notes;
	}
	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}
	
	@XmlTransient
	public Iteration getCurrentIteration() {
		return currentIteration;
	}
	public void setCurrentIteration(Iteration currentIteration) {
		this.currentIteration = currentIteration;
	}
	
}
