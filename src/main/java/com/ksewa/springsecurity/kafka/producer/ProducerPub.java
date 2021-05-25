package com.ksewa.springsecurity.kafka.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

@Configuration
public class ProducerPub {

	private final Logger log = LoggerFactory.getLogger(ProducerPub.class);

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public ProducerPub() {
		log.info("Publisher is ready to publish...!!!!!");
	}

	public ListenableFuture<SendResult<String, String>> sendMessage(String topic, String message) {
		ListenableFuture<SendResult<String, String>> listenableFuture = kafkaTemplate.send(topic, message);
		return listenableFuture;
	}

}
