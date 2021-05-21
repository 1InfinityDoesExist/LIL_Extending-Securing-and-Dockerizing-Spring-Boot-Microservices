package com.ksewa.springsecurity.service.sms;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SmsRequest {

	private final String phoneNumber;
	private final String message;

	public SmsRequest(@JsonProperty("phoneNumber") String phoneNumber, @JsonProperty("message") String message) {
		super();
		this.phoneNumber = phoneNumber;
		this.message = message;
	}

}
