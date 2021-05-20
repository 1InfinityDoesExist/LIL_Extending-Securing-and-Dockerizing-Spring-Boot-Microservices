package com.ksewa.springsecurity.document;

import java.io.Serializable;

@lombok.Data
public class ShopDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String shopName;
	private Address shopAddress;
	private Long ratings;
	private String description;
}
