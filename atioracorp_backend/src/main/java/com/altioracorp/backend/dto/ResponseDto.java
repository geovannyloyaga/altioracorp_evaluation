package com.altioracorp.backend.dto;

public class ResponseDto<T> {

	private int code;

	private String error;

	private T responseObject;

	public ResponseDto(int code, String error, T responseObject) {
		super();
		this.code = code;
		this.error = error;
		this.responseObject = responseObject;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public T getResponseObject() {
		return responseObject;
	}

	public void setResponseObject(T responseObject) {
		this.responseObject = responseObject;
	}

}