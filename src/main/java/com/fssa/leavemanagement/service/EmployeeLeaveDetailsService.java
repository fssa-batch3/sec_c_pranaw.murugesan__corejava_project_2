package com.fssa.leavemanagement.service;

import java.sql.SQLException;

import com.fssa.leavemanagement.dao.EmployeeLeaveDetailsDao;
import com.fssa.leavemanagement.exceptions.DAOException;
import com.fssa.leavemanagement.model.EmployeeLeaveDetails;
import com.fssa.leavemanagement.validator.EmployeeLeaveDetailsValidator;

public class EmployeeLeaveDetailsService {
	public static boolean applyLeave(EmployeeLeaveDetails leaveDetails) throws DAOException, SQLException {
		if (EmployeeLeaveDetailsValidator.validate(leaveDetails)) {
			EmployeeLeaveDetailsDao.applyLeaveRequest(leaveDetails);
		}
		return true;
	}
	public static boolean updateLeave(int employeeId, String status) {
		
		return true;
	}
}
