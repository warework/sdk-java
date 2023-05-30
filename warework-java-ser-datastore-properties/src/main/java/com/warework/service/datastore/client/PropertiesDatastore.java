package com.warework.service.datastore.client;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

import com.warework.core.service.client.ClientException;
import com.warework.core.util.CommonValueL1Constants;
import com.warework.core.util.helper.StringL1Helper;
import com.warework.service.datastore.client.connector.PropertiesConnector;
import com.warework.service.log.LogServiceConstants;

/**
 * Data Store that performs operations with a properties file.<br>
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public final class PropertiesDatastore extends AbstractDatastore {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Constant that defines the default name for the client.
	 */
	public static final String DEFAULT_CLIENT_NAME = "properties-datastore";

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the value to which the specified key is mapped in the properties file.
	 * 
	 * @param statement Properties file key. Must be a String object.<br>
	 *                  <br>
	 * @param values    Map where the keys represent variable names in the
	 *                  statement-string and the values (always as String objects)
	 *                  those that will replace the variables. Every variable must
	 *                  be inside '${' and '}' so the variable CAR_ID must be in
	 *                  this statement-string as '${CAR_ID}' (for example: given
	 *                  statement can represent a key like 'car.${CAR_ID}.color').
	 *                  Pass <code>null</code> to this parameter to make no changes
	 *                  in the query.<br>
	 *                  <br>
	 * @return Object that holds the result of the query.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to query the Data
	 *                         Store.<br>
	 *                         <br>
	 */
	protected Object performQuery(final Object statement, final Map<String, Object> values) throws ClientException {
		if (statement instanceof String) {

			// Get the query to run.
			String query = null;
			if ((values != null) && (values.size() > 0)) {
				query = StringL1Helper.replace((String) statement, values);
			} else {
				query = (String) statement;
			}

			// Get the connection.
			final Properties connection = (Properties) getConnection();

			// Execute the query.
			final String result = connection.getProperty(query);

			// Log a message.
			getScopeFacade().log("WAREWORK successfully executed the query in Data Store '" + getName() + "' of Service '"
					+ getService().getName() + "'.", LogServiceConstants.LOG_LEVEL_INFO);

			// Return the result of the query.
			return result;

		} else {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot execute the statement in Data Store '" + getName() + "' at Service '"
							+ getService().getName() + "' because given statement is not a '" + String.class.getName()
							+ "' class.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	/**
	 * Maps the specified key to the specified value in the Data Store (adds a new
	 * property or updates an existing one). Neither the key nor the value can be
	 * <code>null</code>.
	 * 
	 * @param statement A string like 'property=value'. For example:
	 *                  'user.name=John'.<br>
	 *                  <br>
	 * @param values    Map where the keys represent variable names in the
	 *                  statement-string and the values (always as String objects)
	 *                  those that will replace the variables. Every variable must
	 *                  be inside '${' and '}' so the variable CAR_ID must be in
	 *                  this statement-string as '${CAR_ID}' (for example: given
	 *                  statement can represent a key like 'car.id=${CAR_ID}'). Pass
	 *                  <code>null</code> to this parameter to make no changes in
	 *                  the statement.<br>
	 *                  <br>
	 * @throws ClientException If there is an error when trying to update the Data
	 *                         Store.<br>
	 *                         <br>
	 */
	protected void performUpdate(final Object statement, final Map<String, Object> values) throws ClientException {

		// Validate that statement is a string.
		if (!(statement instanceof String)) {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot execute the update statement in Data Store '" + getName() + "' at Service '"
							+ getService().getName() + "' because given statement is not a string.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Get the query to run.
		String update = null;
		if ((values != null) && (values.size() > 0)) {
			update = StringL1Helper.replace((String) statement, values);
		} else {
			update = (String) statement;
		}

		// Extract the key from the query.
		final String key = StringL1Helper.getPropertyKey(update);

		// Extract the value from the query.
		final String value = StringL1Helper.getPropertyValue(update);

		// Get the connection.
		final Properties connection = (Properties) getConnection();

		// Save the property.
		connection.put(key, value);

		// Log a message.
		getScopeFacade().log("WAREWORK successfully updated Data Store '" + getName() + "' of Service '"
				+ getService().getName() + "'.", LogServiceConstants.LOG_LEVEL_INFO);

	}

	/**
	 * Makes all changes made since the previous commit permanent.
	 * 
	 * @throws ClientException If there is an error when trying to commit the Data
	 *                         Store.<br>
	 *                         <br>
	 */
	protected void performCommit() throws ClientException {

		// Get the connection.
		final Properties connection = (Properties) getConnection();

		// Get the file that points to the properties file to create.
		final File target = createTarget();

		// Validate if the file is accesible.
		if (target != null) {

			// Persist changes.
			try {
				connection.store(new FileOutputStream(target), CommonValueL1Constants.STRING_EMPTY);
			} catch (final Exception e) {
				throw new ClientException(getScopeFacade(), "WAREWORK cannot perform commit on Data Store '" + getName()
						+ "' at Service '" + getService().getName()
						+ "' because the following exception is thrown when trying to save the properties file: "
						+ e.getMessage(), e, LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Log a message.
			getScopeFacade().log("WAREWORK executed commit in Data Store '" + getName() + "' of Service '"
					+ getService().getName() + "'.", LogServiceConstants.LOG_LEVEL_INFO);

		} else {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot perform commit in Data Store '" + getName() + "' of Service '"
							+ getService().getName() + "' because the properties file is not accessible to be written.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

	}

	/**
	 * Closes the connection with the properties Data Store.
	 */
	protected void close() {
		// DO NOTHING.
	}

	/**
	 * Validates if the connection is closed.
	 * 
	 * @return <code>false</code>.<br>
	 *         <br>
	 */
	protected boolean isClosed() {
		return false;
	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates a <code>java.io.File</code> that points to the properties file to
	 * create.
	 * 
	 * @return A <code>java.io.File</code>.<br>
	 *         <br>
	 */
	private File createTarget() {

		// Get the value of the parameter.
		final Object target = getConnector().getInitParameter(PropertiesConnector.PARAMETER_CONFIG_TARGET);

		// Get the stream for the XML file.
		if (target instanceof URL) {
			try {

				// Get the URL for the XML file.
				final URL url = (URL) target;

				// Return the stream for the URL.
				return new File(url.getFile());

			} catch (final Exception e) {
				// DO NOTHING.
			}
		} else if (target instanceof String) {
			try {

				// Get the URL for the XML file.
				final URL url = new URL((String) target);

				// Return the stream for the URL.
				return new File(url.getFile());

			} catch (final Exception e) {
				// DO NOTHING.
			}
		}

		// At this point, nothing to return.
		return null;

	}

}
