package com.warework.loader;

import com.warework.core.scope.AbstractModCoreExtTestCase;
import com.warework.core.scope.ScopeException;
import com.warework.core.scope.ScopeFacade;
import com.warework.core.service.ServiceException;
import com.warework.service.log.LogServiceConstants;
import com.warework.service.log.LogServiceFacade;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public class ProxyServiceXmlLoaderTest extends AbstractModCoreExtTestCase {

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
			system = createXML("system-2");
		} catch (final ScopeException e) {
			fail();
		}

		//
		if ((system.getService("log-service-1") == null) || (system.getService("log-service-2") == null)) {
			fail();
		}

		//
		{

			//
			Object service = system.getService("log-service-1");
			if ((service == null) || (!(service instanceof LogServiceFacade))) {
				fail();
			}

			//
			final LogServiceFacade logService = (LogServiceFacade) service;

			//
			if (!logService.existsClient("console-logger")) {
				fail();
			} else {

				//
				try {
					logService.connect("console-logger");
				} catch (final ServiceException e) {
					fail();
				}

				//
				logService.log("console-logger", "Hello!!!", LogServiceConstants.LOG_LEVEL_INFO);

			}

		}

		//
		{

			//
			Object service = system.getService("log-service-2");
			if ((service == null) || (!(service instanceof LogServiceFacade))) {
				fail();
			}

			//
			final LogServiceFacade logService = (LogServiceFacade) service;

			//
			if (!logService.existsClient("console-logger")) {
				fail();
			} else {

				//
				try {
					logService.connect("console-logger");
				} catch (final ServiceException e) {
					fail();
				}

				//
				logService.log("console-logger", "Hello!!!", LogServiceConstants.LOG_LEVEL_INFO);

			}

		}

	}

}
