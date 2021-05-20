package com.ksewa.springsecurity.document;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import javax.validation.constraints.Email;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "shopkeeper")
@Data
public class Shopkeeper implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	private String firstName;
	private String lastName;
	private String middleName;
	private ShopDetails shopDetails;
	private boolean isActive;
	@Email
	private String email;
	private String mobile;
	private Address address;
	private String profilePicture;
	@CreatedDate
	private LocalDateTime creationDate;
	@LastModifiedDate
	private LocalDateTime updationDate;
	private Set<String> customerIds;
	@Version
	private Long version;
}
