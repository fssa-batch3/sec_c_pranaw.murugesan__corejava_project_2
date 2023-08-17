package com.fssa.leavemanagement.model;

public enum EmployeeLeaveStatus {
	PENDING("Pending"), APPROVED("Approved"), CANCELLED("Cancelled"), REJECTED("Rejected");

	private final String status;

	EmployeeLeaveStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}