package com.example.tms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	
	@Autowired
	JavaMailSender javaMailSender;
	
	public void sendSimpleMessage(String to, String subject, String text) {
		SimpleMailMessage smm= new SimpleMailMessage();
		smm.setFrom("pinkysreeja321@gmail.com");
		smm.setTo(to);
		smm.setSubject(subject);
		smm.setText(text);
		javaMailSender.send(smm);
	}

}
