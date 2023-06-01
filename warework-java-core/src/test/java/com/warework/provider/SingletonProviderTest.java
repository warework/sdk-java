package com.warework.provider;

import com.warework.core.model.Scope;
import com.warework.core.scope.AbstractCoreTestCase;
import com.warework.core.scope.ScopeFacade;
import com.warework.core.util.bean.Parameter;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public class SingletonProviderTest extends AbstractCoreTestCase {

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Tests unique instances.
	 */
	public void testLookup1() {

		//
		final Scope config = new Scope("test");
		{
			config.enableDefaultLog();
			config.setProvider("singleton-provider", SingletonProvider.class, null);
		}

		// Create the system.
		ScopeFacade system = null;
		try {
			system = create(config);
		} catch (final Exception e) {
			fail();
		}

		//
		final Parameter bean1 = (Parameter) system.getObject("singleton-provider",
				"com.warework.core.util.bean.Parameter");

		//
		final Parameter bean2 = (Parameter) system.getObject("singleton-provider",
				"com.warework.core.util.bean.Parameter");

		//
		if (bean1 != bean2) {
			fail();
		}

	}

	/**
	 * Tests that returns null when invalid object is given.
	 */
	public void testLookup2() {

		//
		final Scope config = new Scope("test");
		{
			config.enableDefaultLog();
			config.setProvider("singleton-provider", SingletonProvider.class, null);
		}

		// Create the system.
		ScopeFacade system = null;
		try {
			system = create(config);
		} catch (final Exception e) {
			fail();
		}

		//
		final Object object = system.getObject("singleton-provider", "nothing");

		//
		if (object != null) {
			fail();
		}

	}

}
