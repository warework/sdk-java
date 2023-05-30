package com.warework.service.converter.client;

import com.warework.core.service.client.AbstractClient;
import com.warework.core.service.client.ClientException;
import com.warework.core.util.helper.StringL1Helper;
import com.warework.service.converter.client.connector.StringFormatterConnector;
import com.warework.service.log.LogServiceConstants;

/**
 * Converter to encode and decode in Base64.
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public final class StringFormatter extends AbstractClient implements ConverterFacade {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Constant that defines the default name for the client.
	 */
	public static final String DEFAULT_CLIENT_NAME = "string-formatter";

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Encodes a string into Base64 notation or decodes a Base64 string.
	 * 
	 * @param source Source string to encode or decode.<br>
	 *               <br>
	 * @return New object that represents the transformation of the source
	 *         object.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to perform the
	 *                         transformation.<br>
	 *                         <br>
	 */
	public Object transform(final Object source) throws ClientException {
		if (source instanceof String) {

			// Get the string to transform.
			final String string = (String) source;

			// Get the operation mode.
			final String operationMode = (String) getConnector()
					.getInitParameter(StringFormatterConnector.PARAMETER_OPERATION_MODE);

			// Execute the transformation.
			if (operationMode.equalsIgnoreCase(StringFormatterConnector.OPERATION_MODE_TO_UPPER_CASE)) {
				return string.toUpperCase();
			} else if (operationMode.equalsIgnoreCase(StringFormatterConnector.OPERATION_MODE_TO_LOWER_CASE)) {
				return string.toLowerCase();
			} else if (operationMode
					.equalsIgnoreCase(StringFormatterConnector.OPERATION_MODE_FIRST_LETTER_TO_LOWER_CASE)) {
				return StringL1Helper.firstLetterToLowerCase(string);
			} else if (operationMode
					.equalsIgnoreCase(StringFormatterConnector.OPERATION_MODE_FIRST_LETTER_TO_UPPER_CASE)) {
				return StringL1Helper.firstLetterToUpperCase(string);
			} else {
				return string.trim();
			}

		} else {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot format string provided in Converter '" + getName() + "' at Service '"
							+ getService().getName() + "' because given value is not a string.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * This method does not performs any operation.
	 */
	protected void close() {
		// DO NOTHING.
	}

	/**
	 * This method does not performs any operation.
	 * 
	 * @return <code>false</code>.<br>
	 *         <br>
	 */
	protected boolean isClosed() {
		return false;
	}

}
