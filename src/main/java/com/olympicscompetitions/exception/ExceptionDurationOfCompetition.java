package com.olympicscompetitions.exception;

public class ExceptionDurationOfCompetition extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExceptionDurationOfCompetition() {
		super("Duration of a competition must be at least 30 minutes");
	}

}
