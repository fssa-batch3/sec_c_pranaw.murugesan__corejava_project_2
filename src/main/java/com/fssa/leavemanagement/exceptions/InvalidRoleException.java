package com.fssa.leavemanagement.exceptions;

public class InvalidRoleException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs an InvalidRoleException with the specified error message.
	 * 
	 * @param msg The error message describing the reason for the exception.
	 */
	public InvalidRoleException(String msg) {
		super(msg);
	}

	/**
	 * Constructs an InvalidRoleException with the specified cause.
	 * 
	 * @param te The underlying cause of the exception.
	 */
	public InvalidRoleException(Throwable te) {
		super(te);
	}

	/**
	 * Constructs an InvalidRoleException with the specified error message and
	 * cause.
	 * 
	 * @param msg The error message describing the reason for the exception.
	 * @param te  The underlying cause of the exception.
	 */
	public InvalidRoleException(String msg, Throwable te) {
		super(msg, te);
	}

}