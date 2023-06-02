package com.warework.core.scope;

import com.warework.core.model.Scope;

/**
 * 
 * 
 * @author Jose Schiaffino
 * @version ${project.version}
 */
public final class ServletRequestScope extends AbstractServletRequestScope {

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
	ServletRequestScope(final Scope config) throws ScopeException {
		super(config);
	}

}
