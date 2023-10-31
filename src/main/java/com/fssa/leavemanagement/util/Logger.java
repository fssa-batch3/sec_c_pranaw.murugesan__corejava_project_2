package com.fssa.leavemanagement.util;

public class Logger {
	/**
	 * Default constructor for creating a Logger object.
	 */
	private Logger() {
		// Making this constructor private so that it should not make object
	}
 
	/**
	 * Print an informational log message to the console.
	 * 
	 * @param msg The log message to be printed.
	 */
	public static void info(String msg) {
		System.out.println(msg);
	}
	
	
}
