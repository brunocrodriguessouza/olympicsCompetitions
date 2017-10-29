package com.olympicscompetitions.exception;

public class ExceptionTimeConflict extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExceptionTimeConflict() {
	super("The same date and time is not allowed for the same location and modality");
	}
}
