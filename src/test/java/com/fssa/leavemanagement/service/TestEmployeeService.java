package com.fssa.leavemanagement.service;

import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.fssa.leavemanagement.errors.EmployeeErrors;
import com.fssa.leavemanagement.exceptions.DAOException;
import com.fssa.leavemanagement.exceptions.InvalidEmployeeException;
import com.fssa.leavemanagement.model.Employee;

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
public class TestEmployeeService {

	@Test
	void testAddEmployee() throws InvalidEmployeeException {
		Employee emp = new Employee("pranaw", "pranaw5@gmail.com", "IPranaw@123%", LocalDate.of(2021, 10, 10), true,
				null, null);
		Assertions.assertTrue(EmployeeService.addEmployee(emp, "CEO"));
	}

	@Test
	void testInvalidAddEmployee() {

		try {
			Assertions.assertTrue(EmployeeService.addEmployee(null, null));
			Assertions.fail("Invalid Add Employee failed");
		} catch (InvalidEmployeeException e) {
			Assertions.assertEquals(EmployeeErrors.INVALID_EMPLOYEE, e.getMessage());
		}
	}

	@Test
	void testUpdateEmployee() throws InvalidEmployeeException, DAOException {
		Employee emp = new Employee("pranaw", "pranaw1@gmail.com", "IPranaw@123%", LocalDate.of(2021, 10, 10), true,
				null, null);
		Assertions.assertTrue(EmployeeService.updateEmployee(emp, 2));
	}

	@Test
	void testInvalidUpdateEmployee() {
		Employee emp = new Employee();
		try {
			Assertions.assertTrue(EmployeeService.updateEmployee(emp, 2));
		} catch (InvalidEmployeeException | DAOException e) {
			Assertions.assertEquals(EmployeeErrors.INVALID_NAME, e.getMessage());
		}
	}

	@Test
	void testDeleteEmployee() throws InvalidEmployeeException, DAOException, SQLException {
		Employee employee = new Employee();
		employee.setEmail("pranaw5@gmail.com");
		employee.setName("pranaw");
		employee.setPassword("IPranaw@123%");
		employee.setStatus(true);
		Assertions.assertTrue(EmployeeService.deleteEmployee(employee));
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
	void testReadEmployee() throws InvalidEmployeeException, DAOException, SQLException {
		Assertions.assertTrue(EmployeeService.readEmployee());
	}

	@Test
	void testFindEmployeeByName() throws InvalidEmployeeException, DAOException, SQLException {
		String find = "pranaw";
		Assertions.assertTrue(EmployeeService.findEmployeeByName(find));
	}

	@Test
	void testInvalidFindEmployeeByName() {
		String find = "ab";
		try {
			Assertions.assertTrue(EmployeeService.findEmployeeByName(find));
		} catch (InvalidEmployeeException | DAOException | SQLException e) {
			Assertions.assertEquals(EmployeeErrors.INVALID_NAME, e.getMessage());
		}
	}

}
