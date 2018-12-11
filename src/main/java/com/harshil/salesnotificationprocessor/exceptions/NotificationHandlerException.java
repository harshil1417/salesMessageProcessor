package com.harshil.salesnotificationprocessor.exceptions;

public class NotificationHandlerException extends Exception {
	private static final long serialVersionUID = 1L;

	public NotificationHandlerException(String message) {
        super(message);
    }

    public NotificationHandlerException(String message, Throwable cause) {
        super(message, cause);
    }
}
