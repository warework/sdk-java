package com.warework.service.datastore.client;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.warework.core.scope.AbstractSerDatastoreJdbcTestCase;
import com.warework.core.scope.ScopeFacade;
import com.warework.service.datastore.DatastoreServiceConstants;
import com.warework.service.datastore.DatastoreServiceFacade;
import com.warework.service.datastore.client.bean.Person;
import com.warework.service.datastore.view.RdbmsView;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public class JdbcResultRowsTest extends AbstractSerDatastoreJdbcTestCase {

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testGetBoolean1() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			service.connect(CLIENT_JDBC_3);

			//
			final ResultRows result = (ResultRows) service.query(CLIENT_JDBC_3, null,
					"SELECT * FROM PERSON X WHERE X.ID=1");

			//
			if (result != null) {
				while (result.next()) {
					try {

						//
						result.getBoolean(1);

						//
						fail();

					} catch (final Exception e) {
						// DO NOTHING.
					}
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

	/**
	 * 
	 */
	public void testGetShort1() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			service.connect(CLIENT_JDBC_3);

			//
			final ResultRows result = (ResultRows) service.query(CLIENT_JDBC_3, null,
					"SELECT * FROM PERSON X WHERE X.ID=1");

			//
			if (result != null) {
				while (result.next()) {

					//
					final Number password = result.getNumber(5, Short.class);
					if ((password == null) || (!(password instanceof Short))) {
						fail();
					}

					//
					final Number speed = result.getNumber(7, Short.class);
					if (speed != null) {
						fail();
					}

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

	/**
	 * 
	 */
	public void testGetInteger1() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			service.connect(CLIENT_JDBC_3);

			//
			final ResultRows result = (ResultRows) service.query(CLIENT_JDBC_3, null,
					"SELECT * FROM PERSON X WHERE X.ID=1");

			//
			if (result != null) {
				while (result.next()) {

					//
					final Number password = result.getNumber(5, Integer.class);
					if ((password == null) || (!(password instanceof Integer))) {
						fail();
					}

					//
					final Number speed = result.getNumber(7, Integer.class);
					if (speed != null) {
						fail();
					}

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

	/**
	 * 
	 */
	public void testGetLong1() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			service.connect(CLIENT_JDBC_3);

			//
			final ResultRows result = (ResultRows) service.query(CLIENT_JDBC_3, null,
					"SELECT * FROM PERSON X WHERE X.ID=1");

			//
			if (result != null) {
				while (result.next()) {

					//
					final Number id = result.getNumber(1, Long.class);
					if ((id == null) || (!(id instanceof Long))) {
						fail();
					}

					//
					final Number password = result.getNumber(5, Long.class);
					if ((password == null) || (!(password instanceof Long))) {
						fail();
					}

					//
					final Number speed = result.getNumber(7, Long.class);
					if (speed != null) {
						fail();
					}

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

	/**
	 * 
	 */
	public void testGetFloat1() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			service.connect(CLIENT_JDBC_3);

			//
			final ResultRows result = (ResultRows) service.query(CLIENT_JDBC_3, null,
					"SELECT * FROM PERSON X WHERE X.ID=1");

			//
			if (result != null) {
				while (result.next()) {

					//
					final Number password = result.getNumber(5, Float.class);
					if ((password == null) || (!(password instanceof Float))) {
						fail();
					}

					//
					final Number speed = result.getNumber(7, Float.class);
					if (speed != null) {
						fail();
					}

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

	/**
	 * 
	 */
	public void testGetDouble1() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			service.connect(CLIENT_JDBC_3);

			//
			final ResultRows result = (ResultRows) service.query(CLIENT_JDBC_3, null,
					"SELECT * FROM PERSON X WHERE X.ID=1");

			//
			if (result != null) {
				while (result.next()) {

					//
					final Number password = result.getNumber(5, Double.class);
					if ((password == null) || (!(password instanceof Double))) {
						fail();
					}

					//
					final Number speed = result.getNumber(7, Double.class);
					if (speed != null) {
						fail();
					}

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

	/**
	 * 
	 */
	public void testGetBigDecimal1() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			service.connect(CLIENT_JDBC_3);

			//
			final ResultRows result = (ResultRows) service.query(CLIENT_JDBC_3, null,
					"SELECT * FROM PERSON X WHERE X.ID=1");

			//
			if (result != null) {
				while (result.next()) {

					//
					final Number password = result.getNumber(5, BigDecimal.class);
					if ((password == null) || (!(password instanceof BigDecimal))) {
						fail();
					}

					//
					final Number speed = result.getNumber(7, BigDecimal.class);
					if (speed != null) {
						fail();
					}

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

	/**
	 * 
	 */
	public void testGetString1() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			service.connect(CLIENT_JDBC_3);

			//
			final ResultRows result = (ResultRows) service.query(CLIENT_JDBC_3, null,
					"SELECT * FROM PERSON X WHERE X.ID=1");

			//
			if (result != null) {
				while (result.next()) {

					//
					final String name = result.getString(2);
					if (name == null) {
						fail();
					}

					//
					final String color = result.getString(6);
					if (color != null) {
						fail();
					}

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

	/**
	 * 
	 */
	public void testGetDate1() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			service.connect(CLIENT_JDBC_3);

			//
			final ResultRows result = (ResultRows) service.query(CLIENT_JDBC_3, null,
					"SELECT * FROM PERSON X WHERE X.ID=1");

			//
			if (result != null) {
				while (result.next()) {

					//
					Date name = result.getDate(3);
					if (name == null) {
						fail();
					}

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

	/**
	 * 
	 */
	public void testGetBlob1() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			service.connect(CLIENT_JDBC_3);

			//
			final Map<String, Object> variables = new HashMap<String, Object>();
			{
				variables.put("USER_ID", new Integer(4));
				variables.put("USER_NAME", "Michael Ilchenko");
				variables.put("BIRTH_DATE", "1978-01-01");
				variables.put("PASSWORD", "0000");
				variables.put("PICTURE", "ABC".getBytes());
			}

			//
			final RdbmsView view = (RdbmsView) service.getView(CLIENT_JDBC_3, JdbcViewImpl.class);

			//
			view.executeUpdateByName("createUserImage", variables, null);

			//
			view.commit();

			//
			final ResultRows result = (ResultRows) view.executeQuery("SELECT * FROM PERSON X WHERE X.ID=4", null, -1,
					-1);

			//
			if (result != null) {
				while (result.next()) {

					//
					byte[] picture = result.getBlob(4);
					if (picture == null) {
						fail();
					}

					//
					String text = new String(picture);
					if (!text.equals("ABC")) {
						fail();
					}

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

	/**
	 * 
	 */
	public void testGetBean1() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			service.connect(CLIENT_JDBC_3);

			//
			final Map<String, Object> variables = new HashMap<String, Object>();
			{
				variables.put("USER_ID", new Integer(4));
				variables.put("USER_NAME", "Michael Ilchenko");
				variables.put("BIRTH_DATE", "1978-01-01");
				variables.put("PASSWORD", "0000");
				variables.put("PICTURE", "ABC".getBytes());
			}

			//
			final RdbmsView view = (RdbmsView) service.getView(CLIENT_JDBC_3, JdbcViewImpl.class);

			//
			view.executeUpdateByName("createUserImage", variables, null);

			//
			view.commit();

			//
			final ResultRows result = (ResultRows) view.executeQuery("SELECT * FROM PERSON X WHERE X.ID=4", null, -1,
					-1);

			//
			final Hashtable<Object, Object> mapping = new Hashtable<Object, Object>();
			{
				mapping.put("ID", "id");
				mapping.put("NAME", "name");
				mapping.put("DATE_OF_BIRTH", "dateOfBirth");
				mapping.put("PICTURE", "picture");
				mapping.put("PASSWORD", "password");
				mapping.put("COLOR", "color");
				mapping.put("SPEED", "speed");
			}

			//
			if (result != null) {
				while (result.next()) {

					//
					final Person bean = result.getBean(Person.class, mapping);

					//
					if ((bean == null) || (bean.getId().intValue() != 4)) {
						fail();
					}

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

	/**
	 * 
	 */
	public void testGetBean2() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			service.connect(CLIENT_JDBC_3);

			//
			final Hashtable<String, Object> variables = new Hashtable<String, Object>();
			{
				variables.put("USER_ID", new Integer(4));
				variables.put("USER_NAME", "Michael Ilchenko");
				variables.put("BIRTH_DATE", "1978-01-01");
				variables.put("PASSWORD", "0000");
				variables.put("PICTURE", "ABC".getBytes());
			}

			//
			final RdbmsView view = (RdbmsView) service.getView(CLIENT_JDBC_3, JdbcViewImpl.class);

			//
			view.executeUpdateByName("createUserImage", variables, null);

			//
			view.commit();

			//
			final ResultRows result = (ResultRows) view.executeQuery("SELECT * FROM PERSON X WHERE X.ID=4", null, -1,
					-1);

			//
			if (result != null) {
				while (result.next()) {

					//
					final Person bean = (Person) result.getBean(Person.class, null);

					//
					if ((bean == null) || (bean.getId().intValue() != 4)) {
						fail();
					}

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

	/**
	 * 
	 */
	public void testGetBean3() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			service.connect(CLIENT_JDBC_3);

			//
			RdbmsView view = (RdbmsView) service.getView(CLIENT_JDBC_3, JdbcViewImpl.class);

			//
			ResultRows resultRows = (ResultRows) view.executeQuery("SELECT * FROM PERSON X", null, -1, -1);

			//
			final Map<Object, Object> mapping = new HashMap<Object, Object>();
			{
				mapping.put("ID", "id");
				mapping.put("NAME", "name");
				mapping.put("DATE_OF_BIRTH", "dateOfBirth");
				mapping.put("PICTURE", "picture");
				mapping.put("PASSWORD", "password");
				mapping.put("COLOR", "color");
				mapping.put("SPEED", "speed");
			}

			//
			final List<Person> resultList = AbstractResultRows.toList(resultRows, Person.class, mapping);
			if ((resultList == null) || (resultList.size() != 3)) {
				fail();
			}

			//
			service.disconnect(CLIENT_JDBC_3);

		} catch (final Exception e) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testGetBean4() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			service.connect(CLIENT_JDBC_3);

			//
			final RdbmsView ddbb = (RdbmsView) service.getView(CLIENT_JDBC_3, JdbcViewImpl.class);

			//
			final ResultRows resultRows = (ResultRows) ddbb.executeQuery("SELECT * FROM PERSON X", null, -1, -1);

			//
			final List<Person> resultList = AbstractResultRows.toList(resultRows, Person.class, null);
			if ((resultList == null) || (resultList.size() != 3)) {
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
	public void testNext1() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			service.connect(CLIENT_JDBC_3);

			//
			final ResultRows result = (ResultRows) service.query(CLIENT_JDBC_3, null, "SELECT * FROM PERSON");

			//
			if (result != null) {

				//
				int counter = 0;
				while (result.next()) {
					counter = counter + 1;
				}

				//
				if (result.row() != counter) {
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

	/**
	 * 
	 */
	public void testRow1() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			service.connect(CLIENT_JDBC_3);

			//
			final ResultRows result = (ResultRows) service.query(CLIENT_JDBC_3, null, "SELECT * FROM PERSON");

			//
			if (result != null) {

				//
				int counter = 0;
				while (result.next()) {
					counter = counter + 1;
				}

				//
				if (result.row() != counter) {
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

	/**
	 * 
	 */
	public void testClose1() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			service.connect(CLIENT_JDBC_3);

			//
			final ResultRows result = (ResultRows) service.query(CLIENT_JDBC_3, null, "SELECT * FROM PERSON");

			//
			if (result != null) {

				//
				int counter = 0;
				while (result.next()) {
					counter = counter + 1;
				}

				//
				if (result.row() != counter) {
					fail();
				}

				//
				result.close();

			} else {
				fail();
			}

			//
			service.disconnect(CLIENT_JDBC_3);

		} catch (final Exception e) {
			fail();
		}
	}

}
