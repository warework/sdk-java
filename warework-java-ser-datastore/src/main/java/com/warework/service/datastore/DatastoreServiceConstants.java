package com.warework.service.datastore;

import com.warework.core.service.ProxyServiceConstants;
import com.warework.core.util.CommonValueL1Constants;
import com.warework.core.util.helper.StringL1Helper;

/**
 * Constants for the Data Store Service.<br>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public abstract class DatastoreServiceConstants extends ProxyServiceConstants {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Constant that identifies this Service type.
	 */
	public static final String SERVICE = "datastore";

	/**
	 * Constant that defines the default name for this Service.
	 */
	public static final String DEFAULT_SERVICE_NAME = SERVICE
			+ StringL1Helper.CHARACTER_HYPHEN
			+ CommonValueL1Constants.STRING_SERVICE;

	// OPERATION NAMES

	/**
	 * Executes an statement that retrieves information from the Data Store. Use
	 * the following parameters in order to invoke this operation:<br>
	 * <br>
	 * <ul>
	 * <li><b><code>client-name</code></b>: The name to which the Data Store is
	 * bound in the Service. This parameter must be a
	 * <code>java.lang.String</code>.<br>
	 * <br>
	 * </li>
	 * <li><b><code>statement</code></b>: Specific query language accepted by
	 * the Data Store (SQL, for example). This parameter is mandatory (a
	 * <code>java.lang.Object</code>).<br>
	 * <br>
	 * </li>
	 * <li><b><code>view-name</code></b>: Name of the view of the Data Store
	 * where to execute the query. This parameter is optional. When provided, it
	 * must be a <code>java.lang.String</code>. If it is not provided then this
	 * operation will execute the query in the Current View of the Data Store.</li>
	 * </ul>
	 * This operation returns a new <code>java.lang.Object</code> that holds the
	 * result of the query.
	 */
	public static final String OPERATION_NAME_QUERY = "query";

	/**
	 * Executes an statement from a Provider that retrieves information from the
	 * Data Store. Use the following parameters in order to invoke this
	 * operation:<br>
	 * <br>
	 * <ul>
	 * <li><b><code>client-name</code></b>: The name to which the Data Store is
	 * bound in the Service. This parameter must be a
	 * <code>java.lang.String</code>.<br>
	 * <br>
	 * </li>
	 * <li><b><code>provider-name</code></b>: Name of the Provider where to
	 * retrieve a query object. This parameter must be a
	 * <code>java.lang.String</code>.<br>
	 * <br>
	 * </li>
	 * <li><b><code>statement-name</code></b>: Name of the query to retrieve
	 * from the Provider. This parameter must be a <code>java.lang.String</code>
	 * .<br>
	 * <br>
	 * </li>
	 * <li><b><code>values</code></b>: Variables to replace in the statement
	 * loaded from the Provider. This parameter is optional and when provided it
	 * must be a map where the keys are the names of the variables in the
	 * statement and the values those that will replace the variables. Each Data
	 * Store may process this values in a specific way (not every Data Store
	 * support the replacement of variables). When the statement loaded from the
	 * Provider represents a String object, every variable must be inside '${'
	 * and '}' so the variable CAR must be in this statement-string as '${CAR}'.
	 * Do not provide this parameter to make no changes in the statement loaded.
	 * <br>
	 * <br>
	 * </li>
	 * <li><b><code>view-name</code></b>: Name of the view of the Data Store
	 * where to execute the query. This parameter is optional. When provided, it
	 * must be a <code>java.lang.String</code>. If it is not provided then this
	 * operation will execute the query in the Current View of the Data Store.</li>
	 * </ul>
	 * This operation returns a new <code>java.lang.Object</code> that holds the
	 * result of the query.
	 */
	public static final String OPERATION_NAME_QUERY_BY_NAME = "query-by-name";

	/**
	 * Executes an statement that inserts, updates or deletes data. Use the
	 * following parameters in order to invoke this operation:<br>
	 * <br>
	 * <ul>
	 * <li><b><code>client-name</code></b>: The name to which the Data Store is
	 * bound in the Service. This parameter must be a
	 * <code>java.lang.String</code>.<br>
	 * <br>
	 * </li>
	 * <li><b><code>statement</code></b>: Indicates the specific operation to
	 * perform and the data to update in the Data Store. This parameter is
	 * mandatory (a <code>java.lang.Object</code>).<br>
	 * <br>
	 * </li>
	 * <li><b><code>view-name</code></b>: Name of the view of the Data Store
	 * where to execute the update statement. This parameter is optional. When
	 * provided, it must be a <code>java.lang.String</code>. If it is not
	 * provided then this operation will execute the update statement in the
	 * Current View of the Data Store.</li>
	 * </ul>
	 * This operation does not return anything.
	 */
	public static final String OPERATION_NAME_UPDATE = "update";

	/**
	 * Executes an statement from a Provider that inserts, updates or deletes
	 * data. Use the following parameters in order to invoke this operation:<br>
	 * <br>
	 * <ul>
	 * <li><b><code>client-name</code></b>: The name to which the Data Store is
	 * bound in the Service. This parameter must be a
	 * <code>java.lang.String</code>.<br>
	 * <br>
	 * </li>
	 * <li><b><code>provider-name</code></b>: Name of the Provider where to
	 * retrieve the update statement object. This parameter must be a
	 * <code>java.lang.String</code>.<br>
	 * <br>
	 * </li>
	 * <li><b><code>statement-name</code></b>: Name of the update statement to
	 * retrieve from the Provider. This parameter must be a
	 * <code>java.lang.String</code> .<br>
	 * <br>
	 * </li>
	 * <li><b><code>values</code></b>: Variables to replace in the statement
	 * loaded from the Provider. This parameter is optional and when provided it
	 * must be a map where the keys are the names of the variables in the
	 * statement and the values those that will replace the variables. Each Data
	 * Store may process this values in a specific way (not every Data Store
	 * support the replacement of variables). When the statement loaded from the
	 * Provider represents a String object, every variable must be inside '${'
	 * and '}' so the variable CAR must be in this statement-string as '${CAR}'.
	 * Do not provide this parameter to make no changes in the statement loaded.
	 * <br>
	 * <br>
	 * </li>
	 * <li><b><code>view-name</code></b>: Name of the view of the Data Store
	 * where to execute the update statement. This parameter is optional. When
	 * provided, it must be a <code>java.lang.String</code>. If it is not
	 * provided then this operation will execute the query in the Current View
	 * of the Data Store.</li>
	 * </ul>
	 * This operation does not return anything.
	 */
	public static final String OPERATION_NAME_UPDATE_BY_NAME = "query-by-name";

	/**
	 * Makes in a Data Store all changes made since the previous commit
	 * permanent. Use the following parameters in order to invoke this
	 * operation:<br>
	 * <br>
	 * <ul>
	 * <li><b><code>client-name</code></b>: Name of the Data Store where to
	 * perform the commit operation. This parameter must be a
	 * <code>java.lang.String</code>.</li>
	 * </ul>
	 * This operation does not return anything.
	 */
	public static final String OPERATION_NAME_COMMIT = "commit";

	/**
	 * Begins a transaction in a Database Management System. Use the following
	 * parameters in order to invoke this operation:<br>
	 * <br>
	 * <ul>
	 * <li><b><code>client-name</code></b>: Name of the Data Store where to
	 * perform the operation. This parameter must be a
	 * <code>java.lang.String</code>.<br>
	 * <br>
	 * </li>
	 * <li><b><code>view-name</code></b>: Name of the DBMS view of the Data
	 * Store where to perform the operation. This parameter must be a
	 * <code>java.lang.String</code>.</li>
	 * </ul>
	 * This operation does not return anything.
	 */
	public static final String OPERATION_NAME_DBMS_BEGIN_TRANSACTION = "dbms-begin-transaction";

	/**
	 * Rolls back changes in a Database Management System. Use the following
	 * parameters in order to invoke this operation:<br>
	 * <br>
	 * <ul>
	 * <li><b><code>client-name</code></b>: Name of the Data Store where to
	 * perform the operation. This parameter must be a
	 * <code>java.lang.String</code>.<br>
	 * <br>
	 * </li>
	 * <li><b><code>view-name</code></b>: Name of the DBMS view of the Data
	 * Store where to perform the operation. This parameter must be a
	 * <code>java.lang.String</code>.</li>
	 * </ul>
	 * This operation does not return anything.
	 */
	public static final String OPERATION_NAME_DBMS_ROLLBACK = "dbms-rollback";

	/**
	 * Executes in a Relational Database Management System (RDBMS) View an
	 * statement that retrieves information from the Data Store. Use the
	 * following parameters in order to invoke this operation:<br>
	 * <br>
	 * <ul>
	 * <li><b><code>client-name</code></b>: The name to which the Data Store is
	 * bound in the Service. This parameter must be a
	 * <code>java.lang.String</code>.<br>
	 * <br>
	 * </li>
	 * <li><b><code>statement</code></b>: SQL statement, typically a static SQL
	 * <code>SELECT</code> statement, to retrieve data from the relational
	 * database. This parameter is mandatory (a <code>java.lang.String</code>).<br>
	 * <br>
	 * </li>
	 * <li><b><code>values</code></b>: Variables to replace in the statement.
	 * This parameter is optional and when provided it must be a map where the
	 * keys represent variable names in the query-string and the values those
	 * that will replace the variables. Every variable must be inside '${' and
	 * '}' so the variable CAR must be in this query-string as '${CAR}'. Do not
	 * provide this parameter to make no changes in the statement.<br>
	 * <br>
	 * </li>
	 * <li><b><code>view-name</code></b>: Name of the RDBMS view of the Data
	 * Store where to execute the query. This parameter must be a
	 * <code>java.lang.String</code>.<br>
	 * <br>
	 * </li>
	 * <li><b><code>page</code></b>: Page to get from the result. When you
	 * specify the number of rows (check out <code>page-size</code> parameter of
	 * this operation) that you want in the result of a query, Warework
	 * automatically calculates the number of pages that hold this number of
	 * rows. You have to pass an integer value greater than zero to retrieve a
	 * specific page from the result. This parameter is optional (a
	 * <code>java.lang.Integer</code>). Do not provide this parameter to
	 * retrieve every result in one page.<br>
	 * <br>
	 * </li>
	 * <li><b><code>page-size</code></b>: Number of rows that you want in the
	 * result of the database. This parameter is optional (a
	 * <code>java.lang.Integer</code>). Do not provide this parameter to
	 * retrieve every row.</li>
	 * </ul>
	 * This operation returns a new <code>java.lang.Object</code> that holds the
	 * result of the query.
	 */
	public static final String OPERATION_NAME_RDBMS_QUERY = "rdbms-query";

	/**
	 * Executes in a Relational Database Management System (RDBMS) View an
	 * statement from a Provider that retrieves information from the Data Store.
	 * Use the following parameters in order to invoke this operation:<br>
	 * <br>
	 * <ul>
	 * <li><b><code>client-name</code></b>: The name to which the Data Store is
	 * bound in the Service. This parameter must be a
	 * <code>java.lang.String</code>.<br>
	 * <br>
	 * </li>
	 * <li><b><code>provider-name</code></b>: Name of the Provider where to
	 * retrieve the SQL statement. This parameter must be a
	 * <code>java.lang.String</code>.<br>
	 * <br>
	 * </li>
	 * <li><b><code>statement-name</code></b>: Name of the query to retrieve
	 * from the Provider. This parameter must be a <code>java.lang.String</code>
	 * .<br>
	 * <br>
	 * </li>
	 * <li><b><code>values</code></b>: Variables to replace in the statement
	 * loaded from the Provider. This parameter is optional and when provided it
	 * must be a map where the keys represent variable names in the query-string
	 * loaded from the Provider and the values those that will replace the
	 * variables. Every variable must be inside '${' and '}' so the variable CAR
	 * must be in this query-string as '${CAR}'. Do not provide this parameter
	 * to make no changes in the statement loaded.<br>
	 * <br>
	 * </li>
	 * <li><b><code>view-name</code></b>: Name of the RDBMS view of the Data
	 * Store where to execute the query. This parameter must be a
	 * <code>java.lang.String</code>.<br>
	 * <br>
	 * </li>
	 * <li><b><code>page</code></b>: Page to get from the result. When you
	 * specify the number of rows (check out <code>page-size</code> parameter of
	 * this operation) that you want in the result of a query, Warework
	 * automatically calculates the number of pages that hold this number of
	 * rows. You have to pass an integer value greater than zero to retrieve a
	 * specific page from the result. This parameter is optional (a
	 * <code>java.lang.Integer</code>). Do not provide this parameter to
	 * retrieve every result in one page.<br>
	 * <br>
	 * </li>
	 * <li><b><code>page-size</code></b>: Number of rows that you want in the
	 * result of the database. This parameter is optional (a
	 * <code>java.lang.Integer</code>). Do not provide this parameter to
	 * retrieve every row.</li>
	 * </ul>
	 * This operation returns a new <code>java.lang.Object</code> that holds the
	 * result of the query.
	 */
	public static final String OPERATION_NAME_RDBMS_QUERY_BY_NAME = "rdbms-query-by-name";

	/**
	 * Executes in a Relational Database Management System (RDBMS) View a set of
	 * SQL update statements. Use the following parameters in order to invoke
	 * this operation:<br>
	 * <br>
	 * <ul>
	 * <li><b><code>client-name</code></b>: The name to which the Data Store is
	 * bound in the Service. This parameter must be a
	 * <code>java.lang.String</code>.<br>
	 * <br>
	 * </li>
	 * <li><b><code>statement</code></b>: SQL statements, which may be an
	 * <code>INSERT</code>, <code>UPDATE</code>, or <code>DELETE</code>
	 * statements or SQL statements that returns nothing, such as an SQL DDL
	 * script (for example: with <code>CREATE TABLE</code> statements). This
	 * parameter is mandatory (a <code>java.lang.String</code>).<br>
	 * <br>
	 * </li>
	 * <li><b><code>values</code></b>: Variables to replace in the statement
	 * loaded. This parameter is optional and when provided it must be a map
	 * where the keys represent variable names in the statement and the values
	 * those that will replace the variables. Every variable must be inside '${'
	 * and '}' so the variable CAR must be in this statement as '${CAR}'. Do not
	 * provide this parameter to make no changes in the statement.<br>
	 * <br>
	 * </li>
	 * <li><b><code>view-name</code></b>: Name of the RDBMS view of the Data
	 * Store where to execute the statements. This parameter must be a
	 * <code>java.lang.String</code>.<br>
	 * <br>
	 * </li>
	 * <li><b><code>delimiter</code></b>: Character that separates each
	 * statement (for example: ';'). This parameter is optional (a
	 * <code>java.lang.Integer</code>). Do not provide this parameter to
	 * indicate that just one statement is passed to the database.</li>
	 * </ul>
	 * This operation does not return anything.
	 */
	public static final String OPERATION_NAME_RDBMS_UPDATE = "rdbms-update";

	/**
	 * Executes in a Relational Database Management System (RDBMS) View a set of
	 * SQL update statements loaded from a Provided. Use the following
	 * parameters in order to invoke this operation:<br>
	 * <br>
	 * <ul>
	 * <li><b><code>client-name</code></b>: The name to which the Data Store is
	 * bound in the Service. This parameter must be a
	 * <code>java.lang.String</code>.<br>
	 * <br>
	 * </li>
	 * <li><b><code>provider-name</code></b>: Name of the Provider where to
	 * retrieve the SQL statement. This parameter must be a
	 * <code>java.lang.String</code>.<br>
	 * <br>
	 * </li>
	 * <li><b><code>statement-name</code></b>: Name of the SQL statements to
	 * retrieve from the Provider, which may be an <code>INSERT</code>,
	 * <code>UPDATE</code>, or <code>DELETE</code> statements or SQL statements
	 * that returns nothing, such as an SQL DDL script (for example: with
	 * <code>CREATE TABLE</code> statements). This parameter must be a
	 * <code>java.lang.String</code> .<br>
	 * <br>
	 * </li>
	 * <li><b><code>values</code></b>: Variables to replace in the statement
	 * loaded from the Provider. This parameter is optional and when provided it
	 * must be a map where the keys represent variable names in the statement
	 * loaded from the Provider and the values those that will replace the
	 * variables. Every variable must be inside '${' and '}' so the variable CAR
	 * must be in this statement as '${CAR}'. Do not provide this parameter to
	 * make no changes in the statement loaded.<br>
	 * <br>
	 * </li>
	 * <li><b><code>view-name</code></b>: Name of the RDBMS view of the Data
	 * Store where to execute the update statement. This parameter must be a
	 * <code>java.lang.String</code>.<br>
	 * <br>
	 * </li>
	 * <li><b><code>delimiter</code></b>: Character that separates each
	 * statement (for example: ';'). This parameter is optional (a
	 * <code>java.lang.Integer</code>). Do not provide this parameter to
	 * indicate that just one statement is passed to the database.</li>
	 * </ul>
	 * This operation does not return anything.
	 */
	public static final String OPERATION_NAME_RDBMS_UPDATE_BY_NAME = "rdbms-udate-by-name";

	/**
	 * Maps a specified key to a specified value in a Key-Value view. Use the
	 * following parameters in order to invoke this operation:<br>
	 * <br>
	 * <ul>
	 * <li><b><code>client-name</code></b>: The name to which the Data Store is
	 * bound in the Service. This parameter must be a
	 * <code>java.lang.String</code>.<br>
	 * <br>
	 * </li>
	 * <li><b><code>key</code></b>: Key for the value. This parameter cannot be
	 * <code>null</code>.<br>
	 * <br>
	 * </li>
	 * <li><b><code>value</code></b>: Value to set. This parameter cannot be
	 * <code>null</code>.<br>
	 * <br>
	 * </li>
	 * <li><b><code>view-name</code></b>: Name of the Key-Value view in the Data
	 * Store where to execute the operation. This parameter must be a
	 * <code>java.lang.String</code>.</li>
	 * </ul>
	 * This operation does not return anything.
	 */
	public static final String OPERATION_NAME_KEY_VALUE_PUT = "key-value-put";

	/**
	 * Gets the value to which the specified key is mapped in a Key-Value view.
	 * Use the following parameters in order to invoke this operation:<br>
	 * <br>
	 * <ul>
	 * <li><b><code>client-name</code></b>: The name to which the Data Store is
	 * bound in the Service. This parameter must be a
	 * <code>java.lang.String</code>.<br>
	 * <br>
	 * </li>
	 * <li><b><code>key</code></b>: Key for the value. This parameter cannot be
	 * <code>null</code>.<br>
	 * <br>
	 * </li>
	 * <li><b><code>view-name</code></b>: Name of the Key-Value view in the Data
	 * Store where to execute the operation. This parameter must be a
	 * <code>java.lang.String</code>.</li>
	 * </ul>
	 * This operation returns the value to which the specified key is mapped in
	 * the Data Store.
	 */
	public static final String OPERATION_NAME_KEY_VALUE_GET = "key-value-get";

	/**
	 * Removes the key (and its corresponding value) from a Key-Value view. Use
	 * the following parameters in order to invoke this operation:<br>
	 * <br>
	 * <ul>
	 * <li><b><code>client-name</code></b>: The name to which the Data Store is
	 * bound in the Service. This parameter must be a
	 * <code>java.lang.String</code>.<br>
	 * <br>
	 * </li>
	 * <li><b><code>key</code></b>: Key for the value. This parameter cannot be
	 * <code>null</code>.<br>
	 * <br>
	 * </li>
	 * <li><b><code>view-name</code></b>: Name of the Key-Value view in the Data
	 * Store where to execute the operation. This parameter must be a
	 * <code>java.lang.String</code>.</li>
	 * </ul>
	 * This operation does not return anything.
	 */
	public static final String OPERATION_NAME_KEY_VALUE_REMOVE = "key-value-remove";

	/**
	 * Gets a list with the keys in a Key-Value view. Use the following
	 * parameters in order to invoke this operation:<br>
	 * <br>
	 * <ul>
	 * <li><b><code>client-name</code></b>: The name to which the Data Store is
	 * bound in the Service. This parameter must be a
	 * <code>java.lang.String</code>.<br>
	 * <br>
	 * </li>
	 * <li><b><code>view-name</code></b>: Name of the Key-Value view in the Data
	 * Store where to execute the operation. This parameter must be a
	 * <code>java.lang.String</code>.</li>
	 * </ul>
	 * This operation returns a list with the keys of the Data Store.
	 */
	public static final String OPERATION_NAME_KEY_VALUE_KEYS = "key-value-keys";

	/**
	 * Counts the number of keys in a Key-Value view. Use the following
	 * parameters in order to invoke this operation:<br>
	 * <br>
	 * <ul>
	 * <li><b><code>client-name</code></b>: The name to which the Data Store is
	 * bound in the Service. This parameter must be a
	 * <code>java.lang.String</code>.<br>
	 * <br>
	 * </li>
	 * <li><b><code>view-name</code></b>: Name of the Key-Value view in the Data
	 * Store where to execute the operation. This parameter must be a
	 * <code>java.lang.String</code>.</li>
	 * </ul>
	 * This operation returns the number of keys in a Key-Value Data Store.
	 */
	public static final String OPERATION_NAME_KEY_VALUE_SIZE = "key-value-size";

	// OPERATION PARAMETERS

	/**
	 * Operation parameter that specifies the statement (in a specific query
	 * language) to execute in the Data Store.
	 */
	public static final String OPERATION_PARAMETER_STATEMENT = "statement";

	/**
	 * Operation parameter that specifies the name of the view of the Data Store
	 * where to execute the query.
	 */
	public static final String OPERATION_PARAMETER_VIEW_NAME = "view-name";

	/**
	 * Operation parameter that specifies the name of the Provider where to
	 * retrieve a statement.
	 */
	public static final String OPERATION_PARAMETER_PROVIDER_NAME = "provider-name";

	/**
	 * Operation parameter that specifies the name of the statement to retrieve
	 * from the Provider.
	 */
	public static final String OPERATION_PARAMETER_STATEMENT_NAME = "statement-name";

	/**
	 * Operation parameter that specifies the variables to replace in the
	 * statement. It must be a map where the keys are the names of the variables
	 * in the statement and the values those that will replace the variables.
	 * Each Data Store may process this values in a specific way (not every Data
	 * Store support the replacement of variables). When the statement loaded
	 * from the Provider represents a String object, every variable must be
	 * inside '${' and '}' so the variable CAR must be in this statement-string
	 * as '${CAR}'.
	 */
	public static final String OPERATION_PARAMETER_VALUES = "values";

	/**
	 * Operation parameter that specifies the page to get from the result. When
	 * you specify the number of items (check out <code>page-size</code>
	 * operation parameter) that you want in the result of a query, Warework
	 * automatically calculates the number of pages that hold this number of
	 * items.
	 */
	public static final String OPERATION_PARAMETER_PAGE = "page";

	/**
	 * Operation parameter that specifies the number of items that you want in
	 * the result of the database.
	 */
	public static final String OPERATION_PARAMETER_PAGE_SIZE = "page-size";

	/**
	 * Operation parameter that specifies the character that separates each
	 * statement (for example: ';').
	 */
	public static final String OPERATION_PARAMETER_DELIMITER = "delimiter";

	/**
	 * Operation parameter that specifies the key for a value in a Key-Value
	 * Data Store.
	 */
	public static final String OPERATION_PARAMETER_KEY = "key";

	/**
	 * Operation parameter that specifies the value to set in a Key-Value Data
	 * Store.
	 */
	public static final String OPERATION_PARAMETER_VALUE = "value";

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * This is the default constructor and does not perform any operation.
	 */
	protected DatastoreServiceConstants() {
	}

}
