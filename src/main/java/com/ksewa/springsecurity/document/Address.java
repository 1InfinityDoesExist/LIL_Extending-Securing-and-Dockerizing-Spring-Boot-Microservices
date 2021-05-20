package com.ksewa.springsecurity.document;

import java.io.Serializable;

@lombok.Data
public class Address implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String addressLine1;
	private String addressLine2;
	private String zipcode;
	private String country;
	private String state;
	private String destrict;
}
