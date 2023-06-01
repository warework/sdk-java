package com.warework.loader;

import java.io.InputStream;

import com.warework.core.loader.LoaderException;
import com.warework.core.loader.ResourceLoader;
import com.warework.core.scope.ScopeL1Constants;
import com.warework.service.datastore.DatastoreClasspathJsonLoader;
import com.warework.service.datastore.model.ser.Datastore;
import com.warework.service.datastore.model.ser.DatastoreService;
import com.warework.service.datastore.model.ser.View;

/**
 * Parses a JSON object with the configutation of a Proxy Service.
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public final class DatastoreJsonLoader extends DatastoreClasspathJsonLoader {

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Loads the JSON file.
	 * 
	 * @return JSON file.<br>
	 *         <br>
	 * @throws LoaderException If there is an error when trying to load the
	 *                         resource.<br>
	 *                         <br>
	 */
	protected InputStream loadJSON() throws LoaderException {
		return new ResourceLoader(getScopeFacade()).getResource(
				getInitParameter(ScopeL1Constants.PARAMETER_CONTEXT_LOADER),
				getInitParameter(ScopeL1Constants.PARAMETER_CONFIG_TARGET));
	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates a new Proxy Service.
	 * 
	 * @return A new and empty Proxy Service.<br>
	 *         <br>
	 */
	protected DatastoreService createDatastoreService() {
		return new DatastoreService();
	}

	/**
	 * Creates a new Data Store.
	 * 
	 * @return A new and empty Data Store.<br>
	 *         <br>
	 */
	protected Datastore createDatastore() {
		return new Datastore();
	}

	/**
	 * Creates a new View.
	 * 
	 * @return A new and empty View.<br>
	 *         <br>
	 */
	protected View createView() {
		return new View();
	}

}
