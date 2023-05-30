package com.warework.service.converter.client;

import java.io.IOException;

import com.warework.core.service.client.AbstractClient;
import com.warework.core.service.client.ClientException;
import com.warework.core.util.helper.StringL1Helper;
import com.warework.service.log.LogServiceConstants;

/**
 * JavaScript compressor.
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public final class JavaScriptCompressor extends AbstractClient implements ConverterFacade {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Constant that defines the default name for the client.
	 */
	public static final String DEFAULT_CLIENT_NAME = "js-compressor";

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Minimizes a JavaScript code.
	 * 
	 * @param source Source JavaScript to compress.<br>
	 *               <br>
	 * @return Minimized JavaScript.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to perform the
	 *                         transformation.<br>
	 *                         <br>
	 */
	public Object transform(final Object source) throws ClientException {
		if (source instanceof String) {
			try {
				return StringL1Helper.compressJavaScript((String) source);
			} catch (final IOException e) {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot compress JavaScript in Converter '" + getName() + "' at Service '"
								+ getService().getName() + "' because the following error is found: " + e.getMessage(),
						e, LogServiceConstants.LOG_LEVEL_WARN);
			}
		} else {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot compress JavaScript in Converter '" + getName() + "' at Service '"
							+ getService().getName() + "' because given object is not a string.",
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
