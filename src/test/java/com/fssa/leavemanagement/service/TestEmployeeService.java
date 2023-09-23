package com.fssa.leavemanagement.service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.fssa.leavemanagement.errors.EmployeeErrors;
import com.fssa.leavemanagement.exceptions.DAOException;
import com.fssa.leavemanagement.exceptions.InvalidEmployeeException;
import com.fssa.leavemanagement.model.Employee;
import com.fssa.leavemanagement.model.RoleTypes;

/**
 * 
 * The TestEmployeeService class contains unit tests for the EmployeeService
 * class. It tests the addEmployee, updateEmployee, deleteEmployee,
 * readEmployee, and findEmployeeByName methods of the EmployeeService class. It
 * also includes tests for handling invalid scenarios such as
 * adding/updating/deleting null or invalid Employee objects.
 * 
 * @author PranawMurugesan
 *
 */
class TestEmployeeService {

	static Employee emp = new Employee();

	@Test
	void testAddEmployee() throws InvalidEmployeeException, DAOException, SQLException {
		emp.setEmail("john.doe@fssa.freshworks.com");
		emp.setManager("suman.gopalan@freshworks.com");
		emp.setName("John Doe");
		emp.setPassword("Icodeu100%");
		emp.setStatus(true);
		Assertions.assertTrue(EmployeeService.addEmployee(emp, RoleTypes.TEAMLEAD.getName()));
	}

	@Test
	void testEmployeeAlreadyExists() {
		try {
			Assertions.assertTrue(EmployeeService.addEmployee(emp, RoleTypes.HR.name()));
		} catch (InvalidEmployeeException | DAOException | SQLException e) {
			Assertions.assertEquals(EmployeeErrors.EMPLOYEE_ALREADY_EXISTS, e.getMessage());

			e.printStackTrace();
		}
	}

	@Test
	void testInvalidAddEmployee() throws DAOException, SQLException {

		try {
			Assertions.assertTrue(EmployeeService.addEmployee(null, null));
			Assertions.fail("Invalid Add Employee failed");
		} catch (InvalidEmployeeException e) {
			Assertions.assertEquals(EmployeeErrors.INVALID_EMPLOYEE, e.getMessage());
		}
	}

	@Test
	void testUpdateEmployee() throws InvalidEmployeeException, DAOException, SQLException {

		emp.setEmail("john.doe@fssa.freshworks.com");
		emp.setManager("suman.gopalan@freshworks.com");
		emp.setName("John Doe");
		emp.setPassword("Icodeu100%");
		emp.setStatus(true);
		Assertions.assertTrue(EmployeeService.updateEmployee(emp));
	}

	@Test
	void testInvalidUpdateEmployee() throws SQLException {
		Employee emp = new Employee();
		try {
			Assertions.assertTrue(EmployeeService.updateEmployee(emp));
		} catch (InvalidEmployeeException | DAOException e) {
			Assertions.assertEquals(EmployeeErrors.INVALID_NAME, e.getMessage());
		}
	}

	@Test
	void testFindEmployeeByName() throws InvalidEmployeeException, DAOException, SQLException {
		String find = "John Doe";
		Employee foundEmployee = EmployeeService.findEmployeeByName(find);
		Assertions.assertNotNull(foundEmployee);
		String nonExistentName = "nonexistent";
		Employee nonExistentEmployee = EmployeeService.findEmployeeByName(nonExistentName);
		Assertions.assertNull(nonExistentEmployee);
	}

	@Test
	void testDeleteEmployee() throws InvalidEmployeeException, DAOException, SQLException {

		Assertions.assertTrue(EmployeeService.deleteEmployee(emp.getEmail()));
	}

	@Test
	void testDeleteEmployeeOtherRole() throws InvalidEmployeeException, DAOException, SQLException {
		Employee employee = new Employee();
		employee.setEmail("pranaw6@gmail.com");
		employee.setName("pranaw");
		employee.setPassword("IPranaw@123%");
		employee.setStatus(true);
		Assertions.assertTrue(EmployeeService.deleteEmployee(employee));
	}

	@Test
	void testInvalidDeleteEmployee() {
		Employee employee = null;
		try {
			Assertions.assertTrue(EmployeeService.deleteEmployee(employee));
		} catch (InvalidEmployeeException | DAOException | SQLException e) {
			Assertions.assertEquals(EmployeeErrors.INVALID_EMPLOYEE, e.getMessage());
		}
	}

	@Test
	void testGetAllEmployee() throws DAOException, SQLException {
		List<Employee> employeeList = EmployeeService.getAllEmployee();
		Assertions.assertNotNull(employeeList);
		Assertions.assertFalse(employeeList.isEmpty());
	}

}
