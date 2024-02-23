package com.altioracorp.backend.dto;

import java.util.List;

public class ResponseListDto<T> extends ResponseDto<T> {

	private List<T> responseList;

	private int totalRows;

	public ResponseListDto(int code, String error, List<T> responseList, int totalRows) {
		super(code, error, null);
		this.responseList = responseList;
		this.totalRows = totalRows;
	}

	public List<T> getResponseList() {
		return responseList;
	}

	public void setResponseList(List<T> responseList) {
		this.responseList = responseList;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

}
