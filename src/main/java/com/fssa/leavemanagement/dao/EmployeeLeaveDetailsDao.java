package com.fssa.leavemanagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.fssa.leavemanagement.exceptions.DAOException;
import com.fssa.leavemanagement.model.EmployeeLeaveDetails;
import com.fssa.leavemanagement.util.ConnectionUtil;

public class EmployeeLeaveDetailsDao {
	private EmployeeLeaveDetailsDao() {
//	private constructor
	}

	public static boolean applyLeaveRequest(EmployeeLeaveDetails leaveDetails) throws SQLException, DAOException {
		String insertQuery = "INSERT INTO employee_leave_details (employee_id, "
				+ "leave_type, start_date, end_date, no_of_days, leave_reason, "
				+ "status, manager_id, comments) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (Connection connection = ConnectionUtil.getConnection()) {

			try (PreparedStatement pst = connection.prepareStatement(insertQuery)) {
				pst.setInt(1, leaveDetails.getEmployeeId());
				pst.setString(2, leaveDetails.getLeaveType().getName());
				pst.setDate(3, java.sql.Date.valueOf(leaveDetails.getStartDate()));
				pst.setDate(4, java.sql.Date.valueOf(leaveDetails.getEndDate()));
				pst.setDouble(5, leaveDetails.getNoOfDays());
				pst.setString(6, leaveDetails.getLeaveReason());
				pst.setString(7, leaveDetails.getStatus());
				pst.setInt(8, leaveDetails.getManagerId());
				pst.setString(9, leaveDetails.getComments());

				return (pst.executeUpdate() > 0);
			}
		}
	}

	public static boolean updateLeaveRequest(int employeeId, String status) throws SQLException, DAOException {
		String updateQuery = "UPDATE employee_leave_details SET status = ? WHERE employee_id = ?";
		try (Connection connection = ConnectionUtil.getConnection()) {
			try (PreparedStatement pst = connection.prepareStatement(updateQuery)) {
				pst.setString(1, status);
				pst.setInt(2, employeeId);

				return (pst.executeUpdate() > 0);
			}
		}
	}
}
