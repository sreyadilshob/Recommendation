package com.kk.recommendation.controller;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.kk.recommendation.model.BackendError;
import com.kk.recommendation.model.ErrorMessage;

@ControllerAdvice
public class APIExceptionHandler extends ResponseEntityExceptionHandler{
	

	@ExceptionHandler({HttpClientErrorException.class})
	ResponseEntity<ErrorMessage> handleHttpClientErrorException(HttpClientErrorException exp){
		System.out.println("Exception class name "+exp.getClass());
		System.out.println("Exception message "+exp.getMessage());
		ErrorMessage errMessage = new ErrorMessage();
		errMessage.setCode("REC_1004");
		errMessage.setMessage("Backend service error");
		BackendError backendErrorResp = exp.getResponseBodyAs(BackendError.class);
		errMessage.setBackendError(Arrays.asList(backendErrorResp));
		return new ResponseEntity<ErrorMessage>(errMessage, HttpStatus.SERVICE_UNAVAILABLE);
	}

	@ExceptionHandler({RuntimeException.class})
	ResponseEntity<String> handleRuntimeException(RuntimeException exp){
		System.out.println("Exception class name "+exp.getClass());
		System.out.println("Exception message "+exp.getMessage());
		return new ResponseEntity<String>(" Exception occured "+exp.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
	}
}
