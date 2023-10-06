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
import java.util.ArrayList;
import java.util.List;

import com.fssa.leavemanagement.errors.EmployeeErrors;
import com.fssa.leavemanagement.errors.EmployeeLeaveDetailsErrors;
import com.fssa.leavemanagement.exceptions.DAOException;
import com.fssa.leavemanagement.exceptions.InvalidEmployeeException;
import com.fssa.leavemanagement.model.Employee;
import com.fssa.leavemanagement.util.ConnectionUtil;

public class EmployeeDao {
	private static int roleId;
	private static int employeeId;
	private static String emailConstant = "email";
	private static String passwordConstant = "password";
	private static String isActiveConstant = "is_active";

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

	public static int getCeo() throws SQLException, DAOException {
		int ceoCount = 0;
		String query = "SELECT e.email AS ceo_name FROM employee AS e JOIN employee_role_details AS erd ON e.id = erd.employee_id JOIN role AS r ON erd.role_id = r.id WHERE r.name = 'CEO'";
		try (Connection connection = ConnectionUtil.getConnection()) {
			try (Statement statement = connection.createStatement()) {
				try (ResultSet rs = statement.executeQuery(query)) {
					while (rs.next()) {
						ceoCount++;
					}
				}
			}
		}
		return ceoCount;
	}

	public static boolean checkEmployeeExists(String email) throws SQLException, DAOException {
		String query = "SELECT email FROM employee WHERE email = ?";
		try (Connection connection = ConnectionUtil.getConnection()) {
			try (PreparedStatement pst = connection.prepareStatement(query)) {
				pst.setString(1, email);
				try (ResultSet rs = pst.executeQuery()) {
					return rs.next();
				}
			}
		}
	}

	public static int getEmployeeIdByEmail(String email) throws SQLException, DAOException {
		int id = 0;
		String query = "SELECT id FROM employee WHERE email = ?";
		try (Connection connection = ConnectionUtil.getConnection()) {
			try (PreparedStatement pst = connection.prepareStatement(query)) {
				pst.setString(1, email);
				try (ResultSet rs = pst.executeQuery()) {
					if (rs.next()) {
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
		String queryLeaveBalance = "INSERT INTO employee_leave_balance(employee_id, leave_type, no_of_days) VALUES (?,?,?)";
		try (Connection connection = ConnectionUtil.getConnection()) {

			try (PreparedStatement pst1 = connection.prepareStatement(queryLeaveBalance)) {
				// Insert CL
				pst1.setInt(1, employeeId);
				pst1.setString(2, "CL");
				pst1.setInt(3, 10);
				pst1.addBatch();

				// Execute batch for CL
				pst1.executeBatch();

				// Clear parameters for the next batch
				pst1.clearParameters();

				// Insert SL
				pst1.setInt(1, employeeId);
				pst1.setString(2, "SL");
				pst1.setInt(3, 5);
				pst1.addBatch();

				// Execute batch for SL
				pst1.executeBatch();

				// Clear parameters for the next batch
				pst1.clearParameters();

				// Insert EL
				pst1.setInt(1, employeeId);
				pst1.setString(2, "EL");
				pst1.setInt(3, 5);
				pst1.addBatch();

				// Execute batch for EL
				pst1.executeBatch();
			}
		}
	}

	public static boolean addRoleDetails(Employee employee, String role) throws SQLException, DAOException {
		roleId = RoleDao.getRoleIdByName(role);
		employeeId = EmployeeDao.getEmployeeIdByEmail(employee.getEmail());

		try (Connection connection = ConnectionUtil.getConnection()) {
			int managerId = EmployeeDao.getEmployeeIdByEmail(employee.getManager());
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

	public static boolean updateRoleDetails(Employee employee, String role) throws SQLException, DAOException {
		roleId = RoleDao.getRoleIdByName(role);
		employeeId = EmployeeDao.getEmployeeIdByEmail(employee.getEmail());
		String query = "UPDATE employee_role_details SET employee_id = ?, role_id = ?, reporting_manager_id = ?";
		try (Connection connection = ConnectionUtil.getConnection()) {
			try (PreparedStatement pst = connection.prepareStatement(query)) {
				pst.setInt(1, employeeId);
				pst.setInt(2, roleId);
				pst.setInt(3, getEmployeeIdByEmail(employee.getManager()));
			}
		}
		return true;
	}

	public static String getManagerEmailByEmployeeEmail(String email) throws SQLException, DAOException {

		String query = """
				SELECT e.email AS employee_email, m.email AS manager_email
				FROM employee AS e
				LEFT JOIN employee_role_details AS erd ON e.id = erd.employee_id
				LEFT JOIN employee AS m ON erd.reporting_manager_id = m.id
				WHERE e.email = ?""";
		try (Connection connection = ConnectionUtil.getConnection()) {
			try (PreparedStatement pst = connection.prepareStatement(query)) {
				pst.setString(1, email);
				try (ResultSet rs = pst.executeQuery()) {
					if (rs.next()) {
						return rs.getString("manager_email");
					}
				}
			}
		}
		return null;
	}

	public static boolean addCeoRoleDetails(Employee employee, String role) throws SQLException, DAOException {
		roleId = RoleDao.getRoleIdByName(role);
		employeeId = EmployeeDao.getEmployeeIdByEmail(employee.getEmail());

		try (Connection connection = ConnectionUtil.getConnection()) {
			String queryEmployeeDetail = "INSERT INTO employee_role_details" + " (employee_id,role_id) VALUES (?,?);";
			try (PreparedStatement pst1 = connection.prepareStatement(queryEmployeeDetail)) {
				pst1.setInt(1, employeeId);
				pst1.setInt(2, roleId);
				pst1.executeUpdate();
			}
		}
		return true;
	}

	public static boolean addEmployee(Employee employee) throws InvalidEmployeeException, DAOException {
		int employeeRoleDetailsRows = 0;

		try (Connection connection = ConnectionUtil.getConnection()) {

			String query = "INSERT INTO employee(name,email, password,date_of_joining) VALUES (?,?,?,?);";

			try (PreparedStatement pst = connection.prepareStatement(query)) {
				pst.setString(1, employee.getName().trim());
				pst.setString(2, employee.getEmail().trim());
				pst.setString(3, hashPassword(employee.getPassword()));
				LocalDate date = LocalDate.now();
				pst.setDate(4, java.sql.Date.valueOf(date));

				int rows = pst.executeUpdate();

				return (rows > 0 && employeeRoleDetailsRows > 0);
			}
		} catch (SQLException e) {
			throw new InvalidEmployeeException(EmployeeErrors.CANNOT_ADD_EMPLOYEE + e.getMessage());
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


	public static boolean deleteEmployee(String email) throws SQLException, DAOException {
		String updateQuery = "UPDATE employee SET is_active = ?, date_of_relieving = ? WHERE email = ?";

		try (Connection connection = ConnectionUtil.getConnection()) {
			try (PreparedStatement pstUpdate = connection.prepareStatement(updateQuery)) {

				pstUpdate.setBoolean(1, false);
				pstUpdate.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
				pstUpdate.setString(3, email);

				return (pstUpdate.executeUpdate() > 0);
			}
		} catch (SQLException e) {
			throw new DAOException(EmployeeLeaveDetailsErrors.CANNOT_RELIEVE);
		}
	}

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
	 * @throws InvalidEmployeeException
	 */

	public static boolean updateEmployee(Employee employee) throws DAOException, InvalidEmployeeException {

		try (Connection connection = ConnectionUtil.getConnection()) {

			String query = "UPDATE employee SET name = ?, password = ? WHERE email = ?";
			try (PreparedStatement pst = connection.prepareStatement(query)) {
				pst.setString(1, employee.getName());
				pst.setString(2, hashPassword(employee.getPassword()));
				pst.setString(3, employee.getEmail());
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
						Employee employee = new Employee();
						employee.setEmail(resultSet.getString(emailConstant));
						employee.setName(resultSet.getString("name"));
						employee.setPassword(resultSet.getString(passwordConstant));
						employee.setStatus(resultSet.getBoolean(isActiveConstant));
						employee.setManager(getManagerEmailByEmployeeEmail(resultSet.getString(emailConstant)));
						employee.setDateOfJoining(LocalDate.parse(resultSet.getString("date_of_joining")));
						String relievingDateString = resultSet.getString("date_of_relieving");
						LocalDate relieveDate = (relievingDateString != null) ? LocalDate.parse(relievingDateString)
								: null;
						employee.setDateOfRelieving(relieveDate);
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
			String query = "SELECT * FROM employee WHERE name LIKE ?";
			try (PreparedStatement pst = connection.prepareStatement(query)) {
				pst.setString(1, "%" + name + "%");
				try (ResultSet resultSet = pst.executeQuery()) {
					if (resultSet.next()) {
						LocalDate joinDate = LocalDate.parse(resultSet.getString("date_of_joining"));
						employee.setDateOfJoining(joinDate);

						String relieveDateString = resultSet.getString("date_of_relieving");
						LocalDate relieveDate = (relieveDateString != null) ? LocalDate.parse(relieveDateString) : null;
						employee.setDateOfRelieving(relieveDate);

						employee.setEmail(resultSet.getString(emailConstant));
						employee.setName(resultSet.getString("name"));
						employee.setPassword(resultSet.getString(passwordConstant));
						employee.setStatus(resultSet.getBoolean(isActiveConstant));
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

	public static String findEmployeeNameById(int id) throws DAOException {
		String name = null;
		String query = "SELECT name FROM employee WHERE id = ?";
		try (Connection connection = ConnectionUtil.getConnection()) {
			try (PreparedStatement pst = connection.prepareStatement(query)) {
				pst.setInt(1, id);
				try (ResultSet rs = pst.executeQuery()) {
					if (rs.next()) {
						name = rs.getString("name");
					}
				}
			}
		} catch (SQLException | DAOException e) {
			throw new DAOException(e.getMessage());
		}
		return name;

	}

	public static Employee findEmployeeByEmail(String email) throws DAOException, SQLException {

		Employee employee = new Employee();
		try (Connection connection = ConnectionUtil.getConnection()) {
			String query = "SELECT name,email,password,is_active FROM employee WHERE email = ?";
			try (PreparedStatement pst = connection.prepareStatement(query)) {
				pst.setString(1, email);
				try (ResultSet resultSet = pst.executeQuery()) {
					if (resultSet.next()) {
						employee.setEmail(resultSet.getString(emailConstant));
						employee.setName(resultSet.getString("name"));
						employee.setPassword(resultSet.getString(passwordConstant));
						employee.setStatus(resultSet.getBoolean(isActiveConstant));
						employee.setManager(getManagerEmailByEmployeeEmail(resultSet.getString(emailConstant)));
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
