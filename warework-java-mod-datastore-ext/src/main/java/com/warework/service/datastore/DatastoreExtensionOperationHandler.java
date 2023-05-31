package com.warework.service.datastore;

import java.util.Hashtable;
import java.util.Map;

import com.warework.core.service.ServiceException;
import com.warework.core.service.client.ClientException;
import com.warework.service.datastore.view.ObjectDatastoreView;
import com.warework.service.log.LogServiceConstants;

/**
 * Handles Data Store Extension Services operations.<br>
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public final class DatastoreExtensionOperationHandler extends DatastoreOperationHandler {

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Executes a Data Store Service operation.
	 * 
	 * @param operationName Name of the operation to execute.<br>
	 *                      <br>
	 * @param parameters    Operation parameters.<br>
	 *                      <br>
	 * @return Operation result.<br>
	 *         <br>
	 * @throws ServiceException If there is an error when trying to execute the
	 *                          operation.<br>
	 *                          <br>
	 */
	public Object execute(final String operationName, final Map<String, Object> parameters) throws ServiceException {

		// Validate the operation to perform.
		if (operationName.equals(DatastoreExtensionServiceConstants.OPERATION_NAME_OBJECT_DATASTORE_SAVE)) {
			executeObjectDatastoreSave(parameters);
		} else if (operationName.equals(DatastoreExtensionServiceConstants.OPERATION_NAME_OBJECT_DATASTORE_UPDATE)) {
			executeObjectDatastoreUpdate(parameters);
		} else if (operationName.equals(DatastoreExtensionServiceConstants.OPERATION_NAME_OBJECT_DATASTORE_DELETE)) {
			executeObjectDatastoreDelete(parameters);
		} else if (operationName
				.equals(DatastoreExtensionServiceConstants.OPERATION_NAME_OBJECT_DATASTORE_DELETE_BY_NAME)) {
			executeObjectDatastoreDeleteByName(parameters);
		} else if (operationName.equals(DatastoreExtensionServiceConstants.OPERATION_NAME_OBJECT_DATASTORE_FIND)) {
			return executeObjectDatastoreFind(parameters);
		} else if (operationName
				.equals(DatastoreExtensionServiceConstants.OPERATION_NAME_OBJECT_DATASTORE_QUERY_BY_NAME)) {
			return executeObjectDatastoreQueryByName(parameters);
		} else if (operationName
				.equals(DatastoreExtensionServiceConstants.OPERATION_NAME_OBJECT_DATASTORE_COUNT_BY_NAME)) {
			return executeObjectDatastoreCountByName(parameters);
		} else {
			return super.execute(operationName, parameters);
		}

		// Nothing to return.
		return null;

	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Executes the save operation in a Object Datastore View.
	 * 
	 * @param parameters Operation parameters.<br>
	 *                   <br>
	 * @throws ServiceException If there is an error when trying to execute the
	 *                          operation.<br>
	 *                          <br>
	 */
	private void executeObjectDatastoreSave(final Map<String, Object> parameters) throws ServiceException {

		// Get the Client where to execute the operation.
		final Object clientName = parameters.get(DatastoreExtensionServiceConstants.OPERATION_PARAMETER_CLIENT_NAME);
		if ((clientName == null) || !(clientName instanceof String)) {
			throw new ServiceException(getService().getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreExtensionServiceConstants.OPERATION_NAME_OBJECT_DATASTORE_SAVE
							+ "' operation because given parameter '"
							+ DatastoreExtensionServiceConstants.OPERATION_PARAMETER_CLIENT_NAME
							+ "' is not a string or it does not exists.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the object to save.
		final Object object = parameters.get(DatastoreExtensionServiceConstants.OPERATION_PARAMETER_VALUES);
		if (object == null) {
			throw new ServiceException(getService().getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreExtensionServiceConstants.OPERATION_NAME_OBJECT_DATASTORE_SAVE
							+ "' operation because given parameter '"
							+ DatastoreExtensionServiceConstants.OPERATION_PARAMETER_VALUES + "' does not exists.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the name of the View where to execute the operation.
		final Object viewName = parameters.get(DatastoreExtensionServiceConstants.OPERATION_PARAMETER_VIEW_NAME);
		if ((viewName == null) || !(viewName instanceof String)) {
			throw new ServiceException(getService().getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreExtensionServiceConstants.OPERATION_NAME_OBJECT_DATASTORE_SAVE
							+ "' operation because given parameter '"
							+ DatastoreExtensionServiceConstants.OPERATION_PARAMETER_VIEW_NAME + "' is not a string.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the View and execute operation.
		final AbstractDatastoreView view = getService().getView((String) clientName, (String) viewName);
		if (view instanceof ObjectDatastoreView) {

			// Get the Object Data Store View.
			final ObjectDatastoreView database = (ObjectDatastoreView) view;

			// Execute the database operation.
			try {
				database.save(object);
			} catch (final ClientException e) {
				throw new ServiceException(getService().getScopeFacade(),
						"WAREWORK cannot execute '"
								+ DatastoreExtensionServiceConstants.OPERATION_NAME_OBJECT_DATASTORE_SAVE
								+ "' operation because the following exception is thrown: " + e.getMessage(),
						e, LogServiceConstants.LOG_LEVEL_WARN);
			}

		} else {
			throw new ServiceException(getService().getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreExtensionServiceConstants.OPERATION_NAME_OBJECT_DATASTORE_SAVE
							+ "' operation because given parameter '"
							+ DatastoreExtensionServiceConstants.OPERATION_PARAMETER_VIEW_NAME
							+ "' does not reference to a '" + ObjectDatastoreView.class.getName() + "' view.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

	}

	/**
	 * Executes the update operation in a Object Datastore View.
	 * 
	 * @param parameters Operation parameters.<br>
	 *                   <br>
	 * @throws ServiceException If there is an error when trying to execute the
	 *                          operation.<br>
	 *                          <br>
	 */
	private void executeObjectDatastoreUpdate(final Map<String, Object> parameters) throws ServiceException {

		// Get the Client where to execute the operation.
		final Object clientName = parameters.get(DatastoreExtensionServiceConstants.OPERATION_PARAMETER_CLIENT_NAME);
		if ((clientName == null) || !(clientName instanceof String)) {
			throw new ServiceException(getService().getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreExtensionServiceConstants.OPERATION_NAME_OBJECT_DATASTORE_UPDATE
							+ "' operation because given parameter '"
							+ DatastoreExtensionServiceConstants.OPERATION_PARAMETER_CLIENT_NAME
							+ "' is not a string or it does not exists.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the object to update.
		final Object object = parameters.get(DatastoreExtensionServiceConstants.OPERATION_PARAMETER_VALUES);
		if (object == null) {
			throw new ServiceException(getService().getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreExtensionServiceConstants.OPERATION_NAME_OBJECT_DATASTORE_UPDATE
							+ "' operation because given parameter '"
							+ DatastoreExtensionServiceConstants.OPERATION_PARAMETER_VALUES + "' does not exists.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the name of the View where to execute the operation.
		final Object viewName = parameters.get(DatastoreExtensionServiceConstants.OPERATION_PARAMETER_VIEW_NAME);
		if ((viewName == null) || !(viewName instanceof String)) {
			throw new ServiceException(getService().getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreExtensionServiceConstants.OPERATION_NAME_OBJECT_DATASTORE_UPDATE
							+ "' operation because given parameter '"
							+ DatastoreExtensionServiceConstants.OPERATION_PARAMETER_VIEW_NAME + "' is not a string.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the View and execute operation.
		final AbstractDatastoreView view = getService().getView((String) clientName, (String) viewName);
		if (view instanceof ObjectDatastoreView) {

			// Get the Object Data Store View.
			final ObjectDatastoreView database = (ObjectDatastoreView) view;

			// Execute the database operation.
			try {
				database.update(object);
			} catch (final ClientException e) {
				throw new ServiceException(getService().getScopeFacade(),
						"WAREWORK cannot execute '"
								+ DatastoreExtensionServiceConstants.OPERATION_NAME_OBJECT_DATASTORE_UPDATE
								+ "' operation because the following exception is thrown: " + e.getMessage(),
						e, LogServiceConstants.LOG_LEVEL_WARN);
			}

		} else {
			throw new ServiceException(getService().getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreExtensionServiceConstants.OPERATION_NAME_OBJECT_DATASTORE_UPDATE
							+ "' operation because given parameter '"
							+ DatastoreExtensionServiceConstants.OPERATION_PARAMETER_VIEW_NAME
							+ "' does not reference to a '" + ObjectDatastoreView.class.getName() + "' view.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

	}

	/**
	 * Executes the delete operation in a Object Datastore View.
	 * 
	 * @param parameters Operation parameters.<br>
	 *                   <br>
	 * @throws ServiceException If there is an error when trying to execute the
	 *                          operation.<br>
	 *                          <br>
	 */
	private void executeObjectDatastoreDelete(final Map<String, Object> parameters) throws ServiceException {

		// Get the Client where to execute the operation.
		final Object clientName = parameters.get(DatastoreExtensionServiceConstants.OPERATION_PARAMETER_CLIENT_NAME);
		if ((clientName == null) || !(clientName instanceof String)) {
			throw new ServiceException(getService().getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreExtensionServiceConstants.OPERATION_NAME_OBJECT_DATASTORE_DELETE
							+ "' operation because given parameter '"
							+ DatastoreExtensionServiceConstants.OPERATION_PARAMETER_CLIENT_NAME
							+ "' is not a string or it does not exists.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the object to delete.
		final Object object = parameters.get(DatastoreExtensionServiceConstants.OPERATION_PARAMETER_VALUES);
		if (object == null) {
			throw new ServiceException(getService().getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreExtensionServiceConstants.OPERATION_NAME_OBJECT_DATASTORE_DELETE
							+ "' operation because given parameter '"
							+ DatastoreExtensionServiceConstants.OPERATION_PARAMETER_VALUES + "' does not exists.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the name of the View where to execute the operation.
		final Object viewName = parameters.get(DatastoreExtensionServiceConstants.OPERATION_PARAMETER_VIEW_NAME);
		if ((viewName == null) || !(viewName instanceof String)) {
			throw new ServiceException(getService().getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreExtensionServiceConstants.OPERATION_NAME_OBJECT_DATASTORE_DELETE
							+ "' operation because given parameter '"
							+ DatastoreExtensionServiceConstants.OPERATION_PARAMETER_VIEW_NAME + "' is not a string.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the View and execute operation.
		final AbstractDatastoreView view = getService().getView((String) clientName, (String) viewName);
		if (view instanceof ObjectDatastoreView) {

			// Get the Object Data Store View.
			ObjectDatastoreView database = (ObjectDatastoreView) view;

			// Execute the database operation.
			try {
				database.delete(object);
			} catch (final ClientException e) {
				throw new ServiceException(getService().getScopeFacade(),
						"WAREWORK cannot execute '"
								+ DatastoreExtensionServiceConstants.OPERATION_NAME_OBJECT_DATASTORE_DELETE
								+ "' operation because the following exception is thrown: " + e.getMessage(),
						e, LogServiceConstants.LOG_LEVEL_WARN);
			}

		} else {
			throw new ServiceException(getService().getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreExtensionServiceConstants.OPERATION_NAME_OBJECT_DATASTORE_DELETE
							+ "' operation because given parameter '"
							+ DatastoreExtensionServiceConstants.OPERATION_PARAMETER_VIEW_NAME
							+ "' does not reference to a '" + ObjectDatastoreView.class.getName() + "' view.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

	}

	/**
	 * Loads a query from a Provider and deletes every object specified by the query
	 * in a Object Datastore View.
	 * 
	 * @param parameters Operation parameters.<br>
	 *                   <br>
	 * @throws ServiceException If there is an error when trying to execute the
	 *                          operation.<br>
	 *                          <br>
	 */
	@SuppressWarnings("unchecked")
	private void executeObjectDatastoreDeleteByName(final Map<String, Object> parameters) throws ServiceException {

		// Get the Client where to execute the operation.
		final Object clientName = parameters.get(DatastoreExtensionServiceConstants.OPERATION_PARAMETER_CLIENT_NAME);
		if ((clientName == null) || !(clientName instanceof String)) {
			throw new ServiceException(getService().getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreExtensionServiceConstants.OPERATION_NAME_OBJECT_DATASTORE_DELETE_BY_NAME
							+ "' operation because given parameter '"
							+ DatastoreExtensionServiceConstants.OPERATION_PARAMETER_CLIENT_NAME
							+ "' is not a string or it does not exists.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the name of the Provider where to retrieve the statement.
		final Object providerName = parameters
				.get(DatastoreExtensionServiceConstants.OPERATION_PARAMETER_PROVIDER_NAME);
		if ((providerName != null) && !(providerName instanceof String)) {
			throw new ServiceException(getService().getScopeFacade(), "WAREWORK cannot execute '"
					+ DatastoreExtensionServiceConstants.OPERATION_NAME_OBJECT_DATASTORE_DELETE_BY_NAME
					+ "' operation because given parameter '"
					+ DatastoreExtensionServiceConstants.OPERATION_PARAMETER_PROVIDER_NAME + "' is not a string.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the name of the statement to load from the Provider.
		final Object statementName = parameters
				.get(DatastoreExtensionServiceConstants.OPERATION_PARAMETER_STATEMENT_NAME);
		if ((statementName == null) || !(statementName instanceof String)) {
			throw new ServiceException(getService().getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreExtensionServiceConstants.OPERATION_NAME_OBJECT_DATASTORE_DELETE_BY_NAME
							+ "' operation because given parameter '"
							+ DatastoreExtensionServiceConstants.OPERATION_PARAMETER_STATEMENT_NAME
							+ "' is not a string or it does not exists.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the values to replace in the statement
		final Object statementValues = parameters.get(DatastoreExtensionServiceConstants.OPERATION_PARAMETER_VALUES);
		if ((statementValues != null) && !(statementValues instanceof Map)) {
			throw new ServiceException(getService().getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreExtensionServiceConstants.OPERATION_NAME_OBJECT_DATASTORE_DELETE_BY_NAME
							+ "' operation because given parameter '"
							+ DatastoreExtensionServiceConstants.OPERATION_PARAMETER_VALUES + "' is not a '"
							+ Map.class.getName() + "'.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the name of the View where to execute the operation.
		final Object viewName = parameters.get(DatastoreExtensionServiceConstants.OPERATION_PARAMETER_VIEW_NAME);
		if ((viewName == null) || !(viewName instanceof String)) {
			throw new ServiceException(getService().getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreExtensionServiceConstants.OPERATION_NAME_OBJECT_DATASTORE_DELETE_BY_NAME
							+ "' operation because given parameter '"
							+ DatastoreExtensionServiceConstants.OPERATION_PARAMETER_VIEW_NAME + "' is not a string.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the View and execute operation.
		final AbstractDatastoreView view = getService().getView((String) clientName, (String) viewName);
		if (view instanceof ObjectDatastoreView) {

			// Get the Object Data Store View.
			final ObjectDatastoreView database = (ObjectDatastoreView) view;

			// Execute the database operation.
			try {
				if (providerName == null) {
					database.executeDeleteByName((String) statementName, (Map<String, Object>) statementValues);
				} else {
					database.executeDeleteByName((String) providerName, (String) statementName,
							(Map<String, Object>) statementValues);
				}
			} catch (final ClientException e) {
				throw new ServiceException(getService().getScopeFacade(),
						"WAREWORK cannot execute '"
								+ DatastoreExtensionServiceConstants.OPERATION_NAME_OBJECT_DATASTORE_DELETE_BY_NAME
								+ "' operation because the following exception is thrown: " + e.getMessage(),
						e, LogServiceConstants.LOG_LEVEL_WARN);
			}

		} else {
			throw new ServiceException(getService().getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreExtensionServiceConstants.OPERATION_NAME_OBJECT_DATASTORE_DELETE_BY_NAME
							+ "' operation because given parameter '"
							+ DatastoreExtensionServiceConstants.OPERATION_PARAMETER_VIEW_NAME
							+ "' does not reference to a '" + ObjectDatastoreView.class.getName() + "' view.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

	}

	/**
	 * Finds an object by its values in a Object Datastore View.
	 * 
	 * @param parameters Operation parameters.<br>
	 *                   <br>
	 * @return Object from the Data Store that matches the type and the values of
	 *         the given object.<br>
	 *         <br>
	 * @throws ServiceException If there is an error when trying to execute the
	 *                          operation.<br>
	 *                          <br>
	 */
	private Object executeObjectDatastoreFind(final Map<String, Object> parameters) throws ServiceException {

		// Get the Client where to execute the operation.
		final Object clientName = parameters.get(DatastoreExtensionServiceConstants.OPERATION_PARAMETER_CLIENT_NAME);
		if ((clientName == null) || !(clientName instanceof String)) {
			throw new ServiceException(getService().getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreExtensionServiceConstants.OPERATION_NAME_OBJECT_DATASTORE_FIND
							+ "' operation because given parameter '"
							+ DatastoreExtensionServiceConstants.OPERATION_PARAMETER_CLIENT_NAME
							+ "' is not a string or it does not exists.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the filter.
		final Object filter = parameters.get(DatastoreExtensionServiceConstants.OPERATION_PARAMETER_FILTER);
		if (filter == null) {
			throw new ServiceException(getService().getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreExtensionServiceConstants.OPERATION_NAME_OBJECT_DATASTORE_FIND
							+ "' operation because parameter '"
							+ DatastoreExtensionServiceConstants.OPERATION_PARAMETER_FILTER + "' does not exists.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the name of the View where to execute the operation.
		final Object viewName = parameters.get(DatastoreExtensionServiceConstants.OPERATION_PARAMETER_VIEW_NAME);
		if ((viewName == null) || !(viewName instanceof String)) {
			throw new ServiceException(getService().getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreExtensionServiceConstants.OPERATION_NAME_OBJECT_DATASTORE_FIND
							+ "' operation because given parameter '"
							+ DatastoreExtensionServiceConstants.OPERATION_PARAMETER_VIEW_NAME + "' is not a string.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the View and execute operation.
		final AbstractDatastoreView view = getService().getView((String) clientName, (String) viewName);
		if (view instanceof ObjectDatastoreView) {

			// Get the Object Data Store View.
			final ObjectDatastoreView database = (ObjectDatastoreView) view;

			// Execute the database operation.
			try {
				return database.find(filter);
			} catch (final ClientException e) {
				throw new ServiceException(getService().getScopeFacade(),
						"WAREWORK cannot execute '"
								+ DatastoreExtensionServiceConstants.OPERATION_NAME_OBJECT_DATASTORE_FIND
								+ "' operation because the following exception is thrown: " + e.getMessage(),
						e, LogServiceConstants.LOG_LEVEL_WARN);
			}

		} else {
			throw new ServiceException(getService().getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreExtensionServiceConstants.OPERATION_NAME_OBJECT_DATASTORE_FIND
							+ "' operation because given parameter '"
							+ DatastoreExtensionServiceConstants.OPERATION_PARAMETER_VIEW_NAME
							+ "' does not reference to a '" + ObjectDatastoreView.class.getName() + "' view.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

	}

	/**
	 * Loads a <code>Query</code> object from a Provider and executes it in a Object
	 * Datastore View.
	 * 
	 * @param parameters Operation parameters.<br>
	 *                   <br>
	 * @return List of objects from the Data Store that matches the type and the
	 *         filters specified in the query loaded from the Provider.<br>
	 *         <br>
	 * @throws ServiceException If there is an error when trying to execute the
	 *                          operation.<br>
	 *                          <br>
	 */
	@SuppressWarnings("unchecked")
	private Object executeObjectDatastoreQueryByName(final Map<String, Object> parameters) throws ServiceException {

		// Get the Client where to execute the operation.
		final Object clientName = parameters.get(DatastoreExtensionServiceConstants.OPERATION_PARAMETER_CLIENT_NAME);
		if ((clientName == null) || !(clientName instanceof String)) {
			throw new ServiceException(getService().getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreExtensionServiceConstants.OPERATION_NAME_OBJECT_DATASTORE_QUERY_BY_NAME
							+ "' operation because given parameter '"
							+ DatastoreExtensionServiceConstants.OPERATION_PARAMETER_CLIENT_NAME
							+ "' is not a string or it does not exists.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the name of the Provider where to retrieve the statement.
		final Object providerName = parameters
				.get(DatastoreExtensionServiceConstants.OPERATION_PARAMETER_PROVIDER_NAME);
		if ((providerName != null) && !(providerName instanceof String)) {
			throw new ServiceException(getService().getScopeFacade(), "WAREWORK cannot execute '"
					+ DatastoreExtensionServiceConstants.OPERATION_NAME_OBJECT_DATASTORE_QUERY_BY_NAME
					+ "' operation because given parameter '"
					+ DatastoreExtensionServiceConstants.OPERATION_PARAMETER_PROVIDER_NAME + "' is not a string.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the name of the statement to load from the Provider.
		final Object statementName = parameters
				.get(DatastoreExtensionServiceConstants.OPERATION_PARAMETER_STATEMENT_NAME);
		if ((statementName == null) || !(statementName instanceof String)) {
			throw new ServiceException(getService().getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreExtensionServiceConstants.OPERATION_NAME_OBJECT_DATASTORE_QUERY_BY_NAME
							+ "' operation because given parameter '"
							+ DatastoreExtensionServiceConstants.OPERATION_PARAMETER_STATEMENT_NAME
							+ "' is not a string or it does not exists.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the values to replace in the statement
		final Object statementValues = parameters.get(DatastoreExtensionServiceConstants.OPERATION_PARAMETER_VALUES);
		if ((statementValues != null) && !(statementValues instanceof Map)) {
			throw new ServiceException(getService().getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreExtensionServiceConstants.OPERATION_NAME_OBJECT_DATASTORE_QUERY_BY_NAME
							+ "' operation because given parameter '"
							+ DatastoreExtensionServiceConstants.OPERATION_PARAMETER_VALUES + "' is not a '"
							+ Map.class.getName() + "'.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the page.
		final Object page = parameters.get(DatastoreServiceConstants.OPERATION_PARAMETER_PAGE);
		if ((page != null) && !(page instanceof Integer)) {
			throw new ServiceException(getService().getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreExtensionServiceConstants.OPERATION_NAME_OBJECT_DATASTORE_QUERY_BY_NAME
							+ "' operation because given parameter '"
							+ DatastoreExtensionServiceConstants.OPERATION_PARAMETER_PAGE + "' is not an '"
							+ Integer.class.getName() + "'.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the size of the page.
		final Object pageSize = parameters.get(DatastoreServiceConstants.OPERATION_PARAMETER_PAGE_SIZE);
		if ((pageSize != null) && !(pageSize instanceof Integer)) {
			throw new ServiceException(getService().getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreExtensionServiceConstants.OPERATION_NAME_OBJECT_DATASTORE_QUERY_BY_NAME
							+ "' operation because given parameter '"
							+ DatastoreExtensionServiceConstants.OPERATION_PARAMETER_PAGE_SIZE + "' is not an '"
							+ Integer.class.getName() + "'.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the name of the View where to execute the operation.
		final Object viewName = parameters.get(DatastoreExtensionServiceConstants.OPERATION_PARAMETER_VIEW_NAME);
		if ((viewName == null) || !(viewName instanceof String)) {
			throw new ServiceException(getService().getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreExtensionServiceConstants.OPERATION_NAME_OBJECT_DATASTORE_QUERY_BY_NAME
							+ "' operation because given parameter '"
							+ DatastoreExtensionServiceConstants.OPERATION_PARAMETER_VIEW_NAME + "' is not a string.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the View and execute operation.
		final AbstractDatastoreView view = getService().getView((String) clientName, (String) viewName);
		if (view instanceof ObjectDatastoreView) {

			// Get the Object Data Store View.
			final ObjectDatastoreView database = (ObjectDatastoreView) view;

			// Get the page.
			int queryPage = -1;
			if (page != null) {
				queryPage = ((Integer) page).intValue();
			}

			// Get the size of the page.
			int queryPageSize = -1;
			if (pageSize != null) {
				queryPageSize = ((Integer) pageSize).intValue();
			}

			// Execute the database operation.
			try {
				if (providerName == null) {
					return database.executeQueryByName((String) statementName, (Map<String, Object>) statementValues,
							queryPage, queryPageSize);
				} else {
					return database.executeQueryByName((String) providerName, (String) statementName,
							(Map<String, Object>) statementValues, queryPage, queryPageSize);
				}
			} catch (final ClientException e) {
				throw new ServiceException(getService().getScopeFacade(),
						"WAREWORK cannot execute '"
								+ DatastoreExtensionServiceConstants.OPERATION_NAME_OBJECT_DATASTORE_QUERY_BY_NAME
								+ "' operation because the following exception is thrown: " + e.getMessage(),
						e, LogServiceConstants.LOG_LEVEL_WARN);
			}

		} else {
			throw new ServiceException(getService().getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreExtensionServiceConstants.OPERATION_NAME_OBJECT_DATASTORE_QUERY_BY_NAME
							+ "' operation because given parameter '"
							+ DatastoreExtensionServiceConstants.OPERATION_PARAMETER_VIEW_NAME
							+ "' does not reference to a '" + ObjectDatastoreView.class.getName() + "' view.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

	}

	/**
	 * Counts the number of objects of a specified query in a Object Datastore View.
	 * 
	 * @param parameters Operation parameters.<br>
	 *                   <br>
	 * @return List of objects from the Data Store that matches the type and the
	 *         filters specified in the query loaded from the Provider.<br>
	 *         <br>
	 * @throws ServiceException If there is an error when trying to execute the
	 *                          operation.<br>
	 *                          <br>
	 */
	@SuppressWarnings("unchecked")
	private Object executeObjectDatastoreCountByName(final Map<String, Object> parameters) throws ServiceException {

		// Get the Client where to execute the operation.
		final Object clientName = parameters.get(DatastoreExtensionServiceConstants.OPERATION_PARAMETER_CLIENT_NAME);
		if ((clientName == null) || !(clientName instanceof String)) {
			throw new ServiceException(getService().getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreExtensionServiceConstants.OPERATION_NAME_OBJECT_DATASTORE_COUNT_BY_NAME
							+ "' operation because given parameter '"
							+ DatastoreExtensionServiceConstants.OPERATION_PARAMETER_CLIENT_NAME
							+ "' is not a string or it does not exists.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the name of the Provider where to retrieve the statement.
		final Object providerName = parameters
				.get(DatastoreExtensionServiceConstants.OPERATION_PARAMETER_PROVIDER_NAME);
		if ((providerName != null) && !(providerName instanceof String)) {
			throw new ServiceException(getService().getScopeFacade(), "WAREWORK cannot execute '"
					+ DatastoreExtensionServiceConstants.OPERATION_NAME_OBJECT_DATASTORE_COUNT_BY_NAME
					+ "' operation because given parameter '"
					+ DatastoreExtensionServiceConstants.OPERATION_PARAMETER_PROVIDER_NAME + "' is not a string.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the name of the statement to load from the Provider.
		final Object statementName = parameters
				.get(DatastoreExtensionServiceConstants.OPERATION_PARAMETER_STATEMENT_NAME);
		if ((statementName == null) || !(statementName instanceof String)) {
			throw new ServiceException(getService().getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreExtensionServiceConstants.OPERATION_NAME_OBJECT_DATASTORE_COUNT_BY_NAME
							+ "' operation because given parameter '"
							+ DatastoreExtensionServiceConstants.OPERATION_PARAMETER_STATEMENT_NAME
							+ "' is not a string or it does not exists.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the values to replace in the statement
		final Object statementValues = parameters.get(DatastoreExtensionServiceConstants.OPERATION_PARAMETER_VALUES);
		if ((statementValues != null) && !(statementValues instanceof Map)) {
			throw new ServiceException(getService().getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreExtensionServiceConstants.OPERATION_NAME_OBJECT_DATASTORE_COUNT_BY_NAME
							+ "' operation because given parameter '"
							+ DatastoreExtensionServiceConstants.OPERATION_PARAMETER_VALUES + "' is not a '"
							+ Hashtable.class.getName() + "'.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the name of the View where to execute the operation.
		final Object viewName = parameters.get(DatastoreExtensionServiceConstants.OPERATION_PARAMETER_VIEW_NAME);
		if ((viewName == null) || !(viewName instanceof String)) {
			throw new ServiceException(getService().getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreExtensionServiceConstants.OPERATION_NAME_OBJECT_DATASTORE_COUNT_BY_NAME
							+ "' operation because given parameter '"
							+ DatastoreExtensionServiceConstants.OPERATION_PARAMETER_VIEW_NAME + "' is not a string.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the View and execute operation.
		final AbstractDatastoreView view = getService().getView((String) clientName, (String) viewName);
		if (view instanceof ObjectDatastoreView) {

			// Get the Object Data Store View.
			final ObjectDatastoreView database = (ObjectDatastoreView) view;

			// Execute the database operation.
			try {
				if (providerName == null) {
					return database.executeCountByName((String) statementName, (Map<String, Object>) statementValues);
				} else {
					return database.executeCountByName((String) providerName, (String) statementName,
							(Map<String, Object>) statementValues);
				}
			} catch (final ClientException e) {
				throw new ServiceException(getService().getScopeFacade(),
						"WAREWORK cannot execute '"
								+ DatastoreExtensionServiceConstants.OPERATION_NAME_OBJECT_DATASTORE_COUNT_BY_NAME
								+ "' operation because the following exception is thrown: " + e.getMessage(),
						e, LogServiceConstants.LOG_LEVEL_WARN);
			}

		} else {
			throw new ServiceException(getService().getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreExtensionServiceConstants.OPERATION_NAME_OBJECT_DATASTORE_COUNT_BY_NAME
							+ "' operation because given parameter '"
							+ DatastoreExtensionServiceConstants.OPERATION_PARAMETER_VIEW_NAME
							+ "' does not reference to a '" + ObjectDatastoreView.class.getName() + "' view.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

	}

}
