package com.warework.service.datastore;

import java.util.Enumeration;
import java.util.Map;

import com.warework.core.callback.AbstractCallback;
import com.warework.core.callback.CallbackInvoker;
import com.warework.core.callback.DefaultCallbackImpl;
import com.warework.core.scope.ScopeFacade;
import com.warework.core.service.client.ClientException;
import com.warework.core.util.CommonValueL1Constants;
import com.warework.core.util.helper.DataStructureL1Helper;
import com.warework.service.datastore.client.AbstractDatastore;
import com.warework.service.log.LogServiceConstants;

/**
 * Abstract View for Data Stores that sets a default implementation for a View.
 * This class basically wraps another View and implements the necessary methods
 * to perform the basic operations that it provides. If you plan to create a new
 * View, you should extend this class.<br>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public abstract class AbstractDatastoreView extends AbstractViewsLogic {

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Name of the View.
	private String viewName;

	// Parent View.
	private AbstractViewsLogic parentView;

	// Statement Loader.
	private String statementProvider;

	// Initialization parameters (as string-object pairs) for this View.
	private Map<String, Object> parameters;

	// Milliseconds to wait for the callback operation to be finished.
	private Long callbackWait;

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the connection of the Data Store.
	 * 
	 * @return Connection with the Data Store.<br>
	 *         <br>
	 */
	public Object getConnection() {
		return parentView.getConnection();
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Opens a connection with the Data Store.
	 * 
	 * @throws ClientException If there is an error when trying to connect the Data
	 *                         Store.<br>
	 *                         <br>
	 */
	public void connect() throws ClientException {
		parentView.connect();
	}

	/**
	 * Closes the connection with the Data Store.
	 * 
	 * @throws ClientException If there is an error when trying to disconnect the
	 *                         Data Store.<br>
	 *                         <br>
	 */
	public void disconnect() throws ClientException {
		parentView.disconnect();
	}

	/**
	 * Validates if connection with the Data Store is open.
	 * 
	 * @return <code>true</code> if the connection is open and <code>false</code> if
	 *         the connection is closed.<br>
	 *         <br>
	 */
	public boolean isConnected() {
		return parentView.isConnected();
	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Provides a default implementation for the initialization of the View.
	 * 
	 * @throws ClientException If there is an error when trying to initialize the
	 *                         View.<br>
	 *                         <br>
	 */
	protected void initialize() throws ClientException {
		// DO NOTHING.
	}

	/**
	 * Gets the name of the View.
	 * 
	 * @return View name.<br>
	 *         <br>
	 */
	protected final String getViewName() {
		return viewName;
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Executes a statement that retrieves information from a Data Store.
	 * 
	 * @param statement Query in a specific language accepted by the Data Store.<br>
	 *                  <br>
	 * @param values    Map where the keys are the names of the variables in the
	 *                  query-string loaded and the values those that will replace
	 *                  the variables. Each Data Store may process this values in a
	 *                  specific way (not every Data Store support the replacement
	 *                  of variables). When the query statement loaded from the
	 *                  Provider represents a String object, every variable must be
	 *                  inside '${' and '}' so the variable CAR must be in this
	 *                  query-string as '${CAR}'. Pass <code>null</code> to this
	 *                  parameter to make no changes in the query loaded.<br>
	 *                  <br>
	 * @return Object that holds the result of the query.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to query the Data
	 *                         Store.<br>
	 *                         <br>
	 */
	protected Object query(final Object statement, final Map<String, Object> values) throws ClientException {
		return parentView.query(statement, values);
	}

	/**
	 * Executes a statement that inserts, updates or deletes information in a Data
	 * Store.
	 * 
	 * @param statement Indicates the specific operation to perform and the data to
	 *                  update in the Data Store.<br>
	 *                  <br>
	 * @param values    Map where the keys are the names of the variables in the
	 *                  query-string loaded and the values those that will replace
	 *                  the variables. Each Data Store may process this values in a
	 *                  specific way (not every Data Store support the replacement
	 *                  of variables). When the query statement loaded from the
	 *                  Provider represents a String object, every variable must be
	 *                  inside '${' and '}' so the variable CAR must be in this
	 *                  query-string as '${CAR}'. Pass <code>null</code> to this
	 *                  parameter to make no changes in the query loaded.<br>
	 *                  <br>
	 * @throws ClientException If there is an error when trying to update the Data
	 *                         Store.<br>
	 *                         <br>
	 */
	protected void update(final Object statement, final Map<String, Object> values) throws ClientException {
		parentView.update(statement, values);
	}

	/**
	 * Makes all changes made since the previous commit permanent.
	 * 
	 * @throws ClientException If there is an error when trying to commit the Data
	 *                         Store.<br>
	 *                         <br>
	 */
	public void commit() throws ClientException {
		parentView.commit();
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the Scope where the Data Store Service of this View exists.
	 * 
	 * @return Scope.<br>
	 *         <br>
	 */
	protected final ScopeFacade getScopeFacade() {
		// DO NOT REMOVE THIS METHOD. YOU MUST OVERRIDE 'AbstractViewsLogic'
		// METHOD IN ORDER TO MAKE IT WORK IN ANDROID!!!
		return getService().getScopeFacade();
	}

	/**
	 * Gets the Data Store wrapped by this View.
	 * 
	 * @return Implementation of the Data Store.<br>
	 *         <br>
	 */
	protected final AbstractDatastore getDatastore() {
		return parentView.getDatastore();
	}

	/**
	 * Gets the Parent View of this View. In the stack of Views associated to the
	 * Data Store, the Parent View exists immediately below this View.
	 * 
	 * @return Parent view.<br>
	 *         <br>
	 */
	protected final AbstractViewsLogic getParentView() {
		return parentView;
	}

	/**
	 * Gets a statement from the default Provider.
	 * 
	 * @param name Name of the object to retrieve from the Provider.<br>
	 *             <br>
	 * @return A statement to execute in the Data Store.<br>
	 *         <br>
	 */
	protected Object getStatement(final String name) {
		if (statementProvider == null) {
			return parentView.getStatement(name);
		} else {
			return getService().getScopeFacade().getObject(statementProvider, name);
		}
	}

	/**
	 * Gets a statement from a Provider.
	 * 
	 * @param providerName  Name of the Provider where to search for the
	 *                      statement.<br>
	 *                      <br>
	 * @param statementName Name of the object to retrieve from the Provider.<br>
	 *                      <br>
	 * @return A statement to execute in the Data Store.<br>
	 *         <br>
	 */
	protected Object getStatement(final String providerName, final String statementName) {
		if (providerName == null) {
			return getStatement(statementName);
		} else {
			return getScopeFacade().getObject(providerName, statementName);
		}
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the names of the initialization parameters.
	 * 
	 * @return Initialization parameters' names or null if no one exist.<br>
	 *         <br>
	 */
	protected Enumeration<String> getInitParameterNames() {
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
	protected Object getInitParameter(String name) {
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
	 *         equals, in any case form, to <code>true</code>. Otherwise, this
	 *         method returns <code>false</code> (for example: if it does not
	 *         exist).<br>
	 *         <br>
	 */
	protected boolean isInitParameter(final String name) {

		// Get the value of the parameter.
		final Object value = getInitParameter(name);

		// Decide the boolean value of the parameter.
		if (value != null) {
			if (value instanceof Boolean) {
				return ((Boolean) value).booleanValue();
			} else if ((value instanceof String)
					&& (((String) value).equalsIgnoreCase(CommonValueL1Constants.STRING_TRUE))) {
				return true;
			}
		}

		// At this point, default result is false.
		return false;

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates and initializes a default callback handler.
	 * 
	 * @return Default callback handler.<br>
	 *         <br>
	 */
	protected AbstractCallback createDefaultCallback() {

		// Create a default callback handler.
		final AbstractCallback callback = new DefaultCallbackImpl(getScopeFacade());

		// Set the milliseconds to wait for the callback operation to be
		// finished.
		callback.getControl().setAttribute(AbstractCallback.ATTRIBUTE_MAX_CALLBACK_WAIT, getCallbackWait());

		// Return the callback handler.
		return callback;

	}

	/**
	 * Validates the result of a callback operation.
	 * 
	 * @param callback  Callback operation to validate.<br>
	 *                  <br>
	 * @param operation Words to build the error messages.<br>
	 *                  <br>
	 * @throws ClientException If there is an error when trying to validate the
	 *                         callback.<br>
	 *                         <br>
	 */
	protected void validateCallback(final AbstractCallback callback, final String operation) throws ClientException {

		// Wait until operation is done.
		if (!callback.getControl().waitFinished()) {

			// Get the timeout value.
			final Object maxWait = callback.getAttribute(AbstractCallback.ATTRIBUTE_MAX_CALLBACK_WAIT);

			// Validate timeout.
			if ((maxWait != null) && (maxWait instanceof Long) && (((Long) maxWait)).longValue() > -1) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot " + operation + " in Data Store '" + getName() + "' at Service '"
								+ getService().getName() + "' because the operation took more than " + maxWait
								+ " milliseconds.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			} else {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot " + operation + " in Data Store '" + getName() + "' at Service '"
								+ getService().getName() + "' because the operation took too long."
								+ callback.getControl().getException().getMessage(),
						callback.getControl().getException(), LogServiceConstants.LOG_LEVEL_WARN);
			}

		}

		// Handle failures.
		if (callback.getControl().isFailure()) {
			if (callback.getControl().getException() instanceof ClientException) {
				throw (ClientException) callback.getControl().getException();
			} else if (callback.getControl().getException() != null) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot " + operation + " in Data Store '" + getName() + "' at Service '"
								+ getService().getName() + "' because Data Store reported the following error: "
								+ callback.getControl().getException().getMessage(),
						callback.getControl().getException(), LogServiceConstants.LOG_LEVEL_WARN);
			} else {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot " + operation + " from Data Store '" + getName() + "' at Service '"
								+ getService().getName() + "' because Data Store reported an unknown failure.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}
		}

	}

	/**
	 * Gets the milliseconds for the callback timeout.
	 * 
	 * @return Milliseconds or <code>-1</code> to wait forever.<br>
	 *         <br>
	 */
	protected Long getCallbackWait() {

		// Get the milliseconds.
		if (callbackWait == null) {

			// Get the value of the parameter defined in the connector.
			final Object maxWait = getDatastore().getConnector()
					.getInitParameter(AbstractCallback.ATTRIBUTE_MAX_CALLBACK_WAIT);

			// Parse the milliseconds or specify a default value.
			if (maxWait instanceof Long) {
				callbackWait = (Long) maxWait;
			} else if (maxWait instanceof String) {
				try {
					callbackWait = Long.valueOf((String) maxWait);
				} catch (final Exception e) {

					// Notify about the bad configuration.
					getScopeFacade().log(
							"WAREWORK cannot configure callback timeouts in Data Store '" + getName() + "' at Service '"
									+ getService().getName() + "' because the value of parameter '"
									+ AbstractCallback.ATTRIBUTE_MAX_CALLBACK_WAIT
									+ "' (specified in the Data Store Connector) is not a valid '"
									+ Long.class.getName() + "' number (given value is '" + maxWait
									+ "'). Due to this, callbacks in this Data Store will wait without any time limit.",
							LogServiceConstants.LOG_LEVEL_WARN);

					// Set a default value.
					callbackWait = new Long(-1);

				}
			} else {
				callbackWait = new Long(-1);
			}

		}

		// Return the milliseconds to wait.
		return callbackWait;

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Validates if Data Store is connected.
	 * 
	 * @param operation Words to build the error messages.<br>
	 *                  <br>
	 * @throws ClientException If there is an error when trying to validate the
	 *                         connection.<br>
	 *                         <br>
	 */
	protected void validateConnection(final String operation) throws ClientException {
		if (!isConnected()) {
			throw createConnectionException(operation);
		}
	}

	/**
	 * Validates if Data Store is connected.
	 * 
	 * @param callback  Callback operation where to notify the failure.<br>
	 *                  <br>
	 * @param operation Words to build the error messages.<br>
	 *                  <br>
	 * @return <code>true</code> if Data Store is connected and <code>false</code>
	 *         if not.<br>
	 *         <br>
	 */
	protected boolean validateConnection(final AbstractCallback callback, final String operation) {
		if (isConnected()) {
			return true;
		} else {

			// Invoke failure callback method.
			CallbackInvoker.invokeFailure(callback, null, createConnectionException(operation));

			// Return that connection is closed.
			return false;

		}
	}

	// ///////////////////////////////////////////////////////////////////
	// PACKAGE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Initialization of the View.
	 * 
	 * @param parentView        Parent View of this View. In the stack of Views
	 *                          associated to the Data Store, the Parent View exists
	 *                          immediately below this View.<br>
	 *                          <br>
	 * @param viewName          Name for this View.<br>
	 *                          <br>
	 * @param statementProvider Name of the default Provider for this View that is
	 *                          used to load statements.<br>
	 *                          <br>
	 * @param parameters        Initialization parameters (as string-object pairs)
	 *                          for this View.<br>
	 *                          <br>
	 * @throws ClientException If there is an error when trying to initialize the
	 *                         View.<br>
	 *                         <br>
	 */
	final void init(final AbstractViewsLogic parentView, final String viewName, final String statementProvider,
			final Map<String, Object> parameters) throws ClientException {

		// Set the Parent View.
		this.parentView = parentView;

		// Set the name for this View.
		this.viewName = viewName;

		// Set the name of the default Provider.
		this.statementProvider = statementProvider;

		// Set the initialization parameters.
		this.parameters = parameters;

		// Sub-class initialization (optional).
		initialize();

	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates a exception for closed connections in Data Stores.
	 * 
	 * @param operation Words to build the error messages.<br>
	 *                  <br>
	 * @return Closed connection exception.<br>
	 *         <br>
	 */
	private ClientException createConnectionException(final String operation) {
		return new ClientException(getScopeFacade(),
				"WAREWORK cannot " + operation + " in Data Store '" + getName() + "' at Service '"
						+ getService().getName() + "' because Data Store connection is closed.",
				null, LogServiceConstants.LOG_LEVEL_WARN);
	}

}
