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
	void testDefaultConstructor() {
		Employee employee = new Employee();
		Assertions.assertNull(employee.getName());
		Assertions.assertNull(employee.getEmail());
		Assertions.assertNull(employee.getPassword());
		Assertions.assertNull(employee.getDateOfJoin());
		Assertions.assertFalse(employee.isStatus());
		Assertions.assertNull(employee.getDateOfRelieve());
		Assertions.assertNull(employee.getManager());
	}

	@Test
	void testParameterizedConstructor() {
		String name = "John Doe";
		String email = "john.doe@example.com";
		String password = "MyPassword123";
		LocalDate dateOfJoin = LocalDate.of(2023, 7, 27);
		boolean status = true;
		LocalDate dateOfRelieve = LocalDate.of(2025, 1, 1);
		Employee manager = new Employee("Manager Name", "manager@example.com", "ManagerPass123",
				LocalDate.of(2022, 1, 1), true, null, null);

		Employee employee = new Employee(name, email, password, dateOfJoin, status, dateOfRelieve, manager);

		Assertions.assertEquals(name, employee.getName());
		Assertions.assertEquals(email, employee.getEmail());
		Assertions.assertEquals(password, employee.getPassword());
		Assertions.assertEquals(dateOfJoin, employee.getDateOfJoin());
		Assertions.assertTrue(employee.isStatus());
		Assertions.assertEquals(dateOfRelieve, employee.getDateOfRelieve());
		Assertions.assertEquals(manager, employee.getManager());
	}

	@Test
	void testGettersAndSetters() {
		Employee employee = new Employee();

		String name = "Jane Doe";
		String email = "jane.doe@example.com";
		String password = "Password123";
		LocalDate dateOfJoin = LocalDate.of(2023, 8, 1);
		boolean status = true;
		LocalDate dateOfRelieve = LocalDate.of(2025, 2, 1);
		Employee manager = new Employee("Manager Name", "manager@example.com", "ManagerPass123",
				LocalDate.of(2022, 1, 1), true, null, null);

		employee.setName(name);
		employee.setEmail(email);
		employee.setPassword(password);
		employee.setDateOfJoin(dateOfJoin);
		employee.setStatus(status);
		employee.setDateOfRelieve(dateOfRelieve);
		employee.setManager(manager);

		Assertions.assertEquals(name, employee.getName());
		Assertions.assertEquals(email, employee.getEmail());
		Assertions.assertEquals(password, employee.getPassword());
		Assertions.assertEquals(dateOfJoin, employee.getDateOfJoin());
		Assertions.assertTrue(employee.isStatus());
		Assertions.assertEquals(dateOfRelieve, employee.getDateOfRelieve());
		Assertions.assertEquals(manager, employee.getManager());
	}
}
