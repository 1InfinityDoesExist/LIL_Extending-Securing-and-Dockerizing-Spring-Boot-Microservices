package com.ksewa.springsecurity.service.sms;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "twillio")
public class TwillioConfig {

	@Value("${twillio.account_sid}")
	private String accountId;
	@Value("${twillio.auth_token}")
	private String oauthToken;
	@Value("${twillio.trial_number}")
	private String trialNumber;

	public TwillioConfig() {
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getOauthToken() {
		return oauthToken;
	}

	public void setOauthToken(String oauthToken) {
		this.oauthToken = oauthToken;
	}

	public String getTrialNumber() {
		return trialNumber;
	}

	public void setTrialNumber(String trialNumber) {
		this.trialNumber = trialNumber;
	}

}
