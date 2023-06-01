package com.warework.service.log.client.connector;

import java.io.InputStream;

import org.apache.log4j.Logger;

import com.warework.core.loader.LoaderException;
import com.warework.core.loader.ResourceLoader;
import com.warework.core.scope.ScopeL1Constants;
import com.warework.core.service.client.connector.ConnectorException;
import com.warework.service.log.LogServiceConstants;
import com.warework.service.log.client.Log4jLogger;

/**
 * This abstract Connector holds the information required to create connections
 * for Log4j Loggers.<br>
 * <br>
 * Each Connector that extends this class loads the Log4j configuration resource
 * (typically a '.properties' file) with a <code>java.net.URL</code> or string
 * object with the <code>PARAMETER_CONTEXT_LOADER</code> parameter. If this
 * parameter references a string object, then the Framework performs one of
 * these actions (just one, in this order): (a) searchs for the
 * <code>PARAMETER_CONTEXT_LOADER</code> parameter in the Connector and, if it
 * exists, the Framework extracts the corresponding class/object and uses it to
 * get the resource (for example, by calling the
 * <code>ObjectType.class.getResource(name)</code> method). (b) tries to create
 * a <code>java.net.URL</code> object from given string. (c) searchs for the
 * <code>PARAMETER_CONTEXT_LOADER</code> parameter in the Scope where the
 * Service exists, and extracts the corresponding class to finally retrieve the
 * resource with it.
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public abstract class AbstractLog4jConnector extends AbstractLoggerConnector {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Initialization parameter that specifies where to load resources. If this
	 * parameter references a string object, then the Framework performs one of
	 * these actions (just one, in this order): (a) searchs for the
	 * <code>PARAMETER_CONTEXT_LOADER</code> parameter in the Connector
	 * configuration and, if it exists, the Framework extracts the corresponding
	 * class/object and uses it to get the resource. (b) searchs for the
	 * <code>PARAMETER_CONTEXT_LOADER</code> parameter in the Scope where the
	 * Connector and extracts the corresponding class to finally retrieve the
	 * resource with it. (c) return <code>null</code>.
	 */
	public static final String PARAMETER_CONFIG_TARGET = ScopeL1Constants.PARAMETER_CONFIG_TARGET;

	/**
	 * Initialization parameter that specifies the class that should be used to load
	 * resources. The value of this parameter usually is the name of a class that
	 * exists in your project, so the Warework Framework can read resources from it.
	 */
	public static final String PARAMETER_CONTEXT_LOADER = ScopeL1Constants.PARAMETER_CONTEXT_LOADER;

	/**
	 * Initialization parameter that specifies if the name for the Logger have to be
	 * the name of the Scope where the Log Service exists. It helps to trace
	 * operations in a specific Scope. Accepted values for this parameter are
	 * <code>true</code> or <code>false</code> (as <code>java.lang.String</code> or
	 * <code>java.lang.Boolean</code> objects). If this parameter is not specified
	 * then the value for it is <code>false</code>.
	 */
	public static final String PARAMETER_USE_SCOPE_NAME = "use-scope-name";

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Logger where to perform logging operations.
	private Logger logger;

	// ///////////////////////////////////////////////////////////////////
	// ABSTRACT METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Configures Log4j from an external file.
	 * 
	 * @param config Configuration resource (typically properties or XML files).<br>
	 *               <br>
	 * @throws ConnectorException If there is an error when trying to configure the
	 *                            Logger.<br>
	 *                            <br>
	 */
	protected abstract void configure(final InputStream config) throws ConnectorException;

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the type of the Client.
	 * 
	 * @return A <code>com.warework.service.log.client.Log4jLogger</code>
	 *         client.<br>
	 *         <br>
	 */
	public Class<Log4jLogger> getClientType() {
		return Log4jLogger.class;
	}

	/**
	 * Gets a connection for the Log4j Logger.
	 * 
	 * @return An <code>org.apache.log4j.Logger</code> instance.<br>
	 *         <br>
	 * @throws ConnectorException If there is an error when trying to create the
	 *                            Logger.<br>
	 *                            <br>
	 */
	public Object getClientConnection() throws ConnectorException {

		// Create the connection.
		if (logger == null) {

			// Get the URL that points to the configuration file.
			final InputStream connectionSource = (InputStream) getConnectionSource();

			// Configures Log4j from an external file.
			configure(connectionSource);

			// Create the Logger where to perform logging operations.
			if (isInitParameter(PARAMETER_USE_SCOPE_NAME)) {
				logger = Logger.getLogger(getScopeFacade().getName());
			} else {
				logger = Logger.getLogger(getClientName());
			}

		}

		// Return the connection.
		return logger;

	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates a <code>java.net.URL</code> that points to the properties or XML file
	 * that holds the information required to configure the Logger.
	 * 
	 * @return A <code>java.net.URL</code>.<br>
	 *         <br>
	 * @throws ConnectorException If there is an error when trying to get the
	 *                            configuration of the Logger.<br>
	 *                            <br>
	 */
	protected Object createConnectionSource() throws ConnectorException {
		try {
			return new ResourceLoader(getScopeFacade()).getResource(getInitParameter(PARAMETER_CONTEXT_LOADER),
					getInitParameter(PARAMETER_CONFIG_TARGET));
		} catch (final LoaderException e) {
			throw new ConnectorException(getScopeFacade(),
					"WAREWORK cannot create the connection source for Logger '" + getClientName()
							+ "' because the following exception is thrown when trying to find the resource: "
							+ e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

}
