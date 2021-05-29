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

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaPKMSTServerCodegen", date = "2021-05-20T03:28:49.575Z")

@ApiModel(value = "Customer details", description = "Customer object contains the customer details.")
@Document(collection = "customer")
@lombok.Data
public class Customer implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "Unique mongo id", example = "608bfb4e1b89300008af4b79")
	@Id
	@JsonProperty(value = "_id")
	private String id;

	@ApiModelProperty(value = "First name of the customer", example = "Avinash")
	@JsonProperty(value = "firstName")
	private String firstName;

	@ApiModelProperty(value = "Last name of the customer", example = "Patel")
	@JsonProperty(value = "lastName")
	private String lastName;

	@ApiModelProperty(value = "Middle name of the customer", example = "Aaka", allowEmptyValue = true)
	@JsonProperty(value = "middleName")
	private String middleName;

	@ApiModelProperty(value = "Determina whether the customer is active or not.", example = "true or false", hidden = true, allowEmptyValue = false)
	@JsonProperty(value = "isActive")
	private boolean isActive;

	@ApiModelProperty(value = "Email Id of the customer", example = "patel_avinash@gmail.com")
	@JsonProperty(value = "email")
	@Email
	private String email;

	@ApiModelProperty(value = "Mobile number of the customer", example = "9876543210")
	@JsonProperty(value = "mobile")
	private String mobile;

	@ApiModelProperty(value = "")
	@JsonProperty(value = "address")
	private Address address;

	@ApiModelProperty(value = "Profile pic of the cusomer", example = "www.amazon.com/profile/pic")
	@JsonProperty(value = "profile_picture_url")
	private String profilePicture;

	@ApiModelProperty(value = "Registration date", example = "2017-01-13T17:09:42.411")
	@JsonProperty(value = "creation_date")
	@CreatedDate
	private LocalDateTime creationDate;

	@ApiModelProperty(value = "Customer details modification date", example = "2017-01-13T17:09:42.411")
	@JsonProperty(value = "modification_date")
	@LastModifiedDate
	private LocalDateTime updationDate;

}
