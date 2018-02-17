package com.gustavoalves.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.gustavoalves.cursomc.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);

}
