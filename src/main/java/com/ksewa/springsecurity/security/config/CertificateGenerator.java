package com.ksewa.springsecurity.security.config;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CertificateGenerator {

	private CertificateGenerator() {

	}

	public static Certificate generateCertificate(KeyPair keyPair)
			throws CertificateException, OperatorCreationException {
		X500Name x500Name = new X500Name("CN=***.com, OU=Security&Defense, O=*** Crypto., L=Ottawa, ST=Ontario, C=CA");
		SubjectPublicKeyInfo pubKeyInfo = SubjectPublicKeyInfo.getInstance(keyPair.getPublic().getEncoded());
		final Date start = new Date();
		final Date until = Date
				.from(LocalDate.now().plus(365, ChronoUnit.DAYS).atStartOfDay().toInstant(ZoneOffset.UTC));
		final X509v3CertificateBuilder certificateBuilder = new X509v3CertificateBuilder(x500Name,
				new BigInteger(10, new SecureRandom()), start, until, x500Name, pubKeyInfo);
		ContentSigner contentSigner = new JcaContentSignerBuilder("SHA256WithRSA").build(keyPair.getPrivate());

		Certificate certificate = new JcaX509CertificateConverter().setProvider(new BouncyCastleProvider())
				.getCertificate(certificateBuilder.build(contentSigner));

		log.info("!-----x.509 certificate is successfully generated!!!!!!");

		log.info("-----Certificate : {}", certificate);
		return certificate;

	}
}
