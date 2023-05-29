package com.warework.core.scope;

import com.warework.core.model.Scope;

/**
 * Definition of Scope creation events.<br>
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public interface ScopeFactoryEventHandler {

	/**
	 * Override this method to upgrade the configuration of the Scope once it is
	 * loaded. This method will be invoked before the Scope is created.
	 * 
	 * @param config Scope's configuration.<br>
	 *               <br>
	 * @throws ScopeException If there is an error when trying to configure the
	 *                        Scope.<br>
	 *                        <br>
	 */
	void configLoaded(final Scope config) throws ScopeException;

	/**
	 * Override this method to update the Scope once it is created.
	 * 
	 * @param config Scope's configuration.<br>
	 *               <br>
	 * @param scope Scope to update.<br>
	 *              <br>
	 * @throws ScopeException If there is an error when trying to update the
	 *                        Scope.<br>
	 *                        <br>
	 */
	void scopeCreated(final Scope config, final ScopeFacade scope) throws ScopeException;

}
