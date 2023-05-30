package com.warework.service.datastore;

import java.util.Map;

import com.warework.core.service.client.ClientException;
import com.warework.core.service.client.connector.ConnectorFacade;
import com.warework.service.datastore.client.AbstractDatastore;
import com.warework.service.datastore.client.DatastoreFacade;
import com.warework.service.log.LogServiceConstants;

/**
 * Default View for each Data Store. It is managed automatically by the
 * Framework, do not use it! This class basically wraps a Data Store and
 * implements the necessary methods to perform the basic operations that it
 * provides. <br>
 * 
 * @author Jose Schiaffino
 * @version 2.0.0
 */
final class DefaultView extends AbstractViewsLogic {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// Name for the default View.
	static final String DEFAULT_VIEW_NAME = "default-view";

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// The original Data Store.
	private AbstractDatastore datastore;

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * This constructor does not perform any operation.
	 */
	private DefaultView() {
	}

	/**
	 * Initializes a default view for a Data Store.
	 * 
	 * @param connector
	 *            Connector that specifies which Data Store to create and how to
	 *            create the connections for the Data Store.<br>
	 * <br>
	 * @throws ClientException
	 *             If there is an error when trying to get the Data Store.<br>
	 * <br>
	 */
	DefaultView(ConnectorFacade connector) throws ClientException {

		// Invoke the default constructor.
		this();

		// Create a new instance of the Data Store.
		try {
			datastore = (AbstractDatastore) connector.getClient();
		} catch (Exception e) {
			throw new ClientException(
					connector.getService().getScopeFacade(),
					"WAREWORK cannot create Data Store '"
							+ connector.getClientName()
							+ "' in service '"
							+ connector.getService().getName()
							+ "' because Data Store is not an implementation of the '"
							+ DatastoreFacade.class.getName() + "' interface.",
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}

	}

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the connection of the Data Store.
	 * 
	 * @return Connection with the Data Store.<br>
	 * <br>
	 */
	public Object getConnection() {
		return datastore.getConnection();
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Opens a connection with the Data Store.
	 * 
	 * @throws ClientException
	 *             If there is an error when trying to connect the Data Store.<br>
	 * <br>
	 */
	public void connect() throws ClientException {
		datastore.connect();
	}

	/**
	 * Closes the Data Store.
	 * 
	 * @throws ClientException
	 *             If there is an error when trying to disconnect the Data
	 *             Store.<br>
	 * <br>
	 */
	public void disconnect() throws ClientException {
		datastore.disconnect();
	}

	/**
	 * Validates if the connection is open.
	 * 
	 * @return <code>true</code> if the connection is open and
	 *         <code>false</code> if the connection is closed.<br>
	 * <br>
	 */
	public boolean isConnected() {
		return datastore.isConnected();
	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Executes a statement that retrieves information from a Data Store.
	 * 
	 * @param statement
	 *            Query in a specific language accepted by the Data Store.<br>
	 * <br>
	 * @param values
	 *            Map where the keys are the names of the variables in the
	 *            query-string loaded and the values those that will replace the
	 *            variables. Each Data Store may process this values in a
	 *            specific way (not every Data Store support the replacement of
	 *            variables). When the query statement loaded from the Provider
	 *            represents a String object, every variable must be inside '${'
	 *            and '}' so the variable CAR must be in this query-string as
	 *            '${CAR}'. Pass <code>null</code> to this parameter to make no
	 *            changes in the query loaded.<br>
	 * <br>
	 * @return Object that holds the result of the query.<br>
	 * <br>
	 * @throws ClientException
	 *             If there is an error when trying to query the Data Store.<br>
	 * <br>
	 */
	protected Object query(Object statement, Map<String, Object> values)
			throws ClientException {
		return datastore.query(statement, values);
	}

	/**
	 * Executes a statement that inserts, updates or deletes information in a
	 * Data Store.
	 * 
	 * @param statement
	 *            Indicates the specific operation to perform and the data to
	 *            update in the Data Store.<br>
	 * <br>
	 * @param values
	 *            Map where the keys are the names of the variables in the
	 *            query-string loaded and the values those that will replace the
	 *            variables. Each Data Store may process this values in a
	 *            specific way (not every Data Store support the replacement of
	 *            variables). When the query statement loaded from the Provider
	 *            represents a String object, every variable must be inside '${'
	 *            and '}' so the variable CAR must be in this query-string as
	 *            '${CAR}'. Pass <code>null</code> to this parameter to make no
	 *            changes in the query loaded.<br>
	 * <br>
	 * @throws ClientException
	 *             If there is an error when trying to update the Data Store.<br>
	 * <br>
	 */
	protected void update(Object statement, Map<String, Object> values)
			throws ClientException {
		datastore.update(statement, values);
	}

	/**
	 * Makes all changes made since the previous commit permanent.
	 * 
	 * @throws ClientException
	 *             If there is an error when trying to commit the Data Store.<br>
	 * <br>
	 */
	protected void commit() throws ClientException {
		datastore.commit();
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the name of the View.
	 * 
	 * @return View name.<br>
	 * <br>
	 */
	protected String getViewName() {
		return DEFAULT_VIEW_NAME;
	}

	/**
	 * Gets the Data Store wrapped by this View.
	 * 
	 * @return Implementation of the Data Store.<br>
	 * <br>
	 */
	protected AbstractDatastore getDatastore() {
		return datastore;
	}

	/**
	 * Gets the Parent View of this View. In the stack of Views associated to
	 * the Data Store, the Parent View exists immediately below this View.
	 * 
	 * @return <code>null</code> because no Parent View exist for this View.<br>
	 * <br>
	 */
	protected AbstractViewsLogic getParentView() {
		return null;
	}

	/**
	 * Gets a statement from a Provider.
	 * 
	 * @param name
	 *            Name of the object to retrieve from the Provider.<br>
	 * <br>
	 * @return A statement to execute in the Data Store.<br>
	 * <br>
	 */
	protected Object getStatement(String name) {
		return null;
	}

	/**
	 * Gets a statement from a Provider.
	 * 
	 * @param providerName
	 *            Name of the Provider where to search for the statement.<br>
	 * <br>
	 * @param statementName
	 *            Name of the object to retrieve from the Provider.<br>
	 * <br>
	 * @return A statement to execute in the Data Store.<br>
	 * <br>
	 */
	protected Object getStatement(String providerName, String statementName) {
		return null;
	}

}
