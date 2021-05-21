package com.ksewa.springsecurity.service.sms;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;

//implement google phone number validator

@Service("twillio")
public class TwillioSmsSender implements SmsSender {

	@Value("${twillio.trial_number}")
	private String trialNumber;

	@Override
	public void sendSms(SmsRequest smsRequest) {
		PhoneNumber to = new PhoneNumber(smsRequest.getPhoneNumber());
		PhoneNumber from = new PhoneNumber(trialNumber);
		MessageCreator creator = Message.creator(to, from, smsRequest.getMessage());
		creator.create();

	}

}
