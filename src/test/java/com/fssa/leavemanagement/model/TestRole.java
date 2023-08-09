package com.fssa.leavemanagement.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 
 * The TestRole class contains unit tests for the Role class. It tests the
 * default constructor, parameterized constructor, and getters/setters of the
 * Role class.
 * 
 */
public class TestRole {

	@Test
	void testDefaultConstructor() {
		Role role = new Role();
		Assertions.assertEquals(0, role.getId());
		Assertions.assertNull(role.getName());
	}

	@Test
	void testParameterizedConstructor() {
		int id = 1;
		String name = "Manager";
		Role role = new Role(id, name);

		Assertions.assertEquals(id, role.getId());
		Assertions.assertEquals(name, role.getName());
	}

	@Test
	void testGettersAndSetters() {
		Role role = new Role();

		int id = 2;
		String name = "Employee";

		role.setId(id);
		role.setName(name);

		Assertions.assertEquals(id, role.getId());
		Assertions.assertEquals(name, role.getName());
	}
}
