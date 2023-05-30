package com.warework.service.file.client.connector;

import java.io.IOException;
import java.net.InetAddress;

import org.apache.commons.net.ProtocolCommandEvent;
import org.apache.commons.net.ProtocolCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import com.warework.core.scope.ScopeFacade;
import com.warework.core.scope.ScopeL1Constants;
import com.warework.core.service.ServiceException;
import com.warework.core.service.client.connector.ConnectorException;
import com.warework.core.util.CommonValueL1Constants;
import com.warework.core.util.helper.StringL1Helper;
import com.warework.service.file.client.AbstractFileClient;
import com.warework.service.file.client.FtpClient;
import com.warework.service.log.LogServiceConstants;
import com.warework.service.log.LogServiceFacade;

/**
 * This Connector holds the information required to create FTP Clients.<br>
 * <br>
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public final class FtpConnector extends AbstractFileClientConnector {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

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
	public static final String PARAMETER_FILE_TYPE = "file-type";

	/**
	 * Optional initialization parameter that specifies the transfer mode to use in
	 * the connection with the FTP server. Accepted values are defined in this class
	 * with the <code>TRANSFER_MODE_xxx</code> constants.
	 */
	public static final String PARAMETER_TRANSFER_MODE = "transfer-mode";

	/**
	 * Optional initialization parameter that specifies to debug FTP command
	 * messages with the Log Service. Accepted values for this parameter are
	 * <code>true</code> or <code>false</code> (as <code>java.lang.String</code> or
	 * <code>java.lang.Boolean</code> objects). Default value is <code>false</code>.
	 */
	public static final String PARAMETER_DEBUG_COMMANDS = "debug-commands";

	/**
	 * Optional initialization parameter that specifies the name of the Log Service
	 * to use to debug the FTP command messages.
	 */
	public static final String PARAMETER_DEBUG_COMMANDS_LOG_SERVICE = "debug-commands-log-service";

	/**
	 * Optional initialization parameter that specifies the name of the Client in
	 * the Log Service where to debug the FTP command messages.
	 */
	public static final String PARAMETER_DEBUG_COMMANDS_LOG_CLIENT = "debug-commands-log-client";

	/**
	 * Optional initialization parameter that specifies whether to use EPSV with
	 * IPv4. Might be worth enabling in some circumstances.For example, when using
	 * IPv4 with NAT it may work with some rare configurations.E.g. if FTP server
	 * has a static PASV address (external network) and the client is coming from
	 * another internal network. In that case the data connection after PASV command
	 * would fail, while EPSV would make the client succeed by taking just the
	 * port.<br>
	 * <br>
	 * Accepted values for this parameter are <code>true</code> or
	 * <code>false</code> (as <code>java.lang.String</code> or
	 * <code>java.lang.Boolean</code> objects). Default value is <code>false</code>.
	 */
	public static final String PARAMETER_USE_EPSV_WITH_IPV4 = "use-epsv-with-ipv4";

	/**
	 * Optional initialization parameter that specifies the local mode to use in the
	 * connection with the FTP server. Accepted values are defined in this class
	 * with the <code>MODE_xxx</code> constants.
	 */
	public static final String PARAMETER_LOCAL_MODE = "local-mode";

	/**
	 * Optional initialization parameter that specifies the remote mode to use in
	 * the connection with the FTP server. Accepted values are defined in this class
	 * with the <code>MODE_xxx</code> constants.
	 */
	public static final String PARAMETER_REMOTE_MODE = "remote-mode";

	/**
	 * 
	 */
	public static final String PARAMETER_REMOTE_ACTIVE_HOST = "remote-active-host";

	/**
	 * 
	 */
	public static final String PARAMETER_REMOTE_ACTIVE_PORT = "remote-active-port";

	/**
	 * Optional initialization parameter that specifies the character used to
	 * separate directories. Default value for this parameter is '/'.
	 */
	public static final String PARAMETER_DIRECTORY_CHARACTER_SEPARATOR = AbstractFileClient.CLIENT_INFO_DIRECTORY_CHARACTER_SEPARATOR;

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public static final String FILE_TYPE_ASCII = "ascii";

	/**
	 * 
	 */
	public static final String FILE_TYPE_BINARY = "binary";

	/**
	 * 
	 */
	public static final String FILE_TYPE_LOCAL = "local";

	/**
	 * 
	 */
	public static final String FILE_TYPE_EBCDIC = "ebcdic";

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public static final String TRANSFER_MODE_BLOCK = "block";

	/**
	 * 
	 */
	public static final String TRANSFER_MODE_COMPRESSED = "compressed";

	/**
	 * 
	 */
	public static final String TRANSFER_MODE_STREAM = "stream";

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public static final String MODE_PASSIVE = "passive";

	/**
	 * 
	 */
	public static final String MODE_ACTIVE = "active";

	// ///////////////////////////////////////////////////////////////////
	// INNER CLASSES
	// ///////////////////////////////////////////////////////////////////

	/**
	 * FTP command listener to log messages with the Log Service.<br>
	 * <br>
	 * 
	 * @author Jose Schiaffino
	 * @version 3.0.0
	 */
	public final class LogClientCommandListener implements ProtocolCommandListener {

		// ///////////////////////////////////////////////////////////////
		// ATTRIBUTES
		// ///////////////////////////////////////////////////////////////

		// Scope where FTP Client belongs to.
		private ScopeFacade scope;

		// Name of the Log Service where to log the messages.
		private String serviceName;

		// Name of the Client in the Log Service where to log the messages.
		private String clientName;

		// ///////////////////////////////////////////////////////////////
		// PUBLIC METHODS
		// ///////////////////////////////////////////////////////////////

		/**
		 * 
		 */
		private LogClientCommandListener() {
			// DO NOTHING.
		}

		/**
		 * Setup an FTP command listener to log messages with the default Client of the
		 * default Log Service.
		 * 
		 * @param connector FTP Client Connector.<br>
		 *                  <br>
		 */
		private LogClientCommandListener(final AbstractFileClientConnector connector) {

			// Invoke default constructor.
			this();

			// Set FTP Client Scope.
			this.scope = connector.getService().getScopeFacade();

		}

		/**
		 * Setup an FTP command listener to log messages with a specific Client in a Log
		 * Service.
		 * 
		 * @param connector   FTP Client Connector.<br>
		 *                    <br>
		 * @param serviceName Name of the Log Service where to log the command
		 *                    message.<br>
		 *                    <br>
		 * @param clientName  Name of the Client in the Log Service where to log the
		 *                    command message.<br>
		 *                    <br>
		 */
		private LogClientCommandListener(final AbstractFileClientConnector connector, final String serviceName,
				final String clientName) {

			// Invoke constructor.
			this(connector);

			// Set Log Service name.
			this.serviceName = serviceName;

			// Set Log Client name.
			this.clientName = clientName;

		}

		// ///////////////////////////////////////////////////////////////
		// PUBLIC METHODS
		// ///////////////////////////////////////////////////////////////

		/**
		 * Handles protocol command sent event.
		 * 
		 * @param event Event that holds the FTP operation performed.<br>
		 *              <br>
		 */
		public void protocolCommandSent(final ProtocolCommandEvent event) {
			handleEvent(event, "COMMAND SENT");
		}

		/**
		 * Handles protocol reply received event.
		 * 
		 * @param event Event that holds the FTP operation performed.<br>
		 *              <br>
		 */
		public void protocolReplyReceived(final ProtocolCommandEvent event) {
			handleEvent(event, "REPLY RECEIVED");
		}

		// ///////////////////////////////////////////////////////////////
		// PRIVATE METHODS
		// ///////////////////////////////////////////////////////////////

		/**
		 * Gets the Log Service.
		 * 
		 * @return Log Service.<br>
		 *         <br>
		 */
		private LogServiceFacade getLogService() {
			return (serviceName == null) ? (LogServiceFacade) scope.getService(LogServiceConstants.DEFAULT_SERVICE_NAME)
					: (LogServiceFacade) scope.getService(serviceName);
		}

		/**
		 * Log FTP command message.
		 * 
		 * @param event Event that holds the FTP operation performed.<br>
		 *              <br>
		 * @param type  Event type.<br>
		 *              <br>
		 */
		private void handleEvent(final ProtocolCommandEvent event, final String type) {

			// Get the Log Service.
			final LogServiceFacade service = getLogService();

			// Log message.
			if (clientName == null) {

				// Validate Client is connected.
				if (!service.isConnected(LogServiceConstants.DEFAULT_CLIENT_NAME)) {
					try {
						service.connect(LogServiceConstants.DEFAULT_CLIENT_NAME);
					} catch (final ServiceException e) {
						return;
					}
				}

				// Log message.
				service.debug(LogServiceConstants.DEFAULT_CLIENT_NAME, type + StringL1Helper.CHARACTER_SPACE
						+ StringL1Helper.CHARACTER_HYPHEN + StringL1Helper.CHARACTER_SPACE + event.getMessage().trim());

			} else {

				// Validate Client is connected.
				if (!service.isConnected(clientName)) {
					try {
						service.connect(clientName);
					} catch (final ServiceException e) {
						return;
					}
				}

				// Log message.
				service.debug(clientName, type + StringL1Helper.CHARACTER_SPACE + StringL1Helper.CHARACTER_HYPHEN
						+ StringL1Helper.CHARACTER_SPACE + event.getMessage().trim());

			}

		}

	}

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Server where to connect.
	private String host;

	// Port of the server where to connect.
	private int port = FTP.DEFAULT_PORT;

	// User name required to create a connection with the FTP server.
	private String user;

	// Password required to create a connection with the FTP server.
	private String password;

	// File type for the FTP connection.
	private int fileType = -1;

	// Transfer mode for the FTP connection.
	private int transferMode = -1;

	// Flag to debug commands with the log service.
	private boolean debugCommands = false;

	// Name of the log service and client.
	private String debugCommandsLogService, debugCommandsLogClient;

	// Flag to use EPSV with IPv4.
	private boolean useEPSVwithIPv4 = false;

	//
	private String localMode, remoteMode;

	//
	private String remoteActiveHost;

	//
	private int remoteActivePort = -1;

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the type of the File Client.
	 * 
	 * @return A <code>com.warework.service.file.client.FtpClient</code>.<br>
	 *         <br>
	 */
	public Class<FtpClient> getClientType() {
		return FtpClient.class;
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
			final FTPClient connection = (FTPClient) getConnectionSource();

			// Log commands sent/recieved from/to the server.
			if (debugCommands) {
				connection.addProtocolCommandListener(
						new LogClientCommandListener(this, debugCommandsLogService, debugCommandsLogClient));
			}

			// Connect with FTP server.
			connection.connect(host, port);

			// Perform login.
			if (!connection.login(user, password)) {

				// Close connection.
				connection.disconnect();

				// Notify invalid login.
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot log into FTP Client '" + getClientName() + "' in Service '"
								+ getService().getName() + "' because the given credentials are invalid.",
						null, LogServiceConstants.LOG_LEVEL_WARN);

			}

			// Validate connection.
			if (!FTPReply.isPositiveCompletion(connection.getReplyCode())) {

				// Close connection.
				connection.disconnect();

				// Notify invalid connection.
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot complete connection with FTP Client '" + getClientName() + "' in Service '"
								+ getService().getName() + "' (server response code is '" + connection.getReplyCode()
								+ "').",
						null, LogServiceConstants.LOG_LEVEL_WARN);

			}

			// Configure local mode.
			if (localMode != null) {
				if (localMode.equals(MODE_ACTIVE)) {
					connection.enterLocalActiveMode();
				} else {
					connection.enterLocalPassiveMode();
				}
			}

			// Configure remote mode.
			if (remoteMode != null) {
				if (remoteMode.equals(MODE_ACTIVE)) {
					connection.enterRemoteActiveMode(InetAddress.getByName(remoteActiveHost), remoteActivePort);
				} else {
					connection.enterRemotePassiveMode();
				}
			}

			// Configure connection file type.
			if (fileType != -1) {
				connection.setFileType(fileType);
			}

			// Configure connection transfer mode.
			if (transferMode != -1) {
				connection.setFileTransferMode(transferMode);
			}

			// Enable or disable EPSV with IPv4.
			connection.setUseEPSVwithIPv4(useEPSVwithIPv4);

			// Return connection.
			return connection;

		} catch (final IOException e) {
			throw new ConnectorException(getScopeFacade(),
					"WAREWORK cannot connect FTP Client '" + getClientName() + "' in Service '" + getService().getName()
							+ "' because the following exception is thrown: " + e.getMessage(),
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
					"WAREWORK cannot initialize FTP Client '" + getClientName() + "' in Service '"
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
								+ "' parameter is not a String, Byte, Short or Integer value.",
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
						"WAREWORK cannot initialize FTP Client '" + getClientName() + "' in Service '"
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
						"WAREWORK cannot initialize FTP Client '" + getClientName() + "' in Service '"
								+ getService().getName() + "' because given '" + PARAMETER_PASSWORD
								+ "' parameter is not a String object.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

		}

		// Get the value of the file type parameter.
		if (getInitParameter(PARAMETER_FILE_TYPE) != null) {

			// Get the file type parameter.
			final Object targetFileType = getInitParameter(PARAMETER_FILE_TYPE);

			// Validate the type for file type parameter.
			if (targetFileType instanceof String) {

				// Get the string value of the file type.
				final String fileType = (String) targetFileType;

				// Validate file type.
				if (fileType.trim().equalsIgnoreCase(FILE_TYPE_ASCII)) {
					this.fileType = FTP.ASCII_FILE_TYPE;
				} else if (fileType.trim().equalsIgnoreCase(FILE_TYPE_BINARY)) {
					this.fileType = FTP.BINARY_FILE_TYPE;
				} else if (fileType.trim().equalsIgnoreCase(FILE_TYPE_LOCAL)) {
					this.fileType = FTP.LOCAL_FILE_TYPE;
				} else if (fileType.trim().equalsIgnoreCase(FILE_TYPE_EBCDIC)) {
					this.fileType = FTP.EBCDIC_FILE_TYPE;
				} else {
					throw new ConnectorException(getScopeFacade(),
							"WAREWORK cannot initialize FTP Client '" + getClientName() + "' in Service '"
									+ getService().getName() + "' because given '" + PARAMETER_FILE_TYPE
									+ "' parameter has an invalid value.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}

			} else {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize FTP Client '" + getClientName() + "' in Service '"
								+ getService().getName() + "' because given '" + PARAMETER_FILE_TYPE
								+ "' parameter is not a String object.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

		}

		// Get the value of the transfer mode parameter.
		if (getInitParameter(PARAMETER_TRANSFER_MODE) != null) {

			// Get the transfer mode parameter.
			final Object targetFileType = getInitParameter(PARAMETER_TRANSFER_MODE);

			// Validate the type for transfer mode parameter.
			if (targetFileType instanceof String) {

				// Get the string value of the transfer mode.
				final String fileType = (String) targetFileType;

				// Validate transfer mode.
				if (fileType.trim().equalsIgnoreCase(TRANSFER_MODE_BLOCK)) {
					this.transferMode = FTP.BLOCK_TRANSFER_MODE;
				} else if (fileType.trim().equalsIgnoreCase(TRANSFER_MODE_COMPRESSED)) {
					this.transferMode = FTP.COMPRESSED_TRANSFER_MODE;
				} else if (fileType.trim().equalsIgnoreCase(TRANSFER_MODE_STREAM)) {
					this.transferMode = FTP.STREAM_TRANSFER_MODE;
				} else {
					throw new ConnectorException(getScopeFacade(),
							"WAREWORK cannot initialize FTP Client '" + getClientName() + "' in Service '"
									+ getService().getName() + "' because given '" + PARAMETER_TRANSFER_MODE
									+ "' parameter has an invalid value.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}

			} else {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize FTP Client '" + getClientName() + "' in Service '"
								+ getService().getName() + "' because given '" + PARAMETER_TRANSFER_MODE
								+ "' parameter is not a String object.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

		}

		// Get the value of the debug commands parameter.
		if (getInitParameter(PARAMETER_DEBUG_COMMANDS) != null) {

			// Get the debug commands parameter.
			final Object targetDebugCommands = getInitParameter(PARAMETER_DEBUG_COMMANDS);

			// Validate the type for debug commands parameter.
			if (targetDebugCommands instanceof String) {

				// Get the string value of the debug commands.
				final String debugCommands = (String) targetDebugCommands;

				// Validate debug commands.
				if (debugCommands.trim().equalsIgnoreCase(CommonValueL1Constants.STRING_TRUE)) {
					this.debugCommands = true;
				} else if (debugCommands.trim().equalsIgnoreCase(CommonValueL1Constants.STRING_FALSE)) {
					this.debugCommands = false;
				} else {
					throw new ConnectorException(getScopeFacade(),
							"WAREWORK cannot initialize FTP Client '" + getClientName() + "' in Service '"
									+ getService().getName() + "' because given '" + PARAMETER_DEBUG_COMMANDS
									+ "' parameter has an invalid value.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}

			} else if (targetDebugCommands instanceof Boolean) {
				this.debugCommands = (Boolean) targetDebugCommands;
			} else {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize FTP Client '" + getClientName() + "' in Service '"
								+ getService().getName() + "' because given '" + PARAMETER_DEBUG_COMMANDS
								+ "' parameter is not a String or a Boolean object.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

		}

		// Get the value of the debug command log service name parameter.
		if (getInitParameter(PARAMETER_DEBUG_COMMANDS_LOG_SERVICE) != null) {

			// Get the debug command log service name parameter.
			final Object targetLogService = getInitParameter(PARAMETER_DEBUG_COMMANDS_LOG_SERVICE);

			// Validate the type for debug command log service name parameter.
			if (targetLogService instanceof String) {
				this.debugCommandsLogService = (String) targetLogService;
			} else {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize FTP Client '" + getClientName() + "' in Service '"
								+ getService().getName() + "' because given '" + PARAMETER_DEBUG_COMMANDS_LOG_SERVICE
								+ "' parameter is not a String object.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

		}

		// Get the value of the debug command log client name parameter.
		if (getInitParameter(PARAMETER_DEBUG_COMMANDS_LOG_CLIENT) != null) {

			// Get the debug command log client name parameter.
			final Object targetLogService = getInitParameter(PARAMETER_DEBUG_COMMANDS_LOG_CLIENT);

			// Validate the type for debug command log client name parameter.
			if (targetLogService instanceof String) {
				this.debugCommandsLogClient = (String) targetLogService;
			} else {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize FTP Client '" + getClientName() + "' in Service '"
								+ getService().getName() + "' because given '" + PARAMETER_DEBUG_COMMANDS_LOG_CLIENT
								+ "' parameter is not a String object.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

		}

		// Get the value of the use EPSV with IPv4 parameter.
		if (getInitParameter(PARAMETER_USE_EPSV_WITH_IPV4) != null) {

			// Get the use EPSV with IPv4 parameter.
			final Object targetUseEpsvWithIpv4 = getInitParameter(PARAMETER_USE_EPSV_WITH_IPV4);

			// Validate the type for use EPSV with IPv4 parameter.
			if (targetUseEpsvWithIpv4 instanceof String) {

				// Get the string value of the use EPSV with IPv4.
				final String debugCommands = (String) targetUseEpsvWithIpv4;

				// Validate use EPSV with IPv4 parameter.
				if (debugCommands.trim().equalsIgnoreCase(CommonValueL1Constants.STRING_TRUE)) {
					this.useEPSVwithIPv4 = true;
				} else if (debugCommands.trim().equalsIgnoreCase(CommonValueL1Constants.STRING_FALSE)) {
					this.useEPSVwithIPv4 = false;
				} else {
					throw new ConnectorException(getScopeFacade(),
							"WAREWORK cannot initialize FTP Client '" + getClientName() + "' in Service '"
									+ getService().getName() + "' because given '" + PARAMETER_USE_EPSV_WITH_IPV4
									+ "' parameter has an invalid value.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}

			} else if (targetUseEpsvWithIpv4 instanceof Boolean) {
				this.useEPSVwithIPv4 = (Boolean) targetUseEpsvWithIpv4;
			} else {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize FTP Client '" + getClientName() + "' in Service '"
								+ getService().getName() + "' because given '" + PARAMETER_USE_EPSV_WITH_IPV4
								+ "' parameter is not a String or a Boolean object.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

		}

		// Get the value of the local mode parameter.
		if (getInitParameter(PARAMETER_LOCAL_MODE) != null) {

			// Get the local modeparameter.
			final Object targetLocalMode = getInitParameter(PARAMETER_LOCAL_MODE);

			// Validate the type for local mode parameter.
			if (targetLocalMode instanceof String) {

				// Get the string value of the local mode.
				final String localMode = (String) targetLocalMode;

				// Validate use local mode parameter.
				if (localMode.trim().equalsIgnoreCase(MODE_ACTIVE)) {
					this.localMode = MODE_ACTIVE;
				} else if (localMode.trim().equalsIgnoreCase(MODE_PASSIVE)) {
					this.localMode = MODE_PASSIVE;
				} else {
					throw new ConnectorException(getScopeFacade(),
							"WAREWORK cannot initialize FTP Client '" + getClientName() + "' in Service '"
									+ getService().getName() + "' because given '" + PARAMETER_LOCAL_MODE
									+ "' parameter has an invalid value.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}

			} else {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize FTP Client '" + getClientName() + "' in Service '"
								+ getService().getName() + "' because given '" + PARAMETER_LOCAL_MODE
								+ "' parameter is not a String object.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

		}

		// Get the value of the remote mode parameter.
		if (getInitParameter(PARAMETER_REMOTE_MODE) != null) {

			// Get the remote mode parameter.
			final Object targetRemoteMode = getInitParameter(PARAMETER_REMOTE_MODE);

			// Validate the type for remote mode parameter.
			if (targetRemoteMode instanceof String) {

				// Get the string value of the remote mode.
				final String remoteMode = (String) targetRemoteMode;

				// Validate use remote mode parameter.
				if (remoteMode.trim().equalsIgnoreCase(MODE_ACTIVE)) {
					this.remoteMode = MODE_ACTIVE;
				} else if (remoteMode.trim().equalsIgnoreCase(MODE_PASSIVE)) {
					this.remoteMode = MODE_PASSIVE;
				} else {
					throw new ConnectorException(getScopeFacade(),
							"WAREWORK cannot initialize FTP Client '" + getClientName() + "' in Service '"
									+ getService().getName() + "' because given '" + PARAMETER_REMOTE_MODE
									+ "' parameter has an invalid value.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}

			} else {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize FTP Client '" + getClientName() + "' in Service '"
								+ getService().getName() + "' because given '" + PARAMETER_REMOTE_MODE
								+ "' parameter is not a String object.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

		}

		// Get the value of the remote active host parameter.
		if (getInitParameter(PARAMETER_REMOTE_ACTIVE_HOST) != null) {

			// Get the remote active host parameter.
			final Object targetRemoteActiveHost = getInitParameter(PARAMETER_REMOTE_ACTIVE_HOST);

			// Validate the type for remote active host parameter.
			if (targetRemoteActiveHost instanceof String) {
				this.remoteActiveHost = (String) targetRemoteActiveHost;
			} else {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize FTP Client '" + getClientName() + "' in Service '"
								+ getService().getName() + "' because given '" + PARAMETER_REMOTE_ACTIVE_HOST
								+ "' parameter is not a String object.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

		}

		// Get the value of the remote active port parameter.
		if (getInitParameter(PARAMETER_REMOTE_ACTIVE_PORT) != null) {

			// Get the remote active port parameter.
			final Object targetRemoteActivePort = getInitParameter(PARAMETER_REMOTE_ACTIVE_PORT);

			// Validate the type for remote active port parameter.
			if (targetRemoteActivePort instanceof String) {
				try {
					remoteActivePort = Integer.parseInt((String) targetRemoteActivePort);
				} catch (final Exception e) {
					throw new ConnectorException(getScopeFacade(),
							"WAREWORK cannot initialize FTP Client '" + getClientName() + "' in Service '"
									+ getService().getName() + "' because given '" + PARAMETER_REMOTE_ACTIVE_PORT
									+ "' parameter is not an Integer value.",
							e, LogServiceConstants.LOG_LEVEL_WARN);
				}
			} else if ((targetRemoteActivePort instanceof Byte) || (targetRemoteActivePort instanceof Short)
					|| (targetRemoteActivePort instanceof Integer)) {
				remoteActivePort = ((Number) targetRemoteActivePort).intValue();
			} else {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize FTP Client '" + getClientName() + "' in Service '"
								+ getService().getName() + "' because given '" + PARAMETER_REMOTE_ACTIVE_PORT
								+ "' parameter is not a String, Byte, Short or Integer value.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

		}

		// Get the value of the debug commands parameter.
		if (getInitParameter(PARAMETER_DIRECTORY_CHARACTER_SEPARATOR) != null) {

			// Get the directory character separator parameter.
			final Object dirChar = getInitParameter(PARAMETER_DIRECTORY_CHARACTER_SEPARATOR);

			// Validate the type for directory character separator parameter.
			if (dirChar instanceof String) {

				// Get the string value of the directory character separator.
				final String separator = (String) dirChar;

				// Validate directory character separator.
				if (separator.trim().length() != 1) {
					throw new ConnectorException(getScopeFacade(),
							"WAREWORK cannot initialize FTP Client '" + getClientName() + "' in Service '"
									+ getService().getName() + "' because given '"
									+ PARAMETER_DIRECTORY_CHARACTER_SEPARATOR
									+ "' parameter can not be a string with more than one character.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}

			} else if (!(dirChar instanceof Character)) {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize FTP Client '" + getClientName() + "' in Service '"
								+ getService().getName() + "' because given '" + PARAMETER_DIRECTORY_CHARACTER_SEPARATOR
								+ "' parameter is not a '" + String.class.getName() + "' or a '"
								+ Character.class.getName() + "' object.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

		}

	}

	/**
	 * Retrieves the connection source.
	 * 
	 * @return A new instance of
	 *         <code>org.apache.commons.net.ftp.FTPClient</code>.<br>
	 *         <br>
	 */
	protected Object createConnectionSource() throws ConnectorException {
		return new FTPClient();
	}

}
