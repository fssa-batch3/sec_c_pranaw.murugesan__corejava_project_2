package com.fssa.leavemanagement.model;

public enum RoleTypes {
	CEO("CEO"), MANAGER("Manager"), TEAMLEAD("Team Lead"), HR("HR");

	private String name;

	/**
	 * Constructor for creating a RoleTypes enum constant with the specified name.
	 * 
	 * @param name The name representing the role type.
	 */
	RoleTypes(String name) {
		this.name = name;
	}

	/**
	 * Get the name representing the role type.
	 * 
	 * @return The name of the role type.
	 */
	public String getName() {
		return name;
	}

}
