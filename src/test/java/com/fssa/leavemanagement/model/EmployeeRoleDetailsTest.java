package com.fssa.leavemanagement.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 
 * The EmployeeRoleDetailsTest class contains unit tests for the
 * EmployeeRoleDetails class. It tests the getters, setters, and constructor of
 * the EmployeeRoleDetails class.
 */
class EmployeeRoleDetailsTest {

	@Test
	void testGetSetRoleId() {
		EmployeeRoleDetails erd = new EmployeeRoleDetails();
		int roleId = 1;
		erd.setRoleId(roleId);
		assertEquals(roleId, erd.getRoleId());
	}

	@Test
	void testGetSetEmployeeId() {
		EmployeeRoleDetails erd = new EmployeeRoleDetails();
		int employeeId = 2;
		erd.setEmployeeId(employeeId);
		assertEquals(employeeId, erd.getEmployeeId());
	}

	@Test
	void testGetSetReportingManagerId() {
		EmployeeRoleDetails erd = new EmployeeRoleDetails();
		int reportingManagerId = 3;
		erd.setReportingManagerId(reportingManagerId);
		assertEquals(reportingManagerId, erd.getReportingManagerId());
	}

	@Test
	void testConstructor() {
		int roleId = 1;
		int employeeId = 2;
		int reportingManagerId = 3;
		EmployeeRoleDetails erd = new EmployeeRoleDetails(roleId, employeeId, reportingManagerId);

		assertEquals(roleId, erd.getRoleId());
		assertEquals(employeeId, erd.getEmployeeId());
		assertEquals(reportingManagerId, erd.getReportingManagerId());
	}
}
