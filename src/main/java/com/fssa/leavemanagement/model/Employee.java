package com.fssa.leavemanagement.model;

import java.time.LocalDate;

public class Employee {
	private String name;
	private String email;
	private String password;
	LocalDate dateOfJoin;
	boolean status;
	LocalDate dateOfRelieve;
	Employee manager;

	/**
	 * Default Constructor for creating an empty Employee object.
	 */
	public Employee() {

	}

	/**
	 * Constructor for creating an Employee object with specified attributes.
	 * 
	 * @param name          The name of the employee.
	 * @param email         The email of the employee.
	 * @param password      The password of the employee.
	 * @param dateOfJoin    The date of joining of the employee.
	 * @param status        The status of the employee (active or inactive).
	 * @param dateOfRelieve The date of relieving of the employee (if applicable).
	 * @param manager       The manager of the employee (another Employee object).
	 */
	public Employee(String name, String email, String password, LocalDate dateOfJoin, boolean status,
			LocalDate dateOfRelieve, Employee manager) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.dateOfJoin = dateOfJoin;
		this.status = status;
		this.dateOfRelieve = dateOfRelieve;
		this.manager = manager;
	}

	/**
	 * Get the name of the employee.
	 * 
	 * @return The name of the employee.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name of the employee.
	 * 
	 * @param name The name of the employee to be set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the email of the employee.
	 * 
	 * @return The email of the employee.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Set the email of the employee.
	 * 
	 * @param email The email of the employee to be set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Get the password of the employee.
	 * 
	 * @return The password of the employee.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Set the password of the employee.
	 * 
	 * @param password The password of the employee to be set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Get the date of joining of the employee.
	 * 
	 * @return The date of joining of the employee.
	 */
	public LocalDate getDateOfJoin() {
		return dateOfJoin;
	}

	/**
	 * Set the date of joining of the employee.
	 * 
	 * @param dateOfJoin The date of joining of the employee to be set.
	 */
	public void setDateOfJoin(LocalDate dateOfJoin) {
		this.dateOfJoin = dateOfJoin;
	}

	/**
	 * Check if the employee is active or inactive.
	 * 
	 * @return true if the employee is active, false if inactive.
	 */
	public boolean isStatus() {
		return status;
	}

	/**
	 * Set the status of the employee (active or inactive).
	 * 
	 * @param status The status of the employee to be set.
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}

	/**
	 * Get the date of relieving of the employee (if applicable).
	 * 
	 * @return The date of relieving of the employee (or null if not applicable).
	 */
	public LocalDate getDateOfRelieve() {
		return dateOfRelieve;
	}

	/**
	 * Set the date of relieving of the employee (if applicable).
	 * 
	 * @param dateOfRelieve The date of relieving of the employee to be set.
	 */
	public void setDateOfRelieve(LocalDate dateOfRelieve) {
		this.dateOfRelieve = dateOfRelieve;
	}

	/**
	 * Get the manager of the employee (another Employee object).
	 * 
	 * @return The manager of the employee.
	 */
	public Employee getManager() {
		return manager;
	}

	/**
	 * Set the manager of the employee (another Employee object).
	 * 
	 * @param manager The manager of the employee to be set.
	 */
	public void setManager(Employee manager) {
		this.manager = manager;
	}

}