package com.cloud.project.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component("emailServiceImpl")
public class EmailServiceImpl {

	@Autowired
	private JavaMailSender emailSender;

	public void sendSimpleMessage(String to, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		emailSender.send(message);
	}

	public JavaMailSender getEmailSender() {
		return emailSender;
	}

	public void setEmailSender(JavaMailSender emailSender) {
		this.emailSender = emailSender;
	}

}
