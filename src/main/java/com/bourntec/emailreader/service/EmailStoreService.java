package com.bourntec.emailreader.service;


import java.util.List;
import org.springframework.stereotype.Service;

import com.bourntec.emailreader.models.EmailStoreModel;

/**
 * @author Gopal
 *
 */
@Service(value = "emailStoreService")
public interface EmailStoreService {

	EmailStoreModel findByEmailId(Long emailId);
	List<EmailStoreModel> findBySubject(String subject);
	List<EmailStoreModel> findAll();
	public EmailStoreModel saveEmail(String subject, String sentDate, String from, String to, String contentType, String body,
			String hasAttachment, String attachmentCount, String attachmentNames);
	public EmailStoreModel saveEmail(EmailStoreModel esm);

}
