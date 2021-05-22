package com.ksewa.springsecurity.service.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
 * sudo npm install -g maildev
maildev
 */

@Slf4j
@AllArgsConstructor
@Service
public class EmailService implements EmailSender {

	private final JavaMailSender emailSender;

	@Async
	@Override
	public void send(String to, String email) {

		try {
			MimeMessage mimeMessage = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
			helper.setText(email, true);
			helper.setTo(to);
			helper.setSubject("Confirm your email");
			helper.setFrom("braian@gmail.com");
			emailSender.send(mimeMessage);
		} catch (MessagingException ex) {
			log.error("Failed to send email : {}", ex);
			throw new IllegalStateException();
		}
	}
}
