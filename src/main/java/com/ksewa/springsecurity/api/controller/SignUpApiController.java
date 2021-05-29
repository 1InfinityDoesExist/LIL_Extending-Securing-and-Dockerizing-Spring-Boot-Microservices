package com.ksewa.springsecurity.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import com.ksewa.springsecurity.api.SignUpApi;
import com.ksewa.springsecurity.document.OTPDetails;
import com.ksewa.springsecurity.exception.AlreadyExitException;
import com.ksewa.springsecurity.exception.InvalidInputException;
import com.ksewa.springsecurity.model.request.SignUpRequest;
import com.ksewa.springsecurity.model.response.SignUpResponse;
import com.ksewa.springsecurity.repository.OTPDetailsRepository;
import com.ksewa.springsecurity.util.OTPGenerator;

@RestController
public class SignUpApiController implements SignUpApi {

	private static final Logger LOGGER = LoggerFactory.getLogger(SignUpApiController.class);

	@Value("${otp.expiry.time}")
	private int otpExpirtyTime;

	private final OTPDetailsRepository otpDetailsRepository;
	private final OTPGenerator otpGenerator;

	@Autowired
	public SignUpApiController(OTPDetailsRepository otpDetailsRepository, OTPGenerator otpGenerator) {
		this.otpDetailsRepository = otpDetailsRepository;
		this.otpGenerator = otpGenerator;
	}

	// validate email and mobile
	@Transactional
	@Override
	public ResponseEntity<SignUpResponse> signup(SignUpRequest signUpRequest)
			throws InvalidInputException, AlreadyExitException {
		LOGGER.info("Sign up process starts...!!!");
		SignUpResponse signUpResponse = null;
		if (!ObjectUtils.isEmpty(signUpRequest)) {
			if (!StringUtils.isEmpty(signUpRequest.getEmail())) {
				signUpResponse = new SignUpResponse();
				OTPDetails otpDetails = otpDetailsRepository.findOTPDetailsByEmail(signUpRequest.getEmail());
				if (!ObjectUtils.isEmpty(otpDetails)) {
					throw new AlreadyExitException("Email id already used.");
				}
				int otp = otpGenerator.generateOTP();
				otpDetails = new OTPDetails();
				otpDetails.setEmail(signUpRequest.getEmail());
				otpDetails.setEmailOTP(Integer.valueOf(otp));
				otpDetails.setEmailOtpExpiryDate(otpExpirtyTime);
				otpDetailsRepository.save(otpDetails);

				signUpResponse.setMessage(
						"OTP has been sent to the registered email. Please verify the email to complete the signup process.");
				return ResponseEntity.status(HttpStatus.OK).body(signUpResponse);
			} else {
				throw new InvalidInputException("Email id must not be null or empty.");
			}
		} else {
			throw new InvalidInputException("SignUpRequest must not be null or empty.");
		}
	}
}
