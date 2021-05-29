package com.ksewa.springsecurity.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@lombok.Data
public class SignUpResponse {

	@ApiModelProperty(value = "Message receviced after completion of signup request", example = "Please verify your email id in order to complete the registration process.")
	@JsonProperty(value = "message")
	private String message;

}
