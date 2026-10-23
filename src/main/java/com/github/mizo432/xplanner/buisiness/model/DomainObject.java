package com.github.mizo432.xplanner.buisiness.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.github.mizo432.xplanner.buisiness.model.Identifiable;

@MappedSuperclass
@jakarta.annotation.Generated(value = "Junie", comments = "Suppressing @Temporal deprecation warnings until migration to java.time")
@SuppressWarnings("deprecation")
public class DomainObject implements Identifiable {
	private Long id;
	private Date lastUpdateTime;
	private Map<String,String> attributes = new HashMap<String,String>();
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_update", length = 19)
	public Date getLastUpdateTime() {
		return this.lastUpdateTime;
	}
	
	@PreUpdate
	public void onUpdate(){
		this.lastUpdateTime = new Date();
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((lastUpdateTime == null) ? 0 : lastUpdateTime.hashCode());
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
		DomainObject other = (DomainObject) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastUpdateTime == null) {
			if (other.lastUpdateTime != null)
				return false;
		} else if (!lastUpdateTime.equals(other.lastUpdateTime))
			return false;
		return true;
	}
	
	public String getAttribute(String attributeName) {
		return getAttributes().get(attributeName);
	}

	@Transient
	public Map<String,String> getAttributes() {
		return attributes;
	}

	protected void setAttributes(Map<String,String> attributes) {
		this.attributes = attributes;
	}
}
