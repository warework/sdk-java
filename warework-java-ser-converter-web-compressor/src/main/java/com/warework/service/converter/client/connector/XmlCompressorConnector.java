package com.warework.service.converter.client.connector;

import com.googlecode.htmlcompressor.compressor.XmlCompressor;
import com.warework.core.service.client.connector.ConnectorException;
import com.warework.service.log.LogServiceConstants;

/**
 * This Connector creates a Converter to compress XML.<br>
 * <br>
 * <span>To add a</span><span>n XML compressor into the Converter Service you
 * have to invoke method </span><span>createClient() </span><span>that exists in
 * its Facade with a name, the XML compressor Connector class and optionally, a
 * configuration for the compressor. If you plan to use the XML compressor with
 * the default configuration, then you can use
 * </span><span>XMLCompressorConnector</span><span> as follows:</span><br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Create&nbsp;the&nbsp;XML&nbsp;compressor.<br>converterService.createClient(&quot;xml-compressor&quot;,<br>&nbsp;&nbsp;&nbsp;XMLCompressorConnector.class,&nbsp;null);</code>
 * <br>
 * <br>
 * It is also possible to adjust the configuration of the compressor with the
 * following parameters:<br>
 * <br>
 * <ul class="t0">
 * <li><span>PARAMETER_REMOVE_COMMENTS</span>: specifies if the Converter has to
 * remove the XML comments. Accepted values for this parameter are
 * <span>true</span> or <span>false</span> (as <span>java.lang.String</span> or
 * <span>java.lang.Boolean</span> objects). This parameter is optional. Default
 * value is <span>true</span>.<br>
 * </li>
 * </ul>
 * <br>
 * <ul class="t0">
 * <li><span>PARAMETER_REMOVE_INTERTAG_SPACES</span><span>: specifies if the
 * Converter has to remove iter-tag whitespace characters. Accepted values for
 * this parameter are </span><span>true</span><span> or
 * </span><span>false</span><span> (as
 * </span><span>java.lang.String</span><span> or
 * </span><span>java.lang.Boolean</span><span> objects). This parameter is
 * optional. Default value is </span><span>true</span><span>.</span><br>
 * </li>
 * </ul>
 * <br>
 * <span>To configure this compressor with parameters, use a
 * </span><span>java.util.Map</span><span> like this:</span><br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Create&nbsp;the&nbsp;configuration&nbsp;for&nbsp;the&nbsp;compressor.<br>Map&lt;String, Object&gt;&nbsp;config&nbsp;=&nbsp;new&nbsp;HashMap&lt;String, Object&gt;();<br><br>&#47;&#47;&nbsp;Configure&nbsp;the&nbsp;compressor.<br>config.put(XMLCompressorConnector.PARAMETER_REMOVE_COMMENTS,&nbsp;<br>&nbsp;&nbsp;&nbsp;Boolean.TRUE);<br>config.put(XMLCompressorConnector.PARAMETER_REMOVE_INTERTAG_SPACES,&nbsp;<br>&nbsp;&nbsp;&nbsp;Boolean.TRUE);<br><br>&#47;&#47;&nbsp;Create&nbsp;the&nbsp;XML&nbsp;compressor.<br>converterService.createClient(&quot;xml-compressor&quot;,&nbsp;XMLCompressorConnector.class,&nbsp;<br>&nbsp;&nbsp;&nbsp;config);</code>
 * <br>
 * <br>
 * <span>Check </span><span>it now how to do it with the Proxy Service XML
 * configuration file:</span><br>
 * <br>
 * <code>&lt;?xml&nbsp;version=&quot;1.0&quot;&nbsp;encoding=&quot;UTF-8&quot;?&gt;<br>&lt;proxy-service&nbsp;xmlns:xsi=&quot;http://www.w3.org&#47;2001&#47;XMLSchema-instance&quot;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;xsi:noNamespaceSchemaLocation=&quot;http://repository.warework.com&#47;xsd&#47;proxy-<br>&nbsp;&nbsp;&nbsp;service-1.0.0.xsd&quot;&gt;<br><br>&nbsp;&nbsp;&nbsp;&lt;clients&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;client&nbsp;name=&quot;xml-compressor&quot;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;connector=&quot;com.warework.service.converter.client.connector.&nbsp;&hellip;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&hellip;&nbsp;XMLCompressorConnector&quot;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;remove-comments&quot;&nbsp;value=&quot;true&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;remove-intertag-spaces&quot;&nbsp;value=&quot;true&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;client&gt;<br>&nbsp;&nbsp;&nbsp;&lt;&#47;clients&gt;<br><br>&lt;&#47;proxy-service&gt;</code>
 * <br>
 * <br>
 * <b><u>Minimum prerequisites to run this Connector</u>:</b><br>
 * <br>
 * <ul>
 * <li><b>Runtime:</b> Java 1.5</li>
 * </ul>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public final class XmlCompressorConnector extends AbstractConverterConnector {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// PARAMETERS

	/**
	 * Initialization parameter that specifies if the Converter has to remove the
	 * XML comments. Accepted values for this parameter are <code>true</code> or
	 * <code>false</code> (as <code>java.lang.String</code> or
	 * <code>java.lang.Boolean</code> objects). This parameter is optional. Default
	 * value is <code>true</code>.
	 */
	public final static String PARAMETER_REMOVE_COMMENTS = "remove-comments";

	/**
	 * Initialization parameter that specifies if the Converter has to remove
	 * iter-tag whitespace characters. Accepted values for this parameter are
	 * <code>true</code> or <code>false</code> (as <code>java.lang.String</code> or
	 * <code>java.lang.Boolean</code> objects). This parameter is optional. Default
	 * value is <code>true</code>.
	 */
	public final static String PARAMETER_REMOVE_INTERTAG_SPACES = "remove-intertag-spaces";

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the type of the Converter.
	 * 
	 * @return A <code>com.warework.service.converter.client.XMLCompressor</code>
	 *         Client.<br>
	 *         <br>
	 */
	public Class<com.warework.service.converter.client.XmlCompressor> getClientType() {
		return com.warework.service.converter.client.XmlCompressor.class;
	}

	/**
	 * Gets the object that performs the XML conversion.
	 * 
	 * @return A <code>com.googlecode.htmlcompressor.compressor.XmlCompressor</code>
	 *         instance.<br>
	 *         <br>
	 */
	public Object getClientConnection() throws ConnectorException {

		// Create the object that performs the XML conversion.
		final XmlCompressor connection = new XmlCompressor();

		// Configure PARAMETER_REMOVE_COMMENTS.
		final Object removeComments = getInitParameter(PARAMETER_REMOVE_COMMENTS);
		if (removeComments == null) {
			connection.setRemoveComments(true);
		} else if (removeComments instanceof Boolean) {
			connection.setRemoveComments((Boolean) removeComments);
		} else {
			connection.setRemoveComments(Boolean.valueOf((String) removeComments));
		}

		// Configure PARAMETER_REMOVE_INTERTAG_SPACES.
		final Object removeIntertagSpaces = getInitParameter(PARAMETER_REMOVE_INTERTAG_SPACES);
		if (removeIntertagSpaces == null) {
			connection.setRemoveIntertagSpaces(true);
		} else if (removeIntertagSpaces instanceof Boolean) {
			connection.setRemoveIntertagSpaces((Boolean) removeIntertagSpaces);
		} else {
			connection.setRemoveIntertagSpaces(Boolean.valueOf((String) removeIntertagSpaces));
		}

		// Return connection.
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

		// Validate the value of the PARAMETER_REMOVE_COMMENTS.
		final Object removeComments = getInitParameter(PARAMETER_REMOVE_COMMENTS);
		if (removeComments != null) {
			if (removeComments instanceof String) {
				try {
					Boolean.valueOf((String) removeComments);
				} catch (final Exception e) {
					throw new ConnectorException(getScopeFacade(),
							"WAREWORK cannot initialize Converter '" + getClientName() + "' at Service '"
									+ getService().getName() + "' because parameter '" + PARAMETER_REMOVE_COMMENTS
									+ "' is not a valid boolean value.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}
			} else if (!(removeComments instanceof Boolean)) {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize Converter '" + getClientName() + "' at Service '"
								+ getService().getName() + "' because parameter '" + PARAMETER_REMOVE_COMMENTS
								+ "' is not a string or a boolean value.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}
		}

		// Validate the value of the PARAMETER_REMOVE_INTERTAG_SPACES.
		final Object removeIntertagSpaces = getInitParameter(PARAMETER_REMOVE_INTERTAG_SPACES);
		if (removeIntertagSpaces != null) {
			if (removeIntertagSpaces instanceof String) {
				try {
					Boolean.valueOf((String) removeIntertagSpaces);
				} catch (final Exception e) {
					throw new ConnectorException(getScopeFacade(),
							"WAREWORK cannot initialize Converter '" + getClientName() + "' at Service '"
									+ getService().getName() + "' because parameter '"
									+ PARAMETER_REMOVE_INTERTAG_SPACES + "' is not a valid boolean value.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}
			} else if (!(removeIntertagSpaces instanceof Boolean)) {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot initialize Converter '" + getClientName() + "' at Service '"
								+ getService().getName() + "' because parameter '" + PARAMETER_REMOVE_INTERTAG_SPACES
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
