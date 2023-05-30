package com.warework.service.converter.client;

import com.warework.core.service.client.AbstractClient;
import com.warework.core.service.client.ClientException;
import com.warework.service.log.LogServiceConstants;

/**
 * XML compressor.
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public final class XmlCompressor extends AbstractClient implements ConverterFacade {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Constant that defines the default name for the client.
	 */
	public static final String DEFAULT_CLIENT_NAME = "xml-compressor";

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Minimizes XML code.
	 * 
	 * @param source Source XML to compress.<br>
	 *               <br>
	 * @return Minimized XML.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to perform the
	 *                         transformation.<br>
	 *                         <br>
	 */
	public Object transform(final Object source) throws ClientException {
		if (isConnected()) {
			if (source instanceof String) {

				// Get the Converter connection.
				final com.googlecode.htmlcompressor.compressor.XmlCompressor connection = (com.googlecode.htmlcompressor.compressor.XmlCompressor) getConnection();

				// Perform compression and return result.
				return connection.compress((String) source);

			} else {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot compress XML in Converter '" + getName() + "' at Service '"
								+ getService().getName() + "' because given object is not a string.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}
		} else {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot compress XML in Converter '" + getName() + "' at Service '"
							+ getService().getName() + "' because Converter connection is closed.",
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
