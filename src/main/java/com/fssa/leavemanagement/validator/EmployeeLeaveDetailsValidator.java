package com.fssa.leavemanagement.validator;

import java.time.LocalDate;

import com.fssa.leavemanagement.errors.EmployeeLeaveDetailsErrors;
import com.fssa.leavemanagement.exceptions.DAOException;
import com.fssa.leavemanagement.model.EmployeeLeaveDetails;

public class EmployeeLeaveDetailsValidator {
	private EmployeeLeaveDetailsValidator() {
//		private constructor
	}

	public static boolean validate(EmployeeLeaveDetails leaveDetails) throws DAOException {
		if (leaveDetails == null) {
			throw new DAOException(EmployeeLeaveDetailsErrors.INVALID_DETAILS);
		}
		validateDate(leaveDetails.getStartDate());
		validateDate(leaveDetails.getEndDate());
		validateNoOfDays(leaveDetails.getNoOfDays());
		validateLeaveReason(leaveDetails.getLeaveReason());
		return true;
	}

	public static boolean validateDate(LocalDate date) throws DAOException {
		if (date.isBefore(LocalDate.now())) {
			throw new DAOException(EmployeeLeaveDetailsErrors.INVALID_DATE);
		}
		return true;
	}

	public static boolean validateNoOfDays(int noOfDays) throws DAOException {
		if (noOfDays >= 0) {
			throw new DAOException(EmployeeLeaveDetailsErrors.INVALID_DAYS);
		}
		return true;
	}

	public static boolean validateLeaveReason(String reason) throws DAOException {
		if (reason == null || "".equals(reason) || reason.length() < 3) {
			throw new DAOException(EmployeeLeaveDetailsErrors.INVALID_REASON);
		}
		return true;
	}
}
