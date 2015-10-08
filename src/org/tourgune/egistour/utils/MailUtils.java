package org.tourgune.egistour.utils;

import javax.annotation.Resource;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

/**
 * AppTrack
 *
 * Created by CICtourGUNE on 10/04/13.
 * Copyright (c) 2013 CICtourGUNE. All rights reserved.
 * 
 */
@Service
public class MailUtils {

	@Resource
	private MailSender mailSender;

	/**
	 * Método que especifica el receptor del correo electrónico. Se almacena en un objeto de tipo MailSender
	 * 
	 * @param mailSender Objeto que contiene en receptor del correo
	 */
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	/**
	 * Método que se usa para enviar correos electrónicos
	 * 
	 * @param from Remitente del correo
	 * @param to Destinatario del correo
	 * @param subject Asunto del correo
	 * @param msg Contenido del correo
	 */
	public void sendMail(String from, String to, String subject, String msg) {

		SimpleMailMessage message = new SimpleMailMessage();

		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(msg);
		mailSender.send(message);
	}

}
