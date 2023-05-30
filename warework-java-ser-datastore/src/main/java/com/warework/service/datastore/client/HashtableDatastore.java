package com.warework.service.datastore.client;

import java.util.Map;

import com.warework.core.service.client.ClientException;
import com.warework.core.util.helper.StringL1Helper;
import com.warework.service.log.LogServiceConstants;

/**
 * Data Store that performs operations with a Hashtable.<br>
 * 
 * @author Jose Schiaffino
 * @version 2.0.1
 */
public final class HashtableDatastore extends AbstractDatastore {

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * This operation is not supported in this Data Store.
	 */
	protected void performCommit() {
	}

	/**
	 * Closes the connection with the Data Store.
	 */
	protected void close() {
	}

	/**
	 * Validates if the connection is closed.
	 * 
	 * @return <code>false</code>.<br>
	 * <br>
	 */
	protected boolean isClosed() {
		return false;
	}

	/**
	 * Gets the value to which the specified key is mapped in the Hashtable.
	 * 
	 * @param key
	 *            Hashtable key.<br>
	 * <br>
	 * @param values
	 *            [1]: If the <code>key</code> (first argument of this method)
	 *            is a String object, this <code>values</code> argument may
	 *            hold: (A) keys that represent variable names in the
	 *            <code>key</code> argument and (B) values to replace in the
	 *            variables defined for the <code>key</code> argument. For
	 *            example: given <code>key</code> argument can be something like
	 *            'car.${CAR_ID}.color' so this <code>values</code> argument may
	 *            hold something like this {key=CAR_ID;value="john"}, so the
	 *            result <code>key</code> will be: 'car.john.color'. <br>
	 * <br>
	 *            [2]: If the <code>key</code> argument is not a String object
	 *            this <code>values</code> argument then may hold the real key
	 *            to use in the Hashtable Data Store. For example: if
	 *            <code>key</code> is 256, an Integer object, and
	 *            <code>values</code> argument holds something like
	 *            {key=256;value=32}, then the target <code>key</code> to query
	 *            the Hashtable Data Store will be 32.<br>
	 * <br>
	 *            [3]: Pass <code>null</code> to this parameter to make no
	 *            changes in the query.<br>
	 * <br>
	 * @return Object that holds the result of the query.<br>
	 * <br>
	 */
	@SuppressWarnings("unchecked")
	protected Object performQuery(Object key, Map<String, Object> values) {

		// Get the connection with the Data Store.
		Map<Object, Object> connection = (Map<Object, Object>) getConnection();

		// Get the value that matches the given key.
		Object result = null;
		if (key instanceof String) {

			// Get the query to run.
			String query = null;
			if ((values != null) && (values.size() > 0)) {
				query = StringL1Helper.replace((String) key, values);
			} else {
				query = (String) key;
			}

			// Execute the query.
			result = connection.get(query);

		} else if ((values != null) && (values.containsKey(key))) {
			result = connection.get(values.get(key));
		} else {
			result = connection.get(key);
		}

		// Log a message.
		getScopeFacade().log(
				"WAREWORK successfully executed the query in Data Store '"
						+ getName() + "' of Service '" + getService().getName()
						+ "'.", LogServiceConstants.LOG_LEVEL_INFO);

		// Return the result of the query.
		return result;

	}

	/**
	 * Maps the specified key to the specified value in the Data Store (adds a
	 * new property or updates an existing one). Neither the key nor the value
	 * can be <code>null</code>.
	 * 
	 * @param statement
	 *            A string like <code>property=value</code> (for example:
	 *            <code>user.name=John</code>) or an Object array like
	 *            <code>new Object[]{"age", new Integer(27)}</code>.<br>
	 * <br>
	 * @param values
	 *            [1]: If the <code>statement</code> (first argument of this
	 *            method) is a String object, this <code>values</code> argument
	 *            may hold: (A) keys that represent variable names in the
	 *            <code>statement</code> argument (key or value parts) and (B)
	 *            values to replace in the variables defined for the
	 *            <code>statement</code> argument. For example: given
	 *            <code>statement</code> argument can be something like
	 *            'car.${CAR_ID}.color=${COLOR}' so this <code>values</code>
	 *            argument may hold something like this
	 *            [{key=CAR_ID;value="john"}, {key=COLOR;value="red"}], so the
	 *            result <code>statement</code> will be: 'car.john.color=red'. <br>
	 * <br>
	 *            [2]: If the <code>statement</code> argument is an Object array
	 *            this <code>values</code> argument then may hold the real value
	 *            to use in the Hashtable Data Store. For example: if
	 *            <code>statement</code> is
	 *            <code>new Object[]{"age", new Integer(27)}</code> and
	 *            <code>values</code> argument holds something like
	 *            {key=age;value=32}, then the target <code>statement</code> to
	 *            update the Hashtable Data Store will be 32.<br>
	 * <br>
	 *            [3]: Pass <code>null</code> to this parameter to make no
	 *            changes in the update statement.<br>
	 * <br>
	 * @throws ClientException
	 *             If there is an error when trying to update the Data Store.<br>
	 * <br>
	 */
	@SuppressWarnings("unchecked")
	protected void performUpdate(Object statement, Map<String, Object> values)
			throws ClientException {

		// Get the connection with the Data Store.
		Map<Object, Object> connection = (Map<Object, Object>) getConnection();

		// Update the Hashtable.
		if (statement instanceof Object[]) {

			// Get the key and the value for the Hashatble.
			Object[] keyValue = (Object[]) statement;

			// Validate given statement and perform update.
			if (keyValue.length == 2) {

				// Get the key.
				Object key = keyValue[0];

				// Get the value.
				Object value = keyValue[1];

				// Execute update.
				if ((values != null) && (values.containsKey(key))) {
					connection.put(key, values.get(key));
				} else {
					connection.put(key, value);
				}

			} else {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot update given key-value Object array in Data Store '"
								+ getName() + "' at Service '"
								+ getService().getName()
								+ "' because it is not an array of size 2.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Log a message.
			getScopeFacade().log(
					"WAREWORK successfully updated Data Store '" + getName()
							+ "' of Service '" + getService().getName() + "'.",
					LogServiceConstants.LOG_LEVEL_INFO);

		} else if (statement instanceof String) {

			// Get the query to run.
			String update = null;
			if ((values != null) && (values.size() > 0)) {
				update = StringL1Helper.replace((String) statement, values);
			} else {
				update = (String) statement;
			}

			// Extract the key from the query.
			String key = StringL1Helper.getPropertyKey(update);

			// Extract the value from the query.
			String value = StringL1Helper.getPropertyValue(update);

			// Execute update.
			connection.put(key, value);

			// Log a message.
			getScopeFacade().log(
					"WAREWORK successfully updated Data Store '" + getName()
							+ "' of Service '" + getService().getName() + "'.",
					LogServiceConstants.LOG_LEVEL_INFO);

		} else {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot update given key-value pair in Data Store '"
							+ getName() + "' at Service '"
							+ getService().getName()
							+ "' because it is not a '"
							+ String.class.getName()
							+ "' class or an Object array.", null,
					LogServiceConstants.LOG_LEVEL_WARN);
		}

	}

}
