package com.warework.core.scope;

import com.warework.core.model.Scope;
import com.warework.core.util.helper.ResourceL1Helper;
import com.warework.loader.ScopeClasspathJsonLoader;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public abstract class AbstractSerConverterTestCase extends AbstractTestCase {

	// ///////////////////////////////////////////////////////////////////
	// CONSTANTS
	// ///////////////////////////////////////////////////////////////////

	//
	protected static final String PROJECT_NAME = "warework-java-ser-converter";

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
	protected ScopeFacade create(final String name) throws ScopeException {

		// Create the configuration of the system.
		final Scope config = new Scope(name);

		// Setup the parameters.
		config.setInitParameter(ScopeL1Constants.PARAMETER_CONTEXT_LOADER, AbstractSerConverterTestCase.class);
		config.setInitParameter(ScopeL1Constants.PARAMETER_CONFIG_CLASS, ScopeClasspathJsonLoader.class);
		config.setInitParameter(ScopeL1Constants.PARAMETER_CONFIG_TARGET,
				ResourceL1Helper.DIRECTORY_SEPARATOR + ResourceL1Helper.DIRECTORY_META_INF
						+ ResourceL1Helper.DIRECTORY_SEPARATOR + PROJECT_NAME + ResourceL1Helper.DIRECTORY_SEPARATOR
						+ name + ResourceL1Helper.FILE_EXTENSION_SEPARATOR + ResourceL1Helper.FILE_EXTENSION_JSON);

		// Create new Scope.
		return create(config);

	}

}
