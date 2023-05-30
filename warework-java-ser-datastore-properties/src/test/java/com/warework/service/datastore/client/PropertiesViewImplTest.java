package com.warework.service.datastore.client;

import java.util.HashMap;
import java.util.Map;

import com.warework.core.scope.AbstractSerDatastorePropertiesTestCase;
import com.warework.core.scope.ScopeException;
import com.warework.core.scope.ScopeFacade;
import com.warework.core.service.ServiceException;
import com.warework.service.datastore.DatastoreServiceConstants;
import com.warework.service.datastore.DatastoreServiceFacade;
import com.warework.service.datastore.client.connector.PropertiesConnector;
import com.warework.service.datastore.view.KeyValueView;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public class PropertiesViewImplTest extends AbstractSerDatastorePropertiesTestCase {

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testConnectDisconnect() {
		try {

			//
			final KeyValueView view = getView();

			//
			view.connect();

			//
			view.disconnect();

		} catch (final Exception e) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testGetProperty1() {
		try {

			//
			final KeyValueView view = getView();

			//
			view.connect();

			//
			final String value = (String) view.get("name");
			if ((value == null) || (!(value.equals("James")))) {
				fail();
			}

			//
			view.disconnect();

		} catch (final Exception e) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testPutProperty1() {
		try {

			//
			final KeyValueView view = getView();

			//
			view.connect();

			//
			view.put("age", "12");

			//
			final String value = (String) view.get("age");
			if ((value == null) || (!(value.equals("12")))) {
				fail();
			}

			//
			view.disconnect();

		} catch (final Exception e) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testRemoveProperty1() {
		try {

			//
			final KeyValueView view = getView();

			//
			view.connect();

			//
			view.remove("name");

			//
			final String value = (String) view.get("name");
			if (value != null) {
				fail();
			}

			//
			view.disconnect();

		} catch (final Exception e) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testSize1() {
		try {

			//
			final KeyValueView view = getView();

			//
			view.connect();

			//
			final int size = view.size();
			if ((size != 1) && (size != 2)) {
				fail();
			}

			//
			view.disconnect();

		} catch (final Exception e) {
			fail();
		}
	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @return
	 * @throws ServiceException
	 * @throws ScopeException
	 */
	private KeyValueView getView() throws ServiceException, ScopeException {

		//
		final ScopeFacade system = create("system");

		//
		final DatastoreServiceFacade service = (DatastoreServiceFacade) system
				.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

		//
		final Map<String, Object> connectionParameters = new HashMap<String, Object>();
		{
			connectionParameters.put(PropertiesConnector.PARAMETER_CONTEXT_LOADER, PropertiesDatastoreTest.class);
			connectionParameters.put(PropertiesConnector.PARAMETER_CONFIG_TARGET,
					PropertiesDatastoreTest.class.getResource(PATH_RESOURCE_1));
		}

		//
		service.createClient("my-config", PropertiesConnector.class, connectionParameters);

		//
		service.addView("my-config", PropertiesViewImpl.class, "keyvalue-view", null, null);

		//
		return (KeyValueView) service.getView("my-config");

	}

}
