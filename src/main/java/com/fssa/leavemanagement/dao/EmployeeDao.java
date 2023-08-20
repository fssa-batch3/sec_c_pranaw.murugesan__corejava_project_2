package com.fssa.leavemanagement.dao;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.fssa.leavemanagement.errors.EmployeeErrors;
import com.fssa.leavemanagement.exceptions.DAOException;
import com.fssa.leavemanagement.exceptions.InvalidEmployeeException;
import com.fssa.leavemanagement.model.Employee;
import com.fssa.leavemanagement.util.ConnectionUtil;
import com.fssa.leavemanagement.validator.EmployeeValidator;

public class EmployeeDao {

	private EmployeeDao() {
		// Private constructor to prevent instantiation from outside the class
	}

	/**
	 * This method will return the id of the employee
	 * 
	 * @param name
	 * @return
	 * @throws SQLException
	 * @throws DAOException
	 */
	public static int getEmployeeIdByName(String name) throws SQLException, DAOException {
		int id = 0;
		String query = "SELECT id FROM employee WHERE name = ?";
		try (Connection connection = ConnectionUtil.getConnection()) {
			try (PreparedStatement pst = connection.prepareStatement(query)) {
				pst.setString(1, name);
				try (ResultSet rs = pst.executeQuery()) {
					while (rs.next()) {
						id = rs.getInt(1);
					}
				}
			}
		}
		return id;
	}

	/**
	 * Hashes the provided password using the SHA-256 cryptographic hash function.
	 *
	 * @param password the raw password to be hashed.
	 * @return the hashed password as a hexadecimal string.
	 * @throws InvalidEmployeeException if an error occurs during hashing (e.g.,
	 *                                  NoSuchAlgorithmException).
	 */
	public static String hashPassword(String password) throws InvalidEmployeeException {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hashedBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));

			// Convert the byte array to a hexadecimal string
			StringBuilder sb = new StringBuilder();
			for (byte b : hashedBytes) {
				sb.append(String.format("%02x", b));
			}

			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new InvalidEmployeeException(e.getMessage());
		}
	}

	/**
	 * This method will validate employee object and check whether the object is
	 * valid or not and then add the employee to DB, if the employee is active it
	 * will store the data in two table or else it will store the date of relieving
	 * in DB.
	 * 
	 * @param employee
	 * @param role
	 * @return
	 * @throws InvalidEmployeeException
	 * @throws DAOException
	 * @throws
	 */
	public static void insertLeaveBalances(int employeeId) throws SQLException, DAOException {
		String queryLeaveBalance = "INSERT INTO employee_leave_balance(employee_id, leave_type, no_of_days) values(?,?,?)";
		try (Connection connection = ConnectionUtil.getConnection()) {

			try (PreparedStatement pst1 = connection.prepareStatement(queryLeaveBalance)) {
				// Insert CL
				pst1.setInt(1, employeeId);
				pst1.setString(2, "CL");
				pst1.setInt(3, 10);
				pst1.addBatch();

				// Insert SL
				pst1.setInt(1, employeeId);
				pst1.setString(2, "SL");
				pst1.setInt(3, 5);
				pst1.addBatch();

				// Insert EL
				pst1.setInt(1, employeeId);
				pst1.setString(2, "EL");
				pst1.setInt(3, 5);
				pst1.addBatch();

				pst1.executeBatch();
			}
		}

	}

	public static boolean addRoleDetails(Employee employee, String role) throws SQLException, DAOException {
		int roleId = RoleDao.getRoleIdByName(role);
		int employeeId = EmployeeDao.getEmployeeIdByName(employee.getName());

		try (Connection connection = ConnectionUtil.getConnection()) {
			int managerId = EmployeeDao.getEmployeeIdByName(employee.getManager().getName());
			String queryEmployeeDetail = "INSERT INTO employee_role_details "
					+ "(employee_id,role_id,reporting_manager_id) VALUES (?,?,?);";
			try (PreparedStatement pst1 = connection.prepareStatement(queryEmployeeDetail)) {
				pst1.setInt(1, employeeId);
				pst1.setInt(2, roleId);
				pst1.setInt(3, managerId);

				pst1.executeUpdate();
			}
		}
		return true;
	}

	public static boolean addCeoRoleDetails(Employee employee, String role) throws SQLException, DAOException {
		int roleId = RoleDao.getRoleIdByName(role);
		int employeeId = EmployeeDao.getEmployeeIdByName(employee.getName());
		try (Connection connection = ConnectionUtil.getConnection()) {
			String queryEmployeeDetail = "INSERT INTO employee_role_details" + " (employee_id,role_id) VALUES (?,?);";
			try (PreparedStatement pst1 = connection.prepareStatement(queryEmployeeDetail)) {
				pst1.setInt(1, employeeId);
				pst1.setInt(2, roleId);
				pst1.executeUpdate();
			}
		} catch (SQLException | DAOException e) {
			e.printStackTrace();
		}
		return true;
	}

	public static boolean addEmployee(Employee employee, String role) throws InvalidEmployeeException, DAOException {
		int employeeRoleDetailsRows = 0;

		if (employee.isStatus()) {

			try (Connection connection = ConnectionUtil.getConnection()) {

				String query = "INSERT INTO employee(name,email, is_active,password) VALUES (?,?,?,?);";

				try (PreparedStatement pst = connection.prepareStatement(query)) {
					pst.setString(1, employee.getName());
					pst.setString(2, employee.getEmail());
					pst.setBoolean(3, employee.isStatus());
					pst.setString(4, hashPassword(employee.getPassword()));

					int rows = pst.executeUpdate();

					return (rows > 0 && employeeRoleDetailsRows > 0);
				}
			} catch (SQLException e) {
				throw new InvalidEmployeeException(EmployeeErrors.CANNOT_ADD_EMPLOYEE);
			}
		} else {
			throw new DAOException("Cannot add employee who is inactive");
		}

	}

	/**
	 * This method will get the role by employee name
	 * 
	 * @param name
	 * @return
	 * @throws SQLException
	 * @throws DAOException
	 */
	public static String getRoleByEmployeeName(String name) throws SQLException, DAOException {
		String role = null;
		String query = "SELECT e.name AS employee_name, r.name AS role_name FROM "
				+ "employee AS e LEFT JOIN employee_role_details AS erd ON e.id = "
				+ "erd.employee_id LEFT JOIN role AS r ON erd.role_id = r.id WHERE e.name = ?;";
		try (Connection connection = ConnectionUtil.getConnection()) {
			try (PreparedStatement pst = connection.prepareStatement(query)) {
				pst.setString(1, name);
				try (ResultSet rs = pst.executeQuery()) {
					while (rs.next()) {
						role = rs.getString("role_name");
					}
				}
			}
		}
		return role;
	}

	/**
	 * The updateEmployee will get employee object and an id, this method will
	 * update the employee data in DB (Two tables)
	 * 
	 * @param employee
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	public static boolean updateEmployee(Employee employee, int id) throws DAOException {
		try {
			EmployeeValidator.validateEmployee(employee);
		} catch (InvalidEmployeeException e) {
			throw new DAOException("Invalid employee passed to DAO Layer", e);
		}

		try (Connection connection = ConnectionUtil.getConnection()) {
			if (employee.isStatus()) {
				int roleId = RoleDao.getRoleIdByName(getRoleByEmployeeName(employee.getName()));
				String updateEmployeeRoleDetails = "UPDATE employee_role_details "
						+ "SET role_id = ? WHERE employee_id = ?";
				try (PreparedStatement pst1 = connection.prepareStatement(updateEmployeeRoleDetails)) {
					pst1.setInt(1, roleId);
					pst1.setInt(2, id);
					pst1.executeUpdate();
				}

			}
			String query = "UPDATE employee SET name = ?, email = ?, is_active = ?," + " password = ? WHERE id = ?";
			try (PreparedStatement pst = connection.prepareStatement(query)) {
				pst.setString(1, employee.getName());
				pst.setString(2, employee.getEmail());
				pst.setBoolean(3, employee.isStatus());
				pst.setString(4, employee.getPassword());
				pst.setInt(5, id);

				int rows = pst.executeUpdate();

				return (rows > 0);
			}
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	/**
	 * The delete Employee Method will get employee object and delete the employee
	 * data in DB using the employee id.
	 * 
	 * @param employee
	 * @return
	 * @throws DAOException
	 * @throws SQLException
	 */
	public static void deleteLeaveBalances(int employeeId) throws SQLException, DAOException {
		String deleteQuery = "DELETE FROM employee_leave_balance WHERE employee_id = ?";
		try (Connection connection = ConnectionUtil.getConnection();
				PreparedStatement pst = connection.prepareStatement(deleteQuery)) {
			pst.setInt(1, employeeId);
			pst.executeUpdate();
		}
	}

	public static void deleteEmployeeRoleDetails(int employeeId) throws SQLException, DAOException {
		String deleteQuery = "DELETE FROM employee_role_details WHERE employee_id = ?";
		try (Connection connection = ConnectionUtil.getConnection();
				PreparedStatement pst = connection.prepareStatement(deleteQuery)) {
			pst.setInt(1, employeeId);
			pst.executeUpdate();
		}
	}

	public static boolean deleteEmployeeRecord(int employeeId) throws SQLException, DAOException {
		String deleteQuery = "DELETE FROM employee WHERE id = ?";
		try (Connection connection = ConnectionUtil.getConnection();
				PreparedStatement pst = connection.prepareStatement(deleteQuery)) {
			pst.setInt(1, employeeId);
			int rowsDeleted = pst.executeUpdate();
			return rowsDeleted > 0;
		}
	}

	/**
	 * The getAllEmployee will log all the data in DB in console and return the
	 * value of boolean
	 * 
	 * @return
	 * @throws DAOException
	 * @throws SQLException
	 */
	public static List<Employee> getAllEmployee() throws DAOException, SQLException {
		Employee employee = new Employee();
		List<Employee> employeeList = new ArrayList<>();
		try (Connection connection = ConnectionUtil.getConnection()) {
			String query = "SELECT * FROM employee";
			try (Statement statement = connection.createStatement()) {
				try (ResultSet resultSet = statement.executeQuery(query)) {
					/**
					 * this will run the query and return the value
					 */
					while (resultSet.next()) {
//						adding employee to array list
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy,MM,dd");
						String date = resultSet.getString("date_of_joining");
						LocalDate joinDate = LocalDate.parse(date, formatter);
						employee.setDateOfJoin(joinDate);
						date = resultSet.getString("date_of_relieving");
						LocalDate relieveDate = LocalDate.parse(date, formatter);
						employee.setDateOfRelieve(relieveDate);
						employee.setEmail(resultSet.getString("email"));
						employee.setName(resultSet.getString("name"));
						employee.setPassword(resultSet.getString("password"));
						employee.setStatus(resultSet.getBoolean("is_active"));
						employeeList.add(employee);

					}
					return employeeList;
				}
			} catch (SQLException e) {
				throw new DAOException(e);
			}

		}

	}

	/**
	 * The findEmployeeByName will get parameter as name and log all the data in DB
	 * where the employee name matches the name
	 * 
	 * @param name
	 * @return
	 * @throws DAOException
	 * @throws SQLException
	 */
	public static Employee findEmployeeByName(String name) throws DAOException, SQLException {

		Employee employee = new Employee();
		try (Connection connection = ConnectionUtil.getConnection()) {
			String query = "SELECT * FROM employee WHERE name = ?";
			try (PreparedStatement pst = connection.prepareStatement(query)) {
				pst.setString(1, name);
				try (ResultSet resultSet = pst.executeQuery()) {
					if (resultSet.next()) {
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
						String date = resultSet.getString("date_of_joining");
						LocalDate joinDate = LocalDate.parse(date, formatter);
						employee.setDateOfJoin(joinDate);

						date = resultSet.getString("date_of_relieving");
						if (date != null) {
							LocalDate relieveDate = LocalDate.parse(date, formatter);
							employee.setDateOfRelieve(relieveDate);
						}

						employee.setEmail(resultSet.getString("email"));
						employee.setName(resultSet.getString("name"));
						employee.setPassword(resultSet.getString("password"));
						employee.setStatus(resultSet.getBoolean("is_active"));
					} else {
						employee = null;
					}
				}
			} catch (SQLException e) {
				throw new DAOException(e.getMessage());
			}
		}
		return employee;
	}

}
