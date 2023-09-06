package com.fssa.leavemanagement.model;

public enum LeaveTypes {
	SICK("SL"), CASUAL("CL"), EARNED("EL");

	private final String getName;

	LeaveTypes(String string) {
		this.getName = string;
	}

	public String getName() {
		return getName;
	}

}
