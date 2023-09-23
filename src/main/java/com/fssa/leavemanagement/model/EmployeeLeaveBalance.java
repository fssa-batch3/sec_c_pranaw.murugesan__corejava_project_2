package com.fssa.leavemanagement.model;

public class EmployeeLeaveBalance {
	@Override
	public String toString() {
		return "EmployeeLeaveBalance [name=" + name + ", email=" + email + ", sickLeaveBalance=" + sickLeaveBalance
				+ ", casualLeaveBalance=" + casualLeaveBalance + ", earnedLeaveBalance=" + earnedLeaveBalance + "]";
	}

	private String name;
	private String email;
	private double sickLeaveBalance;
	private double casualLeaveBalance;
	private double earnedLeaveBalance;

	public EmployeeLeaveBalance() {
//		public constructor
	}

	public EmployeeLeaveBalance(String name, String email, double sickLeaveBalance, double casualLeaveBalance,
			byte earnedLeaveBalance) {
		super();
		this.name = name;
		this.email = email;
		this.sickLeaveBalance = sickLeaveBalance;
		this.casualLeaveBalance = casualLeaveBalance;
		this.earnedLeaveBalance = earnedLeaveBalance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public double getSickLeaveBalance() {
		return sickLeaveBalance;
	}

	public void setSickLeaveBalance(double sickLeaveBalance) {
		this.sickLeaveBalance = sickLeaveBalance;
	}

	public double getCasualLeaveBalance() {
		return casualLeaveBalance;
	}

	public void setCasualLeaveBalance(double casualLeaveBalance) {
		this.casualLeaveBalance = casualLeaveBalance;
	}

	public double getEarnedLeaveBalance() {
		return earnedLeaveBalance;
	}

	public void setEarnedLeaveBalance(double earnedLeaveBalance) {
		this.earnedLeaveBalance = earnedLeaveBalance;
	}
}
