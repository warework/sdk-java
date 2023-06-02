package com.warework.core.service;

import com.warework.core.model.Scope;
import com.warework.core.model.Service;
import com.warework.core.scope.AbstractCoreTestCase;
import com.warework.core.scope.ScopeException;
import com.warework.core.scope.ScopeFacade;
import com.warework.service.log.LogServiceConstants;
import com.warework.service.log.LogServiceFacade;
import com.warework.service.log.LogServiceImpl;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public class ServiceTest extends AbstractCoreTestCase {

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Tests that name is the same as the one provided.
	 */
	public void testGetName1() {

		// Create the configuration of the system.
		final Scope config = new Scope("test");
		{
			config.setService("custom-log", LogServiceImpl.class, null, null);
		}

		// Create the system.
		ScopeFacade system = null;
		try {
			system = create(config);
		} catch (final ScopeException e) {
			fail();
		}

		//
		final LogServiceFacade log = (LogServiceFacade) system.getService("custom-log");

		//
		if (!log.getName().equals("custom-log")) {
			fail();
		}

	}

	/**
	 * Tests that system is the same as the one provided.
	 */
	public void testGetScope1() {

		// Create the system.
		ScopeFacade system = null;
		try {

			//
			system = create(new Scope("test"));

			//
			system.createService("custom-log", LogServiceImpl.class, null);

		} catch (Exception e) {
			fail();
		}

		//
		final LogServiceFacade log = (LogServiceFacade) system.getService("custom-log");

		//
		if (log.getScopeFacade() != system) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testCreateServiceWithShortName1() {

		// Create the configuration of the system.
		final Scope config = new Scope("test");
		{

			//
			final Service service = new Service();

			//
			service.setScope(config);
			service.setName(LogServiceConstants.DEFAULT_SERVICE_NAME);
			service.setClazz("Log"); // THIS IS THE SHORT NAME!!!

			//
			config.setService(service);

		}

		// Create the system.
		ScopeFacade system = null;
		try {
			system = create(config);
		} catch (final Exception e) {
			fail();
		}

		//
		if (system.getService(LogServiceConstants.DEFAULT_SERVICE_NAME) == null) {
			fail();
		}

	}

}
