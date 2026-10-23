package com.github.mizo432.xplanner.buisiness.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "xdir")
@SuppressFBWarnings(value = {"EQ_DOESNT_OVERRIDE_EQUALS", "EI_EXPOSE_REP", "EI_EXPOSE_REP2"}, justification = "DomainObject implements equals based on ID, sufficient for JPA entities; getters return mutable objects, acceptable for JPA")
public class Directory extends DomainObject implements java.io.Serializable {
	private static final long serialVersionUID = -1957144606982414797L;
	private String name;
	private List<File> files;
	private Directory parent;
	private List<Directory> subdirectories;

	public Directory() {
	}

	@Column(name = "name")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(mappedBy="directory")
	public List<File> getFiles() {
		return files;
	}
	
	public void setFiles(List<File> files) {
		this.files = files;
	}

	@ManyToOne
	public Directory getParent() {
		return parent;
	}

	public void setParent(Directory parent) {
		this.parent = parent;	
	}
	
	
	@OneToMany()
	@JoinColumn(name="parent_id")
	public List<Directory> getSubdirectories() {
        return subdirectories;
    }

	public void setSubdirectories(List<Directory> subdirectories) {
		this.subdirectories = subdirectories;
	}

}
