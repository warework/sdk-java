package com.warework.service.mail;

import com.warework.core.service.ProxyServiceConstants;
import com.warework.core.util.CommonValueL1Constants;
import com.warework.core.util.helper.StringL1Helper;

/**
 * Constants for the Mail Service.<br>
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public final class MailServiceConstants extends ProxyServiceConstants {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Constant that identifies this Service type.
	 */
	public static final String SERVICE = "mail";

	/**
	 * Constant that defines the default name for this Service.
	 */
	public static final String DEFAULT_SERVICE_NAME = SERVICE + StringL1Helper.CHARACTER_HYPHEN
			+ CommonValueL1Constants.STRING_SERVICE;

	// OPERATION NAMES

	/**
	 * Sends an email with a specific Mail Client. Use the following parameters in
	 * order to invoke this operation:<br>
	 * <br>
	 * <ul>
	 * <li><b><code>client-name</code></b>: Name of the Mail Client where to send
	 * the email. This parameter is mandatory and it must be a
	 * <code>java.lang.String</code>.<br>
	 * <br>
	 * </li>
	 * <li><b><code>subject</code></b>: A brief summary of the topic of the message.
	 * This parameter must be a <code>java.lang.String</code>.<br>
	 * <br>
	 * </li>
	 * <li><b><code>from</code></b>: The email address of the sender. In many email
	 * clients it is not changeable except through changing account settings. This
	 * parameter must be a <code>java.lang.String</code>.<br>
	 * <br>
	 * </li>
	 * <li><b><code>to</code></b>: List with the email addresses of the message's
	 * recipients. You can provide a <code>String</code> object with a single email
	 * address or multiple addresses separated by a semicolon. You can also provide
	 * an array of <code>String</code> objects or a <code>java.util.Vector</code>
	 * with <code>String</code> objects that represent each email address. This
	 * parameter is mandatory and it must be a <code>java.lang.String</code>.<br>
	 * <br>
	 * </li>
	 * <li><b><code>cc</code></b>: Allows you to simultaneously send multiple copies
	 * of an email to secondary recipients. It is a list with the email addresses of
	 * the message's recipients. You can provide a <code>String</code> object with a
	 * single email address or multiple addresses separated by a semicolon. You can
	 * also provide an array of <code>String</code> objects or a
	 * <code>java.util.Vector</code> with <code>String</code> objects that represent
	 * each email address. This argument is optional.<br>
	 * <br>
	 * </li>
	 * <li><b><code>bcc</code></b>: Addresses added for the delivery list but not
	 * (usually) listed in the message data, remaining invisible to other
	 * recipients. It is a list with the email addresses of the message's
	 * recipients. You can provide a <code>String</code> object with a single email
	 * address or multiple addresses separated by a semicolon. You can also provide
	 * an array of <code>String</code> objects or a <code>java.util.Vector</code>
	 * with <code>String</code> objects that represent each email address. This
	 * argument is optional.<br>
	 * <br>
	 * </li>
	 * <li><b><code>attachments</code></b>: Email messages may have one or more
	 * files/resources associated to it. They serve the purpose of delivering binary
	 * or text files of unspecified size. For attachments, you need to bear in mind
	 * that each Mail Client may accept a specific type of object to represent the
	 * attachment. Review the documentation of the Mail Client that you require and
	 * use any of the objects type that it supports. This argument is optional.<br>
	 * <br>
	 * </li>
	 * <li><b><code>message</code></b>: The content of the email message. This is
	 * exactly the same as the body of a regular letter. This parameter must be a
	 * <code>java.lang.String</code>.<br>
	 * </li>
	 * </ul>
	 * This operation does not return anything.
	 */
	public static final String OPERATION_NAME_SEND = "send";

	// OPERATION PARAMETERS

	/**
	 * Operation parameter that specifies the a brief summary of the topic of the
	 * message.
	 */
	public static final String OPERATION_PARAMETER_SUBJECT = "subject";

	/**
	 * Operation parameter that specifies the email address of the sender. In many
	 * email clients it is not changeable except through changing account settings.
	 */
	public static final String OPERATION_PARAMETER_FROM = "from";

	/**
	 * Operation parameter that specifies a list with the email addresses of the
	 * message's recipients. You can provide a <code>String</code> object with a
	 * single email address or multiple addresses separated by a semicolon. You can
	 * also provide an array of <code>String</code> objects or a
	 * <code>java.util.Vector</code> with <code>String</code> objects that represent
	 * each email address.
	 */
	public static final String OPERATION_PARAMETER_TO = "to";

	/**
	 * Operation parameter that allows you to simultaneously send multiple copies of
	 * an email to secondary recipients. It is a list with the email addresses of
	 * the message's recipients. You can provide a <code>String</code> object with a
	 * single email address or multiple addresses separated by a semicolon. You can
	 * also provide an array of <code>String</code> objects or a
	 * <code>java.util.Vector</code> with <code>String</code> objects that represent
	 * each email address.
	 */
	public static final String OPERATION_PARAMETER_CC = "cc";

	/**
	 * Operation parameter that specifies addresses added for the delivery list but
	 * not (usually) listed in the message data, remaining invisible to other
	 * recipients. It is a list with the email addresses of the message's
	 * recipients. You can provide a <code>String</code> object with a single email
	 * address or multiple addresses separated by a semicolon. You can also provide
	 * an array of <code>String</code> objects or a <code>java.util.Vector</code>
	 * with <code>String</code> objects that represent each email address.
	 */
	public static final String OPERATION_PARAMETER_BCC = "bcc";

	/**
	 * Operation parameter that specifies the files/resources associated to the
	 * email. They serve the purpose of delivering binary or text files of
	 * unspecified size. For attachments, you need to bear in mind that each Mail
	 * Client may accept a specific type of object to represent the attachment.
	 * Review the documentation of the Mail Client that you require and use any of
	 * the objects type that it supports.
	 */
	public static final String OPERATION_PARAMETER_ATTACHMENTS = "attachments";

	/**
	 * Operation parameter that specifies the content of the email message. This is
	 * exactly the same as the body of a regular letter.
	 */
	public static final String OPERATION_PARAMETER_MESSAGE = "message";

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * This constructor does not perform any operation.
	 */
	private MailServiceConstants() {
		// DO NOTHING.
	}

}
