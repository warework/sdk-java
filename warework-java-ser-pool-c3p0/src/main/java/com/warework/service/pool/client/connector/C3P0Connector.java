package com.warework.service.pool.client.connector;

import java.util.Enumeration;
import java.util.Properties;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.DataSources;
import com.warework.core.service.client.connector.ConnectorException;
import com.warework.service.log.LogServiceConstants;
import com.warework.service.pool.client.C3P0Pooler;

/**
 * This connector holds the required information to create a c3p0 pool.<br>
 * <br>
 * To add a c3p0 Pooler into the Pool Service you have to invoke method
 * <code>createClient()</code> that exists in its Facade with a name, a c3p0
 * Connector class and a configuration for the Pooler. This configuration has to
 * specify two parameters, one for the JDBC URL and another for the database
 * driver class. Check this out with the following example:<br>
 * <br>
 * <code>
 * // Create the configuration for the Logger.<br>
 * Map&lt;String, Object&gt; config = new HashMap&lt;String, Object&gt;();<br>
 * <br>
 * // Set the driver class and JDBC URL.<br>
 * config.put(C3P0Connector.PARAMETER_JDBC_URL, "jdbc:derby:derbyDB;create=true");<br>
 * config.put(C3P0Connector.PARAMETER_DRIVER_CLASS, "org.apache.derby.jdbc.EmbeddedDriver");<br>
 * <br>
 * // Create the c3p0 Pooler.<br>
 * poolService.createClient("c3p0-pooler", C3P0Connector.class, config);<br> 
 * </code> <br>
 * This code shows how to create a c3p0 Pooler for an Apache Derby database. You
 * should download a specific JDBC driver for your database and review the URL
 * required to connect with the database plus the class that represents the
 * driver.<br>
 * <br>
 * You should also consider using the
 * <code>PARAMETER_CONNECT_ON_CREATE parameter</code>. It is very handy to
 * automatically connect this Pooler after it is created. If you plan to use
 * this Pooler in a Data Store, it is recommended to set the value for this
 * parameter to <code>TRUE</code>:<br>
 * <br>
 * <code>
 * // Create the configuration for the Logger.<br>
 * Map&lt;String, Object&gt; config = new HashMap&lt;String, Object&gt;();<br>
 * <br>
 * // Set the driver class and JDBC URL.<br>
 * config.put(C3P0Connector.PARAMETER_JDBC_URL, "jdbc:derby:derbyDB;create=true");<br>
 * config.put(C3P0Connector.PARAMETER_DRIVER_CLASS, "org.apache.derby.jdbc.EmbeddedDriver");<br>
 * config.put(C3P0Connector.PARAMETER_CONNECT_ON_CREATE, "true");<br>
 * <br>
 * // Create the c3p0 Pooler.<br>
 * poolService.createClient("c3p0-pooler", C3P0Connector.class, config);<br>
 * </code> <br>
 * c3p0 provides a very good default configuration (just with the parameters
 * defined in the previous example the Pooler works very well) but sometimes you
 * may need to define specific parameters for the pool to make it work as you
 * require. Review these parameters in the c3p0 documentation and define those
 * that you need like any other parameter:<br>
 * <br>
 * <code>
 * // Create the configuration for the Logger.<br>
 * Map&lt;String, Object&gt; config = new HashMap&lt;String, Object&gt;();<br>
 * <br>
 * // Set the driver class and JDBC URL.<br>
 * config.put(C3P0Connector.PARAMETER_JDBC_URL, "jdbc:derby:derbyDB;create=true");<br>
 * config.put(C3P0Connector.PARAMETER_DRIVER_CLASS, "org.apache.derby.jdbc.EmbeddedDriver");<br>
 * config.put(C3P0Connector.PARAMETER_CONNECT_ON_CREATE, "true");<br>
 * config.put("acquireIncrement", "3");<br>
 * config.put("acquireRetryAttempts", "30");<br>
 * config.put("acquireRetryDelay", "1000");<br>
 * config.put("autoCommitOnClose", "false");<br>
 * config.put("breakAfterAcquireFailure", "false");<br>
 * config.put("checkoutTimeout", "0");<br>
 * ...<br>
 * <br>
 * // Create the c3p0 Pooler.<br>
 * poolService.createClient("c3p0-pooler", C3P0Connector.class, config);<br>
 * </code> <br>
 * <br>
 * <b><u>Minimum prerequisites to run this Connector</u>:</b><br>
 * <ul>
 * <li><b>Runtime:</b> Java 1.5</li>
 * <li><b>Libraries:</b>
 * <ul>
 * <li>c3p0 version 0.8.4.5</li>
 * </ul>
 * </li>
 * </ul>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public final class C3P0Connector extends AbstractPoolerConnector {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	// PARAMETERS

	/**
	 * Initialization parameter that specifies the name of the JDBC driver class.
	 * Check out the documentation of your database driver to know which is the
	 * class that represents the JDBC driver required to create connections. This
	 * parameter is mandatory and it must be a <code>java.lang.String</code> object.
	 */
	public static final String PARAMETER_DRIVER_CLASS = "driver-class";

	/**
	 * Initialization parameter that specifies the URL for a JDBC connection. Check
	 * out the documentation of your database driver to know which is the URL format
	 * required to create a connection with the database. This parameter is
	 * mandatory and it must be a <code>java.lang.String</code> object.
	 */
	public static final String PARAMETER_JDBC_URL = "jdbc-url";

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets the type of the client.
	 * 
	 * @return A <code>com.warework.service.pool.client.C3P0Pooler</code>
	 *         client.<br>
	 *         <br>
	 */
	public Class<C3P0Pooler> getClientType() {
		return C3P0Pooler.class;
	}

	/**
	 * Gets a <code>javax.sql.DataSource</code> that creates pooled connections with
	 * a database.
	 * 
	 * @return A <code>javax.sql.DataSource</code>.<br>
	 *         <br>
	 * @throws ConnectorException If there is an error when trying to retrieve the
	 *                            connection.<br>
	 *                            <br>
	 */
	public Object getClientConnection() throws ConnectorException {

		// Get the configuration for the data source and the pool.
		final Properties configuration = (Properties) getConnectionSource();

		// Load the JDBC driver.
		try {
			Class.forName(configuration.getProperty(PARAMETER_DRIVER_CLASS));
		} catch (final Exception e) {
			throw new ConnectorException(getScopeFacade(),
					"WAREWORK cannot create a connection for Client '" + getClientName() + "' in Service '"
							+ getService().getName() + "' because given '" + PARAMETER_DRIVER_CLASS
							+ "' parameter does not represents an appropriate JDBC driver class. Check out this error: "
							+ e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Create an unpooled data source.
		DataSource unpooledDataSource = null;
		try {
			unpooledDataSource = DataSources.unpooledDataSource(configuration.getProperty(PARAMETER_JDBC_URL),
					configuration);
		} catch (final Exception e) {
			throw new ConnectorException(getScopeFacade(), "WAREWORK cannot create a connection for Client '"
					+ getClientName() + "' in Service '" + getService().getName()
					+ "' because given configuration is not appropriate for a JDBC data source. Check out this error: "
					+ e.getMessage(), e, LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Create the pooled data source.
		try {
			return DataSources.pooledDataSource(unpooledDataSource, configuration);
		} catch (final Exception e) {
			throw new ConnectorException(getScopeFacade(),
					"WAREWORK cannot create the connection source for Client '" + getClientName() + "' in Service '"
							+ getService().getName()
							+ "' because given configuration is not appropriate for c3p0. Check out this error: "
							+ e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}

	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates a <code>java.util.Properties</code> that contains the information
	 * required to create a connection with a database.
	 * 
	 * @return A <code>java.util.Properties</code>.<br>
	 *         <br>
	 * @throws ConnectorException If there is an error when trying to create the
	 *                            connection source.<br>
	 *                            <br>
	 */
	protected Object createConnectionSource() throws ConnectorException {

		// Create the configuration for the data source and the pool.
		final Properties configuration = new Properties();

		// Get the name of every initialization parameter.
		final Enumeration<String> initParameterNames = getInitParameterNames();

		// Add each string-value parameter in the configuration.
		if (initParameterNames != null) {
			while (initParameterNames.hasMoreElements()) {

				// Get the name of one parameter.
				final String initParameterName = initParameterNames.nextElement();

				// Get the value of the parameter.
				final Object initParameterValue = getInitParameter(initParameterName);
				if (initParameterValue instanceof String) {
					configuration.setProperty(initParameterName, (String) initParameterValue);
				} else if (initParameterValue instanceof Class) {

					// Get the class.
					final Class<?> clazz = (Class<?>) initParameterValue;

					// Set the name of the class as the value for the parameter.
					configuration.setProperty(initParameterName, clazz.getName());

				}

			}
		}

		// Return the configuration.
		return configuration;

	}

}
