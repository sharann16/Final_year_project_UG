package com.courier.demo.exceptions;

public class EmailIdAlreadyExistingException extends RuntimeException {
	public EmailIdAlreadyExistingException(String msg) {
		super(msg);
	}

}
