package com.ksewa.springsecurity.service.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.twilio.Twilio;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class TwillioInitializer {

	private final TwillioConfig twillioConfig;

	@Autowired
	public TwillioInitializer(TwillioConfig twillioConfig) {
		this.twillioConfig = twillioConfig;
		Twilio.init(twillioConfig.getAccountId(), twillioConfig.getOauthToken());
		log.info("-----Twillio Initialized ...!!!!");
	}

}
