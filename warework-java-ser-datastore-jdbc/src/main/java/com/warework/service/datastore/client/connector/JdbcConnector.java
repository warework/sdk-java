package com.warework.service.datastore.client.connector;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.warework.core.callback.AbstractBaseCallback;
import com.warework.core.service.client.connector.ConnectorException;
import com.warework.service.datastore.client.JdbcDatastore;
import com.warework.service.log.LogServiceConstants;

/**
 * <span>To add a </span><span>JDBC Data Store into the Data Store Service you
 * have to invoke method </span><span>createClient() </span><span>that exists in
 * its Facade with a name, the </span><span>JdbcConnector</span><span> class and
 * a configuration for the Data Store. </span> <br>
 * <br>
 * <span>This configuration has</span><span> to specify a Provider where to
 * retrieve a </span><span>javax.sql.DataSource</span><span> or a
 * </span><span>java.sql.Connection</span><span> object (the first one store the
 * information required to create a connection with the database and the second
 * represents the connection itself). A Provider configured for the
 * </span><span>JdbcConnector</span><span> typically will return a
 * </span><span>DataSource</span><span> object in a server platform (for
 * example: you can get a </span><span>DataSource</span><span> object from a
 * </span><a target="blank" HREF=
 * "http://warework.com/download/detail.page?lib=java-pro-jndi"><span>JNDI
 * Provider</span></a><span> which is configured to work in a WebLogic
 * Application Server) and a </span><span>Connection</span><span> object in a
 * standalone application (in most cases, it is a good idea to get
 * </span><span>Connection</span><span> objects with the
 * </span><a target="blank" HREF=
 * "http://warework.com/download/detail.page?lib=java-ser-pool" ><span>Warework
 * Pool Service</span></a><span>; you can use for example a
 * </span><a target="blank" HREF=
 * "http://warework.com/download/detail.page?lib=java-ser-pool-c3p0" ><span>c3p0
 * Pooler</span></a><span>/Client in this Service to define and create your
 * database connections using a pool of </span><span>Connection</span><span>
 * objects).</span><br>
 * <br>
 * <span>If you can get an instance of a </span><span>DataSource</span><span>
 * from a Provider, then you should configure this Data Store like
 * this:</span><br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Create&nbsp;the&nbsp;configuration&nbsp;for&nbsp;the&nbsp;Data&nbsp;Store.<br>Map&lt;String,&nbsp;Object&gt;&nbsp;config&nbsp;=&nbsp;new&nbsp;HashMap&lt;String,&nbsp;Object&gt;();<br><br>&#47;&#47;&nbsp;Set&nbsp;the&nbsp;name&nbsp;of&nbsp;the&nbsp;Provider&nbsp;where&nbsp;to&nbsp;retrieve&nbsp;the&nbsp;DataSource&nbsp;object.<br>config.put(JdbcConnector.PARAMETER_CONNECTION_SOURCE_PROVIDER_NAME,&nbsp;<br>&nbsp;&nbsp;&nbsp;&quot;datasource-provider&quot;);<br><br>&#47;&#47;&nbsp;Set&nbsp;the&nbsp;name&nbsp;of&nbsp;the&nbsp;object&nbsp;that&nbsp;represents&nbsp;the&nbsp;DataSource&nbsp;in&nbsp;the&nbsp;Provider.<br>config.put(JdbcConnector.PARAMETER_CONNECTION_SOURCE_PROVIDER_OBJECT,&nbsp;<br>&nbsp;&nbsp;&nbsp;&quot;datasource-object-name&quot;);<br><br>&#47;&#47;&nbsp;Create&nbsp;the&nbsp;JDBC&nbsp;Data&nbsp;Store.<br>datastoreService.createClient(&quot;jdbc-datastore&quot;,&nbsp;JdbcConnector.class,&nbsp;config);</code>
 * <br>
 * <br>
 * <span>With this code we instruct the </span><span>JDBC Connector to retrieve
 * a </span><span>DataSource</span><span> object named
 * </span><span>datasource-object-name</span><span> (if it is a JNDI name, it
 * may look like </span><span>jdbc/myApplicationDS</span><span>) from Provider
 * </span><span>datasource-provider</span><span> (in a server platform, this
 * Provider usually is a JNDI Provider).</span><br>
 * <br>
 * <span>Check it now how to do it with the Data Store Service XML configuration
 * file (this fun</span><span>ctionality is provided by
 * </span><a target="blank" HREF
 * ="http://warework.com/download/detail.page?lib=java-mod-datastore-ext">
 * <span>Warework Data Store Extension Module</span></a><span>):</span><br>
 * <br>
 * <br>
 * <code>&lt;?xml&nbsp;version=&quot;1.0&quot;&nbsp;encoding=&quot;UTF-8&quot;?&gt;<br>&lt;datastore-service&nbsp;xmlns:xsi=&quot;http://www.w3.org&#47;2001&#47;XMLSchema-instance&quot;<br>&nbsp;&nbsp;&nbsp;xsi:noNamespaceSchemaLocation=&quot;http://repository.warework.com&#47;xsd&#47;datastore<br>&nbsp;&nbsp;&nbsp;-service-1.2.0.xsd&quot;&gt;<br><br>&nbsp;&nbsp;&nbsp;&lt;datastores&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;datastore&nbsp;name=&quot;jdbc-datastore&quot;&nbsp;connector=<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&quot;com.warework.service.datastore.client.connector.JdbcConnector&quot;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameters&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;connection-source-provider-name&quot;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;value=&quot;datasource-provider&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;connection-source-provider-object&quot;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;value=&quot;datasource-object-name&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;parameters&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;datastore&gt;<br>&nbsp;&nbsp;&nbsp;&lt;&#47;datastores&gt;<br><br>&lt;&#47;datastore-service&gt;</code>
 * <br>
 * <br>
 * <span>If you can get an instance of a </span><span>Connection</span><span>
 * object from a Provider, then you should configure the JDBC Data Store like
 * this:</span><br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Create&nbsp;the&nbsp;configuration&nbsp;for&nbsp;the&nbsp;Data&nbsp;Store.<br>Map&lt;String,&nbsp;Object&gt;&nbsp;config&nbsp;=&nbsp;new&nbsp;HashMap&lt;String,&nbsp;Object&gt;();<br><br>&#47;&#47;&nbsp;Set&nbsp;the&nbsp;name&nbsp;of&nbsp;the&nbsp;Provider&nbsp;where&nbsp;to&nbsp;retrieve&nbsp;the&nbsp;Connection&nbsp;object.<br>config.put(JdbcConnector.PARAMETER_CLIENT_CONNECTION_PROVIDER_NAME,&nbsp;<br>&nbsp;&nbsp;&nbsp;&quot;connection-provider&quot;);<br><br>&#47;&#47;&nbsp;Set&nbsp;the&nbsp;name&nbsp;of&nbsp;the&nbsp;object&nbsp;that&nbsp;represents&nbsp;the&nbsp;Connection&nbsp;in&nbsp;the&nbsp;Provider.<br>config.put(JdbcConnector.PARAMETER_CLIENT_CONNECTION_PROVIDER_OBJECT,&nbsp;<br>&nbsp;&nbsp;&nbsp;&quot;connection-object-name&quot;);<br><br>&#47;&#47;&nbsp;Create&nbsp;the&nbsp;JDBC&nbsp;Data&nbsp;Store.<br>datastoreService.createClient(&quot;jdbc-datastore&quot;,&nbsp;JdbcConnector.class,&nbsp;config);</code>
 * <br>
 * <br>
 * <span>This time</span><span> we instruct the JDBC Connector to get from
 * Provider </span><span>connection-provider</span><span> (in a stand-alone
 * application, this Provider usually is the Pooled Object Provider which
 * retrieves object instances from the Pool Service; this Provider is bundled
 * within the Pool Service) a </span><span>java.sql.Connection</span><span>
 * object named </span><span>connection-object-name</span><span> (in a Pooled
 * Object Provider it is the name of the Client/Pooler where to retrieve the
 * object instance).</span><br>
 * <br>
 * Check it now how to do it with the Data Store Service XML configuration
 * file:<br>
 * <br>
 * <br>
 * <code>&lt;?xml&nbsp;version=&quot;1.0&quot;&nbsp;encoding=&quot;UTF-8&quot;?&gt;<br>&lt;datastore-service&nbsp;xmlns:xsi=&quot;http://www.w3.org&#47;2001&#47;XMLSchema-instance&quot;<br>&nbsp;&nbsp;&nbsp;xsi:noNamespaceSchemaLocation=&quot;http://repository.warework.com&#47;xsd&#47;datastore<br>&nbsp;&nbsp;&nbsp;-service-1.2.0.xsd&quot;&gt;<br><br>&nbsp;&nbsp;&nbsp;&lt;datastores&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;datastore&nbsp;name=&quot;jdbc-datastore&quot;&nbsp;connector=<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&quot;com.warework.service.datastore.client.connector.JdbcConnector&quot;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameters&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;client-connection-provider-name&quot;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;value=&quot;connection-provider&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;parameter&nbsp;name=&quot;client-connection-provider-object&quot;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;value=&quot;connection-object-name&quot;&#47;&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;parameters&gt;<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;&#47;datastore&gt;<br>&nbsp;&nbsp;&nbsp;&lt;&#47;datastores&gt;<br><br>&lt;&#47;datastore-service&gt;</code>
 * <br>
 * <br>
 * <span>There is another option for those who plan to create the
 * </span><span>DataSource</span><span> programmatically: the
 * </span><span>PARAMETER_DATA_SOURCE</span><span> parameter. It allows you to
 * specify for the Connector a </span><span>DataSource</span><span> object
 * created by yourself. Check this out with the following code:</span><br>
 * <br>
 * <br>
 * <code>&#47;&#47;&nbsp;Create&nbsp;an&nbsp;instance&nbsp;of&nbsp;a&nbsp;Data&nbsp;Source.<br>DataSource&nbsp;myDataSource&nbsp;=&nbsp;...;<br><br>&#47;&#47;&nbsp;Create&nbsp;the&nbsp;configuration&nbsp;for&nbsp;the&nbsp;Data&nbsp;Store.<br>Map&lt;String,&nbsp;Object&gt;&nbsp;config&nbsp;=&nbsp;new&nbsp;HashMap&lt;String,&nbsp;Object&gt;();<br><br>&#47;&#47;&nbsp;Set&nbsp;the&nbsp;Data&nbsp;Source&nbsp;to&nbsp;use.<br>config.put(JdbcConnector.PARAMETER_DATA_SOURCE,&nbsp;myDataSource);<br><br>&#47;&#47;&nbsp;Create&nbsp;the&nbsp;JDBC&nbsp;Data&nbsp;Store.<br>datastoreService.createClient(&quot;jdbc-datastore&quot;,&nbsp;JdbcConnector.class,&nbsp;config);</code>
 * <br>
 * <br>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public final class JdbcConnector extends AbstractDatastoreConnector {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Initialization parameter that specifies a string that represents the name of
	 * the Provider where to load an object. This object retrieved from the Provider
	 * will be used as the <u>Client connection</u> of the Connector. You must use
	 * this parameter with <code>PARAMETER_CLIENT_CONNECTION_PROVIDER_OBJECT</code>
	 * parameter in order to make it work.
	 */
	public static final String PARAMETER_CLIENT_CONNECTION_PROVIDER_NAME = "client-connection-provider-name";

	/**
	 * Initialization parameter that specifies a string that represents the name of
	 * an object in a Provider that will be used as the <u>Client connection</u> of
	 * the Connector. You must use this parameter with
	 * <code>PARAMETER_CLIENT_CONNECTION_PROVIDER_NAME</code> parameter in order to
	 * make it work.
	 */
	public static final String PARAMETER_CLIENT_CONNECTION_PROVIDER_OBJECT = "client-connection-provider-object";

	/**
	 * Initialization parameter that specifies a string that represents the name of
	 * the Provider where to load an object. This object retrieved from the Provider
	 * will be used as the <u>connection source</u> of the Connector. You must use
	 * this parameter with <code>PARAMETER_CONNECTION_SOURCE_PROVIDER_OBJECT</code>
	 * parameter in order to make it work.
	 */
	public static final String PARAMETER_CONNECTION_SOURCE_PROVIDER_NAME = "connection-source-provider-name";

	/**
	 * Initialization parameter that specifies a string that represents the name of
	 * an object in a Provider that will be used as the <u>connection source</u> of
	 * the Connector. You must use this parameter with
	 * <code>PARAMETER_CONNECTION_SOURCE_PROVIDER_NAME</code> parameter in order to
	 * make it work.
	 */
	public static final String PARAMETER_CONNECTION_SOURCE_PROVIDER_OBJECT = "connection-source-provider-object";

	/**
	 * Initialization parameter that specifies a <code>javax.sql.DataSource</code>
	 * object that holds the information required to connect with the relational
	 * database.
	 */
	public static final String PARAMETER_DATA_SOURCE = "data-source";

	/**
	 * Initialization parameter that specifies if the commit operation must be
	 * executed automatically. This is an optional parameter. Accepted values for
	 * this parameter are <code>true</code> or <code>false</code> (as
	 * <code>java.lang.String</code> or <code>java.lang.Boolean</code> objects). If
	 * this parameter is not specified then the value for it is <code>false</code>.
	 */
	public static final String PARAMETER_AUTO_COMMIT = "auto-commit";

	/**
	 * Initialization parameter that specifies the object to return on queries. By
	 * default, you will always get an instance of
	 * <code>com.warework.service.datastore.client.ResultRows</code> with a JDBC
	 * Data Store when you run a query. Set this parameter to <code>true</code> (as
	 * <code>java.lang.String</code> or <code>java.lang.Boolean</code> objects) to
	 * configure this Data Store to retrieve JDBC <code>java.sql.ResultSet</code>
	 * objects instead of Warework <code>ResultRows</code> objects.
	 */
	public static final String PARAMETER_NATIVE_RESULT_SET = "native-result-set";

	/**
	 * Initialization parameter that specifies the amount of milliseconds to wait
	 * until a callback operation is done. This parameter is typically used in
	 * methods of Clients that wait until an asynchronous operation is finished.
	 * Clients like these wait the time specified with this parameter until the
	 * callback method is executed. If operation takes too long and it is not
	 * invoked, the Client notifies about the situation and then throws an
	 * exception. This parameter accepts a numeric values (
	 * <code>java.lang.Long</code> or <code>java.lang.String</code>) to represent
	 * the milliseconds to wait. If this parameter is not specified then the
	 * Framework waits forever or until any other failure or exception is thrown.
	 */
	public static final String PARAMETER_MAX_CALLBACK_WAIT = AbstractBaseCallback.ATTRIBUTE_MAX_CALLBACK_WAIT;

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the type of the Data Store.
	 * 
	 * @return A <code>com.warework.service.datastore.client.JDBCDatastore</code>
	 *         Client.<br>
	 *         <br>
	 */
	public Class<JdbcDatastore> getClientType() {
		return JdbcDatastore.class;
	}

	/**
	 * Gets a connection for the database.
	 * 
	 * @return A <code>java.sql.Connection</code> instance.<br>
	 *         <br>
	 */
	public Object getClientConnection() throws ConnectorException {
		if ((getInitParameter(PARAMETER_CLIENT_CONNECTION_PROVIDER_NAME) != null)
				&& (getInitParameter(PARAMETER_CLIENT_CONNECTION_PROVIDER_OBJECT) != null)) {

			// Get the name of the Provider where to retrieve the connection.
			final Object providerName = getInitParameter(PARAMETER_CLIENT_CONNECTION_PROVIDER_NAME);

			// Get the name of the connection object bound to the Provider.
			final Object providerObject = getInitParameter(PARAMETER_CLIENT_CONNECTION_PROVIDER_OBJECT);

			// Validate that given parameters are strings and return the
			// connection.
			if ((providerName instanceof String) && (providerObject instanceof String)) {

				// Get the connection from the Provider.
				final Object connection = getScopeFacade().getObject((String) providerName, (String) providerObject);

				// Return the connection.
				if (connection instanceof Connection) {

					// Get the connection object.
					final Connection dddbbConnection = (Connection) connection;

					// Get the value for the auto-commit parameter.
					final boolean autocommit = isInitParameter(PARAMETER_AUTO_COMMIT);

					// Setup auto-commit.
					try {
						dddbbConnection.setAutoCommit(autocommit);
					} catch (final SQLException e) {
						throw new ConnectorException(getScopeFacade(),
								"WAREWORK cannot retrieve the connection for Data Store '" + getClientName()
										+ "' because auto-commit mode cannot be set to '" + autocommit
										+ "' in the connection. Check out this error: " + e.getMessage(),
								e, LogServiceConstants.LOG_LEVEL_WARN);
					}

					// Return the connection with the database.
					return dddbbConnection;

				} else {
					throw new ConnectorException(getScopeFacade(),
							"WAREWORK cannot retrieve the connection for Data Store '" + getClientName()
									+ "' because Provider '" + ((String) providerName) + "' does not returns a '"
									+ Connection.class.getName() + "' object for '" + ((String) providerObject)
									+ "' or it is null.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}

			} else {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot retrieve the connection for Data Store '" + getClientName()
								+ "' because one or both of the connector's parameters are not '"
								+ String.class.getName() + "' objects.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

		} else {

			// Get the data source.
			final DataSource dataSource = (DataSource) getConnectionSource();

			// Get connection with the database.
			Connection connection = null;
			if (dataSource != null) {
				try {
					connection = dataSource.getConnection();
				} catch (final SQLException e) {
					throw new ConnectorException(getScopeFacade(),
							"WAREWORK cannot create a connection for Data Store '" + getClientName()
									+ "' because the data source reported the following error: " + e.getMessage(),
							e, LogServiceConstants.LOG_LEVEL_WARN);
				}
			} else {
				throw new ConnectorException(getScopeFacade(), "WAREWORK cannot create a connection for Data Store '"
						+ getClientName() + "' because no data source is provided.", null,
						LogServiceConstants.LOG_LEVEL_WARN);
			}

			// Get the value for the auto-commit parameter.
			final Boolean autocommit = toBoolean(PARAMETER_AUTO_COMMIT);

			// Setup auto-commit.
			if (autocommit != null) {
				try {
					connection.setAutoCommit(autocommit);
				} catch (final SQLException e) {
					throw new ConnectorException(getScopeFacade(),
							"WAREWORK cannot retrieve the connection for Data Store '" + getClientName()
									+ "' because auto-commit mode cannot be set to '" + autocommit
									+ "' in the connection. Check out this error: " + e.getMessage(),
							e, LogServiceConstants.LOG_LEVEL_WARN);
				}
			}

			// Return the connection with the database.
			return connection;

		}
	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Retrieves a <code>javax.sql.DataSource</code> that holds the information
	 * required to connect with the database.
	 * 
	 * @return A <code>javax.sql.DataSource</code>.<br>
	 *         <br>
	 * @throws ConnectorException If there is an error when trying to create the
	 *                            connection source.<br>
	 *                            <br>
	 */
	protected Object createConnectionSource() throws ConnectorException {
		if (getInitParameter(PARAMETER_DATA_SOURCE) != null) {

			// Retrieve the data source.
			final Object dataSource = getInitParameter(PARAMETER_DATA_SOURCE);

			// Return the data source.
			if (dataSource instanceof DataSource) {
				return dataSource;
			} else {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot retrieve the data source for Data Store '" + getClientName()
								+ "' because given parameter is not a '" + DataSource.class.getName() + "' object.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

		} else if ((getInitParameter(PARAMETER_CONNECTION_SOURCE_PROVIDER_NAME) != null)
				&& (getInitParameter(PARAMETER_CONNECTION_SOURCE_PROVIDER_OBJECT) != null)) {

			// Get the name of the Provider where to retrieve the data source.
			final Object providerName = getInitParameter(PARAMETER_CONNECTION_SOURCE_PROVIDER_NAME);

			// Get the name of the data source object bound to the Provider.
			final Object providerObject = getInitParameter(PARAMETER_CONNECTION_SOURCE_PROVIDER_OBJECT);

			// Validate that given parameters are strings and return the data
			// source.
			if ((providerName instanceof String) && (providerObject instanceof String)) {

				// Get the data source from the Provider.
				final Object dataSource = getScopeFacade().getObject((String) providerName, (String) providerObject);

				// Return the data source.
				if (dataSource instanceof DataSource) {
					return dataSource;
				} else {
					throw new ConnectorException(getScopeFacade(),
							"WAREWORK cannot retrieve the data source for Data Store '" + getClientName()
									+ "' because Provider '" + ((String) providerName) + "' does not returns a '"
									+ DataSource.class.getName() + "' object for '" + ((String) providerObject) + "'.",
							null, LogServiceConstants.LOG_LEVEL_WARN);
				}

			} else {
				throw new ConnectorException(getScopeFacade(),
						"WAREWORK cannot retrieve the data source for Data Store '" + getClientName()
								+ "' because one or both of the connector's parameters are not '"
								+ String.class.getName() + "' objects.",
						null, LogServiceConstants.LOG_LEVEL_WARN);
			}

		} else {
			return null;
		}
	}

}
