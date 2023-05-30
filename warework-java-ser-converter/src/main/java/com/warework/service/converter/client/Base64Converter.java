package com.warework.service.converter.client;

import com.warework.core.service.client.AbstractClient;
import com.warework.core.service.client.ClientException;
import com.warework.core.util.helper.CodecL1Helper;
import com.warework.service.converter.client.connector.Base64ConverterConnector;
import com.warework.service.log.LogServiceConstants;

/**
 * Converter to encode and decode with Base64.
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public final class Base64Converter extends AbstractClient implements ConverterFacade {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Constant that defines the default name for the client.
	 */
	public static final String DEFAULT_CLIENT_NAME = "base64-converter";

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
		if ((source instanceof String) || (source instanceof byte[])) {

			// Get the operation mode.
			final String operationMode = (String) getConnector()
					.getInitParameter(Base64ConverterConnector.PARAMETER_OPERATION_MODE);

			// Execute the transformation.
			if (operationMode.equalsIgnoreCase(Base64ConverterConnector.OPERATION_MODE_ENCODE)) {
				if (source instanceof String) {
					return CodecL1Helper.encodeBase64((String) source);
				} else {
					return CodecL1Helper.encodeBase64((byte[]) source);
				}
			} else {
				try {
					if (source instanceof String) {
						return CodecL1Helper.decodeBase64((String) source);
					} else {
						return CodecL1Helper.decodeBase64((byte[]) source);
					}
				} catch (final Exception e) {
					throw new ClientException(getScopeFacade(),
							"WAREWORK cannot decode given base64 string in Converter '" + getName() + "' at Service '"
									+ getService().getName() + "' because the following exception is thrown: "
									+ e.getMessage(),
							e, LogServiceConstants.LOG_LEVEL_WARN);
				}
			}

		} else {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot encode or decode given object in Converter '" + getName() + "' at Service '"
							+ getService().getName() + "' because given value is not a string or a byte array.",
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
