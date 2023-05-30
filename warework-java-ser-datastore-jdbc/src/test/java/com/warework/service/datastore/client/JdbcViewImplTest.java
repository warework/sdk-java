package com.warework.service.datastore.client;

import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import com.warework.core.callback.AbstractCallback;
import com.warework.core.scope.AbstractSerDatastoreJdbcTestCase;
import com.warework.core.scope.ScopeFacade;
import com.warework.service.datastore.DatastoreServiceConstants;
import com.warework.service.datastore.DatastoreServiceFacade;
import com.warework.service.datastore.view.RdbmsView;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public class JdbcViewImplTest extends AbstractSerDatastoreJdbcTestCase {

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testExecuteQueryA1() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			final RdbmsView view = (RdbmsView) service.getView(CLIENT_JDBC_1, JdbcViewImpl.class);

			//
			view.connect();

			//
			final ResultSet resultSet = (ResultSet) view.executeQuery("SELECT * FROM PERSON", null, -1, -1);

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
			view.disconnect();

		} catch (final Exception e) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testExecuteQueryA2() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			final RdbmsView view = (RdbmsView) service.getView(CLIENT_JDBC_1, JdbcViewImpl.class);

			//
			view.connect();

			//
			final ResultSet resultSet = (ResultSet) view.executeQuery("SELECT * FROM PERSON", null, 2, 2);

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
			view.disconnect();

		} catch (final Exception e) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testExecuteQueryA3() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			final RdbmsView view = (RdbmsView) service.getView(CLIENT_JDBC_1, JdbcViewImpl.class);

			//
			view.connect();

			//
			final ResultSet resultSet = (ResultSet) view.executeQuery("SELECT * FROM PERSON", null, 1, 2);

			//
			if (resultSet != null) {

				//
				int counter = 0;
				while (resultSet.next()) {
					counter++;
				}

				//
				if (counter != 2) {
					fail();
				}

			} else {
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
	public void testExecuteQueryA4() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			final RdbmsView view = (RdbmsView) service.getView(CLIENT_JDBC_1, JdbcViewImpl.class);

			//
			view.connect();

			//
			final ResultSet resultSet = (ResultSet) view.executeQuery("SELECT * FROM PERSON", null, 1, 1);

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
			view.disconnect();

		} catch (final Exception e) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testExecuteQueryA5() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			final RdbmsView view = (RdbmsView) service.getView(CLIENT_JDBC_1, JdbcViewImpl.class);

			//
			view.connect();

			//
			final ResultSet resultSet = (ResultSet) view.executeQuery("SELECT * FROM PERSON", null, 3, 1);

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
			view.disconnect();

		} catch (final Exception e) {
			fail();
		}
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testExecuteQueryB1() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			final RdbmsView view = (RdbmsView) service.getView(CLIENT_JDBC_1, JdbcViewImpl.class);

			//
			view.connect();

			//
			view.executeQuery("SELECT * FROM PERSON", null, -1, -1, new AbstractCallback(service.getScopeFacade()) {

				protected void onFailure(final Throwable exception) {
					fail();
				}

				protected void onSuccess(final Object result) {
					try {

						//
						final ResultSet resultSet = (ResultSet) result;

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

					} catch (final Exception e) {
						fail();
					}

				}
			});

			//
			view.disconnect();

		} catch (final Exception e) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testExecuteQueryB2() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			final RdbmsView view = (RdbmsView) service.getView(CLIENT_JDBC_1, JdbcViewImpl.class);

			//
			view.connect();

			//
			view.executeQuery("SELECT * FROM PERSON", null, 3, 1, new AbstractCallback(service.getScopeFacade()) {

				protected void onFailure(final Throwable exception) {
					fail();
				}

				protected void onSuccess(final Object result) {
					try {

						//
						final ResultSet resultSet = (ResultSet) result;

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

					} catch (Exception e) {
						fail();
					}

				}
			});

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
	public void testExecuteQueryByNameA1() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			final RdbmsView view = (RdbmsView) service.getView(CLIENT_JDBC_1, JdbcViewImpl.class);

			//
			view.connect();

			//
			final ResultSet resultSet = (ResultSet) view.executeQueryByName("listUsers", null, -1, -1);

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
			view.disconnect();

		} catch (final Exception e) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testExecuteQueryByNameA2() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			final RdbmsView view = (RdbmsView) service.getView(CLIENT_JDBC_1, JdbcViewImpl.class);

			//
			view.connect();

			//
			final ResultSet resultSet = (ResultSet) view.executeQueryByName("listUsers", null, 1, 2);

			//
			if (resultSet != null) {

				//
				int counter = 0;
				while (resultSet.next()) {
					counter++;
				}

				//
				if (counter != 2) {
					fail();
				}

			} else {
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
	public void testExecuteQueryByNameA3() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			final RdbmsView view = (RdbmsView) service.getView(CLIENT_JDBC_1, JdbcViewImpl.class);

			//
			view.connect();

			//
			final ResultSet resultSet = (ResultSet) view.executeQueryByName("listUsers", null, 2, 2);

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
			view.disconnect();

		} catch (final Exception e) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testExecuteQueryByNameA4() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			final RdbmsView view = (RdbmsView) service.getView(CLIENT_JDBC_1, JdbcViewImpl.class);

			//
			view.connect();

			//
			final ResultSet resultSet = (ResultSet) view.executeQueryByName("listUsers", null, 1, 1);

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
			view.disconnect();

		} catch (final Exception e) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testExecuteQueryByNameA5() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			final RdbmsView view = (RdbmsView) service.getView(CLIENT_JDBC_1, JdbcViewImpl.class);

			//
			view.connect();

			//
			final ResultSet resultSet = (ResultSet) view.executeQueryByName("listUsers", null, 2, 1);

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
			view.disconnect();

		} catch (final Exception e) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testExecuteQueryByNameA6() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			final RdbmsView view = (RdbmsView) service.getView(CLIENT_JDBC_1, JdbcViewImpl.class);

			//
			view.connect();

			//
			final ResultSet resultSet = (ResultSet) view.executeQueryByName("listUsers", null, 3, 1);

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
			view.disconnect();

		} catch (final Exception e) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testExecuteQueryByNameA7() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			final RdbmsView view = (RdbmsView) service.getView(CLIENT_JDBC_1, JdbcViewImpl.class);

			//
			view.connect();

			//
			final Hashtable<String, Object> values = new Hashtable<String, Object>();
			{
				values.put("USER_ID", new Integer(3));
			}

			//
			final ResultSet resultSet = (ResultSet) view.executeQueryByName("getUser", values, -1, -1);

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
			view.disconnect();

		} catch (final Exception e) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testExecuteQueryByNameA8() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			final RdbmsView view = (RdbmsView) service.getView(CLIENT_JDBC_1, JdbcViewImpl.class);

			//
			view.connect();

			//
			final Hashtable<String, Object> values = new Hashtable<String, Object>();
			{
				values.put("USER_ID", new Integer(3));
			}

			//
			final ResultSet resultSet = (ResultSet) view.executeQueryByName("getUser", values, -1, -1);

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
			view.disconnect();

		} catch (final Exception e) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testExecuteQueryByNameA9() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			final RdbmsView view = (RdbmsView) service.getView(CLIENT_JDBC_1, JdbcViewImpl.class);

			//
			view.connect();

			//
			final Map<String, Object> values = new HashMap<String, Object>();
			{
				values.put("USER_ID", new Integer(3));
			}

			//
			final ResultSet resultSet = (ResultSet) view.executeQueryByName("getUser", values, 1, 1);

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
			view.disconnect();

		} catch (final Exception e) {
			fail();
		}
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testExecuteQueryByNameB1() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			final RdbmsView view = (RdbmsView) service.getView(CLIENT_JDBC_1, JdbcViewImpl.class);

			//
			view.connect();

			//
			view.executeQueryByName("listUsers", null, -1, -1, new AbstractCallback(service.getScopeFacade()) {

				protected void onFailure(final Throwable exception) {
					fail();
				}

				protected void onSuccess(final Object result) {
					try {

						//
						final ResultSet resultSet = (ResultSet) result;

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

					} catch (final Exception e) {
						fail();
					}

				}
			});

			//
			view.disconnect();

		} catch (final Exception e) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testExecuteQueryByNameB2() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			final RdbmsView view = (RdbmsView) service.getView(CLIENT_JDBC_1, JdbcViewImpl.class);

			//
			view.connect();

			//
			final Hashtable<String, Object> values = new Hashtable<String, Object>();
			{
				values.put("USER_ID", new Integer(3));
			}

			//
			view.executeQueryByName("getUser", values, 1, 1, new AbstractCallback(service.getScopeFacade()) {

				protected void onFailure(final Throwable exception) {
					fail();
				}

				protected void onSuccess(final Object result) {
					try {

						//
						final ResultSet resultSet = (ResultSet) result;

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

					} catch (final Exception e) {
						fail();
					}

				}
			});

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
	public void testExecuteQueryByNameC1() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			final RdbmsView view = (RdbmsView) service.getView(CLIENT_JDBC_1, JdbcViewImpl.class);

			//
			view.connect();

			//
			view.executeQueryByName(PROVIDER_SQL_STATEMENT, "listUsers", null, -1, -1,
					new AbstractCallback(service.getScopeFacade()) {

						protected void onFailure(final Throwable exception) {
							fail();
						}

						protected void onSuccess(final Object result) {
							try {

								//
								final ResultSet resultSet = (ResultSet) result;

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

							} catch (final Exception e) {
								fail();
							}

						}
					});

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
	public void testExecuteUpdate1() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			final RdbmsView view = (RdbmsView) service.getView(CLIENT_JDBC_1, JdbcViewImpl.class);

			//
			view.connect();

			//
			view.executeUpdate(
					"INSERT INTO PERSON (ID, NAME, DATE_OF_BIRTH, PASSWORD) VALUES (4, 'John Wood', '1978-01-01', 4444)",
					null, null);

			//
			view.commit();

			//
			final ResultSet resultSet = (ResultSet) view.executeQuery("SELECT * FROM PERSON WHERE ID=4", null, -1, -1);

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
			view.disconnect();

		} catch (final Exception e) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testExecuteUpdate2() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			final RdbmsView view = (RdbmsView) service.getView(CLIENT_JDBC_1, JdbcViewImpl.class);

			//
			view.connect();

			//
			final String sql1 = "INSERT INTO PERSON (ID, NAME, DATE_OF_BIRTH, PASSWORD) VALUES (4, 'John Wood', '1978-01-01', 4444)";
			final String sql2 = "INSERT INTO PERSON (ID, NAME, DATE_OF_BIRTH, PASSWORD) VALUES (5, 'John Wood 2', '1978-01-01', 4444)";

			//
			view.executeUpdate(sql1 + ";" + sql2, null, new Character(';'));

			//
			view.commit();

			//
			final ResultSet resultSet = (ResultSet) view.executeQuery("SELECT * FROM PERSON WHERE ID=4 OR ID=5", null,
					-1, -1);

			//
			if (resultSet != null) {

				//
				int counter = 0;
				while (resultSet.next()) {
					counter++;
				}

				//
				if (counter != 2) {
					fail();
				}

			} else {
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
	public void testExecuteUpdateByNameA1() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			final RdbmsView view = (RdbmsView) service.getView(CLIENT_JDBC_1, JdbcViewImpl.class);

			//
			view.connect();

			//
			view.executeUpdateByName("createOneUser", null, null);

			//
			view.commit();

			//
			final ResultSet resultSet = (ResultSet) view.executeQuery("SELECT * FROM PERSON WHERE ID=4 OR ID=5", null,
					-1, -1);

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
			view.disconnect();

		} catch (final Exception e) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testExecuteUpdateByNameA2() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			final RdbmsView view = (RdbmsView) service.getView(CLIENT_JDBC_1, JdbcViewImpl.class);

			//
			view.connect();

			//
			view.executeUpdateByName("createThreeUsers", null, new Character(';'));

			//
			view.commit();

			//
			final ResultSet resultSet = (ResultSet) view.executeQuery("SELECT * FROM PERSON WHERE ID=4 OR ID=5 OR ID=6",
					null, -1, -1);

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
			view.disconnect();

		} catch (final Exception e) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testExecuteUpdateByNameA3() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			final RdbmsView view = (RdbmsView) service.getView(CLIENT_JDBC_1, JdbcViewImpl.class);

			//
			view.connect();

			//
			final Hashtable<String, Object> values = new Hashtable<String, Object>();
			{
				values.put("USER_ID", new Integer(4));
				values.put("USER_NAME", "John Wood");
				values.put("BIRTH_DATE", new Date());
				values.put("PASSWORD", new Integer(4444));
			}

			//
			view.executeUpdateByName("createUser", values, null);

			//
			view.commit();

			//
			final ResultSet resultSet = (ResultSet) view.executeQuery("SELECT * FROM PERSON WHERE ID=4 OR ID=5 OR ID=6",
					null, -1, -1);

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
			view.disconnect();

		} catch (final Exception e) {
			fail();
		}
	}

	/**
	 * Test JDBCNullType.
	 */
	public void testExecuteUpdateByNameA4() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			final RdbmsView view = (RdbmsView) service.getView(CLIENT_JDBC_1, JdbcViewImpl.class);

			//
			view.connect();

			//
			final Hashtable<String, Object> values = new Hashtable<String, Object>();
			{
				values.put("USER_ID", new Integer(4));
				values.put("USER_NAME", "John Wood");
				values.put("BIRTH_DATE", JdbcNullType.DATE);
				values.put("PASSWORD", new Integer(5555));
			}

			//
			view.executeUpdateByName("createUser", values, null);

			//
			view.commit();

			//
			final ResultSet resultSet = (ResultSet) view.executeQuery("SELECT * FROM PERSON WHERE ID=4 OR ID=5 OR ID=6",
					null, -1, -1);

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
			view.disconnect();

		} catch (final Exception e) {
			fail();
		}
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testExecuteUpdateByNameB1() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			final RdbmsView view = (RdbmsView) service.getView(CLIENT_JDBC_1, JdbcViewImpl.class);

			//
			view.connect();

			//
			view.executeUpdateByName("createThreeUsers", null, new Character(';'),
					new AbstractCallback(service.getScopeFacade()) {

						protected void onFailure(final Throwable exception) {
							fail();
						}

						protected void onSuccess(final Object result) {
							System.out.println("progress: " + getBatch().progress());
						}

					});

			//
			view.commit();

			//
			final ResultSet resultSet = (ResultSet) view.executeQuery("SELECT * FROM PERSON WHERE ID=4 OR ID=5 OR ID=6",
					null, -1, -1);

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
			view.disconnect();

		} catch (final Exception e) {
			fail();
		}
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testExecuteUpdateByNameC1() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			final RdbmsView view = (RdbmsView) service.getView(CLIENT_JDBC_1, JdbcViewImpl.class);

			//
			view.connect();

			//
			view.executeUpdateByName(PROVIDER_SQL_STATEMENT, "createThreeUsers", null, new Character(';'),
					new AbstractCallback(service.getScopeFacade()) {

						protected void onFailure(final Throwable exception) {
							fail();
						}

						protected void onSuccess(final Object result) {
							System.out.println("progress: " + getBatch().progress());
						}

					});

			//
			view.commit();

			//
			final ResultSet resultSet = (ResultSet) view.executeQuery("SELECT * FROM PERSON WHERE ID=4 OR ID=5 OR ID=6",
					null, -1, -1);

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
			view.disconnect();

		} catch (final Exception e) {
			fail();
		}
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testTransactions1() {
		try {

			// Create the Scope.
			final ScopeFacade system = create("test");

			// Get the Datastore Service.
			final DatastoreServiceFacade service = (DatastoreServiceFacade) system
					.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

			//
			final RdbmsView view = (RdbmsView) service.getView(CLIENT_JDBC_1, JdbcViewImpl.class);

			//
			view.connect();

			//
			view.beginTransaction();

			//
			Hashtable<String, Object> values = new Hashtable<String, Object>();
			{
				values.put("USER_ID", new Integer(4));
				values.put("USER_NAME", "John Wood");
				values.put("BIRTH_DATE", new Date());
				values.put("PASSWORD", new Integer(4444));
			}

			//
			view.executeUpdateByName("createUser", values, null);

			//
			view.commit();

			//
			view.beginTransaction();

			//
			values = new Hashtable<String, Object>();
			{
				values.put("USER_ID", new Integer(5));
				values.put("USER_NAME", "James Wood");
				values.put("BIRTH_DATE", new Date());
				values.put("PASSWORD", new Integer(5555));
			}

			//
			view.executeUpdateByName("createUser", values, null);

			//
			view.rollback();

			//
			final ResultSet resultSet = (ResultSet) view.executeQuery("SELECT * FROM PERSON WHERE ID=4 OR ID=5 OR ID=6",
					null, -1, -1);

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
			view.disconnect();

		} catch (final Exception e) {
			fail();
		}
	}

}
