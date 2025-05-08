package com.courier.demoGlobalExceptionHandle;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.courier.demo.exceptions.EmailIdAlreadyExistingException;
import com.courier.demo.exceptions.MobileNumberAlreadyExistingException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(EmailIdAlreadyExistingException.class)
	public ResponseEntity<String> handleduplicatedemail(EmailIdAlreadyExistingException ex){
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
	}
	
	@ExceptionHandler(EmailIdAlreadyExistingException.class)
	public ResponseEntity<String> handleduplicatemobile(MobileNumberAlreadyExistingException ex){
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
	}

}
