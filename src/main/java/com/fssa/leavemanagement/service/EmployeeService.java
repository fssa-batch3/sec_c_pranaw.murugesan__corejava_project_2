package com.fssa.leavemanagement.service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fssa.leavemanagement.dao.EmployeeDao;
import com.fssa.leavemanagement.errors.EmployeeErrors;
import com.fssa.leavemanagement.exceptions.DAOException;
import com.fssa.leavemanagement.exceptions.InvalidEmployeeException;
import com.fssa.leavemanagement.model.Employee;
import com.fssa.leavemanagement.model.RoleTypes;
import com.fssa.leavemanagement.validator.EmployeeValidator;

public class EmployeeService {

	private EmployeeService() {
//		private constructor
	}

	/**
	 * Add an employee to the database.
	 * 
	 * @param employee The Employee object containing the details of the employee to
	 *                 be added.
	 * @param role     The role of the employee to be added.
	 * @return true if the employee is added successfully, false otherwise.
	 * @throws InvalidEmployeeException if the employee data is invalid.
	 * @throws DAOException
	 * @throws SQLException
	 */
	public static boolean addEmployee(Employee employee, String role)
			throws InvalidEmployeeException, DAOException, SQLException {

		if (EmployeeValidator.validateEmployee(employee) && (!EmployeeDao.checkEmployeeExists(employee.getEmail()))) {

			EmployeeDao.addEmployee(employee, role);

		} else {
			throw new InvalidEmployeeException(EmployeeErrors.EMPLOYEE_ALREADY_EXISTS);
		}
		if (role.equals(RoleTypes.CEO.getName())) {
			EmployeeDao.addCeoRoleDetails(employee, role);
		}
		int employeeId = EmployeeDao.getEmployeeIdByEmail(employee.getEmail());

		EmployeeDao.insertLeaveBalances(employeeId);
		EmployeeDao.addRoleDetails(employee, role);

		return true;
	}

	/**
	 * Get all employees from the database and print their details.
	 * 
	 * @return true if all employees are retrieved and printed successfully, false
	 *         otherwise.
	 * @throws InvalidEmployeeException if an invalid employee is encountered during
	 *                                  the process.
	 * @throws DAOException             if an SQL exception occurs while retrieving
	 *                                  employees.
	 * @throws SQLException             if a general SQL exception occurs.
	 */
	public static List<Employee> getAllEmployee() throws DAOException, SQLException {
		List<Employee> employeeList = new ArrayList<>();
		employeeList = EmployeeDao.getAllEmployee();
		return employeeList;
	}

	/**
	 * Update an existing employee in the database.
	 * 
	 * @param employee The Employee object containing the updated details of the
	 *                 employee.
	 * @param id       The ID of the employee to be updated.
	 * @return true if the employee is updated successfully, false otherwise.
	 * @throws InvalidEmployeeException if the employee data is invalid.
	 * @throws DAOException             if an SQL exception occurs while updating
	 *                                  the employee.
	 */
	public static boolean updateEmployee(Employee employee) throws InvalidEmployeeException, DAOException {
		if (EmployeeValidator.validateEmployee(employee)) {
			EmployeeDao.updateEmployee(employee);
		}
		return true;
	}

	public static boolean deleteEmployee(String email) throws SQLException, DAOException {
		return EmployeeDao.deleteEmployee(email);

	}

	/**
	 * Delete an existing employee from the database.
	 * 
	 * @param employee The Employee object containing the details of the employee to
	 *                 be deleted.
	 * @return true if the employee is deleted successfully, false otherwise.
	 * @throws InvalidEmployeeException if the employee data is invalid.
	 * @throws DAOException             if an SQL exception occurs while deleting
	 *                                  the employee.
	 * @throws SQLException             if a general SQL exception occurs.
	 */
	public static boolean deleteEmployee(Employee employee)
			throws InvalidEmployeeException, DAOException, SQLException {
		if (EmployeeValidator.validateEmployee(employee)) {

			/**
			 * The below methods calls are commented out because of changed database table
			 * constraint (ON DELETE CASCADE). So that it can delete foreign tables if we
			 * delete the element in parent table
			 * 
			 * EmployeeDao.deleteLeaveBalances(employeeId);
			 * EmployeeDao.deleteEmployeeRoleDetails(employeeId);
			 */
			EmployeeDao.deleteEmployee(employee.getEmail());
		}
		return true;
	}

	/**
	 * Find an employee by name and print their details.
	 * 
	 * @param name The name of the employee to be searched for.
	 * @return true if the employee is found and their details are printed
	 *         successfully, false otherwise.
	 * @throws InvalidEmployeeException if the employee name is invalid.
	 * @throws DAOException             if an SQL exception occurs while finding the
	 *                                  employee.
	 * @throws SQLException             if a general SQL exception occurs.
	 */
	public static Employee findEmployeeByName(String name) throws InvalidEmployeeException, DAOException, SQLException {
		Employee employee = null;
		if (EmployeeValidator.validateName(name)) {
			employee = EmployeeDao.findEmployeeByName(name);
		}
		return employee;
	}

	public static boolean checkEmployeeExists(String email)
			throws SQLException, DAOException, InvalidEmployeeException {
		if (EmployeeValidator.validateEmail(email)) {

			return EmployeeDao.checkEmployeeExists(email);
		}
		return false;
	}

	public static Employee findEmployeeByEmail(String email)
			throws InvalidEmployeeException, DAOException, SQLException {
		Employee employee = null;
		if (EmployeeValidator.validateEmail(email)) {
			employee = EmployeeDao.findEmployeeByEmail(email);
		}
		return employee;
	}

}
