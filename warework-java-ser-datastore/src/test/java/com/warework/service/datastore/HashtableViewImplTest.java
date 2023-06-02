package com.warework.service.datastore;

import java.util.Enumeration;

import com.warework.core.scope.ScopeException;
import com.warework.core.scope.ScopeFacade;
import com.warework.core.service.ServiceException;
import com.warework.service.datastore.client.HashtableViewImpl;
import com.warework.service.datastore.view.KeyValueView;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public class HashtableViewImplTest extends AbstractHashtableDatastoreTest {

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testConnectDisconnect1() {
		try {

			//
			final KeyValueView view = getDatastoreService1();

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
	public void testQueryUpdate1() {
		try {

			//
			final KeyValueView view = getDatastoreService1();

			//
			view.connect();

			//
			view.put("age", new Integer(24));

			//
			final Integer result = (Integer) view.get("age");

			//
			if (!result.equals(new Integer(24))) {
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
	public void testRemove1() {
		try {

			//
			final KeyValueView view = getDatastoreService1();

			//
			view.connect();

			//
			view.put("age", new Integer(24));

			//
			view.remove("age");

			//
			if (view.get("age") != null) {
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
	public void testKeys1() {
		try {

			//
			final KeyValueView view = getDatastoreService1();

			//
			view.connect();

			//
			view.put("age", new Integer(24));

			//
			final Enumeration<Object> enumer = view.keys();

			//
			int counter = 0;
			while (enumer.hasMoreElements()) {

				//
				final Object type = enumer.nextElement();

				//
				if (!(type instanceof String)) {
					fail();
				} else {
					counter++;
				}

			}

			//
			if (counter != 1) {
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
			final KeyValueView view = getDatastoreService1();

			//
			view.connect();

			//
			view.put("age", new Integer(24));
			view.put("name", "John");

			//
			int counter = view.size();
			if (counter != 2) {
				fail();
			}

			//
			view.disconnect();

		} catch (final Exception e) {
			fail();
		}
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testConnectDisconnect2() {
		try {

			//
			final KeyValueView view = getDatastoreService2();

			//
			view.connect();

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
	private KeyValueView getDatastoreService1() throws ScopeException, ServiceException {

		//
		ScopeFacade system = createScope1();

		//
		DatastoreServiceFacade service = (DatastoreServiceFacade) system
				.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

		//
		return (KeyValueView) service.getView(DATA_STORE_NAME, HashtableViewImpl.class);

	}

	/**
	 * 
	 * @return
	 * @throws ServiceException
	 * @throws ScopeException
	 */
	private KeyValueView getDatastoreService2() throws ScopeException, ServiceException {

		//
		ScopeFacade system = createScope2();

		//
		DatastoreServiceFacade service = (DatastoreServiceFacade) system
				.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

		//
		return (KeyValueView) service.getView(DATA_STORE_NAME, HashtableViewImpl.class);

	}

}
