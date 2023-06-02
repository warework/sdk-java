package com.warework.service.datastore.client;

import java.util.Map;

import com.warework.core.service.client.AbstractClient;
import com.warework.core.service.client.ClientException;
import com.warework.core.util.helper.StringL1Helper;
import com.warework.service.log.LogServiceConstants;

/**
 * Client that defines common operations for Data Stores.<br>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public abstract class AbstractDatastore extends AbstractClient implements
		DatastoreFacade {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// CLIENT'S ATTRIBUTES

	/**
	 * Attribute that specifies if Data Store supports pagination in query
	 * operations. Accepted values for this attribute are <code>true</code> or
	 * <code>false</code> (as <code>java.lang.String</code> or
	 * <code>java.lang.Boolean</code> objects). If this attribute is not
	 * specified then it is <code>false</code>.
	 */
	public static final String CLIENT_ATTRIBUTE_QUERY_PAGINATION_SUPPORT = "query-pagination-support";

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// Flag for paginated operations.TRUE = operation allowed. FALSE = operation
	// cannot be performed.
	private Boolean executePaginatedQuery;

	// ///////////////////////////////////////////////////////////////////
	// ABSTRACT METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Executes a statement that retrieves information from a Data Store.
	 * 
	 * @param statement
	 *            Query in a specific language accepted by the Data Store.<br>
	 * <br>
	 * @param values
	 *            Map where the keys represent variable names in the statement
	 *            and the values those that will replace the variables. Each
	 *            Data Store may process this values in a specific way (not
	 *            every Data Store support the replacement of variables). When
	 *            the query statement represents a String object, every variable
	 *            must be inside '${' and '}' so the variable CAR must be in
	 *            this query-string as '${CAR}'. Pass <code>null</code> to this
	 *            parameter to make no changes in the statement loaded.<br>
	 * <br>
	 * @return Object that holds the result of the query.<br>
	 * <br>
	 * @throws ClientException
	 *             If there is an error when trying to query the Data Store.<br>
	 * <br>
	 */
	protected abstract Object performQuery(Object statement,
			Map<String, Object> values) throws ClientException;

	/**
	 * Executes a statement that inserts, updates or deletes information in a
	 * Data Store.
	 * 
	 * @param statement
	 *            Indicates the specific operation to perform and the data to
	 *            update in the Data Store.<br>
	 * <br>
	 * @param values
	 *            Map where the keys represent variable names in the statement
	 *            and the values those that will replace the variables. Each
	 *            Data Store may process this values in a specific way (not
	 *            every Data Store support the replacement of variables). When
	 *            the update statement represents a String object, every
	 *            variable must be inside '${' and '}' so the variable CAR must
	 *            be in this update-string as '${CAR}'. Pass <code>null</code>
	 *            to this parameter to make no changes in the statement loaded.<br>
	 * <br>
	 * @throws ClientException
	 *             If there is an error when trying to update the Data Store.<br>
	 * <br>
	 */
	protected abstract void performUpdate(Object statement,
			Map<String, Object> values) throws ClientException;

	/**
	 * Makes all changes made since the previous commit permanent.
	 * 
	 * @throws ClientException
	 *             If there is an error when trying to commit the Data Store.<br>
	 * <br>
	 */
	protected abstract void performCommit() throws ClientException;

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
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
	public Object query(Object statement, Map<String, Object> values)
			throws ClientException {
		if (isConnected()) {

			// Log a message.
			if (statement instanceof String) {
				getScopeFacade().log(
						"WAREWORK is going to execute the following query in Data Store '"
								+ getName() + "' of Service '"
								+ getService().getName() + "':\n" + statement,
						LogServiceConstants.LOG_LEVEL_DEBUG);
			} else {
				getScopeFacade().log(
						"WAREWORK is going to execute a query in Data Store '"
								+ getName() + "' of Service '"
								+ getService().getName() + "'.",
						LogServiceConstants.LOG_LEVEL_DEBUG);
			}

			// Perform the query.
			return performQuery(statement, values);

		} else {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot query Data Store '" + getName()
							+ "' at Service '" + getService().getName()
							+ "' because Data Store connection is closed.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}
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
	public void update(Object statement, Map<String, Object> values)
			throws ClientException {
		if (isConnected()) {

			// Log a message.
			if (statement instanceof String) {
				getScopeFacade().log(
						"WAREWORK is going to execute the following statement in Data Store '"
								+ getName() + "' of Service '"
								+ getService().getName() + "':\n"
								+ statement.toString()
								+ StringL1Helper.CHARACTER_PERIOD,
						LogServiceConstants.LOG_LEVEL_DEBUG);
			} else {
				getScopeFacade().log(
						"WAREWORK is going to perform an update operation in Data Store '"
								+ getName() + "' of Service '"
								+ getService().getName() + "'.",
						LogServiceConstants.LOG_LEVEL_DEBUG);
			}

			// Execute update.
			performUpdate(statement, values);

		} else {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot update Data Store '" + getName()
							+ "' at Service '" + getService().getName()
							+ "' because Data Store connection is closed.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	/**
	 * Makes all changes made since the previous commit permanent.
	 * 
	 * @throws ClientException
	 *             If there is an error when trying to commit the Data Store.<br>
	 * <br>
	 */
	public void commit() throws ClientException {
		if (isConnected()) {
			performCommit();
		} else {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot commit Data Store '" + getName()
							+ "' at Service '" + getService().getName()
							+ "' because Data Store connection is closed.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	// ///////////////////////////////////////////////////////////////////
	// PACKAGE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Specifies if pagination is allowed in query operations.
	 * 
	 * @return <code>true</code> if pagination is allowed and <code>false</code>
	 *         if not.<br>
	 * <br>
	 */
	boolean executePaginatedQuery() {

		// Initialize flag.
		if (executePaginatedQuery == null) {
			if (getClientAttributes()
					.isAttribute(
							AbstractDatastore.CLIENT_ATTRIBUTE_QUERY_PAGINATION_SUPPORT)) {
				executePaginatedQuery = Boolean.TRUE;
			} else {
				executePaginatedQuery = Boolean.FALSE;
			}
		}

		// Return if pagination is supported.
		return executePaginatedQuery.booleanValue();

	}

}
