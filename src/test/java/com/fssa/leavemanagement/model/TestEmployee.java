package com.fssa.leavemanagement.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

/**
 * The TestEmployee class contains unit tests for the Employee class. It tests
 * the default constructor, parameterized constructor, and getters/setters of
 * the Employee class.
 */
class TestEmployee {

	@Test
	void testGettersAndSetters() {
		Employee employee = new Employee();

		String name = "Jane Doe";
		String email = "jane.doe@example.com";
		String password = "Password123";
		LocalDate dateOfJoin = LocalDate.now();
		boolean status = true;
		LocalDate dateOfRelieve = LocalDate.now();
		String manager = "manager@example.com";

		employee.setName(name);
		employee.setEmail(email);
		employee.setPassword(password);
		employee.setDateOfJoining(dateOfJoin);
		employee.setStatus(status);
		employee.setDateOfRelieving(dateOfRelieve);
		employee.setManager(manager);

		Assertions.assertEquals(name, employee.getName());
		Assertions.assertEquals(email, employee.getEmail());
		Assertions.assertEquals(password, employee.getPassword());
		Assertions.assertEquals(dateOfJoin, employee.getDateOfJoining());
		Assertions.assertTrue(employee.isStatus());
		Assertions.assertEquals(dateOfRelieve, employee.getDateOfRelieving());
		Assertions.assertEquals(manager, employee.getManager());
	}
}
