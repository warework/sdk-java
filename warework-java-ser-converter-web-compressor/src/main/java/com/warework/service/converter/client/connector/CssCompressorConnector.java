package com.warework.service.converter.client.connector;

import java.util.HashMap;
import java.util.Map;

import com.warework.core.service.client.connector.ConnectorException;
import com.warework.service.log.LogServiceConstants;

/**
 * This Connector creates a Converter to compress Cascading Style Sheets
 * (CSS).<br>
 * <br>
 * <span>To add a </span><span>CSS compressor into the Converter Service you
 * have to invoke method </span><span>createClient() </span><span>that exists in
 * its Facade with a name, the CSS compressor Connector class and optionally, a
 * configuration for the compressor. If you plan to use the CSS compressor with
 * the default configuration, then you can use
 * </span><span>StylesheetCompressorConnector</span><span> as
 * follows:</span><br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Create&nbsp;the&nbsp;CSS&nbsp;compressor.<br>converterService.createClient(&quot;css-compressor&quot;,<br>&nbsp;&nbsp;&nbsp;StylesheetCompressorConnector.class,&nbsp;null);</code>
 * <br>
 * <br>
 * <span>It is also possible to adjust the configuration of the compressor with
 * the </span><span>PARAMETER_LINE_BREAK</span>. It specifies the column to
 * split the lines. Some source control tools don't like files containing lines
 * longer than, say 8000 characters. The line break option is used in that case
 * to split long lines after a specific column. It can also be used to make the
 * code more readable, easier to debug (especially with the MS Script Debugger).
 * Specify 0 to get a line break after each rule in CSS. Specify -1 to return
 * just one line. Bear in mind that this parameter is optional (default value is
 * -1).<br>
 * <br>
 * <span>To configure this </span><span>compressor with this parameter, use a
 * </span><span>java.util.Map</span><span>:</span><br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Create&nbsp;the&nbsp;configuration&nbsp;for&nbsp;the&nbsp;compressor.<br>Map&lt;String, Object&gt;&nbsp;config&nbsp;=&nbsp;new&nbsp;HashMap&lt;String, Object&gt;();<br><br>&#47;&#47;&nbsp;Configure&nbsp;the&nbsp;compressor.<br>config.put(CssCompressorConnector.PARAMETER_LINE_BREAK,&nbsp;<br>&nbsp;&nbsp;&nbsp;new&nbsp;Integer(80));<br><br>&#47;&#47;&nbsp;Create&nbsp;the&nbsp;CSS&nbsp;compressor.<br>converterService.createClient(&quot;css-compressor&quot;,&nbsp;<br>&nbsp;&nbsp;&nbsp;CssCompressorConnector.class,&nbsp;config);</code>
 * <br>
 * <br>
 * <span>Check </span><span>it now how to do it with the Proxy Service XML
 * configuration file:</span><br>
 * <br>
 * <code>&lt;?xml&nbsp;version=&quot;1.0&quot;&nbsp;encoding=&quot;UTF-8&quot;?&gt;<br>&lt;proxy-service&nbsp;xmlns:xsi=&quot;http://www.w3.org&#47;2001&#47;XMLSchema-instance&quot;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;xsi:noNamespaceSchemaLocation=&quot;http://repository.warework.com&#47;xsd&#47;proxy-<br>&nbsp;&nbsp;&nbsp;service-1.0.0.xsd&quot;&gt;<br><br>&nbsp;&nbsp;&nbsp;&lt;clients&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;client&nbsp;name=&quot;css-compressor&quot;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;connector=&quot;com.warework.service.converter.client.connector.&nbsp;&hellip;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&hellip;&nbsp;CssCompressorConnector&quot;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;line-break&quot;&nbsp;value=&quot;80&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;client&gt;<br>&nbsp;&nbsp;&nbsp;&lt;&#47;clients&gt;<br><br>&lt;&#47;proxy-service&gt;</code>
 * <br>
 * <br>
 * <b><u>Minimum prerequisites to run this Connector</u>:</b><br>
 * <br>
 * <ul>
 * <li><b>Runtime:</b> Java 1.5</li>
 * </ul>
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public final class CssCompressorConnector extends AbstractConverterConnector {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// PARAMETERS

	/**
	 * Initialization parameter that specifies the column to split the lines. Some
	 * source control tools don't like files containing lines longer than, say 8000
	 * characters. The linebreak option is used in that case to split long lines
	 * after a specific column. It can also be used to make the code more readable,
	 * easier to debug (especially with the MS Script Debugger). Specify 0 to get a
	 * line break after each rule in CSS. Specify -1 to return just one line. This
	 * parameter is optional. Default value is -1.
	 */
	public final static String PARAMETER_LINE_BREAK = "line-break";

	// DEFAULT VALUES

	// Compress code in one line.
	private final static int DEFAULT_LINE_BREAK_POSITION = -1;

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the type of the Converter.
	 * 
	 * @return A
	 *         <code>com.warework.service.converter.client.CssCompressor</code>
	 *         Client.
	 */
	public Class<com.warework.service.converter.client.CssCompressor> getClientType() {
		return com.warework.service.converter.client.CssCompressor.class;
	}

	/**
	 * Gets a collection with the information required to perform the conversion.
	 * 
	 * @return A <code>java.util.Map</code> instance.
	 */
	public Object getClientConnection() {

		// Create the Converter connection data.
		final Map<String, Object> connection = new HashMap<String, Object>();

		// Configure the line break.
		final Object lineBreak = getInitParameter(PARAMETER_LINE_BREAK);
		if (lineBreak == null) {
			connection.put(PARAMETER_LINE_BREAK, Integer.valueOf(DEFAULT_LINE_BREAK_POSITION));
		} else if (lineBreak instanceof Integer) {
			connection.put(PARAMETER_LINE_BREAK, (Integer) lineBreak);
		} else {
			connection.put(PARAMETER_LINE_BREAK, Integer.valueOf((String) lineBreak));
		}

		// Return the connection data.
		return connection;

	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Initializes the connector.
	 * 
	 * @throws ConnectorException If there is an error when trying to initialize the
	 *                            connector.<br>
	 *                            <br>
	 */
	protected void initialize() throws ConnectorException {

		// Validate the value of the PARAMETER_LINE_BREAK.
		final Object lineBreak = getInitParameter(PARAMETER_LINE_BREAK);
		if (lineBreak != null) {
			if (lineBreak instanceof String) {
				try {
					Integer.valueOf((String) lineBreak);
				} catch (final Exception e) {
					throw new ConnectorException(getScopeFacade(),
							"WAREWORK cannot initialize Converter '" + getClientName() + "' at Service '"
									+ getService().getName() + "' because parameter '" + PARAMETER_LINE_BREAK
									+ "' is not a valid integer value.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}
			} else if (!(lineBreak instanceof Integer)) {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize Converter '" + getClientName() + "' at Service '"
								+ getService().getName() + "' because parameter '" + PARAMETER_LINE_BREAK
								+ "' is not a string or an integer value.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}
		}

	}

	/**
	 * This method does not performs any operation.
	 * 
	 * @return <code>null</code>.<br>
	 *         <br>
	 */
	protected Object createConnectionSource() {
		return null;
	}

}
