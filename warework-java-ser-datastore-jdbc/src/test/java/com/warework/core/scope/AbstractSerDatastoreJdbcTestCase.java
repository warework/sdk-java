package com.warework.core.scope;

import java.util.HashMap;
import java.util.Map;

import org.h2.jdbcx.JdbcDataSource;

import com.warework.core.model.Scope;
import com.warework.core.service.ProxyServiceConstants;
import com.warework.core.service.client.ClientException;
import com.warework.core.util.helper.ResourceL1Helper;
import com.warework.provider.FileTextProvider;
import com.warework.service.datastore.DatastoreServiceConstants;
import com.warework.service.datastore.DatastoreServiceFacade;
import com.warework.service.datastore.client.CacheJdbcViewImpl;
import com.warework.service.datastore.client.JdbcViewImpl;
import com.warework.service.datastore.client.KeyValueJdbcViewImpl;
import com.warework.service.datastore.client.connector.JdbcConnector;
import com.warework.service.datastore.model.DatastoreService;
import com.warework.service.datastore.view.RdbmsView;
import com.warework.service.log.LogServiceConstants;
import com.warework.service.log.LogServiceImpl;
import com.warework.service.log.client.connector.ConsoleConnector;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public abstract class AbstractSerDatastoreJdbcTestCase extends AbstractTestCase {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	//
	protected static final String PROJECT_NAME = "warework-java-ser-datastore-jdbc";

	//

	protected static final String PROVIDER_SQL_STATEMENT = "sql-statement-provider";

	protected static final String DATABASE_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";

	protected static final String CLIENT_JDBC_1 = "jdbc-client-1";

	protected static final String CLIENT_JDBC_2 = "jdbc-client-2";

	protected static final String CLIENT_JDBC_3 = "jdbc-client-3";

	protected static final String CLIENT_JDBC_4 = "jdbc-client-4";

	protected static final String CLIENT_KEY_VALUE_JDBC = "keyvalue-jdbc-client";

	protected static final String VIEW_NAME_RDBMS = "rdbms-view";

	protected static final String VIEW_NAME_KEY_VALUE = "key-value-view";

	protected static final String VIEW_NAME_CACHE = "cache-view";

	protected static final String TABLE_NAME_APPLICATION_CONFIG_1 = "APPLICATION_CONFIG_1";

	protected static final String TABLE_NAME_APPLICATION_CONFIG_2 = "APPLICATION_CONFIG_2";

	protected static final String COLUMN_NAME_FIELD_KEY = "FIELD_KEY";

	protected static final String COLUMN_NAME_FIELD_VALUE = "FIELD_VALUE";

	protected static final String COLUMN_NAME_FIELD_EXPIRE = "FIELD_EXPIRE";

	protected static final String CURRENT_TIMESTAMP_MILLISECONDS = "extract(epoch from current_timestamp()) * 1000";

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates a Scope in this context.
	 * 
	 * @param name Name of the Scope.<br>
	 *             <br>
	 * @return A new instance of a Scope. If a Scope of the same name is already
	 *         bound to the context, the existing Scope is returned.<br>
	 *         <br>
	 * @throws ScopeException  If there is an error when trying to create the
	 *                         Scope.<br>
	 *                         <br>
	 * @throws ClientException
	 */
	protected ScopeFacade create(final String name) throws ScopeException, ClientException {

		// Create new Scope.
		final ScopeFacade scope = create(config(name));

		// Initialize database.
		init(scope);

		// Return initialized Scope.
		return scope;

	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @param name
	 * @return
	 */
	private Scope config(final String name) {

		//
		Scope config = new Scope("test");

		// Create database datasource.
		final JdbcDataSource datasource = new JdbcDataSource();
		{
			datasource.setURL(DATABASE_URL);
		}

		// Setup the LOG SERVICE.
		config.setService(LogServiceConstants.DEFAULT_SERVICE_NAME, LogServiceImpl.class, null, null);
		config.setClient(LogServiceConstants.DEFAULT_SERVICE_NAME, ProxyServiceConstants.DEFAULT_CLIENT_NAME,
				ConsoleConnector.class, null);

		// Setup the DATASTORE SERVICE.
		{

			//
			final DatastoreService datastoreService = new DatastoreService();

			// ------------ DATA STORE 1

			// Client
			datastoreService.setDatastore(CLIENT_JDBC_1, JdbcConnector.class, null, null);

			// Parameters
			datastoreService.setClientParameter(CLIENT_JDBC_1, JdbcConnector.PARAMETER_DATA_SOURCE, datasource);
			datastoreService.setClientParameter(CLIENT_JDBC_1, JdbcConnector.PARAMETER_NATIVE_RESULT_SET, Boolean.TRUE);

			// View
			datastoreService.setView(CLIENT_JDBC_1, JdbcViewImpl.class, VIEW_NAME_RDBMS, PROVIDER_SQL_STATEMENT, null);

			//
			Map<String, Object> viewConfig = new HashMap<String, Object>();
			{
				viewConfig.put(KeyValueJdbcViewImpl.PARAMETER_TABLE_NAME, TABLE_NAME_APPLICATION_CONFIG_1);
				viewConfig.put(KeyValueJdbcViewImpl.PARAMETER_TABLE_KEY_FIELD, COLUMN_NAME_FIELD_KEY);
				viewConfig.put(KeyValueJdbcViewImpl.PARAMETER_TABLE_VALUE_FIELD, COLUMN_NAME_FIELD_VALUE);
			}

			// View
			datastoreService.setView(CLIENT_JDBC_1, KeyValueJdbcViewImpl.class, VIEW_NAME_KEY_VALUE,
					PROVIDER_SQL_STATEMENT, viewConfig);

			//
			viewConfig = new HashMap<String, Object>();
			{
				viewConfig.put(CacheJdbcViewImpl.PARAMETER_TABLE_NAME, TABLE_NAME_APPLICATION_CONFIG_1);
				viewConfig.put(CacheJdbcViewImpl.PARAMETER_TABLE_KEY_FIELD, COLUMN_NAME_FIELD_KEY);
				viewConfig.put(CacheJdbcViewImpl.PARAMETER_TABLE_VALUE_FIELD, COLUMN_NAME_FIELD_VALUE);
				viewConfig.put(CacheJdbcViewImpl.PARAMETER_TABLE_EXPIRE_FIELD, COLUMN_NAME_FIELD_EXPIRE);
				viewConfig.put(CacheJdbcViewImpl.PARAMETER_CURRENT_TIMESTAMP_MILLISECONDS,
						CURRENT_TIMESTAMP_MILLISECONDS);
			}

			// Add view
			datastoreService.setView(CLIENT_JDBC_1, CacheJdbcViewImpl.class, VIEW_NAME_CACHE, PROVIDER_SQL_STATEMENT,
					viewConfig);

			// ------------ DATA STORE 2

			// Client
			datastoreService.setDatastore(CLIENT_JDBC_2, JdbcConnector.class, null, null);

			// Parameters
			datastoreService.setClientParameter(CLIENT_JDBC_2, JdbcConnector.PARAMETER_DATA_SOURCE, datasource);
			datastoreService.setClientParameter(CLIENT_JDBC_2, JdbcConnector.PARAMETER_NATIVE_RESULT_SET, Boolean.TRUE);

			// View
			datastoreService.setView(CLIENT_JDBC_2, JdbcViewImpl.class, VIEW_NAME_RDBMS, PROVIDER_SQL_STATEMENT, null);

			//
			viewConfig = new HashMap<String, Object>();
			{
				viewConfig.put(KeyValueJdbcViewImpl.PARAMETER_TABLE_NAME, TABLE_NAME_APPLICATION_CONFIG_2);
				viewConfig.put(KeyValueJdbcViewImpl.PARAMETER_TABLE_KEY_FIELD, COLUMN_NAME_FIELD_KEY);
				viewConfig.put(KeyValueJdbcViewImpl.PARAMETER_TABLE_VALUE_FIELD, COLUMN_NAME_FIELD_VALUE);
			}

			// Add view
			datastoreService.setView(CLIENT_JDBC_2, KeyValueJdbcViewImpl.class, VIEW_NAME_KEY_VALUE,
					PROVIDER_SQL_STATEMENT, viewConfig);

			//
			viewConfig = new HashMap<String, Object>();
			{
				viewConfig.put(CacheJdbcViewImpl.PARAMETER_TABLE_NAME, TABLE_NAME_APPLICATION_CONFIG_2);
				viewConfig.put(CacheJdbcViewImpl.PARAMETER_TABLE_KEY_FIELD, COLUMN_NAME_FIELD_KEY);
				viewConfig.put(CacheJdbcViewImpl.PARAMETER_TABLE_VALUE_FIELD, COLUMN_NAME_FIELD_VALUE);
				viewConfig.put(CacheJdbcViewImpl.PARAMETER_TABLE_EXPIRE_FIELD, COLUMN_NAME_FIELD_EXPIRE);
				viewConfig.put(CacheJdbcViewImpl.PARAMETER_CURRENT_TIMESTAMP_MILLISECONDS,
						CURRENT_TIMESTAMP_MILLISECONDS);
			}

			// View
			datastoreService.setView(CLIENT_JDBC_2, CacheJdbcViewImpl.class, VIEW_NAME_CACHE, PROVIDER_SQL_STATEMENT,
					viewConfig);

			// ------------ DATA STORE 3

			// Client
			datastoreService.setDatastore(CLIENT_JDBC_3, JdbcConnector.class, null, null);

			// Parameters
			datastoreService.setClientParameter(CLIENT_JDBC_3, JdbcConnector.PARAMETER_DATA_SOURCE, datasource);
			datastoreService.setClientParameter(CLIENT_JDBC_3, JdbcConnector.PARAMETER_NATIVE_RESULT_SET,
					Boolean.FALSE);

			// View
			datastoreService.setView(CLIENT_JDBC_3, JdbcViewImpl.class, VIEW_NAME_RDBMS, PROVIDER_SQL_STATEMENT, null);

			// ------------ DATA STORE 4

			// Client
			datastoreService.setDatastore(CLIENT_JDBC_4, JdbcConnector.class, null, null);

			// Parameters
			datastoreService.setClientParameter(CLIENT_JDBC_4, JdbcConnector.PARAMETER_DATA_SOURCE, datasource);
			datastoreService.setClientParameter(CLIENT_JDBC_4, JdbcConnector.PARAMETER_NATIVE_RESULT_SET,
					Boolean.FALSE);

			// View
			datastoreService.setView(CLIENT_JDBC_4, JdbcViewImpl.class, VIEW_NAME_RDBMS, PROVIDER_SQL_STATEMENT, null);

			//
			viewConfig = new HashMap<String, Object>();
			{
				viewConfig.put(KeyValueJdbcViewImpl.PARAMETER_TABLE_NAME, TABLE_NAME_APPLICATION_CONFIG_1);
				viewConfig.put(KeyValueJdbcViewImpl.PARAMETER_TABLE_KEY_FIELD, COLUMN_NAME_FIELD_KEY);
				viewConfig.put(KeyValueJdbcViewImpl.PARAMETER_TABLE_VALUE_FIELD, COLUMN_NAME_FIELD_VALUE);
			}

			// View
			datastoreService.setView(CLIENT_JDBC_4, KeyValueJdbcViewImpl.class, VIEW_NAME_KEY_VALUE,
					PROVIDER_SQL_STATEMENT, viewConfig);
			//
			viewConfig = new HashMap<String, Object>();
			{
				viewConfig.put(CacheJdbcViewImpl.PARAMETER_TABLE_NAME, TABLE_NAME_APPLICATION_CONFIG_1);
				viewConfig.put(CacheJdbcViewImpl.PARAMETER_TABLE_KEY_FIELD, COLUMN_NAME_FIELD_KEY);
				viewConfig.put(CacheJdbcViewImpl.PARAMETER_TABLE_VALUE_FIELD, COLUMN_NAME_FIELD_VALUE);
				viewConfig.put(CacheJdbcViewImpl.PARAMETER_TABLE_EXPIRE_FIELD, COLUMN_NAME_FIELD_EXPIRE);
				viewConfig.put(CacheJdbcViewImpl.PARAMETER_CURRENT_TIMESTAMP_MILLISECONDS,
						CURRENT_TIMESTAMP_MILLISECONDS);
			}

			// View
			datastoreService.setView(CLIENT_JDBC_4, CacheJdbcViewImpl.class, VIEW_NAME_CACHE, PROVIDER_SQL_STATEMENT,
					viewConfig);

			// -----

			//
			config.setService(datastoreService);

		}

		// Setup the SQL STATEMENT PROVIDER.
		config.setProvider(PROVIDER_SQL_STATEMENT, FileTextProvider.class, null);
		config.setProviderParameter(PROVIDER_SQL_STATEMENT, FileTextProvider.PARAMETER_CONFIG_TARGET,
				ResourceL1Helper.DIRECTORY_SEPARATOR + ResourceL1Helper.DIRECTORY_META_INF
						+ ResourceL1Helper.DIRECTORY_SEPARATOR + PROJECT_NAME + "/statement/sql");
		config.setProviderParameter(PROVIDER_SQL_STATEMENT, FileTextProvider.PARAMETER_FILE_EXTENSION, "sql");
		config.setProviderParameter(PROVIDER_SQL_STATEMENT, FileTextProvider.PARAMETER_CREATE_OBJECTS, Boolean.FALSE);
		config.setProviderParameter(PROVIDER_SQL_STATEMENT, FileTextProvider.PARAMETER_CONTEXT_LOADER,
				AbstractSerDatastoreJdbcTestCase.class);
		config.setProviderParameter(PROVIDER_SQL_STATEMENT, FileTextProvider.PARAMETER_REMOVE_NEW_LINE_CHARACTER,
				Boolean.TRUE);
		config.setProviderParameter(PROVIDER_SQL_STATEMENT, FileTextProvider.PARAMETER_REMOVE_TAB_CHARACTER,
				Boolean.TRUE);

		//
		return config;

	}

	/**
	 * 
	 * @param system
	 * @throws ClientException
	 */
	private void init(final ScopeFacade system) throws ClientException {

		//
		final DatastoreServiceFacade service = (DatastoreServiceFacade) system
				.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

		//
		final RdbmsView view = (RdbmsView) service.getView(CLIENT_JDBC_1, JdbcViewImpl.class);

		//
		view.connect();

		//
		try {
			view.executeUpdate("DROP TABLE IF EXISTS USER_CONTACT", null, null);
		} catch (final ClientException e) {
			// DO NOTHING.
		}
		try {
			view.executeUpdate("DROP TABLE IF EXISTS CONTACT", null, null);
		} catch (final ClientException e) {
			// DO NOTHING.
		}
		try {
			view.executeUpdate("DROP TABLE IF EXISTS PERSON_FRIEND", null, null);
		} catch (final ClientException e) {
			// DO NOTHING.
		}
		try {
			view.executeUpdate("DROP TABLE IF EXISTS PERSON", null, null);
		} catch (final ClientException e) {
			// DO NOTHING.
		}
		try {
			view.executeUpdate("DROP TABLE IF EXISTS CONTACT_TYPE", null, null);
		} catch (final ClientException e) {
			// DO NOTHING.
		}

		//
		view.executeUpdateByName("users.ddl", null, new Character(';'));
		view.executeUpdateByName("users.dml", null, new Character(';'));

		//
		try {
			view.executeUpdate("DROP TABLE IF EXISTS " + TABLE_NAME_APPLICATION_CONFIG_1, null, null);
			view.executeUpdate("DROP TABLE IF EXISTS " + TABLE_NAME_APPLICATION_CONFIG_2, null, null);
		} catch (final ClientException e) {
			// DO NOTHING.
		}

		//
		view.executeUpdateByName("properties.ddl", null, new Character(';'));
		view.executeUpdateByName("properties.dml", null, new Character(';'));

		//
		view.commit();

		//
		view.disconnect();

	}

}
