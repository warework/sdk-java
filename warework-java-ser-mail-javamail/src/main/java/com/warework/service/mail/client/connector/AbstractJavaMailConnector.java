package com.warework.service.mail.client.connector;

import java.util.Enumeration;
import java.util.Properties;

import javax.mail.Session;

import com.warework.core.scope.ScopeL1Constants;
import com.warework.core.service.client.connector.ConnectorException;
import com.warework.service.log.LogServiceConstants;

/**
 * Connector that holds general information to create a JavaMail
 * connections.<br>
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public abstract class AbstractJavaMailConnector extends AbstractMailConnector {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// PARAMETERS

	/**
	 * Initialization parameter that specifies a <code>javax.mail.Session</code>
	 * object that holds the information required create mail connections.
	 */
	public static final String PARAMETER_MAIL_SOURCE = "mail-source";

	/**
	 * Initialization parameter that specifies the server where to connect to send
	 * the email.
	 */
	public static final String PARAMETER_HOST = "mail.host";

	/**
	 * Initialization parameter that specifies the port of the server where to
	 * connect to send the email.
	 */
	public static final String PARAMETER_PORT = "mail.port";

	/**
	 * Initialization parameter that specifies the user name required to create a
	 * connection with the mail server.
	 */
	public static final String PARAMETER_USER = "mail.user";

	/**
	 * Initialization parameter that specifies the password required to create a
	 * connection with the mail server.
	 */
	public static final String PARAMETER_PASSWORD = "mail.password";

	/**
	 * Initialization parameter that specifies a string that represents the name of
	 * the Provider where to load an object. This object retrieved from the Provider
	 * will be used as the <u>connection source</u> of the Connector. You must use
	 * this parameter with <code>PARAMETER_CONNECTION_SOURCE_PROVIDER_OBJECT</code>
	 * parameter in order to make it work.
	 */
	public static final String PARAMETER_CONNECTION_SOURCE_PROVIDER_NAME = "connection-source-provider-name";

	/**
	 * Initialization parameter that specifies a string that represents the name of
	 * an object in a Provider that will be used as the <u>connection source</u> of
	 * the Connector. You must use this parameter with
	 * <code>PARAMETER_CONNECTION_SOURCE_PROVIDER_NAME</code> parameter in order to
	 * make it work.
	 */
	public static final String PARAMETER_CONNECTION_SOURCE_PROVIDER_OBJECT = "connection-source-provider-object";

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Server where to connect to send the email.
	private String host;

	// Port of the server where to connect to send the email.
	private int port = -1;

	// User name required to create a connection with the mail server.
	private String user;

	// Password required to create a connection with the mail server.
	private String password;

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Get the server where to connect to send the email.
	 * 
	 * @return Host.<br>
	 *         <br>
	 */
	protected String getHost() {
		return host;
	}

	/**
	 * Get the port of the server where to connect to send the email.
	 * 
	 * @return Port.<br>
	 *         <br>
	 */
	protected int getPort() {
		return port;
	}

	/**
	 * Gets the user name required to create a connection with the mail server.
	 * 
	 * @return Name of the user.<br>
	 *         <br>
	 */
	protected String getUser() {
		return user;
	}

	/**
	 * Get the password required to create a connection with the mail server.
	 * 
	 * @return Password of the user.<br>
	 *         <br>
	 */
	protected String getPassword() {
		return password;
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Initializes the connector.
	 * 
	 * @throws ConnectorException If there is an error when trying to initialize the
	 *                            Client.<br>
	 *                            <br>
	 */
	protected void initialize() throws ConnectorException {

		// Get the value of the server parameter.
		if (getInitParameter(PARAMETER_HOST) != null) {

			// Get the server parameter.
			final Object targetHost = getInitParameter(PARAMETER_HOST);

			// Validate the type for server parameter.
			if (targetHost instanceof String) {
				host = (String) targetHost;
			} else {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize Client '" + getClientName() + "' in Service '"
								+ getService().getName() + "' because given '" + PARAMETER_HOST
								+ "' parameter is not a String object.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

		}

		// Get the value of the port parameter.
		if (getInitParameter(PARAMETER_PORT) != null) {

			// Get the port parameter.
			final Object targetPort = getInitParameter(PARAMETER_PORT);

			// Validate the type for port parameter.
			if (targetPort instanceof String) {
				try {
					port = Integer.parseInt((String) targetPort);
				} catch (final Exception e) {
					throw new ConnectorException(getScopeFacade(),
							"WAREWORK cannot initialize Client '" + getClientName() + "' in Service '"
									+ getService().getName() + "' because given '" + PARAMETER_PORT
									+ "' parameter is not an Integer value.",
							e, LogServiceConstants.LOG_LEVEL_WARN);
				}
			} else if ((targetPort instanceof Byte) || (targetPort instanceof Short)
					|| (targetPort instanceof Integer)) {
				port = ((Number) targetPort).intValue();
			} else {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize Client '" + getClientName() + "' in Service '"
								+ getService().getName() + "' because given '" + PARAMETER_PORT
								+ "' parameter is not a string, Byte, Short or Integer value.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

		}

		// Get the value of the user parameter.
		if ((getInitParameter(PARAMETER_USER) != null) || (getInitParameter(ScopeL1Constants.PARAMETER_USER) != null)) {

			// Get the user parameter.
			Object targetUser = null;
			if (getInitParameter(PARAMETER_USER) != null) {
				targetUser = getInitParameter(PARAMETER_USER);
			} else {
				targetUser = getInitParameter(ScopeL1Constants.PARAMETER_USER);
			}

			// Validate the type for user parameter.
			if (targetUser instanceof String) {
				user = (String) targetUser;
			} else {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize Client '" + getClientName() + "' in Service '"
								+ getService().getName() + "' because given '" + PARAMETER_USER + " or '"
								+ ScopeL1Constants.PARAMETER_USER + "' parameter is not a String object.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

		}

		// Get the value of the password parameter.
		if ((getInitParameter(PARAMETER_PASSWORD) != null)
				|| (getInitParameter(ScopeL1Constants.PARAMETER_PASSWORD) != null)) {

			// Get the password parameter.
			Object targetPassword = null;
			if (getInitParameter(PARAMETER_PASSWORD) != null) {
				targetPassword = getInitParameter(PARAMETER_PASSWORD);
			} else {
				targetPassword = getInitParameter(ScopeL1Constants.PARAMETER_PASSWORD);
			}

			// Validate the type for password parameter.
			if (targetPassword instanceof String) {
				password = (String) targetPassword;
			} else {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize Client '" + getClientName() + "' in Service '"
								+ getService().getName() + "' because given '" + PARAMETER_PASSWORD + " or '"
								+ ScopeL1Constants.PARAMETER_PASSWORD + "' parameter is not a String object.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

		}

	}

	/**
	 * Creates a <code>javax.mail.Session</code> with the information required to
	 * create a connection with the mail server.
	 * 
	 * @return A <code>javax.mail.Session</code> object.<br>
	 *         <br>
	 */
	protected Object createConnectionSource() throws ConnectorException {
		if (getInitParameter(PARAMETER_MAIL_SOURCE) != null) {

			// Retrieve the data source.
			final Object mailSource = getInitParameter(PARAMETER_MAIL_SOURCE);

			// Return the data source.
			if (mailSource instanceof Session) {
				return mailSource;
			} else {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot retrieve the mail source for Client '" + getClientName()
								+ "' because given parameter '" + PARAMETER_MAIL_SOURCE + "' is not a '"
								+ Session.class.getName() + "' object.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

		} else if ((getInitParameter(PARAMETER_CONNECTION_SOURCE_PROVIDER_NAME) != null)
				&& (getInitParameter(PARAMETER_CONNECTION_SOURCE_PROVIDER_OBJECT) != null)) {

			// Get the name of the provider where to retrieve the
			// connection.
			final Object providerName = getInitParameter(PARAMETER_CONNECTION_SOURCE_PROVIDER_NAME);

			// Get the name of the connection object bound to the provider.
			final Object providerObject = getInitParameter(PARAMETER_CONNECTION_SOURCE_PROVIDER_OBJECT);

			// Validate that given parameters are strings and return the
			// connection.
			if ((providerName instanceof String) && (providerObject instanceof String)) {

				// Get the connection from the Provider.
				final Object connection = getScopeFacade().getObject((String) providerName, (String) providerObject);

				// Return the connection.
				if (connection instanceof Session) {
					return (Session) connection;
				} else {
					throw new ConnectorException(getScopeFacade(),
							"WAREWORK cannot retrieve the mail source for Client '" + getClientName()
									+ "' because Provider '" + ((String) providerName) + "' does not returns a '"
									+ Session.class.getName() + "' object for '" + ((String) providerObject)
									+ "' or it is null.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}

			} else {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot retrieve the connection for mail client '" + getClientName()
								+ "' because one or both of the connector's parameters are not '"
								+ String.class.getName() + "' objects.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

		} else {

			// Create the configuration for the mail session.
			final Properties configuration = new Properties();

			// Get the name of every initialization parameter.
			final Enumeration<String> initParameterNames = getInitParameterNames();

			// Add each string-value parameter in the configuration.
			if (initParameterNames != null) {
				while (initParameterNames.hasMoreElements()) {

					// Get the name of one parameter.
					final String initParameterName = (String) initParameterNames.nextElement();

					// Get the value of the parameter.
					final Object initParameterValue = getInitParameter(initParameterName);
					if (initParameterValue instanceof String) {
						configuration.put(initParameterName, (String) initParameterValue);
					}

				}
			}

			// Create the mail source.
			return Session.getDefaultInstance(configuration);

		}
	}

}
