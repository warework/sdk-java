package com.warework.loader;

import com.warework.core.scope.AbstractCoreTestCase;
import com.warework.core.scope.ScopeException;
import com.warework.core.scope.ScopeFacade;
import com.warework.service.log.LogServiceFacade;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public class ProxyServiceClasspathJsonLoaderTest extends AbstractCoreTestCase {

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testProxyService1() {

		// Create the system.
		ScopeFacade system = null;
		try {
			system = createJSON("system-4");
		} catch (ScopeException e) {
			fail();
		}

		//
		final LogServiceFacade logService = (LogServiceFacade) system.getService("log-service");
		if (logService == null) {
			fail();
		} else if (!logService.existsClient("console-logger")) {
			fail();
		}

	}

}
