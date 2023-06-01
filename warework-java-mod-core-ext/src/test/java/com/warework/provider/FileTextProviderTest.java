package com.warework.provider;

import com.warework.core.scope.AbstractModCoreExtTestCase;
import com.warework.core.scope.ScopeFacade;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public class FileTextProviderTest extends AbstractModCoreExtTestCase {

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

			//
			if (system == null) {
				fail();
			}

			//
			final String text = (String) system.getObject(FileTextProvider.DEFAULT_PROVIDER_NAME, "sample-1");

			//
			if ((text == null) || (!text.equals("SELECT"))) {
				fail();
			}

		} catch (final Exception e) {
			fail();
		}
	}

}
