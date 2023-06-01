package com.warework.service.file.client;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.warework.core.scope.AbstractSerFileTestCase;
import com.warework.core.scope.ScopeFacade;
import com.warework.core.service.client.connector.ConnectorFacade;
import com.warework.service.file.client.connector.UrlConnector;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public final class UrlClientTest extends AbstractSerFileTestCase {

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @return
	 */
	protected Class<? extends ConnectorFacade> getClientConnectorType() {
		return UrlConnector.class;
	}

	/**
	 * 
	 * @return
	 */
	protected Map<String, Object> getClientParameters() {

		//
		final URL url = AbstractSerFileTestCase.class.getResource(getResourcePath(FILE_BINARY_1));

		//
		final Map<String, Object> parameters = new HashMap<String, Object>();

		//
		parameters.put(UrlConnector.PARAMETER_PROTOCOL, "file");
		parameters.put(UrlConnector.PARAMETER_BASE_PATH,
				url.getPath().substring(0, url.getPath().indexOf(DIR_BASE) - 1));

		//
		return parameters;

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Do not sets up anything for the test before it's executed.
	 * 
	 * @param system Not used.<br>
	 *               <br>
	 */
	protected void setup(final ScopeFacade system) {
		// DO NOTHING
	}

	/**
	 * Remove files and directories created in the test.
	 * 
	 * @return Always <code>false</code>.<br>
	 *         <br>
	 */
	protected boolean cleanUp() {
		return false;
	}

}
