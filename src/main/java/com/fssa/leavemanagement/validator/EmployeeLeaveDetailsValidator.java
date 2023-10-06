package com.fssa.leavemanagement.validator;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.fssa.leavemanagement.dao.EmployeeLeaveDetailsDao;
import com.fssa.leavemanagement.errors.EmployeeLeaveDetailsErrors;
import com.fssa.leavemanagement.exceptions.DAOException;
import com.fssa.leavemanagement.exceptions.ValidatorException;
import com.fssa.leavemanagement.model.EmployeeLeaveBalance;
import com.fssa.leavemanagement.model.EmployeeLeaveDetails;
import com.fssa.leavemanagement.model.EmployeeLeaveStatus;
import com.fssa.leavemanagement.model.LeaveTypes;

public class EmployeeLeaveDetailsValidator {
	private EmployeeLeaveDetailsValidator() {
//		private constructor
	}

	public static boolean validate(EmployeeLeaveDetails leaveDetails, String email) throws ValidatorException {
		if (leaveDetails == null) {
			throw new ValidatorException(EmployeeLeaveDetailsErrors.INVALID_DETAILS);
		}
		isValidLeaveType(leaveDetails.getLeaveType().getName());
		isValidLeaveStatus(leaveDetails.getStatus());
		validateStartDate(leaveDetails.getStartDate());
		validateEndDate(leaveDetails.getEndDate(), leaveDetails.getStartDate());
		validateNoOfDays(leaveDetails, email);
		validateLeaveReason(leaveDetails.getLeaveReason());
		if (isWeekend(leaveDetails.getStartDate()) || isWeekend(leaveDetails.getEndDate())) {
			throw new ValidatorException(EmployeeLeaveDetailsErrors.WEEKEND_LEAVE);
		}
		return true;
	}
	

	private static boolean isWeekend(LocalDate localDate) {

		// get Day of week for the passed LocalDate
		return (localDate.get(ChronoField.DAY_OF_WEEK) == 6) || (localDate.get(ChronoField.DAY_OF_WEEK) == 7);
	}

	public static boolean isValidLeaveType(String inputType) {
		for (LeaveTypes leaveType : LeaveTypes.values()) {
			if (leaveType.getName().equalsIgnoreCase(inputType)) {
				return true; // Match found
			}
		}
		return false; // No match found
	}

	public static boolean isValidLeaveStatus(String inputStatus) {
		for (EmployeeLeaveStatus leaveStatus : EmployeeLeaveStatus.values()) {
			if (leaveStatus.getStatus().equalsIgnoreCase(inputStatus)) {
				return true; // Match found
			}
		}
		return false; // No match found
	}

	public static boolean validateEndDate(LocalDate endDate, LocalDate startDate) throws ValidatorException {
		if (endDate.isBefore(startDate)) {
			throw new ValidatorException(EmployeeLeaveDetailsErrors.INVALID_END_DATE);
		}
		return true;
	}

	public static boolean validateStartDate(LocalDate date) throws ValidatorException {
		if (date.isBefore(LocalDate.now())) {
			throw new ValidatorException(EmployeeLeaveDetailsErrors.INVALID_START_DATE);
		}
		return true;
	}

	public static boolean validateNoOfDays(EmployeeLeaveDetails leaveDetails, String email) throws ValidatorException {
		try {
			EmployeeLeaveBalance elb = EmployeeLeaveDetailsDao.getLeaveBalanceByEmail(email);
			if (LeaveTypes.CASUAL.equals(leaveDetails.getLeaveType())) {
				if (elb.getCasualLeaveBalance() < leaveDetails.getNoOfDays()) {
					throw new ValidatorException(EmployeeLeaveDetailsErrors.EXCEEDS_LEAVE_BALANCE);
				}
			} else if (LeaveTypes.SICK.equals(leaveDetails.getLeaveType())) {
				if (elb.getSickLeaveBalance() < leaveDetails.getNoOfDays()) {
					throw new ValidatorException(EmployeeLeaveDetailsErrors.EXCEEDS_LEAVE_BALANCE);
				}
			} else {
				if (elb.getEarnedLeaveBalance() < leaveDetails.getNoOfDays()) {
					throw new ValidatorException(EmployeeLeaveDetailsErrors.EXCEEDS_LEAVE_BALANCE);
				}
			}
		} catch (SQLException | DAOException e) {
			throw new ValidatorException(e.getMessage());
		}
		return true;
	}

	public static boolean validateLeaveReason(String reason) throws ValidatorException {
		if (reason == null || "".equals(reason) || reason.length() < 3) {
			throw new ValidatorException(EmployeeLeaveDetailsErrors.INVALID_REASON);
		}
		return true;
	}
}
