package com.fssa.leavemanagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fssa.leavemanagement.exceptions.DAOException;
import com.fssa.leavemanagement.model.EmployeeLeaveBalance;
import com.fssa.leavemanagement.model.EmployeeLeaveDetails;
import com.fssa.leavemanagement.model.LeaveTypes;
import com.fssa.leavemanagement.util.ConnectionUtil;

public class EmployeeLeaveDetailsDao {
	private EmployeeLeaveDetailsDao() {
//	private constructor
	}

	public static boolean reduceLeaveBalance(EmployeeLeaveBalance e, int employeeId, LeaveTypes leaveType,
			int daysToReduce) throws SQLException, DAOException {
		String query = "UPDATE employee_leave_balance SET no_of_days= ? WHERE employee_id = ? AND leave_type=?";

		try (Connection connection = ConnectionUtil.getConnection()) {
			try (PreparedStatement pst = connection.prepareStatement(query)) {

				if (leaveType.getName().equals("SL")) {

					pst.setDouble(1, e.getSickLeaveBalance() - daysToReduce);
				} else if (leaveType.getName().equals("CL")) {
					pst.setDouble(1, e.getCasualLeaveBalance() - daysToReduce);
				} else {
					pst.setDouble(1, e.getEarnedLeaveBalance() - daysToReduce);
				}
				pst.setInt(2, employeeId);
				pst.setString(3, leaveType.getName());
				return (pst.executeUpdate() > 0);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return true;
	}

	public static List<EmployeeLeaveDetails> getLeaveRequestsByManagerEmail(String email)
			throws SQLException, DAOException {

		List<EmployeeLeaveDetails> arr = new ArrayList<>();
		String query = "SELECT leave_type,start_date,end_date,no_of_days,leave_reason,status,employee_id,id FROM employee_leave_details WHERE manager_id = ?";

		try (Connection connection = ConnectionUtil.getConnection()) {
			try (PreparedStatement pst = connection.prepareStatement(query)) {
				pst.setInt(1, EmployeeDao.getEmployeeIdByEmail(email));
				try (ResultSet rs = pst.executeQuery()) {
					while (rs.next()) {
						EmployeeLeaveDetails elb = new EmployeeLeaveDetails();
						LocalDate endDate = LocalDate.parse(rs.getString("end_date"));
						LocalDate startDate = LocalDate.parse(rs.getString("start_date"));
						if ("CL".equals(rs.getString("leave_type"))) {
							elb.setLeaveType(LeaveTypes.CASUAL);
						} else if ("SL".equals(rs.getString("leave_type"))) {
							elb.setLeaveType(LeaveTypes.CASUAL);
						} else {
							elb.setLeaveType(LeaveTypes.EARNED);
						}
						elb.setEndDate(endDate);
						elb.setLeaveReason(rs.getString("leave_reason"));
						elb.setStartDate(startDate);
						elb.setNoOfDays(rs.getInt("no_of_days"));
						elb.setStatus(rs.getString("status"));
						elb.setId(rs.getInt("id"));
						elb.setEmployeeId(rs.getInt("employee_id"));
						elb.setName(EmployeeDao.findEmployeeNameById(rs.getInt("employee_id")));
						arr.add(elb);
					}
				}
			}
		}

		return arr;
	}

	public static EmployeeLeaveBalance getLeaveBalanceByEmployeeId(int id) throws SQLException, DAOException {
		EmployeeLeaveBalance elb = new EmployeeLeaveBalance();
		String query = "SELECT leave_type, no_of_days FROM employee_leave_balance WHERE employee_id = ?";
		try (Connection connection = ConnectionUtil.getConnection()) {
			try (PreparedStatement pst = connection.prepareStatement(query)) {
				pst.setInt(1, id);
				try (ResultSet rs = pst.executeQuery()) {
					while (rs.next()) {
						String leaveType = rs.getString("leave_type");
						if ("CL".equals(leaveType)) {
							elb.setCasualLeaveBalance(rs.getDouble("no_of_days"));
						} else if ("SL".equals(leaveType)) {
							elb.setSickLeaveBalance(rs.getDouble("no_of_days"));
						} else {
							elb.setEarnedLeaveBalance(rs.getDouble("no_of_days"));
						}
					}
				}
			}
		}
		return elb;
	}

	public static EmployeeLeaveBalance getLeaveBalanceByEmail(String email) throws SQLException, DAOException {
		EmployeeLeaveBalance elb = new EmployeeLeaveBalance();
		String query = "SELECT leave_type, no_of_days FROM employee_leave_balance WHERE employee_id = ?";
		try (Connection connection = ConnectionUtil.getConnection()) {
			try (PreparedStatement pst = connection.prepareStatement(query)) {
				pst.setInt(1, EmployeeDao.getEmployeeIdByEmail(email));
				try (ResultSet rs = pst.executeQuery()) {
					while (rs.next()) {
						String leaveType = rs.getString("leave_type");
						if ("CL".equals(leaveType)) {
							elb.setCasualLeaveBalance(rs.getDouble("no_of_days"));
						} else if ("SL".equals(leaveType)) {
							elb.setSickLeaveBalance(rs.getDouble("no_of_days"));
						} else {
							elb.setEarnedLeaveBalance(rs.getDouble("no_of_days"));
						}
					}
				}
			}
		}
		elb.setEmail(email);
		return elb;
	}

	public static List<EmployeeLeaveDetails> getLeaveRequestsByEmail(String email) throws SQLException, DAOException {
		List<EmployeeLeaveDetails> eldArray = new ArrayList<>();
		String leaveType = null;
		String query = "SELECT leave_type,start_date,end_date,status,leave_reason,employee_id,no_of_days,id FROM employee_leave_details WHERE employee_id = ?";
		try (Connection connection = ConnectionUtil.getConnection()) {
			try (PreparedStatement pst = connection.prepareStatement(query)) {
				pst.setInt(1, EmployeeDao.getEmployeeIdByEmail(email));
				try (ResultSet rs = pst.executeQuery()) {
					while (rs.next()) {
						EmployeeLeaveDetails eld = new EmployeeLeaveDetails();
						eld.setId(rs.getInt("id"));
						eld.setEmployeeId(rs.getInt("employee_id"));
						eld.setName(EmployeeDao.findEmployeeNameById(rs.getInt("employee_id")));
						leaveType = (rs.getString("leave_type"));
						if ("CL".equals(leaveType)) {
							eld.setLeaveType(LeaveTypes.CASUAL);
						} else if ("SL".equals(leaveType)) {
							eld.setLeaveType(LeaveTypes.SICK);
						} else {
							eld.setLeaveType(LeaveTypes.EARNED);
						}
						String sDate = rs.getString("start_date");
						eld.setStartDate(LocalDate.parse(sDate));
						String lDate = rs.getString("end_date");
						eld.setEndDate(LocalDate.parse(lDate));
						eld.setStatus(rs.getString("status"));
						eld.setLeaveReason(rs.getString("leave_reason"));
						eld.setNoOfDays(rs.getInt("no_of_days"));
						eldArray.add(eld);
					}
				}
			}
		}
		return eldArray;
	}

	public static List<EmployeeLeaveBalance> getAllLeaveBalances() {
		List<EmployeeLeaveBalance> elb = new ArrayList<>();
		String query = "SELECT "
				+ "e.name AS employee_name, e.email AS employee_email, COALESCE(slb_sick.no_of_days, 0) AS sick_leave_balance,COALESCE(slb_casual.no_of_days, 0) AS casual_leave_balance,COALESCE(slb_earned.no_of_days, 0) AS earned_leave_balance "
				+ "FROM employee AS e LEFT JOIN employee_leave_balance AS slb_sick ON e.id = slb_sick.employee_id AND slb_sick.leave_type = 'SL' LEFT JOIN employee_leave_balance AS slb_casual "
				+ "ON e.id = slb_casual.employee_id AND slb_casual.leave_type = 'CL' LEFT JOIN employee_leave_balance AS slb_earned ON e.id = slb_earned.employee_id AND slb_earned.leave_type = 'EL'";
		try (Connection connection = ConnectionUtil.getConnection()) {
			try (Statement statement = connection.createStatement()) {
				try (ResultSet rs = statement.executeQuery(query)) {
					while (rs.next()) {
						EmployeeLeaveBalance balance = new EmployeeLeaveBalance();
						balance.setName(rs.getString("employee_name"));
						balance.setEmail(rs.getString("employee_email"));
						balance.setSickLeaveBalance(rs.getDouble("sick_leave_balance"));
						balance.setCasualLeaveBalance(rs.getDouble("casual_leave_balance"));
						balance.setEarnedLeaveBalance(rs.getDouble("earned_leave_balance"));
						elb.add(balance);
					}
				}
			}
		} catch (SQLException | DAOException e) {

			e.printStackTrace();
		}
		return elb;

	}

	public static boolean applyLeaveRequest(EmployeeLeaveDetails leaveDetails) throws SQLException, DAOException {
		String insertQuery = "INSERT INTO employee_leave_details (employee_id, "
				+ "leave_type, start_date, end_date, no_of_days, leave_reason, "
				+ " manager_id, comments) VALUES (?, ?,  ?, ?, ?, ?, ?, ?)";
		try (Connection connection = ConnectionUtil.getConnection()) {

			try (PreparedStatement pst = connection.prepareStatement(insertQuery)) {
				pst.setInt(1, leaveDetails.getEmployeeId());
				pst.setString(2, leaveDetails.getLeaveType().getName());
				pst.setDate(3, java.sql.Date.valueOf(leaveDetails.getStartDate()));
				pst.setDate(4, java.sql.Date.valueOf(leaveDetails.getEndDate()));
				pst.setDouble(5, leaveDetails.getNoOfDays());
				pst.setString(6, leaveDetails.getLeaveReason());
//				pst.setString(7, leaveDetails.getStatus());
				pst.setInt(7, leaveDetails.getManagerId());
				pst.setString(8, leaveDetails.getComments());

				return (pst.executeUpdate() > 0);
			}
		}
	}

	public static boolean updateLeaveRequest(String status, int id, String comments) throws SQLException, DAOException {
		String updateQuery = "UPDATE employee_leave_details SET status = ?, comments = ? WHERE id = ?";
		try (Connection connection = ConnectionUtil.getConnection()) {
			try (PreparedStatement pst = connection.prepareStatement(updateQuery)) {
				pst.setString(1, status);
				pst.setString(2, comments);
				pst.setInt(3, id);

				return (pst.executeUpdate() > 0);
			}
		}
	}
}
