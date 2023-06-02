package com.warework.service.log.client.connector;

import javax.servlet.ServletContext;

import com.warework.core.scope.ScopeL1Constants;
import com.warework.core.service.client.connector.ConnectorException;
import com.warework.service.log.LogServiceConstants;
import com.warework.service.log.client.ServletLogger;

/**
 * This Connector holds the information required to create connections for
 * Servlet Loggers.<br>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public final class ServletConnector extends AbstractServletConnector {

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the type of the Client.
	 * 
	 * @return A <code>com.warework.service.log.client.ConsoleLogger</code>
	 *         client.<br>
	 *         <br>
	 */
	public Class<ServletLogger> getClientType() {
		return ServletLogger.class;
	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Initializes the connector.
	 * 
	 * @throws ConnectorException If there is an error when trying to initialize the
	 *                            connector.<br>
	 *                            <br>
	 */
	protected void initialize() throws ConnectorException {

		// Validate the value of the PARAMETER_REMOVE_COMMENTS.
		final Object servletContext = getInitParameter(PARAMETER_CONTEXT_LOADER);
		if (servletContext == null) {
			if (!(getScopeFacade().getObject(
					(String) getScopeFacade().getInitParameter(ScopeL1Constants.PARAMETER_CONTEXT_LOADER),
					ScopeL1Constants.PARAMETER_CONTEXT_LOADER) instanceof ServletContext)) {
				throw new ConnectorException(getScopeFacade(), "WAREWORK cannot initialize Logger '" + getClientName()
						+ "' at Service '" + getService().getName() + "' because Scope initialization parameter '"
						+ PARAMETER_CONTEXT_LOADER + "' is not a valid " + ServletContext.class.getName() + " value.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}
		} else if (!(servletContext instanceof ServletContext)) {
			throw new ConnectorException(getScopeFacade(),
					"WAREWORK cannot initialize Logger '" + getClientName() + "' at Service '" + getService().getName()
							+ "' because Client parameter '" + PARAMETER_CONTEXT_LOADER + "' is not a valid "
							+ ServletContext.class.getName() + " value.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

	}

}
