package com.warework.service.file.client.connector;

import com.warework.core.service.client.connector.ConnectorException;
import com.warework.service.file.client.LocalFileSystemClient;
import com.warework.service.log.LogServiceConstants;

/**
 * This Connector holds the information required to create Local File System
 * Clients.<br>
 * <br>
 * To add the Local File System Client into the File Service you have to invoke
 * method createClient() that exists in its Facade with a name and a Local File
 * System Connector class.<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Create&nbsp;the&nbsp;Local&nbsp;File&nbsp;System&nbsp;Client.<br>fileService.createClient(&quot;local-fs&quot;,&nbsp;LocalFileSystemConnector.class,&nbsp;<br>&nbsp;&nbsp;&nbsp;null);</code>
 * <br>
 * <br>
 * Check it now how to do it with the Proxy Service XML configuration file:<br>
 * <br>
 * <br>
 * <code>&lt;?xml&nbsp;version=&quot;1.0&quot;&nbsp;encoding=&quot;UTF-8&quot;?&gt;<br>&lt;proxy-service&nbsp;xmlns:xsi=&quot;http://www.w3.org&#47;2001&#47;XMLSchema-instance&quot;<br>&nbsp;&nbsp;&nbsp;xsi:noNamespaceSchemaLocation=&quot;http://repository.warework.com&#47;xsd&#47;proxy-<br>&nbsp;&nbsp;&nbsp;service-1.0.0.xsd&quot;&gt;<br><br>&nbsp;&nbsp;&nbsp;&lt;clients&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;client&nbsp;name=&quot;local-fs&quot;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;connector=&quot;com.warework.service.file.client.connector.&nbsp;&hellip;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&hellip;&nbsp;LocalFileSystemConnector&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&lt;&#47;clients&gt;<br><br>&lt;&#47;proxy-service&gt;</code>
 * <br>
 * <br>
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public final class LocalFileSystemConnector extends AbstractFileClientConnector {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Initialization parameter that specifies if given paths for files and
	 * directories should be transformed to follow Unix style convention.<br>
	 * <br>
	 * Unix style convention mandates that directories character separator must be
	 * '/', for example: '/my-dir'. If you want to use this convention in every
	 * operation with this Client you need to set this parameter to
	 * <code>true</code>.<br>
	 * By setting this parameter to <code>true</code>, every given path will be
	 * automatically converted and '\' characters will be replaced with '/'
	 * character. Also output path in requested list will be updated.<br>
	 * <br>
	 * <b>IMPORTANT</b>: Value specified with <code>PARAMETER_BASE_PATH</code> will
	 * also be updated if this parameter is set to <code>true</code>.<br>
	 * <br>
	 * This parameter is optional and by default it's <code>false</code>. Accepted
	 * values for this parameter are <code>true</code> or <code>false</code> (as
	 * <code>java.lang.String</code> or <code>java.lang.Boolean</code> objects).
	 */
	public static final String PARAMETER_PATH_TRANSFORM = "path-transform";

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the type of the File Client.
	 * 
	 * @return A <code>com.warework.service.file.client.LocalFileSystemClient</code>
	 *         .<br>
	 *         <br>
	 */
	public Class<LocalFileSystemClient> getClientType() {
		return LocalFileSystemClient.class;
	}

	/**
	 * Gets a connection with the Client.
	 * 
	 * @return A dummy object.<br>
	 *         <br>
	 */
	public Object getClientConnection() {
		return Boolean.TRUE;
	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Validates the initialization parameters.
	 * 
	 * @throws ConnectorException If there is an error when trying to initialize the
	 *                            Connector.<br>
	 *                            <br>
	 */
	protected void initialize() throws ConnectorException {

		// Initialize parent.
		super.initialize();

		// Get the value of the path transform parameter.
		final Object pathTransform = getInitParameter(PARAMETER_PATH_TRANSFORM);
		if ((pathTransform != null) && (!(pathTransform instanceof String)) && (!(pathTransform instanceof Boolean))) {
			throw new ConnectorException(getScopeFacade(),
					"WAREWORK cannot initialize File Client '" + getClientName() + "' in Service '"
							+ getService().getName() + "' because given '" + PARAMETER_PATH_TRANSFORM
							+ "' parameter is not a String or a Boolean object.",
					null, LogServiceConstants.LOG_LEVEL_WARN);
		}

	}

	/**
	 * Retrieves the connection source.
	 * 
	 * @return Always <code>null</code>.<br>
	 *         <br>
	 */
	protected Object createConnectionSource() {
		return null;
	}

}
