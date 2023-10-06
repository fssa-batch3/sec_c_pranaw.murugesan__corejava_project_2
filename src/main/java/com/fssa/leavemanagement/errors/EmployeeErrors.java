package com.fssa.leavemanagement.errors;

/**
 * The EmployeeErrors interface defines constant error messages related to
 * employee validation and operations. These messages can be used to communicate
 * specific error scenarios in the application. It contains error messages for
 * various validation checks and error handling related to employees. Each error
 * message describes the reason for the error and provides guidance on how to
 * fix it. The constants in this interface are used for better code organization
 * and to avoid hard coding error messages in the code base. This interface
 * should be implemented wherever custom error messages related to employees are
 * required. Example Usage: String errorMessage = EmployeeErrors.INVALID_NAME;
 * System.out.println(errorMessage); Output: "Invalid Employee Name cannot be
 * empty or lesser than 3 characters" Similarly, other error messages can be
 * accessed and used in the application.
 * 
 * @author PranawMurugesan
 *
 */
public class EmployeeErrors {
	private EmployeeErrors() {
//		private constructor
	}

	public static final String INVALID_NAME = "Name cannot be empty or lesser than 3 characters or should not contain numbers";
	public static final String INVALID_EMAIL = "Email should contains @ and shouldn't have spaces, should contain freshworks email";
	public static final String INVALID_CREDENTIALS = "Minimum eight characters, at least one letter, one number and one special character";
	public static final String INVALID_DATE = "Date of Joining & Relieving cannot be in the future date";
	public static final String INVALID_STATUS = "Invalid Employee Status";
	public static final String INVALID_EMPLOYEE = "Invalid Employee";
	public static final String CANNOT_ADD_EMPLOYEE = "Cannot add Employee";
	public static final String INVALID_ID = "Id cannot 0 or negative or more than 2147483647";
	public static final String INVALID_EMPLOYEE_ROLE_DETAIL = "Invalid Employee Role Detail passed in DAO Layer";
	public static final String EMPLOYEE_ALREADY_EXISTS = "Cannot Add, Employee Already Exists";
	public static final String INVALID_MANAGER = "Manager cannot be empty or outside freshworks";
	public static final String CEO_EXISTS = "You Cannot set CEO if CEO is already exist";

}
