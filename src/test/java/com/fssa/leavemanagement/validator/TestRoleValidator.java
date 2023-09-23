package com.fssa.leavemanagement.validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.fssa.leavemanagement.errors.RoleErrors;
import com.fssa.leavemanagement.exceptions.InvalidRoleException;
import com.fssa.leavemanagement.model.Role;

/**
 * The TestRoleValidator class contains unit tests for the RoleValidator class.
 * It tests the validate, validateId, and validateName methods of the
 * RoleValidator class. It includes tests for handling valid and invalid Role
 * objects, ids, and names.
 * 
 * @author PranawMurugesan
 *
 */
class TestRoleValidator {
	@Test
	void testValidate() {
		Role role = new Role("Manager");
		try {
			Assertions.assertTrue(RoleValidator.validate(role));
		} catch (InvalidRoleException e) {
			Assertions.assertEquals(RoleErrors.INVALID_ROLE, e.getMessage());
		}
	}

	@Test
	void testInvalid() {
		try {
			Assertions.assertTrue(RoleValidator.validate(null));
			// Should throw an exception for a null role
			Assertions.fail("Expected InvalidRoleException for null role");

		} catch (InvalidRoleException e) {
			Assertions.assertEquals(RoleErrors.INVALID_ROLE, e.getMessage());
		}
	}

	@Test
	void testValidName() {
		try {
			Assertions.assertTrue(RoleValidator.validateName("HR"));
		} catch (InvalidRoleException e) {
			Assertions.assertEquals(RoleErrors.INVALID_NAME, e.getMessage());
		}
	}

	@Test
	void testInvalidName() {
		try {
			RoleValidator.validateName("i");
			// Expecting an exception, so the test should fail if no exception is thrown
			Assertions.fail("Expected InvalidRoleException for an invalid name");
		} catch (InvalidRoleException e) {
			Assertions.assertEquals(RoleErrors.INVALID_NAME, e.getMessage());
		}
	}
}
