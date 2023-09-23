package com.fssa.leavemanagement.model;

public class Role {
	private int id;
	private String name;

	/**
	 * Default Constructor for creating an empty Role object.
	 */
	public Role() {

	}

	/**
	 * Constructor for creating a Role object with specified attributes.
	 * 
	 * @param id   The ID of the role.
	 * @param name The name of the role.
	 */
	public Role(String name) {
		this.name = name;
	}

	/**
	 * Get the ID of the role.
	 * 
	 * @return The ID of the role.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Set the ID of the role.
	 * 
	 * @param id The ID of the role to be set.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Get the name of the role.
	 * 
	 * @return The name of the role.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name of the role.
	 * 
	 * @param name The name of the role to be set.
	 */
	public void setName(String name) {
		this.name = name;
	}

}