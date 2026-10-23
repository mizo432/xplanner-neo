package com.github.mizo432.xplanner.buisiness.model;


import java.security.Principal;
import java.util.Date;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;

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
@Table(name = "roles", uniqueConstraints = @UniqueConstraint(columnNames = "role"))
public class Role implements java.io.Serializable, Principal, Identifiable {
	public static final String SYSADMIN = "sysadmin";
	public static final String ADMIN = "admin";
	public static final String EDITOR = "editor";
	public static final String VIEWER = "viewer";
	private static final long serialVersionUID = 4921403899778522202L;
	private Integer left;
	private Integer right;
	private String name;
	private Long id;

	public Role() {
	}

	public Role(String name) {
		setName(name);
	}

	@Column(name = "lft")
	public Integer getLeft() {
		return this.left;
	}

	public void setLeft(Integer lft) {
		this.left = lft;
	}

	@Column(name = "rgt")
	public Integer getRight() {
		return this.right;
	}

	public void setRight(Integer rgt) {
		this.right = rgt;
	}
	
	@Override
	@Column(name="role", nullable=false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
