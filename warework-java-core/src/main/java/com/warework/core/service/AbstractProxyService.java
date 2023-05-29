package com.warework.core.service;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import com.warework.core.model.Client;
import com.warework.core.model.ProxyService;
import com.warework.core.service.client.AbstractClient;
import com.warework.core.service.client.ClientException;
import com.warework.core.service.client.ClientFacade;
import com.warework.core.service.client.connector.AbstractConnector;
import com.warework.core.service.client.connector.ConnectorException;
import com.warework.core.service.client.connector.ConnectorFacade;
import com.warework.core.util.CommonValueL1Constants;
import com.warework.core.util.bean.Parameter;
import com.warework.core.util.helper.DataStructureL1Helper;
import com.warework.core.util.helper.ReflectionL1Helper;
import com.warework.service.log.LogServiceConstants;

/**
 * Provides a default implementation for a Service that handles multiple
 * Clients.<br>
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public abstract class AbstractProxyService extends AbstractService implements ProxyServiceFacade {

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Context for Clients.
	private Map<String, ClientFacade> clients;

	// Service operation handler.
	private OperationHandler operationHandler;

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Terminates the execution of the Service.
	 * 
	 * @throws ServiceException If there is an error when trying to close the
	 *                          Service.<br>
	 *                          <br>
	 */
	public void close() throws ServiceException {

		// Removes all the clients bound to this Service.
		removeAllClients();

		// Terminates the execution of the Service.
		shutdown();

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates a Client and binds it to this Service, using the name specified.
	 * 
	 * @param clientName           The name to which the Client will be bound in the
	 *                             Service. If a Client of the same name is already
	 *                             bound to this Service, an exception is
	 *                             thrown.<br>
	 *                             <br>
	 * @param connectorType        Implementation of the Connector that specifies
	 *                             which Client to use and how to create the
	 *                             connections for the Client.<br>
	 *                             <br>
	 * @param connectionParameters Parameters (as string values) that specifies how
	 *                             the Connector will create the connections for the
	 *                             Client.<br>
	 *                             <br>
	 * @throws ServiceException If there is an error when trying to create the
	 *                          Client.<br>
	 *                          <br>
	 */
	public void createClient(final String clientName, final Class<? extends ConnectorFacade> connectorType,
			final Map<String, Object> connectionParameters) throws ServiceException {

		// Create and initialize the Connector.
		final AbstractConnector connector = createConnector(clientName, connectorType, connectionParameters);

		// Validate that no Client already exists with this name.
		if ((clients != null) && (clients.containsKey(clientName))) {
			throw new ServiceException(getScopeFacade(), "WAREWORK cannot create Client '" + clientName
					+ "' in Service '" + getName() + "' as a Client with this name already exists.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Create the context when needed.
		if (clients == null) {
			clients = new HashMap<String, ClientFacade>();
		}

		// Register the Client in the context.
		clients.put(connector.getClientName(), connector.getClient());

		// Log a message.
		getScopeFacade().log(
				"WAREWORK created Client '" + connector.getClientName() + "' in Service '" + getName() + "'.",
				LogServiceConstants.LOG_LEVEL_INFO);

		// Connect the Client when required.
		if (connectClient(connectionParameters)) {
			connect(clientName);
		}

	}

	/**
	 * Gets the names of all the clients bound to this Service.
	 * 
	 * @return Names of the clients or <code>null</code> if no one exist.<br>
	 *         <br>
	 */
	public Enumeration<String> getClientNames() {

		// Return Client names if they exist.
		if ((clients != null) && (clients.size() > 0)) {
			return DataStructureL1Helper.toEnumeration(clients.keySet());
		}

		// Nothing to return at this point.
		return null;

	}

	/**
	 * Validates if a Client is bound to this Service.
	 * 
	 * @param clientName The name to which the Client is bound in the Service.<br>
	 *                   <br>
	 * @return <code>true</code> if the Client exists and <code>false</code> if the
	 *         Client does not exists.<br>
	 *         <br>
	 */
	public boolean existsClient(final String clientName) {

		// Search for the Client.
		if ((clients != null) && (clientName != null) && (!clientName.equals(CommonValueL1Constants.STRING_EMPTY))) {
			return clients.containsKey(clientName);
		}

		// At this point, return false.
		return false;

	}

	/**
	 * Removes a Client bound to this Service. If this Service does not have a
	 * Client bound with the specified name, this method does nothing. If Client is
	 * connected this method disconnects it first.
	 * 
	 * @param clientName The name to which the Client is bound in the Service.<br>
	 *                   <br>
	 * @throws ServiceException If there is an error when trying to remove the
	 *                          Client.<br>
	 *                          <br>
	 */
	public void removeClient(final String clientName) throws ServiceException {

		// Get the Client.
		final AbstractClient client = getClient(clientName);

		// Remove the Client.
		if (client != null) {

			// Disconnect the Client first.
			if (client.isConnected()) {
				try {
					client.disconnect();
				} catch (final ClientException e) {
					throw new ServiceException(getScopeFacade(),
							"WAREWORK cannot remove Client '" + clientName + "' in Service '" + getName()
									+ "' because Client cannot be disconnected. Check out the following exception: "
									+ e.getMessage(),
							e, LogServiceConstants.LOG_LEVEL_WARN);
				}
			}

			// Destroy Client.
			try {
				client.destroy();
			} catch (final ClientException e) {
				throw new ServiceException(getScopeFacade(),
						"WAREWORK cannot remove Client '" + clientName + "' in Service '" + getName()
								+ "' because Client cannot be destroyed. Check out the following exception: "
								+ e.getMessage(),
						e, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Remove the data source.
			clients.remove(clientName);

			// Remove the context if it's empty.
			if (clients.size() < 1) {
				clients = null;
			}

			// Log a message.
			getScopeFacade().log("WAREWORK removed Client '" + clientName + "' in Service '" + getName() + "'.",
					LogServiceConstants.LOG_LEVEL_INFO);

		} else {
			getScopeFacade().log("WAREWORK cannot remove Client '" + clientName + "' in Service '" + getName()
					+ "' because Client does not exists.", LogServiceConstants.LOG_LEVEL_WARN);
		}

	}

	/**
	 * Removes all the clients bound to this Service.
	 * 
	 * @throws ServiceException If there is an error when trying to remove the
	 *                          Clients.<br>
	 *                          <br>
	 */
	public void removeAllClients() throws ServiceException {

		// Get the names of all clients bound.
		final Enumeration<String> clientsNames = getClientNames();

		// Remove every Client.
		if (clientsNames != null) {
			while (clientsNames.hasMoreElements()) {

				// Get the name of a Client.
				final String clientName = (String) clientsNames.nextElement();

				// Remove the Client.
				removeClient(clientName);

			}
		}

	}

	/**
	 * Gets the class that implements the Client.
	 * 
	 * @param clientName The name to which the Client is bound in the Service.<br>
	 *                   <br>
	 * @return Type of the Client or <code>null</code> if Client does not
	 *         exists.<br>
	 *         <br>
	 */
	public Class<? extends ClientFacade> getClientType(final String clientName) {

		// Get the Client.
		final ClientFacade client = getClient(clientName);

		// Return the Client type.
		return (client == null) ? null : client.getConnector().getClientType();

	}

	/**
	 * Validates if a Client is the same type of a given class.
	 * 
	 * @param clientName The name to which the Client is bound in the Service.<br>
	 *                   <br>
	 * @param clientType Class to compare with the class of the Client.<br>
	 *                   <br>
	 * @return <code>true</code> if the Client's type is the same as given class and
	 *         <code>false</code> if not.<br>
	 *         <br>
	 */
	public boolean isClientType(String clientName, Class<? extends ClientFacade> clientType) {

		// Get the type of the Client.
		final Class<? extends ClientFacade> type = getClientType(clientName);

		// Return if both are the same.
		return ((type != null) && (type.equals(clientType)));

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the connection of a Client. First you'll need to connect the Client to
	 * retrieve the connection, otherwise this method will return <code>null</code>.
	 * 
	 * @param clientName The name to which the Client is bound in the Service.<br>
	 *                   <br>
	 * @return Client's connection or <code>null</code> if it's not connected.<br>
	 *         <br>
	 */
	public Object getConnection(final String clientName) {

		// Get the Client.
		final ClientFacade client = getClient(clientName);

		// Return the connection.
		if (client == null) {

			// Log a message.
			getScopeFacade().log("WAREWORK cannot get the connection for Client '" + clientName + "' in Service '"
					+ getName() + "' because Client does not exists.", LogServiceConstants.LOG_LEVEL_WARN);

			// Nothing to return.
			return null;

		} else {
			return client.getConnection();
		}

	}

	/**
	 * Opens a connection with a Client. If Client's connection is already open this
	 * method throws an exception.
	 * 
	 * @param clientName The name to which the Client is bound in the Service.<br>
	 *                   <br>
	 * @throws ServiceException If there is an error when trying to connect the
	 *                          Client.<br>
	 *                          <br>
	 */
	public void connect(final String clientName) throws ServiceException {

		// Get the Client.
		final ClientFacade client = getClient(clientName);

		// Open a connection with the Client.
		if (client == null) {
			throw new ServiceException(getScopeFacade(), "WAREWORK cannot connect Client '" + clientName
					+ "' in Service '" + getName() + "' because Client does not exists.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		} else {
			try {
				client.connect();
			} catch (ClientException e) {
				throw new ServiceException(getScopeFacade(),
						"WAREWORK cannot connect Client '" + clientName + "' at Service '" + getName() + "'.", e,
						LogServiceConstants.LOG_LEVEL_WARN);
			}
		}

	}

	/**
	 * Closes a connection with a Client.
	 * 
	 * @param clientName The name to which the Client is bound in the Service.<br>
	 *                   <br>
	 * @throws ServiceException If there is an error when trying to disconnect the
	 *                          Client.<br>
	 *                          <br>
	 */
	public void disconnect(final String clientName) throws ServiceException {

		// Get the Client.
		final ClientFacade client = getClient(clientName);

		// Close the connection.
		if (client == null) {
			getScopeFacade().log("WAREWORK cannot disconnect Client '" + clientName + "' in Service '" + getName()
					+ "' because Client does not exists.", LogServiceConstants.LOG_LEVEL_WARN);
		} else {
			try {
				client.disconnect();
			} catch (ClientException e) {
				throw new ServiceException(getScopeFacade(),
						"WAREWORK cannot disconnect Client '" + clientName + "' at Service '" + getName() + "'.", e,
						LogServiceConstants.LOG_LEVEL_WARN);
			}
		}

	}

	/**
	 * Closes the connection of every Client.
	 * 
	 * @throws ServiceException If there is an error when trying to disconnect the
	 *                          Clients.<br>
	 *                          <br>
	 */
	public void disconnectAll() throws ServiceException {

		// Get the names of all the clients bound to this Service.
		final Enumeration<String> clientNames = getClientNames();

		// Disconnect every Client.
		if (clientNames != null) {
			while (clientNames.hasMoreElements()) {

				// Get the name of a Client.
				final String clientName = (String) clientNames.nextElement();

				// Disconnect the Client.
				disconnect(clientName);

			}
		}

	}

	/**
	 * Validates if a Client is connected.
	 * 
	 * @param clientName The name to which the Client is bound in the Service.<br>
	 *                   <br>
	 * @return <code>true</code> if the Client is connected and <code>false</code>
	 *         if the Client is not connected.<br>
	 *         <br>
	 */
	public boolean isConnected(final String clientName) {

		// Get the Client.
		final ClientFacade client = getClient(clientName);

		// Decide if the Client is connected.
		return (client == null) ? false : client.isConnected();

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Executes a Proxy Service operation.
	 * 
	 * @param operationName Name of the operation to execute.<br>
	 *                      <br>
	 * @param parameters    Operation parameters.<br>
	 *                      <br>
	 * @return Operation result.<br>
	 *         <br>
	 * @throws ServiceException If there is an error when trying to execute the
	 *                          Proxy Service operation.<br>
	 *                          <br>
	 */
	public Object execute(final String operationName, final Map<String, Object> parameters) throws ServiceException {
		return operationHandler.execute(operationName, parameters);
	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Provides a default implementation for initialization of the Service.
	 * 
	 * @throws ServiceException If there is an error when trying to initialize the
	 *                          Proxy Service.<br>
	 *                          <br>
	 */
	protected void initialize() throws ServiceException {

		// Create the operation handler.
		operationHandler = new ProxyServiceOperationHandler();

		// initialize the operation handler.
		operationHandler.init(this);

	}

	/**
	 * Provides a default implementation for the finalization of the Service.
	 * 
	 * @throws ServiceException If there is an error when trying to shutdown the
	 *                          Proxy Service.<br>
	 *                          <br>
	 */
	protected void shutdown() throws ServiceException {
		// DO NOTHING.
	}

	/**
	 * Configures the Service. This method provides a default implementation to
	 * configure multiple Clients in a Service. This method should be overwritten if
	 * a diferent kind of configuration is required.
	 * 
	 * @param config Service's configuration. This method processes only classes of
	 *               type <code>com.warework.core.model.ClientManager</code>.<br>
	 *               <br>
	 * @throws ServiceException If there is an error when trying to configure the
	 *                          Proxy Service.<br>
	 *                          <br>
	 */
	protected void configure(final Object config) throws ServiceException {
		if (config instanceof ProxyService) {

			// Get the Proxy Service configuration.
			final ProxyService clientManagerConfig = (ProxyService) config;

			// Get clients.
			final Enumeration<String> clientNames = clientManagerConfig.getClientNames();

			// Create each Client.
			if (clientNames != null) {

				// Name for every Client found.
				String clientName = null;

				// Create Clients.
				try {
					while (clientNames.hasMoreElements()) {

						// Get the name of a Client.
						clientName = clientNames.nextElement();

						// Get the information of one Client.
						final Client client = clientManagerConfig.getClient(clientName);

						// Get the type of the Connector.
						final Class<? extends ConnectorFacade> connectorType = ReflectionL1Helper.getConnectorType(this,
								client.getConnector());

						// Create the Client.
						createClient(client.getName(), connectorType, Parameter.toHashMap(client.getParameters()));

					}
				} catch (final ClassNotFoundException e) {
					throw new ServiceException(getScopeFacade(),
							"WAREWORK cannot create Client '" + clientName + "' in Service '" + getName()
									+ "' because given Connector class is not found.",
							e, LogServiceConstants.LOG_LEVEL_WARN);
				} catch (final Exception e) {
					throw new ServiceException(getScopeFacade(),
							"WAREWORK cannot create Client '" + clientName + "' in Service '" + getName()
									+ "' because the following exception is thrown: " + e.getMessage(),
							e, LogServiceConstants.LOG_LEVEL_WARN);
				}

			}

		}
	}

	/**
	 * Gets a Client.
	 * 
	 * @param clientName The name to which the Client is bound in the Service.<br>
	 *                   <br>
	 * @return A Client or <code>null</code> if it does not exists.<br>
	 *         <br>
	 */
	protected AbstractClient getClient(final String clientName) {

		// Return the Client.
		if ((clientName != null) && (!clientName.equals(CommonValueL1Constants.STRING_EMPTY))
				&& ((clients != null) && (clients.containsKey(clientName)))) {
			return (AbstractClient) clients.get(clientName);
		}

		// Nothing to return at this point.
		return null;

	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates a Connector.
	 * 
	 * @param clientName           Name of the Client.<br>
	 *                             <br>
	 * @param connectorType        type of the connector.<br>
	 *                             <br>
	 * @param connectionParameters Connector initialization parameters.<br>
	 *                             <br>
	 * @return A new Connector.<br>
	 *         <br>
	 * @throws ServiceException If there is an error when trying to create the
	 *                          Connector.<br>
	 *                          <br>
	 */
	private AbstractConnector createConnector(final String clientName,
			final Class<? extends ConnectorFacade> connectorType, final Map<String, Object> connectionParameters)
			throws ServiceException {
		try {

			// Create a new instance of the Connector.
			final AbstractConnector connector = (AbstractConnector) connectorType.newInstance();

			// Initialize the Connector.
			connector.init(this, clientName, connectionParameters);

			// Return the Connector.
			return connector;

		} catch (final ConnectorException e) {
			throw new ServiceException(getScopeFacade(),
					"WAREWORK cannot create Client '" + clientName + "' in Service '" + getName()
							+ "' because the initialization of the Connector generated the following error: "
							+ e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		} catch (final Exception e) {
			throw new ServiceException(getScopeFacade(),
					"WAREWORK cannot create Client '" + clientName + "' in Service '" + getName()
							+ "' because the instantiation of the Connector generated the following error: "
							+ e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	/**
	 * Validates if the Service have to connect the Client when it is created.
	 * 
	 * @param connectionParameters Parameters (as string values) that specifies how
	 *                             the Connector will create the connections for the
	 *                             Client.<br>
	 *                             <br>
	 * @return <code>true</code> if the Service have to connect the Client and
	 *         <code>false</code> otherwise.<br>
	 *         <br>
	 */
	private boolean connectClient(final Map<String, Object> connectionParameters) {

		// Validation.
		boolean connectClient = false;

		// Decide if Service have to connect the Client.
		if ((connectionParameters != null)
				&& (connectionParameters.get(AbstractConnector.PARAMETER_CONNECT_ON_CREATE) != null)) {

			// Get the value of the parameter.
			final Object connectOnCreate = connectionParameters.get(AbstractConnector.PARAMETER_CONNECT_ON_CREATE);

			// Get the boolean value of the parameter.
			if ((connectOnCreate instanceof Boolean) && (((Boolean) connectOnCreate).equals(Boolean.TRUE))) {
				connectClient = true;
			} else if ((connectOnCreate instanceof String)
					&& (((String) connectOnCreate).equalsIgnoreCase(CommonValueL1Constants.STRING_TRUE))) {
				connectClient = true;
			}

		}

		// Return the validation.
		return connectClient;

	}

}
