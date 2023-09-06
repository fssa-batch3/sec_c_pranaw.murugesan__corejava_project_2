package com.fssa.leavemanagement.validator;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.fssa.leavemanagement.errors.EmployeeErrors;
import com.fssa.leavemanagement.exceptions.InvalidEmployeeException;
import com.fssa.leavemanagement.model.Employee;

/**
 * The TestEmployeeValidator class contains unit tests for the EmployeeValidator
 * class. It tests the validateEmployee, validateName, validateEmail, and
 * validatePassword methods of the EmployeeValidator class. It includes tests
 * for handling valid and invalid Employee objects, names, emails, and
 * passwords.
 * 
 * @author PranawMurugesan
 *
 */
class TestEmployeeValidator {

	static LocalDate join = LocalDate.now();
	static Employee employee = new Employee("pranaw", "pranaw@gmail.com", "IFresh100%", join, true, null, null);

	@Test
	void testValidateEmployee() {

		try {
			Assertions.assertTrue(EmployeeValidator.validateEmployee(employee));
		} catch (InvalidEmployeeException e) {
			Assertions.assertEquals(EmployeeErrors.INVALID_EMPLOYEE, e.getMessage());
			e.printStackTrace();
		}
	}

	@Test
	void testValidateNullEmployee() {
		try {
			Assertions.assertTrue(EmployeeValidator.validateEmployee(null));
			Assertions.fail("Null Employee failed");
		} catch (InvalidEmployeeException e) {
			Assertions.assertEquals(EmployeeErrors.INVALID_EMPLOYEE, e.getMessage());
		}
	}

	@Test
	void testValidateName() throws InvalidEmployeeException {
		Assertions.assertTrue(EmployeeValidator.validateName(employee.getName()));
	}

	@Test
	void testInvalidName() {
		try {
			Assertions.assertTrue(EmployeeValidator.validateName(null));
		} catch (InvalidEmployeeException e) {
			Assertions.assertEquals(EmployeeErrors.INVALID_NAME, e.getMessage());
		}
	}

	@Test
	void testValidateEmail() throws InvalidEmployeeException {
		Assertions.assertTrue(EmployeeValidator.validateEmail(employee.getEmail()));
	}

	@Test
	void testInvalidEmail() {
		try {
			Assertions.assertTrue(EmployeeValidator.validateEmail("pranaw.com"));
		} catch (InvalidEmployeeException e) {
			Assertions.assertEquals(EmployeeErrors.INVALID_EMAIL, e.getMessage());
		}
	}

	@Test
	void testValidatePassword() throws InvalidEmployeeException {
		Assertions.assertTrue(EmployeeValidator.validatePassword(employee.getPassword()));
	}

	@Test
	void testInvalidPassword() {
		try {
			Assertions.assertTrue(EmployeeValidator.validatePassword("123"));
		} catch (InvalidEmployeeException e) {
			Assertions.assertEquals(EmployeeErrors.INVALID_PASSWORD, e.getMessage());
		}
	}

}