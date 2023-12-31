package com.fssa.leavemanagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.fssa.leavemanagement.exceptions.DAOException;
import com.fssa.leavemanagement.exceptions.InvalidRoleException;
import com.fssa.leavemanagement.model.Role;
import com.fssa.leavemanagement.util.ConnectionUtil;
import com.fssa.leavemanagement.validator.RoleValidator;

/*
 * The RoleDao class provides methods to interact with the database for managing
 * roles. This class allows adding, updating, and retrieving roles from the
 * database. It also provides methods to find roles by their names and delete
 * roles from the database. The database table used for storing roles is "role."
 * The class utilizes RoleValidator for validating role data before performing
 * database operations. It also uses a custom Logger for logging messages
 * related to role operations.
 */
public class RoleDao {
	private RoleDao() {
//		private constructor
	}

	/**
	 * Adds a Role object to the database.
	 * 
	 * @param role The Role object to be added to the database.
	 * @return true if the addition is successful, false otherwise.
	 * @throws InvalidRoleException If the role object is invalid according to
	 *                              validation rules.
	 * @throws SQLException         If a database access error occurs.
	 * @throws DAOException
	 */
	public static boolean addRole(Role role) throws InvalidRoleException, SQLException, DAOException {
		try {
			RoleValidator.validate(role);
		} catch (InvalidRoleException e) {
			throw new InvalidRoleException("Invalid Role passed to DAO Layer");
		}

		try (Connection connection = ConnectionUtil.getConnection()) {
			String query = "INSERT INTO role(name) VALUES (?);";
			try (PreparedStatement pst = connection.prepareStatement(query)) {
				pst.setString(1, role.getName());

				int rows = pst.executeUpdate();

				return (rows > 0);
			}
		}
	}

	/**
	 * Retrieves the role ID from the database by its name.
	 * 
	 * @param name The name of the role to search for.
	 * @return The ID of the role if found, otherwise 0.
	 * @throws SQLException If a database access error occurs.
	 * @throws DAOException
	 */
	public static int getRoleIdByName(String name) throws SQLException, DAOException {
		int id = 0;
		String query = "SELECT id FROM role WHERE name = ?";
		try (Connection connection = ConnectionUtil.getConnection()) {
			try (PreparedStatement pst = connection.prepareStatement(query)) {
				pst.setString(1, name);
				try (ResultSet rs = pst.executeQuery()) {
					while (rs.next()) {
						id = rs.getInt(1);
					}
				}
			}
		}
		return id;
	}

	/**
	 * Deletes a Role object from the database.
	 * 
	 * @param role The Role object to be deleted from the database.
	 * @return true if the deletion is successful, false otherwise.
	 * @throws InvalidRoleException If the role object is invalid according to
	 *                              validation rules.
	 * @throws SQLException         If a database access error occurs.
	 * @throws DAOException
	 */
	public static boolean deleteRole(Role role) throws InvalidRoleException, SQLException, DAOException {

		int id = RoleDao.getRoleIdByName(role.getName());

		try (Connection connection = ConnectionUtil.getConnection()) {
			String query = "DELETE FROM role WHERE id= ?";
			try (PreparedStatement pst = connection.prepareStatement(query)) {

				pst.setInt(1, id);

				int rows = pst.executeUpdate();

				return (rows > 0);
			}
		} catch (SQLException e) {
			throw new InvalidRoleException(e.getMessage());
		}
	}

	/**
	 * Retrieves all roles from the database and prints them to the console.
	 * 
	 * @return true if the retrieval is successful, false otherwise.
	 * @throws InvalidRoleException If an error occurs during the retrieval process.
	 * @throws SQLException         If a database access error occurs.
	 * @throws DAOException
	 */
	public static List<Role> getAllRole() throws InvalidRoleException, SQLException, DAOException {
		List<Role> arr = new ArrayList<>();

		try (Connection connection = ConnectionUtil.getConnection()) {
			String query = "SELECT * FROM role";
			try (Statement statement = connection.createStatement()) {
				try (ResultSet resultSet = statement.executeQuery(query)) { // this will run the query and return the
																			// value
					while (resultSet.next()) { // printing columns until there is no values
						Role role = new Role();
						role.setId(resultSet.getInt(1));
						role.setName(resultSet.getString(2));
						arr.add(role);

					}
					return arr;
				}
			} catch (SQLException e) {

				throw new InvalidRoleException(e.getMessage());

			}

		}
	}

	/**
	 * Finds and retrieves a role by its name from the database.
	 * 
	 * @param name The name of the role to search for.
	 * @return true if the role is found, false otherwise.
	 * @throws InvalidRoleException If the role with the given name cannot be found.
	 * @throws SQLException         If a database access error occurs.
	 * @throws DAOException
	 */
	public static Role findRoleByName(String name) throws InvalidRoleException, SQLException, DAOException {
		Role role = new Role();
		try (Connection connection = ConnectionUtil.getConnection()) {
			String query = "SELECT * FROM role WHERE name = ?";
			try (PreparedStatement pst = connection.prepareStatement(query)) {
				pst.setString(1, name);
				try (ResultSet resultSet = pst.executeQuery()) {
					if (resultSet.next()) {
						role.setId(resultSet.getInt(1));
						role.setName(resultSet.getString(2));
					}
				}
			} catch (SQLException e) {
				throw new InvalidRoleException("Cannot find role");
			}
		}
		return role;
	}
}
