package com.warework.core.service.client.connector;

import java.util.Enumeration;
import java.util.Map;

import com.warework.core.scope.ScopeFacade;
import com.warework.core.service.ProxyServiceFacade;
import com.warework.core.service.ServiceFacade;
import com.warework.core.service.client.AbstractClient;
import com.warework.core.service.client.ClientException;
import com.warework.core.service.client.ClientFacade;
import com.warework.core.util.CommonValueL1Constants;
import com.warework.core.util.helper.DataStructureL1Helper;
import com.warework.core.util.helper.ReflectionL1Helper;
import com.warework.core.util.helper.StringL1Helper;
import com.warework.service.log.LogServiceConstants;

/**
 * Provides a default implementation for a Connector.<br>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public abstract class AbstractConnector implements ConnectorFacade {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Initialization parameter that specifies if the Service have to connect the
	 * Client when it is created. Accepted values for this parameter are
	 * <code>true</code> or <code>false</code> (as <code>java.lang.String</code> or
	 * <code>java.lang.Boolean</code> objects). If this parameter is not specified
	 * then it is <code>false</code>.
	 */
	public static final String PARAMETER_CONNECT_ON_CREATE = "connect-on-create";

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Service that created this Connector.
	private ProxyServiceFacade service;

	// Name of the Client.
	private String clientName;

	// Object that contains the information required to create connections.
	private Object connectionSource;

	// Client that represents this Connector.
	private ClientFacade client;

	// Initialization parameters (as string-object pairs) for this Service.
	private Map<String, Object> parameters;

	// ///////////////////////////////////////////////////////////////////
	// ABSTRACT METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the type of the Service where clients created by this Connector can
	 * work.
	 * 
	 * @return Type of the Service.<br>
	 *         <br>
	 */
	protected abstract Class<? extends ServiceFacade> getServiceType();

	/**
	 * Creates the object that contains the information required to create
	 * connections.
	 * 
	 * @return A connection source.<br>
	 *         <br>
	 * @throws ConnectorException If there is an error when trying to create the
	 *                            connection source.<br>
	 *                            <br>
	 */
	protected abstract Object createConnectionSource() throws ConnectorException;

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Initializes the Connector.
	 * 
	 * @param service              Service that creates this Connector. This Service
	 *                             will be used for the Client to interact with
	 *                             it.<br>
	 *                             <br>
	 * @param clientName           Name of the Client to which this Connector
	 *                             creates connections.<br>
	 *                             <br>
	 * @param connectionParameters Parameters (as string-object pairs) that
	 *                             specifies how to create the connections.<br>
	 *                             <br>
	 * @throws ConnectorException If there is an error when trying to initialize the
	 *                            Connector.<br>
	 *                            <br>
	 */
	public void init(final ProxyServiceFacade service, final String clientName,
			final Map<String, Object> connectionParameters) throws ConnectorException {

		// Validate that Service exists.
		if (service == null) {
			if ((clientName == null) || (clientName.equals(CommonValueL1Constants.STRING_EMPTY))) {
				throw new ConnectorException(null, "WAREWORK cannot create Client because given Service is null.", null,
						-1);
			} else {
				throw new ConnectorException(null,
						"WAREWORK cannot create Client '" + clientName + "' because given Service is null.", null, -1);
			}
		}

		// Validate the name of the Client.
		if ((clientName == null) || (clientName.equals(CommonValueL1Constants.STRING_EMPTY))) {
			throw new ConnectorException(service.getScopeFacade(), "WAREWORK cannot create the Client in Service '"
					+ service.getName() + "' because given name is null or empty.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Validate that Connector specifies a correct Client-type.
		if (getClientType() == null) {
			throw new ConnectorException(service.getScopeFacade(),
					"WAREWORK cannot create client '" + clientName + "' in Service '" + service.getName()
							+ "' because the Connector does not specifies the type of the client.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Validate that Connector is compatible with the Service.
		if (getServiceType() == null) {
			throw new ConnectorException(service.getScopeFacade(), "WAREWORK cannot create Client '" + clientName
					+ "' in Service '" + service.getName()
					+ "' because the Connector does not specifies the type of the Service where the Client can work.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		} else if (!ReflectionL1Helper.isType(service.getClass(), getServiceType())) {
			throw new ConnectorException(service.getScopeFacade(),
					"WAREWORK cannot create Client '" + clientName + "' in Service '" + service.getName()
							+ "' because the Connector creates clients of type '" + getClientType().getName()
							+ "' that do not work on services of type '" + service.getClass().getName() + "'.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Set the Service that creates this Connector
		this.service = service;

		// Set the name of the Client.
		this.clientName = clientName;

		// Set the initialization parameters.
		this.parameters = connectionParameters;

		// Initializes the Connector.
		initialize();

		// Get the object that contains the information required to create
		// connections.
		if (cacheConnectionSource()) {
			this.connectionSource = createConnectionSource();
		}

		// Create and initialize the Client.
		this.client = createClient();

	}

	/**
	 * Gets the Service that created this Connector. The Client uses this method to
	 * interact with the Service.
	 * 
	 * @return Service.<br>
	 *         <br>
	 */
	public ProxyServiceFacade getService() {
		return service;
	}

	/**
	 * Gets the name of the Client to which this Connector creates connections.
	 * 
	 * @return Name of the Client.<br>
	 *         <br>
	 */
	public String getClientName() {
		return clientName;
	}

	/**
	 * Gets the Client represented by this Connector.
	 * 
	 * @return Client.<br>
	 *         <br>
	 */
	public ClientFacade getClient() {
		return client;
	}

	/**
	 * Gets the object that contains the information required to create connections.
	 * 
	 * @return A connection source.<br>
	 *         <br>
	 */
	public Object getConnectionSource() {
		if (connectionSource == null) {
			try {
				return createConnectionSource();
			} catch (final ConnectorException e) {

				// Log the error.
				getScopeFacade().log("WAREWORK cannot create the connection source for Client '" + clientName
						+ "' in Service '" + service.getName()
						+ "' because the following exception is thrown by the Connector: " + e.getMessage(),
						LogServiceConstants.LOG_LEVEL_WARN);

				// Nothing to return.
				return null;

			}
		} else {
			return connectionSource;
		}
	}

	/**
	 * Gets the names of the initialization parameters.
	 * 
	 * @return Initialization parameters' names or <code>null</code> if no one
	 *         exist.<br>
	 *         <br>
	 */
	public Enumeration<String> getInitParameterNames() {
		return (parameters == null) ? null : DataStructureL1Helper.toEnumeration(parameters.keySet());
	}

	/**
	 * Gets the value of an initialization parameter.
	 * 
	 * @param name Name of the initialization parameter.<br>
	 *             <br>
	 * @return Value of the initialization parameter.<br>
	 *         <br>
	 */
	public Object getInitParameter(final String name) {
		return (parameters == null) ? null : parameters.get(name);
	}

	/**
	 * Gets the boolean value of an initialization parameter.
	 * 
	 * @param name Name of the initialization parameter.<br>
	 *             <br>
	 * @return <code>true</code> if the parameter is a
	 *         <code>java.lang.Boolean</code> that is equals to
	 *         <code>Boolean.TRUE</code> or a <code>java.lang.String</code> that is
	 *         equals (in any case form) to <code>true</code>. Otherwise, this
	 *         method returns <code>false</code> (for example: if it does not
	 *         exist).<br>
	 *         <br>
	 */
	public boolean isInitParameter(final String name) {

		// Get the value of the parameter.
		final Boolean value = toBoolean(name);

		// Return the value of the parameter.
		return (value == null) ? false : value;

	}

	/**
	 * Gets a boolean value of an initialization parameter.
	 * 
	 * @param parameterName Name of the initialization parameter.<br>
	 *                      <br>
	 * @return The boolean value of the initialization parameter or null if
	 *         parameter is not a boolean object or a string that represents a
	 *         boolean value.<br>
	 *         <br>
	 */
	public Boolean toBoolean(final String parameterName) {
		return CommonValueL1Constants.toBoolean(getInitParameter(parameterName));
	}

	/**
	 * Gets an integer value of an initialization parameter.
	 * 
	 * @param parameterName Name of the initialization parameter.<br>
	 *                      <br>
	 * @return The integer value of the initialization parameter or null if
	 *         parameter is not an integer object or a string that represents an
	 *         integer value.<br>
	 *         <br>
	 */
	public Integer toInteger(final String parameterName) {
		return CommonValueL1Constants.toInteger(getInitParameter(parameterName));
	}

	/**
	 * Gets a string value of an initialization parameter.
	 * 
	 * @param parameterName Name of the initialization parameter.<br>
	 *                      <br>
	 * @return The string value of the initialization parameter or null if parameter
	 *         is not a string object.<br>
	 *         <br>
	 */
	public String toString(final String parameterName) {
		return CommonValueL1Constants.toString(getInitParameter(parameterName));
	}

	/**
	 * Gets the name of the Client.
	 * 
	 * @return Name of the Client.<br>
	 *         <br>
	 */
	public String toString() {
		return "Connector for Client '" + clientName + StringL1Helper.CHARACTER_SINGLE_QUOTE;
	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the scope where this Connector and the Client exist.
	 * 
	 * @return Scope.<br>
	 *         <br>
	 */
	protected final ScopeFacade getScopeFacade() {
		return getService().getScopeFacade();
	}

	/**
	 * Creates and initializes the Client.
	 * 
	 * @return A new Client instance.<br>
	 *         <br>
	 * @throws ConnectorException If there is an error when trying to create the
	 *                            Client.<br>
	 *                            <br>
	 */
	protected final ClientFacade createClient() throws ConnectorException {
		try {

			// Create an instance of the Client.
			final AbstractClient clientInstance = (AbstractClient) getClientType().newInstance();

			// Initialize the Client.
			clientInstance.init(this);

			// Return the Client.
			return clientInstance;

		} catch (final ClientException e) {
			throw new ConnectorException(getScopeFacade(),
					"WAREWORK cannot create Client '" + clientName + "' in Service '" + getService().getName()
							+ "' because the initialization of the Client generated the following error: "
							+ e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		} catch (final Exception e) {
			throw new ConnectorException(getScopeFacade(),
					"WAREWORK cannot create Client '" + clientName + "' in Service '" + getService().getName()
							+ "' because the instantiation of the Client generated the following error: "
							+ e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Initializes the Connector. Subclasses may override this method to validate
	 * initialization parameters and other attributes required to create the
	 * Connector.
	 * 
	 * @throws ConnectorException If there is an error when trying to initialize the
	 *                            Connector.<br>
	 *                            <br>
	 */
	protected void initialize() throws ConnectorException {
		// DO NOTHING.
	}

	/**
	 * Specifies if the connection source should be created and stored on start up.
	 * This method always returns <code>true</code> but subclasses may override this
	 * method to indicate the opposite.
	 * 
	 * @return Always <code>true</code>.<br>
	 *         <br>
	 */
	protected boolean cacheConnectionSource() {
		return true;
	}

}
