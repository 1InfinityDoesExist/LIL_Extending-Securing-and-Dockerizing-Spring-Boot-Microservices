package com.ksewa.springsecurity.kafka.producer;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
@EnableKafka
public class ProdConfig {
	@Value("${spring.kafka.producer.bootstrap-servers}")
	private String bootstarpServers;
	@Value("${spring.kafka.producer.key-serializer}")
	private String keySerializer;
	@Value("${spring.kafka.producer.value-serializer}")
	private String valueSerialzier;
	@Value("${spring.kafka.producer.acks}")
	private String acks;
	@Value("${spring.kafka.producer.retries}")
	private String retries;
	@Value("${spring.kafka.producer.batch-size}")
	private String batchSize;
	@Value("${spring.kafka.producer.buffer-memory}")
	private String bufferMemory;

	@Bean
	public Map<String, Object> producerConfiguration() {
		Map<String, Object> properties = new HashMap<>();
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstarpServers);
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerialzier);
		properties.put(ProducerConfig.ACKS_CONFIG, acks);
		properties.put(ProducerConfig.RETRIES_CONFIG, retries);
		properties.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
		properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
		return properties;
	}

	@Bean
	public ProducerFactory<String, String> producerFactory() {
		return new DefaultKafkaProducerFactory<>(producerConfiguration());
	}

	@Bean
	public KafkaTemplate<String, String> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}

	@Bean("producerPub")
	public ProducerPub producerPub() {
		return new ProducerPub();
	}

}
