package com.warework.core.scope;

import com.warework.core.model.Scope;
import com.warework.core.util.helper.ResourceL1Helper;
import com.warework.core.util.helper.ResourceL2Helper;
import com.warework.service.datastore.DatastoreServiceConstants;
import com.warework.service.datastore.DatastoreServiceImpl;

/**
 * Common context utilities for Properties Datastore test cases.<br>
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public abstract class AbstractSerDatastorePropertiesTestCase extends AbstractTestCase {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	//
	protected static final String PROJECT_NAME = "warework-java-ser-datastore-properties";

	//
	protected static final String PATH_RESOURCE_1 = ResourceL1Helper.DIRECTORY_SEPARATOR
			+ ResourceL1Helper.DIRECTORY_META_INF + ResourceL1Helper.DIRECTORY_SEPARATOR + PROJECT_NAME
			+ ResourceL1Helper.DIRECTORY_SEPARATOR + "sample-1" + ResourceL2Helper.FILE_EXTENSION_SEPARATOR
			+ ResourceL2Helper.FILE_EXTENSION_PROPERTIES;

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates a Scope in this context.
	 * 
	 * @param name Name of the Scope.<br>
	 *             <br>
	 * @return A new instance of a Scope. If a Scope of the same name is already
	 *         bound to the context, the existing Scope is returned.<br>
	 *         <br>
	 * @throws ScopeException If there is an error when trying to create the
	 *                        Scope.<br>
	 *                        <br>
	 */
	protected ScopeFacade create(final String name) throws ScopeException {

		// Create the configuration of the system.
		final Scope config = new Scope(name);

		// Setup the parameters.
		config.setService(DatastoreServiceConstants.DEFAULT_SERVICE_NAME, DatastoreServiceImpl.class, null, null);

		// Create new Scope.
		return create(config);

	}

}
