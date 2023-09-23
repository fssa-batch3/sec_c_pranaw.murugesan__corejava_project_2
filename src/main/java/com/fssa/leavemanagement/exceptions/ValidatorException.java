package com.fssa.leavemanagement.exceptions;

public class ValidatorException extends Exception {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;

	public ValidatorException(String msg) {
		super(msg);
	}

	public ValidatorException(Throwable te) {
		super(te);
	}

	public ValidatorException(String msg, Throwable te) {
		super(msg, te);
	}
}
