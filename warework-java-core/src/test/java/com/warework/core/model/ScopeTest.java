package com.warework.core.model;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import com.warework.core.scope.AbstractCoreTestCase;
import com.warework.core.scope.ScopeFacade;
import com.warework.core.scope.ScopeL1Constants;
import com.warework.core.service.ProxyServiceConstants;
import com.warework.core.util.bean.Parameter;
import com.warework.provider.SingletonProvider;
import com.warework.provider.StandardProvider;
import com.warework.service.log.LogServiceConstants;
import com.warework.service.log.LogServiceImpl;
import com.warework.service.log.client.connector.ConsoleConnector;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public class ScopeTest extends AbstractCoreTestCase {

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testSetParent1() {

		// ------------ Main test

		//
		final Scope parent = new Scope("parent");
		{
			parent.setInitParameter("color", "blue");
		}

		//
		final Scope child = new Scope("child");
		{
			child.setParent("parent");
			child.setInitParameter("color", "red");
		}

		//
		if ((child.getParent() == null) || (!child.getParent().equals("parent"))) {
			fail();
		}

		// ------------ Extra test

		//
		try {
			
			// Create parent scope.
			create(parent);

			// Create son scope.
			final ScopeFacade system = create(child);
			if (system == null) {
				fail();
			}

			//
			if ((system.getInitParameter("color") == null) || (!(system.getInitParameter("color") instanceof String))) {
				fail();
			} else {

				//
				final String color = (String) system.getInitParameter("color");
				if (!color.equals("red")) {
					fail();
				}

			}

			//
			if ((system.getParent().getInitParameter("color") == null)
					|| (!(system.getParent().getInitParameter("color") instanceof String))) {
				fail();
			} else {

				//
				final String color = (String) system.getParent().getInitParameter("color");
				if (!color.equals("blue")) {
					fail();
				}

			}

		} catch (final Exception e) {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testSetContextScope1() {

		// ------------ Main test

		//
		final Scope ctxScope1Cfg = new Scope("context-scope-1");
		{
			ctxScope1Cfg.setInitParameter("color", "blue");
		}

		//
		final Scope ctxScope2Cfg = new Scope("context-scope-2");
		{
			ctxScope2Cfg.setInitParameter("color", "red");
		}

		//
		final Scope systemCfg = new Scope("system");
		{
			systemCfg.setContextScope(ctxScope1Cfg);
			systemCfg.setContextScope(ctxScope2Cfg);
		}

		//
		if ((systemCfg.getContextScope("context-scope-1") == null)
				|| (systemCfg.getContextScope("context-scope-1").getInitParameter("color") == null)
				|| (!(systemCfg.getContextScope("context-scope-1").getInitParameter("color") instanceof String))
				|| (!((String) systemCfg.getContextScope("context-scope-1").getInitParameter("color"))
						.equals("blue"))) {
			fail();
		}

		//
		if ((systemCfg.getContextScope("context-scope-2") == null)
				|| (systemCfg.getContextScope("context-scope-2").getInitParameter("color") == null)
				|| (!(systemCfg.getContextScope("context-scope-2").getInitParameter("color") instanceof String))
				|| (!((String) systemCfg.getContextScope("context-scope-2").getInitParameter("color")).equals("red"))) {
			fail();
		}

		// ------------ Extra test

		//
		try {

			//
			final ScopeFacade system = create(systemCfg);
			if (system == null) {
				fail();
			}

			//
			if ((system.getContext().get("context-scope-1") == null)
					|| (!(system.getContext().get("context-scope-1").getInitParameter("color") instanceof String))
					|| (!((String) system.getContext().get("context-scope-1").getInitParameter("color"))
							.equals("blue"))) {
				fail();
			}

			//
			if ((system.getContext().get("context-scope-2") == null)
					|| (!(system.getContext().get("context-scope-2").getInitParameter("color") instanceof String))
					|| (!((String) system.getContext().get("context-scope-2").getInitParameter("color"))
							.equals("red"))) {
				fail();
			}

		} catch (final Exception e) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testGetContextScopeNames1() {

		// ------------ Main test

		//
		final Scope ctxScope1 = new Scope("context-scope-1");
		{
			ctxScope1.setInitParameter("color", "blue");
		}

		//
		final Scope ctxScope2 = new Scope("context-scope-2");
		{
			ctxScope2.setInitParameter("color", "red");
		}

		//
		final Scope config = new Scope("system");
		{
			config.setContextScope(ctxScope1);
			config.setContextScope(ctxScope2);
		}

		//
		if (config.getContextScopeNames() != null) {

			//
			final Enumeration<String> names = config.getContextScopeNames();

			//
			int counter = 0;

			//
			while (names.hasMoreElements()) {

				//
				counter = counter + 1;

				//
				final String name = names.nextElement();

				//
				if (config.getContextScope(name) == null) {
					fail();
				}

			}

			//
			if (counter != 2) {
				fail();
			}

		} else {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testRemoveContextScope1() {

		//
		final Scope ctxScope1 = new Scope("context-scope-1");
		{
			ctxScope1.setInitParameter("color", "blue");
		}

		//
		final Scope ctxScope2 = new Scope("context-scope-2");
		{
			ctxScope2.setInitParameter("color", "red");
		}

		//
		final Scope ctxScope3 = new Scope("context-scope-3");
		{
			ctxScope3.setInitParameter("color", "green");
		}

		//
		final Scope config = new Scope("system");
		{

			//
			config.setContextScope(ctxScope1);
			config.setContextScope(ctxScope2);
			config.setContextScope(ctxScope3);

			//
			config.removeContextScope("context-scope-3");

		}

		//
		if (config.getContextScopeNames() != null) {

			//
			final Enumeration<String> names = config.getContextScopeNames();

			//
			int counter = 0;

			//
			while (names.hasMoreElements()) {

				//
				counter = counter + 1;

				//
				final String name = names.nextElement();

				//
				if (config.getContextScope(name) == null) {
					fail();
				}

			}

			//
			if (counter != 2) {
				fail();
			}

		} else {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testSetScopeTimeout1() {

		//
		final Scope config = new Scope("test");

		//
		config.setTimeout(500);

		//
		if ((config.getInitParameter(ScopeL1Constants.PARAMETER_SCOPE_TIMEOUT) == null)
				|| (!config.getInitParameter(ScopeL1Constants.PARAMETER_SCOPE_TIMEOUT).equals(new Long(500)))) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testEnableDefaultLog1() {

		//
		final Scope config = new Scope("test");

		//
		config.enableDefaultLog();

		//
		if ((config.getService(LogServiceConstants.DEFAULT_SERVICE_NAME) == null)
				|| (config.getService(LogServiceConstants.DEFAULT_SERVICE_NAME).getName() == null)
				|| (!config.getService(LogServiceConstants.DEFAULT_SERVICE_NAME).getClazz()
						.equals(LogServiceImpl.class.getName()))) {
			fail();
		} else {

			//
			final ProxyService service = (ProxyService) config.getService(LogServiceConstants.DEFAULT_SERVICE_NAME);

			//
			if ((service.getClient(ProxyServiceConstants.DEFAULT_CLIENT_NAME) == null)
					|| (!service.getClient(ProxyServiceConstants.DEFAULT_CLIENT_NAME).getConnector()
							.equals(ConsoleConnector.class.getName()))) {
				fail();
			}

		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testSetSingletonObject1() {

		// ------------ Main test

		//
		final Scope config = new Scope("test");

		//
		config.setSingletonObject("sample", Parameter.class);

		//
		if ((config.getProvider(SingletonProvider.DEFAULT_PROVIDER_NAME) == null)
				|| (!config.getProvider(SingletonProvider.DEFAULT_PROVIDER_NAME).getClazz()
						.equals(SingletonProvider.class.getName()))
				|| (config.getObjectReference("sample") == null)
				|| (!config.getObjectReference("sample").getProvider().equals(SingletonProvider.DEFAULT_PROVIDER_NAME))
				|| (!config.getObjectReference("sample").getObject().equals(Parameter.class.getName()))
				|| (!config.getObjectReference("sample").getScope().getName().equals("test"))) {
			fail();
		}

		// ------------ Extra test

		//
		try {

			//
			final ScopeFacade system = create(config);

			//
			if (system == null) {
				fail();
			}

			//
			if ((system.getObject("sample") != null) && (!(system.getObject("sample") instanceof Parameter))) {
				fail();
			}

			//
			if (system.getObject("sample") != system.getObject("sample")) {
				fail();
			}

		} catch (final Exception e) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testSetStandardObject1() {

		//
		final Scope config = new Scope("test");

		//
		config.setStandardObject("sample", Parameter.class);

		//
		if ((config.getProvider(StandardProvider.DEFAULT_PROVIDER_NAME) == null)
				|| (!config.getProvider(StandardProvider.DEFAULT_PROVIDER_NAME).getClazz()
						.equals(StandardProvider.class.getName()))
				|| (config.getProviderParameter(StandardProvider.DEFAULT_PROVIDER_NAME, "sample") == null)
				|| (!config.getProviderParameter(StandardProvider.DEFAULT_PROVIDER_NAME, "sample")
						.equals(Parameter.class))
				|| (config.getObjectReference("sample") == null)
				|| (!config.getObjectReference("sample").getProvider().equals(StandardProvider.DEFAULT_PROVIDER_NAME))
				|| (!config.getObjectReference("sample").getObject().equals("sample"))
				|| (!config.getObjectReference("sample").getScope().getName().equals("test"))) {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testSetInitParameter1() {

		//
		final Scope config = new Scope("test");

		//
		config.setInitParameter("param1", new Integer(250));

		//
		if ((config.getInitParameter("param1") == null)
				|| (!config.getInitParameter("param1").equals(new Integer(250)))) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testGetInitParameterNames1() {

		//
		final Scope config = new Scope("test");

		//
		config.setInitParameter("param1", new Integer(1));
		config.setInitParameter("param2", new Integer(2));
		config.setInitParameter("param3", new Integer(3));

		//
		final Enumeration<String> paramNames = config.getInitParameterNames();

		//
		int counter = 0;

		//
		while (paramNames.hasMoreElements()) {

			//
			counter = counter + 1;

			//
			final String name = paramNames.nextElement();

			//
			if (config.getInitParameter(name) == null) {
				fail();
			}

		}

		//
		if (counter != 3) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testRemoveInitParameter1() {

		//
		final Scope config = new Scope("test");

		//
		config.setInitParameter("param1", new Integer(1));
		config.setInitParameter("param2", new Integer(2));
		config.setInitParameter("param3", new Integer(3));

		//
		config.removeInitParameter("param2");

		//
		final Enumeration<String> paramNames = config.getInitParameterNames();

		//
		int counter = 0;

		//
		while (paramNames.hasMoreElements()) {

			//
			counter = counter + 1;

			//
			final String name = paramNames.nextElement();

			//
			if (config.getInitParameter(name) == null) {
				fail();
			}

		}

		//
		if (counter != 2) {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Tests that if type is null an exception is thrown.
	 */
	public void testSetProvider2() {

		//
		final Scope config = new Scope("test");

		//
		try {

			//
			config.setProvider("p", null, null);

			//
			fail();

		} catch (Exception e) {
		}

	}

	/**
	 * Tests that if parameters are null an exception is not thrown.
	 */
	public void testSetProvider3() {

		//
		final Scope config = new Scope("test");

		//
		try {
			config.setProvider("p", SingletonProvider.class, null);
		} catch (Exception e) {
			fail();
		}

	}

	/**
	 * Tests that diferent kind of parameters.
	 */
	public void testSetProvider4() {

		//
		final Scope config = new Scope("test");

		//
		final Map<String, Object> params = new HashMap<String, Object>();
		{
			params.put("a", "s");
			params.put("b", new Integer(23));
		}

		//
		try {
			config.setProvider("p", SingletonProvider.class, params);
		} catch (Exception e) {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Tests that if name is null an exception is thrown.
	 */
	public void testGetProvider2() {

		//
		final Scope config = new Scope("test");

		//
		try {

			//
			config.setProvider("p", SingletonProvider.class, null);

			//
			final Provider p = config.getProvider("s");

			//
			if (p != null) {
				fail();
			}

		} catch (final Exception e) {
			fail();
		}

	}

	/**
	 * Tests that if no provider is set then null is what you get.
	 */
	public void testGetProvider3() {

		//
		final Scope config = new Scope("test");

		//
		try {

			//
			final Provider p = config.getProvider(null);

			//
			if (p != null) {
				fail();
			}

		} catch (final Exception e) {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Tests names.
	 */
	public void testGetProviderNames1() {

		//
		final Scope config = new Scope("test");

		//
		try {

			//
			config.setProvider("p1", SingletonProvider.class, null);
			config.setProvider("p2", SingletonProvider.class, null);

			//
			final Enumeration<String> names = config.getProviderNames();

			//
			while (names.hasMoreElements()) {

				//
				final String name = names.nextElement();

				//
				if ((!name.equals("p1")) && (!name.equals("p2"))) {
					fail();
				}

			}

		} catch (final Exception e) {
			fail();
		}

	}

	/**
	 * Tests names.
	 */
	public void testGetProviderNames2() {

		//
		final Scope config = new Scope("test");

		//
		try {

			//
			config.setProvider("p1", SingletonProvider.class, null);
			config.setProvider("p1", SingletonProvider.class, null);

			//
			final Enumeration<String> names = config.getProviderNames();

			//
			while (names.hasMoreElements()) {

				//
				final String name = names.nextElement();

				//
				if (!name.equals("p1")) {
					fail();
				}

			}

		} catch (Exception e) {
			fail();
		}

	}

	/**
	 * Tests names.
	 */
	public void testGetProviderNames3() {

		//
		final Scope config = new Scope("test");

		//
		try {

			//
			config.setProvider("p1", SingletonProvider.class, null);
			config.setProvider("p2", SingletonProvider.class, null);

			//
			final Enumeration<String> names = config.getProviderNames();

			//
			while (names.hasMoreElements()) {

				//
				final String name = names.nextElement();

				//
				if ((!name.equals("p1")) && (!name.equals("p2"))) {
					fail();
				}

			}

		} catch (final Exception e) {
			fail();
		}

	}

	/**
	 * Tests names.
	 */
	public void testGetProviderNames4() {

		//
		final Scope config = new Scope("test");

		//
		try {

			//
			final Enumeration<String> names = config.getProviderNames();

			//
			if (names != null) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Tests to delete one provider.
	 */
	public void testRemoveProvider1() {

		//
		final Scope config = new Scope("test");

		//
		try {

			//
			config.setProvider("p1", SingletonProvider.class, null);

			//
			config.removeProvider("p1");

			//
			if (config.getProvider("p1") != null) {
				fail();
			}

		} catch (final Exception e) {
			fail();
		}

	}

	/**
	 * Tests to delete every provider under the same name in every scope.
	 */
	public void testRemoveProvider3() {

		//
		final Scope config = new Scope("test");

		//
		try {

			//
			config.setProvider("p1", SingletonProvider.class, null);
			config.setProvider("p2", SingletonProvider.class, null);

			//
			config.removeProvider("p1");

			//
			if (config.getProvider("p1") != null) {
				fail();
			}
			if (config.getProvider("p2") == null) {
				fail();
			}

			if (config.getProvider("p1") != null) {
				fail();
			}
			if (config.getProvider("p2") == null) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Tests that if type is null an exception is thrown.
	 */
	public void testSetService2() {

		//
		final Scope config = new Scope("test");

		//
		try {

			//
			config.setService("p", null, null, null);

			//
			fail();

		} catch (Exception e) {
		}

	}

	/**
	 * Tests that if parameters are null an exception is not thrown.
	 */
	public void testSetService3() {

		//
		final Scope config = new Scope("test");

		//
		try {
			config.setService("p", LogServiceImpl.class, null, null);
		} catch (Exception e) {
			fail();
		}

	}

	/**
	 * Tests that diferent kind of parameters.
	 */
	public void testSetService4() {

		//
		final Scope config = new Scope("test");

		//
		final Map<String, Object> params = new HashMap<String, Object>();
		{
			params.put("a", "s");
			params.put("b", new Integer(23));
		}

		//
		try {
			config.setService("p", LogServiceImpl.class, params, null);
		} catch (Exception e) {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Tests that if scope is null then system scope is used.
	 */
	public void testGetService2() {

		//
		final Scope config = new Scope("test");

		//
		try {

			//
			config.setService("p", LogServiceImpl.class, null, null);

			//
			final Service p = config.getService("p");

			//
			if (p == null) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	/**
	 * Tests that if no provider is set then null is what you get.
	 */
	public void testGetService3() {

		//
		final Scope config = new Scope("test");

		//
		try {

			//
			final Service p = config.getService(null);

			//
			if (p != null) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	/**
	 * Tests that if name is null an exception is thrown.
	 */
	public void testGetService4() {

		//
		final Scope config = new Scope("test");

		//
		try {

			//
			config.setService("p", LogServiceImpl.class, null, null);

			//
			final Service s = config.getService("s");

			//
			if (s != null) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Tests names.
	 */
	public void testGetServiceNames1() {

		//
		final Scope config = new Scope("test");

		//
		try {

			//
			config.setService("p1", LogServiceImpl.class, null, null);
			config.setService("p2", LogServiceImpl.class, null, null);

			//
			final Enumeration<String> names = config.getServiceNames();

			//
			while (names.hasMoreElements()) {

				//
				final String name = names.nextElement();

				//
				if ((!name.equals("p1")) && (!name.equals("p2"))) {
					fail();
				}

			}

		} catch (Exception e) {
			fail();
		}

	}

	/**
	 * Tests names.
	 */
	public void testGetServiceNames2() {

		//
		final Scope config = new Scope("test");

		//
		try {

			//
			config.setService("p1", LogServiceImpl.class, null, null);
			config.setService("p1", LogServiceImpl.class, null, null);

			//
			final Enumeration<String> names = config.getServiceNames();
			while (names.hasMoreElements()) {

				//
				final String name = names.nextElement();
				if (!name.equals("p1")) {
					fail();
				}

			}

		} catch (Exception e) {
			fail();
		}

	}

	/**
	 * Tests names.
	 */
	public void testGetServiceNames3() {

		//
		final Scope config = new Scope("test");

		//
		try {

			//
			config.setService("p1", LogServiceImpl.class, null, null);
			config.setService("p2", LogServiceImpl.class, null, null);

			//
			final Enumeration<String> names = config.getServiceNames();

			//
			while (names.hasMoreElements()) {

				//
				final String name = names.nextElement();

				//
				if ((!name.equals("p1")) && (!name.equals("p2"))) {
					fail();
				}

			}

		} catch (Exception e) {
			fail();
		}

	}

	/**
	 * Tests names.
	 */
	public void testGetServiceNames4() {

		//
		final Scope config = new Scope("test");

		//
		try {

			//
			final Enumeration<String> names = config.getServiceNames();

			//
			if (names != null) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Tests to delete every service under the same name in every scope.
	 */
	public void testRemoveService3() {

		//
		final Scope config = new Scope("test");

		//
		try {

			//
			config.setService("p1", LogServiceImpl.class, null, null);
			config.setService("p2", LogServiceImpl.class, null, null);

			config.setService("p1", LogServiceImpl.class, null, null);
			config.setService("p2", LogServiceImpl.class, null, null);

			//
			config.removeService("p1");

			//
			if (config.getService("p1") != null) {
				fail();
			}
			if (config.getService("p2") == null) {
				fail();
			}

			if (config.getService("p1") != null) {
				fail();
			}
			if (config.getService("p2") == null) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Tests that if scope is null then system scope is used.
	 */
	public void testGetObject2() {

		//
		final Scope config = new Scope("test");

		//
		try {

			//
			config.setObjectReference("o", "provider", "object");

			//
			final ObjectReference p = config.getObjectReference("o");

			//
			if (p == null) {
				fail();
			}

		} catch (Exception e) {
			fail();
		}

	}

	/**
	 * Tests that if no provider is set then null is what you get.
	 */
	public void testGetObject3() {

		//
		final Scope config = new Scope("test");

		//
		try {

			//
			final ObjectReference p = config.getObjectReference(null);

			//
			if (p != null) {
				fail();
			}

		} catch (final Exception e) {
			fail();
		}

	}

	/**
	 * Tests that if name is null an exception is thrown.
	 */
	public void testGetObject4() {

		//
		final Scope config = new Scope("test");

		//
		try {

			//
			config.setObjectReference("o", "provider", "object");

			//
			final ObjectReference p = config.getObjectReference("s");

			//
			if (p != null) {
				fail();
			}

		} catch (final Exception e) {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Tests to delete one service.
	 */
	public void testRemoveObject1() {

		//
		final Scope config = new Scope("test");

		//
		try {

			//
			config.setObjectReference("p1", "provider", "object");
			config.setObjectReference("p1", "provider", "object");

			//
			config.removeObjectReference("p1");

			//
			if (config.getObjectReference("p1") != null) {
				fail();
			}

		} catch (final Exception e) {
			fail();
		}

	}

}
