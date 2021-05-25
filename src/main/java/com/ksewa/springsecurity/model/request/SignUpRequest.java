package com.ksewa.springsecurity.model.request;

import java.io.Serializable;

@lombok.Data
public class SignUpRequest implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	private String email;
}
