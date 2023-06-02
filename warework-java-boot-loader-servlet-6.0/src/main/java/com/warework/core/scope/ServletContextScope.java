package com.warework.core.scope;

import com.warework.core.model.Scope;

import jakarta.servlet.ServletContext;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public final class ServletContextScope extends AbstractServletContextScope implements ServletContext {

	// ///////////////////////////////////////////////////////////////////
	// CONSTRUCTORS
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Creates the Scope.
	 * 
	 * @param config Scope's configuration.<br>
	 *               <br>
	 * @throws ScopeException
	 */
	ServletContextScope(final Scope config) throws ScopeException {
		super(config);
	}

}
