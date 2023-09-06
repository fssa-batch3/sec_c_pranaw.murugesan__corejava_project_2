package com.fssa.leavemanagement.model;

public class EmployeeRoleDetails {
	private int roleId;
	private int employeeId;
	private int reportingManagerId;

	/**
	 * Default Constructor for creating an empty EmployeeRoleDetails object.
	 */
	public EmployeeRoleDetails() {

	}

	/**
	 * Constructor for creating an EmployeeRoleDetails object with specified
	 * attributes.
	 * 
	 * @param roleId             The ID of the role associated with the employee.
	 * @param employeeId         The ID of the employee.
	 * @param reportingManagerId The ID of the reporting manager (another employee)
	 *                           of the employee.
	 */
	public EmployeeRoleDetails(int roleId, int employeeId, int reportingManagerId) {
		this.roleId = roleId;
		this.employeeId = employeeId;
		this.reportingManagerId = reportingManagerId;
	}

	/**
	 * Get the ID of the role associated with the employee.
	 * 
	 * @return The ID of the role.
	 */
	public int getRoleId() {
		return roleId;
	}

	/**
	 * Set the ID of the role associated with the employee.
	 * 
	 * @param roleId The ID of the role to be set.
	 */
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	/**
	 * Get the ID of the employee.
	 * 
	 * @return The ID of the employee.
	 */
	public int getEmployeeId() {
		return employeeId;
	}

	/**
	 * Set the ID of the employee.
	 * 
	 * @param employeeId The ID of the employee to be set.
	 */
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	/**
	 * Get the ID of the reporting manager (another employee) of the employee.
	 * 
	 * @return The ID of the reporting manager.
	 */
	public int getReportingManagerId() {
		return reportingManagerId;
	}

	/**
	 * Set the ID of the reporting manager (another employee) of the employee.
	 * 
	 * @param reportingManagerId The ID of the reporting manager to be set.
	 */
	public void setReportingManagerId(int reportingManagerId) {
		this.reportingManagerId = reportingManagerId;
	}

}