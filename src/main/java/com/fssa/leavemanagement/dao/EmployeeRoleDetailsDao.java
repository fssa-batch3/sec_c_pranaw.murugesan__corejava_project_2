package com.fssa.leavemanagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.fssa.leavemanagement.exceptions.DAOException;
import com.fssa.leavemanagement.model.Employee;
import com.fssa.leavemanagement.model.EmployeeRoleDetails;
import com.fssa.leavemanagement.util.ConnectionUtil;
import com.fssa.leavemanagement.util.Logger;

/**
 * The EmployeeRoleDetailsDao class provides methods to interact with the
 * database for managing employee role details. This class allows adding,
 * updating, and retrieving employee role details from the database. The
 * database table used for storing employee role details is
 * "employee_role_details."
 * 
 * @author PranawMurugesan
 *
 */
public class EmployeeRoleDetailsDao {
	private static int roleId;
	private EmployeeRoleDetailsDao() {
//		private constructor
	}

	/**
	 * Adds an EmployeeRoleDetails object to the database.
	 * 
	 * @param erd The EmployeeRoleDetails object to be added to the database.
	 * @return true if the addition is successful, false otherwise.
	 * @throws SQLException If a database access error occurs.
	 * @throws DAOException
	 */
	public static boolean addEmployeeRoleDetails(EmployeeRoleDetails erd) throws SQLException, DAOException {
		String query = "INSERT INTO employee_role_details (employee_id,role_id,"
				+ "reporting_manager_id) VALUES (?,?,?);";
		try (Connection connection = ConnectionUtil.getConnection()) {
			try (PreparedStatement pst = connection.prepareStatement(query)) {
				pst.setInt(1, erd.getEmployeeId());
				pst.setInt(2, erd.getRoleId());
				pst.setInt(3, erd.getReportingManagerId());

				int rows = pst.executeUpdate();
				return (rows > 0);
			}
		}

	}

	/**
	 * Updates an existing EmployeeRoleDetails object in the database.
	 * 
	 * @param erd The EmployeeRoleDetails object with updated values.
	 * @return true if the update is successful, false otherwise.
	 * @throws SQLException If a database access error occurs.
	 * @throws DAOException
	 */
	public static boolean updateEmployeeRoleDetails(Employee employee) throws SQLException, DAOException {
		String query = "UPDATE employee_role_details "
				+ "SET role_id = ?, reporting_manager_id = ? WHERE employee_id = ?";
		try (Connection connection = ConnectionUtil.getConnection()) {
			try (PreparedStatement pst = connection.prepareStatement(query)) {
				roleId = RoleDao.getRoleIdByName(EmployeeDao.getRoleByEmployeeName(employee.getName()));
				pst.setInt(1, roleId);
				pst.setInt(2, EmployeeDao.getEmployeeIdByEmail(employee.getManager()));
				pst.setInt(3, EmployeeDao.getEmployeeIdByEmail(employee.getEmail()));
				pst.executeUpdate();
				int rows = pst.executeUpdate();
				return (rows > 0);

			}
		}
	}

	/**
	 * Retrieves all employee role details from the database and prints them to the
	 * console.
	 * 
	 * @return true if the retrieval is successful, false otherwise.
	 * @throws SQLException If a database access error occurs.
	 * @throws DAOException
	 */
	public static boolean getAllEmployeeRoleDetails() throws SQLException, DAOException {
		String query = "SELECT * FROM employee_role_details";
		try (Connection connection = ConnectionUtil.getConnection()) {
			try (Statement st = connection.createStatement()) {
				try (ResultSet rs = st.executeQuery(query)) {
					while (rs.next()) {

						Logger.info("id : " + rs.getInt("id"));
						Logger.info("employee_id : " + rs.getInt("employee_id"));
						Logger.info("role_id : " + rs.getInt("role_id"));
						Logger.info("reporting_manager_id : " + rs.getInt("reporting_manager_id"));
					}
					return true;
				}

			}
		}
	}
}