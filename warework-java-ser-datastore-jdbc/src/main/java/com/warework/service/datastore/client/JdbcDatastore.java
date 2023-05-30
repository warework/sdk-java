package com.warework.service.datastore.client;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

import com.warework.core.service.client.ClientException;
import com.warework.core.util.helper.DataStructureL1Helper;
import com.warework.core.util.helper.StringL1Helper;
import com.warework.service.datastore.client.connector.JdbcConnector;
import com.warework.service.log.LogServiceConstants;

/**
 * This Data Store allows you to perform operations with relational databases
 * using the JDBC technology. It is an abstract layer for JDBC that simplifies
 * the process of connecting with a database and the way you query or update
 * data in it with SQL.<br>
 * <br>
 * This JDBC Data Store allows you to:<br>
 * <ul>
 * <li>Create a String object that represents an SQL statement. <br>
 * <br>
 * </li>
 * <li>Run SQL commands like <code>SELECT</code> or <code>INSERT</code> that
 * exist in a text file. <br>
 * <br>
 * </li>
 * <li>Define variables in a SQL script and replace them with specific values
 * before the script is executed.</li>
 * </ul>
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public final class JdbcDatastore extends AbstractDatastore {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Constant that defines the default name for the client.
	 */
	public static final String DEFAULT_CLIENT_NAME = "jdbc-datastore";

	// SQL KEYWORDS

	/**
	 * SQL <code>AND</code> keyword.
	 */
	static final String KEYWORD_AND = "AND";

	/**
	 * SQL <code>COUNT</code> keyword.
	 */
	static final String KEYWORD_COUNT = "COUNT";

	/**
	 * SQL <code>DELETE</code> keyword.
	 */
	static final String KEYWORD_DELETE = "DELETE";

	/**
	 * SQL <code>DISTINCT</code> keyword.
	 */
	static final String KEYWORD_DISTINCT = "DISTINCT";

	/**
	 * SQL <code>FROM</code> keyword.
	 */
	static final String KEYWORD_FROM = "FROM";

	/**
	 * SQL <code>INSERT INTO</code> keywords.
	 */
	static final String KEYWORD_INSERT = "INSERT INTO";

	/**
	 * SQL <code>IS</code> keywords.
	 */
	static final String KEYWORD_IS = "IS";

	/**
	 * SQL <code>NOT</code> keywords.
	 */
	static final String KEYWORD_NOT = "NOT";

	/**
	 * SQL <code>NULL</code> keywords.
	 */
	static final String KEYWORD_NULL = "NULL";

	/**
	 * SQL <code>OR</code> keyword.
	 */
	static final String KEYWORD_OR = "OR";

	/**
	 * SQL <code>SELECT</code> keyword.
	 */
	static final String KEYWORD_SELECT = "SELECT";

	/**
	 * SQL <code>SET</code> keyword.
	 */
	static final String KEYWORD_SET = "SET";

	/**
	 * SQL <code>UPDATE</code> keyword.
	 */
	static final String KEYWORD_UPDATE = "UPDATE";

	/**
	 * SQL <code>VALUES</code> keyword.
	 */
	static final String KEYWORD_VALUES = "VALUES";

	/**
	 * SQL <code>WHERE</code> keyword.
	 */
	static final String KEYWORD_WHERE = "WHERE";

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Executes an SQL <code>SELECT</code> statement in the relational database.
	 * 
	 * @param statement A <code>String</code> object with an SQL <code>SELECT</code>
	 *                  statement. This statement may have variables in the form of
	 *                  ${variableName} that can be replaced with values. For
	 *                  example:
	 *                  <code>SELECT * FROM PEOPLE WHERE ID=${USER_ID}</code>.<br>
	 *                  <br>
	 * @param values    Map where the keys represent variable names in the statement
	 *                  and the values those that will replace the variables. Every
	 *                  variable defined in the statement must be inside '${' and
	 *                  '}' so the variable CAR must be in this query-string as
	 *                  '${CAR}'. Pass <code>null</code> to this parameter to make
	 *                  no changes in the statement.<br>
	 *                  <br>
	 * @return A <code>java.sql.ResultSet</code> object.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to query the Data
	 *                         Store.<br>
	 *                         <br>
	 */
	protected Object performQuery(final Object statement, final Map<String, Object> values) throws ClientException {
		if (statement instanceof String) {
			return executeQuery((String) statement, values, -1, -1);
		} else {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot execute the statement in Data Store '" + getName() + "' at Service '"
							+ getService().getName() + "' because given statement is not a '" + String.class.getName()
							+ "' class.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	/**
	 * Executes an <code>INSERT</code>, <code>UPDATE</code> or <code>DELETE</code>
	 * SQL statement. You can also perform with this method other SQL operations
	 * that return no data like <code>CREATE TABLE</code>.
	 * 
	 * @param statement <code>String</code> that represents an <code>INSERT</code>,
	 *                  <code>UPDATE</code> or <code>DELETE</code> SQL statement.
	 *                  This statement may have variables in the form of
	 *                  ${variableName} that can be replaced with values. For
	 *                  example: DELETE FROM PEOPLE WHERE ID=${USER_ID}. You can
	 *                  also perform with this method other SQL operations that
	 *                  return no data like <code>CREATE TABLE</code>.<br>
	 *                  <br>
	 * @param values    Map where the keys represent variable names in the statement
	 *                  and the values those that will replace the variables. Every
	 *                  variable defined in the statement must be inside '${' and
	 *                  '}' so the variable CAR must be in this query-string as
	 *                  '${CAR}'. Pass <code>null</code> to this parameter to make
	 *                  no changes in the statement.<br>
	 *                  <br>
	 * @throws ClientException If there is an error when trying to update the Data
	 *                         Store.<br>
	 *                         <br>
	 */
	protected void performUpdate(final Object statement, final Map<String, Object> values) throws ClientException {
		if (statement instanceof String) {

			// Execute SQL.
			executeUpdate((String) statement, values);

			// Log a message.
			getScopeFacade().log("WAREWORK successfully updated Data Store '" + getName() + "' of Service '"
					+ getService().getName() + "'.", LogServiceConstants.LOG_LEVEL_INFO);

		} else {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot execute the statement in Data Store '" + getName() + "' at Service '"
							+ getService().getName() + "' because given statement is not a '" + String.class.getName()
							+ "' class.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	/**
	 * Makes all changes made since the previous commit permanent.
	 * 
	 * @throws ClientException If there is an error when trying to commit the Data
	 *                         Store.<br>
	 *                         <br>
	 */
	protected void performCommit() throws ClientException {
		try {

			// Get the connection.
			final Connection connection = (Connection) getConnection();

			// Commit changes.
			connection.commit();

			// Log a message.
			getScopeFacade().log("WAREWORK executed commit in Data Store '" + getName() + "' of Service '"
					+ getService().getName() + "'.", LogServiceConstants.LOG_LEVEL_INFO);

		} catch (final SQLException e) {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot commit Data Store '" + getName() + "' at Service '" + getService().getName()
							+ "' because the database reported the following error: " + e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	/**
	 * Closes the connection with the database.
	 * 
	 * @throws ClientException If there is an error when trying to close the
	 *                         Client.<br>
	 *                         <br>
	 */
	protected void close() throws ClientException {
		try {

			// Get the connection.
			final Connection connection = (Connection) getConnection();

			// Close the database connection.
			connection.close();

		} catch (final SQLException e) {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot close Data Store '" + getName() + "' at Service '" + getService().getName()
							+ "' because the database reported the following error: " + e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	/**
	 * Validates if the connection with the database is closed.
	 * 
	 * @return <code>true</code> if the connection is closed and <code>false</code>
	 *         if the connection is open.<br>
	 *         <br>
	 */
	protected boolean isClosed() {

		// Get the connection.
		final Connection connection = (Connection) getConnection();

		// Validate the connection.
		if (connection != null) {
			try {
				return connection.isClosed();
			} catch (final SQLException e) {
				getScopeFacade().log("WAREWORK cannot check if connection is closed at Data Store '" + getName()
						+ "' of Service '" + getService().getName()
						+ "' because the database reported the following error: " + e.getMessage(),
						LogServiceConstants.LOG_LEVEL_WARN);
			}
		}

		// At this point, connection is closed.
		return true;

	}

	// ///////////////////////////////////////////////////////////////////
	// PACKAGE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Replace variables in SQL statement and execute the SQL statement.
	 * 
	 * @param query    SQL statement.<br>
	 *                 <br>
	 * @param values   Map where the keys represent variable names in the
	 *                 query-string loaded from the Provider and the values those
	 *                 that will replace the variables. Every variable must be
	 *                 inside '${' and '}' so the variable CAR must be in this
	 *                 query-string as '${CAR}'. Pass <code>null</code> to this
	 *                 parameter to make no changes in the query loaded.<br>
	 *                 <br>
	 * @param page     Page to get from the result. When you specify the number of
	 *                 rows (check out <code>pageRows</code> argument) that you want
	 *                 in the result of a database, Warework automatically
	 *                 calculates the number of pages that hold this number of rows.
	 *                 You have to pass an integer value greater than zero to
	 *                 retrieve a specific page from the result. Set this argument
	 *                 to <code>-1</code> to retrieve every row in one page.<br>
	 *                 <br>
	 * @param pageRows Number of rows that you want in the result of the database.
	 *                 Set this argument to <code>-1</code> to retrieve every
	 *                 row.<br>
	 *                 <br>
	 * @return Result of the SQL query.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to query the Data
	 *                         Store.<br>
	 *                         <br>
	 */
	Object executeQuery(final String query, final Map<String, Object> values, final int page, final int pageRows)
			throws ClientException {

		// Get the connection.
		final Connection connection = (Connection) getConnection();

		// Execute the query with or without parameters.
		ResultSet result = null;
		if ((values != null) && (values.size() > 0)) {

			// Extracts the values in the order that indicates the
			// variables defined in the statement.
			final Object[] valuesFound = StringL1Helper.values(query, values);

			// Updates the value of each key in the values map with '?'.
			final Map<String, Object> updatedValues = DataStructureL1Helper.updateValues(values,
					StringL1Helper.CHARACTER_QUESTION);

			// Convert each variable to '?'.
			final String queryStatement = StringL1Helper.replace(query, updatedValues);

			// Run the query.
			try {

				// Create a JDBC statement.
				final PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.trim());

				// Setup the JDBC statement with the values.
				setupPreparedStatement(preparedStatement, valuesFound);

				// Setup the maximum number of rows to retrieve.
				if ((pageRows > 0) && (page > 0)) {
					preparedStatement.setMaxRows(page * pageRows);
				}

				// Run the query.
				result = preparedStatement.executeQuery();

			} catch (final SQLException e) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot execute the query in Data Store '" + getName() + "' at Service '"
								+ getService().getName() + "' because the database reported the following error: "
								+ e.getMessage(),
						e, LogServiceConstants.LOG_LEVEL_WARN);
			}

		} else {
			try {

				// Create a JDBC statement.
				final Statement statement = connection.createStatement();

				// Setup the maximum number of rows to retrieve.
				if ((pageRows > 0) && (page > 0)) {
					statement.setMaxRows(page * pageRows);
				}

				// Execute the query.
				result = statement.executeQuery(query.trim());

			} catch (final Exception e) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot execute the query in Data Store '" + getName() + "' at Service '"
								+ getService().getName() + "' because the database reported the following error: "
								+ e.getMessage(),
						e, LogServiceConstants.LOG_LEVEL_WARN);
			}
		}

		// Move cursor upto a given page.
		pageResultSet(result, page, pageRows);

		// Set the result for the callback operation.
		if (getConnector().isInitParameter(JdbcConnector.PARAMETER_NATIVE_RESULT_SET)) {
			return result;
		} else {
			return new JdbcResultRows(this, result);
		}

	}

	/**
	 * Executes an <code>INSERT</code>, <code>UPDATE</code> or <code>DELETE</code>
	 * SQL statement. You can also perform with this method other SQL operations
	 * that return no data like <code>CREATE TABLE</code>.
	 * 
	 * @param statement <code>String</code> that represents an <code>INSERT</code>,
	 *                  <code>UPDATE</code> or <code>DELETE</code> SQL statement.
	 *                  This statement may have variables in the form of
	 *                  ${variableName} that can be replaced with values. For
	 *                  example: DELETE FROM PEOPLE WHERE ID=${USER_ID}. You can
	 *                  also perform with this method other SQL operations that
	 *                  return no data like <code>CREATE TABLE</code>.<br>
	 *                  <br>
	 * @param values    Map where the keys represent variable names in the statement
	 *                  and the values those that will replace the variables. Every
	 *                  variable defined in the statement must be inside '${' and
	 *                  '}' so the variable CAR must be in this query-string as
	 *                  '${CAR}'. Pass <code>null</code> to this parameter to make
	 *                  no changes in the statement.<br>
	 *                  <br>
	 * @throws ClientException If there is an error when trying to update the Data
	 *                         Store.<br>
	 *                         <br>
	 */
	void executeUpdate(final String statement, final Map<String, Object> values) throws ClientException {

		// Get the connection.
		final Connection connection = (Connection) getConnection();

		// Execute the statement.
		if ((values != null) && (values.size() > 0)) {

			// Extracts the values in the order that indicates the variables
			// defined in the statement.
			final Object[] valuesFound = StringL1Helper.values(statement, values);

			// Updates the value of each key in the values map with '?'.
			final Map<String, Object> updatedValues = DataStructureL1Helper.updateValues(values,
					StringL1Helper.CHARACTER_QUESTION);

			// Convert each variable to '?'.
			final String updatedStatement = StringL1Helper.replace(statement, updatedValues);

			// Run the statement.
			try {

				// Create a JDBC statement.
				final PreparedStatement preparedStatement = connection.prepareStatement(updatedStatement.trim());

				// Setup the JDBC statement with the values.
				setupPreparedStatement(preparedStatement, valuesFound);

				// Run the statement.
				preparedStatement.executeUpdate();

				// Close the statement.
				preparedStatement.close();

			} catch (final SQLException e) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot execute the statement in Data Store '" + getName() + "' at Service '"
								+ getService().getName() + "' because the database reported the following error: "
								+ e.getMessage(),
						e, LogServiceConstants.LOG_LEVEL_WARN);
			}

		} else {
			try {

				// Create a new statement.
				final Statement jdbcStatement = connection.createStatement();

				// Execute the statement.
				jdbcStatement.executeUpdate(statement.trim());

				// Close the statement.
				jdbcStatement.close();

			} catch (final Exception e) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot execute the statement in Data Store '" + getName() + "' at Service '"
								+ getService().getName() + "' because the database reported the following error: "
								+ e.getMessage(),
						e, LogServiceConstants.LOG_LEVEL_WARN);
			}
		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Sets the values for a prepared statement
	 * 
	 * @param statement  The prepared statement to setup.<br>
	 *                   <br>
	 * @param parameters Values for the prepared statement.<br>
	 *                   <br>
	 * @throws SQLException If there is an error when trying to create the prepared
	 *                      statement.<br>
	 *                      <br>
	 */
	void setupPreparedStatement(final PreparedStatement statement, final Object[] parameters) throws SQLException {
		for (int i = 0; i < parameters.length; i++) {

			// Get one parameter.
			final Object object = parameters[i];

			// Set the value of the parameter in the statement.
			if (object instanceof Boolean) {
				statement.setBoolean((i + 1), ((Boolean) object).booleanValue());
			} else if (object instanceof Byte) {
				statement.setByte((i + 1), ((Byte) object).byteValue());
			} else if (object instanceof Short) {
				statement.setShort((i + 1), ((Short) object).shortValue());
			} else if (object instanceof Integer) {
				statement.setInt((i + 1), ((Integer) object).intValue());
			} else if (object instanceof Long) {
				statement.setLong((i + 1), ((Long) object).longValue());
			} else if (object instanceof Float) {
				statement.setFloat((i + 1), ((Float) object).floatValue());
			} else if (object instanceof Double) {
				statement.setDouble((i + 1), ((Double) object).doubleValue());
			} else if (object instanceof BigDecimal) {
				statement.setBigDecimal((i + 1), (BigDecimal) object);
			} else if (object instanceof String) {
				statement.setString((i + 1), (String) object);
			} else if (object instanceof byte[]) {
				statement.setBytes((i + 1), (byte[]) object);
			} else if (object instanceof java.sql.Date) {

				// Get the SQL date object.
				final java.sql.Date fecha = (java.sql.Date) object;

				// Set the value.
				statement.setDate((i + 1), fecha);

			} else if (object instanceof java.util.Date) {

				// Get the java date object.
				final java.util.Date fecha = (java.util.Date) object;

				// Set the value.
				statement.setTimestamp((i + 1), new Timestamp(fecha.getTime()));

			} else if (object instanceof Calendar) {

				// Get the calendar.
				final Calendar calendar = (Calendar) object;

				// Set the value.
				statement.setTimestamp((i + 1), new Timestamp(calendar.getTime().getTime()));

			} else if (object instanceof java.sql.Time) {
				statement.setTime((i + 1), (java.sql.Time) object);
			} else if (object instanceof Timestamp) {
				statement.setTimestamp((i + 1), (Timestamp) object);
			} else if (object instanceof JdbcNullType) {

				// Get the null type.
				final JdbcNullType type = (JdbcNullType) object;

				// Set the null type.
				statement.setNull((i + 1), type.getType());

			} else {
				getScopeFacade().log(
						"WAREWORK cannot set a given variable in the prepared statement for the statement to run in Data Store '"
								+ getName() + "' of Service '" + getService().getName() + "'.",
						LogServiceConstants.LOG_LEVEL_WARN);
			}

		}
	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Move the cursor of a <code>ResultSet</code> to a specific page.
	 * 
	 * @param result   JDBC <code>ResultSet</code> object.<br>
	 *                 <br>
	 * @param page     Number of pages which hold the number of rows that you want
	 *                 in the result of the database (check out
	 *                 <code>pageRows</code> argument).<br>
	 *                 <br>
	 * @param pageRows Number of rows that you want in the result of the
	 *                 database.<br>
	 *                 <br>
	 * @throws ClientException If there is an error when trying to find the
	 *                         page.<br>
	 *                         <br>
	 */
	private void pageResultSet(final ResultSet result, final int page, final int pageRows) throws ClientException {
		if ((pageRows > 0) && (page > 0)) {
			try {

				/*
				 * Why we don't use 'absolute()' method:
				 * 
				 * http://www.jroller.com/maximdim/entry/oracle_rowid_and_scrollable_resultset
				 */

				// Calculate where to move the cursor.
				final int firstRow = (pageRows * (page - 1)) + 1;

				// Current row counter.
				int currentRow = 1;

				// Move the cursor.
				while (currentRow < firstRow) {

					// Step over one row.
					result.next();

					// Next row.
					currentRow = currentRow + 1;

				}

			} catch (final Exception e) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot move cursor to given page for the query executed in Data Store '" + getName()
								+ "' at Service '" + getService().getName()
								+ "' because there are no more rows to step over.",
						e, LogServiceConstants.LOG_LEVEL_WARN);
			}
		}
	}

}
