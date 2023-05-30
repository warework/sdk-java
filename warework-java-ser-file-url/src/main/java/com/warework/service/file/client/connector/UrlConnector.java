package com.warework.service.file.client.connector;

import com.warework.core.service.client.connector.ConnectorException;
import com.warework.core.util.helper.StringL1Helper;
import com.warework.service.file.FileServiceConstants;
import com.warework.service.file.client.UrlClient;
import com.warework.service.log.LogServiceConstants;

/**
 * This Connector holds the information required to create URL Clients.<br>
 * <br>
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public final class UrlConnector extends AbstractFileClientConnector {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Initialization parameter that specifies which protocol to use to point to a
	 * resource on the World Wide Web. This parameter is mandatory and the values it
	 * may accept depend on which Java version you use. Common values are:
	 * <code>http</code>, <code>https</code>, <code>file</code> and
	 * <code>jar</code>.
	 */
	public static final String PARAMETER_PROTOCOL = "protocol";

	/**
	 * Initialization parameter that specifies the name or IP of the host machine.
	 * This parameter is optional and when provided it must be a
	 * <code>String</code>.
	 */
	public static final String PARAMETER_HOST = "host";

	/**
	 * Optional initialization parameter that specifies the port of the host machine
	 * where to connect. Accepted values for this parameter are integer numbers as
	 * <code>java.lang.String</code> or <code>java.lang.Integer</code> objects.
	 */
	public static final String PARAMETER_PORT = "port";

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Protocol to use in the connection.
	private String protocol;

	// Server where to connect.
	private String host;

	// Port of the server where to connect.
	private int port = -1;

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the type of the File Client.
	 * 
	 * @return A <code>com.warework.service.file.client.LocalFileSystemClient</code>
	 *         .<br>
	 *         <br>
	 */
	public Class<UrlClient> getClientType() {
		return UrlClient.class;
	}

	/**
	 * Gets a <code>java.lang.String</code> object that represents the base URL
	 * required to create a connection with the remote resource.
	 * 
	 * @return <code>java.lang.String</code> with the protocol, host and optionally
	 *         the port number.<br>
	 *         <br>
	 */
	public Object getClientConnection() {

		// Create base connection string.
		String connection = protocol + StringL1Helper.CHARACTER_COLON
				+ FileServiceConstants.DIRECTORY_SEPARATOR_UNIX_STYLE
				+ FileServiceConstants.DIRECTORY_SEPARATOR_UNIX_STYLE;

		// Add host to connection string.
		if (host != null) {
			connection = connection + host;
		}

		// Add port to connection string.
		if (port >= 0) {
			connection = connection + StringL1Helper.CHARACTER_COLON + host;
		}

		// Return connection string.
		return connection;

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

		// Get the value of the server parameter.
		if (getInitParameter(PARAMETER_PROTOCOL) == null) {
			throw new ConnectorException(getScopeFacade(),
					"WAREWORK cannot initialize URL Client '" + getClientName() + "' in Service '"
							+ getService().getName() + "' because parameter '" + PARAMETER_PROTOCOL
							+ "' is not specified.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		} else {

			// Get the server parameter.
			final Object targetHost = getInitParameter(PARAMETER_PROTOCOL);

			// Validate the type for server parameter.
			if (targetHost instanceof String) {
				protocol = (String) targetHost;
			} else {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize URL Client '" + getClientName() + "' in Service '"
								+ getService().getName() + "' because given '" + PARAMETER_PROTOCOL
								+ "' parameter is not a String object.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

		}

		// Get the value of the server parameter.
		if (getInitParameter(PARAMETER_HOST) != null) {

			// Get the server parameter.
			final Object targetHost = getInitParameter(PARAMETER_HOST);

			// Validate the type for server parameter.
			if (targetHost instanceof String) {
				host = (String) targetHost;
			} else {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize FTP Client '" + getClientName() + "' in Service '"
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
							"WAREWORK cannot initialize FTP Client '" + getClientName() + "' in Service '"
									+ getService().getName() + "' because given '" + PARAMETER_PORT
									+ "' parameter is not an Integer value.",
							e, LogServiceConstants.LOG_LEVEL_WARN);
				}
			} else if ((targetPort instanceof Byte) || (targetPort instanceof Short)
					|| (targetPort instanceof Integer)) {
				port = ((Number) targetPort).intValue();
			} else {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize FTP Client '" + getClientName() + "' in Service '"
								+ getService().getName() + "' because given '" + PARAMETER_PORT
								+ "' parameter is not a string, Byte, Short or Integer value.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

		}

	}

	/**
	 * Retrieves the connection source.
	 * 
	 * @return Always <code>null</code>.<br>
	 *         <br>
	 */
	protected Object createConnectionSource() {
		return null;
	}

}
