package com.fssa.leavemanagement.model;

import java.time.LocalDate;

public class EmployeeLeaveDetails {

	@Override
	public String toString() {
		return "EmployeeLeaveDetails [id=" + id + ", name=" + name + ", employeeId=" + employeeId + ", leaveType="
				+ leaveType + ", startDate=" + startDate + ", endDate=" + endDate + ", noOfDays=" + noOfDays
				+ ", leaveReason=" + leaveReason + ", status=" + status + ", managerId=" + managerId + ", comments="
				+ comments + "]\n";
	}

	private int id;
	private String name;
	private int employeeId;
	private LeaveTypes leaveType;
	private LocalDate startDate;
	private LocalDate endDate;
	private double noOfDays;
	private String leaveReason;
	private String status;
	private int managerId;
	private String comments;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LeaveTypes getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(LeaveTypes leaveType) {
		this.leaveType = leaveType;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public double getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(double noOfDays) {
		this.noOfDays = noOfDays;
	}

	public String getLeaveReason() {
		return leaveReason;
	}

	public void setLeaveReason(String leaveReason) {
		this.leaveReason = leaveReason;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getManagerId() {
		return managerId;
	}

	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
}
