package com.fssa.leavemanagement.validator;

import java.util.regex.Pattern;

import com.fssa.leavemanagement.errors.RoleErrors;
import com.fssa.leavemanagement.exceptions.InvalidRoleException;
import com.fssa.leavemanagement.model.Role;

public class RoleValidator {
	private RoleValidator() {
//	private constructor
	}

	/**
	 * 
	 * Validate a Role object to ensure that its data is valid.
	 * 
	 * @param role The Role object to be validated.
	 * @return true if the role data is valid, false otherwise.
	 * @throws InvalidRoleException if the role data is invalid.
	 */
	public static boolean validate(Role role) throws InvalidRoleException {
		if (role == null) {
			throw new InvalidRoleException(RoleErrors.INVALID_ROLE);
		}
		validateId(role.getId());
		validateName(role.getName());
		return true;
	}

	/**
	 * Validate the ID of a role.
	 * 
	 * @param id The ID of the role to be validated.
	 * @return true if the ID is valid, false otherwise.
	 * @throws InvalidRoleException if the ID is invalid.
	 */
	public static boolean validateId(int id) throws InvalidRoleException {
		if (id <= 0) {
			throw new InvalidRoleException(RoleErrors.INVALID_ID);
		}
		return true;
	}

	/**
	 * Validate the name of a role.
	 * 
	 * @param name The name of the role to be validated.
	 * @return true if the name is valid, false otherwise.
	 * @throws InvalidRoleException if the name is invalid.
	 */
	public static boolean validateName(String name) throws InvalidRoleException {
		if (name == null || name.trim().length() < 2) {
			throw new InvalidRoleException(RoleErrors.INVALID_NAME);
		}
		String regex = "^[A-Za-z ]{2,}$";
		boolean match = Pattern.compile(regex).matcher(name).matches();
		if (match) {
			return true;
		} else {
			throw new InvalidRoleException(RoleErrors.INVALID_NAME);
		}
	}

}
