package com.warework.service.datastore.client;

import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.warework.core.scope.AbstractSerDatastoreJdbcTestCase;
import com.warework.core.scope.ScopeFacade;
import com.warework.service.datastore.DatastoreServiceConstants;
import com.warework.service.datastore.DatastoreServiceFacade;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public class JdbcDatastoreTest extends AbstractSerDatastoreJdbcTestCase {

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testQuery1A() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			service.connect(CLIENT_JDBC_1);

			//
			final ResultSet resultSet = (ResultSet) service.query(CLIENT_JDBC_1, null, "SELECT * FROM PERSON");

			//
			if (resultSet != null) {

				//
				int counter = 0;
				while (resultSet.next()) {
					counter++;
				}

				//
				if (counter != 3) {
					fail();
				}

			} else {
				fail();
			}

			//
			service.disconnect(CLIENT_JDBC_1);

		} catch (final Exception e) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testQuery2A() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			service.connect(CLIENT_JDBC_1);

			//
			final ResultSet resultSet = (ResultSet) service.query(CLIENT_JDBC_1, null, PROVIDER_SQL_STATEMENT,
					"listUsers", null);

			//
			if (resultSet != null) {

				//
				int counter = 0;
				while (resultSet.next()) {
					counter++;
				}

				//
				if (counter != 3) {
					fail();
				}

			} else {
				fail();
			}

			//
			service.disconnect(CLIENT_JDBC_1);

		} catch (final Exception e) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testQuery3A() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			service.connect(CLIENT_JDBC_1);

			//
			final Map<String, Object> values = new HashMap<String, Object>();
			{
				values.put("USER_ID", new Integer(3));
			}

			//
			final ResultSet resultSet = (ResultSet) service.query(CLIENT_JDBC_1, null, PROVIDER_SQL_STATEMENT,
					"getUser", values);

			//
			if (resultSet != null) {

				//
				int counter = 0;
				while (resultSet.next()) {
					counter++;
				}

				//
				if (counter != 1) {
					fail();
				}

			} else {
				fail();
			}

			//
			service.disconnect(CLIENT_JDBC_1);

		} catch (final Exception e) {
			fail();
		}
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testQuery1B() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			service.connect(CLIENT_JDBC_3);

			//
			final ResultRows resultSet = (ResultRows) service.query(CLIENT_JDBC_3, null, "SELECT * FROM PERSON");

			//
			if (resultSet != null) {

				//
				int counter = 0;
				while (resultSet.next()) {
					counter++;
				}

				//
				if (counter != 3) {
					fail();
				}

			} else {
				fail();
			}

			//
			service.disconnect(CLIENT_JDBC_3);

		} catch (final Exception e) {
			fail();
		}
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testUpdate1() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			service.connect(CLIENT_JDBC_1);

			//
			service.update(CLIENT_JDBC_1, null,
					"INSERT INTO PERSON (ID, NAME, DATE_OF_BIRTH, PASSWORD) VALUES (4, 'John Wood', '1978-01-01', 4444)");

			//
			service.commit(CLIENT_JDBC_1);

			//
			final ResultSet resultSet = (ResultSet) service.query(CLIENT_JDBC_1, null,
					"SELECT * FROM PERSON WHERE ID=4");

			//
			if (resultSet != null) {

				//
				int counter = 0;
				while (resultSet.next()) {
					counter++;
				}

				//
				if (counter != 1) {
					fail();
				}

			} else {
				fail();
			}

			//
			service.disconnect(CLIENT_JDBC_1);

		} catch (final Exception e) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testUpdate2() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			service.connect(CLIENT_JDBC_1);

			//
			service.update(CLIENT_JDBC_1, null, PROVIDER_SQL_STATEMENT, "createOneUser", null);

			//
			service.commit(CLIENT_JDBC_1);

			//
			final ResultSet resultSet = (ResultSet) service.query(CLIENT_JDBC_1, null,
					"SELECT * FROM PERSON WHERE ID=4");

			//
			if (resultSet != null) {

				//
				int counter = 0;
				while (resultSet.next()) {
					counter++;
				}

				//
				if (counter != 1) {
					fail();
				}

			} else {
				fail();
			}

			//
			service.disconnect(CLIENT_JDBC_1);

		} catch (final Exception e) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testUpdate3() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			service.connect(CLIENT_JDBC_1);

			//
			final Map<String, Object> values = new HashMap<String, Object>();
			{
				values.put("USER_ID", new Integer(4));
				values.put("USER_NAME", "John Wood");
				values.put("BIRTH_DATE", new Date());
				values.put("PASSWORD", new Integer(4444));
			}

			//
			service.update(CLIENT_JDBC_1, null, PROVIDER_SQL_STATEMENT, "createUser", values);

			//
			service.commit(CLIENT_JDBC_1);

			//
			final ResultSet resultSet = (ResultSet) service.query(CLIENT_JDBC_1, null,
					"SELECT * FROM PERSON WHERE ID=4");

			//
			if (resultSet != null) {

				//
				int counter = 0;
				while (resultSet.next()) {
					counter++;
				}

				//
				if (counter != 1) {
					fail();
				}

			} else {
				fail();
			}

			//
			service.disconnect(CLIENT_JDBC_1);

		} catch (final Exception e) {
			fail();
		}
	}

}
