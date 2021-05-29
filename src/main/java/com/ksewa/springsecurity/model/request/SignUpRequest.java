package com.ksewa.springsecurity.model.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@lombok.Data
public class SignUpRequest implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "Email Id of the customer/shopkeeper", example = "patel@gmail.com")
	@JsonProperty(value = "email")
	private String email;
}
