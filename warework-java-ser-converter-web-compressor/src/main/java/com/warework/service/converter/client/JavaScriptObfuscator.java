package com.warework.service.converter.client;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import com.warework.core.service.client.AbstractClient;
import com.warework.core.service.client.ClientException;
import com.warework.service.converter.client.connector.JavaScriptObfuscatorConnector;
import com.warework.service.log.LogServiceConstants;

/**
 * JavaScript obfuscator.
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public final class JavaScriptObfuscator extends AbstractClient implements ConverterFacade {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Constant that defines the default name for the client.
	 */
	public static final String DEFAULT_CLIENT_NAME = "js-obfuscator";

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

					// Create a new JS YUI compressor.
					final com.yahoo.platform.yui.compressor.JavaScriptCompressor compressor = new com.yahoo.platform.yui.compressor.JavaScriptCompressor(
							new StringReader((String) source), null);

					// Get the value of the line break parameter.
					final Integer lineBreak = (Integer) connection
							.get(JavaScriptObfuscatorConnector.PARAMETER_LINE_BREAK);

					// Get the value of the obfuscate parameter.
					final Boolean obfuscate = (Boolean) connection
							.get(JavaScriptObfuscatorConnector.PARAMETER_OBFUSCATE);

					// Get the value of the preserve semicolons parameter.
					final Boolean preserveSemicolons = (Boolean) connection
							.get(JavaScriptObfuscatorConnector.PARAMETER_PRESERVE_SEMICOLONS);

					// Get the value of the enable optimizations parameter.
					final Boolean disableOptimizations = (Boolean) connection
							.get(JavaScriptObfuscatorConnector.PARAMETER_DISABLE_OPTIMIZATIONS);

					// Result handler.
					final Writer writer = new StringWriter();

					// Compress JS code.
					compressor.compress(writer, lineBreak, obfuscate, false, preserveSemicolons, disableOptimizations);

					// Return minimized JS.
					return writer.toString();

				} catch (final IOException e) {
					throw new ClientException(getScopeFacade(),
							"WAREWORK cannot compress JavaScript in Converter '" + getName() + "' at Service '"
									+ getService().getName() + "' because the following exception is thrown: "
									+ e.getMessage(),
							e, LogServiceConstants.LOG_LEVEL_WARN);
				}
			} else {
				throw new ClientException(getScopeFacade(),
						"WAREWORK cannot compress JavaScript in Converter '" + getName() + "' at Service '"
								+ getService().getName() + "' because given object is not a string.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}
		} else {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot compress JavaScript in Converter '" + getName() + "' at Service '"
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
