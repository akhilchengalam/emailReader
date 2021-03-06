package com.bourntec.emailreader.activemq.listener;

import java.io.IOException;
import java.util.Map;

import javax.jms.Queue;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.bourntec.emailreader.flows.utils.MailProcessor;

/**
 * @author Gopal
 *
 */
@Component
public class QueueConsumers {

	@Autowired
	MailProcessor mailProcessor;

	@Autowired
	JmsTemplate jmsTemplate;

	@Autowired
	Queue persistQueue;

	@JmsListener(destination = "emailreader.saveQueue")
	public void saveFiles(byte[] rawMessageBytes) throws IOException, MessagingException {
		MimeMessage message = mailProcessor.toMimeMessage(rawMessageBytes);
		jmsTemplate.convertAndSend(persistQueue, mailProcessor.processMimeMessage(message));
	}

	@JmsListener(destination = "emailreader.persistQueue")
	public void persistToDB(Map<String,String> email) {
		mailProcessor.printEmail(email);
		mailProcessor.saveEmail(email);
	}
	
	@JmsListener(destination = "emailreader.jmsTest")
	public void jmsTest(String input) {
		jmsTemplate.convertAndSend("emailreader.jmsTestReceiver", input);
	}
	
}