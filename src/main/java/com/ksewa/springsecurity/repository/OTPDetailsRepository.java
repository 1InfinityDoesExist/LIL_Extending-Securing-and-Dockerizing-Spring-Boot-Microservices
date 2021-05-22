package com.ksewa.springsecurity.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ksewa.springsecurity.document.OTPDetails;

@Repository
public interface OTPDetailsRepository extends MongoRepository<OTPDetails, String> {

	public OTPDetails findOTPDetailsByEmail(String email);

}
