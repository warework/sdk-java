package com.warework.service.file.client.connector;

import java.util.Properties;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.warework.core.scope.ScopeL1Constants;
import com.warework.core.service.client.connector.ConnectorException;
import com.warework.core.util.CommonValueL1Constants;
import com.warework.service.file.client.SftpClient;
import com.warework.service.log.LogServiceConstants;

/**
 * This Connector holds the information required to create SSFTP Clients.<br>
 * <br>
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public final class SftpConnector extends AbstractFileClientConnector {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Default SFTP server port.
	 */
	public static final int DEFAULT_PORT = 22;

	/**
	 * Mandatory initialization parameter to specify the FTP server where to
	 * connect. This parameter must be a <code>String</code>.
	 */
	public static final String PARAMETER_HOST = "host";

	/**
	 * Optional initialization parameter that specifies the port of the server where
	 * to connect. If this parameter is not specified then default port value is 21.
	 * Accepted values for this parameter are integer numbers as
	 * <code>java.lang.String</code> or <code>java.lang.Integer</code> objects.
	 */
	public static final String PARAMETER_PORT = "port";

	/**
	 * Optional initialization parameter that specifies the user name to create a
	 * connection with the FTP server.
	 */
	public static final String PARAMETER_USER = ScopeL1Constants.PARAMETER_USER;

	/**
	 * Optional initialization parameter that specifies the password to create a
	 * connection with the FTP server.
	 */
	public static final String PARAMETER_PASSWORD = ScopeL1Constants.PARAMETER_PASSWORD;

	/**
	 * Optional initialization parameter that specifies the file type to use in the
	 * connection with the FTP server. Accepted values are defined in this class
	 * with the <code>FILE_TYPE_xxx</code> constants.
	 */
	public static final String PARAMETER_STRICT_HOST_KEY_CHECKING = "strict-host-key-checking";

	/**
	 * Optional initialization parameter that specifies the path (example:
	 * '/home/ubuntu_user/.ssh/known_hosts') to the known hosts file to help SFTP
	 * Client create the connection with the SFTP server.
	 */
	public static final String PARAMETER_KNOWN_HOSTS = "known-hosts";

	// ///////////////////////////////////////////////////////////////////

	// CONFIG PROPERTIES

	private static final String CONFIG_PROPERTY_STRICT_HOST_KEY_CHECKING = "StrictHostKeyChecking";

	//

	private static final String STRICT_HOST_KEY_CHECKING_ENABLED = "yes";

	private static final String STRICT_HOST_KEY_CHECKING_DISABLED = "no";

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Server where to connect.
	private String host;

	// Port of the server where to connect.
	private int port = DEFAULT_PORT;

	// User name required to create a connection with the SFTP server.
	private String user;

	// Password required to create a connection with the SFTP server.
	private String password;

	// Path to known hosts file. Example: /home/ubuntu_user/.ssh/known_hosts
	private String knownHosts;

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the type of the File Client.
	 * 
	 * @return A <code>com.warework.service.file.client.SftpClient</code>.<br>
	 *         <br>
	 */
	public Class<SftpClient> getClientType() {
		return SftpClient.class;
	}

	/**
	 * Gets a connection with the FTP server.
	 * 
	 * @return A <code>org.apache.commons.net.ftp.FTPClient</code> instance.<br>
	 *         <br>
	 * @throws ConnectorException If there is an error when trying to create the
	 *                            connection with the FTP server.<br>
	 *                            <br>
	 */
	public Object getClientConnection() throws ConnectorException {
		try {

			// Get the FTP connection source.
			final JSch connection = new JSch();

			//
			if (knownHosts != null) {
				connection.setKnownHosts(knownHosts);
			}

			// Create SSH session.
			final Session session = connection.getSession(user, host, port);

			// Set password.
			session.setPassword(password);

			// Setup config.
			session.setConfig((Properties) getConnectionSource());

			//
			session.connect();

			//
			final ChannelSftp channel = (ChannelSftp) session.openChannel("sftp");

			//
			channel.connect();

			//
			return channel;

		} catch (final JSchException e) {
			throw new ConnectorException(getScopeFacade(),
					"WAREWORK cannot connect SFTP Client '" + getClientName() + "' in Service '"
							+ getService().getName() + "' because the following exception is thrown: " + e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}
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
		if (getInitParameter(PARAMETER_HOST) == null) {
			throw new ConnectorException(getScopeFacade(),
					"WAREWORK cannot initialize SFTP Client '" + getClientName() + "' in Service '"
							+ getService().getName() + "' because parameter '" + PARAMETER_HOST + "' is not specified.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		} else {

			// Get the server parameter.
			final Object targetHost = getInitParameter(PARAMETER_HOST);

			// Validate the type for server parameter.
			if (targetHost instanceof String) {
				host = (String) targetHost;
			} else {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize SFTP Client '" + getClientName() + "' in Service '"
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
							"WAREWORK cannot initialize SFTP Client '" + getClientName() + "' in Service '"
									+ getService().getName() + "' because given '" + PARAMETER_PORT
									+ "' parameter is not an Integer value.",
							e, LogServiceConstants.LOG_LEVEL_WARN);
				}
			} else if ((targetPort instanceof Byte) || (targetPort instanceof Short)
					|| (targetPort instanceof Integer)) {
				port = ((Number) targetPort).intValue();
			} else {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize SFTP Client '" + getClientName() + "' in Service '"
								+ getService().getName() + "' because given '" + PARAMETER_PORT
								+ "' parameter is not a string, Byte, Short or Integer value.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

		}

		// Get the value of the user parameter.
		if (getInitParameter(PARAMETER_USER) != null) {

			// Get the user parameter.
			final Object targetUser = getInitParameter(PARAMETER_USER);

			// Validate the type for user parameter.
			if (targetUser instanceof String) {
				user = (String) targetUser;
			} else {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize SFTP Client '" + getClientName() + "' in Service '"
								+ getService().getName() + "' because given '" + PARAMETER_USER
								+ "' parameter is not a String object.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

		}

		// Get the value of the password parameter.
		if (getInitParameter(PARAMETER_PASSWORD) != null) {

			// Get the password parameter.
			final Object targetPassword = getInitParameter(PARAMETER_PASSWORD);

			// Validate the type for password parameter.
			if (targetPassword instanceof String) {
				password = (String) targetPassword;
			} else {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize SFTP Client '" + getClientName() + "' in Service '"
								+ getService().getName() + "' because given '" + PARAMETER_PASSWORD
								+ "' parameter is not a String object.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

		}

		// Get the value of the known hosts parameter.
		if (getInitParameter(PARAMETER_KNOWN_HOSTS) != null) {

			// Get the known hosts parameter.
			final Object targetKnownHosts = getInitParameter(PARAMETER_KNOWN_HOSTS);

			// Validate the type for known hosts parameter.
			if (targetKnownHosts instanceof String) {
				knownHosts = (String) targetKnownHosts;
			} else {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize SFTP Client '" + getClientName() + "' in Service '"
								+ getService().getName() + "' because given '" + PARAMETER_KNOWN_HOSTS
								+ "' parameter is not a String object.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

		}

	}

	/**
	 * Retrieves the connection source.
	 * 
	 * @return A new instance of <code>com.jcraft.jsch.JSch</code>.<br>
	 *         <br>
	 */
	protected Object createConnectionSource() throws ConnectorException {

		// Create connection config.
		final Properties config = new Properties();

		// Get the value of the file type parameter.
		if (getInitParameter(PARAMETER_STRICT_HOST_KEY_CHECKING) != null) {

			// Get the parameter value.
			final Object targetStrictHostKeyChecking = getInitParameter(PARAMETER_STRICT_HOST_KEY_CHECKING);

			// Validate the type of the parameter.
			if (targetStrictHostKeyChecking instanceof String) {

				// Get the specific value type of the parameter.
				final String strictHostKeyChecking = (String) targetStrictHostKeyChecking;

				// Validate parameter value.
				if (strictHostKeyChecking.trim().equalsIgnoreCase(CommonValueL1Constants.STRING_TRUE)) {
					config.put(CONFIG_PROPERTY_STRICT_HOST_KEY_CHECKING, STRICT_HOST_KEY_CHECKING_ENABLED);
				} else if (strictHostKeyChecking.trim().equalsIgnoreCase(CommonValueL1Constants.STRING_FALSE)) {
					config.put(CONFIG_PROPERTY_STRICT_HOST_KEY_CHECKING, STRICT_HOST_KEY_CHECKING_DISABLED);
				} else {
					throw new ConnectorException(getScopeFacade(),
							"WAREWORK cannot initialize SFTP Client '" + getClientName() + "' in Service '"
									+ getService().getName() + "' because given '" + PARAMETER_STRICT_HOST_KEY_CHECKING
									+ "' parameter has an invalid value.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}

			} else if (targetStrictHostKeyChecking instanceof Boolean) {
				if ((Boolean) targetStrictHostKeyChecking) {
					config.put(CONFIG_PROPERTY_STRICT_HOST_KEY_CHECKING, STRICT_HOST_KEY_CHECKING_ENABLED);
				} else {
					config.put(CONFIG_PROPERTY_STRICT_HOST_KEY_CHECKING, STRICT_HOST_KEY_CHECKING_DISABLED);
				}
			} else {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize SFTP Client '" + getClientName() + "' in Service '"
								+ getService().getName() + "' because given '" + PARAMETER_STRICT_HOST_KEY_CHECKING
								+ "' parameter is not a Boolean or a String object.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

		}

		// Return connection config.
		return config;

	}

}
