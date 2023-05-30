package com.warework.service.mail.client;

import java.util.Iterator;
import java.util.List;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.warework.core.service.client.AbstractClient;
import com.warework.core.service.client.ClientException;
import com.warework.core.util.helper.StringL1Helper;
import com.warework.service.log.LogServiceConstants;
import com.warework.service.mail.client.connector.JavaMailSenderConnector;

/**
 * Mail Client that performs operations with JavaMail.<br>
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public final class JavaMailSender extends AbstractClient implements MailClientFacade {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Constant that defines the default name for the client.
	 */
	public static final String DEFAULT_CLIENT_NAME = "javamail-sender";

	// Message type.

	private static final String TYPE_TEXT = "text";

	private static final String SUBTYPE_PLAIN = "plain";

	private static final String CHARSET = "charset";

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Charset to use for the text of the mail.
	private String charset;

	// MIME subtype to use for the message.
	private String subtype;

	// Message type.
	private String type;

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Sends an email.
	 * 
	 * @param subject     A brief summary of the topic of the message.<br>
	 *                    <br>
	 * @param from        The email address of the sender. In many email clients it
	 *                    is not changeable except through changing account
	 *                    settings.<br>
	 *                    <br>
	 * @param to          List with the email addresses of the message's
	 *                    recipients.<br>
	 *                    <br>
	 * @param cc          List with the email addresses of the message's recipients.
	 *                    Allows you to simultaneously send multiple copies of an
	 *                    email to secondary recipients.<br>
	 *                    <br>
	 * @param bcc         List with the email addresses of the message's recipients.
	 *                    Addresses added for the delivery list but not (usually)
	 *                    listed in the message data, remaining invisible to other
	 *                    recipients.<br>
	 *                    <br>
	 * @param attachments List with <code>javax.mail.BodyPart</code> objects where
	 *                    each one specifies an attachment.<br>
	 *                    <br>
	 * @param message     The content of the email message. This is exactly the same
	 *                    as the body of a regular letter.<br>
	 *                    <br>
	 * @throws ClientException If there is an error when trying to send the
	 *                         e-mail.<br>
	 *                         <br>
	 */
	public void send(final String subject, final String from, final List<String> to, final List<String> cc,
			final List<String> bcc, final List<?> attachments, final String message) throws ClientException {

		// Create and initialize the mail message.
		MimeMessage mimeMessage = null;
		try {

			// Create the mail message.
			mimeMessage = new MimeMessage((Session) getConnector().getConnectionSource());

			// FROM.
			mimeMessage.setFrom(new InternetAddress(from));

			// TO.
			if ((to != null) && (to.size() > 0)) {
				for (final Iterator<String> iterator = to.iterator(); iterator.hasNext();) {
					mimeMessage.addRecipients(Message.RecipientType.TO, iterator.next());
				}
			} else {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot send an email with Client '" + getName() + "' in Service '"
								+ getService().getName() + "' because no recipients for the message are defined.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// CC.
			if ((cc != null) && (cc.size() > 0)) {
				for (final Iterator<String> iterator = cc.iterator(); iterator.hasNext();) {
					mimeMessage.addRecipients(Message.RecipientType.CC, iterator.next());
				}
			}

			// BCC.
			if ((bcc != null) && (bcc.size() > 0)) {
				for (final Iterator<String> iterator = bcc.iterator(); iterator.hasNext();) {
					mimeMessage.addRecipients(Message.RecipientType.BCC, iterator.next());
				}
			}

			// Set the subject and the text for the message.
			if ((charset != null) && (subtype != null)) {

				// Set the subject of the message.
				mimeMessage.setSubject(subject, charset);

				// Set the text for the message.
				mimeMessage.setText(message, charset, subtype);

			} else if (charset != null) {

				// Set the subject of the message.
				mimeMessage.setSubject(subject, charset);

				// Set the text for the message.
				mimeMessage.setText(message, charset);

			} else {

				// Set the subject of the message.
				mimeMessage.setSubject(subject);

				// Set the text for the message.
				mimeMessage.setText(message);

			}

			// Create the message body part.
			final MimeBodyPart messageBodyPart = new MimeBodyPart();

			// Bind the MIME message as the content of the body part.
			messageBodyPart.setContent(mimeMessage, type);

			// Set the text for the body part.
			if ((charset != null) && (subtype != null)) {
				messageBodyPart.setText(message, charset, subtype);
			} else if (charset != null) {
				messageBodyPart.setText(message, charset);
			} else {
				messageBodyPart.setText(message);
			}

			// Add attachments.
			if ((attachments != null) && (attachments.size() > 0)) {

				// Create a multipart.
				final Multipart multipart = new MimeMultipart();

				// Bind the message body part.
				multipart.addBodyPart(messageBodyPart);

				// Add attachments.
				for (int i = 0; i < attachments.size(); i++) {

					// Get an attachment;
					final Object attachment = attachments.get(i);

					// Add the attachment.
					if (attachment instanceof BodyPart) {
						multipart.addBodyPart((BodyPart) attachment);
					} else {
						throw new ClientException(getScopeFacade(),
								"WAREWORK cannot send e-mail with Client '" + getName() + "' in Service '"
										+ getService().getName() + "' because given attachment is not a '"
										+ BodyPart.class.getName() + "' class.",
								null, LogServiceConstants.LOG_LEVEL_WARN);
					}

				}

				// Bind the multipart into the mime message.
				mimeMessage.setContent(multipart);

			}

			// Save the message.
			mimeMessage.saveChanges();

		} catch (final MessagingException e) {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot send email with Client '" + getName() + "' in Service '" + getService().getName()
							+ "' because the creation of the message reported the following error: " + e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Create the connection.
		final Transport connection = (Transport) getConnection();

		// Send the email.
		try {
			connection.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
		} catch (final Exception e) {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot send an email with Client '" + getName() + "' in Service '"
							+ getService().getName() + "' because the mail connection reported the following error: "
							+ e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}

	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Closes the connection with the mail server.
	 * 
	 * @throws ClientException If there is an error when trying to close the
	 *                         connection.<br>
	 *                         <br>
	 */
	protected void close() throws ClientException {

		// Create the connection.
		final Transport connection = (Transport) getConnection();

		// Close the mail connection.
		try {
			connection.close();
		} catch (final MessagingException e) {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot close Mail Client '" + getName() + "' at Service '" + getService().getName()
							+ "' because the mail connection reported the following error: " + e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}

	}

	/**
	 * Validates if the connection with the mail server is closed.
	 * 
	 * @return <code>true</code> if the connection is closed and <code>false</code>
	 *         if the connection is open.<br>
	 *         <br>
	 */
	protected boolean isClosed() {

		// Create the connection.
		final Transport connection = (Transport) getConnection();

		// Return if the connection with the mail server is closed
		return !connection.isConnected();

	}

	/**
	 * Initializes the client.
	 * 
	 * @throws ClientException If there is an error when trying to initialize the
	 *                         Client.<br>
	 *                         <br>
	 */
	protected void initialize() throws ClientException {

		// Get the value of the charset parameter.
		if (getConnector().getInitParameter(JavaMailSenderConnector.PARAMETER_MIME_MESSAGE_CHARSET) != null) {

			// Get the protocol parameter.
			final Object charsetParam = getConnector()
					.getInitParameter(JavaMailSenderConnector.PARAMETER_MIME_MESSAGE_CHARSET);

			// Validate the type for protocol parameter.
			if (charsetParam instanceof String) {
				charset = (String) charsetParam;
			} else {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot initialize Client '" + getName() + "' in Service '" + getService().getName()
								+ "' because given '" + JavaMailSenderConnector.PARAMETER_MIME_MESSAGE_CHARSET
								+ "' parameter is not a String object.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

		}

		// Get the value of the charset parameter.
		if (getConnector().getInitParameter(JavaMailSenderConnector.PARAMETER_MIME_MESSAGE_SUBTYPE) != null) {

			// Get the protocol parameter.
			final Object subtypeParam = getConnector()
					.getInitParameter(JavaMailSenderConnector.PARAMETER_MIME_MESSAGE_SUBTYPE);

			// Validate the type for protocol parameter.
			if (subtypeParam instanceof String) {
				subtype = (String) subtypeParam;
			} else {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot initialize Client '" + getName() + "' in Service '" + getService().getName()
								+ "' because given '" + JavaMailSenderConnector.PARAMETER_MIME_MESSAGE_SUBTYPE
								+ "' parameter is not a String object.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

		}

		// Create the type of the mail message.
		final StringBuffer mailType = new StringBuffer();

		// Set text type.
		mailType.append(TYPE_TEXT);
		mailType.append(StringL1Helper.CHARACTER_FORWARD_SLASH);

		// Set subtype.
		if (subtype != null) {
			mailType.append(subtype);
		} else {
			mailType.append(SUBTYPE_PLAIN);
		}

		// Add a semicolon.
		mailType.append(StringL1Helper.CHARACTER_SEMICOLON);

		// Set the charset.
		if (charset != null) {
			mailType.append(StringL1Helper.CHARACTER_SPACE);
			mailType.append(CHARSET);
			mailType.append(StringL1Helper.CHARACTER_EQUALS);
			mailType.append(charset);
		}

		// Set the type.
		type = mailType.toString();

	}

}
