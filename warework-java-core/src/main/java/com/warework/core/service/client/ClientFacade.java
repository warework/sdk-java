package com.warework.core.service.client;

import com.warework.core.service.ProxyServiceFacade;
import com.warework.core.service.client.AbstractClient.ClientAttributes;
import com.warework.core.service.client.connector.ConnectorFacade;

/**
 * This facade defines basic operations for Clients managed by a Proxy Service.
 * 
 * @author Jose Schiaffino
 * @version 2.0.0
 */
public interface ClientFacade {

	/**
	 * Gets the Service to which this Client belongs.
	 * 
	 * @return Service.<br>
	 * <br>
	 */
	ProxyServiceFacade getService();

	/**
	 * Gets the name of this Client.
	 * 
	 * @return Name of the Client.<br>
	 * <br>
	 */
	String getName();

	/**
	 * Opens a connection with the Client.
	 * 
	 * @throws ClientException
	 *             If there is an error when trying to connect the Client.<br>
	 * <br>
	 */
	void connect() throws ClientException;

	/**
	 * Closes the connection with the Client.
	 * 
	 * @throws ClientException
	 *             If there is an error when trying to disconnect the Client.<br>
	 * <br>
	 */
	void disconnect() throws ClientException;

	/**
	 * Validates if the connection is open.
	 * 
	 * @return <code>true</code> if the connection is open and
	 *         <code>false</code> if the connection is closed.<br>
	 * <br>
	 */
	boolean isConnected();

	/**
	 * Gets the connection of the Client.
	 * 
	 * @return Client's connection.<br>
	 * <br>
	 */
	Object getConnection();

	/**
	 * Gets the Connector of the Client.
	 * 
	 * @return Client's Connector.<br>
	 * <br>
	 */
	ConnectorFacade getConnector();

	/**
	 * Gets Client attributes that define specific characteristics or features
	 * of the Client.
	 * 
	 * @return Client attributes manager.<br>
	 * <br>
	 */
	ClientAttributes getClientAttributes();

}
