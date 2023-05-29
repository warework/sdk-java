package com.warework.core.service;

import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import com.warework.core.service.client.ClientFacade;
import com.warework.core.service.client.connector.ConnectorFacade;
import com.warework.core.util.helper.DataStructureL1Helper;
import com.warework.core.util.helper.ReflectionL1Helper;
import com.warework.service.log.LogServiceConstants;

/**
 * Handles Proxy Services operations.<br>
 * 
 * @author Jose Schiaffino
 * @version 2.0.0
 */
public class ProxyServiceOperationHandler implements OperationHandler {

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Service where to perform the operations.
	private ProxyServiceFacade service;

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Initializes the operation handler.
	 * 
	 * @param proxyService
	 *            Service where to perform the operations.<br>
	 * <br>
	 * @throws ServiceException
	 *             If there is an error when trying to initialize the operation
	 *             handler.<br>
	 * <br>
	 */
	public void init(ServiceFacade proxyService) throws ServiceException {
		if (proxyService instanceof ProxyServiceFacade) {
			service = (ProxyServiceFacade) proxyService;
		} else {
			throw new ServiceException(proxyService.getScopeFacade(),
					"WAREWORK cannot initialize service '"
							+ proxyService.getName()
							+ "' because it is not an instance of '"
							+ ProxyServiceFacade.class.getName() + "'.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	/**
	 * Executes a Proxy Service operation.
	 * 
	 * @param operationName
	 *            Name of the operation to execute.<br>
	 * <br>
	 * @param parameters
	 *            Operation parameters.<br>
	 * <br>
	 * @return Operation result.<br>
	 * <br>
	 * @throws ServiceException
	 *             If there is an error when trying to execute the Proxy Service
	 *             operation.<br>
	 * <br>
	 */
	public Object execute(String operationName, Map<String, Object> parameters)
			throws ServiceException {

		// Execute operation.
		if (operationName
				.equals(ProxyServiceConstants.OPERATION_NAME_CREATE_CLIENT)) {
			executeCreateClient(parameters);
		} else if (operationName
				.equals(ProxyServiceConstants.OPERATION_NAME_GET_CLIENT_NAMES)) {
			return executeGetClientNames();
		} else if (operationName
				.equals(ProxyServiceConstants.OPERATION_NAME_EXISTS_CLIENT)) {
			return executeExistsClient(parameters);
		} else if (operationName
				.equals(ProxyServiceConstants.OPERATION_NAME_REMOVE_CLIENT)) {
			executeRemoveClient(parameters);
		} else if (operationName
				.equals(ProxyServiceConstants.OPERATION_NAME_REMOVE_ALL_CLIENTS)) {
			service.removeAllClients();
		} else if (operationName
				.equals(ProxyServiceConstants.OPERATION_NAME_GET_CLIENT_TYPE)) {
			return executeGetClientType(parameters);
		} else if (operationName
				.equals(ProxyServiceConstants.OPERATION_NAME_IS_CLIENT_TYPE)) {
			return executeIsClientType(parameters);
		} else if (operationName
				.equals(ProxyServiceConstants.OPERATION_NAME_GET_CLIENT_CONNECTION)) {
			return executeGetClientConnection(parameters);
		} else if (operationName
				.equals(ProxyServiceConstants.OPERATION_NAME_CONNECT_CLIENT)) {
			executeConnect(parameters);
		} else if (operationName
				.equals(ProxyServiceConstants.OPERATION_NAME_DISCONNECT_CLIENT)) {
			executeDisconnect(parameters);
		} else if (operationName
				.equals(ProxyServiceConstants.OPERATION_NAME_DISCONNECT_ALL_CLIENTS)) {
			service.disconnectAll();
		} else if (operationName
				.equals(ProxyServiceConstants.OPERATION_NAME_IS_CLIENT_CONNECTED)) {
			return executeIsClientConnected(parameters);
		} else {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ operationName
							+ "' operation because it is not supported by the '"
							+ service.getName() + "' Service.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// At this point, nothing to return.
		return null;

	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates a Client and binds it to a Proxy Service.
	 * 
	 * @param parameters
	 *            Operation parameters.<br>
	 * <br>
	 * @throws ServiceException
	 *             If there is an error when trying to execute the Proxy Service
	 *             operation.<br>
	 * <br>
	 */
	@SuppressWarnings("unchecked")
	private void executeCreateClient(Map<String, Object> parameters)
			throws ServiceException {

		// Get the Client where to execute the operation.
		Object clientName = parameters
				.get(ProxyServiceConstants.OPERATION_PARAMETER_CLIENT_NAME);
		if ((clientName == null) || !(clientName instanceof String)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ ProxyServiceConstants.OPERATION_NAME_CREATE_CLIENT
							+ "' operation because given parameter '"
							+ ProxyServiceConstants.OPERATION_NAME_CREATE_CLIENT
							+ "' is not a string or it does not exists.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the Client Connector.
		Object connector = parameters
				.get(ProxyServiceConstants.OPERATION_PARAMETER_CONNECTOR_TYPE);
		if (connector == null) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ ProxyServiceConstants.OPERATION_NAME_CREATE_CLIENT
							+ "' operation because given parameter '"
							+ ProxyServiceConstants.OPERATION_PARAMETER_CONNECTOR_TYPE
							+ "' is null.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the input variables.
		Object connectionParameters = parameters
				.get(ProxyServiceConstants.OPERATION_PARAMETER_CONNECTOR_PARAMETERS);
		if ((connectionParameters != null)
				&& !(connectionParameters instanceof Map)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ ProxyServiceConstants.OPERATION_NAME_CREATE_CLIENT
							+ "' operation because given parameter '"
							+ ProxyServiceConstants.OPERATION_PARAMETER_CONNECTOR_PARAMETERS
							+ "'  is not an instance of '"
							+ Map.class.getName() + "'.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the class for the Connector.
		Class<? extends ConnectorFacade> connectorType = null;
		if (clientName instanceof String) {
			try {
				connectorType = ReflectionL1Helper.getConnectorType(service,
						(String) connector);
			} catch (ClassNotFoundException e) {
				throw new ServiceException(
						service.getScopeFacade(),
						"WAREWORK cannot execute '"
								+ ProxyServiceConstants.OPERATION_NAME_CREATE_CLIENT
								+ "' operation because given parameter '"
								+ ProxyServiceConstants.OPERATION_PARAMETER_CONNECTOR_TYPE
								+ "'  is not a valid connector. Check the following exception: "
								+ e.getMessage(), e,
						LogServiceConstants.LOG_LEVEL_WARN);
			}
		} else if (clientName instanceof String) {
			connectorType = (Class<? extends ConnectorFacade>) connector;
		} else {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ ProxyServiceConstants.OPERATION_NAME_CREATE_CLIENT
							+ "' operation because given parameter '"
							+ ProxyServiceConstants.OPERATION_PARAMETER_CONNECTOR_TYPE
							+ "'  is not a string or a class object.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Create the Client in the Proxy Service.
		service.createClient((String) clientName, connectorType,
				(Map<String, Object>) connectionParameters);

	}

	/**
	 * Gets the names of all the clients bound to this Service.
	 * 
	 * @return A list with the names of the Clients or <code>null</code> if no
	 *         one exist.<br>
	 * <br>
	 */
	private List<String> executeGetClientNames() {

		// Get the name of the Clients.
		Enumeration<String> clienNames = service.getClientNames();

		// Return a list with the name of the Clients.
		if (clienNames != null) {
			return DataStructureL1Helper.toArrayList(clienNames);
		} else {
			return null;
		}

	}

	/**
	 * Validates if a Client is bound to this Service.
	 * 
	 * @param parameters
	 *            Operation parameters.<br>
	 * <br>
	 * @return @return <code>true</code> if the Client exists and
	 *         <code>false</code> if the Client does not exists.<br>
	 * <br>
	 * @throws ServiceException
	 *             If there is an error when trying to execute the Proxy Service
	 *             operation.<br>
	 * <br>
	 */
	private Boolean executeExistsClient(Map<String, Object> parameters)
			throws ServiceException {

		// Get the Client where to execute the operation.
		Object clientName = parameters
				.get(ProxyServiceConstants.OPERATION_PARAMETER_CLIENT_NAME);
		if ((clientName == null) || !(clientName instanceof String)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ ProxyServiceConstants.OPERATION_NAME_EXISTS_CLIENT
							+ "' operation because given parameter '"
							+ ProxyServiceConstants.OPERATION_PARAMETER_CLIENT_NAME
							+ "' is not a string or it does not exists.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Return if Client exists in the Proxy Service.
		return Boolean.valueOf(service.existsClient((String) clientName));

	}

	/**
	 * Removes a Client bound to the Proxy Service.
	 * 
	 * @param parameters
	 *            Operation parameters.<br>
	 * <br>
	 * @throws ServiceException
	 *             If there is an error when trying to execute the Proxy Service
	 *             operation.<br>
	 * <br>
	 */
	private void executeRemoveClient(Map<String, Object> parameters)
			throws ServiceException {

		// Get the Client where to execute the operation.
		Object clientName = parameters
				.get(ProxyServiceConstants.OPERATION_PARAMETER_CLIENT_NAME);
		if ((clientName == null) || !(clientName instanceof String)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ ProxyServiceConstants.OPERATION_NAME_REMOVE_CLIENT
							+ "' operation because given parameter '"
							+ ProxyServiceConstants.OPERATION_PARAMETER_CLIENT_NAME
							+ "' is not a string or it does not exists.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Remove the Client in the Proxy Service.
		service.removeClient((String) clientName);

	}

	/**
	 * Gets the class that implements the Client.
	 * 
	 * @param parameters
	 *            Operation parameters.<br>
	 * <br>
	 * @return Type of the Client or <code>null</code> if Client does not
	 *         exists.<br>
	 * <br>
	 * @throws ServiceException
	 *             If there is an error when trying to execute the Proxy Service
	 *             operation.<br>
	 * <br>
	 */
	private Class<? extends ClientFacade> executeGetClientType(
			Map<String, Object> parameters) throws ServiceException {

		// Get the Client where to execute the operation.
		Object clientName = parameters
				.get(ProxyServiceConstants.OPERATION_PARAMETER_CLIENT_NAME);
		if ((clientName == null) || !(clientName instanceof String)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ ProxyServiceConstants.OPERATION_NAME_GET_CLIENT_TYPE
							+ "' operation because given parameter '"
							+ ProxyServiceConstants.OPERATION_PARAMETER_CLIENT_NAME
							+ "' is not a string or it does not exists.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Return the Client type.
		return service.getClientType((String) clientName);

	}

	/**
	 * Validates if a Client is the same type of a given class.
	 * 
	 * @param parameters
	 *            Operation parameters.<br>
	 * <br>
	 * @return <code>true</code> if the Client's type is the same as given class
	 *         and <code>false</code> if not.<br>
	 * <br>
	 * @throws ServiceException
	 *             If there is an error when trying to execute the Proxy Service
	 *             operation.<br>
	 * <br>
	 */
	@SuppressWarnings("unchecked")
	private Boolean executeIsClientType(Map<String, Object> parameters)
			throws ServiceException {

		// Get the Client where to execute the operation.
		Object clientName = parameters
				.get(ProxyServiceConstants.OPERATION_PARAMETER_CLIENT_NAME);
		if ((clientName == null) || !(clientName instanceof String)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ ProxyServiceConstants.OPERATION_NAME_IS_CLIENT_TYPE
							+ "' operation because given parameter '"
							+ ProxyServiceConstants.OPERATION_PARAMETER_CLIENT_NAME
							+ "' is not a string or it does not exists.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the Client type to compare with.
		Object type = parameters
				.get(ProxyServiceConstants.OPERATION_PARAMETER_CLIENT_TYPE);
		if ((type == null) || !(type instanceof Class)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ ProxyServiceConstants.OPERATION_NAME_IS_CLIENT_TYPE
							+ "' operation because given parameter '"
							+ ProxyServiceConstants.OPERATION_PARAMETER_CLIENT_TYPE
							+ "' is null or it is not a '"
							+ Class.class.getName() + "'.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Return if given type is the same of the Client type.
		return Boolean.valueOf(service.isClientType((String) clientName,
				(Class<? extends ClientFacade>) type));

	}

	/**
	 * Gets the connection of a Client.
	 * 
	 * @param parameters
	 *            Operation parameters.<br>
	 * <br>
	 * @return Client's connection or <code>null</code> if it's not connected.<br>
	 * <br>
	 * @throws ServiceException
	 *             If there is an error when trying to execute the Proxy Service
	 *             operation.<br>
	 * <br>
	 */
	private Object executeGetClientConnection(Map<String, Object> parameters)
			throws ServiceException {

		// Get the Client where to execute the operation.
		Object clientName = parameters
				.get(ProxyServiceConstants.OPERATION_PARAMETER_CLIENT_NAME);
		if ((clientName == null) || !(clientName instanceof String)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ ProxyServiceConstants.OPERATION_NAME_GET_CLIENT_CONNECTION
							+ "' operation because given parameter '"
							+ ProxyServiceConstants.OPERATION_PARAMETER_CLIENT_NAME
							+ "' is not a string or it does not exists.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Return the connection.
		return service.getConnection((String) clientName);

	}

	/**
	 * Opens a connection with a Client.
	 * 
	 * @param parameters
	 *            Operation parameters.<br>
	 * <br>
	 * @throws ServiceException
	 *             If there is an error when trying to execute the Proxy Service
	 *             operation.<br>
	 * <br>
	 */
	private void executeConnect(Map<String, Object> parameters)
			throws ServiceException {

		// Get the Client where to execute the operation.
		Object clientName = parameters
				.get(ProxyServiceConstants.OPERATION_PARAMETER_CLIENT_NAME);
		if ((clientName == null) || !(clientName instanceof String)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ ProxyServiceConstants.OPERATION_NAME_CONNECT_CLIENT
							+ "' operation because given parameter '"
							+ ProxyServiceConstants.OPERATION_PARAMETER_CLIENT_NAME
							+ "' is not a string or it does not exists.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Connects with the Client.
		service.connect((String) clientName);

	}

	/**
	 * Closes a connection with a Client.
	 * 
	 * @param parameters
	 *            Operation parameters.<br>
	 * <br>
	 * @throws ServiceException
	 *             If there is an error when trying to execute the Proxy Service
	 *             operation.<br>
	 * <br>
	 */
	private void executeDisconnect(Map<String, Object> parameters)
			throws ServiceException {

		// Get the Client where to execute the operation.
		Object clientName = parameters
				.get(ProxyServiceConstants.OPERATION_PARAMETER_CLIENT_NAME);
		if ((clientName == null) || !(clientName instanceof String)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ ProxyServiceConstants.OPERATION_NAME_DISCONNECT_CLIENT
							+ "' operation because given parameter '"
							+ ProxyServiceConstants.OPERATION_PARAMETER_CLIENT_NAME
							+ "' is not a string or it does not exists.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Disconnects with the Client.
		service.disconnect((String) clientName);

	}

	/**
	 * Validates if a Client is connected.
	 * 
	 * @param parameters
	 *            Operation parameters.<br>
	 * <br>
	 * @return @return <code>true</code> if the Client is connected and
	 *         <code>false</code> if the Client is not connected.<br>
	 * <br>
	 * @throws ServiceException
	 *             If there is an error when trying to execute the Proxy Service
	 *             operation.<br>
	 * <br>
	 */
	private Boolean executeIsClientConnected(Map<String, Object> parameters)
			throws ServiceException {

		// Get the Client where to execute the operation.
		Object clientName = parameters
				.get(ProxyServiceConstants.OPERATION_PARAMETER_CLIENT_NAME);
		if ((clientName == null) || !(clientName instanceof String)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ ProxyServiceConstants.OPERATION_NAME_IS_CLIENT_CONNECTED
							+ "' operation because given parameter '"
							+ ProxyServiceConstants.OPERATION_PARAMETER_CLIENT_NAME
							+ "' is not a string or it does not exists.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Return if Client exists in the Proxy Service.
		return Boolean.valueOf(service.isConnected((String) clientName));

	}

}
