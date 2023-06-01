package com.warework.service.file.client;

import java.util.HashMap;
import java.util.Map;

import com.warework.core.scope.AbstractSerFileTestCase;
import com.warework.core.service.client.connector.ConnectorFacade;
import com.warework.service.file.client.connector.SftpConnector;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public final class SftpClientTest extends AbstractSerFileTestCase {

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @return
	 */
	protected final Class<? extends ConnectorFacade> getClientConnectorType() {
		return SftpConnector.class;
	}

	/**
	 * 
	 * @return
	 */
	protected final Map<String, Object> getClientParameters() {

		//
		final Map<String, Object> parameters = new HashMap<String, Object>();

		//
		parameters.put(SftpConnector.PARAMETER_HOST, HOSTNAME);
		parameters.put(SftpConnector.PARAMETER_PORT, Integer.valueOf(22));
		parameters.put(SftpConnector.PARAMETER_USER, USERNAME);
		parameters.put(SftpConnector.PARAMETER_PASSWORD, PASSWORD);
		parameters.put(SftpConnector.PARAMETER_STRICT_HOST_KEY_CHECKING, Boolean.FALSE);

		//
		return parameters;

	}

}
