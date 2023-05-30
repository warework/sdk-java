package com.warework.service.pool.client;

import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.DataSources;
import com.warework.core.service.client.AbstractClient;
import com.warework.core.service.client.ClientException;
import com.warework.core.util.CommonValueL1Constants;
import com.warework.service.log.LogServiceConstants;

/**
 * Client that uses c3p0 as a pool for database connections.<br>
 * <br>
 * c3p0 is an excellent Java Framework that minimizes the time needed to
 * retrieve connections from relational databases by implementing the Object
 * Pool design pattern with traditional JDBC drivers. For enterprise level
 * applications, c3p0 is one of the best solutions you can find in the open
 * source market: it is very stable in stressful situations, takes care quite
 * well about memory consumption and clearly improves the overall performance of
 * a software application.<br>
 * <br>
 * Warework Pooler for c3p0 wraps this Framework to integrate its functionality
 * with the Pool Service. It lets you configure c3p0 with XML files and interact
 * with other Warework Services, principally with the Data Store Service. Prior
 * to work with this Pooler, you should review the c3p0 documentation,
 * especially the section related with the configuration properties. There you
 * will find specific parameters for c3p0 to make it work as you require.<br>
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public final class C3P0Pooler extends AbstractClient implements PoolerFacade {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Constant that defines the default name for the client.
	 */
	public static final String DEFAULT_CLIENT_NAME = "c3p0-pooler";

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Gets an object from a c3p0 pool.
	 * 
	 * @return Object from a pool of objects.<br>
	 *         <br>
	 */
	public Object getObject() {

		// Get the pooled data source.
		final DataSource pooledDataSource = (DataSource) getConnection();

		// Validate that Client is connected.
		if (pooledDataSource == null) {
			getScopeFacade().log(
					"WAREWORK cannot retrieve a database connection from c3p0 pool '" + getName() + "' in Service '"
							+ getService().getName() + "' because it is not connected.",
					LogServiceConstants.LOG_LEVEL_WARN);
		}

		// Return a database connection from the pool.
		try {
			return pooledDataSource.getConnection();
		} catch (final SQLException e) {
			if ((e.getMessage() != null) && (!e.getMessage().equals(CommonValueL1Constants.STRING_EMPTY))) {

				// Log a Warework message.
				getScopeFacade().log("WAREWORK cannot retrieve a database connection from the c3p0 pool '" + getName()
						+ "' in Service '" + getService().getName() + "' because c3p0 reported the following error: ",
						LogServiceConstants.LOG_LEVEL_WARN);

				// Log the exception message.
				getScopeFacade().log(e.getMessage(), LogServiceConstants.LOG_LEVEL_WARN);

			} else {
				getScopeFacade().log("WAREWORK cannot retrieve a database connection from c3p0 pool '" + getName()
						+ "' in Service '" + getService().getName() + "' because a '" + SQLException.class.getName()
						+ "' exception is thrown.", LogServiceConstants.LOG_LEVEL_WARN);
			}
		}

		// Nothing to return at this point.
		return null;

	}

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Closes the connection with the client.
	 * 
	 * @throws ClientException If there is an error when trying to close the
	 *                         Pooler.<br>
	 *                         <br>
	 */
	protected void close() throws ClientException {

		// Get the pooled data source.
		final DataSource pooledDataSource = (DataSource) getConnection();

		// Remove every connection from the pool.
		try {
			DataSources.destroy(pooledDataSource);
		} catch (final Exception e) {
			throw new ClientException(getScopeFacade(),
					"WAREWORK cannot destroy database connections from c3p0 pool '" + getName() + "' in Service '"
							+ getService().getName() + "' because the following exception is thrown: " + e.getMessage(),
					e, LogServiceConstants.LOG_LEVEL_WARN);
		}

	}

	/**
	 * Validates if the connection is closed.
	 * 
	 * @return <code>true</code> if the connection is closed and <code>false</code>
	 *         if the connection is open.<br>
	 *         <br>
	 */
	protected boolean isClosed() {
		return false;
	}

}
