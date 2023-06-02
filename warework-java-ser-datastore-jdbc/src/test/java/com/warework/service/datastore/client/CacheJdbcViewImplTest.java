package com.warework.service.datastore.client;

import java.util.Date;
import java.util.Enumeration;

import com.warework.core.scope.AbstractSerDatastoreJdbcTestCase;
import com.warework.core.scope.ScopeFacade;
import com.warework.core.util.helper.DateL2Helper;
import com.warework.service.datastore.DatastoreServiceConstants;
import com.warework.service.datastore.DatastoreServiceFacade;
import com.warework.service.datastore.view.CacheView;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public class CacheJdbcViewImplTest extends AbstractSerDatastoreJdbcTestCase {

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testGet1() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			final CacheView view = (CacheView) service.getView(CLIENT_JDBC_1, CacheJdbcViewImpl.class);

			//
			view.connect();

			//
			final String value1 = (String) view.get("app.name");
			if ((value1 == null) || (!(value1.equals("calculator")))) {
				fail();
			}

			//
			final String value2 = (String) view.get("app.version");
			if ((value2 == null) || (!(value2.equals("1.0.0")))) {
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
	public void testGet2() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			final CacheView view = (CacheView) service.getView(CLIENT_JDBC_2, CacheJdbcViewImpl.class);

			//
			view.connect();

			//
			final Date value1 = (Date) view.get(new Integer(1));
			if (value1 == null) {
				fail();
			}

			//
			final Date value2 = (Date) view.get(new Integer(2));
			if (value2 == null) {
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
	public void testPut1() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			final CacheView view = (CacheView) service.getView(CLIENT_JDBC_1, CacheJdbcViewImpl.class);

			//
			view.connect();

			//
			view.put("app.owner", "James Wood");

			//
			view.commit();

			//
			final String value1 = (String) view.get("app.owner");

			//
			if ((value1 == null) || (!(value1.equals("James Wood")))) {
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
	public void testPut2() {
		try {
			
			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			final CacheView view = (CacheView) service.getView(CLIENT_JDBC_1, CacheJdbcViewImpl.class);

			//
			view.connect();

			// Update the key
			view.put("app.name", "XYZ", 1);

			//
			view.commit();
			
			//
			Thread.sleep(DateL2Helper.SECOND_MILLIS);

			//
			final String value1 = (String) view.get("app.name");
			if (value1 != null) {
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
	public void testPut3() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			final CacheView view = (CacheView) service.getView(CLIENT_JDBC_2, CacheJdbcViewImpl.class);

			//
			view.connect();

			//
			final Integer key = new Integer(3);

			// Update the key
			view.put(key, new Date());

			//
			view.commit();

			//
			final Date value1 = (Date) view.get(key);
			if (value1 == null) {
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

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			final CacheView view = (CacheView) service.getView(CLIENT_JDBC_1, CacheJdbcViewImpl.class);

			//
			view.connect();

			//
			view.remove("app.name");

			//
			view.commit();

			//
			final Object value1 = view.get("app.name");
			if (value1 != null) {
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
	public void testRemove2() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			final CacheView view = (CacheView) service.getView(CLIENT_JDBC_1, CacheJdbcViewImpl.class);

			//
			view.connect();


			// Update the key
			view.put("app.version", "3.0.0", 1);
			
			//
			view.remove("app.name");

			//
			view.commit();
			
			//
			Thread.sleep(DateL2Helper.SECOND_MILLIS);

			//
			final Object value1 = view.get("app.version");
			if (value1 != null) {
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
	public void testKeys1A() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			final CacheView view = (CacheView) service.getView(CLIENT_JDBC_1, CacheJdbcViewImpl.class);

			//
			view.connect();

			//
			final Enumeration<Object> keys = view.keys();
			while (keys.hasMoreElements()) {

				//
				final String key = (String) keys.nextElement();

				//
				if ((!key.equals("app.name")) && (!key.equals("app.version"))) {
					fail();
				}

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
	public void testKeys1B() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			final CacheView view = (CacheView) service.getView(CLIENT_JDBC_1, CacheJdbcViewImpl.class);

			//
			view.connect();

			// Execute the same statement multiple times
			Enumeration<Object> keys = view.keys();

			//
			keys = view.keys();
			keys = view.keys();
			keys = view.keys();
			keys = view.keys();
			keys = view.keys();
			keys = view.keys();

			//
			while (keys.hasMoreElements()) {

				//
				String key = (String) keys.nextElement();

				//
				if ((!key.equals("app.name")) && (!key.equals("app.version"))) {
					fail();
				}

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
	public void testKeys2A() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			final CacheView view = (CacheView) service.getView(CLIENT_JDBC_4, CacheJdbcViewImpl.class);

			//
			view.connect();

			//
			final Enumeration<Object> keys = view.keys();

			//
			while (keys.hasMoreElements()) {

				//
				final String key = (String) keys.nextElement();

				//
				if ((!key.equals("app.name")) && (!key.equals("app.version"))) {
					fail();
				}

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
	public void testKeys2B() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			final CacheView view = (CacheView) service.getView(CLIENT_JDBC_4, CacheJdbcViewImpl.class);

			//
			view.connect();

			//
			Enumeration<Object> keys = view.keys();
			keys = view.keys();
			keys = view.keys();
			keys = view.keys();
			keys = view.keys();
			keys = view.keys();
			keys = view.keys();
			keys = view.keys();
			keys = view.keys();

			//
			while (keys.hasMoreElements()) {

				//
				final String key = (String) keys.nextElement();

				//
				if ((!key.equals("app.name")) && (!key.equals("app.version"))) {
					fail();
				}

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

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			final CacheView view = (CacheView) service.getView(CLIENT_JDBC_4, CacheJdbcViewImpl.class);

			//
			view.connect();

			//
			int size = view.size();

			//
			if (size != 2) {
				fail();
			}

			//
			view.disconnect();

		} catch (final Exception e) {
			fail();
		}
	}

}
