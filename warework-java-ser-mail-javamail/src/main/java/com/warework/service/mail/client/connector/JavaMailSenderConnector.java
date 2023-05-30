package com.warework.service.mail.client.connector;

import javax.mail.Session;
import javax.mail.Transport;

import com.warework.core.service.client.connector.ConnectorException;
import com.warework.service.log.LogServiceConstants;
import com.warework.service.mail.client.JavaMailSender;

/**
 * Connector that holds the required information to create a connection with a
 * mail server using JavaMail.<br>
 * <br>
 * To add a JavaMail Client into the Mail Service you have to invoke method
 * <code>createClient()</code> that exists in its Facade with a name, a JavaMail
 * Connector class and a configuration for the Client.<br>
 * <br>
 * You can use the following initialization parameters to configure this
 * Connector:<br>
 * <br>
 * <ul>
 * <li><code>PARAMETER_HOST</code>: server where to connect to send the
 * email.</li>
 * <li><code>PARAMETER_PORT</code>: port of the server where to connect to send
 * the email.</li>
 * <li><code>PARAMETER_USER</code>: user name required to create a connection
 * with the mail server.</li>
 * <li><code>PARAMETER_PASSWORD</code>: user password required to create a
 * connection with the mail server.</li>
 * <li><code>PARAMETER_TRANSPORT_PROTOCOL</code>: protocol used to send the
 * email, like SMTP.</li>
 * </ul>
 * These are common parameters that you may require for your mail connections.
 * They are not mandatory and there is also the possibility to specify more
 * initialization parameters without the constants of a Connector. Check this
 * out with the following example. It shows how to setup the Client to send
 * emails with GMail:<br>
 * <br>
 * <code>
 * // Create the configuration for the Mail Client.<br>
 * Map&lt;String, Object&gt; config = new HashMap&lt;String, Object&gt;();<br>
 * <br>
 * // Set the mail server configuration.<br>
 * config.put(JavaMailSenderConnector.PARAMETER_HOST, "smtp.gmail.com");<br>
 * config.put(JavaMailSenderConnector.PARAMETER_PORT, "587");<br>
 * config.put(JavaMailSenderConnector.PARAMETER_USER, "abc@gmail.com");<br>
 * config.put(JavaMailSenderConnector.PARAMETER_PASSWORD, "123");<br>
 * config.put(JavaMailSenderConnector.PARAMETER_TRANSPORT_PROTOCOL, "smtp");<br>
 * config.put("mail.smtp.auth", "true");<br>
 * config.put("mail.smtp.starttls.enable", "true");<br>
 * <br>
 * // Create the JavaMail Client.<br>
 * mailService.createClient("javamail-client", JavaMailSenderConnector.class, config);<br> 
 * </code> <br>
 * You can also configure the JavaMail Client to connect with an mail server via
 * JNDI. In many application servers you can store the information required to
 * create connections with a mail server. This information is accessible with a
 * JNDI Provider and it can be used by Warework to connect with the mail server.
 * To achieve this you have to specify at least two initialization
 * parameters:<br>
 * <br>
 * <ul>
 * <li><code>PARAMETER_CONNECTION_SOURCE_PROVIDER_NAME</code>: name of the
 * Provider, typically a JNDI Provider, that retrieves the mail connection
 * information from an application server.</li>
 * <li><code>PARAMETER_CONNECTION_SOURCE_PROVIDER_OBJECT</code>: name used to
 * get a <code>javax.mail.Session</code> object. This is the object that the
 * application server provides and it represents the mail connection
 * information.</li>
 * </ul>
 * The following example shows you how to use these two parameters plus another
 * two to complement the information locally:<br>
 * <br>
 * <code>
 * // Create the configuration for the Mail Client.<br>
 * Map&lt;String, Object&gt; config = new HashMap&lt;String, Object&gt;();<br>
 * <br>
 * // Set the mail server configuration.<br>
 * config.put(JavaMailSenderConnector.PARAMETER_CONNECTION_SOURCE_PROVIDER_NAME, "jndi-provider");<br>
 * config.put(JavaMailSenderConnector.PARAMETER_CONNECTION_SOURCE_PROVIDER_OBJECT, "mail/myApp");<br>
 * config.put(JavaMailSenderConnector.PARAMETER_USER, "abc@gmail.com");<br>
 * config.put(JavaMailSenderConnector.PARAMETER_PASSWORD, "123");<br>
 * <br>
 * // Create the JavaMail Client.<br>
 * mailService.createClient("javamail-client", JavaMailSenderConnector.class, config);<br> 
 * </code> <br>
 * <b><u>Minimum prerequisites to run this Connector</u>:</b><br>
 * <br>
 * <ul>
 * <li><b>Runtime:</b> Java 1.5</li>
 * <li><b>Libraries:</b>
 * <ul>
 * <li>JavaBeans Activation Framework version 1.1 (or later)</li>
 * <li>JavaMail version 1.4 (or later)</li>
 * </ul>
 * </li>
 * </ul>
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public final class JavaMailSenderConnector extends AbstractJavaMailConnector {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// PARAMETERS

	/**
	 * Initialization parameter that specifies the protocol used to send the email.
	 * For instance: smtp.
	 */
	public static final String PARAMETER_TRANSPORT_PROTOCOL = "mail.transport.protocol";

	/**
	 * Initialization parameter that specifies the charset to use for the text of
	 * the mail. The given Unicode string that represents the text will be
	 * charset-encoded using the specified charset. For instance: utf-8, ISO-8859-1,
	 * etc.
	 */
	public static final String PARAMETER_MIME_MESSAGE_CHARSET = "mail.message.charset";

	/**
	 * Initialization parameter that specifies the MIME subtype to use for the
	 * message. For instance: html. The default value for this parameter is
	 * <code>plain</code>.
	 */
	public static final String PARAMETER_MIME_MESSAGE_SUBTYPE = "mail.message.subtype";

	/**
	 * Initialization parameter that specifies a string that represents the name of
	 * the Provider where to load an object. This object retrieved from the Provider
	 * will be used as the <u>Client connection</u> of the Connector. You must use
	 * this parameter with <code>PARAMETER_CLIENT_CONNECTION_PROVIDER_OBJECT</code>
	 * parameter in order to make it work.
	 */
	public static final String PARAMETER_CLIENT_CONNECTION_PROVIDER_NAME = "client-connection-provider-name";

	/**
	 * Initialization parameter that specifies a string that represents the name of
	 * an object in a Provider that will be used as the <u>Client connection</u> of
	 * the Connector. You must use this parameter with
	 * <code>PARAMETER_CLIENT_CONNECTION_PROVIDER_NAME</code> parameter in order to
	 * make it work.
	 */
	public static final String PARAMETER_CLIENT_CONNECTION_PROVIDER_OBJECT = "client-connection-provider-object";

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Protocol used to send the email.
	private String protocol;

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the type of the client.
	 * 
	 * @return A <code>com.warework.service.mail.client.JavaMailSender</code>
	 *         client.<br>
	 *         <br>
	 */
	public Class<JavaMailSender> getClientType() {
		return JavaMailSender.class;
	}

	/**
	 * Creates a <code>javax.mail.Transport</code> which allows to send emails.
	 * 
	 * @return A <code>javax.mail.Transport</code>.<br>
	 *         <br>
	 * @throws ConnectorException If there is an error when trying to get the
	 *                            connection.<br>
	 *                            <br>
	 */
	public Object getClientConnection() throws ConnectorException {

		// Get the mail transport layer.
		Transport transport = null;
		if ((getInitParameter(PARAMETER_CLIENT_CONNECTION_PROVIDER_NAME) != null)
				&& (getInitParameter(PARAMETER_CLIENT_CONNECTION_PROVIDER_OBJECT) != null)) {

			// Get the name of the Provider where to retrieve the
			// connection.
			final Object providerName = getInitParameter(PARAMETER_CLIENT_CONNECTION_PROVIDER_NAME);

			// Get the name of the connection object bound to the Provider.
			final Object providerObject = getInitParameter(PARAMETER_CLIENT_CONNECTION_PROVIDER_OBJECT);

			// Validate that given parameters are strings and return the
			// connection.
			if ((providerName instanceof String) && (providerObject instanceof String)) {

				// Get the connection from the provider.
				final Object connection = getScopeFacade().getObject((String) providerName, (String) providerObject);

				// Return the connection.
				if (connection instanceof Transport) {
					transport = (Transport) connection;
				} else {
					throw new ConnectorException(getScopeFacade(),
							"WAREWORK cannot retrieve a connection for Client '" + getClientName()
									+ "' because Provider '" + ((String) providerName) + "' does not returns a '"
									+ Transport.class.getName() + "' object for '" + ((String) providerObject)
									+ "' or it is null.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}

			} else {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot retrieve a connection for Client '" + getClientName()
								+ "' because one or both of the connector's parameters are not '"
								+ String.class.getName() + "' objects.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

		} else {

			// Get the mail connection source.
			final Session mailSession = (Session) getConnectionSource();

			// Create the connection.
			try {
				if (protocol == null) {
					transport = mailSession.getTransport();
				} else {
					transport = mailSession.getTransport(protocol);
				}
			} catch (final Exception e) {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot retrieve a connection from Client '" + getClientName()
								+ "' because the mail session object reported the following error: " + e.getMessage(),
						e, LogServiceConstants.LOG_LEVEL_WARN);
			}

		}

		// Connect with the server.
		try {
			if ((getHost() != null) && (getPort() >= 0) && (getUser() != null) && (getPassword() != null)) {
				transport.connect(getHost(), getPort(), getUser(), getPassword());
			} else if ((getHost() != null) && (getUser() != null) && (getPassword() != null)) {
				transport.connect(getHost(), getUser(), getPassword());
			} else if ((getUser() != null) && (getPassword() != null)) {
				transport.connect(getUser(), getPassword());
			} else {
				transport.connect();
			}
		} catch (final Exception e) {
			throw new ConnectorException(getScopeFacade(),
					"WAREWORK cannot retrieve a connection from Client '" + getClientName()
							+ "' because the mail transport object reported the following error: " + e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Return the connection.
		return transport;

	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Initializes the connector.
	 * 
	 * @throws ConnectorException If there is an error when trying to initialize the
	 *                            Client.<br>
	 *                            <br>
	 */
	protected void initialize() throws ConnectorException {

		// Initialize parent connector.
		super.initialize();

		// Get the value of the protocol parameter.
		if (getInitParameter(PARAMETER_TRANSPORT_PROTOCOL) != null) {

			// Get the protocol parameter.
			final Object targetProtocol = getInitParameter(PARAMETER_TRANSPORT_PROTOCOL);

			// Validate the type for protocol parameter.
			if (targetProtocol instanceof String) {
				protocol = (String) targetProtocol;
			} else {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize Client '" + getClientName() + "' in Service '"
								+ getService().getName() + "' because given '" + PARAMETER_TRANSPORT_PROTOCOL
								+ "' parameter is not a String object.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

		}

	}

}
