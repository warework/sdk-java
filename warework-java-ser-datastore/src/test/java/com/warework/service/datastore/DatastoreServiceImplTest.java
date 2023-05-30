package com.warework.service.datastore;

import java.util.Enumeration;

import com.warework.core.model.Scope;
import com.warework.core.provider.ProviderException;
import com.warework.core.scope.AbstractSerDatastoreTestCase;
import com.warework.core.scope.ScopeException;
import com.warework.core.scope.ScopeFacade;
import com.warework.core.service.ServiceException;
import com.warework.service.datastore.client.HashtableViewImpl;
import com.warework.service.datastore.client.connector.HashtableConnector;
import com.warework.service.datastore.view.KeyValueView;
import com.warework.service.datastore.view.SampleKeyValueViewL1;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public class DatastoreServiceImplTest extends AbstractSerDatastoreTestCase {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	//
	private final static String SCOPE_NAME = "test";

	//
	private final static String DATA_STORE_NAME = "hashtable-datastore";

	//
	private final static String VIEW_NAME = "key-value-view";

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Query with default View
	 */
	public void testQueryB1() {
		try {

			//
			final DatastoreServiceFacade service = getService2();

			//
			service.connect(DATA_STORE_NAME);

			// Init data
			service.update(DATA_STORE_NAME, null, "name=steve");

			// Add Views
			service.addView(DATA_STORE_NAME, HashtableViewImpl.class, "key-value-view-1", null, null);
			service.addView(DATA_STORE_NAME, SampleKeyValueViewL1.class, "key-value-view-2", null, null);

			//
			final String name = (String) service.query(DATA_STORE_NAME, null, "name");
			if (!name.equals("john")) {
				fail();
			}

			//
			service.disconnect(DATA_STORE_NAME);

		} catch (final Exception e) {
			fail();
		}
	}

	/**
	 * Query with a specific View - 1
	 */
	public void testQueryB2() {
		try {

			//
			final DatastoreServiceFacade service = getService2();

			//
			service.connect(DATA_STORE_NAME);

			// Init data
			service.update(DATA_STORE_NAME, null, "name=steve");

			// Add Views
			service.addView(DATA_STORE_NAME, HashtableViewImpl.class, "key-value-view-1", null, null);
			service.addView(DATA_STORE_NAME, SampleKeyValueViewL1.class, "key-value-view-2", null, null);

			//
			final String name = (String) service.query(DATA_STORE_NAME, "key-value-view-2", "name");
			if (!name.equals("john")) {
				fail();
			}

			//
			service.disconnect(DATA_STORE_NAME);

		} catch (final Exception e) {
			fail();
		}
	}

	/**
	 * Query with a specific View - 2
	 */
	public void testQueryB3() {
		try {

			//
			final DatastoreServiceFacade service = getService2();

			//
			service.connect(DATA_STORE_NAME);

			// Init data
			service.update(DATA_STORE_NAME, null, "name=steve");

			// Add Views
			service.addView(DATA_STORE_NAME, HashtableViewImpl.class, "key-value-view-1", null, null);
			service.addView(DATA_STORE_NAME, SampleKeyValueViewL1.class, "key-value-view-2", null, null);

			//
			final String name = (String) service.query(DATA_STORE_NAME, "key-value-view-1", "name");
			if (!name.equals("steve")) {
				fail();
			}

			//
			service.disconnect(DATA_STORE_NAME);

		} catch (Exception e) {
			fail();
		}
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * View cannot be named 'default-view'
	 */
	public void testAddView1() {
		try {

			//
			final DatastoreServiceFacade service = getService2();

			//
			service.addView(DATA_STORE_NAME, HashtableViewImpl.class, "default-view", null, null);

			//
			fail();

		} catch (final Exception e) {
			// DO NOTHING.
		}
	}

	/**
	 * 
	 */
	public void testAddView2() {
		try {

			//
			final DatastoreServiceFacade service = getService2();

			//
			service.connect(DATA_STORE_NAME);

			// Init data
			service.update(DATA_STORE_NAME, null, "name=steve");

			// Add View 1
			service.addView(DATA_STORE_NAME, HashtableViewImpl.class, "key-value-view-1", null, null);

			// Add View 2
			service.addView(DATA_STORE_NAME, SampleKeyValueViewL1.class, "key-value-view-2", null, null);

			//
			final KeyValueView view = (KeyValueView) service.getView(DATA_STORE_NAME);

			//
			final String name = (String) view.get("name");
			if (!name.equals("john")) {
				fail();
			}

			//
			service.disconnect(DATA_STORE_NAME);

		} catch (final Exception e) {
			fail();
		}
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testGetViewNames1() {
		try {

			//
			final DatastoreServiceFacade service = getService3();

			//
			final Enumeration<String> viewNames = service.getViewNames(DATA_STORE_NAME);

			//
			if (viewNames != null) {
				while (viewNames.hasMoreElements()) {

					//
					final String name = (String) viewNames.nextElement();

					//
					if (!name.equals(VIEW_NAME)) {
						fail();
					}

				}
			} else {
				fail();
			}

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
	 * @throws ScopeException
	 * @throws ServiceException
	 */
	private DatastoreServiceFacade getService1() throws ScopeException, ServiceException {

		//
		final Scope config = new Scope(SCOPE_NAME);
		{
			config.setService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME, DatastoreServiceImpl.class, null, null);
		}

		//
		final ScopeFacade scope = create(config);

		//
		return (DatastoreServiceFacade) scope.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

	}

	/**
	 * 
	 * @return
	 * @throws ServiceException
	 * @throws ScopeException
	 * @throws ProviderException
	 */
	private DatastoreServiceFacade getService2() throws ScopeException, ServiceException, ProviderException {

		//
		final DatastoreServiceFacade service = getService1();

		//
		service.createClient(DATA_STORE_NAME, HashtableConnector.class, null);

		//
		return service;

	}

	/**
	 * 
	 * @return
	 * @throws ServiceException
	 * @throws ScopeException
	 * @throws ProviderException
	 */
	private DatastoreServiceFacade getService3() throws ScopeException, ServiceException, ProviderException {

		//
		final DatastoreServiceFacade service = getService2();

		//
		service.addView(DATA_STORE_NAME, HashtableViewImpl.class, VIEW_NAME, null, null);

		//
		return service;

	}

}
