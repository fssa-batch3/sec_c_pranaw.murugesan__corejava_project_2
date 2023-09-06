package com.fssa.leavemanagement.exceptions;

public class DAOException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a DAOException with the specified error message.
	 * 
	 * @param msg The error message describing the reason for the exception.
	 */
	public DAOException(String msg) {
		super(msg);
	}

	/**
	 * Constructs a DAOException with the specified cause.
	 * 
	 * @param te The underlying cause of the exception.
	 */
	public DAOException(Throwable te) {
		super(te);
	}

	/**
	 * Constructs a DAOException with the specified error message and cause.
	 * 
	 * @param msg The error message describing the reason for the exception.
	 * @param te  The underlying cause of the exception.
	 */
	public DAOException(String msg, Throwable te) {
		super(msg, te);
	}

}