package com.warework.core.scope;

import com.warework.core.model.Scope;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public class ScopeFacadeTest extends AbstractCoreTestCase {

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testGetName1() {

		// Create the system.
		ScopeFacade system = null;
		try {
			system = create(new Scope("test"));
		} catch (final ScopeException e) {
			fail();
		}

		//
		if (!system.getName().equals("test")) {
			fail();
		}

	}

}
