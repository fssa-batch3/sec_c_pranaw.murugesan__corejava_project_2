//package com.fssa.leavemanagement.dao;
//
//import java.sql.SQLException;
//import java.time.LocalDate;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//
//import com.fssa.leavemanagement.errors.EmployeeErrors;
//import com.fssa.leavemanagement.exceptions.DAOException;
//import com.fssa.leavemanagement.exceptions.InvalidEmployeeException;
//import com.fssa.leavemanagement.model.Employee;
//
//
//
//public class TestEmployeeDao {
//
//	@Test
//	void testAddEmployee() throws SQLException, InvalidEmployeeException {
//		Employee employee = new Employee();
//		employee.setDateOfJoin(LocalDate.of(2022, 10, 10));
//		employee.setEmail("pranaw2@gmail.com");
//		employee.setName("pranaw");
//		employee.setPassword("Ipranaw100%");
//		employee.setStatus(true);
//
//		Assertions.assertTrue(EmployeeDao.addEmployee(employee,"Manager"));
//	}
//
//	@Test
//	void testInvalidAddEmployee() {
//		Employee employee = new Employee();
//		try {
//			Assertions.assertTrue(EmployeeDao.addEmployee(employee,"abc"));
//		} catch (InvalidEmployeeException e) {
//			e.printStackTrace();
//			Assertions.assertEquals("Invalid employee passed to DAO Layer", e.getMessage());
//		}
//	}
//	@Test
//	void testUpdateEmployee() throws SQLException, InvalidEmployeeException, DAOException {
//		Employee employee = new Employee();
//		employee.setDateOfJoin(LocalDate.of(2022, 10, 10));
//		employee.setEmail("pranaw1@gmail.com");
//		employee.setName("pranaw");
//		employee.setPassword("Ipranaw100%");
//		employee.setStatus(true);
//		int primaryKey = 2;
//		Assertions.assertTrue(EmployeeDao.updateEmployee(employee, primaryKey));
//
//	}
//
//	@Test
//	void testDeleteEmployee() throws DAOException, InvalidEmployeeException {
//		int id = 2;
//		Assertions.assertTrue(EmployeeDao.deleteEmployee(id));
//	}
//	@Test
//	void testInvalidDeleteEmployee() {
//		int id = -2;
//		try {
//			Assertions.assertTrue(EmployeeDao.deleteEmployee(id));
//		} catch (DAOException | InvalidEmployeeException e) {
//			Assertions.assertEquals(EmployeeErrors.INVALID_ID, e.getMessage());
//		}
//	}
//	@Test
//	void testInvalidUpdateEmployee() {
//		Employee employee = new Employee();
//		try {
//			Assertions.assertTrue(EmployeeDao.updateEmployee(employee,0));
//		} catch (DAOException e) {
//			System.out.println(e);
//			e.printStackTrace();
//		}
//	}
//	@Test
//	void testReadEmployee() throws Exception {
//	
//			try {
//				boolean result = EmployeeDao.readEmployee();
//				Assertions.assertTrue(result);
//			} catch (DAOException | SQLException e) {
//				throw e;
//			}
//		
//	}
//	@Test
//	void testFindEmployeeByName() throws DAOException, SQLException {
//		String find = "pranaw";
//		Assertions.assertTrue(EmployeeDao.findEmployeeByName(find));
//	}
//	@Test
//	void testInvalidFindEmployeeByName() throws DAOException, SQLException {
//		String find = "po";
//		Assertions.assertTrue(EmployeeDao.findEmployeeByName(find));
//	}
//}