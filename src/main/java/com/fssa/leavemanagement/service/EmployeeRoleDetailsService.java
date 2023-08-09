package com.fssa.leavemanagement.service;

import java.sql.SQLException;

import com.fssa.leavemanagement.dao.EmployeeRoleDetailsDao;
import com.fssa.leavemanagement.errors.EmployeeErrors;
import com.fssa.leavemanagement.exceptions.DAOException;
import com.fssa.leavemanagement.model.EmployeeRoleDetails;
import com.fssa.leavemanagement.util.Logger;

public class EmployeeRoleDetailsService {
	private EmployeeRoleDetailsService() {
//		private constructor
	}
	static Logger logger = new Logger();

	/**
	 * Add employee role details to the database.
	 * 
	 * @param erd The EmployeeRoleDetails object containing the role details of the
	 *            employee.
	 * @return true if the employee role details are added successfully, false
	 *         otherwise.
	 * @throws DAOException if an SQL exception occurs while adding the details.
	 */
	public static boolean addEmployeeRoleDetails(EmployeeRoleDetails erd) throws DAOException {
		try {
			return EmployeeRoleDetailsDao.addEmployeeRoleDetails(erd);
		} catch (SQLException e) {
			throw new DAOException(EmployeeErrors.INVALID_EMPLOYEE_ROLE_DETAIL);
		}
	}

	/**
	 * Update employee role details in the database.
	 * 
	 * @param erd The EmployeeRoleDetails object containing the updated role details
	 *            of the employee.
	 * @return true if the employee role details are updated successfully, false
	 *         otherwise.
	 * @throws DAOException if an SQL exception occurs while updating the details.
	 */
	public static boolean updateEmployeeRoleDetails(EmployeeRoleDetails erd) throws DAOException {
		try {
			return EmployeeRoleDetailsDao.updateEmployeeRoleDetails(erd);
		} catch (SQLException e) {
			throw new DAOException(EmployeeErrors.INVALID_EMPLOYEE_ROLE_DETAIL);
		}
	}

	/**
	 * Get all employee role details from the database.
	 * 
	 * @return true if all employee role details are retrieved successfully, false
	 *         otherwise.
	 * @throws DAOException 
	 */
	public static boolean getAllEmployeeRoleDetails() throws DAOException {
		try {
			return EmployeeRoleDetailsDao.getAllEmployeeRoleDetails();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}