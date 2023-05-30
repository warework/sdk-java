package com.warework.service.datastore.client;

import java.util.Map;

import com.warework.core.callback.AbstractCallback;
import com.warework.core.callback.CallbackInvoker;
import com.warework.core.service.client.ClientException;
import com.warework.core.util.helper.StringL1Helper;
import com.warework.service.datastore.AbstractDatastoreView;
import com.warework.service.datastore.view.RdbmsView;
import com.warework.service.log.LogServiceConstants;

/**
 * View that defines common operations for Relational Database Management
 * Systems.<br>
 * 
 * @author Jose Schiaffino
 * @version 2.0.1
 */
public abstract class AbstractRdbmsView extends AbstractDatastoreView implements
		RdbmsView {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// CALLBACK MESSAGES

	static final String MESSAGE_VALIDATE_CALLBACK_BEGIN_TRANSACTION = "begin a transaction";

	static final String MESSAGE_VALIDATE_CALLBACK_ROLLBACK_TRANSACTION = "perform rollback";

	static final String MESSAGE_VALIDATE_CALLBACK_EXECUTE_QUERY = "execute the query";

	static final String MESSAGE_VALIDATE_CALLBACK_EXECUTE_UPDATE = "execute the update statement/s";

	// ///////////////////////////////////////////////////////////////////
	// ABSTRACTS METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Begins a transaction in the Relational Database Management System.
	 * 
	 * @throws ClientException
	 *             If there is an error when trying to begin the transaction in
	 *             the Data Store.<br>
	 * <br>
	 */
	protected abstract void performBeginTransaction() throws ClientException;

	/**
	 * Rolls back changes in the Relational Database Management System.
	 * 
	 * @throws ClientException
	 *             If there is an error when trying to perform rollback in the
	 *             Data Store.<br>
	 * <br>
	 */
	protected abstract void performRollback() throws ClientException;

	/**
	 * Executes an SQL query statement in the Relational Database Management
	 * System.
	 * 
	 * @param sql
	 *            SQL statement, typically a static SQL <code>SELECT</code>
	 *            statement, to retrieve data from the relational database.<br>
	 * <br>
	 * @param values
	 *            Map where the keys represent variable names in the
	 *            query-string loaded from the Provider and the values those
	 *            that will replace the variables. Every variable must be inside
	 *            '${' and '}' so the variable CAR must be in this query-string
	 *            as '${CAR}'. Pass <code>null</code> to this parameter to make
	 *            no changes in the query loaded.<br>
	 * <br>
	 * @param page
	 *            Page to get from the result. When you specify the number of
	 *            rows (check out <code>pageRows</code> argument of this method)
	 *            that you want in the result of a query, Warework automatically
	 *            calculates the number of pages that hold this number of rows.
	 *            You have to pass an integer value greater than zero to
	 *            retrieve a specific page from the result. Set this argument to
	 *            <code>-1</code> to retrieve every result in one page.<br>
	 * <br>
	 * @param pageRows
	 *            Number of rows that you want in the result of the database.
	 *            Set this argument to <code>-1</code> to retrieve every row.<br>
	 * <br>
	 * @param invoker
	 *            Object required to invoke the callback operation.<br>
	 * <br>
	 */
	protected abstract void performQuery(String sql,
			Map<String, Object> values, int page, int pageRows,
			CallbackInvoker invoker);

	/**
	 * Executes a set of SQL update statements in the Relational Database
	 * Management System
	 * 
	 * @param statement
	 *            SQL statement, which may be an <code>INSERT</code>,
	 *            <code>UPDATE</code>, or <code>DELETE</code> statement or an
	 *            SQL statement that returns nothing, such as an SQL DDL
	 *            statement (for example: <code>CREATE TABLE</code>).<br>
	 * <br>
	 * @param values
	 *            Map where the keys represent variable names in the
	 *            update-string loaded from the Provider and the values those
	 *            that will replace the variables. Every variable must be inside
	 *            '${' and '}' so the variable CAR must be in this
	 *            statement-string as '${CAR}'. Pass <code>null</code> to this
	 *            parameter to make no changes in the statement loaded.<br>
	 * <br>
	 * @param invoker
	 *            Object required to invoke the callback operation.<br>
	 * <br>
	 */
	protected abstract void performUpdate(String statement,
			Map<String, Object> values, CallbackInvoker invoker);

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Begins a transaction in the Relational Database Management System.
	 * 
	 * @throws ClientException
	 *             If there is an error when trying to begin the transaction in
	 *             the Data Store.<br>
	 * <br>
	 */
	public void beginTransaction() throws ClientException {

		// Validate connection.
		validateConnection(MESSAGE_VALIDATE_CALLBACK_BEGIN_TRANSACTION);

		// Begin transaction.
		performBeginTransaction();

	}

	/**
	 * Rolls back changes in the Relational Database Management System.
	 * 
	 * @throws ClientException
	 *             If there is an error when trying to perform rollback in the
	 *             Data Store.<br>
	 * <br>
	 */
	public void rollback() throws ClientException {

		// Validate connection.
		validateConnection(MESSAGE_VALIDATE_CALLBACK_ROLLBACK_TRANSACTION);

		// Rollback.
		performRollback();

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Executes an SQL query statement in the Relational Database Management
	 * System.
	 * 
	 * @param sql
	 *            SQL statement, typically a static SQL <code>SELECT</code>
	 *            statement, to retrieve data from the relational database.<br>
	 * <br>
	 * @param values
	 *            Map where the keys represent variable names in the query
	 *            statement and the values those that will replace the
	 *            variables. Every variable must be inside '${' and '}' so the
	 *            variable CAR must be in this query-string as '${CAR}'. Pass
	 *            <code>null</code> to this parameter to make no changes in the
	 *            query.<br>
	 * <br>
	 * @param page
	 *            Page to get from the result. When you specify the number of
	 *            rows (check out <code>pageRows</code> argument of this method)
	 *            that you want in the result of a query, Warework automatically
	 *            calculates the number of pages that hold this number of rows.
	 *            You have to pass an integer value greater than zero to
	 *            retrieve a specific page from the result. Set this argument to
	 *            <code>-1</code> to retrieve every result in one page.<br>
	 * <br>
	 * @param pageRows
	 *            Number of rows that you want in the result of the database.
	 *            Set this argument to <code>-1</code> to retrieve every row.<br>
	 * <br>
	 * @return Object that holds the result of the query.<br>
	 * <br>
	 * @throws ClientException
	 *             If there is an error when trying to query the Data Store.<br>
	 * <br>
	 */
	public Object executeQuery(String sql, Map<String, Object> values,
			int page, int pageRows) throws ClientException {
		return handleQuery(sql, values, page, pageRows);
	}

	/**
	 * Executes an SQL query statement in the Relational Database Management
	 * System.
	 * 
	 * @param sql
	 *            SQL statement, typically a static SQL <code>SELECT</code>
	 *            statement, to retrieve data from the relational database.<br>
	 * <br>
	 * @param values
	 *            Map where the keys represent variable names in the query
	 *            statement and the values those that will replace the
	 *            variables. Every variable must be inside '${' and '}' so the
	 *            variable CAR must be in this query-string as '${CAR}'. Pass
	 *            <code>null</code> to this parameter to make no changes in the
	 *            query.<br>
	 * <br>
	 * @param page
	 *            Page to get from the result. When you specify the number of
	 *            rows (check out <code>pageRows</code> argument of this method)
	 *            that you want in the result of a query, Warework automatically
	 *            calculates the number of pages that hold this number of rows.
	 *            You have to pass an integer value greater than zero to
	 *            retrieve a specific page from the result. Set this argument to
	 *            <code>-1</code> to retrieve every result in one page.<br>
	 * <br>
	 * @param pageRows
	 *            Number of rows that you want in the result of the database.
	 *            Set this argument to <code>-1</code> to retrieve every row.<br>
	 * <br>
	 * @param callback
	 *            Operation response handler.<br>
	 * <br>
	 */
	public void executeQuery(String sql, Map<String, Object> values, int page,
			int pageRows, AbstractCallback callback) {
		handleQuery(sql, values, page, pageRows, callback);
	}

	/**
	 * Loads a String that represents an SQL query (typically a static SQL
	 * <code>SELECT</code> statement) from the default Provider defined for this
	 * View (or the next View in the stack of Views of the Data Store) and
	 * executes it in the Relational Database Management System.
	 * 
	 * @param name
	 *            Name of the query statement to load from the default Provider
	 *            defined for this View.<br>
	 * <br>
	 * @param values
	 *            Map where the keys represent variable names in the
	 *            query-string loaded from the Provider and the values those
	 *            that will replace the variables. Every variable must be inside
	 *            '${' and '}' so the variable CAR must be in this query-string
	 *            as '${CAR}'. Pass <code>null</code> to this parameter to make
	 *            no changes in the query loaded.<br>
	 * <br>
	 * @param page
	 *            Page to get from the result. When you specify the number of
	 *            rows (check out <code>pageRows</code> argument) that you want
	 *            in the result of a database, Warework automatically calculates
	 *            the number of pages that hold this number of rows. You have to
	 *            pass an integer value greater than zero to retrieve a specific
	 *            page from the result. Set this argument to <code>-1</code> to
	 *            retrieve every row in one page.<br>
	 * <br>
	 * @param pageRows
	 *            Number of rows that you want in the result of the query. Set
	 *            this argument to <code>-1</code> to retrieve every row.<br>
	 * <br>
	 * @return Object that holds the result of the query.<br>
	 * <br>
	 * @throws ClientException
	 *             If there is an error when trying to query the Data Store.<br>
	 * <br>
	 */
	public Object executeQueryByName(String name, Map<String, Object> values,
			int page, int pageRows) throws ClientException {
		return executeQueryByName(null, name, values, page, pageRows);
	}

	/**
	 * Loads a String that represents an SQL query (typically a static SQL
	 * <code>SELECT</code> statement) from the default Provider defined for this
	 * View (or the next View in the stack of Views of the Data Store) and
	 * executes it in the Relational Database Management System.
	 * 
	 * @param name
	 *            Name of the query statement to load from the default Provider
	 *            defined for this View.<br>
	 * <br>
	 * @param values
	 *            Map where the keys represent variable names in the
	 *            query-string loaded from the Provider and the values those
	 *            that will replace the variables. Every variable must be inside
	 *            '${' and '}' so the variable CAR must be in this query-string
	 *            as '${CAR}'. Pass <code>null</code> to this parameter to make
	 *            no changes in the query loaded.<br>
	 * <br>
	 * @param page
	 *            Page to get from the result. When you specify the number of
	 *            rows (check out <code>pageRows</code> argument) that you want
	 *            in the result of a database, Warework automatically calculates
	 *            the number of pages that hold this number of rows. You have to
	 *            pass an integer value greater than zero to retrieve a specific
	 *            page from the result. Set this argument to <code>-1</code> to
	 *            retrieve every row in one page.<br>
	 * <br>
	 * @param pageRows
	 *            Number of rows that you want in the result of the query. Set
	 *            this argument to <code>-1</code> to retrieve every row.<br>
	 * <br>
	 * @param callback
	 *            Operation response handler.<br>
	 * <br>
	 */
	public void executeQueryByName(String name, Map<String, Object> values,
			int page, int pageRows, AbstractCallback callback) {
		executeQueryByName(null, name, values, page, pageRows, callback);
	}

	/**
	 * Loads a String that represents an SQL query (typically a static SQL
	 * <code>SELECT</code> statement) from a given Provider and executes it in
	 * the Relational Database Management System.
	 * 
	 * @param providerName
	 *            Name of the Provider where to load the statement.<br>
	 * <br>
	 * @param queryName
	 *            Name of the query statement to load from the Provider.<br>
	 * <br>
	 * @param values
	 *            Map where the keys represent variable names in the
	 *            query-string loaded from the Provider and the values those
	 *            that will replace the variables. Every variable must be inside
	 *            '${' and '}' so the variable CAR must be in this query-string
	 *            as '${CAR}'. Pass <code>null</code> to this parameter to make
	 *            no changes in the query loaded.<br>
	 * <br>
	 * @param page
	 *            Page to get from the result. When you specify the number of
	 *            rows (check out <code>pageRows</code> argument) that you want
	 *            in the result of a database, Warework automatically calculates
	 *            the number of pages that hold this number of rows. You have to
	 *            pass an integer value greater than zero to retrieve a specific
	 *            page from the result. Set this argument to <code>-1</code> to
	 *            retrieve every row in one page.<br>
	 * <br>
	 * @param pageRows
	 *            Number of rows that you want in the result of the query. Set
	 *            this argument to <code>-1</code> to retrieve every row.<br>
	 * <br>
	 * @return Object that holds the result of the query.<br>
	 * <br>
	 * @throws ClientException
	 *             If there is an error when trying to query the Data Store.<br>
	 * <br>
	 */
	public Object executeQueryByName(String providerName, String queryName,
			Map<String, Object> values, int page, int pageRows)
			throws ClientException {

		// Read the query.
		Object query = getStatement(providerName, queryName);

		// Execute the query.
		return handleQuery(query, values, page, pageRows);

	}

	/**
	 * Loads a String that represents an SQL query (typically a static SQL
	 * <code>SELECT</code> statement) from a given Provider and executes it in
	 * the Relational Database Management System.
	 * 
	 * @param providerName
	 *            Name of the Provider where to load the statement.<br>
	 * <br>
	 * @param queryName
	 *            Name of the query statement to load from the Provider.<br>
	 * <br>
	 * @param values
	 *            Map where the keys represent variable names in the
	 *            query-string loaded from the Provider and the values those
	 *            that will replace the variables. Every variable must be inside
	 *            '${' and '}' so the variable CAR must be in this query-string
	 *            as '${CAR}'. Pass <code>null</code> to this parameter to make
	 *            no changes in the query loaded.<br>
	 * <br>
	 * @param page
	 *            Page to get from the result. When you specify the number of
	 *            rows (check out <code>pageRows</code> argument) that you want
	 *            in the result of a database, Warework automatically calculates
	 *            the number of pages that hold this number of rows. You have to
	 *            pass an integer value greater than zero to retrieve a specific
	 *            page from the result. Set this argument to <code>-1</code> to
	 *            retrieve every row in one page.<br>
	 * <br>
	 * @param pageRows
	 *            Number of rows that you want in the result of the query. Set
	 *            this argument to <code>-1</code> to retrieve every row.<br>
	 * <br>
	 * @param callback
	 *            Operation response handler.<br>
	 * <br>
	 */
	public void executeQueryByName(String providerName, String queryName,
			Map<String, Object> values, int page, int pageRows,
			AbstractCallback callback) {

		// Read the query.
		Object query = getStatement(providerName, queryName);

		// Execute the query.
		handleQuery(query, values, page, pageRows, callback);

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Executes a set of SQL update statements in the Relational Database
	 * Management System.
	 * 
	 * @param statements
	 *            SQL statements, which may be an <code>INSERT</code>,
	 *            <code>UPDATE</code>, or <code>DELETE</code> statements or SQL
	 *            statements that returns nothing, such as an SQL DDL script
	 *            (for example: with <code>CREATE TABLE</code> statements).<br>
	 * <br>
	 * @param values
	 *            Map where the keys represent variable names in the
	 *            query-string loaded from the Provider and the values those
	 *            that will replace the variables. Every variable must be inside
	 *            '${' and '}' so the variable CAR must be in this query-string
	 *            as '${CAR}'. Pass <code>null</code> to this parameter to make
	 *            no changes in the query loaded.<br>
	 * <br>
	 * @param delimiter
	 *            Character that separates each statement (for example: ';').
	 *            Use <code>null</code> to indicate that just one statement is
	 *            passed to the database.<br>
	 * <br>
	 * @throws ClientException
	 *             If there is an error when trying to update the Data Store.<br>
	 * <br>
	 */
	public void executeUpdate(String statements, Map<String, Object> values,
			Character delimiter) throws ClientException {
		update(statements, values, delimiter);
	}

	/**
	 * Executes a set of SQL update statements in the Relational Database
	 * Management System.
	 * 
	 * @param statements
	 *            SQL statements, which may be an <code>INSERT</code>,
	 *            <code>UPDATE</code>, or <code>DELETE</code> statements or SQL
	 *            statements that returns nothing, such as an SQL DDL script
	 *            (for example: with <code>CREATE TABLE</code> statements).<br>
	 * <br>
	 * @param values
	 *            Map where the keys represent variable names in the
	 *            query-string loaded from the Provider and the values those
	 *            that will replace the variables. Every variable must be inside
	 *            '${' and '}' so the variable CAR must be in this query-string
	 *            as '${CAR}'. Pass <code>null</code> to this parameter to make
	 *            no changes in the query loaded.<br>
	 * <br>
	 * @param delimiter
	 *            Character that separates each statement (for example: ';').
	 *            Use <code>null</code> to indicate that just one statement is
	 *            passed to the database.<br>
	 * <br>
	 * @param callback
	 *            Operation response handler.<br>
	 * <br>
	 */
	public void executeUpdate(String statements, Map<String, Object> values,
			Character delimiter, AbstractCallback callback) {
		update(statements, values, delimiter, callback);
	}

	/**
	 * Reads a set of SQL update statements in the default Provider defined for
	 * this View (or the next View in the stack of Views of the Data Store) and
	 * executes them in the Relational Database Management System.
	 * 
	 * @param name
	 *            Name of the statement to load from the default Provider
	 *            defined for this View.<br>
	 * <br>
	 * @param values
	 *            Map where the keys represent variable names in the
	 *            update-string loaded from the Provider and the values those
	 *            that will replace the variables. Every variable must be inside
	 *            '${' and '}' so the variable CAR must be in this
	 *            statement-string as '${CAR}'. Pass <code>null</code> to this
	 *            parameter to make no changes in the statement loaded.<br>
	 * <br>
	 * @param delimiter
	 *            Character that separates each statement (i.e.: ';'). Use
	 *            <code>null</code> to indicate that just one statement is
	 *            passed to the database.<br>
	 * <br>
	 * @throws ClientException
	 *             If there is an error when trying to update the Data Store.<br>
	 * <br>
	 */
	public void executeUpdateByName(String name, Map<String, Object> values,
			Character delimiter) throws ClientException {

		// Read the statements.
		Object statement = getStatement(name);

		// Execute the statements.
		update(statement, values, delimiter);

	}

	/**
	 * Reads a set of SQL update statements in the default Provider defined for
	 * this View (or the next View in the stack of Views of the Data Store) and
	 * executes them in the Relational Database Management System.
	 * 
	 * @param name
	 *            Name of the statement to load from the default Provider
	 *            defined for this View.<br>
	 * <br>
	 * @param values
	 *            Map where the keys represent variable names in the
	 *            update-string loaded from the Provider and the values those
	 *            that will replace the variables. Every variable must be inside
	 *            '${' and '}' so the variable CAR must be in this
	 *            statement-string as '${CAR}'. Pass <code>null</code> to this
	 *            parameter to make no changes in the statement loaded.<br>
	 * <br>
	 * @param delimiter
	 *            Character that separates each statement (i.e.: ';'). Use
	 *            <code>null</code> to indicate that just one statement is
	 *            passed to the database.<br>
	 * <br>
	 * @param callback
	 *            Operation response handler.<br>
	 * <br>
	 */
	public void executeUpdateByName(String name, Map<String, Object> values,
			Character delimiter, AbstractCallback callback) {

		// Read the statements.
		Object statement = getStatement(name);

		// Execute the statements.
		update(statement, values, delimiter, callback);

	}

	/**
	 * Reads a set of SQL update statements from a Provider and executes them in
	 * the Relational Database Management System.
	 * 
	 * @param providerName
	 *            Name of the Provider where to read the statement.<br>
	 * <br>
	 * @param statementName
	 *            Name of the statement to load from the Provider.<br>
	 * <br>
	 * @param values
	 *            Map where the keys represent variable names in the
	 *            update-string loaded from the Provider and the values those
	 *            that will replace the variables. Every variable must be inside
	 *            '${' and '}' so the variable CAR must be in this
	 *            statement-string as '${CAR}'. Pass <code>null</code> to this
	 *            parameter to make no changes in the statement loaded.<br>
	 * <br>
	 * @param delimiter
	 *            Character that separates each statement (i.e.: ';'). Use
	 *            <code>null</code> to indicate that just one statement is
	 *            passed to the database.<br>
	 * <br>
	 * @throws ClientException
	 *             If there is an error when trying to update the Data Store.<br>
	 * <br>
	 */
	public void executeUpdateByName(String providerName, String statementName,
			Map<String, Object> values, Character delimiter)
			throws ClientException {

		// Read the statements.
		Object statements = getScopeFacade().getObject(providerName, statementName);

		// Execute the statements.
		update(statements, values, delimiter);

	}

	/**
	 * Reads a set of SQL update statements from a Provider and executes them in
	 * the Relational Database Management System.
	 * 
	 * @param providerName
	 *            Name of the Provider where to read the statement.<br>
	 * <br>
	 * @param statementName
	 *            Name of the statement to load from the Provider.<br>
	 * <br>
	 * @param values
	 *            Map where the keys represent variable names in the
	 *            update-string loaded from the Provider and the values those
	 *            that will replace the variables. Every variable must be inside
	 *            '${' and '}' so the variable CAR must be in this
	 *            statement-string as '${CAR}'. Pass <code>null</code> to this
	 *            parameter to make no changes in the statement loaded.<br>
	 * <br>
	 * @param delimiter
	 *            Character that separates each statement (i.e.: ';'). Use
	 *            <code>null</code> to indicate that just one statement is
	 *            passed to the database.<br>
	 * <br>
	 * @param callback
	 *            Operation response handler.<br>
	 * <br>
	 */
	public void executeUpdateByName(String providerName, String statementName,
			Map<String, Object> values, Character delimiter,
			AbstractCallback callback) {

		// Read the statements.
		Object statements = getScopeFacade().getObject(providerName, statementName);

		// Execute the statements.
		update(statements, values, delimiter, callback);

	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Executes an SQL query statement in the Relational Database Management
	 * System.
	 * 
	 * @param query
	 *            SQL statement, typically a static SQL <code>SELECT</code>
	 *            statement, to retrieve data from the relational database.<br>
	 * <br>
	 * @param values
	 *            Map where the keys represent variable names in the
	 *            query-string loaded from the Provider and the values those
	 *            that will replace the variables. Every variable must be inside
	 *            '${' and '}' so the variable CAR must be in this query-string
	 *            as '${CAR}'. Pass <code>null</code> to this parameter to make
	 *            no changes in the query loaded.<br>
	 * <br>
	 * @param page
	 *            Page to get from the result. When you specify the number of
	 *            rows (check out <code>pageRows</code> argument of this method)
	 *            that you want in the result of a query, Warework automatically
	 *            calculates the number of pages that hold this number of rows.
	 *            You have to pass an integer value greater than zero to
	 *            retrieve a specific page from the result. Set this argument to
	 *            <code>-1</code> to retrieve every result in one page.<br>
	 * <br>
	 * @param pageRows
	 *            Number of rows that you want in the result of the database.
	 *            Set this argument to <code>-1</code> to retrieve every row.<br>
	 * <br>
	 * @return Object that holds the result of the query.<br>
	 * <br>
	 * @throws ClientException
	 *             If there is an error when trying to update the Data Store.<br>
	 * <br>
	 */
	private Object handleQuery(Object query, Map<String, Object> values,
			int page, int pageRows) throws ClientException {

		// Create a default callback handler.
		AbstractCallback callback = createDefaultCallback();

		// Execute the statement.
		CallbackInvoker invoker = handleQuery(query, values, page, pageRows,
				callback);

		// Validate the callback operation.
		validateCallback(callback, MESSAGE_VALIDATE_CALLBACK_EXECUTE_QUERY);

		// Return the result of the operation.
		return invoker.getResult();

	}

	/**
	 * Executes an SQL query statement in the Relational Database Management
	 * System.
	 * 
	 * @param query
	 *            SQL statement, typically a static SQL <code>SELECT</code>
	 *            statement, to retrieve data from the relational database.<br>
	 * <br>
	 * @param values
	 *            Map where the keys represent variable names in the
	 *            query-string loaded from the Provider and the values those
	 *            that will replace the variables. Every variable must be inside
	 *            '${' and '}' so the variable CAR must be in this query-string
	 *            as '${CAR}'. Pass <code>null</code> to this parameter to make
	 *            no changes in the query loaded.<br>
	 * <br>
	 * @param page
	 *            Page to get from the result. When you specify the number of
	 *            rows (check out <code>pageRows</code> argument of this method)
	 *            that you want in the result of a query, Warework automatically
	 *            calculates the number of pages that hold this number of rows.
	 *            You have to pass an integer value greater than zero to
	 *            retrieve a specific page from the result. Set this argument to
	 *            <code>-1</code> to retrieve every result in one page.<br>
	 * <br>
	 * @param pageRows
	 *            Number of rows that you want in the result of the database.
	 *            Set this argument to <code>-1</code> to retrieve every row.<br>
	 * <br>
	 * @param callback
	 *            Operation response handler.<br>
	 * <br>
	 * @return Object required to invoke the callback that holds the result of
	 *         the operation.<br>
	 * <br>
	 */
	private CallbackInvoker handleQuery(Object query,
			Map<String, Object> values, int page, int pageRows,
			AbstractCallback callback) {

		// Validate connection.
		if (!validateConnection(callback,
				MESSAGE_VALIDATE_CALLBACK_EXECUTE_QUERY)) {
			return null;
		}

		// Execute the query.
		if (query instanceof String) {

			// Log a message.
			getScopeFacade().log(
					"WAREWORK is going to execute the following query in Data Store '"
							+ getName() + "' of Service '"
							+ getService().getName() + "':\n"
							+ query.toString(),
					LogServiceConstants.LOG_LEVEL_DEBUG);

			// Create the message for the successful operation.
			String successMessage = "WAREWORK successfully executed the query in Data Store '"
					+ getName()
					+ "' of Service '"
					+ getService().getName()
					+ "'.";

			// Initialize callback batch parameters.
			callback.getControl().destroyBatch();

			// Create the object required to invoke the callback
			// operation.
			CallbackInvoker invoker = CallbackInvoker.createInvoker(callback,
					null, successMessage, null);

			// Run the query in the database.
			performQuery((String) query, values, page, pageRows, invoker);

			// Return the callback invoker.
			return invoker;

		} else {

			// Invoke failure in callback operation.
			CallbackInvoker
					.invokeFailure(
							callback,
							null,
							new ClientException(
									getScopeFacade(),
									"WAREWORK cannot execute the query in Data Store '"
											+ getName()
											+ "' at Service '"
											+ getService().getName()
											+ "' because the query returned by the Provider is null or it is not a string.",
									null, LogServiceConstants.LOG_LEVEL_WARN));

			// At this point, nothing to return.
			return null;

		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Executes a set of SQL update statements in the Relational Database
	 * Management System.
	 * 
	 * @param statements
	 *            SQL statements, which may be an <code>INSERT</code>,
	 *            <code>UPDATE</code>, or <code>DELETE</code> statements or SQL
	 *            statements that returns nothing, such as an SQL DDL script
	 *            (for example: with <code>CREATE TABLE</code> statements).<br>
	 * <br>
	 * @param values
	 *            Map where the keys represent variable names in the
	 *            update-string loaded from the Provider and the values those
	 *            that will replace the variables. Every variable must be inside
	 *            '${' and '}' so the variable CAR must be in this
	 *            statement-string as '${CAR}'. Pass <code>null</code> to this
	 *            parameter to make no changes in the statement loaded.<br>
	 * <br>
	 * @param delimiter
	 *            Character that separates each statement (i.e.: ';'). Use
	 *            <code>null</code> to indicate that just one statement is
	 *            passed to the database.<br>
	 * <br>
	 * @throws ClientException
	 *             If there is an error when trying to update the Data Store.<br>
	 * <br>
	 */
	private void update(Object statements, Map<String, Object> values,
			Character delimiter) throws ClientException {

		// Create a default callback handler.
		AbstractCallback callback = createDefaultCallback();

		// Execute the statement.
		update(statements, values, delimiter, callback);

		// Validate the callback operation.
		validateCallback(callback, MESSAGE_VALIDATE_CALLBACK_EXECUTE_UPDATE);

	}

	/**
	 * Executes a set of SQL update statements in the Relational Database
	 * Management System.
	 * 
	 * @param statements
	 *            SQL statements, which may be an <code>INSERT</code>,
	 *            <code>UPDATE</code>, or <code>DELETE</code> statements or SQL
	 *            statements that returns nothing, such as an SQL DDL script
	 *            (for example: with <code>CREATE TABLE</code> statements).<br>
	 * <br>
	 * @param values
	 *            Map where the keys represent variable names in the
	 *            update-string loaded from the Provider and the values those
	 *            that will replace the variables. Every variable must be inside
	 *            '${' and '}' so the variable CAR must be in this
	 *            statement-string as '${CAR}'. Pass <code>null</code> to this
	 *            parameter to make no changes in the statement loaded.<br>
	 * <br>
	 * @param delimiter
	 *            Character that separates each statement (i.e.: ';'). Use
	 *            <code>null</code> to indicate that just one statement is
	 *            passed to the database.<br>
	 * <br>
	 * @param callback
	 *            Operation response handler.<br>
	 * <br>
	 */
	private void update(Object statements, Map<String, Object> values,
			Character delimiter, AbstractCallback callback) {

		// Validate connection.
		if (!validateConnection(callback,
				MESSAGE_VALIDATE_CALLBACK_EXECUTE_UPDATE)) {
			return;
		}

		// Execute update.
		if (statements instanceof String) {
			if (delimiter == null) {

				// Log a message.
				getScopeFacade().log(
						"WAREWORK is going to execute the following update statement in Data Store '"
								+ getName() + "' of Service '"
								+ getService().getName() + "':\n"
								+ statements.toString(),
						LogServiceConstants.LOG_LEVEL_DEBUG);

				// Create the message for the successful operation.
				String successMessage = "WAREWORK successfully executed the statement in Data Store '"
						+ getName()
						+ "' of Service '"
						+ getService().getName()
						+ "'.";

				// Initialize callback batch parameters.
				callback.getControl().destroyBatch();

				// Create the object required to invoke the callback
				// operation.
				CallbackInvoker invoker = CallbackInvoker.createInvoker(
						callback, null, successMessage, null);

				// Execute the statement.
				performUpdate((String) statements, values, invoker);

			} else {

				// Breaks a given SQL into multiple statements.
				String[] updates = StringL1Helper.toStrings(
						(String) statements, delimiter.charValue());

				// Get the size of the array.
				int length = updates.length;

				// Perform a batch operation when required.
				if (length > 1) {

					// Initialize callback batch parameters.
					callback.getControl().initBatch(length);

					// Log batch operation.
					getScopeFacade()
							.log("WAREWORK started batch operation '"
									+ callback.getBatch().id()
									+ "' in Data Store '" + getName()
									+ "' of Service '" + getService().getName()
									+ "' to execute " + length + " statements.",
									LogServiceConstants.LOG_LEVEL_INFO);

					// Execute each SQL statement.
					for (int index = 0; index < updates.length; index++) {

						// Get the statement to execute.
						String statement = updates[index];

						// Log a message.
						getScopeFacade().log(
								"WAREWORK is going to execute update statement "
										+ (index + 1) + " of " + length
										+ " from batch operation '"
										+ callback.getBatch().id()
										+ "' in Data Store '" + getName()
										+ "' of Service '"
										+ getService().getName() + "':\n"
										+ statement,
								LogServiceConstants.LOG_LEVEL_DEBUG);

						// Create the message for the successful operation.
						String successMessage = "WAREWORK successfully executed statement "
								+ (index + 1)
								+ " of "
								+ length
								+ " from batch operation '"
								+ callback.getBatch().id()
								+ "' in Data Store '"
								+ getName()
								+ "' of Service '"
								+ getService().getName()
								+ "'.";

						// Create the object required to invoke the callback
						// operation.
						CallbackInvoker invoker = CallbackInvoker
								.createInvoker(callback, null, successMessage,
										null);

						// Execute the statement.
						performUpdate(statement, values, invoker);

						// Break loop if operation fails.
						if (callback.getControl().isFailure()) {
							return;
						}

					}

				} else if (length == 1) {

					// Log a message.
					getScopeFacade().log(
							"WAREWORK is going to execute the following update statement in Data Store '"
									+ getName() + "' of Service '"
									+ getService().getName() + "':\n"
									+ updates[0],
							LogServiceConstants.LOG_LEVEL_DEBUG);

					// Create the message for the successful operation.
					String successMessage = "WAREWORK successfully executed the statement in Data Store '"
							+ getName()
							+ "' of Service '"
							+ getService().getName() + "'.";

					// Initialize callback batch parameters.
					callback.getControl().destroyBatch();

					// Create the object required to invoke the callback
					// operation.
					CallbackInvoker invoker = CallbackInvoker.createInvoker(
							callback, null, successMessage, null);

					// Execute the statement.
					performUpdate(updates[0], values, invoker);

				} else {
					CallbackInvoker
							.invokeFailure(
									callback,
									null,
									new ClientException(
											getScopeFacade(),
											"WAREWORK cannot execute the update statement in Data Store '"
													+ getName()
													+ "' at Service '"
													+ getService().getName()
													+ "' because there are no statements to execute.",
											null,
											LogServiceConstants.LOG_LEVEL_WARN));
				}

			}
		} else {
			CallbackInvoker
					.invokeFailure(
							callback,
							null,
							new ClientException(
									getScopeFacade(),
									"WAREWORK cannot execute the update statement/s in Data Store '"
											+ getName()
											+ "' at Service '"
											+ getService().getName()
											+ "' because the statement returned by the Provider is null or it is not a string.",
									null, LogServiceConstants.LOG_LEVEL_WARN));
		}

	}

}
