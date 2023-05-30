package com.warework.service.mail;

import java.util.List;
import java.util.Map;

import com.warework.core.service.ProxyServiceFacade;
import com.warework.core.service.ServiceException;

/**
 * Warework Mail Service is a Proxy Service responsible of email operations. You
 * can handle different implementations of Mail Clients, like JavaMail, and
 * perform email operations in different platforms with them. Each Mail Client
 * can be configured with an XML file to work with a specific mail server, so
 * this Service is quite handy when you have to connect to multiple mail
 * servers.<br>
 * <br>
 * Sending an email requires you to write just one line of code. You only have
 * to indicate which Mail Client to use (it holds the information required to
 * connect with the mail server) plus the subject, the message and the
 * recipients of the email. You can also include attachments very easily.<br>
 * <br>
 * This Service integrates with the Framework to offer you some interesting
 * features. For instance, a Mail Client can retrieve connections via a JNDI
 * Provider. This feature allows you store the configuration for your email
 * connections in an application server.<br>
 * <br>
 * <b>Create and retrieve a Mail Service</b><br>
 * <br>
 * To create the Mail Service in a Scope, you always need to provide a unique
 * name for the Service and the <code>MailServiceImpl</code> class that exists
 * in the <code>com.warework.service.mail</code> package:<br>
 * <br>
 * <code>
 * // Create the Mail Service and register it in a Scope. <br>
 * scope.createService("mail-service", MailServiceImpl.class, null);<br>
 * </code> <br>
 * Once it is created, you can get it using the same name (when you retrieve an
 * instance of a Mail Service, you will get the <code>MailServiceFacade</code>
 * interface):<br>
 * <br>
 * <code>
 * // Get an instance of the Mail Service. <br>
 * MailServiceFacade mailService = (MailServiceFacade) scope.getService("mail-service");<br>
 * </code> <br>
 * Most of the times, you will need to specify a set of parameters that
 * configure how the Service must work. Review the next section to know how to
 * define Mail Clients with these parameters.<br>
 * <br>
 * <b>Add and connect Mail Clients</b><br>
 * <br>
 * Now the Mail Service is running but you need at least one Client where to
 * perform operations. To add a Mail Client into the Service you have to invoke
 * method <code>createClient()</code> that exists in its Facade. This method
 * requests a name for the new Client and a Connector which performs the
 * creation of the Mail Client. Let's see how to register a sample Client in
 * this Service:<br>
 * <br>
 * <code>
 * // Add a Mail Client in the Mail Service. <br>
 * mailService.createClient("sample-client", SampleConnector.class, null);<br>
 * </code> <br>
 * The SampleConnector class creates the Sample Mail Client and registers it in
 * the Mail Service. After that, we have to tell the Mail Service that we want
 * to perform operations with the Sample Mail Client. We do so by connecting the
 * Mail Client:<br>
 * <br>
 * <code>
 * // Connect the Sample Mail Client. <br>
 * mailService.connect("sample-client");<br>
 * </code> <br>
 * Remember to review the documentation of each Mail Client to know which
 * parameters it accepts.<br>
 * <br>
 * <b>Perform Mail operations</b><br>
 * <br>
 * Each Mail Client exposes its functionality in the
 * <code>MailServiceFacade</code> interface and it is where you can find the
 * necessary method to perform Mail operations.<br>
 * <br>
 * Now we are going to check out how to send an email with the send method. The
 * minimum information you have to provide to this method is shown below:<br>
 * <br>
 * <ul>
 * <li>Mail Client name: Mail Service is a Proxy Service so you have to identify
 * each Mail Client by name.</li>
 * <li>Subject: A brief summary of the topic of the message.</li>
 * <li>From: The email address of the sender. In many email clients it is not
 * changeable except through changing account settings.</li>
 * <li>To: List with the email addresses of the message's recipients.</li>
 * <li>Message: The content of the email message. This is exactly the same as
 * the body of a regular letter.</li>
 * </ul>
 * <br>
 * This is the minimum line of code required to send an email:<br>
 * <br>
 * <code>
 * // Send an email. <br>
 * mailService.send("sample-client", "subject", "from@mail.com", "to@mail.com", null, null, null, "mail message");<br>
 * </code> <br>
 * If you require sending the message to multiple recipients, you can separate
 * each email address with a semicolon like this:<br>
 * <br>
 * <code>
 * // Send an email to multiple recipients.<br> 
 * mailService.send("sample-client", "subject", "from@mail.com", "to1@mail.com;to2@mail.com;to3@mail.com", null, null, null, "mail message");<br>
 * </code> <br>
 * You can perform the same action with an array of String objects:<br>
 * <br>
 * <code>
 * // Send an email to multiple recipients.<br> 
 * mailService.send("sample-client", "subject", "from@mail.com", new String[]{"to1@mail.com", "to2@mail.com", "to3@mail.com"}, null, null, null, "mail message");<br>
 * </code> <br>
 * If you prefer a java.util.List instead of an array of String objects, you can
 * use it too:<br>
 * <br>
 * <code>
 * // Create a list for the recipients of the message.<br> 
 * List&lt;String&gt; to = new ArrayList&lt;String&gt;();<br>
 * <br>
 * // Add each recipient. <br>
 * to.addElement("to1@mail.com");<br>
 * to.addElement("to2@mail.com");<br>
 * to.addElement("to3@mail.com");<br>
 * <br>
 * // Send an email to multiple recipients. <br>
 * mailService.send("sample-client", "subject", "from@mail.com", to, null, null, null, "mail message");<br>
 * </code> <br>
 * You can also specify the following optional arguments in this method:<br>
 * <br>
 * <ul>
 * <li>CC (Carbon Copy): Allows you to simultaneously send multiple copies of an
 * email to secondary recipients.</li>
 * <li>BCC (Blind Carbon Copy): Addresses added for the delivery list but not
 * (usually) listed in the message data, remaining invisible to other
 * recipients.</li>
 * <li>Attachments: Email messages may have one or more files/resources
 * associated to it. They serve the purpose of delivering binary or text files
 * of unspecified size.</li>
 * </ul>
 * <br>
 * The following example shows how to send an email with CC and BCC
 * recipients:<br>
 * <br>
 * <code>
 * // Send an email to multiple recipients.<br> 
 * mailService.send("sample-client", "subject", "from@mail.com", new String[]{"to1@mail.com", "to2@mail.com", "to3@mail.com"}, new String[]{"cc1@mail.com", "cc2@mail.com", "cc3@mail.com"}, new String[]{"bcc1@mail.com", "bcc2@mail.com", "bcc3@mail.com"}, null, "mail message");<br>
 * </code> <br>
 * For attachments, you need to bear in mind that each Mail Client may accept a
 * specific type of object to represent the attachment. Review the documentation
 * of the Mail Client that you require and use any of the objects type that it
 * supports. If our <code>sample-client</code> supports
 * <code>java.io.File</code> objects then we can add attachments like this:<br>
 * <br>
 * <code>
 * // Create a list for the attachments.<br> 
 * List&lt;File&gt; attachments = new List&lt;File&gt;();<br>
 * <br>
 * // Add attachments. <br>
 * attachments.addElement(new File("file1.txt"));<br>
 * attachments.addElement(new File("file2.jpg"));<br>
 * attachments.addElement(new File("file3.zip"));<br>
 * <br>
 * // Send an email with attachments.<br> 
 * mailService.send("sample-client", "subject", "from@mail.com", "to1@mail.com", null, null, attachments, "mail message"); <br>
 * </code> <br>
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public interface MailServiceFacade extends ProxyServiceFacade {

	/**
	 * Sends an email with a specific Mail Client.
	 * 
	 * @param clientName  Name of the Mail Client where to send the email. This
	 *                    argument is mandatory.<br>
	 *                    <br>
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
	 * @throws ServiceException If there is an error when trying to send the
	 *                          e-mail.<br>
	 *                          <br>
	 */
	void send(final String clientName, final String subject, final String from, final Object to, final Object cc,
			final Object bcc, final List<?> attachments, final String message) throws ServiceException;

	/**
	 * Sends an email with a message loaded from a Provider. You can use this method
	 * to load templates from a Provider and replace each variable (for example:
	 * Hello ${USER_NAME}, how are you today?) defined in the template.
	 * 
	 * @param clientName   Name of the Mail Client where to send the email. This
	 *                     argument is mandatory.<br>
	 *                     <br>
	 * @param subject      A brief summary of the topic of the message.<br>
	 *                     <br>
	 * @param from         The email address of the sender. In many email clients it
	 *                     is not changeable except through changing account
	 *                     settings.<br>
	 *                     <br>
	 * @param to           List with the email addresses of the message's
	 *                     recipients. You can provide a <code>String</code> object
	 *                     with a single email address or multiple addresses
	 *                     separated by a semicolon. You can also provide an array
	 *                     of <code>String</code> objects or a
	 *                     <code>java.util.List</code> with <code>String</code>
	 *                     objects that represent each email address. This argument
	 *                     is mandatory.<br>
	 *                     <br>
	 * @param cc           Allows you to simultaneously send multiple copies of an
	 *                     email to secondary recipients. It is a list with the
	 *                     email addresses of the message's recipients. You can
	 *                     provide a <code>String</code> object with a single email
	 *                     address or multiple addresses separated by a semicolon.
	 *                     You can also provide an array of <code>String</code>
	 *                     objects or a <code>java.util.List</code> with
	 *                     <code>String</code> objects that represent each email
	 *                     address. This argument is optional.<br>
	 *                     <br>
	 * @param bcc          Addresses added for the delivery list but not (usually)
	 *                     listed in the message data, remaining invisible to other
	 *                     recipients. It is a list with the email addresses of the
	 *                     message's recipients. You can provide a
	 *                     <code>String</code> object with a single email address or
	 *                     multiple addresses separated by a semicolon. You can also
	 *                     provide an array of <code>String</code> objects or a
	 *                     <code>java.util.List</code> with <code>String</code>
	 *                     objects that represent each email address. This argument
	 *                     is optional.<br>
	 *                     <br>
	 * @param attachments  Email messages may have one or more files/resources
	 *                     associated to it. They serve the purpose of delivering
	 *                     binary or text files of unspecified size. For
	 *                     attachments, you need to bear in mind that each Mail
	 *                     Client may accept a specific type of object to represent
	 *                     the attachment. Review the documentation of the Mail
	 *                     Client that you require and use any of the objects type
	 *                     that it supports. This argument is optional.<br>
	 *                     <br>
	 * @param providerName Provider where to retrieve the message.<br>
	 *                     <br>
	 * @param messageName  Name of the message in the Provider.<br>
	 *                     <br>
	 * @param values       Map where the keys represent variable names in the
	 *                     message loaded from the Provider and the values those
	 *                     that will replace the variables. Every variable must be
	 *                     inside '${' and '}' so the variable CAR must be in this
	 *                     message as '${CAR}'. Pass <code>null</code> to this
	 *                     parameter to make no changes in the message loaded.<br>
	 *                     <br>
	 * @throws ServiceException If there is an error when trying to send the
	 *                          e-mail.<br>
	 *                          <br>
	 */
	void send(final String clientName, final String subject, final String from, final Object to, final Object cc,
			final Object bcc, final List<?> attachments, final String providerName, final String messageName,
			final Map<String, Object> values) throws ServiceException;

}
