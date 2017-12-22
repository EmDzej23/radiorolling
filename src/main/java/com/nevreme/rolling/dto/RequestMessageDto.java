package com.nevreme.rolling.dto;

import org.springframework.stereotype.Component;

@Component
public class RequestMessageDto {
	private int requestType;

	public int getRequestType() {
		return requestType;
	}

	public void setRequestType(int requestType) {
		this.requestType = requestType;
	}
	
}
