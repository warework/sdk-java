package com.warework.service.converter.client.connector;

import com.warework.core.service.client.connector.ConnectorException;
import com.warework.service.converter.client.StringFormatter;
import com.warework.service.log.LogServiceConstants;

/**
 * This connector holds the configuration required to setup a string
 * formatter.<br>
 * <br>
 * 
 * The configuration must declare the operation to perform. Use the
 * <code>PARAMETER_OPERATION_MODE</code> constant (defined in the
 * <code>StringFormatterConnector</code> class) with any of the following values
 * to specify the operation:<br>
 * <br>
 * <ul>
 * <li><code>OPERATION_MODE_TO_UPPER_CASE</code>: transforms a string to upper
 * case. In XML configu-ration files use <code>"to-upper-case"</code>.<br>
 * <br>
 * </li>
 * <li><code>OPERATION_MODE_TO_LOWER_CASE</code>: transforms a string to lower
 * case. In XML configu-ration files use <code>"to-lower-case"</code>.<br>
 * <br>
 * </li>
 * <li><code>OPERATION_MODE_TRIM</code>: removes white spaces from both ends of
 * a string. In XML configuration files use "trim".<br>
 * <br>
 * </li>
 * <li><code>OPERATION_MODE_FIRST_LETTER_TO_LOWER_CASE</code>: transforms the
 * first letter of a string to lower case. In XML configuration files use
 * <code>"first-letter-to-lower-case"</code>.<br>
 * <br>
 * </li>
 * <li><code>OPERATION_MODE_FIRST_LETTER_TO_UPPER_CASE</code>: transforms the
 * first letter of a string to upper case. In XML configuration files use
 * <code>"first-letter-to-upper-case"</code>.</li>
 * </ul>
 * <br>
 * The following example shows how to create a String Formatter in the Converter
 * Service to transform strings to upper case:<br>
 * <br>
 * <code>
 * // Create the configuration for the String Formatter.<br>
 * Map&lt;String, Object&gt; config = new HashMap&lt;String, Object&gt;();<br>
 * <br>
 * // Set the formatter configuration.<br>
 * config.put(StringFormatterConnector.PARAMETER_OPERATION_MODE, StringFormatterConnector.OPERATION_MODE_TO_UPPER_CASE);<br>
 * <br>
 * // Create the String Formatter.<br>
 * converterService.createClient("uppercase-formatter", StringFormatterConnector.class, config);<br> 
 * </code> <br>
 * <b><u>Minimum prerequisites to run this Connector</u>:</b><br>
 * <br>
 * <ul>
 * <li><b>Runtime:</b> Java 1.5</li>
 * </ul>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public final class StringFormatterConnector extends AbstractConverterConnector {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// PARAMETERS

	/**
	 * Initialization parameter that specifies what kind of operation to perform.
	 * Use for this parameter any of the <code>OPERATION_MODE_xyz</code> constants
	 * defined in this class.
	 */
	public final static String PARAMETER_OPERATION_MODE = "operation-mode";

	// OPERATION MODES

	/**
	 * Operation mode to convert a string to upper case.
	 */
	public final static String OPERATION_MODE_TO_UPPER_CASE = "to-upper-case";

	/**
	 * Operation mode to convert a string to lower case.
	 */
	public final static String OPERATION_MODE_TO_LOWER_CASE = "to-lower-case";

	/**
	 * Operation mode to remove white spaces from both ends of a string.
	 */
	public final static String OPERATION_MODE_TRIM = "trim";

	/**
	 * Operation mode to convert the first letter of a string to lower case.
	 */
	public final static String OPERATION_MODE_FIRST_LETTER_TO_LOWER_CASE = "first-letter-to-lower-case";

	/**
	 * Operation mode to convert the first letter of a string to upper case.
	 */
	public final static String OPERATION_MODE_FIRST_LETTER_TO_UPPER_CASE = "first-letter-to-upper-case";

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the type of the Converter.
	 * 
	 * @return A <code>com.warework.service.converter.client.Base64Converter</code>
	 *         Client.
	 */
	public Class<StringFormatter> getClientType() {
		return StringFormatter.class;
	}

	/**
	 * This method does not performs any operation.
	 * 
	 * @return <code>null</code>.<br>
	 *         <br>
	 */
	public Object getClientConnection() {
		return null;
	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * This method does not performs any operation.
	 * 
	 * @return <code>null</code>.<br>
	 *         <br>
	 */
	protected Object createConnectionSource() {
		return null;
	}

	/**
	 * Initializes the connector.
	 * 
	 * @throws ConnectorException If there is an error when trying to initialize the
	 *                            Connector.<br>
	 *                            <br>
	 */
	protected void initialize() throws ConnectorException {

		// Get the value of the server parameter.
		if (getInitParameter(PARAMETER_OPERATION_MODE) != null) {

			// Get the operation mode parameter.
			final Object config = getInitParameter(PARAMETER_OPERATION_MODE);

			// Validate the type of the parameter.
			if (config instanceof String) {

				// Get the operation mode.
				final String operationMode = (String) config;

				// Validate the value of the parameter.
				if (!(operationMode.equalsIgnoreCase(OPERATION_MODE_TO_UPPER_CASE))
						&& !(operationMode.equalsIgnoreCase(OPERATION_MODE_TO_LOWER_CASE))
						&& !(operationMode.equalsIgnoreCase(OPERATION_MODE_TRIM))
						&& !(operationMode.equalsIgnoreCase(OPERATION_MODE_FIRST_LETTER_TO_LOWER_CASE))
						&& !(operationMode.equalsIgnoreCase(OPERATION_MODE_FIRST_LETTER_TO_UPPER_CASE))) {
					throw new ConnectorException(getScopeFacade(), "WAREWORK cannot initialize Converter '" + getClientName()
							+ "' in Service '" + getService().getName()
							+ "' because given operation mode is not valid. Please use any of the OPERATION_MODE_xyz constants defined at '"
							+ StringFormatterConnector.class.getName() + "' to specify which operation to perform.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}

			} else {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize Converter '" + getClientName() + "' in Service '"
								+ getService().getName() + "' because given '" + PARAMETER_OPERATION_MODE
								+ "' parameter is not a string.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

		} else {
			throw new ConnectorException(getScopeFacade(),
					"WAREWORK cannot initialize Converter '" + getClientName() + "' at Service '"
							+ getService().getName() + "' because parameter '" + PARAMETER_OPERATION_MODE
							+ "' is not defined.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

	}

}
