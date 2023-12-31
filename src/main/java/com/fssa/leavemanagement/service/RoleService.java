package com.fssa.leavemanagement.service;

import java.sql.SQLException;
import java.util.List;

import com.fssa.leavemanagement.dao.RoleDao;
import com.fssa.leavemanagement.errors.RoleErrors;
import com.fssa.leavemanagement.exceptions.DAOException;
import com.fssa.leavemanagement.exceptions.InvalidRoleException;
import com.fssa.leavemanagement.model.Role;
import com.fssa.leavemanagement.validator.RoleValidator;

public class RoleService {
	private RoleService() {
//		private constructor
	}

	/**
	 * Add a role to the database.
	 * 
	 * @param role The Role object containing the details of the role to be added.
	 * @return true if the role is added successfully, false otherwise.
	 * @throws InvalidRoleException if the role data is invalid.
	 * @throws SQLException         if a general SQL exception occurs.
	 * @throws DAOException
	 */
	public static boolean addRole(Role role) throws InvalidRoleException, SQLException, DAOException {
		if (RoleValidator.validate(role)) {
			RoleDao.addRole(role);
		}
		return true;
	}

	/**
	 * Get all roles from the database and print their details.
	 * 
	 * @return true if all roles are retrieved and printed successfully, false
	 *         otherwise.
	 * @throws InvalidRoleException if an invalid role is encountered during the
	 *                              process.
	 * @throws SQLException         if a general SQL exception occurs.
	 * @throws DAOException
	 */
	public static List<Role> getAllRole() throws InvalidRoleException, SQLException, DAOException {
	    return RoleDao.getAllRole();
	}

	/**
	 * Delete an existing role from the database.
	 * 
	 * @param role The Role object containing the details of the role to be deleted.
	 * @return true if the role is deleted successfully, false otherwise.
	 * @throws InvalidRoleException if the role data is invalid.
	 * @throws SQLException         if a general SQL exception occurs.
	 * @throws DAOException
	 */
	public static boolean deleteRole(Role role) throws InvalidRoleException, SQLException, DAOException {
		if (RoleValidator.validate(role)) {
			RoleDao.deleteRole(role);
		}
		return true;
	}

	/**
	 * Find a role by name and print its details.
	 * 
	 * @param name The name of the role to be searched for.
	 * @return true if the role is found and its details are printed successfully,
	 *         false otherwise.
	 * @throws InvalidRoleException if the role name is invalid.
	 * @throws SQLException         if a general SQL exception occurs.
	 * @throws DAOException
	 */

	
	public static Role findRoleByName(String name) throws InvalidRoleException, SQLException, DAOException {
	    if (RoleValidator.validateName(name)) {
	        return RoleDao.findRoleByName(name);
	    }
	    throw new InvalidRoleException(RoleErrors.ROLE_NOT_FOUND);
	}

}
