package com.fssa.leavemanagement.errors;

/**
 * 
 * The RoleErrors interface defines constant error messages related to role
 * validation and operations. These messages can be used to communicate specific
 * error scenarios in the application. It contains error messages for various
 * validation checks and error handling related to roles. Each error message
 * describes the reason for the error and provides guidance on how to fix it.
 * The constants in this interface are used for better code organization and to
 * avoid hardcoding error messages in the codebase. This interface should be
 * implemented wherever custom error messages related to roles are required.
 * Note: The actual error message strings can be modified as per the
 * application's specific requirements. Example Usage: String errorMessage =
 * RoleErrors.INVALID_NAME; System.out.println(errorMessage); // Output: "Role
 * name cannot be lesser than 2 characters" Similarly, other error messages can
 * be accessed and used in the application.
 */
public class RoleErrors {
	private RoleErrors() {
//		 private constructor to hide the implicit public one.
	}

	public static final String INVALID_ID = "Id cannot be 0 or lesser than 0";
	public static final String INVALID_NAME = "Role name cannot be lesser than 2 characters";
	public static final String INVALID_ROLE = "Role cannot be null";
	public static final String ROLE_NOT_FOUND = "Invalid role name";
}
