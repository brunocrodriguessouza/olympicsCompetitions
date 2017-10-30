package com.olympicscompetitions.exception;

public class ExceptionMoreThanFourCompetitionOnTheSameDay extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExceptionMoreThanFourCompetitionOnTheSameDay() {
		super("TIt is not allowed to enter more than four competitions on the same day and place");
	}
	
}
