package com.fssa.leavemanagement.errors;

public class EmployeeLeaveDetailsErrors {
	private EmployeeLeaveDetailsErrors() {
//	private constructor
	}

	public static final String INVALID_REASON = "Reason cannot be empty or less than 3 characters";
	public static final String INVALID_DAYS = "Days cannot be zero or less than zero";
	public static final String INVALID_DATE = "Date cannot be in past";
	public static final String INVALID_DETAILS = "Employee Leave Details cannot be null";
}
