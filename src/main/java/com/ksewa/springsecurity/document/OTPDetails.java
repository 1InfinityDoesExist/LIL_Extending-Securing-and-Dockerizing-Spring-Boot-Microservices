package com.ksewa.springsecurity.document;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "otp_details")
public class OTPDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	private String mobile;
	private String email;
	private Integer emailOTP;
	private Integer mobileOTP;
	private boolean isEmailVerified;
	private boolean isMobileVerified;
	private Date emailOtpExpiryDate;

	public void setEmailOtpExpiryDate(int minutes) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, minutes);
		this.emailOtpExpiryDate = calendar.getTime();
	}

	public boolean isEmailOTPExpired() {
		return new Date().after(this.emailOtpExpiryDate);
	}

	private Date mobileOtpExpiryDate;

	public void setMobileOtpExpiryDate(int minutes) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MINUTE, minutes);
		this.mobileOtpExpiryDate = now.getTime();
	}

	public boolean isMobileOtpExpired() {
		return new Date().after(mobileOtpExpiryDate);
	}
}
