package com.fssa.leavemanagement.service;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import com.fssa.leavemanagement.dao.EmployeeLeaveDetailsDao;
import com.fssa.leavemanagement.exceptions.DAOException;
import com.fssa.leavemanagement.exceptions.InvalidEmployeeException;
import com.fssa.leavemanagement.exceptions.ValidatorException;
import com.fssa.leavemanagement.model.EmployeeLeaveBalance;
import com.fssa.leavemanagement.model.EmployeeLeaveDetails;
import com.fssa.leavemanagement.model.LeaveTypes;
import com.fssa.leavemanagement.validator.EmployeeLeaveDetailsValidator;
import com.fssa.leavemanagement.validator.EmployeeValidator;

public class EmployeeLeaveDetailsService {
	private EmployeeLeaveDetailsService() {
//		private constructor
	}

	public static List<EmployeeLeaveDetails> getLeaveRequestsByEmail(String email)
			throws SQLException, DAOException, InvalidEmployeeException {
		if (EmployeeValidator.validateEmail(email)) {

			return EmployeeLeaveDetailsDao.getLeaveRequestsByEmail(email);
		}
		return Collections.emptyList();
	}

	public static EmployeeLeaveBalance getLeaveBalanceByEmployeeId(int id) throws DAOException {
		try {
			return EmployeeLeaveDetailsDao.getLeaveBalanceByEmployeeId(id);
		} catch (SQLException | DAOException e) {
			throw new DAOException(e.getMessage());
		}
	}

	public static EmployeeLeaveBalance getLeaveBalanceByEmail(String email)
			throws InvalidEmployeeException, SQLException, DAOException {
		if (EmployeeValidator.validateEmail(email)) {

			return EmployeeLeaveDetailsDao.getLeaveBalanceByEmail(email);
		}
		return null;
	}

	public static List<EmployeeLeaveBalance> getAllLeaveBalance() throws DAOException {
		return EmployeeLeaveDetailsDao.getAllLeaveBalances();
	}

	public static boolean applyLeave(EmployeeLeaveDetails leaveDetails, String email)
			throws DAOException, SQLException, ValidatorException {
		if (EmployeeLeaveDetailsValidator.validate(leaveDetails, email)) {
			EmployeeLeaveDetailsDao.applyLeaveRequest(leaveDetails);
		}
		return true;
	}

	public static List<EmployeeLeaveDetails> getLeaveRequestsByManagerEmail(String email)
			throws InvalidEmployeeException, DAOException {
		if (EmployeeValidator.validateEmail(email)) {
			try {
				return EmployeeLeaveDetailsDao.getLeaveRequestsByManagerEmail(email);
			} catch (SQLException | DAOException e) {
				throw new DAOException(e.getMessage());
			}
		}
		return Collections.emptyList();
	}

	public static boolean updateLeaveRequest(String status, int id, String comments, EmployeeLeaveBalance e,
			int employeeId, LeaveTypes leaveType, int daysToReduce) throws SQLException, DAOException {

		if (leaveType.getName().equals("CL")) {
			leaveType = LeaveTypes.CASUAL;
		} else if (leaveType.getName().equals("SL")) {
			leaveType = LeaveTypes.SICK;
		} else {
			leaveType = LeaveTypes.EARNED;
		}

		if (EmployeeLeaveDetailsValidator.isValidLeaveStatus(status)
				&& EmployeeLeaveDetailsValidator.isValidLeaveType(leaveType.getName())) {
			EmployeeLeaveDetailsDao.updateLeaveRequest(status, id, comments);

			if (status.equals("APPROVED")) {
				EmployeeLeaveDetailsDao.reduceLeaveBalance(e, employeeId, leaveType, daysToReduce);
			}
		}

		return true;
	}
}
