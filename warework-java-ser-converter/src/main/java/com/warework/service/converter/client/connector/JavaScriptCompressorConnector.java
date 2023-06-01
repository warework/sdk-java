package com.warework.service.converter.client.connector;

import com.warework.service.converter.client.JavaScriptCompressor;

/**
 * This connector creates a converter to compress JavaScript or JSON code.<br>
 * <br>
 * The following example shows how to create a JavaScript Compressor in the
 * Converter Service:<br>
 * <br>
 * <code>
 * // Create the JavaScript Compressor.<br>
 * converterService.createClient("js-compressor", JavaScriptCompressorConnector.class, null);<br> 
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
public final class JavaScriptCompressorConnector extends
		AbstractConverterConnector {

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the type of the Converter.
	 * 
	 * @return A
	 *         <code>com.warework.service.converter.client.JavaScriptCompressor</code>
	 *         Client.
	 */
	public Class<JavaScriptCompressor> getClientType() {
		return JavaScriptCompressor.class;
	}

	/**
	 * This method does not performs any operation.
	 * 
	 * @return <code>null</code>.<br>
	 * <br>
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
	 * <br>
	 */
	protected Object createConnectionSource() {
		return null;
	}

}
