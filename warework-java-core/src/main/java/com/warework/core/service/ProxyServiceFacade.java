package com.warework.core.service;

import java.util.Enumeration;
import java.util.Map;

import com.warework.core.service.client.ClientFacade;
import com.warework.core.service.client.connector.ConnectorFacade;

/**
 * This facade is the entry point to perform operations with Services that
 * handle multiple Clients. To create a new instance use the
 * <code>createService</code> method of the Scope where the Service will be.<br>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public interface ProxyServiceFacade extends ServiceFacade {

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
	 * @param connectionParameters Parameters that specifies how the Connector will
	 *                             create the connections for the Client.<br>
	 *                             <br>
	 * @throws ServiceException If there is an error when trying to create the
	 *                          Client.<br>
	 *                          <br>
	 */
	void createClient(final String clientName, final Class<? extends ConnectorFacade> connectorType,
			final Map<String, Object> connectionParameters) throws ServiceException;

	/**
	 * Gets the names of all the clients bound to this Service.
	 * 
	 * @return Names of the clients.<br>
	 *         <br>
	 */
	Enumeration<String> getClientNames();

	/**
	 * Validates if a Client is bound to this Service.
	 * 
	 * @param clientName The name to which the Client is bound in the Service.<br>
	 *                   <br>
	 * @return <code>true</code> if the Client exists and <code>false</code> if the
	 *         Client does not exists.<br>
	 *         <br>
	 */
	boolean existsClient(final String clientName);

	/**
	 * Removes a Client bound to this Service. If this Service does not have a
	 * Client bound with the specified name, this method does nothing. If Client is
	 * connected, this method disconnects it first.
	 * 
	 * @param clientName The name to which the Client is bound in the Service.<br>
	 *                   <br>
	 * @throws ServiceException If there is an error when trying to remove the
	 *                          Client.<br>
	 *                          <br>
	 */
	void removeClient(final String clientName) throws ServiceException;

	/**
	 * Removes all the clients bound to this Service.
	 * 
	 * @throws ServiceException If there is an error when trying to remove the
	 *                          Clients.<br>
	 *                          <br>
	 */
	void removeAllClients() throws ServiceException;

	/**
	 * Gets the class that implements the Client.
	 * 
	 * @param clientName The name to which the Client is bound in the Service.<br>
	 *                   <br>
	 * @return Type of the Client or <code>null</code> if Client does not
	 *         exists.<br>
	 *         <br>
	 */
	Class<? extends ClientFacade> getClientType(final String clientName);

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
	boolean isClientType(final String clientName, final Class<? extends ClientFacade> clientType);

	/**
	 * Gets the connection of a Client. First you'll need to connect the Client to
	 * retrieve the connection, otherwise this method will return <code>null</code>.
	 * 
	 * @param clientName The name to which the Client is bound in the Service.<br>
	 *                   <br>
	 * @return Data store's connection or <code>null</code> if the data store does
	 *         not exists.<br>
	 *         <br>
	 */
	Object getConnection(final String clientName);

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
	void connect(final String clientName) throws ServiceException;

	/**
	 * Closes a connection with a Client.
	 * 
	 * @param clientName The name to which the Client is bound in the Service.<br>
	 *                   <br>
	 * @throws ServiceException If there is an error when trying to disconnect the
	 *                          Client.<br>
	 *                          <br>
	 */
	void disconnect(final String clientName) throws ServiceException;

	/**
	 * Closes the connection of every Client.
	 * 
	 * @throws ServiceException If there is an error when trying to disconnect the
	 *                          Clients.<br>
	 *                          <br>
	 */
	void disconnectAll() throws ServiceException;

	/**
	 * Validates if a Client is connected.
	 * 
	 * @param clientName The name to which the Client is bound in the Service.<br>
	 *                   <br>
	 * @return <code>true</code> if the Client is connected and <code>false</code>
	 *         if the Client is not connected.<br>
	 *         <br>
	 */
	boolean isConnected(final String clientName);

}
