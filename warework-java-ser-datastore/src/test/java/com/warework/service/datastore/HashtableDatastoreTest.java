package com.warework.service.datastore;

import com.warework.core.scope.ScopeException;
import com.warework.core.service.ServiceException;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public class HashtableDatastoreTest extends AbstractHashtableDatastoreTest {

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testConnectDisconnect1() {
		try {

			//
			final DatastoreServiceFacade service = getDatastoreService();

			//
			service.connect(DATA_STORE_NAME);

			//
			service.disconnect(DATA_STORE_NAME);

		} catch (Exception e) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testQueryUpdate1() {
		try {

			//
			final DatastoreServiceFacade service = getDatastoreService();

			//
			service.connect(DATA_STORE_NAME);

			//
			service.update(DATA_STORE_NAME, null, "name=john");

			//
			final Object result = service.query(DATA_STORE_NAME, null, "name");

			//
			if ((result == null) || !(result instanceof String)) {
				fail();
			} else {

				//
				final String stringResult = (String) result;

				//
				if (!stringResult.equals("john")) {
					fail();
				}

			}

			//
			service.disconnect(DATA_STORE_NAME);

		} catch (final Exception e) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testQueryUpdate2() {
		try {

			//
			final DatastoreServiceFacade service = getDatastoreService();

			//
			service.connect(DATA_STORE_NAME);

			//
			service.update(DATA_STORE_NAME, null, new Object[] { new Integer(123), "james" });

			//
			final Object result = service.query(DATA_STORE_NAME, null, new Integer(123));

			//
			if ((result == null) || !(result instanceof String)) {
				fail();
			} else {

				//
				final String stringResult = (String) result;

				//
				if (!stringResult.equals("james")) {
					fail();
				}

			}

			//
			service.disconnect(DATA_STORE_NAME);

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
	private DatastoreServiceFacade getDatastoreService() throws ScopeException, ServiceException {
		return (DatastoreServiceFacade) createScope1().getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);
	}

}
