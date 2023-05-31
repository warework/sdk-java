package com.warework.module.business.api;

import java.util.Collection;

import com.warework.core.scope.ScopeFacade;

/**
 * 
 * @author Jose Schiaffino
 * @version 3.0.0
 */
public interface ApiFacade {

	/**
	 * Gets the Scope Facade.
	 * 
	 * @return Scope Facade.<br>
	 *         <br>
	 */
	ScopeFacade getScopeFacade();

	/**
	 * 
	 * @return
	 * @throws ApiException
	 */
	Collection<String> getKeys() throws ApiException;

	/**
	 * 
	 */
	void flushCache();

}
