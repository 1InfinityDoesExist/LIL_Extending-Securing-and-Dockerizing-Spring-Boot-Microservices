package com.ksewa.springsecurity.configurations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@EnableCaching
public class RedisConfig {

	private final Logger log = LoggerFactory.getLogger(RedisConfig.class);

	@Value("${ksewa.spring.redis.host}")
	private String host;

	@Value("${ksewa.spring.redis.port}")
	private String port;

	/**
	 * For standAlone redis connection. Remember this config is not for cluster.
	 * 
	 * @return
	 */
	@Bean
	@Primary
	public LettuceConnectionFactory redisConnectionFactoryStandalone() {
		log.info("---Redis Config Host and Port --Host {} and Port {}", host, port);
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
		redisStandaloneConfiguration.setHostName(host);
		redisStandaloneConfiguration.setPort(Integer.valueOf(port));
		return new LettuceConnectionFactory(redisStandaloneConfiguration);
	}

	/**
	 * For cluster , add @Bean Annotation on the method.
	 * 
	 * @return
	 */
	@Bean
	public LettuceConnectionFactory redisConnectionFactoryCuster() {
		RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration();
		clusterConfiguration.clusterNode(host, Integer.parseInt(port));
		return new LettuceConnectionFactory(clusterConfiguration);
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> redisTemp = new RedisTemplate<>();
		redisTemp.setConnectionFactory(redisConnectionFactoryStandalone());
		return redisTemp;
	}
}
