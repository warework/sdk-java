package com.warework.service.file.client;

import java.util.HashMap;
import java.util.Map;

import com.warework.core.scope.AbstractSerFileTestCase;
import com.warework.core.service.client.connector.ConnectorFacade;
import com.warework.service.file.client.connector.FtpConnector;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public final class FtpClientTest extends AbstractSerFileTestCase {

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @return
	 */
	protected final Class<? extends ConnectorFacade> getClientConnectorType() {
		return FtpConnector.class;
	}

	/**
	 * 
	 * @return
	 */
	protected final Map<String, Object> getClientParameters() {

		//
		final Map<String, Object> parameters = new HashMap<String, Object>();

		//
		parameters.put(FtpConnector.PARAMETER_HOST, HOSTNAME);
		parameters.put(FtpConnector.PARAMETER_PORT, Integer.valueOf(3800));
		parameters.put(FtpConnector.PARAMETER_USER, USERNAME);
		parameters.put(FtpConnector.PARAMETER_PASSWORD, PASSWORD);
		parameters.put(FtpConnector.PARAMETER_DEBUG_COMMANDS, Boolean.TRUE);
		parameters.put(FtpConnector.PARAMETER_FILE_TYPE, FtpConnector.FILE_TYPE_BINARY);
		parameters.put(FtpConnector.PARAMETER_LOCAL_MODE, FtpConnector.MODE_PASSIVE);
		parameters.put(FtpConnector.PARAMETER_USE_EPSV_WITH_IPV4, Boolean.TRUE);
		
		//
		parameters.put(FtpConnector.PARAMETER_BASE_PATH, "/test");

		//
		return parameters;

	}

}
