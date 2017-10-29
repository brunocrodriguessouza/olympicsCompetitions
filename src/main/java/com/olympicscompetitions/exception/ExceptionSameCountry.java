package com.olympicscompetitions.exception;

public class ExceptionSameCountry extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExceptionSameCountry() {
		super("Confronted with the same country only is allowed in the semi-finals and finals");
	}

}
