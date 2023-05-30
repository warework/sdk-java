package com.warework.loader;

import java.io.IOException;
import java.util.Hashtable;

import com.warework.core.model.ser.ProxyService;
import com.warework.core.model.ser.Scope;
import com.warework.core.scope.AbstractModCoreExtTestCase;
import com.warework.core.scope.ScopeException;
import com.warework.core.scope.ScopeFacade;
import com.warework.core.scope.ScopeL1Constants;
import com.warework.core.service.ServiceException;
import com.warework.provider.SingletonProvider;
import com.warework.service.log.LogServiceConstants;
import com.warework.service.log.LogServiceFacade;
import com.warework.service.log.LogServiceImpl;
import com.warework.service.log.client.connector.ConsoleConnector;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public class ObjectDeserializerLoaderTest extends AbstractModCoreExtTestCase {

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(final String[] args) throws IOException {
		serialize();
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
		try {

			//
			final LogServiceFacade service = (LogServiceFacade) system.getService("log-service");

			//
			service.connect("console-logger");

			//
			service.log("console-logger", "Hello from serialized config", LogServiceConstants.LOG_LEVEL_INFO);

			//
			service.disconnect("console-logger");

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
	private static void serialize() throws IOException {

		/*
		 * SERIALIZE SCOPE
		 */

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
				"/METa-INF/warework-java-mod-core-ext/log-service-1.ser");

		//
		scope.setObjectReference("map", SingletonProvider.DEFAULT_PROVIDER_NAME, Hashtable.class.getName());

		//
		serialize(scope, "system-1.ser");

		/*
		 * SERIALIZE LOG SERVICE
		 */

		//
		final ProxyService service = new ProxyService();

		//
		service.setName(LogServiceConstants.DEFAULT_SERVICE_NAME);
		service.setClazz(LogServiceImpl.class.getName());

		//
		service.setClient("console-logger", ConsoleConnector.class);

		//
		serialize(service, "log-service-1.ser");

	}

}
