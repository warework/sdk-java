package com.warework.loader;

import java.util.Hashtable;
import java.util.Vector;

import com.warework.core.scope.AbstractCoreTestCase;
import com.warework.core.scope.ScopeException;
import com.warework.core.scope.ScopeFacade;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public class ScopeClasspathJsonLoaderTest extends AbstractCoreTestCase {

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	@SuppressWarnings("rawtypes")
	public void testSystem1() {

		// Create the system.
		ScopeFacade system = null;
		try {
			system = createJSON("system-1");
		} catch (final ScopeException e) {
			fail();
		}

		//
		String color = (String) system.getInitParameter("color");
		if (!color.equals("red")) {
			fail();
		}

		//
		if (!system.existsProvider("singleton-provider")) {
			fail();
		}

		//
		Vector list = (Vector) system.getObject("list");
		if (list == null) {
			fail();
		}

		//
		Hashtable map = (Hashtable) system.getObject("map");
		if (map == null) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testSystem2() {

		// Create the system.
		ScopeFacade system = null;
		try {
			system = createJSON("system-2");
		} catch (final ScopeException e) {
			fail();
		}

		//
		if (system.getService("log-service") == null) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testSystem3() {

		// Create the system.
		ScopeFacade system = null;
		try {
			system = createJSON("system-3");
		} catch (final ScopeException e) {
			fail();
		}

		//
		String color = (String) system.getInitParameter("color");
		if (!color.equals("red")) {
			fail();
		}

		//
		system = system.getContext().get("ctx-system-1");

		//
		color = (String) system.getInitParameter("color");
		if (!color.equals("blue1")) {
			fail();
		}

		//
		system = system.getDomain().getContext().get("ctx-system-2");

		//
		color = (String) system.getInitParameter("color");
		if (!color.equals("blue2")) {
			fail();
		}

	}

}
