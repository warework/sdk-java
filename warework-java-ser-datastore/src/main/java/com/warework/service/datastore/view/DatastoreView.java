package com.warework.service.datastore.view;

import com.warework.core.service.client.ClientException;

/**
 * Defines basic rules for each Data Store View.<br>
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public interface DatastoreView {

	/**
	 * Opens a connection with the Data Store.
	 * 
	 * @throws ClientException If there is an error when trying to connect the Data
	 *                         Store.<br>
	 *                         <br>
	 */
	void connect() throws ClientException;

	/**
	 * Closes the Data Store.
	 * 
	 * @throws ClientException If there is an error when trying to disconnect the
	 *                         Data Store.<br>
	 *                         <br>
	 */
	void disconnect() throws ClientException;

	/**
	 * Validates if the connection is open.
	 * 
	 * @return <code>true</code> if the connection is open and <code>false</code> if
	 *         the connection is closed.<br>
	 *         <br>
	 */
	boolean isConnected();

}
