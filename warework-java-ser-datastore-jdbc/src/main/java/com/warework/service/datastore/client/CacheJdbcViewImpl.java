package com.warework.service.datastore.client;

import java.util.HashMap;
import java.util.Map;

import com.warework.core.service.client.ClientException;
import com.warework.core.util.CommonValueL1Constants;
import com.warework.core.util.helper.StringL1Helper;
import com.warework.service.datastore.view.CacheView;
import com.warework.service.log.LogServiceConstants;

/**
 * <u>Cache View for JDBC Data Stores</u><br>
 * <br>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public final class CacheJdbcViewImpl extends KeyValueJdbcViewImpl implements CacheView {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// PARAMETERS

	/**
	 * Initialization parameter that specifies a string with the name of the column
	 * (this column must exist in the table specified with
	 * <code>PARAMETER_TABLE_NAME</code>) that represents the timestamp in
	 * milliseconds when a spefic key will expire. This column in the table must be
	 * a numeric type of length 15. This parameter is mandatory.
	 */
	public static final String PARAMETER_TABLE_EXPIRE_FIELD = "table-expire-field";

	/**
	 * Mandatory initialization parameter that specifies a string with the SQL
	 * expression that calculates the current timestamp in millisecond. For example,
	 * in most PostgreSQL distributions you can get the current timestamp in
	 * milliseconds with
	 * '<code>extract(epoch from current_timestamp) * 1000</code>'. Examples values
	 * for this parameter could be:<br>
	 * <br>
	 * <ul>
	 * <li><b>H2</b>:
	 * <code>extract(epoch from current_timestamp()) * 1000</code></li>
	 * <li><b>PostgreSQL</b>:
	 * <code>extract(epoch from current_timestamp) * 1000</code></li>
	 * <li><b>SQLite</b>: <code>strftime('%s', 'now') * 1000</code></li>
	 * </ul>
	 */
	public static final String PARAMETER_CURRENT_TIMESTAMP_MILLISECONDS = "current-timestamp-milliseconds";

	// DEFAULT VALUES

	/**
	 * Default value to specify that a key has no expiration time.
	 */
	public static final long DEFAULT_VALUE_EXPIRE_DISABLED = -1;

	// VARIABLE REPLACEMENT

	/**
	 * Name of the <code>EXPIRE</code> variable to be replaced in the SQL statement.
	 */
	private static final String VARIABLE_EXPIRE = "EXPIRE";

	// ///////////////////////////////////////////////////////////////////
	// ATTRIBUTES
	// ///////////////////////////////////////////////////////////////////

	// PARAMETER VALUES

	// Column for expire field.
	private String tableExpireField;

	// Value of the current timestamp parameter.
	private String currentTimestamp;

	// STATEMENTS

	private String deleteExpired;

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Maps the specified key to the specified value in the JDBC Data Store. Neither
	 * the key nor the value can be <code>null</code>.
	 * 
	 * @param key   Key for the value.<br>
	 *              <br>
	 * @param value Value to set.<br>
	 *              <br>
	 * @throws ClientException If there is an error when trying to store the value
	 *                         in the Data Store.<br>
	 *                         <br>
	 */
	public void put(final Object key, final Object value) throws ClientException {
		put(key, value, DEFAULT_VALUE_EXPIRE_DISABLED);
	}

	/**
	 * Maps the specified key to the specified value in the Data Store. Neither the
	 * key nor the value can be <code>null</code>.
	 * 
	 * @param key    Key for the value.<br>
	 *               <br>
	 * @param value  Value to set.<br>
	 *               <br>
	 * @param expire Timeout in milliseconds for the specified key. After the
	 *               timeout the key will return <code>null</code>.<br>
	 *               <br>
	 * @throws ClientException If there is an error when trying to store the value
	 *                         in the Data Store.<br>
	 *                         <br>
	 */
	public void put(final Object key, final Object value, final long expire) throws ClientException {
		if (isConnected()) {

			// Validate that key and value are not null.
			if ((key == null) || (key.equals(CommonValueL1Constants.STRING_EMPTY)) || (value == null)) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot add or update a key in Data Store '" + getName() + "' at Service '"
								+ getService().getName() + "' because the key and/or the value are null or empty.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Define variables.
			final Map<String, Object> values = new HashMap<String, Object>();

			// Set common variables.
			values.put(VARIABLE_KEY, key);
			values.put(VARIABLE_VALUE, value);

			// Set expire variable.
			if (expire > DEFAULT_VALUE_EXPIRE_DISABLED) {
				values.put(VARIABLE_EXPIRE, (expire + System.currentTimeMillis()));
			} else {
				values.put(VARIABLE_EXPIRE, DEFAULT_VALUE_EXPIRE_DISABLED);
			}

			// Delete all expired keys.
			executeDeleteExpired(key);

			// Execute the statement.
			executeUpdate(key, values);

		} else {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot add or update a key in Data Store " + getName() + "' at Service '"
							+ getService().getName() + "' because Data Store connection is closed.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	/**
	 * Removes the key (and its corresponding value) from the JDBC Data Store.
	 * 
	 * @param key Data Store key.<br>
	 *            <br>
	 * @throws ClientException If there is an error when trying to remove the value
	 *                         in the Data Store.<br>
	 *                         <br>
	 */
	public void remove(final Object key) throws ClientException {
		if (isConnected()) {

			// Validate that key is not null.
			if ((key == null) || (key.equals(CommonValueL1Constants.STRING_EMPTY))) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot remove a key in Data Store '" + getName() + "' at Service '"
								+ getService().getName() + "' because the key is null or empty.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Execute the statement.
			executeDelete(key);

			// Delete all expired keys.
			executeDeleteExpired(key);

		} else {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot delete a key in Data Store " + getName() + "' at Service '"
							+ getService().getName() + "' because Data Store connection is closed.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Initialization of the View.
	 * 
	 * @throws ClientException If there is an error when trying to initialize the
	 *                         View.<br>
	 *                         <br>
	 */
	protected void initialize() throws ClientException {

		// Invoke parent.
		super.initialize();

		// Initialize the name of the expire field.
		initTableFieldExpire();

		// Initialize current timestamp parameter.
		initCurrentTimestamp();

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates the statement to query the table that represents the Key-Value Data
	 * Store for a specific key.
	 * 
	 * @return SQL <code>SELECT</code> statement.<br>
	 *         <br>
	 */
	protected String createSelectSingle() {

		// SQL statement.
		final StringBuffer sql = new StringBuffer();

		// Create the SQL statement.
		sql.append(JdbcDatastore.KEYWORD_SELECT);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(tableValueField);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(JdbcDatastore.KEYWORD_FROM);
		sql.append(StringL1Helper.CHARACTER_SPACE);

		// Add schema if required.
		if (schema != null) {
			sql.append(schema);
			sql.append(StringL1Helper.CHARACTER_PERIOD);
		}

		// Create the SQL statement.
		sql.append(tableName);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(JdbcDatastore.KEYWORD_WHERE);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(tableKeyField);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(StringL1Helper.CHARACTER_EQUALS);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(CommonValueL1Constants.STRING_DOLLAR_LEFT_CURLY_BRACKET);
		sql.append(VARIABLE_KEY);
		sql.append(StringL1Helper.CHARACTER_RIGHT_CURLY_BRACKET);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(JdbcDatastore.KEYWORD_AND);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(StringL1Helper.CHARACTER_LEFT_PARENTHESES);
		sql.append(StringL1Helper.CHARACTER_LEFT_PARENTHESES);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(tableExpireField);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(JdbcDatastore.KEYWORD_IS);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(JdbcDatastore.KEYWORD_NULL);
		sql.append(StringL1Helper.CHARACTER_RIGHT_PARENTHESES);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(JdbcDatastore.KEYWORD_OR);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(StringL1Helper.CHARACTER_LEFT_PARENTHESES);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(tableExpireField);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(StringL1Helper.CHARACTER_EQUALS);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(DEFAULT_VALUE_EXPIRE_DISABLED);
		sql.append(StringL1Helper.CHARACTER_RIGHT_PARENTHESES);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(JdbcDatastore.KEYWORD_OR);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(StringL1Helper.CHARACTER_LEFT_PARENTHESES);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(tableExpireField);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(StringL1Helper.CHARACTER_GREATER_THAN);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(StringL1Helper.CHARACTER_LEFT_PARENTHESES);
		sql.append(currentTimestamp);
		sql.append(StringL1Helper.CHARACTER_RIGHT_PARENTHESES);
		sql.append(StringL1Helper.CHARACTER_RIGHT_PARENTHESES);
		sql.append(StringL1Helper.CHARACTER_RIGHT_PARENTHESES);

		// Cache this statement.
		return sql.toString();

	}

	/**
	 * Creates the SQL statement to validate that a single value exists in the table
	 * that represents the Key-Value Data Store.
	 * 
	 * @return SQL <code>SELECT</code> statement.<br>
	 *         <br>
	 */
	protected String createSelectExists() {
		return super.createSelectSingle();
	}

	/**
	 * Creates the SQL statement to select all keys in the table that represents the
	 * Key-Value Data Store.
	 * 
	 * @return SQL <code>SELECT</code> statement.<br>
	 *         <br>
	 */
	protected String createSelectAll() {

		// SQL statement.
		final StringBuffer sql = new StringBuffer();

		// Create SQL statement.
		sql.append(JdbcDatastore.KEYWORD_SELECT);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(JdbcDatastore.KEYWORD_DISTINCT);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(tableKeyField);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(JdbcDatastore.KEYWORD_FROM);
		sql.append(StringL1Helper.CHARACTER_SPACE);

		// Add schema if required.
		if (schema != null) {
			sql.append(schema);
			sql.append(StringL1Helper.CHARACTER_PERIOD);
		}

		// Create SQL statement.
		sql.append(tableName);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(JdbcDatastore.KEYWORD_WHERE);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(StringL1Helper.CHARACTER_LEFT_PARENTHESES);
		sql.append(StringL1Helper.CHARACTER_LEFT_PARENTHESES);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(tableExpireField);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(JdbcDatastore.KEYWORD_IS);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(JdbcDatastore.KEYWORD_NULL);
		sql.append(StringL1Helper.CHARACTER_RIGHT_PARENTHESES);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(JdbcDatastore.KEYWORD_OR);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(StringL1Helper.CHARACTER_LEFT_PARENTHESES);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(tableExpireField);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(StringL1Helper.CHARACTER_EQUALS);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(DEFAULT_VALUE_EXPIRE_DISABLED);
		sql.append(StringL1Helper.CHARACTER_RIGHT_PARENTHESES);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(JdbcDatastore.KEYWORD_OR);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(StringL1Helper.CHARACTER_LEFT_PARENTHESES);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(tableExpireField);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(StringL1Helper.CHARACTER_GREATER_THAN);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(StringL1Helper.CHARACTER_LEFT_PARENTHESES);
		sql.append(currentTimestamp);
		sql.append(StringL1Helper.CHARACTER_RIGHT_PARENTHESES);
		sql.append(StringL1Helper.CHARACTER_RIGHT_PARENTHESES);
		sql.append(StringL1Helper.CHARACTER_RIGHT_PARENTHESES);

		// Return statement.
		return sql.toString();

	}

	/**
	 * Creates the SQL statement to count all available keys in the table that
	 * represents the Key-Value Data Store.
	 * 
	 * @return SQL <code>SELECT</code> statement.<br>
	 *         <br>
	 */
	protected String createSelectCount() {

		// SQL statement.
		final StringBuffer sql = new StringBuffer();

		// Create SQL statement.
		sql.append(JdbcDatastore.KEYWORD_SELECT);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(JdbcDatastore.KEYWORD_COUNT);
		sql.append(StringL1Helper.CHARACTER_LEFT_PARENTHESES);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(tableValueField);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(StringL1Helper.CHARACTER_RIGHT_PARENTHESES);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(JdbcDatastore.KEYWORD_FROM);
		sql.append(StringL1Helper.CHARACTER_SPACE);

		// Add schema if required.
		if (schema != null) {
			sql.append(schema);
			sql.append(StringL1Helper.CHARACTER_PERIOD);
		}

		// Create SQL statement.
		sql.append(tableName);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(JdbcDatastore.KEYWORD_WHERE);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(StringL1Helper.CHARACTER_LEFT_PARENTHESES);
		sql.append(StringL1Helper.CHARACTER_LEFT_PARENTHESES);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(tableExpireField);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(JdbcDatastore.KEYWORD_IS);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(JdbcDatastore.KEYWORD_NULL);
		sql.append(StringL1Helper.CHARACTER_RIGHT_PARENTHESES);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(JdbcDatastore.KEYWORD_OR);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(StringL1Helper.CHARACTER_LEFT_PARENTHESES);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(tableExpireField);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(StringL1Helper.CHARACTER_EQUALS);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(DEFAULT_VALUE_EXPIRE_DISABLED);
		sql.append(StringL1Helper.CHARACTER_RIGHT_PARENTHESES);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(JdbcDatastore.KEYWORD_OR);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(StringL1Helper.CHARACTER_LEFT_PARENTHESES);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(tableExpireField);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(StringL1Helper.CHARACTER_GREATER_THAN);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(StringL1Helper.CHARACTER_LEFT_PARENTHESES);
		sql.append(currentTimestamp);
		sql.append(StringL1Helper.CHARACTER_RIGHT_PARENTHESES);
		sql.append(StringL1Helper.CHARACTER_RIGHT_PARENTHESES);
		sql.append(StringL1Helper.CHARACTER_RIGHT_PARENTHESES);

		// Return statement.
		return sql.toString();

	}

	/**
	 * Create the UPDATE statement.
	 * 
	 * @return SQL update statement.<br>
	 *         <br>
	 */
	protected String createUpdate() {

		// SQL statement.
		final StringBuffer sql = new StringBuffer();

		// Create SQL statement.
		sql.append(JdbcDatastore.KEYWORD_UPDATE);
		sql.append(StringL1Helper.CHARACTER_SPACE);

		// Add schema if required.
		if (schema != null) {
			sql.append(schema);
			sql.append(StringL1Helper.CHARACTER_PERIOD);
		}

		// Create SQL statement.
		sql.append(tableName);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(JdbcDatastore.KEYWORD_SET);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(tableValueField);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(StringL1Helper.CHARACTER_EQUALS);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(CommonValueL1Constants.STRING_DOLLAR_LEFT_CURLY_BRACKET);
		sql.append(VARIABLE_VALUE);
		sql.append(StringL1Helper.CHARACTER_RIGHT_CURLY_BRACKET);
		sql.append(StringL1Helper.CHARACTER_COMMA);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(tableExpireField);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(StringL1Helper.CHARACTER_EQUALS);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(CommonValueL1Constants.STRING_DOLLAR_LEFT_CURLY_BRACKET);
		sql.append(VARIABLE_EXPIRE);
		sql.append(StringL1Helper.CHARACTER_RIGHT_CURLY_BRACKET);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(JdbcDatastore.KEYWORD_WHERE);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(tableKeyField);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(StringL1Helper.CHARACTER_EQUALS);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(CommonValueL1Constants.STRING_DOLLAR_LEFT_CURLY_BRACKET);
		sql.append(VARIABLE_KEY);
		sql.append(StringL1Helper.CHARACTER_RIGHT_CURLY_BRACKET);

		// Return the update statement.
		return sql.toString();

	}

	/**
	 * Create the INSERT statement.
	 * 
	 * @return SQL insert statement.<br>
	 *         <br>
	 */
	protected String createInsert() {

		// SQL statement.
		final StringBuffer sql = new StringBuffer();

		// Create SQL statement.
		sql.append(JdbcDatastore.KEYWORD_INSERT);
		sql.append(StringL1Helper.CHARACTER_SPACE);

		// Add schema if required.
		if (schema != null) {
			sql.append(schema);
			sql.append(StringL1Helper.CHARACTER_PERIOD);
		}

		// Create SQL statement.
		sql.append(tableName);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(StringL1Helper.CHARACTER_LEFT_PARENTHESES);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(tableKeyField);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(StringL1Helper.CHARACTER_COMMA);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(tableValueField);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(StringL1Helper.CHARACTER_COMMA);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(tableExpireField);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(StringL1Helper.CHARACTER_RIGHT_PARENTHESES);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(JdbcDatastore.KEYWORD_VALUES);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(StringL1Helper.CHARACTER_LEFT_PARENTHESES);
		sql.append(CommonValueL1Constants.STRING_DOLLAR_LEFT_CURLY_BRACKET);
		sql.append(VARIABLE_KEY);
		sql.append(StringL1Helper.CHARACTER_RIGHT_CURLY_BRACKET);
		sql.append(StringL1Helper.CHARACTER_COMMA);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(CommonValueL1Constants.STRING_DOLLAR_LEFT_CURLY_BRACKET);
		sql.append(VARIABLE_VALUE);
		sql.append(StringL1Helper.CHARACTER_RIGHT_CURLY_BRACKET);
		sql.append(StringL1Helper.CHARACTER_COMMA);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(CommonValueL1Constants.STRING_DOLLAR_LEFT_CURLY_BRACKET);
		sql.append(VARIABLE_EXPIRE);
		sql.append(StringL1Helper.CHARACTER_RIGHT_CURLY_BRACKET);
		sql.append(StringL1Helper.CHARACTER_RIGHT_PARENTHESES);

		// Return the insert statement.
		return sql.toString();

	}

	/**
	 * Creates the DELETE statement to remove expired keys.
	 * 
	 * @return SQL insert statement.<br>
	 *         <br>
	 */
	protected String createDeleteExpired() throws ClientException {

		// SQL statement.
		final StringBuffer sql = new StringBuffer();

		// Create SQL statement.
		sql.append(JdbcDatastore.KEYWORD_DELETE);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(JdbcDatastore.KEYWORD_FROM);
		sql.append(StringL1Helper.CHARACTER_SPACE);

		// Add schema if required.
		if (schema != null) {
			sql.append(schema);
			sql.append(StringL1Helper.CHARACTER_PERIOD);
		}

		// Create SQL statement.
		sql.append(tableName);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(JdbcDatastore.KEYWORD_WHERE);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(tableExpireField);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(StringL1Helper.CHARACTER_GREATER_THAN);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(DEFAULT_VALUE_EXPIRE_DISABLED);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(JdbcDatastore.KEYWORD_AND);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(tableExpireField);
		if (quoteFields) {
			sql.append(StringL1Helper.CHARACTER_DOUBLE_QUOTE);
		}
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(StringL1Helper.CHARACTER_LESS_THAN);
		sql.append(StringL1Helper.CHARACTER_SPACE);
		sql.append(StringL1Helper.CHARACTER_LEFT_PARENTHESES);
		sql.append(currentTimestamp);
		sql.append(StringL1Helper.CHARACTER_RIGHT_PARENTHESES);

		// Cache this statement.
		return sql.toString();

	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Initialize <code>PARAMETER_TABLE_EXPIRE_FIELD</code>.
	 * 
	 * @throws ClientException If there is an error when trying to validate the
	 *                         key.<br>
	 *                         <br>
	 */
	private void initTableFieldExpire() throws ClientException {

		// Get the value of the parameter.
		final Object parameterValue = getInitParameter(PARAMETER_TABLE_EXPIRE_FIELD);

		// Get the name of the key field.
		if ((parameterValue == null) || (!(parameterValue instanceof String))) {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot add View '" + getClass().getName() + "' in Data Store '" + getName()
							+ "' because given '" + PARAMETER_TABLE_EXPIRE_FIELD + "' parameter is null or not a "
							+ String.class.getName() + ".",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		} else {
			tableExpireField = (String) parameterValue;
		}

	}

	/**
	 * Initialize <code>PARAMETER_CURRENT_TIMESTAMP_MILLISECONDS</code>.
	 * 
	 * @throws ClientException If there is an error when trying to validate the
	 *                         Schema.<br>
	 *                         <br>
	 */
	private void initCurrentTimestamp() throws ClientException {

		// Get the value of the parameter.
		final Object parameterValue = getInitParameter(PARAMETER_CURRENT_TIMESTAMP_MILLISECONDS);

		// Validate schema.
		if ((parameterValue == null) || (!(parameterValue instanceof String))) {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot add View '" + getClass().getName() + "' in Data Store '" + getName()
							+ "' because parameter '" + PARAMETER_CURRENT_TIMESTAMP_MILLISECONDS
							+ "' is undefined or it's not a " + String.class.getName() + ".",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		} else {
			currentTimestamp = (String) parameterValue;
		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Removes all expired keys (and its corresponding value) from the JDBC Data
	 * Store.
	 * 
	 * @param key Data Store key.<br>
	 *            <br>
	 * @throws ClientException If there is an error when trying to remove the value
	 *                         in the Data Store.<br>
	 *                         <br>
	 */
	private void executeDeleteExpired(final Object key) throws ClientException {

		// Create an DELETE statement if required.
		if (deleteExpired == null) {
			deleteExpired = createDeleteExpired();
		}

		// Define variables.
		final Map<String, Object> values = new HashMap<String, Object>();

		// Set the variables.
		values.put(VARIABLE_KEY, key);

		// Execute the statement.
		getDatastore().update(deleteExpired, values);

	}

}
