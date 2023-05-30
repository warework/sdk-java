package com.warework.loader;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.warework.core.scope.AbstractModCoreExtTestCase;
import com.warework.core.scope.ScopeException;
import com.warework.core.scope.ScopeFacade;
import com.warework.service.log.LogServiceFacade;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public class ScopeXmlLoaderTest extends AbstractModCoreExtTestCase {

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void test1() {

		//
		ScopeFacade system = null;
		try {
			system = createXML("system-1");
		} catch (final ScopeException e) {
			fail();
		}

		// -------------------------------
		// MAIN SCOPE
		// -------------------------------

		// /////////////////// INIT.PARAMS

		// Validate 'name' init. parameter.
		if ((system.getInitParameter("name") == null) || (!(system.getInitParameter("name") instanceof String))) {
			fail();
		} else {

			// Get the value of the initialization parameter.
			final String param = (String) system.getInitParameter("name");

			// Validate the value of the initialization parameter.
			if (!param.equals("john")) {
				fail();
			}

		}

		// Validate 'name' init. parameter.
		if ((system.getInitParameter("color") == null) || (!(system.getInitParameter("color") instanceof String))) {
			fail();
		} else {

			// Get the value of the initialization parameter.
			final String param = (String) system.getInitParameter("color");

			// Validate the value of the initialization parameter.
			if (!param.equals("red")) {
				fail();
			}

		}

		// /////////////////// PROVIDERS

		//
		if (!system.existsProvider("singleton-provider")) {
			fail();
		}
		if (!system.existsProvider("standard-provider")) {
			fail();
		}
		if (system.getService("log-service") == null) {
			fail();
		}
		if (system.getObject("string-builder") == null) {
			fail();
		}

		// /////////////////// OBJECTS

		//
		Object defaultSet = system.getObject("standard-provider", "default-set");
		if ((defaultSet == null) || (!(defaultSet instanceof Set))) {
			fail();
		}

		//
		Object defaultMap = system.getObject("standard-provider", "default-map");
		if ((defaultMap == null) || (!(defaultMap instanceof Map))) {
			fail();
		}

		//
		Object defaultList = system.getObject("standard-provider", "default-list");
		if ((defaultList == null) || (!(defaultList instanceof List))) {
			fail();
		}

		//
		Object logService = system.getService("log-service");
		if ((logService == null) || (!(logService instanceof LogServiceFacade))) {
			fail();
		}

		//
		Object stringBuilderObject = system.getObject("string-builder");
		if ((stringBuilderObject == null) || (!(stringBuilderObject instanceof StringBuilder))) {
			fail();
		}

		// -------------------------------
		// CONTEXT SCOPE (C1)
		// -------------------------------

		// Get the parent Scope.
		system = system.getContext().get("system-c1");

		//
		validateScope(system, "system-c1", "david", "yellow");

		// -------------------------------
		// CONTEXT SCOPE (C2)
		// -------------------------------

		// Get the parent Scope.
		// system = system.getParent();
		system = system.getDomain().getContext().get("system-c2");

		//
		validateScope(system, "system-c2", "rita", "cyan");

	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @param scope
	 * @param scopeName
	 * @param name
	 * @param color
	 */
	private void validateScope(ScopeFacade scope, String scopeName, String name, String color) {

		// Validate Scope name.
		if ((scope.getName() == null) || (!scope.getName().equals(scopeName))) {
			fail();
		}

		// /////////////////// INIT.PARAMS

		// Validate 'name' init. parameter.
		if ((scope.getInitParameter("name") == null) || (!(scope.getInitParameter("name") instanceof String))) {
			fail();
		} else {

			// Get the value of the initialization parameter.
			String param = (String) scope.getInitParameter("name");

			// Validate the value of the initialization parameter.
			if (!param.equals(name)) {
				fail();
			}

		}

		// Validate 'name' init. parameter.
		if ((scope.getInitParameter("color") == null) || (!(scope.getInitParameter("color") instanceof String))) {
			fail();
		} else {

			// Get the value of the initialization parameter.
			String param = (String) scope.getInitParameter("color");

			// Validate the value of the initialization parameter.
			if (!param.equals(color)) {
				fail();
			}

		}

	}

}
