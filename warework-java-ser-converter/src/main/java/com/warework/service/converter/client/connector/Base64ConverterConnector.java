package com.warework.service.converter.client.connector;

import com.warework.core.service.client.connector.ConnectorException;
import com.warework.service.converter.client.Base64Converter;
import com.warework.service.log.LogServiceConstants;

/**
 * This connector holds the configuration required to setup a Converter to
 * encode / decode strings and byte arrays with Base64.<br>
 * <br>
 * The configuration has to specify what kind of operation to perform (encode or
 * decode with Base64). Use the <code>PARAMETER_OPERATION_MODE</code> constant
 * (defined in the Connector class) with any of the following values to specify
 * the operation:<br>
 * 
 * <ul>
 * <li><code>OPERATION_MODE_ENCODE</code>: encodes a string or a byte array with
 * Base64. In XML configuration files use <code>"encode"</code>.<br>
 * <br>
 * </li>
 * <li><code>OPERATION_MODE_DECODE</code>: decodes a string or a byte array with
 * Base64. In XML configuration files use <code>"decode"</code>.</li>
 * </ul>
 * 
 * The following example shows how to create a Base64 Converter in the Converter
 * Service:<br>
 * <br>
 * <code>
 * // Create the configuration for the Converter.<br>
 * Map&lt;String, Object&gt; config = new HashMap&lt;String, Object&gt;();<br>
 * <br>
 * // Set the Converter configuration.<br>
 * config.put(Base64ConverterConnector.PARAMETER_OPERATION_MODE, Base64ConverterConnector.OPERATION_MODE_ENCODE);<br>
 * <br>
 * // Create the Base64 Converter.<br>
 * converterService.createClient("base64-encoder", Base64ConverterConnector.class, config);<br> 
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
public final class Base64ConverterConnector extends AbstractConverterConnector {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// PARAMETERS

	/**
	 * Initialization parameter that specifies the operation to perform. Use
	 * <code>OPERATION_MODE_Encode</code> to transform string into Base64 notation
	 * and <code>OPERATION_MODE_Decode</code> to decode Base64 strings.
	 */
	public final static String PARAMETER_OPERATION_MODE = "operation-mode";

	// OPERATION MODES

	/**
	 * Operation mode to encode a string to Base64.
	 */
	public final static String OPERATION_MODE_ENCODE = "encode";

	/**
	 * Operation mode to decode a Base64 string.
	 */
	public final static String OPERATION_MODE_DECODE = "decode";

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the type of the Converter.
	 * 
	 * @return A <code>com.warework.service.converter.client.Base64Converter</code>
	 *         Client.
	 */
	public Class<Base64Converter> getClientType() {
		return Base64Converter.class;
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
				if ((!operationMode.equalsIgnoreCase(OPERATION_MODE_ENCODE))
						&& (!operationMode.equalsIgnoreCase(OPERATION_MODE_DECODE))) {
					throw new ConnectorException(getScopeFacade(), "WAREWORK cannot initialize Converter '" + getClientName()
							+ "' in Service '" + getService().getName() + "' because given '" + PARAMETER_OPERATION_MODE
							+ "' parameter is not '" + OPERATION_MODE_ENCODE + "' or '" + OPERATION_MODE_DECODE + "'.",
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
