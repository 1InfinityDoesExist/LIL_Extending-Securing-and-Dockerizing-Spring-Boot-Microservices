package com.ksewa.springsecurity.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ksewa.springsecurity.exception.AlreadyExitException;
import com.ksewa.springsecurity.exception.InvalidInputException;
import com.ksewa.springsecurity.model.request.SignUpRequest;
import com.ksewa.springsecurity.model.response.SignUpResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaPKMSTServerCodegen", date = "2021-04-16T06:00"
		+ ":39.859Z")
@Api(value = "SignUpController", description = "The SignUpController API")
public interface SignUpApi {

	@ApiOperation(value = "", notes = "", response = SignUpResponse.class, tags = {})
	@ApiResponses(value = { @ApiResponse(code = 200, message = "") })
	@RequestMapping(value = "/signup", consumes = "application/json", produces = "application/json", method = RequestMethod.POST)
	public ResponseEntity<?> signup(@RequestBody SignUpRequest signUpRequest)
			throws InvalidInputException, AlreadyExitException;

}
