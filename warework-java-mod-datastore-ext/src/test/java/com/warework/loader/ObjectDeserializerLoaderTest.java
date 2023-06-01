package com.warework.loader;

import java.io.IOException;
import java.util.Hashtable;

import com.warework.core.model.ser.ProxyService;
import com.warework.core.model.ser.Scope;
import com.warework.core.scope.AbstractModDatastoreExtTestCase;
import com.warework.core.scope.ScopeException;
import com.warework.core.scope.ScopeFacade;
import com.warework.core.scope.ScopeL1Constants;
import com.warework.core.service.ProxyServiceConstants;
import com.warework.core.service.ServiceException;
import com.warework.core.util.helper.ResourceL1Helper;
import com.warework.core.util.helper.ResourceL2Helper;
import com.warework.core.util.helper.StringL2Helper;
import com.warework.provider.SingletonProvider;
import com.warework.service.datastore.DatastoreExtensionServiceConstants;
import com.warework.service.datastore.DatastoreServiceConstants;
import com.warework.service.datastore.DatastoreServiceFacade;
import com.warework.service.datastore.DatastoreServiceImpl;
import com.warework.service.datastore.client.connector.HashtableConnector;
import com.warework.service.datastore.model.ser.DatastoreService;
import com.warework.service.log.LogServiceConstants;
import com.warework.service.log.LogServiceFacade;
import com.warework.service.log.LogServiceImpl;
import com.warework.service.log.client.connector.ConsoleConnector;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public class ObjectDeserializerLoaderTest extends AbstractModDatastoreExtTestCase {

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(final String[] args) throws IOException {

		//
		serializeScope();

		//
		serializeLogService();

		//
		serializeDatastoreService();

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void test1() {

		//
		ScopeFacade system = null;
		try {
			system = createSER("system-1");
		} catch (final ScopeException e) {
			fail();
		}

		//
		final Object paramValue = system.getInitParameter("user-name");
		if ((paramValue != null) && (paramValue instanceof String)) {

			//
			final String value = (String) paramValue;

			//
			if (!value.equals("john")) {
				fail();
			}

		} else {
			fail();
		}

		//
		try {

			//
			final LogServiceFacade ds = (LogServiceFacade) system.getService(LogServiceConstants.DEFAULT_SERVICE_NAME);

			//
			if (!ds.isConnected(ProxyServiceConstants.DEFAULT_CLIENT_NAME)) {
				ds.connect(ProxyServiceConstants.DEFAULT_CLIENT_NAME);
			}

			//
			ds.log(ProxyServiceConstants.DEFAULT_CLIENT_NAME, "Hello from serialized config",
					LogServiceConstants.LOG_LEVEL_INFO);

		} catch (final ServiceException e) {
			fail();
		}

		//
		try {

			//
			final String datastoreName = "hastable-datasource";

			//
			final DatastoreServiceFacade ds = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			if (!ds.existsClient(datastoreName)) {
				fail();
			}

			//
			if (!ds.isConnected(datastoreName)) {
				ds.connect(datastoreName);
			}

			//
			ds.disconnect(datastoreName);

		} catch (final ServiceException e) {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @throws IOException
	 */
	private static void serializeScope() throws IOException {

		//
		final Scope scope = new Scope();

		//
		scope.setInitParameter("user-name", "john");

		//
		scope.setProvider(SingletonProvider.DEFAULT_PROVIDER_NAME, SingletonProvider.class, null);

		//
		scope.setService(LogServiceConstants.DEFAULT_SERVICE_NAME, LogServiceImpl.class, null,
				ObjectDeserializerLoader.class);
		scope.setServiceParameter(LogServiceConstants.DEFAULT_SERVICE_NAME, ScopeL1Constants.PARAMETER_CONFIG_TARGET,
				ResourceL1Helper.DIRECTORY_SEPARATOR + ResourceL2Helper.DIRECTORY_META_INF
						+ ResourceL1Helper.DIRECTORY_SEPARATOR + PROJECT_NAME + ResourceL1Helper.DIRECTORY_SEPARATOR
						+ LogServiceConstants.DEFAULT_SERVICE_NAME + StringL2Helper.CHARACTER_HYPHEN + "1"
						+ ResourceL2Helper.FILE_EXTENSION_SEPARATOR + ResourceL2Helper.FILE_EXTENSION_SER);

		//
		scope.setService(DatastoreExtensionServiceConstants.DEFAULT_SERVICE_NAME, DatastoreServiceImpl.class, null,
				ObjectDeserializerLoader.class);
		scope.setServiceParameter(DatastoreExtensionServiceConstants.DEFAULT_SERVICE_NAME,
				ScopeL1Constants.PARAMETER_CONFIG_TARGET,
				ResourceL1Helper.DIRECTORY_SEPARATOR + ResourceL2Helper.DIRECTORY_META_INF
						+ ResourceL1Helper.DIRECTORY_SEPARATOR + PROJECT_NAME + ResourceL1Helper.DIRECTORY_SEPARATOR
						+ DatastoreServiceConstants.DEFAULT_SERVICE_NAME + StringL2Helper.CHARACTER_HYPHEN + "1"
						+ ResourceL2Helper.FILE_EXTENSION_SEPARATOR + ResourceL2Helper.FILE_EXTENSION_SER);

		//
		scope.setObjectReference("map", SingletonProvider.DEFAULT_PROVIDER_NAME, Hashtable.class.getName());

		//
		serialize(scope, "system-1" + ResourceL2Helper.FILE_EXTENSION_SEPARATOR + ResourceL2Helper.FILE_EXTENSION_SER);

	}

	/**
	 * 
	 * @throws IOException
	 */
	private static void serializeLogService() throws IOException {

		//
		final ProxyService service = new ProxyService();

		//
		service.setName(LogServiceConstants.DEFAULT_SERVICE_NAME);
		service.setClazz(LogServiceImpl.class.getName());

		//
		service.setClient(ProxyServiceConstants.DEFAULT_CLIENT_NAME, ConsoleConnector.class);

		//
		serialize(service, LogServiceConstants.DEFAULT_SERVICE_NAME + StringL2Helper.CHARACTER_HYPHEN + "1"
				+ ResourceL2Helper.FILE_EXTENSION_SEPARATOR + ResourceL2Helper.FILE_EXTENSION_SER);

	}

	/**
	 * 
	 * @throws IOException
	 */
	private static void serializeDatastoreService() throws IOException {

		//
		final DatastoreService service = new DatastoreService();

		//
		service.setClient("hastable-datasource", HashtableConnector.class);

		//
		serialize(service, DatastoreServiceConstants.DEFAULT_SERVICE_NAME + StringL2Helper.CHARACTER_HYPHEN + "1"
				+ ResourceL2Helper.FILE_EXTENSION_SEPARATOR + ResourceL2Helper.FILE_EXTENSION_SER);

	}

}
