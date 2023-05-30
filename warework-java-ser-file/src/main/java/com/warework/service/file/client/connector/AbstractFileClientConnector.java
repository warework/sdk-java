package com.warework.service.file.client.connector;

import com.warework.core.service.client.connector.AbstractConnector;
import com.warework.core.service.client.connector.ConnectorException;
import com.warework.service.file.FileServiceImpl;
import com.warework.service.file.client.AbstractFileClient;
import com.warework.service.log.LogServiceConstants;

/**
 * Abstract Connector that holds common information required to create
 * connections with file systems.<br>
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public abstract class AbstractFileClientConnector extends AbstractConnector {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Optional initialization parameter that specifies the base path to append
	 * before any given path and for every operation to perform. For example: if
	 * this paramete value is '/resources' and <code>read</code> operation is
	 * executed with path '/pictures' then Client will read the file from
	 * '/resources/pictures'.<br>
	 * <br>
	 * When provided, this parameter must be always a String with a path supported
	 * by the underlying Client. No matter if you transform path with specific
	 * Client paremeters, value for this parameter must be <b>always with the
	 * default notation supported by the Client</b>.
	 */
	public static final String PARAMETER_BASE_PATH = AbstractFileClient.CLIENT_INFO_BASE_PATH;

	/**
	 * Initialization parameter that specifies the size for the buffer to use in
	 * <code>read</code> and <code>write</code> operations. This parameter is not
	 * mandatory. When provided, it must be a String or an integer value with the
	 * number of bytes for the buffer. If this parameter is not provided then it
	 * defaults to <code>AbstractFileClient.DEFAULT_BUFFER_SIZE</code>.
	 */
	public static final String PARAMETER_BUFFER_SIZE = "buffer-size";

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the type of the service where clients created by this connector can
	 * work.
	 * 
	 * @return Type of the service.
	 */
	protected Class<FileServiceImpl> getServiceType() {
		return FileServiceImpl.class;
	}

	/**
	 * Validates the initialization parameters.
	 * 
	 * @throws ConnectorException If there is an error when trying to initialize the
	 *                            connector.<br>
	 *                            <br>
	 */
	protected void initialize() throws ConnectorException {

		// Validate common parameters.
		super.initialize();

		// Get the size of the buffer.
		Object basePath = getInitParameter(PARAMETER_BASE_PATH);
		if ((basePath != null) && !(basePath instanceof String)) {
			throw new ConnectorException(getScopeFacade(),
					"WAREWORK cannot initialize File Client '" + getClientName() + "' at Service '"
							+ getService().getName() + "' because given '" + PARAMETER_BASE_PATH
							+ "' connector parameter is not an '" + String.class.getName() + "' value.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the size of the buffer.
		Object bufferSize = getInitParameter(PARAMETER_BUFFER_SIZE);
		if ((bufferSize != null) && !(bufferSize instanceof String) && !(bufferSize instanceof Integer)) {
			throw new ConnectorException(getScopeFacade(),
					"WAREWORK cannot initialize File Client '" + getClientName() + "' at Service '"
							+ getService().getName() + "' because given '" + PARAMETER_BUFFER_SIZE
							+ "' connector parameter is not an '" + Integer.class.getName() + "' or a '"
							+ String.class.getName() + "' value.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

	}

}
