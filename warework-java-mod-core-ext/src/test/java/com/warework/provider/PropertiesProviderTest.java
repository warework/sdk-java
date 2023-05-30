package com.warework.provider;

import com.warework.core.scope.AbstractModCoreExtTestCase;
import com.warework.core.scope.ScopeFacade;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public class PropertiesProviderTest extends AbstractModCoreExtTestCase {

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testGetObject1() {
		try {

			//
			final ScopeFacade system = createXML("system-3");
			if (system == null) {
				fail();
			}

			//
			final String text = (String) system.getObject(PropertiesProvider.DEFAULT_PROVIDER_NAME, "name");

			//
			if ((text == null) || (!text.equals("James"))) {
				fail();
			}

		} catch (final Exception e) {
			fail();
		}
	}

}
