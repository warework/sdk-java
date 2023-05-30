package com.warework.service.datastore;

import java.util.Map;

import com.warework.core.service.ProxyServiceOperationHandler;
import com.warework.core.service.ServiceException;
import com.warework.core.service.ServiceFacade;
import com.warework.core.service.client.ClientException;
import com.warework.core.util.helper.DataStructureL1Helper;
import com.warework.service.datastore.view.DbmsView;
import com.warework.service.datastore.view.KeyValueView;
import com.warework.service.datastore.view.RdbmsView;
import com.warework.service.log.LogServiceConstants;

/**
 * Handles Data Store Services operations.<br>
 * 
 * @author Jose Schiaffino
 * @version 2.0.1
 */
public class DatastoreOperationHandler extends ProxyServiceOperationHandler {

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Service where to perform the operations.
	private DatastoreServiceFacade service;

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Initializes the operation handler.
	 * 
	 * @param datastoreService
	 *            Service where to perform the operations.<br>
	 * <br>
	 * @throws ServiceException
	 *             If there is an error when trying to initialize the operation
	 *             handler.<br>
	 * <br>
	 */
	public void init(ServiceFacade datastoreService) throws ServiceException {

		// Validate service type.
		if (datastoreService instanceof DatastoreServiceFacade) {
			service = (DatastoreServiceFacade) datastoreService;
		} else {
			throw new ServiceException(datastoreService.getScopeFacade(),
					"WAREWORK cannot initialize service '"
							+ datastoreService.getName()
							+ "' because it is not an instance of '"
							+ DatastoreServiceFacade.class.getName() + "'.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Initialize Proxy Service.
		super.init(datastoreService);

	}

	/**
	 * Executes a Data Store Service operation.
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
	 *             If there is an error when trying to execute the operation.<br>
	 * <br>
	 */
	public Object execute(String operationName, Map<String, Object> parameters)
			throws ServiceException {

		// Validate the operation to perform.
		if (operationName
				.equals(DatastoreServiceConstants.OPERATION_NAME_QUERY)) {
			return executeQuery(parameters);
		} else if (operationName
				.equals(DatastoreServiceConstants.OPERATION_NAME_QUERY_BY_NAME)) {
			return executeQueryByName(parameters);
		} else if (operationName
				.equals(DatastoreServiceConstants.OPERATION_NAME_UPDATE)) {
			executeUpdate(parameters);
		} else if (operationName
				.equals(DatastoreServiceConstants.OPERATION_NAME_UPDATE_BY_NAME)) {
			executeUpdateByName(parameters);
		} else if (operationName
				.equals(DatastoreServiceConstants.OPERATION_NAME_COMMIT)) {
			executeCommit(parameters);
		} else if (operationName
				.equals(DatastoreServiceConstants.OPERATION_NAME_DBMS_BEGIN_TRANSACTION)) {
			executeDBMSBeginTransaction(parameters);
		} else if (operationName
				.equals(DatastoreServiceConstants.OPERATION_NAME_DBMS_ROLLBACK)) {
			executeDBMSRollback(parameters);
		} else if (operationName
				.equals(DatastoreServiceConstants.OPERATION_NAME_RDBMS_QUERY)) {
			return executeRDBMSQuery(parameters);
		} else if (operationName
				.equals(DatastoreServiceConstants.OPERATION_NAME_RDBMS_QUERY_BY_NAME)) {
			return executeRDBMSQueryByName(parameters);
		} else if (operationName
				.equals(DatastoreServiceConstants.OPERATION_NAME_RDBMS_UPDATE)) {
			executeRDBMSUpdate(parameters);
		} else if (operationName
				.equals(DatastoreServiceConstants.OPERATION_NAME_RDBMS_UPDATE_BY_NAME)) {
			executeRDBMSUpdateByName(parameters);
		} else if (operationName
				.equals(DatastoreServiceConstants.OPERATION_NAME_KEY_VALUE_PUT)) {
			executeKeyValuePut(parameters);
		} else if (operationName
				.equals(DatastoreServiceConstants.OPERATION_NAME_KEY_VALUE_GET)) {
			return executeKeyValueGet(parameters);
		} else if (operationName
				.equals(DatastoreServiceConstants.OPERATION_NAME_KEY_VALUE_REMOVE)) {
			executeKeyValueRemove(parameters);
		} else if (operationName
				.equals(DatastoreServiceConstants.OPERATION_NAME_KEY_VALUE_KEYS)) {
			return executeKeyValueKeys(parameters);
		} else if (operationName
				.equals(DatastoreServiceConstants.OPERATION_NAME_KEY_VALUE_SIZE)) {
			return executeKeyValueSize(parameters);
		} else {
			return super.execute(operationName, parameters);
		}

		// Nothing to return.
		return null;

	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the Data Store Service.
	 * 
	 * @return Data Store Service.<br>
	 * <br>
	 */
	protected DatastoreServiceFacade getService() {
		return service;
	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Executes the query operation.
	 * 
	 * @param parameters
	 *            Operation parameters.<br>
	 * <br>
	 * @return Object that holds the result of the query.<br>
	 * <br>
	 * @throws ServiceException
	 *             If there is an error when trying to execute the operation.<br>
	 * <br>
	 */
	private Object executeQuery(Map<String, Object> parameters)
			throws ServiceException {

		// Get the Client where to execute the operation.
		Object client = parameters
				.get(DatastoreServiceConstants.OPERATION_PARAMETER_CLIENT_NAME);
		if ((client == null) || !(client instanceof String)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_QUERY
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_CLIENT_NAME
							+ "' is not a string or it does not exists.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the statement to execute.
		Object statement = parameters
				.get(DatastoreServiceConstants.OPERATION_PARAMETER_STATEMENT);
		if (statement == null) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_QUERY
							+ "' operation because parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_STATEMENT
							+ "' does not exists.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the name of the View where to execute the operation.
		Object viewName = parameters
				.get(DatastoreServiceConstants.OPERATION_PARAMETER_VIEW_NAME);
		if ((viewName != null) && !(viewName instanceof String)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_QUERY
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_VIEW_NAME
							+ "' is not a string.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Execute the query.
		return service.query((String) client, (String) viewName, statement);

	}

	/**
	 * Executes the query operation from a Provider.
	 * 
	 * @param parameters
	 *            Operation parameters.<br>
	 * <br>
	 * @return Object that holds the result of the query.<br>
	 * <br>
	 * @throws ServiceException
	 *             If there is an error when trying to execute the operation.<br>
	 * <br>
	 */
	@SuppressWarnings("unchecked")
	private Object executeQueryByName(Map<String, Object> parameters)
			throws ServiceException {

		// Get the Client where to execute the operation.
		Object client = parameters
				.get(DatastoreServiceConstants.OPERATION_PARAMETER_CLIENT_NAME);
		if ((client == null) || !(client instanceof String)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_QUERY_BY_NAME
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_CLIENT_NAME
							+ "' is not a string or it does not exists.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the name of the Provider where to retrieve the statement.
		Object providerName = parameters
				.get(DatastoreServiceConstants.OPERATION_PARAMETER_PROVIDER_NAME);
		if ((providerName == null) || !(providerName instanceof String)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_QUERY_BY_NAME
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_PROVIDER_NAME
							+ "' is not a string or it does not exists.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the name of the statement to load from the Provider.
		Object statementName = parameters
				.get(DatastoreServiceConstants.OPERATION_PARAMETER_STATEMENT_NAME);
		if ((statementName == null) || !(statementName instanceof String)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_QUERY_BY_NAME
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_STATEMENT_NAME
							+ "' is not a string or it does not exists.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the values to replace in the statement
		Object statementValues = parameters
				.get(DatastoreServiceConstants.OPERATION_PARAMETER_VALUES);
		if ((statementValues != null) && !(statementValues instanceof Map)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_QUERY_BY_NAME
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_VALUES
							+ "' is not a '" + Map.class.getName() + "'.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the name of the View where to execute the operation.
		Object viewName = parameters
				.get(DatastoreServiceConstants.OPERATION_PARAMETER_VIEW_NAME);
		if ((viewName != null) && !(viewName instanceof String)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_QUERY_BY_NAME
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_VIEW_NAME
							+ "' is not a string.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Execute the query.
		return service.query((String) client, (String) viewName,
				(String) providerName, (String) statementName,
				(Map<String, Object>) statementValues);

	}

	/**
	 * Executes the query operation.
	 * 
	 * @param parameters
	 *            Operation parameters.<br>
	 * <br>
	 * @throws ServiceException
	 *             If there is an error when trying to execute the operation.<br>
	 * <br>
	 */
	private void executeUpdate(Map<String, Object> parameters)
			throws ServiceException {

		// Get the Client where to execute the operation.
		Object clientName = parameters
				.get(DatastoreServiceConstants.OPERATION_PARAMETER_CLIENT_NAME);
		if ((clientName == null) || !(clientName instanceof String)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_UPDATE
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_CLIENT_NAME
							+ "' is not a string or it does not exists.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the statement to execute.
		Object statement = parameters
				.get(DatastoreServiceConstants.OPERATION_PARAMETER_STATEMENT);
		if (statement == null) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_UPDATE
							+ "' operation because parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_STATEMENT
							+ "' does not exists.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the name of the View where to execute the operation.
		Object viewName = parameters
				.get(DatastoreServiceConstants.OPERATION_PARAMETER_VIEW_NAME);
		if ((viewName != null) && !(viewName instanceof String)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_UPDATE
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_VIEW_NAME
							+ "' is not a string.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Execute update.
		service.update((String) clientName, (String) viewName, statement);

	}

	/**
	 * Executes the update operation from a Provider.
	 * 
	 * @param parameters
	 *            Operation parameters.<br>
	 * <br>
	 * @throws ServiceException
	 *             If there is an error when trying to execute the operation.<br>
	 * <br>
	 */
	@SuppressWarnings("unchecked")
	private void executeUpdateByName(Map<String, Object> parameters)
			throws ServiceException {

		// Get the Client where to execute the operation.
		Object client = parameters
				.get(DatastoreServiceConstants.OPERATION_PARAMETER_CLIENT_NAME);
		if ((client == null) || !(client instanceof String)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_UPDATE_BY_NAME
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_CLIENT_NAME
							+ "' is not a string or it does not exists.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the name of the Provider where to retrieve the statement.
		Object providerName = parameters
				.get(DatastoreServiceConstants.OPERATION_PARAMETER_PROVIDER_NAME);
		if ((providerName == null) || !(providerName instanceof String)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_UPDATE_BY_NAME
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_PROVIDER_NAME
							+ "' is not a string or it does not exists.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the name of the statement to load from the Provider.
		Object statementName = parameters
				.get(DatastoreServiceConstants.OPERATION_PARAMETER_STATEMENT_NAME);
		if ((statementName == null) || !(statementName instanceof String)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_UPDATE_BY_NAME
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_STATEMENT_NAME
							+ "' is not a string or it does not exists.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the values to replace in the statement
		Object statementValues = parameters
				.get(DatastoreServiceConstants.OPERATION_PARAMETER_VALUES);
		if ((statementValues != null) && !(statementValues instanceof Map)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_UPDATE_BY_NAME
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_VALUES
							+ "' is not a '" + Map.class.getName() + "'.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the name of the View where to execute the operation.
		Object viewName = parameters
				.get(DatastoreServiceConstants.OPERATION_PARAMETER_VIEW_NAME);
		if ((viewName != null) && !(viewName instanceof String)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_UPDATE_BY_NAME
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_VIEW_NAME
							+ "' is not a string.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Execute the update.
		service.update((String) client, (String) viewName,
				(String) providerName, (String) statementName,
				(Map<String, Object>) statementValues);

	}

	/**
	 * Executes the commit operation.
	 * 
	 * @param parameters
	 *            Operation parameters.<br>
	 * <br>
	 * @throws ServiceException
	 *             If there is an error when trying to execute the operation.<br>
	 * <br>
	 */
	private void executeCommit(Map<String, Object> parameters)
			throws ServiceException {

		// Get the Client where to execute the operation.
		Object clientName = parameters
				.get(DatastoreServiceConstants.OPERATION_PARAMETER_CLIENT_NAME);
		if ((clientName == null) || !(clientName instanceof String)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_COMMIT
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_CLIENT_NAME
							+ "' is not a string or it does not exists.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Execute commit.
		service.commit((String) clientName);

	}

	/**
	 * Begins a transaction in the Database Management System.
	 * 
	 * @param parameters
	 *            Operation parameters.<br>
	 * <br>
	 * @throws ServiceException
	 *             If there is an error when trying to execute the operation.<br>
	 * <br>
	 */
	private void executeDBMSBeginTransaction(Map<String, Object> parameters)
			throws ServiceException {

		// Get the Client where to execute the operation.
		Object clientName = parameters
				.get(DatastoreServiceConstants.OPERATION_PARAMETER_CLIENT_NAME);
		if ((clientName == null) || !(clientName instanceof String)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_DBMS_BEGIN_TRANSACTION
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_CLIENT_NAME
							+ "' is not a string or it does not exists.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the name of the View where to execute the operation.
		Object viewName = parameters
				.get(DatastoreServiceConstants.OPERATION_PARAMETER_VIEW_NAME);
		if ((viewName == null) || !(viewName instanceof String)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_DBMS_BEGIN_TRANSACTION
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_VIEW_NAME
							+ "' is not a string.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the View and execute operation.
		AbstractDatastoreView view = service.getView((String) clientName,
				(String) viewName);
		if (view instanceof DbmsView) {

			// Get the DBMS View.
			DbmsView database = (DbmsView) view;

			// Begin the transaction.
			try {
				database.beginTransaction();
			} catch (ClientException e) {
				throw new ServiceException(
						service.getScopeFacade(),
						"WAREWORK cannot execute '"
								+ DatastoreServiceConstants.OPERATION_NAME_DBMS_BEGIN_TRANSACTION
								+ "' operation because the following exception is thrown: "
								+ e.getMessage(), e,
						LogServiceConstants.LOG_LEVEL_WARN);
			}

		} else {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_DBMS_BEGIN_TRANSACTION
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_VIEW_NAME
							+ "' does not reference to a '"
							+ DbmsView.class.getName() + "' view.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

	}

	/**
	 * Rolls back changes in the Database Management System.
	 * 
	 * @param parameters
	 *            Operation parameters.<br>
	 * <br>
	 * @throws ServiceException
	 *             If there is an error when trying to execute the operation.<br>
	 * <br>
	 */
	private void executeDBMSRollback(Map<String, Object> parameters)
			throws ServiceException {

		// Get the Client where to execute the operation.
		Object clientName = parameters
				.get(DatastoreServiceConstants.OPERATION_PARAMETER_CLIENT_NAME);
		if ((clientName == null) || !(clientName instanceof String)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_DBMS_ROLLBACK
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_CLIENT_NAME
							+ "' is not a string or it does not exists.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the name of the View where to execute the operation.
		Object viewName = parameters
				.get(DatastoreServiceConstants.OPERATION_PARAMETER_VIEW_NAME);
		if ((viewName == null) || !(viewName instanceof String)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_DBMS_ROLLBACK
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_VIEW_NAME
							+ "' is not a string.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the View and execute operation.
		AbstractDatastoreView view = service.getView((String) clientName,
				(String) viewName);
		if (view instanceof DbmsView) {

			// Get the DBMS View.
			DbmsView database = (DbmsView) view;

			// Execute rollback.
			try {
				database.rollback();
			} catch (ClientException e) {
				throw new ServiceException(
						service.getScopeFacade(),
						"WAREWORK cannot execute '"
								+ DatastoreServiceConstants.OPERATION_NAME_DBMS_ROLLBACK
								+ "' operation because the following exception is thrown: "
								+ e.getMessage(), e,
						LogServiceConstants.LOG_LEVEL_WARN);
			}

		} else {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_DBMS_ROLLBACK
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_VIEW_NAME
							+ "' does not reference to a '"
							+ DbmsView.class.getName() + "' view.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

	}

	/**
	 * Executes the query operation in a RDBMS View.
	 * 
	 * @param parameters
	 *            Operation parameters.<br>
	 * <br>
	 * @return Object that holds the result of the query.<br>
	 * <br>
	 * @throws ServiceException
	 *             If there is an error when trying to execute the operation.<br>
	 * <br>
	 */
	@SuppressWarnings("unchecked")
	private Object executeRDBMSQuery(Map<String, Object> parameters)
			throws ServiceException {

		// Get the Client where to execute the operation.
		Object clientName = parameters
				.get(DatastoreServiceConstants.OPERATION_PARAMETER_CLIENT_NAME);
		if ((clientName == null) || !(clientName instanceof String)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_RDBMS_QUERY
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_CLIENT_NAME
							+ "' is not a string or it does not exists.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the statement to execute.
		Object statement = parameters
				.get(DatastoreServiceConstants.OPERATION_PARAMETER_STATEMENT);
		if ((statement == null) || !(statement instanceof String)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_RDBMS_QUERY
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_STATEMENT
							+ "' is not a string or it does not exists.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the values to replace in the statement
		Object statementValues = parameters
				.get(DatastoreServiceConstants.OPERATION_PARAMETER_VALUES);
		if ((statementValues != null) && !(statementValues instanceof Map)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_RDBMS_QUERY
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_VALUES
							+ "' is not a '" + Map.class.getName() + "'.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the name of the View where to execute the operation.
		Object viewName = parameters
				.get(DatastoreServiceConstants.OPERATION_PARAMETER_VIEW_NAME);
		if ((viewName == null) || !(viewName instanceof String)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_RDBMS_QUERY
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_VIEW_NAME
							+ "' is not a string.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the page.
		Object page = parameters
				.get(DatastoreServiceConstants.OPERATION_PARAMETER_PAGE);
		if ((page != null) && !(page instanceof Integer)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_RDBMS_QUERY
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_PAGE
							+ "' is not an '" + Integer.class.getName() + "'.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the size of the page.
		Object pageSize = parameters
				.get(DatastoreServiceConstants.OPERATION_PARAMETER_PAGE_SIZE);
		if ((pageSize != null) && !(pageSize instanceof Integer)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_RDBMS_QUERY
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_PAGE_SIZE
							+ "' is not an '" + Integer.class.getName() + "'.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the View and execute operation.
		AbstractDatastoreView view = service.getView((String) clientName,
				(String) viewName);
		if (view instanceof RdbmsView) {

			// Get the RDBMS View.
			RdbmsView database = (RdbmsView) view;

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

			// Execute the query.
			try {
				return database.executeQuery((String) statement,
						(Map<String, Object>) statementValues, queryPage,
						queryPageSize);
			} catch (ClientException e) {
				throw new ServiceException(
						service.getScopeFacade(),
						"WAREWORK cannot execute '"
								+ DatastoreServiceConstants.OPERATION_NAME_RDBMS_QUERY
								+ "' operation because the following exception is thrown: "
								+ e.getMessage(), e,
						LogServiceConstants.LOG_LEVEL_WARN);
			}

		} else {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_RDBMS_QUERY
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_VIEW_NAME
							+ "' does not reference to a '"
							+ RdbmsView.class.getName() + "' view.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

	}

	/**
	 * Executes a query loaded from a Provider in a RDBMS View.
	 * 
	 * @param parameters
	 *            Operation parameters.<br>
	 * <br>
	 * @return Object that holds the result of the query.<br>
	 * <br>
	 * @throws ServiceException
	 *             If there is an error when trying to execute the operation.<br>
	 * <br>
	 */
	@SuppressWarnings("unchecked")
	private Object executeRDBMSQueryByName(Map<String, Object> parameters)
			throws ServiceException {

		// Get the Client where to execute the operation.
		Object clientName = parameters
				.get(DatastoreServiceConstants.OPERATION_PARAMETER_CLIENT_NAME);
		if ((clientName == null) || !(clientName instanceof String)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_RDBMS_QUERY_BY_NAME
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_CLIENT_NAME
							+ "' is not a string or it does not exists.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the name of the Provider where to retrieve the statement.
		Object providerName = parameters
				.get(DatastoreServiceConstants.OPERATION_PARAMETER_PROVIDER_NAME);
		if ((providerName == null) || !(providerName instanceof String)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_RDBMS_QUERY_BY_NAME
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_PROVIDER_NAME
							+ "' is not a string or it does not exists.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the name of the statement to load from the Provider.
		Object statementName = parameters
				.get(DatastoreServiceConstants.OPERATION_PARAMETER_STATEMENT_NAME);
		if ((statementName == null) || !(statementName instanceof String)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_RDBMS_QUERY_BY_NAME
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_STATEMENT_NAME
							+ "' is not a string or it does not exists.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the values to replace in the statement
		Object statementValues = parameters
				.get(DatastoreServiceConstants.OPERATION_PARAMETER_VALUES);
		if ((statementValues != null) && !(statementValues instanceof Map)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_RDBMS_QUERY_BY_NAME
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_VALUES
							+ "' is not a '" + Map.class.getName() + "'.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the name of the View where to execute the operation.
		Object viewName = parameters
				.get(DatastoreServiceConstants.OPERATION_PARAMETER_VIEW_NAME);
		if ((viewName == null) || !(viewName instanceof String)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_RDBMS_QUERY_BY_NAME
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_VIEW_NAME
							+ "' is not a string.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the page.
		Object page = parameters
				.get(DatastoreServiceConstants.OPERATION_PARAMETER_PAGE);
		if ((page != null) && !(page instanceof Integer)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_RDBMS_QUERY_BY_NAME
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_PAGE
							+ "' is not an '" + Integer.class.getName() + "'.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the size of the page.
		Object pageSize = parameters
				.get(DatastoreServiceConstants.OPERATION_PARAMETER_PAGE_SIZE);
		if ((pageSize != null) && !(pageSize instanceof Integer)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_RDBMS_QUERY_BY_NAME
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_PAGE_SIZE
							+ "' is not an '" + Integer.class.getName() + "'.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the View and execute operation.
		AbstractDatastoreView view = service.getView((String) clientName,
				(String) viewName);
		if (view instanceof RdbmsView) {

			// Get the RDBMS View.
			RdbmsView database = (RdbmsView) view;

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

			// Execute the query.
			try {
				return database.executeQueryByName((String) providerName,
						(String) statementName,
						(Map<String, Object>) statementValues, queryPage,
						queryPageSize);
			} catch (ClientException e) {
				throw new ServiceException(
						service.getScopeFacade(),
						"WAREWORK cannot execute '"
								+ DatastoreServiceConstants.OPERATION_NAME_RDBMS_QUERY_BY_NAME
								+ "' operation because the following exception is thrown: "
								+ e.getMessage(), e,
						LogServiceConstants.LOG_LEVEL_WARN);
			}

		} else {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_RDBMS_QUERY_BY_NAME
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_VIEW_NAME
							+ "' does not reference to a '"
							+ RdbmsView.class.getName() + "' view.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

	}

	/**
	 * Executes the update operation in a RDBMS View.
	 * 
	 * @param parameters
	 *            Operation parameters.<br>
	 * <br>
	 * @throws ServiceException
	 *             If there is an error when trying to execute the operation.<br>
	 * <br>
	 */
	@SuppressWarnings("unchecked")
	private void executeRDBMSUpdate(Map<String, Object> parameters)
			throws ServiceException {

		// Get the Client where to execute the operation.
		Object clientName = parameters
				.get(DatastoreServiceConstants.OPERATION_PARAMETER_CLIENT_NAME);
		if ((clientName == null) || !(clientName instanceof String)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_RDBMS_UPDATE
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_CLIENT_NAME
							+ "' is not a string or it does not exists.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the statement to execute.
		Object statement = parameters
				.get(DatastoreServiceConstants.OPERATION_PARAMETER_STATEMENT);
		if ((statement == null) || !(statement instanceof String)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_RDBMS_UPDATE
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_STATEMENT
							+ "' is not a string or it does not exists.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the values to replace in the statement
		Object statementValues = parameters
				.get(DatastoreServiceConstants.OPERATION_PARAMETER_VALUES);
		if ((statementValues != null) && !(statementValues instanceof Map)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_RDBMS_UPDATE
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_VALUES
							+ "' is not a '" + Map.class.getName() + "'.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the name of the View where to execute the operation.
		Object viewName = parameters
				.get(DatastoreServiceConstants.OPERATION_PARAMETER_VIEW_NAME);
		if ((viewName == null) || !(viewName instanceof String)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_RDBMS_UPDATE
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_VIEW_NAME
							+ "' is not a string.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the delimiter.
		Object delimiter = parameters
				.get(DatastoreServiceConstants.OPERATION_PARAMETER_DELIMITER);
		if ((delimiter != null)
				&& ((!(delimiter instanceof Character) && !(delimiter instanceof String)) || ((delimiter instanceof String) && (((String) delimiter)
						.length() != 1)))) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_RDBMS_UPDATE
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_DELIMITER
							+ "' is not a '" + Character.class.getName()
							+ "' object or a string with just one character.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the View and execute operation.
		AbstractDatastoreView view = service.getView((String) clientName,
				(String) viewName);
		if (view instanceof RdbmsView) {

			// Get the RDBMS View.
			RdbmsView database = (RdbmsView) view;

			// Get the character to separate each statement.
			Character separator = null;
			if (delimiter instanceof String) {
				separator = new Character(((String) delimiter).charAt(0));
			} else if (delimiter instanceof Character) {
				separator = (Character) delimiter;
			}

			// Execute the update statement.
			try {
				database.executeUpdate((String) statement,
						(Map<String, Object>) statementValues, separator);
			} catch (ClientException e) {
				throw new ServiceException(
						service.getScopeFacade(),
						"WAREWORK cannot execute '"
								+ DatastoreServiceConstants.OPERATION_NAME_RDBMS_UPDATE
								+ "' operation because the following exception is thrown: "
								+ e.getMessage(), e,
						LogServiceConstants.LOG_LEVEL_WARN);
			}

		} else {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_RDBMS_UPDATE
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_VIEW_NAME
							+ "' does not reference to a '"
							+ RdbmsView.class.getName() + "' view.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

	}

	/**
	 * Executes an update statement loaded from a Provider in a RDBMS View.
	 * 
	 * @param parameters
	 *            Operation parameters.<br>
	 * <br>
	 * @throws ServiceException
	 *             If there is an error when trying to execute the operation.<br>
	 * <br>
	 */
	@SuppressWarnings("unchecked")
	private void executeRDBMSUpdateByName(Map<String, Object> parameters)
			throws ServiceException {

		// Get the Client where to execute the operation.
		Object clientName = parameters
				.get(DatastoreServiceConstants.OPERATION_PARAMETER_CLIENT_NAME);
		if ((clientName == null) || !(clientName instanceof String)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_RDBMS_UPDATE_BY_NAME
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_CLIENT_NAME
							+ "' is not a string or it does not exists.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the name of the Provider where to retrieve the statement.
		Object providerName = parameters
				.get(DatastoreServiceConstants.OPERATION_PARAMETER_PROVIDER_NAME);
		if ((providerName == null) || !(providerName instanceof String)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_RDBMS_UPDATE_BY_NAME
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_PROVIDER_NAME
							+ "' is not a string or it does not exists.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the name of the statement to load from the Provider.
		Object statementName = parameters
				.get(DatastoreServiceConstants.OPERATION_PARAMETER_STATEMENT_NAME);
		if ((statementName == null) || !(statementName instanceof String)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_RDBMS_UPDATE_BY_NAME
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_STATEMENT_NAME
							+ "' is not a string or it does not exists.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the values to replace in the statement
		Object statementValues = parameters
				.get(DatastoreServiceConstants.OPERATION_PARAMETER_VALUES);
		if ((statementValues != null) && !(statementValues instanceof Map)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_RDBMS_UPDATE_BY_NAME
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_VALUES
							+ "' is not a '" + Map.class.getName() + "'.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the name of the View where to execute the operation.
		Object viewName = parameters
				.get(DatastoreServiceConstants.OPERATION_PARAMETER_VIEW_NAME);
		if ((viewName == null) || !(viewName instanceof String)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_RDBMS_UPDATE_BY_NAME
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_VIEW_NAME
							+ "' is not a string.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the delimiter.
		Object delimiter = parameters
				.get(DatastoreServiceConstants.OPERATION_PARAMETER_DELIMITER);
		if ((delimiter != null)
				&& ((!(delimiter instanceof Character) && !(delimiter instanceof String)) || ((delimiter instanceof String) && (((String) delimiter)
						.length() != 1)))) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_RDBMS_UPDATE_BY_NAME
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_DELIMITER
							+ "' is not a '" + Character.class.getName()
							+ "' object or a string with just one character.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the View and execute operation.
		AbstractDatastoreView view = service.getView((String) clientName,
				(String) viewName);
		if (view instanceof RdbmsView) {

			// Get the RDBMS View.
			RdbmsView database = (RdbmsView) view;

			// Get the character to separate each statement.
			Character separator = null;
			if (delimiter instanceof String) {
				separator = new Character(((String) delimiter).charAt(0));
			} else if (delimiter instanceof Character) {
				separator = (Character) delimiter;
			}

			// Execute the update statement.
			try {
				database.executeUpdateByName((String) providerName,
						(String) statementName,
						(Map<String, Object>) statementValues, separator);
			} catch (ClientException e) {
				throw new ServiceException(
						service.getScopeFacade(),
						"WAREWORK cannot execute '"
								+ DatastoreServiceConstants.OPERATION_NAME_RDBMS_UPDATE_BY_NAME
								+ "' operation because the following exception is thrown: "
								+ e.getMessage(), e,
						LogServiceConstants.LOG_LEVEL_WARN);
			}

		} else {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_RDBMS_UPDATE_BY_NAME
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_VIEW_NAME
							+ "' does not reference to a '"
							+ RdbmsView.class.getName() + "' view.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

	}

	/**
	 * Maps the specified key to the specified value in the Data Store.
	 * 
	 * @param parameters
	 *            Operation parameters.<br>
	 * <br>
	 * @throws ServiceException
	 *             If there is an error when trying to execute the operation.<br>
	 * <br>
	 */
	private void executeKeyValuePut(Map<String, Object> parameters)
			throws ServiceException {

		// Get the Client where to execute the operation.
		Object clientName = parameters
				.get(DatastoreServiceConstants.OPERATION_PARAMETER_CLIENT_NAME);
		if ((clientName == null) || !(clientName instanceof String)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_KEY_VALUE_PUT
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_CLIENT_NAME
							+ "' is not a string or it does not exists.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the key.
		Object key = parameters
				.get(DatastoreServiceConstants.OPERATION_PARAMETER_KEY);
		if (key == null) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_KEY_VALUE_PUT
							+ "' operation because parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_KEY
							+ "' does not exists.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the value.
		Object value = parameters
				.get(DatastoreServiceConstants.OPERATION_PARAMETER_VALUE);
		if (value == null) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_KEY_VALUE_PUT
							+ "' operation because parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_VALUE
							+ "' does not exists.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the name of the View where to execute the operation.
		Object viewName = parameters
				.get(DatastoreServiceConstants.OPERATION_PARAMETER_VIEW_NAME);
		if ((viewName == null) || !(viewName instanceof String)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_KEY_VALUE_PUT
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_VIEW_NAME
							+ "' is not a string.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the View and execute operation.
		AbstractDatastoreView view = service.getView((String) clientName,
				(String) viewName);
		if (view instanceof KeyValueView) {

			// Get the Key-Value View.
			KeyValueView database = (KeyValueView) view;

			// Execute database operation.
			try {
				database.put(key, value);
			} catch (ClientException e) {
				throw new ServiceException(
						service.getScopeFacade(),
						"WAREWORK cannot execute '"
								+ DatastoreServiceConstants.OPERATION_NAME_KEY_VALUE_PUT
								+ "' operation because the following exception is thrown: "
								+ e.getMessage(), e,
						LogServiceConstants.LOG_LEVEL_WARN);
			}

		} else {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_KEY_VALUE_PUT
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_VIEW_NAME
							+ "' does not reference to a '"
							+ KeyValueView.class.getName() + "' view.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

	}

	/**
	 * Gets the value to which the specified key is mapped in the Data Store.
	 * 
	 * @param parameters
	 *            Operation parameters.<br>
	 * <br>
	 * @return The value to which the specified key is mapped in the Data Store.<br>
	 * <br>
	 * @throws ServiceException
	 *             If there is an error when trying to execute the operation.<br>
	 * <br>
	 */
	private Object executeKeyValueGet(Map<String, Object> parameters)
			throws ServiceException {

		// Get the Client where to execute the operation.
		Object clientName = parameters
				.get(DatastoreServiceConstants.OPERATION_PARAMETER_CLIENT_NAME);
		if ((clientName == null) || !(clientName instanceof String)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_KEY_VALUE_GET
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_CLIENT_NAME
							+ "' is not a string or it does not exists.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the key.
		Object key = parameters
				.get(DatastoreServiceConstants.OPERATION_PARAMETER_KEY);
		if (key == null) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_KEY_VALUE_GET
							+ "' operation because parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_KEY
							+ "' does not exists.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the name of the View where to execute the operation.
		Object viewName = parameters
				.get(DatastoreServiceConstants.OPERATION_PARAMETER_VIEW_NAME);
		if ((viewName == null) || !(viewName instanceof String)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_KEY_VALUE_GET
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_VIEW_NAME
							+ "' is not a string.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the View and execute operation.
		AbstractDatastoreView view = service.getView((String) clientName,
				(String) viewName);
		if (view instanceof KeyValueView) {

			// Get the Key-Value View.
			KeyValueView database = (KeyValueView) view;

			// Execute database operation.
			try {
				return database.get(key);
			} catch (ClientException e) {
				throw new ServiceException(
						service.getScopeFacade(),
						"WAREWORK cannot execute '"
								+ DatastoreServiceConstants.OPERATION_NAME_KEY_VALUE_GET
								+ "' operation because the following exception is thrown: "
								+ e.getMessage(), e,
						LogServiceConstants.LOG_LEVEL_WARN);
			}

		} else {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_KEY_VALUE_GET
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_VIEW_NAME
							+ "' does not reference to a '"
							+ KeyValueView.class.getName() + "' view.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

	}

	/**
	 * Removes the key (and its corresponding value) from the Data Store.
	 * 
	 * @param parameters
	 *            Operation parameters.<br>
	 * <br>
	 * @throws ServiceException
	 *             If there is an error when trying to execute the operation.<br>
	 * <br>
	 */
	private void executeKeyValueRemove(Map<String, Object> parameters)
			throws ServiceException {

		// Get the Client where to execute the operation.
		Object clientName = parameters
				.get(DatastoreServiceConstants.OPERATION_PARAMETER_CLIENT_NAME);
		if ((clientName == null) || !(clientName instanceof String)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_KEY_VALUE_REMOVE
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_CLIENT_NAME
							+ "' is not a string or it does not exists.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the key.
		Object key = parameters
				.get(DatastoreServiceConstants.OPERATION_PARAMETER_KEY);
		if (key == null) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_KEY_VALUE_REMOVE
							+ "' operation because parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_KEY
							+ "' does not exists.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the name of the View where to execute the operation.
		Object viewName = parameters
				.get(DatastoreServiceConstants.OPERATION_PARAMETER_VIEW_NAME);
		if ((viewName == null) || !(viewName instanceof String)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_KEY_VALUE_REMOVE
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_VIEW_NAME
							+ "' is not a string.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the View and execute operation.
		AbstractDatastoreView view = service.getView((String) clientName,
				(String) viewName);
		if (view instanceof KeyValueView) {

			// Get the Key-Value View.
			KeyValueView database = (KeyValueView) view;

			// Execute database operation.
			try {
				database.remove(key);
			} catch (ClientException e) {
				throw new ServiceException(
						service.getScopeFacade(),
						"WAREWORK cannot execute '"
								+ DatastoreServiceConstants.OPERATION_NAME_KEY_VALUE_REMOVE
								+ "' operation because the following exception is thrown: "
								+ e.getMessage(), e,
						LogServiceConstants.LOG_LEVEL_WARN);
			}

		} else {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_KEY_VALUE_REMOVE
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_VIEW_NAME
							+ "' does not reference to a '"
							+ KeyValueView.class.getName() + "' view.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

	}

	/**
	 * Gets an enumeration of the keys in the Data Store.
	 * 
	 * @param parameters
	 *            Operation parameters.<br>
	 * <br>
	 * @return An list with the keys of the Data Store.<br>
	 * <br>
	 * @throws ServiceException
	 *             If there is an error when trying to execute the operation.<br>
	 * <br>
	 */
	private Object executeKeyValueKeys(Map<String, Object> parameters)
			throws ServiceException {

		// Get the Client where to execute the operation.
		Object clientName = parameters
				.get(DatastoreServiceConstants.OPERATION_PARAMETER_CLIENT_NAME);
		if ((clientName == null) || !(clientName instanceof String)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_KEY_VALUE_KEYS
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_CLIENT_NAME
							+ "' is not a string or it does not exists.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the name of the View where to execute the operation.
		Object viewName = parameters
				.get(DatastoreServiceConstants.OPERATION_PARAMETER_VIEW_NAME);
		if ((viewName == null) || !(viewName instanceof String)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_KEY_VALUE_KEYS
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_VIEW_NAME
							+ "' is not a string.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the View and execute operation.
		AbstractDatastoreView view = service.getView((String) clientName,
				(String) viewName);
		if (view instanceof KeyValueView) {

			// Get the Key-Value View.
			KeyValueView database = (KeyValueView) view;

			// Execute database operation.
			try {
				return DataStructureL1Helper.toVector(database.keys());
			} catch (ClientException e) {
				throw new ServiceException(
						service.getScopeFacade(),
						"WAREWORK cannot execute '"
								+ DatastoreServiceConstants.OPERATION_NAME_KEY_VALUE_KEYS
								+ "' operation because the following exception is thrown: "
								+ e.getMessage(), e,
						LogServiceConstants.LOG_LEVEL_WARN);
			}

		} else {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_KEY_VALUE_KEYS
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_VIEW_NAME
							+ "' does not reference to a '"
							+ KeyValueView.class.getName() + "' view.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

	}

	/**
	 * Counts the number of keys in the Data Store.
	 * 
	 * @param parameters
	 *            Operation parameters.<br>
	 * <br>
	 * @return Number of keys in the Data Store.<br>
	 * <br>
	 * @throws ServiceException
	 *             If there is an error when trying to execute the operation.<br>
	 * <br>
	 */
	private Object executeKeyValueSize(Map<String, Object> parameters)
			throws ServiceException {

		// Get the Client where to execute the operation.
		Object clientName = parameters
				.get(DatastoreServiceConstants.OPERATION_PARAMETER_CLIENT_NAME);
		if ((clientName == null) || !(clientName instanceof String)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_KEY_VALUE_SIZE
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_CLIENT_NAME
							+ "' is not a string or it does not exists.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the name of the View where to execute the operation.
		Object viewName = parameters
				.get(DatastoreServiceConstants.OPERATION_PARAMETER_VIEW_NAME);
		if ((viewName == null) || !(viewName instanceof String)) {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_KEY_VALUE_SIZE
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_VIEW_NAME
							+ "' is not a string.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the View and execute operation.
		AbstractDatastoreView view = service.getView((String) clientName,
				(String) viewName);
		if (view instanceof KeyValueView) {

			// Get the Key-Value View.
			KeyValueView database = (KeyValueView) view;

			// Execute database operation.
			try {
				return new Integer(database.size());
			} catch (ClientException e) {
				throw new ServiceException(
						service.getScopeFacade(),
						"WAREWORK cannot execute '"
								+ DatastoreServiceConstants.OPERATION_NAME_KEY_VALUE_SIZE
								+ "' operation because the following exception is thrown: "
								+ e.getMessage(), e,
						LogServiceConstants.LOG_LEVEL_WARN);
			}

		} else {
			throw new ServiceException(
					service.getScopeFacade(),
					"WAREWORK cannot execute '"
							+ DatastoreServiceConstants.OPERATION_NAME_KEY_VALUE_SIZE
							+ "' operation because given parameter '"
							+ DatastoreServiceConstants.OPERATION_PARAMETER_VIEW_NAME
							+ "' does not reference to a '"
							+ KeyValueView.class.getName() + "' view.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

	}

}
