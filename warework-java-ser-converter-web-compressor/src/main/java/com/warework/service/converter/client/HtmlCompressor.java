package com.warework.service.converter.client;

import com.warework.core.service.client.AbstractClient;
import com.warework.core.service.client.ClientException;
import com.warework.service.log.LogServiceConstants;

/**
 * HTML compressor.
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public final class HtmlCompressor extends AbstractClient implements ConverterFacade {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Constant that defines the default name for the client.
	 */
	public static final String DEFAULT_CLIENT_NAME = "html-compressor";

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Minimizes HTML code.
	 * 
	 * @param source Source HTML to compress.<br>
	 *               <br>
	 * @return Minimized HTML.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to perform the
	 *                         transformation.<br>
	 *                         <br>
	 */
	public Object transform(final Object source) throws ClientException {
		if (isConnected()) {
			if (source instanceof String) {

				// Get the Converter connection.
				final com.googlecode.htmlcompressor.compressor.HtmlCompressor connection = (com.googlecode.htmlcompressor.compressor.HtmlCompressor) getConnection();

				// Perform compression and return result.
				return connection.compress((String) source);

			} else {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot compress HTML in Converter '" + getName() + "' at Service '"
								+ getService().getName() + "' because given object is not a string.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}
		} else {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot compress HTML in Converter '" + getName() + "' at Service '"
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
