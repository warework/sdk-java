package com.warework.service.datastore.view;

import com.warework.core.service.client.ClientException;

/**
 * View for Database Management Systems.
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public interface DbmsView extends DatastoreView {

	/**
	 * Begins a transaction in the Database Management System.
	 * 
	 * @throws ClientException If there is an error when trying to begin the
	 *                         transaction in the Data Store.<br>
	 *                         <br>
	 */
	void beginTransaction() throws ClientException;

	/**
	 * Rolls back changes in the Database Management System.
	 * 
	 * @throws ClientException If there is an error when trying to perform rollback
	 *                         in the Data Store.<br>
	 *                         <br>
	 */
	void rollback() throws ClientException;

	/**
	 * Commits changes in the Database Management System.
	 * 
	 * @throws ClientException If there is an error when trying to commit the Data
	 *                         Store.<br>
	 *                         <br>
	 */
	void commit() throws ClientException;

}
