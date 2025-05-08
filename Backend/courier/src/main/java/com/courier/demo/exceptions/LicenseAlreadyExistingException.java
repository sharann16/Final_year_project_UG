package com.courier.demo.exceptions;

public class LicenseAlreadyExistingException extends RuntimeException{
	public LicenseAlreadyExistingException(String msg) {
		super(msg);
	}

}
