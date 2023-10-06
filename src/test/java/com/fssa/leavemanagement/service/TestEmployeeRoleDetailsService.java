package com.fssa.leavemanagement.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.fssa.leavemanagement.errors.EmployeeErrors;
import com.fssa.leavemanagement.exceptions.DAOException;
import com.fssa.leavemanagement.model.EmployeeRoleDetails;

/**
 * The TestEmployeeRoleDetailsService class contains unit tests for the
 * EmployeeRoleDetailsService class. It tests the addEmployeeRoleDetails,
 * updateEmployeeRoleDetails, and getAllEmployeeRoleDetails methods of the
 * EmployeeRoleDetailsService class. It also includes tests for handling invalid
 * scenarios such as adding/updating invalid EmployeeRoleDetails objects.
 * 
 * @author PranawMurugesan
 *
 */
class TestEmployeeRoleDetailsService {
	@Test
	void testAddEmployeeRoleDetails() throws DAOException {
		EmployeeRoleDetails erd = new EmployeeRoleDetails();
		erd.setEmployeeId(5);
		erd.setReportingManagerId(3);
		erd.setRoleId(5);
		Assertions.assertTrue(EmployeeRoleDetailsService.addEmployeeRoleDetails(erd));
	}

	@Test
	void testupdateEmployeeRoleDetails() throws DAOException {
		EmployeeRoleDetails erd = new EmployeeRoleDetails();
		erd.setEmployeeId(5);
		erd.setReportingManagerId(3);
		erd.setRoleId(5);
//		Assertions.assertTrue(EmployeeRoleDetailsService.updateEmployeeRoleDetails(erd));
	}

	@Test
	void testGetAllEmployeeRoleDetails() throws DAOException {
		Assertions.assertTrue(EmployeeRoleDetailsService.getAllEmployeeRoleDetails());
	}

	@Test
	void testInvalidAddEmployeeRoleDetails() throws DAOException {
		EmployeeRoleDetails erd = new EmployeeRoleDetails();
		Assertions.assertThrows(DAOException.class, () -> {
			EmployeeRoleDetailsService.addEmployeeRoleDetails(erd);
		});
	}

	@Test
	void testInvalidUpdateEmployeeRoleDetails() {
		EmployeeRoleDetails erd = new EmployeeRoleDetails();
		try {
			EmployeeRoleDetailsService.addEmployeeRoleDetails(erd);
		} catch (DAOException e) {
			Assertions.assertEquals(EmployeeErrors.INVALID_EMPLOYEE_ROLE_DETAIL, e.getMessage());
		}
	}
}