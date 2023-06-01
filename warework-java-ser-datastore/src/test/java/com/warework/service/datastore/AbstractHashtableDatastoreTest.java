package com.warework.service.datastore;

import com.warework.core.model.Scope;
import com.warework.core.scope.AbstractSerDatastoreTestCase;
import com.warework.core.scope.ScopeException;
import com.warework.core.scope.ScopeFacade;
import com.warework.core.service.ServiceException;
import com.warework.service.datastore.client.HashtableViewImpl;
import com.warework.service.datastore.client.connector.HashtableConnector;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public abstract class AbstractHashtableDatastoreTest extends AbstractSerDatastoreTestCase {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	//
	protected final static String DATA_STORE_NAME = "hashtable-datastore";

	//
	protected final static String VIEW_NAME = "key-value-view";

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @return
	 * @throws ScopeException
	 * @throws ServiceException
	 */
	protected final ScopeFacade createScope1() throws ScopeException,
			ServiceException {

		//
		final Scope config = new Scope("test");
		{
			config.setService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME,
					DatastoreServiceImpl.class, null, null);
		}

		//
		final ScopeFacade scope = create(config);

		//
		final DatastoreServiceFacade service = (DatastoreServiceFacade) scope
				.getService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME);

		//
		service.createClient(DATA_STORE_NAME, HashtableConnector.class, null);

		//
		service.addView(DATA_STORE_NAME, HashtableViewImpl.class, VIEW_NAME,
				null, null);

		//
		return scope;

	}

	/**
	 * 
	 * @return
	 * @throws ScopeException 
	 */
	protected final ScopeFacade createScope2() throws ScopeException {
		return createJSON("system-1");
	}

}
