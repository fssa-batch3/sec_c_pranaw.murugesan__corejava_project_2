package com.fssa.leavemanagement.model;

public enum EmployeeLeaveStatus {
	 APPROVED("APPROVED"), CANCELLED("CANCELLED"), REJECTED("REJECTED");

	private final String status;

	EmployeeLeaveStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}