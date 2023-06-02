package com.warework.service.datastore;

import java.util.Map;

import com.warework.core.scope.ScopeFacade;
import com.warework.core.service.client.ClientException;
import com.warework.service.datastore.client.AbstractDatastore;
import com.warework.service.datastore.view.DatastoreView;

/**
 * Defines the rules that each Data Store View must implement.<br>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
abstract class AbstractViewsLogic implements DatastoreView {

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/*
	 * These are methods that Warework needs to expose but only in the
	 * implementation class of the View, not in the interface. This let us to
	 * provide simple interface while implementation classes are a little bit
	 * more complex as they provide some extra methods.
	 */

	/**
	 * Gets the Service to which this Data Store belongs.
	 * 
	 * @return Data Store Service.<br>
	 * <br>
	 */
	public final DatastoreServiceFacade getService() {
		return (DatastoreServiceFacade) getDatastore().getService();
	}

	/**
	 * Gets the name of the Data Store where this View belongs to.
	 * 
	 * @return Name of the Data Store.<br>
	 * <br>
	 */
	public final String getName() {
		return getDatastore().getName();
	}

	/**
	 * Gets the connection of the Data Store.
	 * 
	 * @return Connection with the Data Store.<br>
	 * <br>
	 */
	public abstract Object getConnection();

	/**
	 * Gets the name of the View.
	 * 
	 * @return View name.<br>
	 * <br>
	 */
	public String toString() {
		return getViewName();
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
	protected abstract Object query(Object statement, Map<String, Object> values)
			throws ClientException;

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
	protected abstract void update(Object statement, Map<String, Object> values)
			throws ClientException;

	/**
	 * Makes all changes made since the previous commit permanent.
	 * 
	 * @throws ClientException
	 *             If there is an error when trying to commit the Data Store.<br>
	 * <br>
	 */
	protected abstract void commit() throws ClientException;

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the name of the View.
	 * 
	 * @return View name.<br>
	 * <br>
	 */
	protected abstract String getViewName();

	/**
	 * Gets the Scope where the Data Store Service of this View exists.
	 * 
	 * @return Scope.<br>
	 * <br>
	 */
	protected ScopeFacade getScopeFacade() {
		return getService().getScopeFacade();
	}

	/**
	 * Gets the Data Store wrapped by this View.
	 * 
	 * @return Implementation of the Data Store.<br>
	 * <br>
	 */
	protected abstract AbstractDatastore getDatastore();

	/**
	 * Gets the Parent View of this View. In the stack of Views associated to
	 * the Data Store, the Parent View exists immediately below this View.
	 * 
	 * @return Parent view.<br>
	 * <br>
	 */
	protected abstract AbstractViewsLogic getParentView();

	/**
	 * Gets a statement from a Provider.
	 * 
	 * @param name
	 *            Name of the object to retrieve from the Provider.<br>
	 * <br>
	 * @return A statement to execute in the Data Store.<br>
	 * <br>
	 */
	protected abstract Object getStatement(String name);

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
	protected abstract Object getStatement(String providerName,
			String statementName);

}
