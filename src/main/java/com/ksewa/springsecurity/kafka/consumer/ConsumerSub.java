package com.ksewa.springsecurity.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ConsumerSub {

	private final Logger log = LoggerFactory.getLogger(ConsumerSub.class);

	public ConsumerSub() {
		log.info("Consumer is ready to consume...!!!!!");
	}

	/**
	 * Send otp to the registered email id for Email verification.
	 * 
	 * @param record
	 */
	@KafkaListener(topics = "sendEmailOTP", containerFactory = "concurrentKafkaListenerContainerFactor")
	public void sendEmailForVerification(ConsumerRecord<String, String> record) {

	}
}
