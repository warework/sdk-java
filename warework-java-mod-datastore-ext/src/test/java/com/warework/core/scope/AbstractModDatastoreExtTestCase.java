package com.warework.core.scope;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import com.warework.core.model.Scope;
import com.warework.core.util.helper.ResourceL1Helper;
import com.warework.core.util.helper.ResourceL2Helper;
import com.warework.loader.ObjectDeserializerLoader;

/**
 * Common context utilities for Datastore Service Extension Module test
 * cases.<br>
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public abstract class AbstractModDatastoreExtTestCase extends AbstractTestCase {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	//
	protected static final String PROJECT_NAME = "warework-java-mod-datastore-ext";

	// ///////////////////////////////////////////////////////////////////
	// PROTECTED METHODS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates a Scope in this context.
	 * 
	 * @param name Serialized file name. This is also the name of the Scope.<br>
	 *             <br>
	 * @return A new instance of a Scope. If a Scope of the same name is already
	 *         bound to the context, the existing Scope is returned.<br>
	 *         <br>
	 * @throws ScopeException If there is an error when trying to create the
	 *                        Scope.<br>
	 *                        <br>
	 */
	protected ScopeFacade createSER(final String name) throws ScopeException {

		// Create the configuration of the system.
		final Scope config = new Scope(name);

		// Setup the parameters.
		config.setInitParameter(ScopeL1Constants.PARAMETER_CONTEXT_LOADER, AbstractModDatastoreExtTestCase.class);
		config.setInitParameter(ScopeL1Constants.PARAMETER_CONFIG_TARGET,
				AbstractModDatastoreExtTestCase.class.getResource(ResourceL1Helper.DIRECTORY_SEPARATOR
						+ ResourceL2Helper.DIRECTORY_META_INF + ResourceL1Helper.DIRECTORY_SEPARATOR + PROJECT_NAME
						+ ResourceL1Helper.DIRECTORY_SEPARATOR + name + ResourceL2Helper.FILE_EXTENSION_SEPARATOR
						+ ResourceL2Helper.FILE_EXTENSION_SER));
		config.setInitParameter(ScopeL1Constants.PARAMETER_CONFIG_CLASS, ObjectDeserializerLoader.class);

		// Create new Scope.
		return create(config);

	}

	// ///////////////////////////////////////////////////////////////////

	/**
	 * Saves an object instance in a file.
	 * 
	 * @param object   Object to store in file.<br>
	 *                 <br>
	 * @param fileName Name of the file.<br>
	 *                 <br>
	 * @throws IOException
	 */
	protected final static void serialize(final Object object, final String fileName) throws IOException {

		// Write object with ObjectOutputStream
		final ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(fileName));

		// Write object out to disk
		stream.writeObject(object);

		// Close stream.
		stream.close();

	}

}
