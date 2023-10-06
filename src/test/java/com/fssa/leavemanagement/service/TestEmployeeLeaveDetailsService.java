package com.fssa.leavemanagement.service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.fssa.leavemanagement.exceptions.DAOException;
import com.fssa.leavemanagement.exceptions.InvalidEmployeeException;
import com.fssa.leavemanagement.exceptions.ValidatorException;
import com.fssa.leavemanagement.model.EmployeeLeaveBalance;
import com.fssa.leavemanagement.model.EmployeeLeaveDetails;
import com.fssa.leavemanagement.model.LeaveTypes;

class TestEmployeeLeaveDetailsService {
	@Test
	void testGetLeaveRequestsByEmail() throws SQLException, DAOException, InvalidEmployeeException {
		String email = "girish@freshworks.com";
		List<EmployeeLeaveDetails> arr = EmployeeLeaveDetailsService.getLeaveRequestsByEmail(email);
		Assertions.assertNotNull(arr);
	}

	@Test
	void testGetLeaveBalanceByEmployeeId() throws DAOException {
		int id = 1;
		EmployeeLeaveBalance elb = EmployeeLeaveDetailsService.getLeaveBalanceByEmployeeId(id);
		Assertions.assertNotNull(elb);
	}

	@Test
	void testGetLeaveBalanceByEmail() throws InvalidEmployeeException, SQLException, DAOException {
		String email = "girish@freshworks.com";
		EmployeeLeaveBalance elb = EmployeeLeaveDetailsService.getLeaveBalanceByEmail(email);
		Assertions.assertNotNull(elb);
	}

	@Test
	void testGetAllLeaveBalance() throws DAOException {
		List<EmployeeLeaveBalance> arr = EmployeeLeaveDetailsService.getAllLeaveBalance();
		Assertions.assertNotNull(arr);
	}

	@Test
	void testApplyLeave() throws DAOException, SQLException, ValidatorException {
		EmployeeLeaveDetails leaveDetails = new EmployeeLeaveDetails();
		leaveDetails.setComments(null);
		leaveDetails.setEmployeeId(2);

		LocalDate today = LocalDate.now();
		LocalDate tomorrow = today.plusDays(1);
		leaveDetails.setEndDate(tomorrow);
		leaveDetails.setLeaveReason("Testing with TestCase");
		leaveDetails.setLeaveType(LeaveTypes.CASUAL);
		leaveDetails.setManagerId(1);
		leaveDetails.setName("Suman Gopalan");
		leaveDetails.setNoOfDays(1);
		leaveDetails.setStartDate(today);
		String email = "suman.gopalan@freshworks.com";
		Assertions.assertTrue(EmployeeLeaveDetailsService.applyLeave(leaveDetails, email));
	}
}
