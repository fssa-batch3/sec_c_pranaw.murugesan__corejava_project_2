package com.fssa.leavemanagement.service;

import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.fssa.leavemanagement.errors.RoleErrors;
import com.fssa.leavemanagement.exceptions.DAOException;
import com.fssa.leavemanagement.exceptions.InvalidRoleException;
import com.fssa.leavemanagement.model.Role;

/**
 * The TestRoleService class contains unit tests for the RoleService class. It
 * tests the addRole, deleteRole, readRole, and findRoleByName methods of the
 * RoleService class. It also includes tests for handling invalid scenarios such
 * as adding/deleting null or invalid Role objects.
 * 
 * @author PranawMurugesan
 *
 */
class TestRoleService {
	@Test
	void testAddRole() throws InvalidRoleException, SQLException, DAOException {
		Role role = new Role("Junior Designer");
		Assertions.assertTrue(RoleService.addRole(role));
	}

	@Test
	void testInvalidAddRole() throws DAOException {
		Role role = new Role();

		try {
			Assertions.assertTrue(RoleService.addRole(role));
		} catch (InvalidRoleException | SQLException e) {
			Assertions.assertEquals(RoleErrors.INVALID_NAME, e.getMessage());
		}

	}

	@Test
	void testDeleteRole() throws InvalidRoleException, SQLException, DAOException {
		Role role = new Role("Junior Designer");
		Assertions.assertTrue(RoleService.deleteRole(role));
	}

	@Test
	void testInvalidDeleteRole() throws DAOException {
		Role role = new Role("abc");
		try {
			Assertions.assertTrue(RoleService.deleteRole(role));
		} catch (InvalidRoleException | SQLException e) {
			Assertions.assertEquals(RoleErrors.INVALID_NAME, e.getMessage());
		}
	}

	@Test
	void testGetAllRole() throws InvalidRoleException, SQLException, DAOException {
		List<Role> roles = RoleService.getAllRole();

		Assertions.assertNotNull(roles);
		Assertions.assertFalse(roles.isEmpty());
	}

	@Test
	void testFindRoleByName() throws InvalidRoleException, SQLException, DAOException {
		Role role = new Role("CEO");
		Role foundRole = RoleService.findRoleByName(role.getName());

		Assertions.assertNotNull(foundRole);
	}
}
