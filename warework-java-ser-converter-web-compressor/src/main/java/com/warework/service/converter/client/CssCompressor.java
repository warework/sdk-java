package com.warework.service.converter.client;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import com.warework.core.service.client.AbstractClient;
import com.warework.core.service.client.ClientException;
import com.warework.service.converter.client.connector.CssCompressorConnector;
import com.warework.service.log.LogServiceConstants;

/**
 * Cascading Style Sheets compressor.
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public final class CssCompressor extends AbstractClient implements ConverterFacade {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Constant that defines the default name for the client.
	 */
	public static final String DEFAULT_CLIENT_NAME = "css-compressor";

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Minimizes CSS code.
	 * 
	 * @param source Source CSS to compress.<br>
	 *               <br>
	 * @return Minimized CSS.<br>
	 *         <br>
	 * @throws ClientException If there is an error when trying to perform the
	 *                         transformation.<br>
	 *                         <br>
	 */
	@SuppressWarnings("unchecked")
	public Object transform(final Object source) throws ClientException {
		if (isConnected()) {
			if (source instanceof String) {
				try {

					// Get the connection data.
					final Map<String, Object> connection = (Map<String, Object>) getConnection();

					// Create a new CSS YUI compressor.
					final com.yahoo.platform.yui.compressor.CssCompressor compressor = new com.yahoo.platform.yui.compressor.CssCompressor(
							new StringReader((String) source));

					// Result handler.
					final Writer writer = new StringWriter();

					// Compress CSS code.
					compressor.compress(writer, (Integer) connection.get(CssCompressorConnector.PARAMETER_LINE_BREAK));

					// Return minimized CSS.
					return writer.toString();

				} catch (final IOException e) {
					throw new ClientException(getScopeFacade(),
							"WAREWORK cannot compress CSS in Converter '" + getName() + "' at Service '"
									+ getService().getName() + "' because the following exception is thrown: "
									+ e.getMessage(),
							e, LogServiceConstants.LOG_LEVEL_WARN);
				}
			} else {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot compress CSS in Converter '" + getName() + "' at Service '"
								+ getService().getName() + "' because given object is not a string.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}
		} else {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot compress CSS in Converter '" + getName() + "' at Service '"
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
