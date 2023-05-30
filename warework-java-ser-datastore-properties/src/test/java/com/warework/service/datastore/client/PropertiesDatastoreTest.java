package com.warework.service.datastore.client;

import java.util.HashMap;
import java.util.Map;

import com.warework.core.scope.AbstractSerDatastorePropertiesTestCase;
import com.warework.core.scope.ScopeFacade;
import com.warework.core.service.ServiceException;
import com.warework.service.datastore.DatastoreServiceConstants;
import com.warework.service.datastore.DatastoreServiceFacade;
import com.warework.service.datastore.client.connector.PropertiesConnector;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public class PropertiesDatastoreTest extends AbstractSerDatastorePropertiesTestCase {

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testCreateClient1() {
		try {

			//
			final ScopeFacade system = create("system");

			//
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			final Map<String, Object> connectionParameters = new HashMap<String, Object>();
			{
				connectionParameters.put(PropertiesConnector.PARAMETER_CONTEXT_LOADER, PropertiesDatastoreTest.class);
				connectionParameters.put(PropertiesConnector.PARAMETER_CONFIG_TARGET, PATH_RESOURCE_1);
			}

			//
			service.createClient("my-config", PropertiesConnector.class, connectionParameters);

			//
			service.connect("my-config");

			//
			service.disconnect("my-config");

		} catch (final Exception e) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testQuery1() {
		try {

			//
			final ScopeFacade system = create("system");

			//
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			configure(service);

			//
			service.connect("my-config");

			//
			final String value = (String) service.query("my-config", null, "name");
			if ((value == null) || (!(value.equals("James")))) {
				fail();
			}

			//
			service.disconnect("my-config");

		} catch (final Exception e) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testUpdate1() {
		try {

			//
			final ScopeFacade system = create("system");

			//
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			configure(service);

			//
			service.connect("my-config");

			//
			service.update("my-config", null, "color=red");

			//
			service.commit("my-config");

			//
			final String value = (String) service.query("my-config", null, "color");
			if ((value == null) || (!(value.equals("red")))) {
				fail();
			}

			//
			service.disconnect("my-config");

		} catch (final Exception e) {
			fail();
		}
	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @param service
	 * @throws ServiceException
	 */
	private void configure(final DatastoreServiceFacade service) throws ServiceException {

		//
		final Map<String, Object> connectionParameters = new HashMap<String, Object>();
		{
			connectionParameters.put(PropertiesConnector.PARAMETER_CONTEXT_LOADER, PropertiesDatastoreTest.class);
			connectionParameters.put(PropertiesConnector.PARAMETER_CONFIG_TARGET,
					PropertiesDatastoreTest.class.getResource(PATH_RESOURCE_1));
		}

		//
		service.createClient("my-config", PropertiesConnector.class, connectionParameters);

	}

}
