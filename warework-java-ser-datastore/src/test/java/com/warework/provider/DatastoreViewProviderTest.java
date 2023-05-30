package com.warework.provider;

import java.util.HashMap;
import java.util.Map;

import com.warework.core.provider.ProviderException;
import com.warework.core.scope.ScopeException;
import com.warework.core.scope.ScopeFacade;
import com.warework.core.service.ServiceException;
import com.warework.service.datastore.AbstractHashtableDatastoreTest;
import com.warework.service.datastore.DatastoreServiceConstants;
import com.warework.service.datastore.view.KeyValueView;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public class DatastoreViewProviderTest extends AbstractHashtableDatastoreTest {

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	//
	private final static String PROVIDER_NAME = DatastoreViewProvider.DEFAULT_PROVIDER_NAME;

	// ///////////////////////////////////////////////////////////////////
	// PUBLIC METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 */
	public void testGetObject1() {
		try {

			//
			final ScopeFacade scope = getScope1();

			//
			final Object view = scope.getObject(PROVIDER_NAME, DATA_STORE_NAME);

			//
			if (!(view instanceof KeyValueView)) {
				fail();
			}

		} catch (final Exception e) {
			fail();
		}
	}

	/**
	 * 
	 */
	public void testGetObject2() {
		try {

			//
			final ScopeFacade scope = getScope2();

			//
			final Object view = scope.getObject(PROVIDER_NAME, VIEW_NAME);

			//
			if (!(view instanceof KeyValueView)) {
				fail();
			}

		} catch (final Exception e) {
			fail();
		}
	}

	// ///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @return
	 * @throws ServiceException
	 * @throws ScopeException
	 * @throws ProviderException
	 */
	private ScopeFacade getScope1() throws ScopeException, ServiceException,
			ProviderException {

		//
		final ScopeFacade system = createScope1();

		//
		final Map<String, Object> config = new HashMap<String, Object>();
		{
			config.put(DatastoreViewProvider.PARAMETER_SERVICE_NAME,
					DatastoreServiceConstants.DEFAULT_SERVICE_NAME);
		}

		//
		system.createProvider(PROVIDER_NAME, DatastoreViewProvider.class,
				config);

		//
		return system;

	}

	/**
	 * 
	 * @return
	 * @throws ServiceException
	 * @throws ScopeException
	 * @throws ProviderException
	 */
	private ScopeFacade getScope2() throws ScopeException, ServiceException,
			ProviderException {

		//
		final ScopeFacade system = createScope1();

		//
		final Map<String, Object> config = new HashMap<String, Object>();
		{
			config.put(DatastoreViewProvider.PARAMETER_SERVICE_NAME,
					DatastoreServiceConstants.DEFAULT_SERVICE_NAME);
			config.put(DatastoreViewProvider.PARAMETER_DATASTORE_NAME,
					DATA_STORE_NAME);
		}

		//
		system.createProvider(PROVIDER_NAME, DatastoreViewProvider.class,
				config);

		//
		return system;

	}

}
