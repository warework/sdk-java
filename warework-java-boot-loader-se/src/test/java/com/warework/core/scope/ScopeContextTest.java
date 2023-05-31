package com.warework.core.scope;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;

import com.warework.core.model.ser.ProxyService;
import com.warework.core.model.ser.Scope;
import com.warework.core.service.ProxyServiceConstants;
import com.warework.core.util.helper.ResourceL1Helper;
import com.warework.core.util.helper.ResourceL2Helper;
import com.warework.core.util.helper.StringL2Helper;
import com.warework.loader.ObjectDeserializerLoader;
import com.warework.provider.SingletonProvider;
import com.warework.service.log.LogServiceConstants;
import com.warework.service.log.LogServiceImpl;
import com.warework.service.log.client.connector.ConsoleConnector;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public class ScopeContextTest extends AbstractBootLoaderSeTestCase {

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(final String[] args) throws IOException {

		//
		serializeScope();

		//
		serializeLogService();

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Create Scope from serialized file.
	 */
	public void testCreate1() {
		try {
			create("system-1", "test", null);
		} catch (final ScopeException e) {
			fail();
		}
	}

	/**
	 * Create Scope from XML file.
	 */
	public void testCreate2() {
		try {
			create("system-2", "test", null);
		} catch (final ScopeException e) {
			fail();
		}
	}

	/**
	 * Create Scope from JSON file.
	 */
	public void testCreate3() {
		try {
			create("system-3", "test", null);
		} catch (final ScopeException e) {
			fail();
		}
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Tests that retrieves the system.
	 */
	public void testGet1() {

		// Create the system.
		try {
			create("system-2", "test", null);
		} catch (final ScopeException e) {
			fail();
		}

		// Validate Scope exists.
		if (get("test") == null) {
			fail();
		}

	}

	/**
	 * Tests that context retrieves the same instance for the same scope name.
	 */
	public void testGet2() {

		// Create the system.
		ScopeFacade system1 = null;
		try {
			system1 = create("system-2", "test", null);
		} catch (final ScopeException e) {
			fail();
		}

		//
		final ScopeFacade system2 = get("test");

		//
		if (system1 != system2) {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Tests that a system exists .
	 */
	public void testExists1() {

		// Create Scope.
		try {
			create("system-2", "test", null);
		} catch (final ScopeException e) {
			fail();
		}

		// Validate Scope exists.
		if (!exists("test")) {
			fail();
		}

	}

	/**
	 * Tests that when a Scope is created in the Context of another Scope, created
	 * Scope does not exists in the Context of the domain Scope.
	 */
	public void testExists2() {
		try {

			//
			final ScopeFacade system = create("system-2", "test", null);

			//
			system.getContext().create(new Scope("b"));

			//
			if (exists("b")) {
				fail();
			}

		} catch (final Exception e) {
			fail();
		}
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Tests list of systems.
	 */
	public void testList1() {

		// Create the system.
		try {
			create("system-1", "1", null);
			create("system-1", "2", null);
			create("system-1", "3", null);
		} catch (final ScopeException e) {
			fail();
		}

		// Get available Scopes.
		final Enumeration<String> names = list();
		if (names == null) {
			fail();
		}

		//
		while (names.hasMoreElements()) {

			//
			final String name = names.nextElement();

			// Validate Scope name.
			if ((!name.equals("1")) && (!name.equals("2")) && (!name.equals("3"))) {
				fail();
			}

			// Validate Scope exists.
			if (!exists(name)) {
				fail();
			}

		}

	}

	/**
	 * Tests list of systems.
	 */
	public void testList2() {

		//
		final Enumeration<String> names = list();

		//
		if (names != null) {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Tests remove an existing system.
	 */
	public void testRemove1() {

		// Create the system.
		try {
			create("system-1", "test", null);
		} catch (final ScopeException e) {
			fail();
		}

		//
		if (!exists("test")) {
			fail();
		}

		//
		try {
			remove("test");
		} catch (final ScopeException e) {
			fail();
		}

		//
		if (exists("test")) {
			fail();
		}

	}

	/**
	 * Tests to remove a non existing system.
	 */
	public void testRemove2() {
		try {
			remove("test");
		} catch (final ScopeException e) {
			// DO NOTHING HERE, IT'S OK.
		}
	}

	/**
	 * Tests that a Scope with Scopes in its context cannot be deleted before all
	 * context Scopes are deleted first.
	 */
	public void testRemove3() {

		// Create Scopes.
		try {
			create("system-2", "test", null);
		} catch (final Exception e) {
			fail();
		}

		//
		try {

			//
			remove("test");

			//
			fail();

		} catch (final Exception e) {
			// DO NOTHING HERE, IT'S OK.
		}

	}

	/**
	 * Tests that a Scope cannot be removed if it has active children.
	 */
	public void testRemove4() {

		//
		try {
			create("system-1", "parent", null);
			create("system-3", "child-1", "parent");
			create("system-3", "child-2", "parent");
		} catch (final Exception e) {
			fail();
		}

		// Cannot remove 'parent' before removing 'child-1' and 'child-2'.
		try {

			//
			remove("parent");

			//
			fail();

		} catch (final ScopeException e) {
			// DO NOTHING HERE, IT'S OK.
		}

	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @throws IOException
	 */
	private static void serializeScope() throws IOException {

		//
		final Scope scope = new Scope();

		//
		scope.setInitParameter("user-name", "john");

		//
		scope.setProvider(SingletonProvider.DEFAULT_PROVIDER_NAME, SingletonProvider.class, null);

		//
		scope.setService(LogServiceConstants.DEFAULT_SERVICE_NAME, LogServiceImpl.class, null,
				ObjectDeserializerLoader.class);
		scope.setServiceParameter(LogServiceConstants.DEFAULT_SERVICE_NAME, ScopeL1Constants.PARAMETER_CONFIG_TARGET,
				ResourceL1Helper.DIRECTORY_SEPARATOR + ResourceL2Helper.DIRECTORY_META_INF
						+ ResourceL1Helper.DIRECTORY_SEPARATOR + PROJECT_NAME + ResourceL1Helper.DIRECTORY_SEPARATOR
						+ LogServiceConstants.DEFAULT_SERVICE_NAME + StringL2Helper.CHARACTER_HYPHEN + "1"
						+ ResourceL2Helper.FILE_EXTENSION_SEPARATOR + ResourceL2Helper.FILE_EXTENSION_SER);

		//
		scope.setObjectReference("map", SingletonProvider.DEFAULT_PROVIDER_NAME, Hashtable.class.getName());

		//
		serialize(scope, "system-1" + ResourceL2Helper.FILE_EXTENSION_SEPARATOR + ResourceL2Helper.FILE_EXTENSION_SER);

	}

	/**
	 * 
	 * @throws IOException
	 */
	private static void serializeLogService() throws IOException {

		//
		final ProxyService service = new ProxyService();

		//
		service.setName(LogServiceConstants.DEFAULT_SERVICE_NAME);
		service.setClazz(LogServiceImpl.class.getName());

		//
		service.setClient(ProxyServiceConstants.DEFAULT_CLIENT_NAME, ConsoleConnector.class);

		//
		serialize(service, LogServiceConstants.DEFAULT_SERVICE_NAME + StringL2Helper.CHARACTER_HYPHEN + "1"
				+ ResourceL2Helper.FILE_EXTENSION_SEPARATOR + ResourceL2Helper.FILE_EXTENSION_SER);

	}

}
