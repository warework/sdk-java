package com.warework.service.mail.client;

import java.util.List;

import com.warework.core.service.client.ClientException;
import com.warework.core.service.client.ClientFacade;

/**
 * Client to perform mail operations.<br>
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public interface MailClientFacade extends ClientFacade {

	/**
	 * Sends an email.
	 * 
	 * @param subject     A brief summary of the topic of the message.<br>
	 *                    <br>
	 * @param from        The email address of the sender. In many email clients it
	 *                    is not changeable except through changing account
	 *                    settings.<br>
	 *                    <br>
	 * @param to          List with the email addresses of the message's recipients.
	 *                    You can provide a <code>String</code> object with a single
	 *                    email address or multiple addresses separated by a
	 *                    semicolon. You can also provide an array of
	 *                    <code>String</code> objects or a
	 *                    <code>java.util.List</code> with <code>String</code>
	 *                    objects that represent each email address. This argument
	 *                    is mandatory.<br>
	 *                    <br>
	 * @param cc          Allows you to simultaneously send multiple copies of an
	 *                    email to secondary recipients. It is a list with the email
	 *                    addresses of the message's recipients. You can provide a
	 *                    <code>String</code> object with a single email address or
	 *                    multiple addresses separated by a semicolon. You can also
	 *                    provide an array of <code>String</code> objects or a
	 *                    <code>java.util.List</code> with <code>String</code>
	 *                    objects that represent each email address. This argument
	 *                    is optional.<br>
	 *                    <br>
	 * @param bcc         Addresses added for the delivery list but not (usually)
	 *                    listed in the message data, remaining invisible to other
	 *                    recipients. It is a list with the email addresses of the
	 *                    message's recipients. You can provide a
	 *                    <code>String</code> object with a single email address or
	 *                    multiple addresses separated by a semicolon. You can also
	 *                    provide an array of <code>String</code> objects or a
	 *                    <code>java.util.List</code> with <code>String</code>
	 *                    objects that represent each email address. This argument
	 *                    is optional.<br>
	 *                    <br>
	 * @param attachments Email messages may have one or more files/resources
	 *                    associated to it. They serve the purpose of delivering
	 *                    binary or text files of unspecified size. For attachments,
	 *                    you need to bear in mind that each Mail Client may accept
	 *                    a specific type of object to represent the attachment.
	 *                    Review the documentation of the Mail Client that you
	 *                    require and use any of the objects type that it supports.
	 *                    This argument is optional.<br>
	 *                    <br>
	 * @param message     The content of the email message. This is exactly the same
	 *                    as the body of a regular letter.<br>
	 *                    <br>
	 * @throws ClientException If there is an error when trying to send the
	 *                         e-mail.<br>
	 *                         <br>
	 */
	void send(final String subject, final String from, final List<String> to, final List<String> cc,
			final List<String> bcc, final List<?> attachments, final String message) throws ClientException;

}
