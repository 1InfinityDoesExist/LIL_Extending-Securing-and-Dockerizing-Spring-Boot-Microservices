package com.linkedInLearning.springsecurity.config;

import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPublicKey;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bouncycastle.operator.OperatorCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.util.Base64;
import com.nimbusds.jose.util.Base64URL;

import lombok.extern.slf4j.Slf4j;

/*
 * keytool -genkeypair -alias ssia -keyalg RSA -keypass ssia123 -keystore
 * ssia.jks -storepass ssia123
 * 
 */

/*
 * public key extraction keytool -list -rfc --keystore ssia.jks | openssl x509
 * -inform pem -pubkey
 */

@Slf4j
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
	private static final String JWK_KID = UUID.randomUUID().toString();
	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.passwordEncoder(NoOpPasswordEncoder.getInstance()).checkTokenAccess("isAuthenticated()")
				.tokenKeyAccess("isAuthenticated()");
	}

	/**
	 * scopes are defined by OAuth2. It is intended to define what the end-user
	 * allowed each client to do on its behalf (information from
	 * authorization-server to resource-servers).
	 * 
	 * Consider this, a user authorizes Twitter to post a user's tweet to Facebook.
	 * In this case, Twitter will have a scope write_facebook_status. Although user
	 * has authority to change it's own profile but this doesn't mean that Twitter
	 * can also change user's profile. In other words, scope are client
	 * authorities/roles and it's not the User's authorities/roles.
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient("client").secret("secret").scopes("READ_ALL_GUEST", "UPDATE_GUEST", "WRITE_GUEST")
				.autoApprove(true).authorities("ROLE_GUEST_AUTHORIZE_CLIENT")
				.authorizedGrantTypes("password", "refresh_token").and().withClient("client1").secret("secret1")
				.scopes("READ_ALL_GUEST").autoApprove(true).authorities("ROLE_GUEST_AUTHORIZE_CLIENT")
				.authorizedGrantTypes("password", "refresh_token");
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(List.of(new CustomTokenEnhancer(), accessTokenConverter()));
		endpoints.tokenStore(tokenStore()).accessTokenConverter(accessTokenConverter())
				.tokenEnhancer(tokenEnhancerChain).authenticationManager(authenticationManager);
	}

	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		Map<String, String> customHeaders = Collections.singletonMap("kid", JWK_KID);
		JwtAccessTokenConverter tokenConverter = new JwtCustomHeadersAccessTokenConverter(customHeaders, keyPair());
		tokenConverter.setKeyPair(keyPair());
		tokenConverter.setAccessTokenConverter(new CustomAccessTokenConverter());
		return tokenConverter;
	}

	@Bean
	public KeyPair keyPair() {
		KeyStoreKeyFactory keyPairFactory = new KeyStoreKeyFactory(new ClassPathResource("ssia.jks"),
				"ssia123".toCharArray());
		return keyPairFactory.getKeyPair("ssia");
	}

//	private KeyPair keyPair() {
//		KeyPair keyPair = null;
//		try {
//			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
//			keyPairGenerator.initialize(2048);
//			keyPair = keyPairGenerator.generateKeyPair();
//		} catch (final NoSuchAlgorithmException e) {
//			log.debug("Failed to generate keyPair");
//		}
//		return keyPair;
//	}

	@Bean
	public JWKSet jwkSet() throws Exception {
		try {
			MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
			Certificate cert = CertificateGenerator.generateCertificate(keyPair());
			log.info("-----cert {}", cert.getPublicKey());
			RSAKey.Builder builder = new RSAKey.Builder((RSAPublicKey) keyPair().getPublic()).keyUse(KeyUse.SIGNATURE)
					.algorithm(JWSAlgorithm.RS256).keyID(JWK_KID)
					.x509CertChain(Collections.singletonList(Base64.encode(cert.getEncoded())))
					.x509CertSHA256Thumbprint(Base64URL.encode(sha256.digest(cert.getEncoded())));
			return new JWKSet(builder.build());
		} catch (NoSuchAlgorithmException | CertificateException | OperatorCreationException e) {
			e.printStackTrace();
		}
		return null;
	}
}
