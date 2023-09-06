package com.fssa.leavemanagement.exceptions;

public class InvalidEmployeeException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs an InvalidEmployeeException with the specified error message.
	 * 
	 * @param msg The error message describing the reason for the exception.
	 */
	public InvalidEmployeeException(String msg) {
		super(msg);
	}

	/**
	 * Constructs an InvalidEmployeeException with the specified cause.
	 * 
	 * @param te The underlying cause of the exception.
	 */
	public InvalidEmployeeException(Throwable te) {
		super(te);
	}

	/**
	 * Constructs an InvalidEmployeeException with the specified error message and
	 * cause.
	 * 
	 * @param msg The error message describing the reason for the exception.
	 * @param te  The underlying cause of the exception.
	 */
	public InvalidEmployeeException(String msg, Throwable te) {
		super(msg, te);
	}

}
