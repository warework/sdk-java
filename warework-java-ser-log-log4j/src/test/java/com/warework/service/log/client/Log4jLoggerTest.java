package com.warework.service.log.client;

import java.util.HashMap;
import java.util.Map;

import com.warework.core.model.Scope;
import com.warework.core.scope.AbstractSerLogLog4jlTestCase;
import com.warework.core.scope.ScopeFacade;
import com.warework.core.scope.ScopeL1Constants;
import com.warework.core.util.helper.ResourceL1Helper;
import com.warework.service.log.LogServiceConstants;
import com.warework.service.log.LogServiceFacade;
import com.warework.service.log.LogServiceImpl;
import com.warework.service.log.client.connector.AbstractLog4jConnector;
import com.warework.service.log.client.connector.Log4jPropertiesConnector;

/**
 * 
 */
public class Log4jLoggerTest extends AbstractSerLogLog4jlTestCase {

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testLog1() {
		try {

			//
			final Scope config = new Scope("test");
			{
				config.setInitParameter(ScopeL1Constants.PARAMETER_CONTEXT_LOADER, Log4jLoggerTest.class);
				config.setService(LogServiceConstants.DEFAULT_SERVICE_NAME, LogServiceImpl.class, null, null);
				config.setClient(LogServiceConstants.DEFAULT_SERVICE_NAME, LogServiceConstants.DEFAULT_CLIENT_NAME,
						Log4jPropertiesConnector.class, null);
				config.setClientParameter(LogServiceConstants.DEFAULT_SERVICE_NAME,
						LogServiceConstants.DEFAULT_CLIENT_NAME, AbstractLog4jConnector.PARAMETER_CONFIG_TARGET,
						ResourceL1Helper.DIRECTORY_SEPARATOR + ResourceL1Helper.DIRECTORY_META_INF
								+ ResourceL1Helper.DIRECTORY_SEPARATOR + PROJECT_NAME + "/log4j.properties");
			}

			//
			final ScopeFacade system = create(config);
			if (system == null) {
				fail();
			}

			//
			system.log("This is a sample DEBUG message!", LogServiceConstants.LOG_LEVEL_DEBUG);

			//
			system.log("This is a sample FATAL message!", LogServiceConstants.LOG_LEVEL_FATAL);

			//
			system.log("This is a sample INFO message!", LogServiceConstants.LOG_LEVEL_INFO);

			//
			system.log("This is a sample WARNING message!", LogServiceConstants.LOG_LEVEL_WARN);

			//
			system.log("This is a sample ERROR message!", Log4jLogger.LOG_LEVEL_ERROR);

			//
			system.log("This is a sample TRACE message!", Log4jLogger.LOG_LEVEL_TRACE);

		} catch (final Exception e) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testLog2() {
		try {

			//
			final Scope config = new Scope("test");
			{
				config.setInitParameter(ScopeL1Constants.PARAMETER_CONTEXT_LOADER, Log4jLoggerTest.class);
				config.setService(LogServiceConstants.DEFAULT_SERVICE_NAME, LogServiceImpl.class, null, null);
			}

			//
			final ScopeFacade system = create(config);
			if (system == null) {
				fail();
			}

			//
			final LogServiceFacade service = (LogServiceFacade) system
					.getService(LogServiceConstants.DEFAULT_SERVICE_NAME);

			//
			final Map<String, Object> serviceConfig = new HashMap<String, Object>();
			{
				serviceConfig.put(Log4jPropertiesConnector.PARAMETER_CONFIG_TARGET,
						ResourceL1Helper.DIRECTORY_SEPARATOR + ResourceL1Helper.DIRECTORY_META_INF
								+ ResourceL1Helper.DIRECTORY_SEPARATOR + PROJECT_NAME + "/log4j.properties");
			}

			//
			service.createClient("log4j-logger", Log4jPropertiesConnector.class, serviceConfig);

			//
			system.log("This is a sample DEBUG message!", LogServiceConstants.LOG_LEVEL_DEBUG);

			//
			system.log("This is a sample FATAL message!", LogServiceConstants.LOG_LEVEL_FATAL);

			//
			system.log("This is a sample INFO message!", LogServiceConstants.LOG_LEVEL_INFO);

			//
			system.log("This is a sample WARNING message!", LogServiceConstants.LOG_LEVEL_WARN);

			//
			system.log("This is a sample ERROR message!", Log4jLogger.LOG_LEVEL_ERROR);

			//
			system.log("This is a sample TRACE message!", Log4jLogger.LOG_LEVEL_TRACE);

		} catch (final Exception e) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testLog3() {

		//
		final Scope config = new Scope("test");
		{

			//
			config.setInitParameter(ScopeL1Constants.PARAMETER_CONTEXT_LOADER, Log4jLoggerTest.class);
			config.setService(LogServiceConstants.DEFAULT_SERVICE_NAME, LogServiceImpl.class, null, null);

			//
			config.setClient(LogServiceConstants.DEFAULT_SERVICE_NAME, "custom-logger", Log4jPropertiesConnector.class,
					null);

			//
			config.setClientParameter(LogServiceConstants.DEFAULT_SERVICE_NAME, "custom-logger",
					AbstractLog4jConnector.PARAMETER_CONFIG_TARGET,
					ResourceL1Helper.DIRECTORY_SEPARATOR + ResourceL1Helper.DIRECTORY_META_INF
							+ ResourceL1Helper.DIRECTORY_SEPARATOR + PROJECT_NAME + "/log4j.properties");

		}

		//
		ScopeFacade system = null;
		try {
			system = create(config);
		} catch (final Exception e) {
			fail();
		}

		//
		if (system == null) {
			fail();
		}

		//
		final LogServiceFacade service = (LogServiceFacade) system.getService(LogServiceConstants.DEFAULT_SERVICE_NAME);

		//
		try {
			service.log("custom-logger", "This is a sample DEBUG message!", LogServiceConstants.LOG_LEVEL_DEBUG);
		} catch (final Exception e) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testLog4() {
		try {

			//
			final Scope config = new Scope("test");
			{

				//
				config.setInitParameter(ScopeL1Constants.PARAMETER_CONTEXT_LOADER, Log4jLoggerTest.class);

				//
				config.setService(LogServiceConstants.DEFAULT_SERVICE_NAME, LogServiceImpl.class, null, null);

				//
				config.setClient(LogServiceConstants.DEFAULT_SERVICE_NAME, LogServiceConstants.DEFAULT_CLIENT_NAME,
						Log4jPropertiesConnector.class, null);
				config.setClientParameter(LogServiceConstants.DEFAULT_SERVICE_NAME,
						LogServiceConstants.DEFAULT_CLIENT_NAME, AbstractLog4jConnector.PARAMETER_CONFIG_TARGET,
						ResourceL1Helper.DIRECTORY_SEPARATOR + ResourceL1Helper.DIRECTORY_META_INF
								+ ResourceL1Helper.DIRECTORY_SEPARATOR + PROJECT_NAME + "/log4j.properties");

				//
				config.setClient(LogServiceConstants.DEFAULT_SERVICE_NAME, "sample1", Log4jPropertiesConnector.class,
						null);
				config.setClientParameter(LogServiceConstants.DEFAULT_SERVICE_NAME, "sample1",
						AbstractLog4jConnector.PARAMETER_CONFIG_TARGET,
						ResourceL1Helper.DIRECTORY_SEPARATOR + ResourceL1Helper.DIRECTORY_META_INF
								+ ResourceL1Helper.DIRECTORY_SEPARATOR + PROJECT_NAME + "/sample1.properties");

				//
				config.setClient(LogServiceConstants.DEFAULT_SERVICE_NAME, "sample2", Log4jPropertiesConnector.class,
						null);
				config.setClientParameter(LogServiceConstants.DEFAULT_SERVICE_NAME, "sample2",
						AbstractLog4jConnector.PARAMETER_CONFIG_TARGET,
						ResourceL1Helper.DIRECTORY_SEPARATOR + ResourceL1Helper.DIRECTORY_META_INF
								+ ResourceL1Helper.DIRECTORY_SEPARATOR + PROJECT_NAME + "/sample2.properties");

			}

			//
			final ScopeFacade system = create(config);
			if (system == null) {
				fail();
			}

			//
			system.log("This is a DEBUG message from default Logger!", LogServiceConstants.LOG_LEVEL_DEBUG);

			//
			final LogServiceFacade service = (LogServiceFacade) system
					.getService(LogServiceConstants.DEFAULT_SERVICE_NAME);

			//
			service.connect("sample1");
			service.connect("sample2");

			//
			service.log("sample1", "This is a FATAL message from 'sample1' Logger!",
					LogServiceConstants.LOG_LEVEL_FATAL);

			//
			service.log("sample2", "This is a INFO message from 'sample2' Logger!", LogServiceConstants.LOG_LEVEL_INFO);

		} catch (final Exception e) {
			fail();
		}
	}

}
