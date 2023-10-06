package com.fssa.leavemanagement.errors;

public class EmployeeLeaveDetailsErrors {
	private EmployeeLeaveDetailsErrors() {
//	private constructor
	}

	public static final String INVALID_REASON = "Reason cannot be empty or less than 3 characters";
	public static final String INVALID_DAYS = "Days cannot be zero or negative or maximum from the leave balance";
	public static final String INVALID_START_DATE = "Start Date cannot be in past";
	public static final String INVALID_END_DATE = "End date cannot be before the start date";
	public static final String INVALID_DETAILS = "Employee Leave Details cannot be null";
	public static final String EXCEEDS_LEAVE_BALANCE = "Number of days exceeds the leave balance";
	public static final String WEEKEND_LEAVE = "Cannot Apply Leave on Weekends";
	public static final String CANNOT_RELIEVE = "An employee cannot be relieved on the same day they joined";
}
