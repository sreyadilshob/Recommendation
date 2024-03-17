package com.kk.recommendation.model;

import java.util.List;

public class ErrorMessage {
	String code;
	String message;
	List<BackendError> backendError;	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<BackendError> getBackendError() {
		return backendError;
	}

	public void setBackendError(List<BackendError> backendError) {
		this.backendError = backendError;
	}
	
}
