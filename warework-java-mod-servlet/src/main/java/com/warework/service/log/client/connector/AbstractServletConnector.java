package com.warework.service.log.client.connector;

import com.warework.core.scope.ScopeL1Constants;

/**
 * This Connector holds the information required to create connections for
 * Servlet Loggers.<br>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public abstract class AbstractServletConnector extends AbstractLoggerConnector {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Initialization parameter that specifies the
	 * <code>javax.servlet.ServletContext</code> class that should be used to load
	 * resources and to perform the log.
	 */
	public static final String PARAMETER_CONTEXT_LOADER = ScopeL1Constants.PARAMETER_CONTEXT_LOADER;

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets a <code>javax.servlet.ServletContext</code> connection for the Client.
	 * 
	 * @return Client's connection.<br>
	 *         <br>
	 */
	public final Object getClientConnection() {
		return (getInitParameter(PARAMETER_CONTEXT_LOADER) == null) ? getScopeFacade().getObject(
				(String) getScopeFacade().getInitParameter(ScopeL1Constants.PARAMETER_CONTEXT_LOADER),
				ScopeL1Constants.PARAMETER_CONTEXT_LOADER) : getInitParameter(PARAMETER_CONTEXT_LOADER);
	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * As this Connector always returns a specific connection the result of this
	 * method is always <code>null</code>.
	 * 
	 * @return Always <code>null</code>.<br>
	 *         <br>
	 */
	protected final Object createConnectionSource() {
		return null;
	}

}
