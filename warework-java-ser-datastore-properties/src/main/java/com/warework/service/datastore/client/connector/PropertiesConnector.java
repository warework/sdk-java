package com.warework.service.datastore.client.connector;

import java.io.InputStream;
import java.util.Properties;

import com.warework.core.loader.ResourceLoader;
import com.warework.core.loader.LoaderException;
import com.warework.core.scope.ScopeL1Constants;
import com.warework.core.service.client.connector.ConnectorException;
import com.warework.service.datastore.client.PropertiesDatastore;
import com.warework.service.log.LogServiceConstants;

/**
 * Connector that holds the information required to load properties files.<br>
 * <br>
 * To add a Properties Data Store into the Data Store Service you have to invoke
 * method createClient() that exists in its Facade with a name, the Properties
 * Connector class and a configuration for the Data Store. You can use just one
 * type of Connector:<br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Create&nbsp;the&nbsp;configuration&nbsp;for&nbsp;the&nbsp;Data&nbsp;Store.<br>Map&lt;String, Object&gt;&nbsp;config&nbsp;=&nbsp;new&nbsp;HashMap&lt;String, Object&gt;();<br><br>&#47;&#47;&nbsp;Set&nbsp;the&nbsp;location&nbsp;of&nbsp;the&nbsp;properties&nbsp;file.<br>config.put(PropertiesConnector.PARAMETER_CONFIG_TARGET,&nbsp;<br>&nbsp;&nbsp;&nbsp;&quot;&#47;META-INF&#47;config.properties&quot;);<br><br>&#47;&#47;&nbsp;Create&nbsp;the&nbsp;Properties&nbsp;Data&nbsp;Store.<br>datastoreService.createClient(&quot;properties-datastore&quot;,<br>&nbsp;&nbsp;&nbsp;PropertiesConnector.class,&nbsp;config);</code>
 * <br>
 * <br>
 * The PropertiesConnector collects the configuration and sets up a Properties
 * Data Store with the properties file that exists where <a target="blank" HREF=
 * "http://api.warework.com/java/warework-java-core/2.0.0/com/warework/core/service/client/connector/AbstractConnector.html#PARAMETER_CONFIG_TARGET"
 * >PARAMETER_CONFIG_TARGET</a> specifies. Check it now how to do it with the
 * Data Store Service XML configuration file (this functionality is provided by
 * <a target="blank" HREF=
 * "http://warework.com/download/detail.page?lib=java-mod-datastore-ext"
 * >Warework Data Store Extension Module</a>):<br>
 * <br>
 * <br>
 * <code>&lt;?xml&nbsp;version=&quot;1.0&quot;&nbsp;encoding=&quot;UTF-8&quot;?&gt;<br>&lt;datastore-service&nbsp;xmlns:xsi=&quot;http://www.w3.org&#47;2001&#47;XMLSchema-instance&quot;<br>&nbsp;&nbsp;&nbsp;xsi:noNamespaceSchemaLocation=&quot;http://repository.warework.com&#47;xsd&#47;datastore<br>&nbsp;&nbsp;&nbsp;-service-1.1.0.xsd&quot;&gt;<br><br>&nbsp;&nbsp;&nbsp;&lt;datastores&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;datastore&nbsp;name=&quot;properties-datastore&quot;&nbsp;connector=<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&quot;com.warework.service.datastore.client.connector.PropertiesConnector&quot;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;config-target&quot;&nbsp;value=&quot;&#47;META-INF&#47;config.properties&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;datastore&gt;<br>&nbsp;&nbsp;&nbsp;&lt;&#47;datastores&gt;<br><br>&lt;&#47;datastore-service&gt;</code>
 * <br>
 * <br>
 * For those who need to load a properties file from a different JAR, remember
 * that you can do so by adding parameter PropertiesConnector.<a href=
 * "http://api.warework.com/java/warework-java-core/1.1.1/com/warework/core/service/client/connector/AbstractConnector.html#PARAMETER_CONTEXT_LOADER"
 * >PARAMETER_CONTEXT_LOADER</a>.<br>
 * <br>
 * <br>
 * <b><u>Minimum prerequisites to run this Connector</u>:</b><br>
 * <ul>
 * <li><b>Runtime:</b> Java 1.5</li>
 * </ul>
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public final class PropertiesConnector extends AbstractDatastoreConnector {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Initialization parameter that specifies where to load resources. If this
	 * parameter references a string object, then the Framework performs one of
	 * these actions (just one, in this order): (a) searchs for the
	 * <code>PARAMETER_CONTEXT_LOADER</code> parameter in the Connector
	 * configuration and, if it exists, the Framework extracts the corresponding
	 * class/object and uses it to get the resource. (b) searchs for the
	 * <code>PARAMETER_CONTEXT_LOADER</code> parameter in the Scope where the
	 * Connector and extracts the corresponding class to finally retrieve the
	 * resource with it. (c) return <code>null</code>.
	 */
	public static final String PARAMETER_CONFIG_TARGET = ScopeL1Constants.PARAMETER_CONFIG_TARGET;

	/**
	 * Initialization parameter that specifies the class that should be used to load
	 * resources. The value of this parameter usually is the name of a class that
	 * exists in your project, so the Warework Framework can read resources from it.
	 */
	public static final String PARAMETER_CONTEXT_LOADER = ScopeL1Constants.PARAMETER_CONTEXT_LOADER;

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the type of the Data Store.
	 * 
	 * @return A
	 *         <code>com.warework.service.datastore.client.PropertiesDataStore</code>
	 *         Client.<br>
	 *         <br>
	 */
	public Class<PropertiesDatastore> getClientType() {
		return PropertiesDatastore.class;
	}

	/**
	 * Gets a properties file.
	 * 
	 * @return A <code>java.util.Properties</code> instance.<br>
	 *         <br>
	 * @throws ConnectorException If there is an error when trying to retrieve a
	 *                            connection for the Data Store.<br>
	 *                            <br>
	 */
	public Object getClientConnection() throws ConnectorException {

		// Create the properties.
		final Properties connection = new Properties();
		try {

			// Get the stream that points to the properties file.
			final InputStream input = (InputStream) getConnectionSource();

			// Load the properties file into the collection.
			connection.load(input);

			// Close the stream.
			input.close();

		} catch (final Exception e) {
			throw new ConnectorException(getScopeFacade(),
					"WAREWORK cannot create the connection for Data Store '" + getClientName()
							+ "' because the InputStream that points to the properties file cannot be loaded into a '"
							+ Properties.class.getName() + "' object.",
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Return the properties file.
		return connection;

	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates a <code>java.io.InputStream</code> that points to the properties
	 * file.
	 * 
	 * @return A <code>java.io.InputStream</code>.<br>
	 *         <br>
	 * @throws ConnectorException If there is an error when trying to create the
	 *                            connection source for the Data Store.<br>
	 *                            <br>
	 */
	protected Object createConnectionSource() throws ConnectorException {
		try {
			return new ResourceLoader(getScopeFacade()).getResource(getInitParameter(PARAMETER_CONTEXT_LOADER),
					getInitParameter(PARAMETER_CONFIG_TARGET));
		} catch (final LoaderException e) {
			throw new ConnectorException(getScopeFacade(),
					"WAREWORK cannot create the connection for Data Store '" + getClientName()
							+ "' because the following exception is thrown when trying to find the resource: "
							+ e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}
	}

	/**
	 * Specifies if the connection source should be created and stored on start up.
	 * This method always returns <code>false</code>.
	 * 
	 * @return Always <code>false</code>.<br>
	 *         <br>
	 */
	protected boolean cacheConnectionSource() {
		return false;
	}

}
