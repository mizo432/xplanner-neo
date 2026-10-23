package com.github.mizo432.xplanner.buisiness.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.github.mizo432.xplanner.buisiness.model.Nameable;

@MappedSuperclass
public class NamedObject extends DomainObject implements Nameable {
	private String name;
	private String description;

	@Column(name = "name", length = 255)
	public String getName() {
		return this.name;
	}

	@Column(name = "description", length = 65535)
	public String getDescription() {
		return this.description;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		NamedObject other = (NamedObject) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
