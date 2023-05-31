package com.warework.provider;

import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import com.warework.core.model.Scope;
import com.warework.core.scope.AbstractModDatastoreExtTestCase;
import com.warework.core.scope.ScopeException;
import com.warework.core.scope.ScopeFacade;
import com.warework.core.scope.ScopeL1Constants;
import com.warework.core.util.helper.DateL2Helper;
import com.warework.core.util.helper.ReflectionL2Helper;
import com.warework.core.util.helper.ResourceL1Helper;
import com.warework.core.util.helper.ResourceL2Helper;
import com.warework.service.datastore.query.oo.Operator;
import com.warework.service.datastore.query.oo.Query;
import com.warework.service.datastore.query.oo.SampleBean;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public class ObjectQueryProviderTest extends AbstractModDatastoreExtTestCase {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	//
	private final static String CLASS_NAME = ReflectionL2Helper.getClassName(SampleBean.class);

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testGetObject1() {
		try {

			//
			final ScopeFacade system = createScope();

			//
			final Query<?> query = (Query<?>) system.getObject(ObjectQueryProvider.DEFAULT_PROVIDER_NAME, "sample1");
			if (!CLASS_NAME.equals(query.toString())) {
				fail();
			}

		} catch (final Exception e) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testGetObject2() {
		try {

			//
			final ScopeFacade system = createScope();

			//
			final Query<?> query = (Query<?>) system.getObject(ObjectQueryProvider.DEFAULT_PROVIDER_NAME, "sample2");

			//
			String result = CLASS_NAME;
			if (!result.equals(query.toString())) {
				fail();
			}

			//
			query.getWhere().addVariable("user.name", "Alf");

			//
			result = CLASS_NAME + " WHERE (name = 'Alf')";
			if (!result.equals(query.toString())) {
				fail();
			}

		} catch (final Exception e) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testGetObject3() {
		try {

			//
			final ScopeFacade system = createScope();

			//
			final Query<?> query = (Query<?>) system.getObject(ObjectQueryProvider.DEFAULT_PROVIDER_NAME, "sample3");

			//
			String result = CLASS_NAME;
			if (!result.equals(query.toString())) {
				fail();
			}

			//
			query.getWhere().addVariable("user.name", "Alf");

			//
			result = CLASS_NAME + " WHERE (name = 'Alf')";
			if (!result.equals(query.toString())) {
				fail();
			}

		} catch (final Exception e) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testGetObject4a() {
		try {

			//
			final ScopeFacade system = createScope();

			//
			final Query<?> query = (Query<?>) system.getObject(ObjectQueryProvider.DEFAULT_PROVIDER_NAME, "sample4a");

			//
			String result = CLASS_NAME + " WHERE ((name IS_NULL) AND (name IS_NOT_NULL))";
			if (!result.equals(query.toString())) {
				fail();
			}

			//
			query.getWhere().addVariable("user.name", "Alf");

			//
			result = CLASS_NAME + " WHERE ((name " + Operator.EQUAL_TO + " 'Alf')" + " AND (name "
					+ Operator.NOT_EQUAL_TO + " 'Alf')" + " AND (name " + Operator.GREATER_THAN + " 'Alf')"
					+ " AND (name " + Operator.GREATER_THAN_OR_EQUAL_TO + " 'Alf')" + " AND (name " + Operator.LESS_THAN
					+ " 'Alf')" + " AND (name " + Operator.LESS_THAN_OR_EQUAL_TO + " 'Alf')" + " AND (name "
					+ Operator.LIKE + " 'Alf')" + " AND (name " + Operator.NOT_LIKE + " 'Alf')" + " AND (name "
					+ Operator.IS_NULL + ")" + " AND (name " + Operator.IS_NOT_NULL + "))";

			//
			if (!result.equals(query.toString())) {
				fail();
			}

		} catch (final Exception e) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testGetObject4b() {
		try {

			//
			final ScopeFacade system = createScope();

			//
			final Query<?> query = (Query<?>) system.getObject(ObjectQueryProvider.DEFAULT_PROVIDER_NAME, "sample4b");

			//
			String result = CLASS_NAME + " WHERE ((name IS_NULL) AND (name IS_NOT_NULL))";
			if (!result.equals(query.toString())) {
				fail();
			}

			//
			query.getWhere().addVariable("user.name", "Alf");

			//
			result = CLASS_NAME + " WHERE ((name " + Operator.EQUAL_TO + " 'Alf')" + " AND (name "
					+ Operator.NOT_EQUAL_TO + " 'Alf')" + " AND (name " + Operator.GREATER_THAN + " 'Alf')"
					+ " AND (name " + Operator.GREATER_THAN_OR_EQUAL_TO + " 'Alf')" + " AND (name " + Operator.LESS_THAN
					+ " 'Alf')" + " AND (name " + Operator.LESS_THAN_OR_EQUAL_TO + " 'Alf')" + " AND (name "
					+ Operator.LIKE + " 'Alf')" + " AND (name " + Operator.NOT_LIKE + " 'Alf')" + " AND (name "
					+ Operator.IS_NULL + ")" + " AND (name " + Operator.IS_NOT_NULL + "))";

			//
			if (!result.equals(query.toString())) {
				fail();
			}

		} catch (final Exception e) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testGetObject5() {
		try {

			//
			final ScopeFacade system = createScope();

			//
			final Query<?> query = (Query<?>) system.getObject(ObjectQueryProvider.DEFAULT_PROVIDER_NAME, "sample5");

			//
			final Date date = DateL2Helper.toDateDDMMYYYY("13/08/1978", TimeZone.getTimeZone("europe/london"));

			//
			final String dateStr = DateL2Helper.toString(date, TimeZone.getTimeZone("europe/london"), Locale.UK,
					"dd/MM/yyyy HH:mm:ss");

			//
			final String result1 = CLASS_NAME + " WHERE ((name " + Operator.EQUAL_TO + " '" + dateStr + "')"
					+ " AND (name " + Operator.NOT_EQUAL_TO + " '" + dateStr + "')" + " AND (name "
					+ Operator.GREATER_THAN + " '" + dateStr + "')" + " AND (name " + Operator.GREATER_THAN_OR_EQUAL_TO
					+ " '" + dateStr + "')" + " AND (name " + Operator.LESS_THAN + " '" + dateStr + "')" + " AND (name "
					+ Operator.LESS_THAN_OR_EQUAL_TO + " '" + dateStr + "')" + " AND (name " + Operator.LIKE + " '"
					+ dateStr + "')" + " AND (name " + Operator.NOT_LIKE + " '" + dateStr + "')" + " AND (name "
					+ Operator.IS_NULL + ")" + " AND (name " + Operator.IS_NOT_NULL + "))";

			//
			final String result2 = query.toString();

			//
			if (!result1.equals(result2)) {
				fail();
			}

		} catch (final Exception e) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testGetObject6() {
		try {

			//
			final ScopeFacade system = createScope();

			//
			final Query<?> query = (Query<?>) system.getObject(ObjectQueryProvider.DEFAULT_PROVIDER_NAME, "sample6");

			//
			String name = "Matthew";

			//
			final String result = CLASS_NAME + " WHERE ((name " + Operator.EQUAL_TO + " '" + name + "')" + " AND (name "
					+ Operator.NOT_EQUAL_TO + " '" + name + "')" + " AND (name " + Operator.GREATER_THAN + " '" + name
					+ "')" + " AND (name " + Operator.GREATER_THAN_OR_EQUAL_TO + " '" + name + "')" + " AND (name "
					+ Operator.LESS_THAN + " '" + name + "')" + " AND (name " + Operator.LESS_THAN_OR_EQUAL_TO + " '"
					+ name + "')" + " AND (name " + Operator.LIKE + " '" + name + "')" + " AND (name "
					+ Operator.NOT_LIKE + " '" + name + "')" + " AND (name " + Operator.IS_NULL + ")" + " AND (name "
					+ Operator.IS_NOT_NULL + "))";

			//
			if (!result.equals(query.toString())) {
				fail();
			}

		} catch (final Exception e) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testGetObject7() {
		try {

			//
			final ScopeFacade system = createScope();

			//
			final Query<?> query = (Query<?>) system.getObject(ObjectQueryProvider.DEFAULT_PROVIDER_NAME, "sample7");

			//
			String result = CLASS_NAME + " WHERE ((name " + Operator.NOT_LIKE + " 'Matthew')" + " AND (name "
					+ Operator.NOT_LIKE + " 'James')" + " AND (name IS_NOT_NULL))";
			if (!result.equals(query.toString())) {
				fail();
			}

			//
			query.getWhere().addVariable("user.name", "Alf");

			//
			result = CLASS_NAME + " WHERE ((name " + Operator.NOT_LIKE + " 'Alf')" + " AND (name " + Operator.NOT_LIKE
					+ " 'Matthew')" + " AND (name " + Operator.NOT_LIKE + " 'James')" + " AND (name IS_NOT_NULL))";

			//
			if (!result.equals(query.toString())) {
				fail();
			}

		} catch (final Exception e) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testGetObject8() {
		try {

			//
			final ScopeFacade system = createScope();

			//
			final Query<?> query = (Query<?>) system.getObject(ObjectQueryProvider.DEFAULT_PROVIDER_NAME, "sample8");

			//
			String result = CLASS_NAME + " WHERE ((name " + Operator.NOT_LIKE + " 'James')" + " AND ((name "
					+ Operator.NOT_LIKE + " 'Arnold')" + " OR (name IS_NOT_NULL)))";
			if (!result.equals(query.toString())) {
				fail();
			}

		} catch (final Exception e) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testGetObject9() {
		try {

			//
			final ScopeFacade system = createScope();

			//
			final Query<?> query = (Query<?>) system.getObject(ObjectQueryProvider.DEFAULT_PROVIDER_NAME, "sample9");

			//
			String result = CLASS_NAME + " WHERE NOT ((name " + Operator.NOT_LIKE + " 'James')" + " AND ((name "
					+ Operator.NOT_LIKE + " 'Arnold')" + " OR (name IS_NOT_NULL)))";
			if (!result.equals(query.toString())) {
				fail();
			}

		} catch (final Exception e) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testGetObject10() {
		try {

			//
			final ScopeFacade system = createScope();

			//
			final Query<?> query = (Query<?>) system.getObject(ObjectQueryProvider.DEFAULT_PROVIDER_NAME, "sample10");

			//
			final String result = CLASS_NAME + " ORDER BY name ASC";
			if (!result.equals(query.toString())) {
				fail();
			}

		} catch (final Exception e) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testGetObject11() {
		try {

			//
			final ScopeFacade system = createScope();

			//
			final Query<?> query = (Query<?>) system.getObject(ObjectQueryProvider.DEFAULT_PROVIDER_NAME, "sample11");

			//
			String result = CLASS_NAME + " ORDER BY name ASC, birthDate DESC, phone ASC";
			if (!result.equals(query.toString())) {
				fail();
			}

		} catch (final Exception e) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testGetObject12() {
		try {

			//
			final ScopeFacade system = createScope();

			//
			final Query<?> query = (Query<?>) system.getObject(ObjectQueryProvider.DEFAULT_PROVIDER_NAME, "sample12");

			//
			final String result = CLASS_NAME + " PAGE 2 SIZE 9";
			if (!result.equals(query.toString())) {
				fail();
			}

		} catch (final Exception e) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testGetObject13() {
		try {

			//
			final ScopeFacade system = createScope();

			//
			final Query<?> query = (Query<?>) system.getObject(ObjectQueryProvider.DEFAULT_PROVIDER_NAME, "sample13");

			//
			String result = CLASS_NAME + " WHERE ((name " + Operator.NOT_LIKE + " 'James')" + " AND ((name "
					+ Operator.NOT_LIKE + " 'Arnold')"
					+ " OR (name IS_NOT_NULL))) ORDER BY name ASC, birthDate DESC, phone ASC PAGE 2 SIZE 9";
			if (!result.equals(query.toString())) {
				fail();
			}

		} catch (final Exception e) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testGetObject14() {
		try {

			//
			final ScopeFacade system = createScope();

			//
			final Query<?> query = (Query<?>) system.getObject(ObjectQueryProvider.DEFAULT_PROVIDER_NAME, "sample14");

			//
			final String name = (String) query.getParameter("name");
			if (!name.equals("sample")) {
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
	 */
	private ScopeFacade createScope() throws ScopeException {

		//
		final Scope config = new Scope("test");

		//
		config.setInitParameter(ScopeL1Constants.PARAMETER_CONTEXT_LOADER, ObjectQueryProviderTest.class);

		//
		config.setProvider(ObjectQueryProvider.DEFAULT_PROVIDER_NAME, ObjectQueryProvider.class, null);
		config.setProviderParameter(ObjectQueryProvider.DEFAULT_PROVIDER_NAME,
				ObjectQueryProvider.PARAMETER_CONFIG_TARGET,
				ResourceL1Helper.DIRECTORY_SEPARATOR + ResourceL2Helper.DIRECTORY_META_INF
						+ ResourceL1Helper.DIRECTORY_SEPARATOR + PROJECT_NAME + ResourceL1Helper.DIRECTORY_SEPARATOR
						+ "statement" + ResourceL1Helper.DIRECTORY_SEPARATOR + "xoq");

		//
		config.setProvider(DummyProvider.DEFAULT_PROVIDER_NAME, DummyProvider.class, null);

		//
		return create(config);

	}

}
