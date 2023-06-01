package com.warework.provider;

import com.warework.core.model.Provider;
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
public class DefaultProviderTest extends AbstractCoreTestCase {

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testGetObject1() {

		//
		final Scope config = new Scope("test");
		{
			config.enableDefaultLog();
			config.setProvider(StandardProvider.DEFAULT_PROVIDER_NAME, StandardProvider.class, null);
			config.setProviderParameter(StandardProvider.DEFAULT_PROVIDER_NAME, "bean1", Parameter.class);
			config.setProviderParameter(StandardProvider.DEFAULT_PROVIDER_NAME, "bean2", Parameter.class);
		}

		// Create the system.
		ScopeFacade system = null;
		try {
			system = create(config);
		} catch (final Exception e) {
			fail();
		}

		//
		final Parameter bean1 = (Parameter) system.getObject(StandardProvider.DEFAULT_PROVIDER_NAME, "bean1");

		//
		final Parameter bean2 = (Parameter) system.getObject(StandardProvider.DEFAULT_PROVIDER_NAME, "bean2");

		//
		if ((bean1 == null) || (bean2 == null)) {
			fail();
		}

		//
		if (bean1 == bean2) {
			fail();
		}

	}

	/**
	 * 
	 */
	public void testGetObject2() {

		//
		final Scope config = new Scope("test");
		{
			config.enableDefaultLog();
			config.setProvider(StandardProvider.DEFAULT_PROVIDER_NAME, StandardProvider.class, null);
			config.setProviderParameter(StandardProvider.DEFAULT_PROVIDER_NAME, "bean1", Parameter.class.getName());
			config.setProviderParameter(StandardProvider.DEFAULT_PROVIDER_NAME, "bean2", Parameter.class.getName());
		}

		// Create the system.
		ScopeFacade system = null;
		try {
			system = create(config);
		} catch (final Exception e) {
			fail();
		}

		//
		final Parameter bean1 = (Parameter) system.getObject(StandardProvider.DEFAULT_PROVIDER_NAME, "bean1");

		//
		final Parameter bean2 = (Parameter) system.getObject(StandardProvider.DEFAULT_PROVIDER_NAME, "bean2");

		//
		if ((bean1 == null) || (bean2 == null)) {
			fail();
		}

		//
		if (bean1 == bean2) {
			fail();
		}

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testCreateWithShortName1() {

		//
		final Scope config = new Scope("test");
		{

			//
			final Provider provider = new Provider();
			{
				provider.setScope(config);
				provider.setName(StandardProvider.DEFAULT_PROVIDER_NAME);
				provider.setClazz("Standard"); // THIS IS THE SHORT NAME!!!
			}

			//
			config.enableDefaultLog();
			config.setProvider(provider);
			config.setProviderParameter(StandardProvider.DEFAULT_PROVIDER_NAME, "bean1", Parameter.class.getName());
			config.setProviderParameter(StandardProvider.DEFAULT_PROVIDER_NAME, "bean2", Parameter.class.getName());
		}

		// Create the system.
		ScopeFacade system = null;
		try {
			system = create(config);
		} catch (final Exception e) {
			fail();
		}

		//
		final Parameter bean1 = (Parameter) system.getObject(StandardProvider.DEFAULT_PROVIDER_NAME, "bean1");

		//
		final Parameter bean2 = (Parameter) system.getObject(StandardProvider.DEFAULT_PROVIDER_NAME, "bean2");

		//
		if ((bean1 == null) || (bean2 == null)) {
			fail();
		}

		//
		if (bean1 == bean2) {
			fail();
		}

	}

}
