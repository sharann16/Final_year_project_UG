package com.courier.demo.exceptions;

public class MobileNumberAlreadyExistingException extends RuntimeException {
	public MobileNumberAlreadyExistingException(String msg) {
		super(msg);
	}

}
