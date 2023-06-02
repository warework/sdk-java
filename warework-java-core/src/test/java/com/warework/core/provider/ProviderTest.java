package com.warework.core.provider;

import com.warework.core.model.Scope;
import com.warework.core.scope.AbstractCoreTestCase;
import com.warework.core.scope.ScopeFacade;
import com.warework.provider.StandardProvider;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public class ProviderTest extends AbstractCoreTestCase {

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testProviderName1() {

		// Create the system.
		ScopeFacade system = null;
		try {
			system = create(new Scope("test"));
		} catch (final Exception e) {
			fail();
		}

		//
		try {

			//
			system.createProvider(null, StandardProvider.class, null);

			//
			fail();

		} catch (final ProviderException e) {
			// DO NOTHING, IT'S OK.
		}

	}

}
