package com.ksewa.springsecurity.service.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@org.springframework.stereotype.Service
public class Service {

	private final SmsSender smsSender;

	@Autowired
	public Service(@Qualifier("twillio") TwillioSmsSender twillioSmsSender) {
		this.smsSender = twillioSmsSender;
	}

	public void sendSms(SmsRequest smsRequest) {
		smsSender.sendSms(smsRequest);
	}

}
