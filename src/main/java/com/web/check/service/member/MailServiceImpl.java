package com.web.check.service.member;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.web.check.service.MailService;

//MailService Interface ���� Ŭ����
@Service
public class MailServiceImpl implements MailService {
	// org.springframework.mail.javamail.JavaMailSender
	private JavaMailSender javaMailSender;

	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}


	@Override
	public boolean send(String subject, String text, String from, String to, String filePath) {
		// javax.mail.internet.MimeMessage
		System.out.println(javaMailSender.toString());
		MimeMessage message = javaMailSender.createMimeMessage();

		try {
			// org.springframework.mail.javamail.MimeMessageHelper
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			helper.setSubject(subject);
			helper.setText(text, true);
			helper.setFrom(from);
			helper.setTo(to);

			// ÷�� ���� ó��
			if (filePath != null) {
				File file = new File(filePath);
				if (file.exists()) {
					helper.addAttachment(file.getName(), new File(filePath));
				}
			}

			javaMailSender.send(message);
			return true;
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return false;
	}



	
}
