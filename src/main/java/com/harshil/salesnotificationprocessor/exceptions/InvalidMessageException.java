package com.harshil.salesnotificationprocessor.exceptions;

public class InvalidMessageException extends Exception {
	private static final long serialVersionUID = 1L;

	public InvalidMessageException(String message) {
        super(message);
    }

    public InvalidMessageException(String message, Throwable cause) {
        super(message, cause);
    }
}
