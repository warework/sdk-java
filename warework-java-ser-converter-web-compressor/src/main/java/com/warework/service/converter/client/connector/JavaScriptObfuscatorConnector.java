package com.warework.service.converter.client.connector;

import java.util.HashMap;
import java.util.Map;

import com.warework.core.service.client.connector.ConnectorException;
import com.warework.service.converter.client.JavaScriptObfuscator;
import com.warework.service.log.LogServiceConstants;

/**
 * This Connector creates a Converter to compress and obfuscate JavaScript
 * code.<br>
 * <br>
 * <span>To add a </span><span>JavaScript obfuscator into the Converter Service
 * you have to invoke method </span><span>createClient() </span><span>that
 * exists in its Facade with a name, the JavaScript obfuscator Connector class
 * and optionally, a configuration for the obfuscator. If you plan to use the
 * JavaScript obfuscator with the default configuration, then you can use
 * </span><span>JavaScriptObfuscatorConnector</span><span> as
 * follows:</span><br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Create&nbsp;the&nbsp;JavaScript&nbsp;obfuscator.<br>converterService.createClient(&quot;js-obfuscator&quot;,<br>&nbsp;&nbsp;&nbsp;JavaScriptObfuscatorConnector.class,&nbsp;null);</code>
 * <br>
 * <br>
 * It is also possible to adjust the configuration of the obfuscator with the
 * following parameters:<br>
 * <br>
 * <ul class="t0">
 * <li><span>PARAMETER_LINE_BREAK</span>: Specifies the column to split the
 * lines. Some source control tools don't like files containing lines longer
 * than, say 8000 characters. The line break option is used in that case to
 * split long lines after a specific column. It can also be used to make the
 * code more readable, easier to debug (especially with the MS Script Debugger).
 * Specify 0 to get a line break after each semi-colon in JavaScript. Specify -1
 * to return just one line. This parameter is optional. Default value is -1.<br>
 * </li>
 * </ul>
 * <br>
 * <ul class="t0">
 * <li><span>PARAMETER_OBFUSCATE</span><span>: Specifies if the Converter has to
 * obfuscate the JavaScript local symbols. Accepted values for this parameter
 * are </span><span>true</span><span> or </span><span>false</span><span> (as
 * </span><span>java.lang.String</span><span> or
 * </span><span>java.lang.Boolean</span><span> objects). Use
 * </span><span>false</span><span> just to minify the JavaScript code. This
 * parameter is optional. Default value is
 * </span><span>true</span><span>.</span><br>
 * </li>
 * </ul>
 * <br>
 * <ul class="t0">
 * <li><span>PARAMETER_PRESERVE_SEMICOLONS</span><span>: Specifies if the
 * Converter has to preserve unnecessary semicolons (such as right before a
 * '}'). Accepted values for this parameter are </span><span>true</span><span>
 * or </span><span>false</span><span> (as
 * </span><span>java.lang.String</span><span> or
 * </span><span>java.lang.Boolean</span><span> objects). This parameter is
 * optional. Default value is </span><span>false</span><span>.</span><br>
 * </li>
 * </ul>
 * <br>
 * <ul class="t0">
 * <li><span>PARAMETER_DISABLE_OPTIMIZATIONS</span><span>: Specifies if the
 * Converter has to disable all the built-in micro optimizations. Accepted
 * values for this parameter are </span><span>true</span><span> or
 * </span><span>false</span><span> (as
 * </span><span>java.lang.String</span><span> or
 * </span><span>java.lang.Boolean</span><span> objects). This parameter is
 * optional. Default value is </span><span>false</span><span>.</span><br>
 * </li>
 * </ul>
 * <br>
 * <span>To configure this obfuscator with parameters, use a
 * </span><span>java.util.Map</span><span> like this:</span><br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Create&nbsp;the&nbsp;configuration&nbsp;for&nbsp;the&nbsp;obfuscator.<br>Map&lt;String, Object&gt;&nbsp;config&nbsp;=&nbsp;new&nbsp;HashMap&lt;String, Object&gt;();<br><br>&#47;&#47;&nbsp;Configure&nbsp;the&nbsp;obfuscator.<br>config.put(JavaScriptObfuscatorConnector.PARAMETER_LINE_BREAK,&nbsp;<br>&nbsp;&nbsp;&nbsp;new&nbsp;Integer(80));<br>config.put(JavaScriptObfuscatorConnector.PARAMETER_PRESERVE_SEMICOLONS,&nbsp;<br>&nbsp;&nbsp;&nbsp;Boolean.TRUE);<br>config.put(JavaScriptObfuscatorConnector.PARAMETER_DISABLE_OPTIMIZATIONS,&nbsp;<br>&nbsp;&nbsp;&nbsp;Boolean.TRUE);<br><br>&#47;&#47;&nbsp;Create&nbsp;the&nbsp;JavaScript&nbsp;obfuscator.<br>converterService.createClient(&quot;js-obfuscator&quot;,&nbsp;<br>&nbsp;&nbsp;&nbsp;JavaScriptObfuscatorConnector.class,&nbsp;config);</code>
 * <br>
 * <br>
 * <span>Check </span><span>it now how to do it with the Proxy Service XML
 * configuration file:</span><br>
 * <br>
 * <code>&lt;?xml&nbsp;version=&quot;1.0&quot;&nbsp;encoding=&quot;UTF-8&quot;?&gt;<br>&lt;proxy-service&nbsp;xmlns:xsi=&quot;http://www.w3.org&#47;2001&#47;XMLSchema-instance&quot;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;xsi:noNamespaceSchemaLocation=&quot;http://repository.warework.com&#47;xsd&#47;proxy-<br>&nbsp;&nbsp;&nbsp;service-1.0.0.xsd&quot;&gt;<br><br>&nbsp;&nbsp;&nbsp;&lt;clients&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;client&nbsp;name=&quot;js-obfuscator&quot;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;connector=&quot;com.warework.service.converter.client.connector.&nbsp;&hellip;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&hellip;&nbsp;JavaScriptObfuscatorConnector&quot;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;line-break&quot;&nbsp;value=&quot;80&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;obfuscate&quot;&nbsp;value=&quot;true&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;preserve-semicolons&quot;&nbsp;value=&quot;true&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;disable-optimizations&quot;&nbsp;value=&quot;false&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;client&gt;<br>&nbsp;&nbsp;&nbsp;&lt;&#47;clients&gt;<br><br>&lt;&#47;proxy-service&gt;</code>
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
public final class JavaScriptObfuscatorConnector extends AbstractConverterConnector {

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
	 * line break after each semi-colon in JavaScript. Specify -1 to return just one
	 * line. This parameter is optional. Default value is -1.
	 */
	public final static String PARAMETER_LINE_BREAK = "line-break";

	/**
	 * Initialization parameter that specifies if the Converter has to obfuscate the
	 * JavaScript local symbols. Accepted values for this parameter are
	 * <code>true</code> or <code>false</code> (as <code>java.lang.String</code> or
	 * <code>java.lang.Boolean</code> objects). Use <code>false</code> just to
	 * minify the JavaScript code. This parameter is optional. Default value is
	 * <code>true</code>.
	 */
	public final static String PARAMETER_OBFUSCATE = "obfuscate";

	/**
	 * Initialization parameter that specifies if the Converter has to preserve
	 * unnecessary semicolons (such as right before a '}'). Accepted values for this
	 * parameter are <code>true</code> or <code>false</code> (as
	 * <code>java.lang.String</code> or <code>java.lang.Boolean</code> objects).
	 * This parameter is optional. Default value is <code>false</code>.
	 */
	public final static String PARAMETER_PRESERVE_SEMICOLONS = "preserve-semicolons";

	/**
	 * Initialization parameter that specifies if the Converter has to disable all
	 * the built-in micro optimizations. Accepted values for this parameter are
	 * <code>true</code> or <code>false</code> (as <code>java.lang.String</code> or
	 * <code>java.lang.Boolean</code> objects). This parameter is optional. Default
	 * value is <code>false</code>.
	 */
	public final static String PARAMETER_DISABLE_OPTIMIZATIONS = "disable-optimizations";

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
	 *         <code>com.warework.service.converter.client.JavaScriptObfuscator</code>
	 *         Client.
	 */
	public Class<JavaScriptObfuscator> getClientType() {
		return JavaScriptObfuscator.class;
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

		// Configure the obfuscate parameter.
		final Object obfuscate = getInitParameter(PARAMETER_OBFUSCATE);
		if (obfuscate == null) {
			connection.put(PARAMETER_OBFUSCATE, Boolean.TRUE);
		} else if (obfuscate instanceof Boolean) {
			connection.put(PARAMETER_OBFUSCATE, (Boolean) obfuscate);
		} else {
			connection.put(PARAMETER_OBFUSCATE, Boolean.valueOf((String) obfuscate));
		}

		// Configure the preserve semicolons parameter.
		final Object preserveSemicolons = getInitParameter(PARAMETER_PRESERVE_SEMICOLONS);
		if (preserveSemicolons == null) {
			connection.put(PARAMETER_PRESERVE_SEMICOLONS, Boolean.FALSE);
		} else if (preserveSemicolons instanceof Boolean) {
			connection.put(PARAMETER_PRESERVE_SEMICOLONS, (Boolean) preserveSemicolons);
		} else {
			connection.put(PARAMETER_PRESERVE_SEMICOLONS, Boolean.valueOf((String) preserveSemicolons));
		}

		// Configure the enable optimizations parameter.
		final Object disableOptimizations = getInitParameter(PARAMETER_DISABLE_OPTIMIZATIONS);
		if (disableOptimizations == null) {
			connection.put(PARAMETER_DISABLE_OPTIMIZATIONS, Boolean.FALSE);
		} else if (disableOptimizations instanceof Boolean) {
			connection.put(PARAMETER_DISABLE_OPTIMIZATIONS, (Boolean) disableOptimizations);
		} else {
			connection.put(PARAMETER_DISABLE_OPTIMIZATIONS, Boolean.valueOf((String) disableOptimizations));
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

		// Validate the value of the PARAMETER_OBFUSCATE.
		final Object obfuscate = getInitParameter(PARAMETER_OBFUSCATE);
		if (obfuscate != null) {
			if (obfuscate instanceof String) {
				try {
					Boolean.valueOf((String) obfuscate);
				} catch (final Exception e) {
					throw new ConnectorException(getScopeFacade(),
							"WAREWORK cannot initialize Converter '" + getClientName() + "' at Service '"
									+ getService().getName() + "' because parameter '" + PARAMETER_OBFUSCATE
									+ "' is not a valid boolean value.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}
			} else if (!(obfuscate instanceof Boolean)) {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize Converter '" + getClientName() + "' at Service '"
								+ getService().getName() + "' because parameter '" + PARAMETER_OBFUSCATE
								+ "' is not a string or a boolean value.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}
		}

		// Validate the value of the PARAMETER_PRESERVE_SEMICOLONS.
		final Object preserveSemicolons = getInitParameter(PARAMETER_PRESERVE_SEMICOLONS);
		if (preserveSemicolons != null) {
			if (preserveSemicolons instanceof String) {
				try {
					Boolean.valueOf((String) preserveSemicolons);
				} catch (final Exception e) {
					throw new ConnectorException(getScopeFacade(),
							"WAREWORK cannot initialize Converter '" + getClientName() + "' at Service '"
									+ getService().getName() + "' because parameter '" + PARAMETER_PRESERVE_SEMICOLONS
									+ "' is not a valid boolean value.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}
			} else if (!(preserveSemicolons instanceof Boolean)) {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize Converter '" + getClientName() + "' at Service '"
								+ getService().getName() + "' because parameter '" + PARAMETER_PRESERVE_SEMICOLONS
								+ "' is not a string or a boolean value.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}
		}

		// Validate the value of the PARAMETER_DISABLE_OPTIMIZATIONS.
		final Object disableOptimizations = getInitParameter(PARAMETER_DISABLE_OPTIMIZATIONS);
		if (disableOptimizations != null) {
			if (disableOptimizations instanceof String) {
				try {
					Boolean.valueOf((String) disableOptimizations);
				} catch (final Exception e) {
					throw new ConnectorException(getScopeFacade(),
							"WAREWORK cannot initialize Converter '" + getClientName() + "' at Service '"
									+ getService().getName() + "' because parameter '" + PARAMETER_DISABLE_OPTIMIZATIONS
									+ "' is not a valid boolean value.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}
			} else if (!(disableOptimizations instanceof Boolean)) {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize Converter '" + getClientName() + "' at Service '"
								+ getService().getName() + "' because parameter '" + PARAMETER_DISABLE_OPTIMIZATIONS
								+ "' is not a string or a boolean value.",
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
